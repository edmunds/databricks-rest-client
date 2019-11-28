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
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@SuppressWarnings("PMD")
@Data
public class JobSettingsDTO implements Serializable {

  @Getter @Setter @JsonProperty("existing_cluster_id")
  private String existingClusterId;
  @Getter @Setter @JsonProperty("new_cluster")
  private NewClusterDTO newCluster;
  @Getter @Setter @JsonProperty("notebook_task")
  private NotebookTaskDTO notebookTask;
  @Getter @Setter @JsonProperty("spark_jar_task")
  private SparkJarTaskDTO sparkJarTask;
  @Getter @Setter @JsonProperty("spark_python_task")
  private SparkPythonTaskDTO sparkPythonTask;
  @Getter @Setter @JsonProperty("spark_submit_task")
  private SparkSubmitTaskDTO sparkSubmitTask;
  @Getter @Setter @JsonProperty("name")
  private String name;
  @Getter @Setter @JsonProperty("libraries")
  private LibraryDTO[] libraries;
  @Getter @Setter @JsonProperty("email_notifications")
  private JobEmailNotificationsDTO emailNotifications;
  @Getter @Setter @JsonProperty("timeout_seconds")
  private Long timeoutSeconds;
  @Getter @Setter @JsonProperty("max_retries")
  private Integer maxRetries;
  @Getter @Setter @JsonProperty("min_retry_interval_millis")
  private Long minRetryIntervalMillis;
  @Getter @Setter @JsonProperty("retry_on_timeout")
  private boolean retryOnTimeout;
  @Getter @Setter @JsonProperty("schedule")
  private CronScheduleDTO schedule;
  @Getter @Setter @JsonProperty("max_concurrent_runs")
  private Integer maxConcurrentRuns;
}
