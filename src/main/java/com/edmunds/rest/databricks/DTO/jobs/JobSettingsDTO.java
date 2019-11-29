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

import com.edmunds.rest.databricks.DTO.libraries.LibraryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 */
@SuppressWarnings("PMD")
@Data
public class JobSettingsDTO implements Serializable {

  @JsonProperty("existing_cluster_id")
  private String existingClusterId;
  @JsonProperty("new_cluster")
  private NewClusterDTO newCluster;
  @JsonProperty("notebook_task")
  private NotebookTaskDTO notebookTask;
  @JsonProperty("spark_jar_task")
  private SparkJarTaskDTO sparkJarTask;
  @JsonProperty("spark_python_task")
  private SparkPythonTaskDTO sparkPythonTask;
  @JsonProperty("spark_submit_task")
  private SparkSubmitTaskDTO sparkSubmitTask;
  @JsonProperty("name")
  private String name;
  @JsonProperty("libraries")
  private LibraryDTO[] libraries;
  @JsonProperty("email_notifications")
  private JobEmailNotificationsDTO emailNotifications;
  @JsonProperty("timeout_seconds")
  private Long timeoutSeconds;
  @JsonProperty("max_retries")
  private Integer maxRetries;
  @JsonProperty("min_retry_interval_millis")
  private Long minRetryIntervalMillis;
  @JsonProperty("retry_on_timeout")
  private boolean retryOnTimeout;
  @JsonProperty("schedule")
  private CronScheduleDTO schedule;
  @JsonProperty("max_concurrent_runs")
  private Integer maxConcurrentRuns;

}
