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
import com.edmunds.rest.databricks.restclient.DefaultHttpClientBuilderFactory;
import com.edmunds.rest.databricks.restclient.HttpClientBuilderFactory;
import com.edmunds.rest.databricks.service.AccountService;
import com.edmunds.rest.databricks.service.AccountServiceImpl;
import com.edmunds.rest.databricks.service.ClusterPoliciesService;
import com.edmunds.rest.databricks.service.ClusterPoliciesServiceImpl;
import com.edmunds.rest.databricks.service.ClusterService;
import com.edmunds.rest.databricks.service.ClusterServiceImpl;
import com.edmunds.rest.databricks.service.DLTService;
import com.edmunds.rest.databricks.service.DLTServiceImpl;
import com.edmunds.rest.databricks.service.DbfsService;
import com.edmunds.rest.databricks.service.DbfsServiceImpl;
import com.edmunds.rest.databricks.service.GlobalInitScriptService;
import com.edmunds.rest.databricks.service.GlobalInitScriptServiceImpl;
import com.edmunds.rest.databricks.service.GroupsService;
import com.edmunds.rest.databricks.service.GroupsServiceImpl;
import com.edmunds.rest.databricks.service.InstanceProfilesService;
import com.edmunds.rest.databricks.service.InstanceProfilesServiceImpl;
import com.edmunds.rest.databricks.service.JobService;
import com.edmunds.rest.databricks.service.JobServiceImpl;
import com.edmunds.rest.databricks.service.LibraryService;
import com.edmunds.rest.databricks.service.LibraryServiceImpl;
import com.edmunds.rest.databricks.service.PoolService;
import com.edmunds.rest.databricks.service.PoolServiceImpl;
import com.edmunds.rest.databricks.service.ScimService;
import com.edmunds.rest.databricks.service.ScimServiceImpl;
import com.edmunds.rest.databricks.service.SecretsService;
import com.edmunds.rest.databricks.service.SecretsServiceImpl;
import com.edmunds.rest.databricks.service.SqlWarehouseService;
import com.edmunds.rest.databricks.service.SqlWarehouseServiceImpl;
import com.edmunds.rest.databricks.service.TokenManagementService;
import com.edmunds.rest.databricks.service.TokenManagementServiceImpl;
import com.edmunds.rest.databricks.service.TokenService;
import com.edmunds.rest.databricks.service.TokenServiceImpl;
import com.edmunds.rest.databricks.service.UnityCatalogService;
import com.edmunds.rest.databricks.service.UnityCatalogServiceImpl;
import com.edmunds.rest.databricks.service.UnityCatalogWorkspaceService;
import com.edmunds.rest.databricks.service.UnityCatalogWorkspaceServiceImpl;
import com.edmunds.rest.databricks.service.WorkspaceService;
import com.edmunds.rest.databricks.service.WorkspaceServiceImpl;

import java.util.Properties;


/**
 * This is the class that clients should interact with. It provides singletons for all of the Services, as well as
 * abstracting the construction of the databricks rest client.
 */
public class DatabricksServiceFactory {

  /**
   * Databricks rest http client socket parameters default values. Unit is milliseconds.
   */
  public static final int SOCKET_TIMEOUT = 10000;
  public static final int CONNECTION_TIMEOUT = 10000;
  public static final int CONNECTION_REQUEST_TIMEOUT = 10000;

  /**
   * Databricks rest http client {@link com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy} default
   * values.
   */
  public static final int DEFAULT_HTTP_CLIENT_MAX_RETRY = 3;
  public static final long DEFAULT_HTTP_CLIENT_RETRY_INTERVAL = 10000L;


  private DatabricksRestClient client2dot0;
  private DatabricksRestClient client2dot1;
  private ClusterService clusterService;

  private ClusterPoliciesService clusterPoliciesService;
  private LibraryService libraryService;
  private WorkspaceService workspaceService;
  private JobService jobService;
  private DLTService dltService;
  private PoolService poolService;
  private SqlWarehouseService sqlWarehouseService;
  private GlobalInitScriptService globalInitScriptService;
  private DbfsService dbfsService;
  private GroupsService groupsService;
  private ScimService scimService;
  private InstanceProfilesService instanceProfilesService;
  private SecretsService secretsService;
  private AccountService accountService;
  private TokenManagementService tokenManagementService;
  private TokenService tokenService;
  private UnityCatalogService unityCatalogService;

  private UnityCatalogWorkspaceService unityCatalogWorkspaceService;

  public DatabricksServiceFactory(DatabricksRestClient databricksRestClient,
                                  DatabricksRestClient databricksRestClient2dot1) {
    this.client2dot0 = databricksRestClient;
    this.client2dot1 = databricksRestClient2dot1;
  }

  public DatabricksServiceFactory(DatabricksRestClient databricksRestClient) {
    this.client2dot0 = databricksRestClient;
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
   * Will return a Databricks Cluster Policies Service singleton.
   */
  public ClusterPoliciesService getClusterPoliciesService() {
    if (clusterPoliciesService == null) {
      clusterPoliciesService = new ClusterPoliciesServiceImpl(client2dot0);
    }
    return clusterPoliciesService;
  }

  /**
   * Will return a Databricks LibraryDTO Service singleton.
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
      if (client2dot1 != null) {
        jobService = new JobServiceImpl(client2dot1);
      } else {
        jobService = new JobServiceImpl(client2dot0);
      }
    }
    return jobService;
  }

  /**
   * Will return a DLTService singleton.
   */
  public DLTService getDLTService() {
    if (dltService == null) {
      dltService = new DLTServiceImpl(client2dot0);
    }
    return dltService;
  }

  /**
   * Will return a PoolService singleton.
   */
  public PoolService getPoolService() {
    if (poolService == null) {
      poolService = new PoolServiceImpl(client2dot0);
    }
    return poolService;
  }

  /**
   * Will return a UnityCatalogWorkspaceServiceImpl singleton.
   */
  public UnityCatalogWorkspaceService getUnityCatalogWorkspaceService() {
    if (unityCatalogWorkspaceService == null) {
      unityCatalogWorkspaceService = new UnityCatalogWorkspaceServiceImpl(client2dot1);
    }
    return unityCatalogWorkspaceService;
  }

  /**
   * Will return a SqlWarehouseService singleton.
   */
  public SqlWarehouseService getSqlWarehouseService() {
    if (sqlWarehouseService == null) {
      sqlWarehouseService = new SqlWarehouseServiceImpl(client2dot0);
    }
    return sqlWarehouseService;
  }

  /**
   * Will return a GlobalInitScriptService singleton.
   */
  public GlobalInitScriptService getGlobalInitScriptService() {
    if (globalInitScriptService == null) {
      globalInitScriptService = new GlobalInitScriptServiceImpl(client2dot0);
    }
    return globalInitScriptService;
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
   * Will return a ScimService singleton.
   */
  public ScimService getScimService() {
    if (scimService == null) {
      scimService = new ScimServiceImpl(client2dot0);
    }
    return scimService;
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
   * Will return an SecretService singleton.
   */
  public SecretsService getSecretsService() {
    if (secretsService == null) {
      secretsService = new SecretsServiceImpl(client2dot0);
    }
    return secretsService;
  }

  /**
   * Will return an TokenManagementService singleton.
   */
  public TokenManagementService getTokenManagementService() {
    if (tokenManagementService == null) {
      tokenManagementService = new TokenManagementServiceImpl(client2dot0);
    }
    return tokenManagementService;
  }

  /**
   * Will return an TokenService singleton.
   */
  public TokenService getTokenService() {
    if (tokenService == null) {
      tokenService = new TokenServiceImpl(client2dot0);
    }
    return tokenService;
  }

  /**
   * Will return an AccountService singleton.
   */
  public AccountService getAccountService() {
    if (accountService == null && client2dot0.getHost().equals("accounts.cloud.databricks.com")) {
      accountService = new AccountServiceImpl(client2dot0);
    }
    return accountService;
  }

  /**
   * Will return a UnityCatalogService singleton.
   */
  public UnityCatalogService getUnityCatalogService() {
    if (unityCatalogService == null && client2dot0.getHost().equals("accounts.cloud.databricks.com")) {
      unityCatalogService = new UnityCatalogServiceImpl(client2dot0);
    }
    return unityCatalogService;
  }

  /**
   * This is how the DatabricksServiceFactory should be constructed. This gives flexibility to add more parameters later
   * without ending up with large constructors.
   */
  public static class Builder {

    String host;
    String token;
    String username;
    String password;
    String userAgent;
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
     * From the docs in DefaultHttpRequestRetryHandler: Whether or not methods that have successfully sent their request
     * will be retried.
     */
    boolean requestSentRetryEnabled = false;
    //This can be used for miscellaneous properties that are needed for custom implementations.
    Properties properties = new Properties();

    public Properties getProperties() {
      return properties;
    }

    public Builder withProperties(Properties properties) {
      this.properties = properties;
      return this;
    }

    private Builder() {
      //NO-OP
    }

    private Builder(Builder copy) {
      this.host = copy.host;
      this.token = copy.token;
      this.username = copy.username;
      this.password = copy.password;
      this.userAgent = copy.userAgent;
      this.apiVersion = copy.apiVersion;
      this.retryInterval = copy.retryInterval;
      this.maxRetries = copy.maxRetries;
      this.soTimeout = copy.soTimeout;
      this.connectionTimeout = copy.connectionTimeout;
      this.connectionRequestTimeout = copy.connectionRequestTimeout;
      this.requestSentRetryEnabled = copy.requestSentRetryEnabled;
      this.properties = copy.properties;
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
     * @deprecated in version 2.3.4, please use {@link Builder#createTokenAuthentication(String, String)}
     */
    @Deprecated
    public static Builder createUserPasswordAuthentication(String username,
        String password, String host) {
      Builder builder = new Builder();
      builder.username = username;
      builder.password = password;
      builder.host = host;
      return builder;
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

    public String getUserAgent() {
      return userAgent;
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
     */
    public Builder withRetryInterval(long retryInterval) {
      this.retryInterval = retryInterval;
      return this;
    }

    /**
     * set Http-Client SoTimeout.
     *
     * @param soTimeout unit is milliseconds
     */
    public Builder withSoTimeout(int soTimeout) {
      this.soTimeout = soTimeout;
      return this;
    }

    /**
     * set Http-Client connection timeout.
     *
     * @param connectionTimeout unit is milliseconds
     */
    public Builder withConnectionTimeout(int connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      return this;
    }

    /**
     * set Http-Client connection request timeout.
     *
     * @param connectionRequestTimeout unit is milliseconds
     */
    public Builder withConnectionRequestTimeout(int connectionRequestTimeout) {
      this.connectionRequestTimeout = connectionRequestTimeout;
      return this;
    }

    public Builder withRequestSentRetryEnabled(boolean requestSentRetryEnabled) {
      this.requestSentRetryEnabled = requestSentRetryEnabled;
      return this;
    }

    public Builder withUserAgent(String userAgent) {
      this.userAgent = userAgent;
      return this;
    }

    /**
     * Builds a DatabricksServiceFactory.
     *
     * @return the databricks service factory object
     */
    public DatabricksServiceFactory build() {
      return this.build(new DefaultHttpClientBuilderFactory(this));
    }

    /**
     * Builds a DatabricksServiceFactory.
     *
     * @return the databricks service factory object
     */
    public DatabricksServiceFactory build(HttpClientBuilderFactory factory) {
      if ("2.1".equals(apiVersion)) {
        DatabricksRestClient restClient2dot1 = new DatabricksRestClientImpl(this, factory);
        Builder builder2dot0 = new Builder(this).withApiVersion("2.0");
        DatabricksRestClient restClient2dot0 = new DatabricksRestClientImpl(builder2dot0, factory);
        return new DatabricksServiceFactory(restClient2dot0, restClient2dot1);
      } else {
        DatabricksRestClient restClient = new DatabricksRestClientImpl(this, factory);
        return new DatabricksServiceFactory(restClient);
      }
    }
  }
}
