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

/**
 *
 */
public class JobTaskDTO implements Serializable {

  @JsonProperty("notebook_task")
  private NotebookTaskDTO notebookTask;
  @JsonProperty("spark_jar_task")
  private SparkJarTaskDTO sparkJarTask;
  @JsonProperty("spark_python_task")
  private SparkPythonTaskDTO sparkPythonTask;
  @JsonProperty("spark_submit_task")
  private SparkSubmitTaskDTO sparkSubmitTask;

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

  public NotebookTaskDTO getNotebookTask() {
    return notebookTask;
  }

  public void setNotebookTask(NotebookTaskDTO notebookTask) {
    this.notebookTask = notebookTask;
  }

  public SparkJarTaskDTO getSparkJarTask() {
    return sparkJarTask;
  }

  public void setSparkJarTask(SparkJarTaskDTO sparkJarTask) {
    this.sparkJarTask = sparkJarTask;
  }
}
