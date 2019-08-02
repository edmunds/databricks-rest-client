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

package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.ExportFormatDTO;
import com.edmunds.rest.databricks.DTO.JobDTO;
import com.edmunds.rest.databricks.DTO.JobSettingsDTO;
import com.edmunds.rest.databricks.DTO.LanguageDTO;
import com.edmunds.rest.databricks.DTO.NotebookTaskDTO;
import com.edmunds.rest.databricks.DTO.RunDTO;
import com.edmunds.rest.databricks.DTO.RunNowDTO;
import com.edmunds.rest.databricks.DTO.RunsDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.TestUtil;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest.ImportWorkspaceRequestBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class JobTest {
  private static final String JOB_NAME = "JobRunnerTest_test_job";
  private static final String NOTEBOOK_PATH = "/tmp/testing/test_notebook.scala";
  private JobService service;
  private WorkspaceService workspaceService;
  private RunDTO runDTO;
  private RunNowDTO runNowDTO;
  private String[] argsWithJobId;
  private String[] argsWithJobName;
  private String[] argsWithInvalidJobName;

  @BeforeClass(enabled = true)
  public void setUpOnce() throws IOException, DatabricksRestException {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();
    service = factory.getJobService();
    workspaceService = factory.getWorkspaceService();

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
    String defaultClusterId = TestUtil.getDefaultClusterId(factory.getClusterService());
    JobSettingsDTO jobSettingsDTO = new JobSettingsDTO();
    jobSettingsDTO.setName(JOB_NAME);
    jobSettingsDTO.setExistingClusterId(defaultClusterId);
    jobSettingsDTO.setNotebookTask(notebook_task);

    // there's possibility test TearDownFailure. it cause test job not-deleted.
    List<JobDTO> jobList = service.getJobsByName(JOB_NAME);
    for (JobDTO jobDTO : jobList) {
      service.deleteJob(jobDTO.getJobId());
    }

    runNowDTO = service.runSubmit(jobSettingsDTO);
    runDTO = service.getRun(runNowDTO.getRunId());

  }

  @AfterClass(alwaysRun = true)
  public void tearDownOnce() throws IOException, DatabricksRestException {
    service.deleteJob(runDTO.getJobId());

    workspaceService.delete(NOTEBOOK_PATH, false);
  }

  @Test
  public void main_runExists()
      throws InterruptedException, DatabricksRestException, IOException, ParseException {
    RunsDTO runsDTO = service.listRuns(runDTO.getJobId(), null, null, null);
    assertEquals(runsDTO.getRuns().length, 1);
  }

}
