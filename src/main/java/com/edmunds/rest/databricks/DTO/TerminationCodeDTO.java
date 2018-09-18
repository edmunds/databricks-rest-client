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

import java.io.Serializable;

/**
 *
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
  REQUEST_REJECTED("REQUEST_REJECTED");

  private String value;

  TerminationCodeDTO(String value) {
    this.value = value;
  }

  public String toString() {
    return this.value;
  }
}
