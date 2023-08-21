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

package com.edmunds.rest.databricks.DTO.clusters;

import com.edmunds.rest.databricks.DTO.DatabricksAssetDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import lombok.Data;

/**
 *
 */
@Data
public class ClusterInfoDTO implements DatabricksAssetDTO {

  @JsonProperty("num_workers")
  private Integer numWorkers;
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
  @JsonProperty("init_scripts")
  private InitScriptInfoDTO[] initScripts;
  @JsonProperty("spark_env_vars")
  private Map<String, String> sparkEnvVars;
  @JsonProperty("autotermination_minutes")
  private Integer autoTerminationMinutes;
  @JsonProperty("enable_elastic_disk")
  private boolean enableElasticDisk;
  @JsonProperty("instance_pool_id")
  private String instancePoolId;
  @JsonProperty("driver_instance_pool_id")
  private String driverInstancePoolId;
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
  @JsonProperty("docker_image")
  private DockerImageDTO dockerImage;
  @JsonProperty("runtime_engine")
  private String runtimeEngine;
  @JsonProperty("single_user_name")
  private String singleUserName;
  @JsonProperty("policy_id")
  private String policyId;
  @JsonProperty("data_security_mode")
  private String dataSecurityMode;

  @Override
  public String getId() {
    return clusterId;
  }

  @Override
  public String getName() {
    return clusterName;
  }
}
