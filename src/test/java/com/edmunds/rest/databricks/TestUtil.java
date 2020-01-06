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

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import com.edmunds.rest.databricks.DTO.clusters.AwsAttributesDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterInfoDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterStateDTO;
import com.edmunds.rest.databricks.DTO.EbsVolumeTypeDTO;
import com.edmunds.rest.databricks.DTO.libraries.LibraryDTO;
import com.edmunds.rest.databricks.DTO.libraries.LibraryFullStatusDTO;
import com.edmunds.rest.databricks.DTO.libraries.LibraryInstallStatusDTO;
import com.edmunds.rest.databricks.DTO.jobs.RunDTO;
import com.edmunds.rest.databricks.DTO.jobs.RunLifeCycleStateDTO;
import com.edmunds.rest.databricks.request.CreateClusterRequest;
import com.edmunds.rest.databricks.service.ClusterService;
import com.edmunds.rest.databricks.service.JobService;
import com.edmunds.rest.databricks.service.LibraryService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

public final class TestUtil {
  private TestUtil() {
  }
  private final static Logger logger = Logger.getLogger(TestUtil.class.getName());
  private static final String SMALL_NODE_TYPE = "m4.large";
  private static final String MEDIUM_NODE_TYPE = "m4.xlarge";
  private static final String SPARK_VERSION = "4.0.x-scala2.11";
  private static final String CLUSTER_NAME_PREFIX = "clusterServiceTest_";

  public static List<String> getTestClusters(ClusterService clusterService)
      throws IOException, DatabricksRestException {
    ClusterInfoDTO[] clusters = clusterService.list();
    List<String> foundClusters = new ArrayList<>();
    logger.info("looking for cluster that starts with prefix: " + CLUSTER_NAME_PREFIX);
    for (ClusterInfoDTO cluster : clusters) {
      if (cluster.getClusterName().contains(CLUSTER_NAME_PREFIX)) {
        logger.info("found existing cluster! " + cluster.getClusterName());
        foundClusters.add(cluster.getClusterId());
      }
    }
    return foundClusters;
  }

  public static void cleanupTestClusters(ClusterService clusterService,
      List<String> testClustersToCleanup)
      throws IOException, DatabricksRestException {
    for (int i = 0; i < testClustersToCleanup.size(); i++) {
      logger.info("Terminating old test cluster: " + testClustersToCleanup.get(i));
      clusterService.delete(testClustersToCleanup.get(i));
    }
  }

  public static String getTestClusterId(ClusterService clusterService)
      throws IOException, DatabricksRestException, InterruptedException {
    List<String> testClusters = getTestClusters(clusterService);
    if (testClusters.size() > 1) {
      cleanupTestClusters(clusterService, testClusters.subList(1, testClusters.size()));
    }
    String clusterId;
    if (testClusters.isEmpty()) {
      logger.info("Test cluster did not exist. Creating.");
      clusterId = createCluster(clusterService);
    } else {
      clusterId = testClusters.get(0);
    }
    if (clusterService.isClusterRunning(clusterId)) {
      return clusterId;
    }
    try {
      logger.info("Trying to start cluster...");
      clusterService.start(clusterId);
    } catch (DatabricksRestException e) {
      e.printStackTrace();
    }
    await().atMost(10, MINUTES).pollInterval(30, SECONDS)
        .until(clusterStatusHasChangedTo(ClusterStateDTO.RUNNING, clusterId, clusterService));
    logger.info("Cluster started! Beginning tests");
    return clusterId;
  }

  public static String createCluster(ClusterService clusterService)
      throws IOException, DatabricksRestException {
    String uniqueId = UUID.randomUUID().toString();
    AwsAttributesDTO awsAttributesDTO = new AwsAttributesDTO();
    awsAttributesDTO.setEbsVolumeType(EbsVolumeTypeDTO.GENERAL_PURPOSE_SSD);
    awsAttributesDTO.setEbsVolumeCount(1);
    awsAttributesDTO.setEbsVolumeSize(100);

    CreateClusterRequest request = new CreateClusterRequest.CreateClusterRequestBuilder(1,
        CLUSTER_NAME_PREFIX
            + uniqueId, SPARK_VERSION, SMALL_NODE_TYPE)
        .withAwsAttributes(awsAttributesDTO)
        .withAutoterminationMinutes(10).build();
    return clusterService.create(request);
  }

  public static Callable<Boolean> clusterStatusHasChangedTo(final ClusterStateDTO status, final String clusterId,
                                                            final ClusterService service) {
    return new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return Objects.equals(getClusterStatus(clusterId, service), status);
      }
    };
  }

  public static ClusterStateDTO getClusterStatus(String clusterId, ClusterService service)
      throws IOException,
             DatabricksRestException {
    ClusterInfoDTO clusterInfo = service.getInfo(clusterId);

    return clusterInfo.getState();
  }

  public static Callable<Boolean> runLifeCycleStateHasChangedTo(final RunLifeCycleStateDTO state, final long run_id,
                                                                final JobService service) {
    return new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return Objects.equals(getRunState(run_id, service), state);
      }
    };
  }

  private static RunLifeCycleStateDTO getRunState(long run_id, final JobService service)
      throws IOException,
             DatabricksRestException {
    RunDTO run = service.getRun(run_id);
    return run.getState().getLifeCycleState();
  }

  public static Callable<Boolean> jarLibraryStatusHasChangedTo(final LibraryInstallStatusDTO status,
                                                               final String clusterId,
                                                               final LibraryDTO library,
                                                               final LibraryService service) {
    return new Callable<Boolean>() {
      public Boolean call() throws Exception {
        return Objects.equals(getLibraryStatus(clusterId, library, service), status);
      }
    };
  }

  private static LibraryInstallStatusDTO getLibraryStatus(final String clusterId,
                                                          final LibraryDTO library,
                                                          final LibraryService service)
      throws IOException,
             DatabricksRestException {
    LibraryFullStatusDTO[] libraryFullStatuses = service.clusterStatus(String.valueOf(clusterId)).getLibraryFullStatuses();
    for (LibraryFullStatusDTO libraryFullStatusDTO : libraryFullStatuses) {
      if (Objects.equals(libraryFullStatusDTO.getLibrary(), library)) {
        return libraryFullStatusDTO.getStatus();
      }
    }
    return LibraryInstallStatusDTO.PENDING;
  }
}
