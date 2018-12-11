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
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Data
public class ClusterInfoDTO implements Serializable {

  @Getter @Setter @JsonProperty("num_workers")
  private int numWorkers;
  @Getter @Setter @JsonProperty("autoscale")
  private AutoScaleDTO autoscale;
  @Getter @Setter @JsonProperty("cluster_id")
  private String clusterId;
  @Getter @Setter @JsonProperty("creator_user_name")
  private String creatorUserName;
  @Getter @Setter @JsonProperty("driver")
  private SparkNodeDTO driver;
  @Getter @Setter @JsonProperty("executors")
  private SparkNodeDTO[] executors;
  @Getter @Setter @JsonProperty("spark_context_id")
  private long sparkContextId;
  @Getter @Setter @JsonProperty("jdbc_port")
  private int jdbcPort;
  @Getter @Setter @JsonProperty("cluster_name")
  private String clusterName;
  @Getter @Setter @JsonProperty("spark_version")
  private String sparkVersion;
  @Getter @Setter @JsonProperty("spark_conf")
  private Map<String, String> sparkConf;
  @Getter @Setter @JsonProperty("aws_attributes")
  private AwsAttributesDTO awsAttributes;
  @Getter @Setter @JsonProperty("node_type_id")
  private String nodeTypeId;
  @Getter @Setter @JsonProperty("driver_node_type_id")
  private String driverNodeTypeId;
  @Getter @Setter @JsonProperty("ssh_public_keys")
  private String[] sshPublicKeys;
  @Getter @Setter @JsonProperty("custom_tags")
  private Map<String, String> customTags;
  @Getter @Setter @JsonProperty("cluster_log_conf")
  private ClusterLogConfDTO clusterLogConf;
  @Getter @Setter @JsonProperty("spark_env_vars")
  private Map<String, String> sparkEnvVars;
  @Getter @Setter @JsonProperty("autotermination_minutes")
  private int autoTerminationMinutes;
  @Getter @Setter @JsonProperty("enable_elastic_disk")
  private boolean enableElasticDisk;
  @Getter @Setter @JsonProperty("cluster_source")
  private ClusterSourceDTO clusterSource;
  @Getter @Setter @JsonProperty("state")
  private ClusterStateDTO state;
  @Getter @Setter @JsonProperty("state_message")
  private String stateMessage;
  @Getter @Setter @JsonProperty("start_time")
  private long startTime;
  @Getter @Setter @JsonProperty("terminated_time")
  private long terminatedTime;
  @Getter @Setter @JsonProperty("last_state_loss_time")
  private long lastStateLossTime;
  @Getter @Setter @JsonProperty("last_activity_time")
  private long lastActivityTime;
  @Getter @Setter @JsonProperty("cluster_memory_mb")
  private long clusterMemoryMb;
  @Getter @Setter @JsonProperty("cluster_cores")
  private float clusterCores;
  @Getter @Setter @JsonProperty("default_tags")
  private Map<String, String> defaultTags;
  @Getter @Setter @JsonProperty("cluster_log_status")
  private LogSyncStatusDTO clusterLogStatus;
  @Getter @Setter @JsonProperty("termination_reason")
  private TerminationReasonDTO terminationReason;
  @Getter @Setter @JsonProperty("enable_jdbc_auto_start")
  private boolean enableJdbcAutoStart;
}
