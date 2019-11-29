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

package com.edmunds.rest.databricks.DTO.jobs;

import com.edmunds.rest.databricks.DTO.clusters.AutoScaleDTO;
import com.edmunds.rest.databricks.DTO.clusters.AwsAttributesDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterLogConfDTO;
import com.edmunds.rest.databricks.DTO.clusters.InitScriptInfoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import lombok.Data;

/**
 *
 */
@Data
public class NewClusterDTO implements Serializable {

  @JsonProperty("num_workers")
  private int numWorkers;
  @JsonProperty("autoscale")
  private AutoScaleDTO autoScale;
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
  private InitScriptInfoDTO initScripts;
  @JsonProperty("spark_env_vars")
  private Map<String, String> sparkEnvVars;
  @JsonProperty("enable_elastic_disk")
  private boolean enableElasticDisk;
  @JsonProperty("instance_pool_id")
  private String instancePoolId;

  // custom parameters
  @JsonProperty("autotermination_minutes")
  private int autoTerminationMinutes;
  @JsonProperty("artifact_paths")
  private Collection<String> artifactPaths;

}
