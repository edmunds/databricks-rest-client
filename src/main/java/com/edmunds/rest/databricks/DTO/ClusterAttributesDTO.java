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
public class ClusterAttributesDTO implements Serializable {

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
  @JsonProperty("enable_jdbc_auto_start")
  private boolean enableJdbcAutoStart;

  public boolean isEnableJdbcAutoStart() {
    return enableJdbcAutoStart;
  }

  public void setEnableJdbcAutoStart(boolean enableJdbcAutoStart) {
    this.enableJdbcAutoStart = enableJdbcAutoStart;
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
}
