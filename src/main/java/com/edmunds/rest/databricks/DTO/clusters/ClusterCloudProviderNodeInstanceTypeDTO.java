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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
public class ClusterCloudProviderNodeInstanceTypeDTO implements Serializable {
  @JsonProperty("instance_type_id")
  private String instanceTypeId;

  @JsonProperty("local_disks")
  private int localDisks;

  @JsonProperty("local_disk_size_gb")
  private int localDiskSizeGb;

  @JsonProperty("instance_family")
  private String instanceFamily;

  @JsonProperty("swap_size")
  private String swapSize;

  @JsonProperty("is_graviton")
  private boolean isGraviton;

  // note: currently provided on AWS only, and is true for fleet instance type
  @JsonProperty("is_virtual")
  private boolean isVirtual;
}
