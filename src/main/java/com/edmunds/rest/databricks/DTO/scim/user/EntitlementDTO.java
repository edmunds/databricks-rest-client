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
package com.edmunds.rest.databricks.DTO.scim.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public enum EntitlementDTO implements Serializable {

  ALLOW_CLUSTER_CREATE("allow-cluster-create"), ALLOW_INSTANCE_POOL_CREATE("allow-instance-pool-create");
  private static Map<String, EntitlementDTO> namesMap = new HashMap<>();

  static {
    namesMap.put("allow-cluster-create", ALLOW_CLUSTER_CREATE);
    namesMap.put("allow-instance-pool-create", ALLOW_INSTANCE_POOL_CREATE);
  }

  private final String value;

  EntitlementDTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static EntitlementDTO forValue(String value) {
    return namesMap.get(value);
  }

  @Override
  public String toString() {
    return this.value;
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, EntitlementDTO> entry : namesMap.entrySet()) {
      if (entry.getValue() == this) {
        return entry.getKey();
      }
    }
    return null;
  }
}
