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

package com.edmunds.rest.databricks.DTO.jobs;

import com.edmunds.rest.databricks.DTO.permissions.AccessControlRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 *
 */
@SuppressWarnings("PMD")
@Data
public class JobSettingsDTOv21 implements Serializable {

  @JsonProperty("name")
  private String name;
  @JsonProperty("tags")
  private Map<String, String> tags;
  @JsonProperty("tasks")
  private JobTaskDTOv21[] tasks;
  @JsonProperty("job_clusters")
  private JobClusterDTO[] jobClusters;
  @JsonProperty("email_notifications")
  private JobEmailNotificationsDTO emailNotifications;
  @JsonProperty("timeout_seconds")
  private int timeoutSeconds;
  @JsonProperty("schedule")
  private CronScheduleDTO schedule;
  @JsonProperty("max_concurrent_runs")
  private int maxConcurrentRuns;
  @JsonProperty("access_control_list")
  private AccessControlRequestDTO[] accessControlList;
}
