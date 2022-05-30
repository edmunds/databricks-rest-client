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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 *
 */
@Data
public class RunDTO implements Serializable {

  @JsonProperty("job_id")
  private long jobId;
  @JsonProperty("run_id")
  private long runId;
  @JsonProperty("creator_user_name")
  private String creatorUserName;
  @JsonProperty("number_in_job")
  private long numberInJob;
  @JsonProperty("original_attempt_run_id")
  private long originalAttemptRunId;
  @JsonProperty("state")
  private RunStateDTO state;
  @JsonProperty("schedule")
  private CronScheduleDTO schedule;
  @JsonProperty("tasks")
  private JobTaskDTO[] tasks;
  @JsonProperty("cluster_spec")
  private ClusterSpecDTO clusterSpec;
  @JsonProperty("cluster_instance")
  private ClusterInstanceDTO clusterInstance;
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

  // custom parameters
  @JsonProperty("run_name")
  private String runName;
  @JsonProperty("run_page_url")
  private String runPageUrl;
  @JsonProperty("run_type")
  private String runType;

}
