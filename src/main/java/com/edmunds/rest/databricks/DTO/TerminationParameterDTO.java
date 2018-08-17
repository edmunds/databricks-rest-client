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
public enum TerminationParameterDTO implements Serializable {
    username("username"),
    aws_api_error_code("aws_api_error_code"),
    aws_instance_state_reason("aws_instance_state_reason"),
    aws_spot_request_status("aws_spot_request_status"),
    aws_spot_request_fault_code("aws_spot_request_fault_code"),
    aws_impaired_status_details("aws_impaired_status_details"),
    aws_instance_status_event("aws_instance_status_event"),
    aws_error_message("aws_error_message"),
    databricks_error_message("databricks_error_message"),
    inactivity_duration_min("inactivity_duration_min"),
    instance_id("instance_id");

    private String value;

    TerminationParameterDTO(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
