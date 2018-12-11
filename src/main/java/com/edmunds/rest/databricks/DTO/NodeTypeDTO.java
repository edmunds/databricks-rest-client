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
public class NodeTypeDTO implements Serializable {

  @Getter @Setter @JsonProperty("node_type_id")
  private String nodeTypeId;
  @Getter @Setter @JsonProperty("memory_mb")
  private int memoryMb;
  @Getter @Setter @JsonProperty("num_cores")
  private float numCores;
  @Getter @Setter @JsonProperty("description")
  private String description;
  @Getter @Setter @JsonProperty("instance_type_id")
  private String instanceTypeId;
  @Getter @Setter @JsonProperty("is_deprecated")
  private boolean isDeprecated;
}
