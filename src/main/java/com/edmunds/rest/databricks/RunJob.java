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

package com.edmunds.rest.databricks;

import com.edmunds.rest.databricks.DTO.RunDTO;
import com.edmunds.rest.databricks.DTO.RunLifeCycleStateDTO;
import com.edmunds.rest.databricks.DTO.RunNowDTO;
import com.edmunds.rest.databricks.DTO.RunParametersDTO;
import com.edmunds.rest.databricks.DTO.RunResultStateDTO;
import com.edmunds.rest.databricks.DTO.RunStateDTO;
import com.edmunds.rest.databricks.service.JobService;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 *
 */
public class RunJob {

  // Use databricks job timeout setting. So, set 1 day for safety.
  public static final long DEFAULT_JOB_TIMEOUT = TimeUnit.DAYS.toMillis(1);
  public static final long DEFAULT_JOB_CHECK_INTERVAL = TimeUnit.MINUTES.toMillis(2);
  private static Logger log = Logger.getLogger(RunJob.class);
  private JobService service;
  private long jobId;
  private RunParametersDTO runParametersDTO;
  private long timeout;
  private long checkInterval;


  /**
   * providing numberInJob for info.
   */
  private long runId;
  private long numberInJob;

  public RunJob(JobService service, long jobId, RunParametersDTO runParametersDTO) {
    this(service, jobId, runParametersDTO, DEFAULT_JOB_TIMEOUT, DEFAULT_JOB_CHECK_INTERVAL);
  }

  public RunJob(JobService service, long jobId, RunParametersDTO runParametersDTO, long timeout,
      long checkInterval) {
    this.service = service;
    this.jobId = jobId;
    this.runParametersDTO = runParametersDTO;
    this.timeout = timeout;
    this.checkInterval = checkInterval;
  }

  /**
   * @return throws DatabricksRestException if job do not succeed.
   */
  public RunResultStateDTO process()
      throws IOException, DatabricksRestException, InterruptedException {

    // run job
    launchJob();

    // check job result
    RunStateDTO runStateDTO = waitForJobFinished();
    RunResultStateDTO resultState = runStateDTO.getResultState();
    switch (resultState) {
      case SUCCESS:
        log.info(
            "Job[=" + jobId + "] finished successfully. '" + resultState.name() + "' " + runStateDTO
                .getStateMessage());
        break;
      case FAILED:
      case TIMEDOUT:
      case CANCELED:
        throw new DatabricksRestException("Job[=" + jobId + "] error '" + resultState.name() + "'");
      default:
        throw new DatabricksRestException(
            "Job[=" + jobId + "] error by unknown '" + resultState.name() + "'");

    }

    return resultState;
  }


  public RunNowDTO launchJob() throws IOException, DatabricksRestException {
    RunNowDTO runNowDTO = service.runJobNow(jobId, runParametersDTO);
    runId = runNowDTO.getRunId();
    numberInJob = runNowDTO.getNumberInJob();
    log.info(
        "RunJob run-id=" + runId + ", [" + service.buildRunJobRestUrl(jobId, numberInJob) + "]");

    return runNowDTO;
  }


  private RunStateDTO waitForJobFinished()
      throws IOException, InterruptedException, DatabricksRestException {
    long elapsed = 0;

    while (elapsed < timeout) {
      RunDTO runDTO = getRunDTO();
      RunStateDTO runStateDTO = runDTO.getState();
      RunLifeCycleStateDTO lifeCycleState = runStateDTO.getLifeCycleState();

      switch (lifeCycleState) {
        case PENDING:
        case RUNNING:
        case TERMINATING:
          log.info(
              "Sleep for " + (checkInterval / 1000) + " secs. Job lifeCycleState '" + lifeCycleState
                  + "'");
          Thread.sleep(checkInterval);
          elapsed += checkInterval;
          continue;

        case SKIPPED:
          throw new DatabricksRestException(
              "Job lifeCycleState '" + lifeCycleState + "'. " + runStateDTO.getStateMessage());

        default:
          return runStateDTO;
      }
    } // eof while

    // cancel running job
    log.info("Job did not finished expected " + timeout / 1000 + "secs. Cancel run_id=" + runId);
    cancelJob();

    throw new DatabricksRestException("Job canceled due to timeout " + timeout / 1000 + "secs");
  }


  public RunDTO getRunDTO() throws IOException, DatabricksRestException {
    return service.getRun(runId);
  }

  public void cancelJob() throws IOException, DatabricksRestException {
    service.cancelRun(runId);
  }

  public long getJobId() {
    return jobId;
  }

  public long getRunId() {
    return runId;
  }

  public long getNumberInJob() {
    return numberInJob;
  }

  public long getTimeout() {
    return timeout;
  }

  public long getCheckInterval() {
    return checkInterval;
  }
}
