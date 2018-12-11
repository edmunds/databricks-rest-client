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

/**
 *
 */
@Data
public class EventDetailsDTO implements Serializable {

  @JsonProperty("current_num_workers")
  private int currentNumWorkers;
  @JsonProperty("target_num_workers")
  private int targetNumWorkers;
  @JsonProperty("previous_attributes")
  private ClusterAttributesDTO previousAttributes;
  @JsonProperty("attributes")
  private ClusterAttributesDTO attributes;
  @JsonProperty("previous_cluster_size")
  private ClusterSizeDTO previousClusterSize;
  @JsonProperty("cluster_size")
  private ClusterSizeDTO clusterSize;
  @JsonProperty("cause")
  private ResizeCauseDTO cause;
  @JsonProperty("reason")
  private TerminationReasonDTO reason;
  @JsonProperty("user")
  private String user;

  public int getCurrentNumWorkers() {
    return currentNumWorkers;
  }

  public void setCurrentNumWorkers(int currentNumWorkers) {
    this.currentNumWorkers = currentNumWorkers;
  }

  public int getTargetNumWorkers() {
    return targetNumWorkers;
  }

  public void setTargetNumWorkers(int targetNumWorkers) {
    this.targetNumWorkers = targetNumWorkers;
  }

  public ClusterAttributesDTO getPreviousAttributes() {
    return previousAttributes;
  }

  public void setPreviousAttributes(ClusterAttributesDTO previousAttributes) {
    this.previousAttributes = previousAttributes;
  }

  public ClusterAttributesDTO getAttributes() {
    return attributes;
  }

  public void setAttributes(ClusterAttributesDTO attributes) {
    this.attributes = attributes;
  }

  public ClusterSizeDTO getPreviousClusterSize() {
    return previousClusterSize;
  }

  public void setPreviousClusterSize(ClusterSizeDTO previousClusterSize) {
    this.previousClusterSize = previousClusterSize;
  }

  public ClusterSizeDTO getClusterSize() {
    return clusterSize;
  }

  public void setClusterSize(ClusterSizeDTO clusterSize) {
    this.clusterSize = clusterSize;
  }

  public ResizeCauseDTO getCause() {
    return cause;
  }

  public void setCause(ResizeCauseDTO cause) {
    this.cause = cause;
  }

  public TerminationReasonDTO getReason() {
    return reason;
  }

  public void setReason(TerminationReasonDTO reason) {
    this.reason = reason;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
