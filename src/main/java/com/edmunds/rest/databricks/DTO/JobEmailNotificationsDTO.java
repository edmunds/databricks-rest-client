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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 */
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobEmailNotificationsDTO implements Serializable {

  @JsonProperty("on_failure")
  private String[] onFailure;
  @JsonProperty("on_start")
  private String[] onStart;
  @JsonProperty("on_success")
  private String[] onSuccess;
  @JsonProperty("no_alert_for_skipped_runs")
  private boolean noAlertForSkippedRuns;

  public String[] getOnFailure() {
    return onFailure;
  }

  public void setOnFailure(String[] onFailure) {
    this.onFailure = onFailure;
  }

  public String[] getOnStart() {
    return onStart;
  }

  public void setOnStart(String[] onStart) {
    this.onStart = onStart;
  }

  public String[] getOnSuccess() {
    return onSuccess;
  }

  public void setOnSuccess(String[] onSuccess) {
    this.onSuccess = onSuccess;
  }

  public boolean isNoAlertForSkippedRuns() {
    return noAlertForSkippedRuns;
  }

  public void setNoAlertForSkippedRuns(boolean noAlertForSkippedRuns) {
    this.noAlertForSkippedRuns = noAlertForSkippedRuns;
  }
}
