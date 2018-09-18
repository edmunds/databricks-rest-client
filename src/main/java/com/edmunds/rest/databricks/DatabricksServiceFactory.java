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

import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.edmunds.rest.databricks.restclient.DatabricksRestClientImpl;
import com.edmunds.rest.databricks.restclient.DatabricksRestClientImpl425;
import com.edmunds.rest.databricks.service.ClusterService;
import com.edmunds.rest.databricks.service.ClusterServiceImpl;
import com.edmunds.rest.databricks.service.DbfsService;
import com.edmunds.rest.databricks.service.DbfsServiceImpl;
import com.edmunds.rest.databricks.service.JobService;
import com.edmunds.rest.databricks.service.JobServiceImpl;
import com.edmunds.rest.databricks.service.LibraryService;
import com.edmunds.rest.databricks.service.LibraryServiceImpl;
import com.edmunds.rest.databricks.service.WorkspaceService;
import com.edmunds.rest.databricks.service.WorkspaceServiceImpl;


/**
 * Factory class for all other specific Databricks Service Wrappers.
 */
public final class DatabricksServiceFactory {

  public static final int DEFAULT_HTTP_CLIENT_MAX_RETRY = 3;
  public static final long DEFAULT_HTTP_CLIENT_RETRY_INTERVAL = 10000L;

  private DatabricksRestClient client2dot0;
  private ClusterService clusterService;
  private LibraryService libraryService;
  private WorkspaceService workspaceService;
  private JobService jobService;
  private DbfsService dbfsService;

  public DatabricksServiceFactory(String username, String password, String host) {
    this(username, password, host, DEFAULT_HTTP_CLIENT_MAX_RETRY,
        DEFAULT_HTTP_CLIENT_RETRY_INTERVAL);
  }

  /**
   * Creating a Databricks Service object.
   *
   * @param maxRetry http client maxRetry when failed due to I/O , timeout error
   * @param retryInterval http client retry interval when failed due to I/O , timeout error
   */
  public DatabricksServiceFactory(String username, String password, String host, int maxRetry,
      long retryInterval) {
    this(username, password, host, maxRetry, retryInterval, false);
  }


  /**
   * When use databricks service on CDH 5.7.1 , useLegacyAPI425 set true for v4.2.5 compatible API.
   *
   * @param useLegacyAPI425 choose what version of API compatible HttpClient.
   */
  public DatabricksServiceFactory(String username, String password, String host, int maxRetry,
      long retryInterval, boolean useLegacyAPI425) {
    if (useLegacyAPI425) {
      client2dot0 = DatabricksRestClientImpl425
          .createClientWithUserPassword(username, password, host, "2.0", maxRetry,
              retryInterval);
    } else {
      client2dot0 = DatabricksRestClientImpl
          .createClientWithUserPassword(username, password, host, "2.0", maxRetry,
              retryInterval);
    }
  }

  /**
   * Create a databricks service factory using personal token authentication instead.
   *
   * @param personalToken your personal token
   * @param host the databricks host
   * @param maxRetry the maximum number of retries
   * @param retryInterval the retry interval between each attempt
   */
  public DatabricksServiceFactory(String personalToken, String host,
      int maxRetry, long retryInterval) {
    client2dot0 = DatabricksRestClientImpl
        .createClientWithTokenAuthentication(personalToken, host, "2.0", maxRetry, retryInterval);
  }

  /**
   * Will return a Databricks Cluster Service singleton.
   */
  public ClusterService getClusterService() {
    if (clusterService == null) {
      clusterService = new ClusterServiceImpl(client2dot0);
    }
    return clusterService;
  }

  /**
   * Will return a Databricks Library Service singleton.
   */
  public LibraryService getLibraryService() {
    if (libraryService == null) {
      libraryService = new LibraryServiceImpl(client2dot0);
    }
    return libraryService;
  }

  /**
   * Will return a JobService singleton.
   */
  public JobService getJobService() {
    if (jobService == null) {
      jobService = new JobServiceImpl(client2dot0);
    }
    return jobService;
  }

  /**
   * Will return a workspace singleton.
   */
  public WorkspaceService getWorkspaceService() {
    if (workspaceService == null) {
      workspaceService = new WorkspaceServiceImpl(client2dot0);
    }
    return workspaceService;
  }

  /**
   * Will return a DBFS (databricks filesystem) singleton.
   */
  public DbfsService getDbfsService() {
    if (dbfsService == null) {
      dbfsService = new DbfsServiceImpl(client2dot0);
    }
    return dbfsService;
  }
}
