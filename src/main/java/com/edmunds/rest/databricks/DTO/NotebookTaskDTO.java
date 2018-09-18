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
import java.util.Map;

/**
 *
 */
public class NotebookTaskDTO implements Serializable {

  @JsonProperty("notebook_path")
  private String notebookPath;
  @JsonProperty("base_parameters")
  private Map<String, String> baseParameters;
  @JsonProperty("revision_timestamp")
  private long revisionTimestamp;

  public long getRevisionTimestamp() {
    return revisionTimestamp;
  }

  public void setRevisionTimestamp(long revisionTimestamp) {
    this.revisionTimestamp = revisionTimestamp;
  }

  public Map<String, String> getBaseParameters() {
    return baseParameters;
  }

  public void setBaseParameters(Map<String, String> baseParameters) {
    this.baseParameters = baseParameters;
  }

  public String getNotebookPath() {
    return notebookPath;
  }

  public void setNotebookPath(String notebookPath) {
    this.notebookPath = notebookPath;
  }
}
