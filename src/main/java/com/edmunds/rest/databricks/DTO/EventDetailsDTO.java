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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Data
public class EventDetailsDTO implements Serializable {

  @Getter @Setter @JsonProperty("current_num_workers")
  private int currentNumWorkers;
  @Getter @Setter @JsonProperty("target_num_workers")
  private int targetNumWorkers;
  @Getter @Setter @JsonProperty("previous_attributes")
  private ClusterAttributesDTO previousAttributes;
  @Getter @Setter @JsonProperty("attributes")
  private ClusterAttributesDTO attributes;
  @Getter @Setter @JsonProperty("previous_cluster_size")
  private ClusterSizeDTO previousClusterSize;
  @Getter @Setter @JsonProperty("cluster_size")
  private ClusterSizeDTO clusterSize;
  @Getter @Setter @JsonProperty("cause")
  private ResizeCauseDTO cause;
  @Getter @Setter @JsonProperty("reason")
  private TerminationReasonDTO reason;
  @Getter @Setter @JsonProperty("user")
  private String user;

}
