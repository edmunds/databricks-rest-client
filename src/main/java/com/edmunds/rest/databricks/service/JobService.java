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

package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.JobDTO;
import com.edmunds.rest.databricks.DTO.JobSettingsDTO;
import com.edmunds.rest.databricks.DTO.JobsDTO;
import com.edmunds.rest.databricks.DTO.RunDTO;
import com.edmunds.rest.databricks.DTO.RunNowDTO;
import com.edmunds.rest.databricks.DTO.RunParametersDTO;
import com.edmunds.rest.databricks.DTO.RunsDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A wrapper around the Databricks Job API.
 * @see <a href="https://docs.databricks.com/api/latest/jobs.html">https://docs.databricks.com/api/latest/jobs.html</a>
 */
public interface JobService {

  /**
   * Creates job from settings DTO.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#create">https://docs.databricks.com/api/latest/jobs.html#create</a>
   */
  long createJob(JobSettingsDTO jobSettingsDTO) throws IOException, DatabricksRestException;

  /**
   * Deletes the job and sends an email to the addresses specified in email_notifications.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#delete">https://docs.databricks.com/api/latest/jobs.html#delete</a>
   * @param jobId The canonical identifier of the job to delete
   */
  void deleteJob(long jobId) throws IOException, DatabricksRestException;

  /**
   * Deletes a job with a given name. Will fail if multiple jobs exist.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#delete">https://docs.databricks.com/api/latest/jobs.html#delete</a>
   * @param jobName - the job name to delete.
   */
  void deleteJob(String jobName) throws IOException, DatabricksRestException;

  /**
   * Retrieves information about a single job.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#get">https://docs.databricks.com/api/latest/jobs.html#get</a>
   * @param jobId The canonical identifier of the job to retrieve information about
   * @return POJO of the Job information
   */
  JobDTO getJob(long jobId) throws IOException, DatabricksRestException;

  /**
   * Retrieves all jobs matching a name exactly.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#list">https://docs.databricks.com/api/latest/jobs.html#list</a>
   * @param jobName - the full name of the job
   * @return - all jobs that match.
   */
  List<JobDTO> getJobsByName(String jobName) throws IOException, DatabricksRestException;

  /**
   * Will try and get a job by name. If more then one job exists, it will fail. If no job exists it
   * will return null.
   * Uses a list behind the scenes, so is O(n) with number of jobs.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#list">https://docs.databricks.com/api/latest/jobs.html#list</a>
   */
  JobDTO getJobByName(String jobName) throws IOException, DatabricksRestException;

  /**
   * Will try and get a job by name. If more then one job exists and failOnMultipleJobs set to
   * false, it will return the first job created.
   * Uses a list behind the scenes, so is O(n) with number of jobs.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#list">https://docs.databricks.com/api/latest/jobs.html#list</a>
   */
  JobDTO getJobByName(String jobName, boolean failOnMultipleJobs)
      throws IOException, DatabricksRestException;


  /**
   * Retrieves all jobs with a name matching a given regex.
   * Uses a list behind the scenes, so is O(n) with number of jobs.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#list">https://docs.databricks.com/api/latest/jobs.html#list</a>
   * @param regex - the regex to earch for
   */
  List<JobDTO> getJobsByRegex(Pattern regex) throws IOException, DatabricksRestException;

  /**
   * Returns a list of all jobs that are active.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#list">https://docs.databricks.com/api/latest/jobs.html#list</a>
   * @return A POJO of the Jobs
   */
  JobsDTO listAllJobs() throws IOException, DatabricksRestException;

  /**
   * Produces the URL of a job given job id.
   * @param jobId The canonical identifier of the job to retrieve information about
   * @return URL of the job
   */
  String getJobLink(long jobId);

  /**
   * Runs the job now.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#run-now">https://docs.databricks.com/api/latest/jobs.html#run-now</a>
   * @param jobId The job to run
   * @return Returns the run_id and number_in_run of the triggered run
   */
  RunNowDTO runJobNow(long jobId) throws DatabricksRestException, IOException;

  /**
   * Runs the job now. With specific notebook params.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#run-now">https://docs.databricks.com/api/latest/jobs.html#run-now</a>
   * @param jobId The job to run
   * @param notebookParams A map from keys to values for jobs with notebook task
   * @return Returns the run_id and number_in_run of the triggered run
   */
  RunNowDTO runJobNow(long jobId, Map<String, String> notebookParams)
      throws DatabricksRestException, IOException;

  /**
   * Runs the job now. With specific run parameters.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#run-now">https://docs.databricks.com/api/latest/jobs.html#run-now</a>
   * @param jobId the job id to run
   * @param params the run parameters
   * @return the run now object
   * @throws DatabricksRestException any db specific exceptions
   * @throws IOException any other exceptions
   */
  RunNowDTO runJobNow(long jobId, RunParametersDTO params)
      throws DatabricksRestException, IOException;

  /**
   * Lists runs from most recently started to least.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#runs-list">https://docs.databricks.com/api/latest/jobs.html#runs-list</a>
   * @param jobId The job for which to list runs. If omitted,
   *     the Jobs service will list runs from all jobs
   * @param activeOnly If true, lists active runs only; otherwise, lists both active and inactive
   *     runs
   * @param offset The offset of the first run to return, relative to the most recent run. The
   *     default value is 20
   * @param limit The number of runs to return. This value should be greater than 0 and less than
   *     1000
   * @return Returns RunsDTO containing an array of runs and a boolean indicating if there are more
   *     jobs that haven't been included
   */
  RunsDTO listRuns(Long jobId, Boolean activeOnly, Integer offset, Integer limit)
      throws DatabricksRestException,
      IOException;

  /**
   * Retrieves the metadata of a run.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#runs-get">https://docs.databricks.com/api/latest/jobs.html#runs-get</a>
   * @param runId The canonical identifier of the run for which to retrieve the metadata
   * @return Returns the metadata of the specified run
   */
  RunDTO getRun(long runId) throws DatabricksRestException, IOException;

  /**
   * Cancels a run. The run is canceled asynchronously, so when this request completes, the run may
   * still be running. The run will be terminated shortly.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#runs-cancel">https://docs.databricks.com/api/latest/jobs.html#runs-cancel</a>
   * @param runId The desired run to cancel
   */
  void cancelRun(long runId) throws IOException, DatabricksRestException;

  /**
   * "resets" or "edits" a job definition.
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#reset">https://docs.databricks.com/api/latest/jobs.html#reset</a>
   * @param jobId the job to edit
   * @param jobSettings the settings to change the job to
   * @throws IOException any other errors
   * @throws DatabricksRestException any specific db errors
   */
  void reset(long jobId, JobSettingsDTO jobSettings) throws IOException, DatabricksRestException;


  /**
   * Formatting databricks job rest url. ex> https://something.cloud.databricks.com/#job/123/run/123
   */
  String buildRunJobRestUrl(long jobId, long numberInJob);

  /**
   * Given a job settings DTO object it will: - try to find the id by name. If multiple exist, it
   * will fail if the duplicate job name flag is set. Else it will update all jobs found. - create
   * the job if it doesn't exist - reset the job if it does exist.
   * Uses a combination of
   * If job doesn't exist:
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#create">https://docs.databricks.com/api/latest/jobs.html#create</a>
   * If job exists:
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#reset">https://docs.databricks.com/api/latest/jobs.html#reset</a>
   */
  void upsertJob(JobSettingsDTO jobSettingsDTO, boolean failOnDuplicateJobNames)
      throws IOException, DatabricksRestException;

  /**
   * Submit a one-time run. This endpoint doesn’t require a Databricks job to be created
   *
   * @param jobSettings the settings to change the job to
   * @return Returns the run_id of the triggered run
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#runs-submit">https://docs.databricks.com/api/latest/jobs.html#runs-submit</a>
   */
  RunNowDTO runSubmit(JobSettingsDTO jobSettings) throws IOException, DatabricksRestException;

  /**
   * Retrieve the output of a run
   *
   * @param runId The desired run id
   * @return Returns the data output of the specified run
   * @see <a href="https://docs.databricks.com/api/latest/jobs.html#runs-get-output">https://docs.databricks.com/api/latest/jobs.html#runs-get-output</a>
   */
  String getOutput(long runId) throws IOException, DatabricksRestException;
}
