/*
 * Copyright 2018 Edmunds.com, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.edmunds.rest.databricks;

import com.edmunds.rest.databricks.DTO.ExportFormatDTO;
import com.edmunds.rest.databricks.DTO.JobDTO;
import com.edmunds.rest.databricks.DTO.JobSettingsDTO;
import com.edmunds.rest.databricks.DTO.LanguageDTO;
import com.edmunds.rest.databricks.DTO.NotebookTaskDTO;
import com.edmunds.rest.databricks.DTO.RunDTO;
import com.edmunds.rest.databricks.DTO.RunLifeCycleStateDTO;
import com.edmunds.rest.databricks.DTO.RunResultStateDTO;
import com.edmunds.rest.databricks.DTO.RunsDTO;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest.ImportWorkspaceRequestBuilder;
import com.edmunds.rest.databricks.service.JobService;
import com.edmunds.rest.databricks.service.WorkspaceService;
import java.io.InputStream;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.edmunds.rest.databricks.fixtures.DatabricksFixtures.HOSTNAME;
import static com.edmunds.rest.databricks.fixtures.DatabricksFixtures.TOKEN;
import static org.testng.Assert.assertEquals;

public class JobRunnerTest {
  private static final String JOB_NAME = "JobRunnerTest_test_job";
  private static final String NOTEBOOK_PATH = "/tmp/testing/test_notebook.scala";
  private static final Long CHECK_INTERVAL = 10000L;
  private JobService service;
  private WorkspaceService workspaceService;
  private Long jobId = null;
  private String[] argsWithJobId;
  private String[] argsWithJobName;
  private String[] argsWithInvalidJobName;

  @BeforeClass(enabled = true)
  public void setUpOnce() throws IOException, DatabricksRestException, InterruptedException {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();
    service = factory.getJobService();
    workspaceService = factory.getWorkspaceService();

    //TODO repeated code from job service, also keep DRY with notebook path
    workspaceService.mkdirs("/tmp/testing/");
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("test_notebook.scala");
    byte[] content = IOUtils.toByteArray(stream);
    ImportWorkspaceRequest request = new ImportWorkspaceRequestBuilder(NOTEBOOK_PATH)
        .withContent(content)
        .withFormat(ExportFormatDTO.SOURCE)
        .withLanguage(LanguageDTO.SCALA)
        .withOverwrite(true)
        .build();
    workspaceService.importWorkspace(request);

    NotebookTaskDTO notebook_task = new NotebookTaskDTO();
    notebook_task.setNotebookPath(NOTEBOOK_PATH);
    String defaultClusterId = TestUtil.getTestClusterId(factory.getClusterService());
    JobSettingsDTO jobSettingsDTO = new JobSettingsDTO();
    jobSettingsDTO.setName(JOB_NAME);
    jobSettingsDTO.setExistingClusterId(defaultClusterId);
    jobSettingsDTO.setNotebookTask(notebook_task);

    // there's possibility test TearDownFailure. it cause test job not-deleted.
    List<JobDTO> jobList = service.getJobsByName(JOB_NAME);
    for (JobDTO jobDTO : jobList) {
      service.deleteJob(jobDTO.getJobId());
    }

    jobId = service.createJob(jobSettingsDTO);

    argsWithJobId = new String[] {
        "-t " + TOKEN,
        "-h " + HOSTNAME,
        "--checkInterval " + CHECK_INTERVAL,
        "-j " + jobId
    };

    argsWithJobName = new String[] {
        "-t " + TOKEN,
        "-h " + HOSTNAME,
        "--checkInterval " + CHECK_INTERVAL,
        "-n " + JOB_NAME,
    };

    argsWithInvalidJobName =
        new String[] {
            "-t " + TOKEN,
            "-h " + HOSTNAME,
            "--checkInterval " + CHECK_INTERVAL,
            "-n " + "Fake Job Name"
        };

  }


  @AfterClass(alwaysRun = true)
  public void tearDownOnce() throws IOException, DatabricksRestException {
    service.deleteJob(jobId);

    workspaceService.delete(NOTEBOOK_PATH, false);
  }

  @Test
  public void main_whenCalledWithArguments_startsRunOfJob()
      throws InterruptedException, DatabricksRestException, IOException, ParseException {
    int expected = getNumberOfRuns(jobId) + 1;

    JobRunner.main(argsWithJobId);

    int numberOfRuns = getNumberOfRuns(jobId);
    assertEquals(numberOfRuns, expected);
  }

  @Test
  public void main_whenCalledWithJobName_startsRunOfJob()
      throws InterruptedException, DatabricksRestException, ParseException, IOException {
    int expected = getNumberOfRuns(jobId) + 1;

    JobRunner.main(argsWithJobName);

    int numberOfRuns = getNumberOfRuns(jobId);
    assertEquals(numberOfRuns, expected);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void main_whenCalledWithInvalidJobName_throwsIllegalArgumentException()
      throws InterruptedException, DatabricksRestException, ParseException, IOException {
    JobRunner.main(argsWithInvalidJobName);
  }

  @Test
  public void main_whenCalled_runsToCompletionWithSuccess()
      throws InterruptedException, DatabricksRestException, ParseException, IOException {
    JobRunner.main(argsWithJobId);

    RunDTO runDTO = getMostRecentRun(jobId);

    assertEquals(runDTO.getState().getLifeCycleState(), RunLifeCycleStateDTO.TERMINATED);
    assertEquals(runDTO.getState().getResultState(), RunResultStateDTO.SUCCESS);
  }

  private RunDTO getMostRecentRun(Long jobId) throws IOException, DatabricksRestException {
    return service.listRuns(jobId, null, null, null).getRuns()[0];
  }

  private int getNumberOfRuns(long jobId) throws IOException, DatabricksRestException {
    RunsDTO runsDTO = service.listRuns(jobId, null, null, null);
    return runsDTO.getRuns() != null ? runsDTO.getRuns().length : 0;
  }
}
