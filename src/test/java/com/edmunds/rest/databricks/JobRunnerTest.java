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

import com.edmunds.rest.databricks.DTO.JobDTO;
import com.edmunds.rest.databricks.DTO.JobSettingsDTO;
import com.edmunds.rest.databricks.DTO.NotebookTaskDTO;
import com.edmunds.rest.databricks.DTO.RunDTO;
import com.edmunds.rest.databricks.DTO.RunLifeCycleStateDTO;
import com.edmunds.rest.databricks.DTO.RunResultStateDTO;
import com.edmunds.rest.databricks.DTO.RunsDTO;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import com.edmunds.rest.databricks.service.JobService;
import org.apache.commons.cli.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.edmunds.rest.databricks.fixtures.DatabricksFixtures.HOSTNAME;
import static com.edmunds.rest.databricks.fixtures.DatabricksFixtures.PASSWORD;
import static com.edmunds.rest.databricks.fixtures.DatabricksFixtures.USERNAME;
import static org.testng.Assert.assertEquals;

/**
 * Created by shong on 8/2/16.
 */
public class JobRunnerTest {
    private static final String JOB_NAME = "JobRunnerTest_test_job";
    private JobService service;
    private Long jobId = null;
    private String[] argsWithJobId;
    private String[] argsWithJobName;
    private String[] argsWithInvalidJobName;

    @BeforeClass
    public void setUpOnce() throws IOException, DatabricksRestException {
        DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();
        service = factory.getJobService();

        NotebookTaskDTO notebook_task = new NotebookTaskDTO();
        notebook_task.setNotebookPath("/Users/dwhrestapi@edmunds.com/test_notebook");
        String defaultClusterId = TestUtil.getDefaultClusterId(factory.getClusterService());
        JobSettingsDTO jobSettingsDTO = new JobSettingsDTO();
        jobSettingsDTO.setName(JOB_NAME);
        jobSettingsDTO.setExistingClusterId(defaultClusterId);
        jobSettingsDTO.setNotebookTask(notebook_task);

        // there's possibility test TearDownFailure. it cause test job not-deleted.
        List<JobDTO> jobList = service.getJobsByName(JOB_NAME);
        for(JobDTO jobDTO:jobList) {
            service.deleteJob(jobDTO.getJobId());
        }

        jobId = service.createJob(jobSettingsDTO);

        argsWithJobId = new String[]{
            "-u " + USERNAME,
            "-p " + PASSWORD,
            "-h " + HOSTNAME,
            "-j " + jobId
        };

        argsWithJobName = new String[] {
            "-u " + USERNAME,
            "-p " + PASSWORD,
            "-h " + HOSTNAME,
            "-n " + JOB_NAME
        };

        argsWithInvalidJobName =
            new String[] {
                "-u " + USERNAME,
                "-p " + PASSWORD,
                "-h " + HOSTNAME,
                "-n " + "Fake Job Name"
        };

    }


    @AfterClass(alwaysRun = true)
    public void tearDownOnce() throws IOException, DatabricksRestException {
        service.deleteJob(jobId);
    }

    @Test
    public void main_whenCalledWithArguments_startsRunOfJob() throws InterruptedException, DatabricksRestException, IOException, ParseException {
        int expected = getNumberOfRuns(jobId) + 1;

        JobRunner.main(argsWithJobId);

        int numberOfRuns = getNumberOfRuns(jobId);
        assertEquals(numberOfRuns, expected);
    }

    @Test
    public void main_whenCalledWithJobName_startsRunOfJob() throws InterruptedException, DatabricksRestException, ParseException, IOException {
        int expected = getNumberOfRuns(jobId) + 1;

        JobRunner.main(argsWithJobName);

        int numberOfRuns = getNumberOfRuns(jobId);
        assertEquals(numberOfRuns, expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void main_whenCalledWithInvalidJobName_throwsIllegalArgumentException() throws InterruptedException, DatabricksRestException, ParseException, IOException {
        JobRunner.main(argsWithInvalidJobName);
    }

    @Test
    public void main_whenCalled_runsToCompletionWithSuccess() throws InterruptedException, DatabricksRestException, ParseException, IOException {
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
