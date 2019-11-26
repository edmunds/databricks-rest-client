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
public class InstancePoolAndStatsDTO implements Serializable {

  @Getter @Setter @JsonProperty("instance_pool_name")
  private String instancePoolName;
  @Getter @Setter @JsonProperty("min_idle_instances")
  private int minIdleInstances;
  @Getter @Setter @JsonProperty("max_capacity")
  private int maxCapacity;
  @Getter @Setter @JsonProperty("aws_attributes")
  private InstancePoolAwsAttributesDTO awsAttributes;
  @Getter @Setter @JsonProperty("node_type_id")
  private String nodeTypeId;
  @Getter @Setter @JsonProperty("custom_tags")
  private Map<String, String> customTags;
  @Getter @Setter @JsonProperty("idle_instance_autotermination_minutes")
  private int idleInstanceAutoterminationMinutes;
  @Getter @Setter @JsonProperty("enable_elastic_disk")
  private boolean enableElasticDisk;
  @Getter @Setter @JsonProperty("disk_spec")
  private DiskSpecDTO diskSpec;
  @Getter @Setter @JsonProperty("preloaded_spark_versions")
  private String[] preloadedSparkVersions;
  @Getter @Setter @JsonProperty("instance_pool_id")
  private String instancePoolId;
  @Getter @Setter @JsonProperty("default_tags")
  private Map<String, String> defaultTags;
  @Getter @Setter
  private InstancePoolStateDTO state;
  @Getter @Setter
  private InstancePoolStatsDTO stats;

}
