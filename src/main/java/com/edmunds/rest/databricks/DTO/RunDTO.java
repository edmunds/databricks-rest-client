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
import java.util.Date;

/**
 *
 */
public class RunDTO implements Serializable {

  @JsonProperty("job_id")
  private long jobId;
  @JsonProperty("run_id")
  private long runId;
  @JsonProperty("number_in_job")
  private long numberInJob;
  @JsonProperty("state")
  private RunStateDTO state;
  @JsonProperty("task")
  private JobTaskDTO task;
  @JsonProperty("cluster_spec")
  private ClusterSpecDTO clusterSpec;
  @JsonProperty("cluster_instance")
  private ClusterInstanceDTO clusterInstance;
  @JsonProperty("original_attempt_run_id")
  private long originalAttemptRunId;
  @JsonProperty("overriding_parameters")
  private RunParametersDTO overridingParameters;
  @JsonProperty("start_time")
  private Date startTime;
  @JsonProperty("setup_duration")
  private long setupDuration;
  @JsonProperty("execution_duration")
  private long executionDuration;
  @JsonProperty("cleanup_duration")
  private long cleanupDuration;
  @JsonProperty("trigger")
  private TriggerTypeDTO trigger;
  @JsonProperty("schedule")
  private CronScheduleDTO schedule;
  @JsonProperty("creator_user_name")
  private String creatorUserName;
  @JsonProperty("run_name")
  private String runName;
  @JsonProperty("run_page_url")
  private String runPageUrl;
  @JsonProperty("run_type")
  private String runType;

  public String getRunType() {
    return runType;
  }

  public void setRunType(String runType) {
    this.runType = runType;
  }

  public String getRunPageUrl() {
    return runPageUrl;
  }

  public void setRunPageUrl(String runPageUrl) {
    this.runPageUrl = runPageUrl;
  }

  public String getRunName() {
    return runName;
  }

  public void setRunName(String runName) {
    this.runName = runName;
  }

  public String getCreatorUserName() {
    return creatorUserName;
  }

  public void setCreatorUserName(String creatorUserName) {
    this.creatorUserName = creatorUserName;
  }

  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  public long getRunId() {
    return runId;
  }

  public void setRunId(long runId) {
    this.runId = runId;
  }

  public long getNumberInJob() {
    return numberInJob;
  }

  public void setNumberInJob(long numberInJob) {
    this.numberInJob = numberInJob;
  }

  public RunStateDTO getState() {
    return state;
  }

  public void setState(RunStateDTO state) {
    this.state = state;
  }

  public JobTaskDTO getTask() {
    return task;
  }

  public void setTask(JobTaskDTO task) {
    this.task = task;
  }

  public ClusterSpecDTO getClusterSpec() {
    return clusterSpec;
  }

  public void setClusterSpec(ClusterSpecDTO clusterSpec) {
    this.clusterSpec = clusterSpec;
  }

  public ClusterInstanceDTO getClusterInstance() {
    return clusterInstance;
  }

  public void setClusterInstance(ClusterInstanceDTO clusterInstance) {
    this.clusterInstance = clusterInstance;
  }

  public long getOriginalAttemptRunId() {
    return originalAttemptRunId;
  }

  public void setOriginalAttemptRunId(long originalAttemptRunId) {
    this.originalAttemptRunId = originalAttemptRunId;
  }

  public RunParametersDTO getOverridingParameters() {
    return overridingParameters;
  }

  public void setOverridingParameters(RunParametersDTO overridingParameters) {
    this.overridingParameters = overridingParameters;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = new Date(startTime);
  }

  public long getSetupDuration() {
    return setupDuration;
  }

  public void setSetupDuration(long setupDuration) {
    this.setupDuration = setupDuration;
  }

  public long getExecutionDuration() {
    return executionDuration;
  }

  public void setExecutionDuration(long executionDuration) {
    this.executionDuration = executionDuration;
  }

  public long getCleanupDuration() {
    return cleanupDuration;
  }

  public void setCleanupDuration(long cleanupDuration) {
    this.cleanupDuration = cleanupDuration;
  }

  public TriggerTypeDTO getTrigger() {
    return trigger;
  }

  public void setTrigger(TriggerTypeDTO trigger) {
    this.trigger = trigger;
  }

  public CronScheduleDTO getSchedule() {
    return schedule;
  }

  public void setSchedule(CronScheduleDTO schedule) {
    this.schedule = schedule;
  }
}
