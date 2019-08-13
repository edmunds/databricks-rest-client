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

import static com.edmunds.rest.databricks.TestUtil.clusterStatusHasChangedTo;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.edmunds.rest.databricks.DTO.AutoScaleDTO;
import com.edmunds.rest.databricks.DTO.AwsAttributesDTO;
import com.edmunds.rest.databricks.DTO.ClusterEventDTO;
import com.edmunds.rest.databricks.DTO.ClusterEventTypeDTO;
import com.edmunds.rest.databricks.DTO.ClusterInfoDTO;
import com.edmunds.rest.databricks.DTO.ClusterStateDTO;
import com.edmunds.rest.databricks.DTO.EbsVolumeTypeDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.fixtures.ClusterDependentTest;
import com.edmunds.rest.databricks.request.CreateClusterRequest;
import com.edmunds.rest.databricks.request.EditClusterRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.testng.annotations.Test;

public class ClusterServiceTest extends ClusterDependentTest {
  private static final String SMALL_NODE_TYPE = "m4.large";
  private static final String MEDIUM_NODE_TYPE = "m4.xlarge";
  private static final String SPARK_VERSION = "4.0.x-scala2.11";
  private static final String CLUSTER_NAME_PREFIX = "clusterServiceTest_";

  private AwsAttributesDTO getAwsAttributesDTO() {
    AwsAttributesDTO awsAttributesDTO = new AwsAttributesDTO();
    awsAttributesDTO.setEbsVolumeType(EbsVolumeTypeDTO.GENERAL_PURPOSE_SSD);
    awsAttributesDTO.setEbsVolumeCount(1);
    awsAttributesDTO.setEbsVolumeSize(100);
    return awsAttributesDTO;
  }

  @Test
  public void testSetUpOnce() {
    assertNotNull(clusterId);
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void createAndDeleteCluster() throws IOException, DatabricksRestException {
    String uniqueId = UUID.randomUUID().toString();
    CreateClusterRequest newRequest = new CreateClusterRequest.CreateClusterRequestBuilder(1, CLUSTER_NAME_PREFIX
        + uniqueId, SPARK_VERSION, SMALL_NODE_TYPE)
        .withAutoterminationMinutes(10)
        .withAwsAttributes(getAwsAttributesDTO()).build();
    String createdClusterId = service.create(newRequest);
    assertNotNull(createdClusterId);

    service.delete(createdClusterId);
    await().pollInterval(10, SECONDS)
        .atMost(10, MINUTES).until(clusterStatusHasChangedTo(ClusterStateDTO.TERMINATED,
        createdClusterId, service));
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void listAllClusters_whenCalled_returnsListGreaterThanZero()
      throws IOException, DatabricksRestException {
    ClusterInfoDTO[] result = service.list();
    assertTrue(result.length >= 1);
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void showClusterStatus_whenCalled_returnsClusterStatuses()
      throws IOException, DatabricksRestException {
    ClusterInfoDTO result = service.getInfo(clusterId);

    assertEquals(result.getClusterId(), clusterId);
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void restartCluster_whenCalled_statusChangesToRestarting()
      throws IOException, DatabricksRestException {
    service.restart(clusterId);

    await()
        .pollInterval(10, SECONDS)
        .atMost(10, MINUTES).until(clusterStatusHasChangedTo(ClusterStateDTO.RESTARTING, clusterId, service));
  }

  @Test(dependsOnMethods = {"testSetUpOnce", "showClusterStatus_whenCalled_returnsClusterStatuses"})
  public void resizeCluster_whenCalledWithAutoscale_numWorkersChangestoAutoscale()
      throws IOException,
             DatabricksRestException {
    AutoScaleDTO autoScaleDTO = new AutoScaleDTO();
    autoScaleDTO.setMaxWorkers(2);
    autoScaleDTO.setMinWorkers(1);
    service.resize(autoScaleDTO, clusterId);

    ClusterInfoDTO info = service.getInfo(clusterId);
    assertEquals(info.getAutoscale().getMaxWorkers(), autoScaleDTO.getMaxWorkers());
    assertEquals(info.getAutoscale().getMinWorkers(), autoScaleDTO.getMinWorkers());
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void stopAndStartCluster() throws IOException, DatabricksRestException {
    service.delete(clusterId);
    await()
        .pollInterval(10, SECONDS)
        .atMost(10, MINUTES).until(clusterStatusHasChangedTo(ClusterStateDTO.TERMINATED, clusterId, service));

    service.start(clusterId);
    await()
        .pollInterval(10, SECONDS)
        .atMost(10, MINUTES).until(clusterStatusHasChangedTo(ClusterStateDTO.RUNNING, clusterId, service));
  }

  @Test(dependsOnMethods = {"testSetUpOnce",
      "restartCluster_whenCalled_statusChangesToRestarting",
      "showClusterStatus_whenCalled_returnsClusterStatuses"})
  public void edit_whenCalled_changesNodeType() throws IOException, DatabricksRestException {
    String name = service.getInfo(clusterId).getClusterName();
    EditClusterRequest editRequest = new EditClusterRequest.EditClusterRequestBuilder(1, clusterId, name,
        SPARK_VERSION, MEDIUM_NODE_TYPE).withAwsAttributes(getAwsAttributesDTO()).build();
    service.edit(editRequest);
    await()
        .pollInterval(10, SECONDS)
        .atMost(10, MINUTES).until(clusterStatusHasChangedTo(ClusterStateDTO.RUNNING, clusterId, service));
    service.restart(clusterId);
    await()
        .pollInterval(10, SECONDS)
        .atMost(10, MINUTES).until(clusterStatusHasChangedTo(ClusterStateDTO.RUNNING, clusterId, service));

    ClusterInfoDTO clusterInfo = service.getInfo(clusterId);
    assertEquals(clusterInfo.getNodeTypeId(), MEDIUM_NODE_TYPE);
    assertEquals(clusterInfo.getNumWorkers(), 1);
  }

  @Test
  public void listEvents_whenCalled_showsEvents() throws IOException, DatabricksRestException {
    ClusterInfoDTO[] clusterInfoDTO = service.list();
    String clusterId = clusterInfoDTO[0].getClusterId();
    List<ClusterEventDTO> events = service.listEvents(clusterId, new ClusterEventTypeDTO[0], 0, 50);
    assertTrue(events.size() > 0);
  }
}