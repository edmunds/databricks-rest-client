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

import com.edmunds.rest.databricks.DTO.ClusterStateDTO;
import com.edmunds.rest.databricks.DTO.JobSettingsDTO;
import com.edmunds.rest.databricks.DTO.LibraryDTO;
import com.edmunds.rest.databricks.DTO.LibraryInstallStatusDTO;
import com.edmunds.rest.databricks.DTO.NewClusterDTO;
import com.edmunds.rest.databricks.DTO.RunDTO;
import com.edmunds.rest.databricks.DTO.RunLifeCycleStateDTO;
import com.edmunds.rest.databricks.DTO.RunNowDTO;
import com.edmunds.rest.databricks.DTO.RunResultStateDTO;
import com.edmunds.rest.databricks.DTO.SparkSubmitTaskDTO;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import com.edmunds.rest.databricks.request.CreateClusterRequest;
import com.edmunds.rest.databricks.service.ClusterService;
import com.edmunds.rest.databricks.service.DbfsService;
import com.edmunds.rest.databricks.service.JobService;
import com.edmunds.rest.databricks.service.LibraryService;
import com.edmunds.rest.databricks.service.WorkspaceService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.edmunds.rest.databricks.TestUtil.clusterStatusHasChangedTo;
import static com.edmunds.rest.databricks.TestUtil.jarLibraryStatusHasChangedTo;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertEquals;

/**
 *
 */
public class DatabricksRestClientIT {

  private ClusterService clusterService;
  private DbfsService dbfsService;
  private JobService jobService;
  private LibraryService libraryService;
  private WorkspaceService workspaceService;

  private long jobId;
  private String dbfsTmpDir = "dbfs:/restApiTestDir/";
  private String clusterId;

  private String dbfsLocation = dbfsTmpDir + "test-1.0.0.jar";
  private String resourcesLocation = "./src/test/resources/testlib/test-1.0.0.jar";
  private String sparkVersion = "4.0.x-scala2.11";
  private String nodeId = "r3.xlarge";
  private int numWorkers = 1;

  @BeforeClass
  public void setUpOnce() {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();
    clusterService = factory.getClusterService();
    dbfsService = factory.getDbfsService();
    jobService = factory.getJobService();
    libraryService = factory.getLibraryService();
    workspaceService = factory.getWorkspaceService();
  }

  @AfterClass(alwaysRun = true)
  public void tearDownOnce() throws IOException, DatabricksRestException {
    dbfsService.rm(dbfsTmpDir, true);
    if (jobId != 0L) {
      jobService.deleteJob(jobId);
    }
    if (clusterId != null) {
      clusterService.delete(clusterId);
    }
  }


  //TODO currently timing out
  @Ignore
  public void createAndRunJobOnNewCluster() throws DatabricksRestException, IOException {

    // Upload Jar
    String[] sparkSubmitParams = new String[] {"--class", "Test", dbfsLocation};
    InputStream is = new FileInputStream(resourcesLocation);
    dbfsService.write(dbfsLocation, is, true);

    // Create job specs
    SparkSubmitTaskDTO sparkSubmitTask = new SparkSubmitTaskDTO();
    sparkSubmitTask.setParameters(sparkSubmitParams);
    NewClusterDTO newClusterDTO = new NewClusterDTO();
    newClusterDTO.setNumWorkers(numWorkers);
    newClusterDTO.setSparkVersion(sparkVersion);
    newClusterDTO.setNodeTypeId(nodeId);

    JobSettingsDTO jobSettingsDTO = new JobSettingsDTO();
    jobSettingsDTO.setName("JobServiceTest_integrationTest_job");
    jobSettingsDTO.setNewCluster(newClusterDTO);
    jobSettingsDTO.setSparkSubmitTask(sparkSubmitTask);

    jobId = jobService.createJob(jobSettingsDTO);
    RunNowDTO run = jobService.runJobNow(jobId);

    await().atMost(10, MINUTES).until(TestUtil.runLifeCycleStateHasChangedTo(RunLifeCycleStateDTO.TERMINATED,
        run.getRunId(), jobService));

    RunDTO runInfo = jobService.getRun(run.getRunId());

    assertEquals(runInfo.getState().getResultState(), RunResultStateDTO.SUCCESS);
    assertEquals(runInfo.getClusterSpec().getNewCluster().getSparkVersion(), sparkVersion);
    assertEquals(runInfo.getTask().getSparkSubmitTask().getParameters(), sparkSubmitParams);
  }

  @Test
  public void uploadAndInstallJarOnNewCluster() throws IOException, DatabricksRestException {
    // Create cluster
    String uniqueId = UUID.randomUUID().toString();
    CreateClusterRequest request = new CreateClusterRequest.CreateClusterRequestBuilder(numWorkers,
        "clusterServiceIntegrationTest_" + uniqueId, sparkVersion, nodeId).build();
    clusterId = clusterService.create(request);

    await().atMost(10, MINUTES).until(clusterStatusHasChangedTo(ClusterStateDTO.RUNNING, clusterId, clusterService));

    // Upload library
    InputStream is = new FileInputStream(resourcesLocation);
    dbfsService.write(dbfsLocation, is, true);

    LibraryDTO library = new LibraryDTO();
    library.setJar(dbfsLocation);
    LibraryDTO[] libraries = new LibraryDTO[] {library};

    // Install
    libraryService.install(clusterId, libraries);

    // Make sure it is installed successfully
    await().atMost(5, MINUTES).until(jarLibraryStatusHasChangedTo(LibraryInstallStatusDTO.INSTALLED, clusterId,
        library, libraryService));

  }

}