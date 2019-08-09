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
import com.edmunds.rest.databricks.service.GroupsService;
import com.edmunds.rest.databricks.service.GroupsServiceImpl;
import com.edmunds.rest.databricks.service.InstanceProfilesService;
import com.edmunds.rest.databricks.service.InstanceProfilesServiceImpl;
import com.edmunds.rest.databricks.service.JobService;
import com.edmunds.rest.databricks.service.JobServiceImpl;
import com.edmunds.rest.databricks.service.LibraryService;
import com.edmunds.rest.databricks.service.LibraryServiceImpl;
import com.edmunds.rest.databricks.service.WorkspaceService;
import com.edmunds.rest.databricks.service.WorkspaceServiceImpl;


/**
 * This is the class that clients should interact with.
 * It provides singletons for all of the Services, as well as
 * abstracting the construction of the databricks rest client.
 */
public class DatabricksServiceFactory {

  /**
   * Databricks rest http client socket parameters default values.
   * Unit is milliseconds
   */
  public static final int SOCKET_TIMEOUT = 10000;
  public static final int CONNECTION_TIMEOUT = 10000;
  public static final int CONNECTION_REQUEST_TIMEOUT = 10000;

  /**
   * Databricks rest http client {@link com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy} default values.
   */
  public static final int DEFAULT_HTTP_CLIENT_MAX_RETRY = 3;
  public static final long DEFAULT_HTTP_CLIENT_RETRY_INTERVAL = 10000L;


  private DatabricksRestClient client2dot0;
  private ClusterService clusterService;
  private LibraryService libraryService;
  private WorkspaceService workspaceService;
  private JobService jobService;
  private DbfsService dbfsService;
  private GroupsService groupsService;
  private InstanceProfilesService instanceProfilesService;

  public DatabricksServiceFactory(DatabricksRestClient databricksRestClient) {
    this.client2dot0 = databricksRestClient;
  }

  /**
   * Old constructor.
   *
   * @deprecated in version 2.0.2, please use {@link Builder#createUserPasswordAuthentication(String, String, String)}
   */
  @Deprecated
  public DatabricksServiceFactory(String username, String password, String host) {
    this(username, password, host, DEFAULT_HTTP_CLIENT_MAX_RETRY,
        DEFAULT_HTTP_CLIENT_RETRY_INTERVAL);
  }

  /**
   * Creating a Databricks Service object.
   *
   * @param maxRetry      http client maxRetry when failed due to I/O , timeout error
   * @param retryInterval http client retry interval when failed due to I/O , timeout error
   * @deprecated in version 2.0.2, please use {@link Builder#createUserPasswordAuthentication(String, String, String)}
   */
  @Deprecated
  public DatabricksServiceFactory(String username, String password, String host, int maxRetry,
                                  long retryInterval) {
    this(username, password, host, maxRetry, retryInterval, false);
  }


  /**
   * When use databricks service on CDH 5.7.1 , useLegacyAPI425 set true for v4.2.5 compatible API.
   *
   * @param useLegacyAPI425 choose what version of API compatible HttpClient.
   * @deprecated in version 2.0.2, please use {@link Builder#createUserPasswordAuthentication(String, String, String)}
   */
  @Deprecated
  public DatabricksServiceFactory(String username, String password, String host, int maxRetry,
                                  long retryInterval, boolean useLegacyAPI425) {

    client2dot0 = Builder.createUserPasswordAuthentication(username, password, host)
        .withMaxRetries(maxRetry)
        .withRetryInterval(retryInterval)
        .withUseLegacyAPI425(useLegacyAPI425)
        .build().client2dot0;
  }

  /**
   * Create a databricks service factory using personal token authentication instead.
   *
   * @param personalToken your personal token
   * @param host          the databricks host
   * @param maxRetry      the maximum number of retries
   * @param retryInterval the retry interval between each attempt
   * @deprecated in version 2.0.2, please use {@link Builder#createTokenAuthentication(String, String)}
   */
  @Deprecated
  public DatabricksServiceFactory(String personalToken, String host,
                                  int maxRetry, long retryInterval) {

    client2dot0 = Builder.createTokenAuthentication(personalToken, host)
        .withMaxRetries(maxRetry)
        .withRetryInterval(retryInterval)
        .build().client2dot0;
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

  /**
   * Will return a Groups singleton.
   */
  public GroupsService getGroupsService() {
    if (groupsService == null) {
      groupsService = new GroupsServiceImpl(client2dot0);
    }
    return groupsService;
  }

  /**
   * Will return an Instance Profiles singleton.
   */
  public InstanceProfilesService getInstanceProfilesService() {
    if (instanceProfilesService == null) {
      instanceProfilesService = new InstanceProfilesServiceImpl(client2dot0);
    }
    return instanceProfilesService;
  }

  /**
   * This is how the DatabricksServiceFactory should be constructed. This gives flexibility to add
   * more parameters later without ending up with large constructors.
   */
  public static class Builder {

    String host;
    String token;
    String username;
    String password;

    /**
     * Databricks rest-api version.
     */
    String apiVersion = "2.0";

    /**
     * Databricks rest http client {@link com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy} default.
     */
    long retryInterval = DEFAULT_HTTP_CLIENT_RETRY_INTERVAL;
    int maxRetries = DEFAULT_HTTP_CLIENT_MAX_RETRY;

    /**
     * Databricks rest http client socket parameters.
     */
    int soTimeout = SOCKET_TIMEOUT;
    int connectionTimeout = CONNECTION_TIMEOUT;
    int connectionRequestTimeout = CONNECTION_REQUEST_TIMEOUT;

    /**
     * From the docs in DefaultHttpRequestRetryHandler:
     * Whether or not methods that have successfully sent their request will be retried.
     */
    boolean requestSentRetryEnabled = false;

    /**
     * set true if you want to use http-client v4.2.5 compatible API.
     * This could be needed in some runtime environment which provide legacy http-client library as platform runtime.
     */
    public boolean useLegacyAPI425 = false;


    private Builder() {
      //NO-OP
    }


    public String getHost() {
      return host;
    }

    public String getToken() {
      return token;
    }

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }

    public String getApiVersion() {
      return apiVersion;
    }

    public long getRetryInterval() {
      return retryInterval;
    }

    public int getMaxRetries() {
      return maxRetries;
    }

    public int getSoTimeout() {
      return soTimeout;
    }

    public int getConnectionTimeout() {
      return connectionTimeout;
    }

    public int getConnectionRequestTimeout() {
      return connectionRequestTimeout;
    }

    public boolean isRequestSentRetryEnabled() {
      return requestSentRetryEnabled;
    }

    public boolean isUseLegacyAPI425() {
      return useLegacyAPI425;
    }

    /**
     * Creates a DatabricksServiceFactory using token authentication.
     *
     * @param token your databricks token
     * @param host  the databricks host where that token is valid
     * @return the builder object
     */
    public static Builder createTokenAuthentication(String token, String host) {
      Builder builder = new Builder();
      builder.token = token;
      builder.host = host;
      return builder;
    }

    /**
     * Creates a DatabrickServiceFactory using username password authentication.
     *
     * @param username databricks username
     * @param password databricks password
     * @param host     the host object
     * @return the builder object
     */
    public static Builder createUserPasswordAuthentication(String username,
                                                           String password, String host) {
      Builder builder = new Builder();
      builder.username = username;
      builder.password = password;
      builder.host = host;
      return builder;
    }


    public Builder withApiVersion(String apiVersion) {
      this.apiVersion = apiVersion;
      return this;
    }

    public Builder withMaxRetries(int maxRetries) {
      this.maxRetries = maxRetries;
      return this;
    }

    /**
     * set Http Retry Interval.
     *
     * @param retryInterval unit is milliseconds
     * @return
     */
    public Builder withRetryInterval(long retryInterval) {
      this.retryInterval = retryInterval;
      return this;
    }

    /**
     * set Http-Client SoTimeout.
     *
     * @param soTimeout unit is milliseconds
     * @return
     */
    public Builder withSoTimeout(int soTimeout) {
      this.soTimeout = soTimeout;
      return this;
    }

    /**
     * set Http-Client connection timeout.
     *
     * @param connectionTimeout unit is milliseconds
     * @return
     */
    public Builder withConnectionTimeout(int connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      return this;
    }

    /**
     * set Http-Client connection request timeout.
     *
     * @param connectionRequestTimeout unit is milliseconds
     * @return
     */
    public Builder withConnectionRequestTimeout(int connectionRequestTimeout) {
      this.connectionRequestTimeout = connectionRequestTimeout;
      return this;
    }

    public Builder withUseLegacyAPI425(boolean useLegacyAPI425) {
      this.useLegacyAPI425 = useLegacyAPI425;
      return this;
    }

    public Builder withRequestSentRetryEnabled(boolean requestSentRetryEnabled) {
      this.requestSentRetryEnabled = requestSentRetryEnabled;
      return this;
    }

    /**
     * Builds a DatabricksServiceFactory.
     *
     * @return the databricks service factory object
     */
    public DatabricksServiceFactory build() {

      DatabricksRestClient restClient = null;
      if (useLegacyAPI425) {
        restClient = new DatabricksRestClientImpl425(this);

      } else {
        restClient = new DatabricksRestClientImpl(this);
      }

      return new DatabricksServiceFactory(restClient);

    }

  }
}
