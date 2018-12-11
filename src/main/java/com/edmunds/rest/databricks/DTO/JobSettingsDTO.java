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
@SuppressWarnings("PMD")
@Data
public class JobSettingsDTO implements Serializable {

  @JsonProperty("name")
  private String name;
  @JsonProperty("new_cluster")
  private NewClusterDTO newCluster;
  @JsonProperty("existing_cluster_id")
  private String existingClusterId;
  @JsonProperty("email_notifications")
  private JobEmailNotificationsDTO emailNotifications;
  @JsonProperty("timeout_seconds")
  private Long timeoutSeconds;
  @JsonProperty("schedule")
  private CronScheduleDTO schedule;
  @JsonProperty("notebook_task")
  private NotebookTaskDTO notebookTask;
  @JsonProperty("spark_jar_task")
  private SparkJarTaskDTO sparkJarTask;
  @JsonProperty("spark_python_task")
  private SparkPythonTaskDTO sparkPythonTask;
  @JsonProperty("spark_submit_task")
  private SparkSubmitTaskDTO sparkSubmitTask;
  @JsonProperty("retry_on_timeout")
  private boolean retryOnTimeout;
  @JsonProperty("max_retries")
  private Integer maxRetries;
  @JsonProperty("min_retry_interval_millis")
  private Long minRetryIntervalMillis;
  @JsonProperty("libraries")
  private LibraryDTO[] libraries;
  @JsonProperty("max_concurrent_runs")
  private Integer maxConcurrentRuns;

  public SparkPythonTaskDTO getSparkPythonTask() {
    return sparkPythonTask;
  }

  public void setSparkPythonTask(SparkPythonTaskDTO sparkPythonTask) {
    this.sparkPythonTask = sparkPythonTask;
  }

  public SparkSubmitTaskDTO getSparkSubmitTask() {
    return sparkSubmitTask;
  }

  public void setSparkSubmitTask(SparkSubmitTaskDTO sparkSubmitTask) {
    this.sparkSubmitTask = sparkSubmitTask;
  }

  public boolean isRetryOnTimeout() {
    return retryOnTimeout;
  }

  public void setRetryOnTimeout(boolean retryOnTimeout) {
    this.retryOnTimeout = retryOnTimeout;
  }

  public Integer getMaxConcurrentRuns() {
    return maxConcurrentRuns;
  }

  public void setMaxConcurrentRuns(int maxConcurrentRuns) {
    this.maxConcurrentRuns = maxConcurrentRuns;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public NewClusterDTO getNewCluster() {
    return newCluster;
  }

  public void setNewCluster(NewClusterDTO newCluster) {
    this.newCluster = newCluster;
  }

  public JobEmailNotificationsDTO getEmailNotifications() {
    return emailNotifications;
  }

  public void setEmailNotifications(JobEmailNotificationsDTO emailNotifications) {
    this.emailNotifications = emailNotifications;
  }

  public Long getTimeoutSeconds() {
    return timeoutSeconds;
  }

  public void setTimeoutSeconds(long timeoutSeconds) {
    this.timeoutSeconds = timeoutSeconds;
  }

  public CronScheduleDTO getSchedule() {
    return schedule;
  }

  public void setSchedule(CronScheduleDTO schedule) {
    this.schedule = schedule;
  }

  public NotebookTaskDTO getNotebookTask() {
    return notebookTask;
  }

  public void setNotebookTask(NotebookTaskDTO notebookTask) {
    this.notebookTask = notebookTask;
  }

  public String getExistingClusterId() {
    return existingClusterId;
  }

  public void setExistingClusterId(String existingClusterId) {
    this.existingClusterId = existingClusterId;
  }

  public Integer getMaxRetries() {
    return maxRetries;
  }

  public void setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
  }

  public Long getMinRetryIntervalMillis() {
    return minRetryIntervalMillis;
  }

  public void setMinRetryIntervalMillis(long minRetryIntervalMillis) {
    this.minRetryIntervalMillis = minRetryIntervalMillis;
  }

  public LibraryDTO[] getLibraries() {
    return libraries;
  }

  public void setLibraries(LibraryDTO[] libraries) {
    this.libraries = libraries;
  }

  public SparkJarTaskDTO getSparkJarTask() {
    return sparkJarTask;
  }

  public void setSparkJarTask(SparkJarTaskDTO sparkJarTask) {
    this.sparkJarTask = sparkJarTask;
  }

}
