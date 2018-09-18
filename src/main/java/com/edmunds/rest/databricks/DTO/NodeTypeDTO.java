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

/**
 *
 */
public class NodeTypeDTO implements Serializable {

  @JsonProperty("node_type_id")
  private String nodeTypeId;
  @JsonProperty("memory_mb")
  private int memoryMb;
  @JsonProperty("num_cores")
  private float numCores;
  @JsonProperty("description")
  private String description;
  @JsonProperty("instance_type_id")
  private String instanceTypeId;
  @JsonProperty("is_deprecated")
  private boolean isDeprecated;

  public String getNodeTypeId() {
    return nodeTypeId;
  }

  public void setNodeTypeId(String nodeTypeId) {
    this.nodeTypeId = nodeTypeId;
  }

  public int getMemoryMb() {
    return memoryMb;
  }

  public void setMemoryMb(int memoryMb) {
    this.memoryMb = memoryMb;
  }

  public float getNumCores() {
    return numCores;
  }

  public void setNumCores(float numCores) {
    this.numCores = numCores;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInstanceTypeId() {
    return instanceTypeId;
  }

  public void setInstanceTypeId(String instanceTypeId) {
    this.instanceTypeId = instanceTypeId;
  }

  public boolean isDeprecated() {
    return isDeprecated;
  }

  public void setIsDeprecated(boolean isDeprecated) {
    this.isDeprecated = isDeprecated;
  }
}
