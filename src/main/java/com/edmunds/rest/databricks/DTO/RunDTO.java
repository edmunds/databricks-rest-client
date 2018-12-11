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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 *
 */
@Data
public class RunDTO implements Serializable {

  @Getter @Setter @JsonProperty("job_id")
  private long jobId;
  @Getter @Setter @JsonProperty("run_id")
  private long runId;
  @Getter @Setter @JsonProperty("number_in_job")
  private long numberInJob;
  @Getter @Setter @JsonProperty("state")
  private RunStateDTO state;
  @Getter @Setter @JsonProperty("task")
  private JobTaskDTO task;
  @Getter @Setter @JsonProperty("cluster_spec")
  private ClusterSpecDTO clusterSpec;
  @Getter @Setter @JsonProperty("cluster_instance")
  private ClusterInstanceDTO clusterInstance;
  @Getter @Setter @JsonProperty("original_attempt_run_id")
  private long originalAttemptRunId;
  @Getter @Setter @JsonProperty("overriding_parameters")
  private RunParametersDTO overridingParameters;
  @Getter @Setter @JsonProperty("start_time")
  private Date startTime;
  @Getter @Setter @JsonProperty("setup_duration")
  private long setupDuration;
  @Getter @Setter @JsonProperty("execution_duration")
  private long executionDuration;
  @Getter @Setter @JsonProperty("cleanup_duration")
  private long cleanupDuration;
  @Getter @Setter @JsonProperty("trigger")
  private TriggerTypeDTO trigger;
  @Getter @Setter @JsonProperty("schedule")
  private CronScheduleDTO schedule;
  @Getter @Setter @JsonProperty("creator_user_name")
  private String creatorUserName;
  @Getter @Setter @JsonProperty("run_name")
  private String runName;
  @Getter @Setter @JsonProperty("run_page_url")
  private String runPageUrl;
  @Getter @Setter @JsonProperty("run_type")
  private String runType;
}
