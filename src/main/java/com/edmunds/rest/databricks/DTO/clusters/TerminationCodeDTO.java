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

import java.io.Serializable;

/**
 * @see <a href="https://docs.databricks.com/dev-tools/api/latest/clusters.html#clusterterminationreasonterminationcode">https://docs.databricks.com/dev-tools/api/latest/clusters.html#clusterterminationreasonterminationcode</a>
 */
public enum TerminationCodeDTO implements Serializable {
  USER_REQUEST("USER_REQUEST"),
  JOB_FINISHED("JOB_FINISHED"),
  INACTIVITY("INACTIVITY"),
  CLOUD_PROVIDER_SHUTDOWN("CLOUD_PROVIDER_SHUTDOWN"),
  COMMUNICATION_LOST("COMMUNICATION_LOST"),
  CLOUD_PROVIDER_LAUNCH_FAILURE("CLOUD_PROVIDER_LAUNCH_FAILURE"),
  SPARK_STARTUP_FAILURE("SPARK_STARTUP_FAILURE"),
  INVALID_ARGUMENT("INVALID_ARGUMENT"),
  UNEXPECTED_LAUNCH_FAILURE("UNEXPECTED_LAUNCH_FAILURE"),
  INTERNAL_ERROR("INTERNAL_ERROR"),
  INSTANCE_UNREACHABLE("INSTANCE_UNREACHABLE"),
  INSTANCE_POOL_CLUSTER_FAILURE("INSTANCE_POOL_CLUSTER_FAILURE"),
  REQUEST_REJECTED("REQUEST_REJECTED"),
  INIT_SCRIPT_FAILURE("INIT_SCRIPT_FAILURE"),
  TRIAL_EXPIRED("TRIAL_EXPIRED"),
  SPARK_ERROR("SPARK_ERROR"),
  METASTORE_COMPONENT_UNHEALTHY("METASTORE_COMPONENT_UNHEALTHY"),
  DBFS_COMPONENT_UNHEALTHY("DBFS_COMPONENT_UNHEALTHY"),
  DRIVER_UNREACHABLE("DRIVER_UNREACHABLE"),
  DRIVER_UNRESPONSIVE("DRIVER_UNRESPONSIVE");

  private String value;

  TerminationCodeDTO(String value) {
    this.value = value;
  }

  public String toString() {
    return this.value;
  }
}
