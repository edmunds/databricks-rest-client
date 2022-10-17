/*
 * Copyright 2022 Edmunds.com, Inc.
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

import com.edmunds.rest.databricks.DTO.permissions.AccessControlRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class RunSubmitDTO implements Serializable {
    @JsonProperty("tasks")
    private RunSubmitTaskDTO[] tasks;
    @JsonProperty("run_name")
    private String runName;
    @JsonProperty("timeout_seconds")
    private int timeoutSeconds;
    @JsonProperty("idempotency_token")
    private String idempotencyToken;
    @JsonProperty("access_control_list")
    private AccessControlRequestDTO[] accessControlRequests;
}
