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
import com.edmunds.rest.databricks.DTO.RunResultStateDTO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.Thread.sleep;


/**
 *
 */
public class RunJobs {


  private static Logger log = Logger.getLogger(RunJobs.class);

  private RunJob[] runJobs;
  private long timeout;
  private long checkInterval;

  public RunJobs(RunJob[] runJobs, long timeout, long checkInterval) {
    this.runJobs = runJobs;
    this.timeout = timeout;
    this.checkInterval = checkInterval;
  }


  public List<Map.Entry<RunJob, Exception>> process() throws InterruptedException {

    List<RunJob> runningJobs = new ArrayList<>(runJobs.length);
    List<Map.Entry<RunJob, Exception>> failedJobs = new ArrayList<>();

    for (RunJob runJob : runJobs) {
      try {
        runJob.launchJob();
        runningJobs.add(runJob);
      } catch (IOException | DatabricksRestException e) {
        failedJobs.add(new AbstractMap.SimpleEntry(runJob, e));
      }
    }

    long elapsed = 0L;
    while (!runningJobs.isEmpty()) {
      sleep(checkInterval);
      elapsed += checkInterval;
      Iterator<RunJob> runningJobsIterator = runningJobs.iterator();
      while (runningJobsIterator.hasNext()) {
        RunJob currentJob = runningJobsIterator.next();
        try {
          RunDTO runDTO = currentJob.getRunDTO();

          if (checkJobIsFinished(runDTO)) {
            log.info("Job[=" + currentJob.getJobId() + "] Finished");
            runningJobsIterator.remove();
          } else {
            log.info("Job[=" + currentJob.getJobId() + "] Still Running");
          }
        } catch (IOException | DatabricksRestException e) {
          log.error("Job[=" + currentJob.getJobId() + "] failed", e);

          failedJobs.add(new AbstractMap.SimpleEntry(currentJob, e));
          runningJobsIterator.remove();
        }
      }
      if (elapsed > timeout) {
        for (RunJob runningJob : runningJobs) {
          try {
            runningJob.cancelJob();
          } catch (IOException | DatabricksRestException e) {
            log.error("Failed to cancel Job[=" + runningJob.getJobId() + "]", e);

          } finally {
            failedJobs.add(new AbstractMap.SimpleEntry(runningJob,
                new DatabricksRestException("Job canceled due to timeout " + timeout / 1000 + "secs")));
          }
        }
        runningJobs.clear();
      }

    }

    return failedJobs;
  }


  private boolean checkJobIsFinished(RunDTO run) throws DatabricksRestException {
    RunLifeCycleStateDTO runLifeCycleState = run.getState().getLifeCycleState();

    if (Objects.equals(runLifeCycleState, RunLifeCycleStateDTO.TERMINATED) ||
        Objects.equals(runLifeCycleState, RunLifeCycleStateDTO.SKIPPED) ||
        Objects.equals(runLifeCycleState, RunLifeCycleStateDTO.INTERNAL_ERROR)) {

      RunResultStateDTO runResultState = run.getState().getResultState();
      if (runResultState != RunResultStateDTO.SUCCESS) {
        throw new DatabricksRestException("Run was terminated with state: " + runResultState);
      }

      return true;
    }

    return false;
  }


}
