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
public class ClusterAttributesDTO implements Serializable {

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
  @Getter @Setter @JsonProperty("enable_jdbc_auto_start")
  private boolean enableJdbcAutoStart;

}
