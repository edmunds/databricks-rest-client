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

import com.edmunds.rest.databricks.DTO.JobsDTOv21;
import com.edmunds.rest.databricks.DTO.RunMetadataDTO;
import com.edmunds.rest.databricks.DTO.RunNowDTO;
import com.edmunds.rest.databricks.DTO.RunsDTO;
import com.edmunds.rest.databricks.DTO.jobs.JobDTOv21;
import com.edmunds.rest.databricks.DTO.jobs.JobSettingsDTOv21;
import com.edmunds.rest.databricks.DTO.jobs.RunDTO;
import com.edmunds.rest.databricks.DTO.jobs.RunParametersDTOv21;
import com.edmunds.rest.databricks.DTO.jobs.RunSubmitDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The implementation of JobService.
 */
public class JobServiceImplV21 extends DatabricksService implements JobServiceV21 {

  private static Logger log = LogManager.getLogger(JobServiceImplV21.class);

  public JobServiceImplV21(final DatabricksRestClient client) {
    super(client);
  }

  @Override
  public long createJob(JobSettingsDTOv21 jobSettingsDTO) throws IOException, DatabricksRestException {
    String marshalled = this.mapper.writeValueAsString(jobSettingsDTO);
    Map<String, Object> data = this.mapper.readValue(marshalled, new
        TypeReference<Map<String, Object>>() {
        });
    byte[] responseBody = client.performQuery(RequestMethod.POST, "/jobs/create", data);
    Map<String, Long> response = this.mapper
        .readValue(responseBody, new TypeReference<Map<String, Long>>() {
        });
    return response.get("job_id");
  }

  @Override
  public void deleteJob(long jobId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("job_id", jobId);
    client.performQuery(RequestMethod.POST, "/jobs/delete", data);
  }

  @Override
  public void deleteJob(String jobName) throws IOException, DatabricksRestException {
    JobDTOv21 jobDTO = getJobByName(jobName);
    if (jobDTO != null) {
      log.info("Deleting: " + getJobLink(jobDTO.getJobId()));
      deleteJob(jobDTO.getJobId());
    } else {
      log.info("No jobs found for " + jobName);
    }
  }

  @Override
  public JobDTOv21 getJob(long jobId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("job_id", jobId);
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/jobs/get", data);
    return this.mapper.readValue(responseBody, JobDTOv21.class);
  }

  @Override
  public List<JobDTOv21> getJobsByName(String jobName) throws IOException, DatabricksRestException {
    return getJobsByRegex(Pattern.compile(jobName));
  }

  @Override
  public JobDTOv21 getJobByName(String jobName) throws IOException, DatabricksRestException {
    return getJobByName(jobName, true);
  }

  @Override
  public JobDTOv21 getJobByName(String jobName, boolean failOnMultipleJobs)
      throws IOException, DatabricksRestException {
    List<JobDTOv21> jobs = getJobsByName(jobName);
    if (jobs.size() > 1) {
      String errorMessage = String
          .format("[%s] job ids found for name: [%s]. Please delete duplicate jobs, or "
              + "renaming conflicting jobs:\n%s\n", jobs.size(), jobName, jobs);
      if (failOnMultipleJobs) {
        throw new IllegalStateException(errorMessage);
      } else {
        log.error(errorMessage);
        log.error("returning the job with the lowest jobId");
        return jobs.stream().min(Comparator.comparing(JobDTOv21::getJobId)).get();
      }
    }
    if (jobs.isEmpty()) {
      return null;
    }
    return jobs.get(0);
  }

  @Override
  public List<JobDTOv21> getJobsByRegex(Pattern regex) throws IOException, DatabricksRestException {
    if (regex == null) {
      throw new IllegalArgumentException("Job name must not be blank.");
    }

    return listAllJobs().stream()
            .filter(x -> regex.matcher(x.getSettings().getName()).matches())
            .collect(Collectors.toList());
  }

  @Override
  public List<JobDTOv21> listAllJobs() throws DatabricksRestException, IOException {
    int limit = 25;
    int offset = 0;

    List<JobDTOv21> result = new ArrayList<>();
    JobsDTOv21 jobsDTO;

    do {
      jobsDTO = listJobs(limit, offset, true);
      offset += limit;
      result.addAll(Arrays.asList(jobsDTO.getJobs()));
    } while (jobsDTO.isHasMore());

    return result;
  }

  @Override
  public JobsDTOv21 listJobs(int limit, int offset, boolean expandTasks) throws DatabricksRestException, IOException {
    Map<String, Object> data = new HashMap<>();

    data.put("limit", String.valueOf(limit));
    data.put("offset", String.valueOf(offset));
    data.put("expand_tasks", String.valueOf(expandTasks));

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/jobs/list", data);
    return this.mapper.readValue(responseBody, JobsDTOv21.class);
  }

  @Override
  public String getJobLink(long jobId) {
    return String.format("https://%s/#job/%s", client.getHost(), jobId);
  }

  @Override
  public RunNowDTO runJobNow(long jobId) throws DatabricksRestException, IOException {
    return runJobNow(jobId, (RunParametersDTOv21) null);
  }

  @Override
  public RunNowDTO runJobNow(long jobId, Map<String, String> notebookParams)
      throws DatabricksRestException,
      IOException {
    RunParametersDTOv21 parametersDTO = new RunParametersDTOv21();
    parametersDTO.setNotebookParams(notebookParams);
    return runJobNow(jobId, parametersDTO);
  }

  //CHECKSTYLE:OFF
  @Override
  public RunNowDTO runJobNow(long jobId, RunParametersDTOv21 params) throws DatabricksRestException,
      IOException {
    Map<String, Object> data = new HashMap<>();
    data.put("job_id", jobId);
    if (params == null) {
      // skip param setting

    } else if (params.getJarParams() != null) {
      data.put("jar_params", params.getJarParams());

    } else if (params.getNotebookParams() != null) {
      data.put("notebook_params", params.getNotebookParams());

    } else if (params.getPythonParams() != null) {
      data.put("python_params", params.getPythonParams());

    } else if (params.getSparkSubmitParams() != null) {
      data.put("spark_submit_params", params.getSparkSubmitParams());

    }

    byte[] responseBody = client.performQuery(RequestMethod.POST, "/jobs/run-now", data);
    return this.mapper.readValue(responseBody, RunNowDTO.class);
  }


  @Override
  public RunsDTO listRuns(Long jobId, Boolean activeOnly, Integer offset, Integer limit) throws
      DatabricksRestException, IOException {
    Map<String, Object> data = new HashMap<>();

    if (jobId != null) {
      data.put("job_id", jobId);
    }
    if (activeOnly != null) {
      data.put("active_only", activeOnly);
    }
    //TODO we need a way to do completed_only
    if (offset != null) {
      data.put("offset", offset);
    }
    if (limit != null) {
      data.put("limit", limit);
    }

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/jobs/runs/list", data);
    return this.mapper.readValue(responseBody, RunsDTO.class);
  }

  @Override
  public RunDTO getRun(long runId) throws DatabricksRestException, IOException {
    Map<String, Object> data = new HashMap<>();
    data.put("run_id", runId);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/jobs/runs/get", data);
    return this.mapper.readValue(responseBody, RunDTO.class);
  }

  @Override
  public void cancelRun(long runId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("run_id", runId);

    client.performQuery(RequestMethod.POST, "/jobs/runs/cancel", data);
  }

  @Override
  public void reset(long jobId, JobSettingsDTOv21 jobSettings)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("job_id", jobId);
    data.put("new_settings", jobSettings);

    client.performQuery(RequestMethod.POST, "/jobs/reset", data);
  }

  @Override
  public String buildRunJobRestUrl(long jobId, long numberInJob) {
    return "https://" + client.getHost() + "/#job/" + jobId + "/run/" + numberInJob;
  }

  @Override
  public void upsertJob(JobSettingsDTOv21 jobSettingsDTO, boolean failOnDuplicateJobNames)
      throws IOException, DatabricksRestException {
    String jobName = jobSettingsDTO.getName();
    List<JobDTOv21> jobs = getJobsByName(jobName);
    if (jobs.size() > 1) {
      String errorMessage = String.format(
          "[%s] job ids found for name: [%s]. Please consider deleting duplicate jobs, "
              + "or renaming "
              + "conflicting jobs:\n%s\n",
          jobs.size(), jobName, jobs);

      if (failOnDuplicateJobNames) {
        throw new IllegalArgumentException(errorMessage);
      } else {
        log.error(errorMessage + "UPDATING FIRST JOB ONLY");
      }
    }
    if (jobs.size() == 0) {
      long newJobId = createJob(jobSettingsDTO);
      log.info(String.format("Created job, url: %s", getJobLink(newJobId)));
    } else {
      for (JobDTOv21 job : jobs) {
        reset(job.getJobId(), jobSettingsDTO);
        log.info(String.format("Updated job, url: %s", getJobLink(job.getJobId())));
      }
    }
  }

  @Override
  public RunNowDTO runSubmit(RunSubmitDTO runSettings) throws IOException, DatabricksRestException {
    String marshalled = this.mapper.writeValueAsString(runSettings);
    Map<String, Object> data = this.mapper.readValue(marshalled, new
        TypeReference<Map<String, Object>>() {
        });
    byte[] responseBody = client.performQuery(RequestMethod.POST, "/jobs/runs/submit", data);
    return this.mapper.readValue(responseBody, RunNowDTO.class);
  }

  @Override
  public String getOutput(long runId)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("run_id", runId);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/jobs/runs/get-output", data);
    RunMetadataDTO metadata = this.mapper.readValue(responseBody, RunMetadataDTO.class);
    return metadata.getNotebookOutput().getResult();
  }
}
