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

package com.edmunds.rest.databricks.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 *
 */
@Data
public class ClusterInfoDTO implements Serializable {

  @JsonProperty("num_workers")
  private int numWorkers;
  @JsonProperty("autoscale")
  private AutoScaleDTO autoscale;
  @JsonProperty("cluster_id")
  private String clusterId;
  @JsonProperty("creator_user_name")
  private String creatorUserName;
  @JsonProperty("driver")
  private SparkNodeDTO driver;
  @JsonProperty("executors")
  private SparkNodeDTO[] executors;
  @JsonProperty("spark_context_id")
  private long sparkContextId;
  @JsonProperty("jdbc_port")
  private int jdbcPort;
  @JsonProperty("cluster_name")
  private String clusterName;
  @JsonProperty("spark_version")
  private String sparkVersion;
  @JsonProperty("spark_conf")
  private Map<String, String> sparkConf;
  @JsonProperty("aws_attributes")
  private AwsAttributesDTO awsAttributes;
  @JsonProperty("node_type_id")
  private String nodeTypeId;
  @JsonProperty("driver_node_type_id")
  private String driverNodeTypeId;
  @JsonProperty("ssh_public_keys")
  private String[] sshPublicKeys;
  @JsonProperty("custom_tags")
  private Map<String, String> customTags;
  @JsonProperty("cluster_log_conf")
  private ClusterLogConfDTO clusterLogConf;
  @JsonProperty("spark_env_vars")
  private Map<String, String> sparkEnvVars;
  @JsonProperty("autotermination_minutes")
  private int autoTerminationMinutes;
  @JsonProperty("enable_elastic_disk")
  private boolean enableElasticDisk;
  @JsonProperty("cluster_source")
  private ClusterSourceDTO clusterSource;
  @JsonProperty("state")
  private ClusterStateDTO state;
  @JsonProperty("state_message")
  private String stateMessage;
  @JsonProperty("start_time")
  private long startTime;
  @JsonProperty("terminated_time")
  private long terminatedTime;
  @JsonProperty("last_state_loss_time")
  private long lastStateLossTime;
  @JsonProperty("last_activity_time")
  private long lastActivityTime;
  @JsonProperty("cluster_memory_mb")
  private long clusterMemoryMb;
  @JsonProperty("cluster_cores")
  private float clusterCores;
  @JsonProperty("default_tags")
  private Map<String, String> defaultTags;
  @JsonProperty("cluster_log_status")
  private LogSyncStatusDTO clusterLogStatus;
  @JsonProperty("termination_reason")
  private TerminationReasonDTO terminationReason;
  @JsonProperty("enable_jdbc_auto_start")
  private boolean enableJdbcAutoStart;

  public boolean isEnableJdbcAutoStart() {
    return enableJdbcAutoStart;
  }

  public void setEnableJdbcAutoStart(boolean enableJdbcAutoStart) {
    this.enableJdbcAutoStart = enableJdbcAutoStart;
  }

  public int getNumWorkers() {
    return numWorkers;
  }

  public void setNumWorkers(int numWorkers) {
    this.numWorkers = numWorkers;
  }

  public AutoScaleDTO getAutoscale() {
    return autoscale;
  }

  public void setAutoscale(AutoScaleDTO autoscale) {
    this.autoscale = autoscale;
  }

  public String getClusterId() {
    return clusterId;
  }

  public void setClusterId(String clusterId) {
    this.clusterId = clusterId;
  }

  public String getCreatorUserName() {
    return creatorUserName;
  }

  public void setCreatorUserName(String creatorUserName) {
    this.creatorUserName = creatorUserName;
  }

  public SparkNodeDTO getDriver() {
    return driver;
  }

  public void setDriver(SparkNodeDTO driver) {
    this.driver = driver;
  }

  public long getSparkContextId() {
    return sparkContextId;
  }

  public void setSparkContextId(long sparkContextId) {
    this.sparkContextId = sparkContextId;
  }

  public SparkNodeDTO[] getExecutors() {
    return executors;
  }

  public void setExecutors(SparkNodeDTO[] executors) {
    this.executors = executors;
  }

  public int getJdbcPort() {
    return jdbcPort;
  }

  public void setJdbcPort(int jdbcPort) {
    this.jdbcPort = jdbcPort;
  }

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public String getSparkVersion() {
    return sparkVersion;
  }

  public void setSparkVersion(String sparkVersion) {
    this.sparkVersion = sparkVersion;
  }

  public Map<String, String> getSparkConf() {
    return sparkConf;
  }

  public void setSparkConf(Map<String, String> sparkConf) {
    this.sparkConf = sparkConf;
  }

  public AwsAttributesDTO getAwsAttributes() {
    return awsAttributes;
  }

  public void setAwsAttributes(AwsAttributesDTO awsAttributes) {
    this.awsAttributes = awsAttributes;
  }

  public String getNodeTypeId() {
    return nodeTypeId;
  }

  public void setNodeTypeId(String nodeTypeId) {
    this.nodeTypeId = nodeTypeId;
  }

  public String getDriverNodeTypeId() {
    return driverNodeTypeId;
  }

  public void setDriverNodeTypeId(String driverNodeTypeId) {
    this.driverNodeTypeId = driverNodeTypeId;
  }

  public String[] getSshPublicKeys() {
    return sshPublicKeys;
  }

  public void setSshPublicKeys(String[] sshPublicKeys) {
    this.sshPublicKeys = sshPublicKeys;
  }

  public Map<String, String> getCustomTags() {
    return customTags;
  }

  public void setCustomTags(Map<String, String> customTags) {
    this.customTags = customTags;
  }

  public ClusterLogConfDTO getClusterLogConf() {
    return clusterLogConf;
  }

  public void setClusterLogConf(ClusterLogConfDTO clusterLogConf) {
    this.clusterLogConf = clusterLogConf;
  }

  public Map<String, String> getSparkEnvVars() {
    return sparkEnvVars;
  }

  public void setSparkEnvVars(Map<String, String> sparkEnvVars) {
    this.sparkEnvVars = sparkEnvVars;
  }

  public int getAutoTerminationMinutes() {
    return autoTerminationMinutes;
  }

  public void setAutoTerminationMinutes(int autoTerminationMinutes) {
    this.autoTerminationMinutes = autoTerminationMinutes;
  }

  public boolean isEnableElasticDisk() {
    return enableElasticDisk;
  }

  public void setEnableElasticDisk(boolean enableElasticDisk) {
    this.enableElasticDisk = enableElasticDisk;
  }

  public ClusterSourceDTO getClusterSource() {
    return clusterSource;
  }

  public void setClusterSource(ClusterSourceDTO clusterSource) {
    this.clusterSource = clusterSource;
  }

  public ClusterStateDTO getState() {
    return state;
  }

  public void setState(ClusterStateDTO state) {
    this.state = state;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public String getStateMessage() {
    return stateMessage;
  }

  public void setStateMessage(String stateMessage) {
    this.stateMessage = stateMessage;
  }

  public long getTerminatedTime() {
    return terminatedTime;
  }

  public void setTerminatedTime(long terminatedTime) {
    this.terminatedTime = terminatedTime;
  }

  public long getLastStateLossTime() {
    return lastStateLossTime;
  }

  public void setLastStateLossTime(long lastStateLossTime) {
    this.lastStateLossTime = lastStateLossTime;
  }

  public long getLastActivityTime() {
    return lastActivityTime;
  }

  public void setLastActivityTime(long lastActivityTime) {
    this.lastActivityTime = lastActivityTime;
  }

  public long getClusterMemoryMb() {
    return clusterMemoryMb;
  }

  public void setClusterMemoryMb(long clusterMemoryMb) {
    this.clusterMemoryMb = clusterMemoryMb;
  }

  public float getClusterCores() {
    return clusterCores;
  }

  public void setClusterCores(float clusterCores) {
    this.clusterCores = clusterCores;
  }

  public Map<String, String> getDefaultTags() {
    return defaultTags;
  }

  public void setDefaultTags(Map<String, String> defaultTags) {
    this.defaultTags = defaultTags;
  }

  public LogSyncStatusDTO getClusterLogStatus() {
    return clusterLogStatus;
  }

  public void setClusterLogStatus(LogSyncStatusDTO clusterLogStatus) {
    this.clusterLogStatus = clusterLogStatus;
  }

  public TerminationReasonDTO getTerminationReason() {
    return terminationReason;
  }

  public void setTerminationReason(TerminationReasonDTO terminationReason) {
    this.terminationReason = terminationReason;
  }
}
