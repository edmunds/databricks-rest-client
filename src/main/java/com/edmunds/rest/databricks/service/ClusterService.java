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

import com.edmunds.rest.databricks.DTO.UpsertClusterDTO;
import com.edmunds.rest.databricks.DTO.clusters.AutoScaleDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterEventDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterEventTypeDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterInfoDTO;
import com.edmunds.rest.databricks.DTO.clusters.ListOrderDTO;
import com.edmunds.rest.databricks.DTO.clusters.NodeTypeDTO;
import com.edmunds.rest.databricks.DTO.clusters.SparkVersionDTO;
import com.edmunds.rest.databricks.DTO.jobs.NewClusterDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.request.CreateClusterRequest;
import com.edmunds.rest.databricks.request.EditClusterRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A Wrapper around the cluster part of the databricks rest api.
 *
 * @see <a href="https://docs.databricks.com/api/latest/clusters.html">https://docs.databricks.com/api/latest/clusters.html</a>
 */
public interface ClusterService {

  /**
   * Creates a databricks cluster.
   * @deprecated in version 2.3.2, please use {@link ClusterService#create(NewClusterDTO)}
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#create">https://docs.databricks.com/api/latest/clusters.html#create</a>
   * @param createClusterRequest the cluster request object
   * @return the clusterId
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  @Deprecated
  String create(CreateClusterRequest createClusterRequest) throws IOException, DatabricksRestException;

  /**
   * Creates a databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#create">https://docs.databricks.com/api/latest/clusters.html#create</a>
   * @param clusterDTO cluster DTO
   * @return the clusterId
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  String create(NewClusterDTO clusterDTO) throws IOException, DatabricksRestException;

  /**
   * Edits the configurations of a databricks cluster.
   * @deprecated in version 2.3.2, please use {@link ClusterService#edit(UpsertClusterDTO)}
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#edit">https://docs.databricks.com/api/latest/clusters.html#edit</a>
   * @param editClusterRequest the edit cluster request object
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  @Deprecated
  void edit(EditClusterRequest editClusterRequest) throws IOException, DatabricksRestException;

  /**
   * Edits the configurations of a databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#edit">https://docs.databricks.com/api/latest/clusters.html#edit</a>
   * @param data cluster configuration
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void edit(Map<String, Object> data) throws IOException, DatabricksRestException;

  /**
   * Edits the configurations of a databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#edit">https://docs.databricks.com/api/latest/clusters.html#edit</a>
   * @param clusterDTO cluster DTO
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void edit(UpsertClusterDTO clusterDTO) throws IOException, DatabricksRestException;

  /**
   * Starts a databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#start">https://docs.databricks.com/api/latest/clusters.html#start</a>
   * @param clusterId the clusterId of the cluster you want to start.
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void start(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Returns true if the cluster is running.
   * @param clusterId the clusterId to check.
   * @return true if the cluster is running.
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  boolean isClusterRunning(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Restarts a given databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#restart">https://docs.databricks.com/api/latest/clusters.html#restart</a>
   * @param clusterId the clusterid of the cluster you want to restart.
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void restart(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Resizes a cluster. Will give it a fixed number of workers.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#resize">https://docs.databricks.com/api/latest/clusters.html#resize</a>
   * @param numWorkers the number of workers you want to resize to
   * @param clusterId the clusterId you want to operate on
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with request
   */
  void resize(int numWorkers, String clusterId) throws IOException, DatabricksRestException;

  /**
   * Resizes a cluster. Will add/edit autoscale functionality.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#resize">https://docs.databricks.com/api/latest/clusters.html#resize</a>
   * @param autoscale the autoscale specs.
   * @param clusterId the clusterId you want to work on
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with request
   */
  void resize(AutoScaleDTO autoscale, String clusterId) throws IOException, DatabricksRestException;

  /**
   * Terminates a cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#delete-terminate">https://docs.databricks.com/api/latest/clusters.html#delete-terminate</a>
   * @param clusterId the cluster you want to terminate
   * @throws IOException any other errors
   * @throws DatabricksRestException errors with request
   */
  void delete(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Permanently terminates a cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#permanent-delete">https://docs.databricks.com/api/latest/clusters.html#permanent-delete</a>
   * @param clusterId the cluster you want to permanently terminate
   * @throws IOException any other errors
   * @throws DatabricksRestException errors with request
   */
  void permanentDelete(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Gets information about a given cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#get">https://docs.databricks.com/api/latest/clusters.html#get</a>
   * @param clusterId the cluster you want to get info about
   * @return the information DTO object
   * @throws IOException any other errors.
   * @throws DatabricksRestException any errors with the request
   */
  ClusterInfoDTO getInfo(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Lists all of the clusters on a given databricks instance.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#list">https://docs.databricks.com/api/latest/clusters.html#list</a>
   * @return an array of cluster information objects
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  ClusterInfoDTO[] list() throws IOException, DatabricksRestException;

  /**
   * Lists events of a specific cluster on a given databricks instance.
   * Allows you to filter which events you want to see too.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#events">https://docs.databricks.com/api/latest/clusters.html#events</a>
   * @return an array of cluster events objects
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  List<ClusterEventDTO> listEvents(String clusterId, ClusterEventTypeDTO[] eventsToFilter,
                                   Integer offset, Integer limit,
                                   ListOrderDTO order,
                                   Long startTime, Long endTime)
      throws IOException, DatabricksRestException;

  /**
   * Given a cluster settings DTO object it will:
   * - create the cluster if it doesn't exist
   * - edit the cluster if it does exist.
   * Uses a combination of
   * If cluster doesn't exist:
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#create">https://docs.databricks.com/api/latest/clusters.html#create</a>
   * If cluster exists:
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#edit">https://docs.databricks.com/api/latest/clusters.html#edit</a>
   * @param clusterDTO cluster DTO
   */
  void upsertCluster(String clusterId, NewClusterDTO clusterDTO) throws IOException, DatabricksRestException;

  /**
   * Look for clusters with a given name.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#findByName">https://docs.databricks.com/api/latest/clusters.html#findByName</a>
   * @param clusterName name to look for
   * @return a list of cluster information objects
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  List<ClusterInfoDTO> findByName(String clusterName) throws IOException, DatabricksRestException;

  /**
   * Same as findByName, but it will throw an exception if more then one cluster has the name.
   *
   * @param clusterName name to look for
   * @return a cluster if only one was found
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request,
   *            including if more then 1 cluster was found with the name.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#findByName">https://docs.databricks.com/api/latest/clusters.html#findByName</a>
   */
  Optional<ClusterInfoDTO> findUniqueByName(String clusterName)
      throws IOException, DatabricksRestException;

  /**
   * Pin a cluster with a given id. Needs administrator rights.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#pin">https://docs.databricks.com/api/latest/clusters.html#pin</a>
   * @param clusterId the clusterId you want to pin
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void pin(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Unpin a cluster with a given id. Needs administrator rights.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#unpin">https://docs.databricks.com/api/latest/clusters.html#unpin</a>
   * @param clusterId the clusterId you want to unpin
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void unpin(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Return a list of supported Spark node types.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#list-node-types">https://docs.databricks.com/api/latest/clusters.html#list-node-types</a>
   * @return an array of node types
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  List<NodeTypeDTO> listNodeTypes() throws IOException, DatabricksRestException;

  /**
   * Return a list of availability zones where clusters can be created in. This method only works on AWS.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#list-zones">https://docs.databricks.com/api/latest/clusters.html#list-zones</a>
   * @return the a list of availability zones
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  List<String> listZones() throws IOException, DatabricksRestException;

  /**
   * Return the list of available runtime versions.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#spark-versions">https://docs.databricks.com/api/latest/clusters.html#spark-versions</a>
   * @return the a list of runtime versions
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  List<SparkVersionDTO> listSparkVersions() throws IOException, DatabricksRestException;


}
