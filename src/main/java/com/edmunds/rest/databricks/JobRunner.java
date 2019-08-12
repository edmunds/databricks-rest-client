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

import com.edmunds.rest.databricks.DTO.JobDTO;
import com.edmunds.rest.databricks.DTO.RunParametersDTO;
import com.edmunds.rest.databricks.service.JobService;
import java.io.IOException;
import org.apache.commons.cli.ParseException;


/**
 * A tool to launch and monitor Databricks Jobs.
 */
public class JobRunner {

  private JobService service;
  private JobRunnerCliParser parser;

  /**
   * JobRunner from arguments.
   */
  public JobRunner(String... args) throws ParseException {
    parser = new JobRunnerCliParser();
    parser.parse(args);

    if (parser.hasHelp()) {
      parser.printHelp();
    }

    service = getService();
  }

  public static void main(String[] args)
      throws IOException, InterruptedException, DatabricksRestException, ParseException {
    JobRunner main = new JobRunner(args);
    main.runJob();
  }

  /**
   * Runs a job on databricks.
   * Will monitor its status.
   * @throws IOException other errors
   * @throws DatabricksRestException any databricks server errors including if the job failed.
   * @throws InterruptedException thread interruption
   */
  public void runJob() throws IOException, DatabricksRestException, InterruptedException {
    long jobId = getJobId();

    RunParametersDTO runParametersDTO = new RunParametersDTO();
    if (parser.hasJobJarParams()) {
      runParametersDTO.setJarParams(parser.getJarParams());
    } else if (parser.hasJobNotebookParams()) {
      runParametersDTO.setNotebookParams(parser.getNotebookParams());
    } else if (parser.hasJobSaprkSubmitParams()) {
      runParametersDTO.setSparkSubmitParams(parser.getSparkSubmitParams());
    } else if (parser.hasJobPythonParams()) {
      runParametersDTO.setPythonParams(parser.getPythonParams());
    }

    RunJob runJob = new RunJob(service, jobId, runParametersDTO, parser.getJobTimeout(),
        parser.getJobCheckInterval());
    runJob.process();

  }

  private JobService getService() {
    String username = parser.getUsername();
    String password = parser.getPassword();
    String token = parser.getToken();
    String hostname = parser.getHostname();

    DatabricksServiceFactory factory = null;
    if (token != null) {
      factory = DatabricksServiceFactory.Builder.createTokenAuthentication(token, hostname)
          .build();
    } else {
      DatabricksServiceFactory.Builder
          .createUserPasswordAuthentication(username, password, hostname)
          .build();
    }
    return factory.getJobService();
  }

  private long getJobId() throws IOException, DatabricksRestException {
    if (parser.hasJobId()) {
      return parser.getJobId();
    }

    return getJobId(parser.getJobName());
  }

  private long getJobId(String targetJobName) throws IOException, DatabricksRestException {
    JobDTO job = service.getJobByName(targetJobName);
    if (job == null) {
      throw new IllegalArgumentException("Job '" + targetJobName + "' not found");
    }

    return job.getJobId();
  }

}
