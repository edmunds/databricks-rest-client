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
 *
 */
public interface JobService {

  /**
   * Creates job from settings DTO.
   *
   * @param jobSettingsDTO
   * @return
   * @throws IOException
   * @throws DatabricksRestException
   */
  long createJob(JobSettingsDTO jobSettingsDTO) throws IOException, DatabricksRestException;

  /**
   * Deletes the job and sends an email to the addresses specified in email_notifications.
   *
   * @param jobId The canonical identifier of the job to delete
   * @throws DatabricksRestException
   */
  void deleteJob(long jobId) throws IOException, DatabricksRestException;

  /**
   * Deletes a job with a given name.
   * Will fail if multiple jobs exist.
   *
   * @param jobName - the job name to delete.
   * @throws IOException
   * @throws DatabricksRestException
   */
  void deleteJob(String jobName) throws IOException, DatabricksRestException;

  /**
   * Retrieves information about a single job.
   *
   * @param jobId The canonical identifier of the job to retrieve information about
   * @return POJO of the Job information
   * @throws IOException
   * @throws DatabricksRestException
   */
  JobDTO getJob(long jobId) throws IOException, DatabricksRestException;

  /**
   * Retrieves all jobs matching a name exactly.
   *
   * @param jobName - the full name of the job
   * @return - all jobs that match.
   * @throws IOException
   * @throws DatabricksRestException
   */
  List<JobDTO> getJobsByName(String jobName) throws IOException, DatabricksRestException;

  /**
   * Will try and get a job by name.
   * If more then one job exists, it will fail.
   * If no job exists it will return null.
   *
   * @throws IOException
   * @throws DatabricksRestException
   */
  JobDTO getJobByName(String jobName) throws IOException, DatabricksRestException;

  /**
   * Will try and get a job by name.
   * If more then one job exists and failOnMultipleJobs set to false, it will return the first job created.
   */
  JobDTO getJobByName(String jobName, boolean failOnMultipleJobs)
      throws IOException, DatabricksRestException;


  /**
   * Retrieves all jobs with a name matching a given regex.
   *
   * @param regex - the regex to earch for
   * @return
   * @throws IOException
   * @throws DatabricksRestException
   */
  List<JobDTO> getJobsByRegex(Pattern regex) throws IOException, DatabricksRestException;

  /**
   * Returns a list of all jobs that are active.
   *
   * @return A POJO of the Jobs
   * @throws DatabricksRestException
   * @throws IOException
   */
  JobsDTO listAllJobs() throws IOException, DatabricksRestException;

  /**
   * Produces the URL of a job given job id.
   *
   * @param jobId The canonical identifier of the job to retrieve information about
   * @return URL of the job
   */
  String getJobLink(long jobId);

  /**
   * Runs the job now.
   *
   * @param jobId The job to run
   * @return Returns the run_id and number_in_run of the triggered run
   * @throws DatabricksRestException
   * @throws IOException
   */
  RunNowDTO runJobNow(long jobId) throws DatabricksRestException, IOException;

  /**
   * Runs the job now.
   *
   * @param jobId          The job to run
   * @param notebookParams A map from keys to values for jobs with notebook task
   * @return Returns the run_id and number_in_run of the triggered run
   * @throws DatabricksRestException
   * @throws IOException
   */
  RunNowDTO runJobNow(long jobId, Map<String, String> notebookParams)
      throws DatabricksRestException, IOException;

  RunNowDTO runJobNow(long jobId, RunParametersDTO params)
      throws DatabricksRestException, IOException;

  /**
   * Lists runs from most recently started to least.
   *
   * @param jobId      The job for which to list runs. If omitted, the Jobs service will list runs from all jobs
   * @param activeOnly If true, lists active runs only; otherwise, lists both active and inactive runs
   * @param offset     The offset of the first run to return, relative to the most recent run. The default value is 20
   * @param limit      The number of runs to return. This value should be greater than 0 and less than 1000
   * @return Returns RunsDTO containing an array of runs and a boolean indicating if there are more jobs that haven't been included
   * @throws DatabricksRestException
   * @throws IOException
   */
  RunsDTO listRuns(Long jobId, Boolean activeOnly, Integer offset, Integer limit)
      throws DatabricksRestException,
             IOException;

  /**
   * Retrieves the metadata of a run.
   *
   * @param runId The canonical identifier of the run for which to retrieve the metadata
   * @return Returns the metadata of the specified run
   * @throws DatabricksRestException
   * @throws IOException
   */
  RunDTO getRun(long runId) throws DatabricksRestException, IOException;

  /**
   * Cancels a run. The run is canceled asynchronously, so when this request completes, the run may still be running.
   * The run will be terminated shortly.
   *
   * @param runId The desired run to cancel
   * @throws DatabricksRestException
   */
  void cancelRun(long runId) throws IOException, DatabricksRestException;

  void reset(long jobId, JobSettingsDTO jobSettings) throws IOException, DatabricksRestException;


  /**
   * Formatting databricks job rest url.
   * ex> https://something.cloud.databricks.com/#job/123/run/123
   *
   * @param jobId
   * @param numberInJob
   * @return
   */
  String buildRunJobRestUrl(long jobId, long numberInJob);

  /**
   * Given a job settings DTO object it will:
   * - try to find the id by name. If multiple exist, it will fail if the duplicate job name flag is set. Else it
   * will update all jobs found.
   * - create the job if it doesn't exist
   * - reset the job if it does exist.
   *
   * @param jobSettingsDTO
   */
  void upsertJob(JobSettingsDTO jobSettingsDTO, boolean failOnDuplicateJobNames)
      throws IOException, DatabricksRestException;

}
