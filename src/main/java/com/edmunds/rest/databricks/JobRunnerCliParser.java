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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 */
public class JobRunnerCliParser {

  private static final String SERVICE_NAME = "Run Job";
  private static final String HELP_NAME = "help";
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String HOSTNAME = "hostname";
  private static final String JOB_ID = "job_id";
  private static final String JOB_NAME = "name";
  private static final String JOB_JAR_PARAMS = "jarParams";
  private static final String JOB_NOTEBOOK_PARAMS = "notebookParams";
  private static final String JOB_SPARK_SUBMIT_PARAMS = "sparkSubmitParams";
  private static final String JOB_PYTHON_PARAMS = "pythonParams";
  private static final String JOB_TIMEOUT = "timeout"; // job timeout milliseconds
  private static final String JOB_CHECK_INTERVAL = "checkInterval"; // job check interval milliseconds

  // Use databricks job timeout setting. So, set 1 day for safety.
  private static final long DEFAULT_JOB_TIMEOUT = TimeUnit.DAYS.toMillis(1);
  private static final long DEFAULT_JOB_CHECK_INTERVAL = TimeUnit.MINUTES.toMillis(2);

  private Options options;
  private CommandLineParser parser = new BasicParser();
  private HelpFormatter formatter = new HelpFormatter();
  private CommandLine commandLine;

  public void parse(String... cliArgs) throws ParseException {
    if (options == null) {
      options = makeOptions();
    }

    cliArgs = splitCliArguments(cliArgs);

    commandLine = parser.parse(options, cliArgs);

    if (this.hasHelp()) {
      return;
    }

    if (!hasValidArguments()) {
      throw new IllegalArgumentException("Arguments must include -u -p -h and (-j or -n) options");
    }
  }

  private String[] splitCliArguments(String[] args) {
    ArrayList<String> toReturn = new ArrayList<>();

    for (String arg : args) {
      Collections.addAll(toReturn, arg.trim().split("\\s+", 2));
    }

    return toReturn.toArray(new String[0]);
  }

  public String getUsername() {
    return getOptionValue(USERNAME);
  }

  public String getPassword() {
    return getOptionValue(PASSWORD);
  }

  public String getHostname() {
    return getOptionValue(HOSTNAME);
  }

  public boolean hasJobId() {
    return commandLine.hasOption(JOB_ID);
  }

  public int getJobId() {
    return Integer.parseInt(getOptionValue(JOB_ID));
  }

  public String getJobName() {
    return getOptionValue(JOB_NAME);
  }

  public boolean hasHelp() {
    return commandLine.hasOption(HELP_NAME);
  }

  public void printHelp() {
    formatter.printHelp(SERVICE_NAME, options);
  }


  public boolean hasJobJarParams() {
    return commandLine.hasOption(JOB_JAR_PARAMS);
  }


  public String[] getJarParams() {
    if (hasJobJarParams()) {
      return commandLine.getOptionValues(JOB_JAR_PARAMS);
    }

    return new String[]{};
  }

  public boolean hasJobNotebookParams() {
    return commandLine.hasOption(JOB_NOTEBOOK_PARAMS);
  }

  public Map<String, String> getNotebookParams() {
    Map<String, String> notebookParams = new HashMap<>();

    if (hasJobNotebookParams()) {
      String[] args = commandLine.getOptionValues(JOB_NOTEBOOK_PARAMS);

      if (args.length % 2 != 0) {
        throw new IllegalArgumentException(
            "Notebook parameters must be a multiple of 2 for key/value pairs.");
      }

      for (int i = 0; i < args.length; i += 2) {
        notebookParams.put(args[i], args[i + 1]);
      }

    }

    return notebookParams;
  }

  public boolean hasJobPythonParams() {
    return commandLine.hasOption(JOB_PYTHON_PARAMS);
  }

  public String[] getPythonParams() {
    if (hasJobPythonParams()) {
      return commandLine.getOptionValues(JOB_PYTHON_PARAMS);
    }

    return new String[]{};
  }

  public boolean hasJobSaprkSubmitParams() {
    return commandLine.hasOption(JOB_SPARK_SUBMIT_PARAMS);
  }

  public String[] getSparkSubmitParams() {
    if (hasJobSaprkSubmitParams()) {
      return commandLine.getOptionValues(JOB_SPARK_SUBMIT_PARAMS);
    }

    return new String[]{};
  }

  public long getJobTimeout() {
    if (commandLine.hasOption(JOB_TIMEOUT)) {
      return Long.parseLong(commandLine.getOptionValue(JOB_TIMEOUT));
    }

    return DEFAULT_JOB_TIMEOUT;
  }

  public long getJobCheckInterval() {
    if (commandLine.hasOption(JOB_CHECK_INTERVAL)) {
      return Long.parseLong(commandLine.getOptionValue(JOB_CHECK_INTERVAL));
    }

    return DEFAULT_JOB_CHECK_INTERVAL;
  }

  private Options makeOptions() {
    Options opts = new Options();

    opts.addOption(HELP_NAME, false, "print this message");
    opts.addOption("u", USERNAME, true, "databricks login username");
    opts.addOption("p", PASSWORD, true, "databricks login password");
    opts.addOption("h", HOSTNAME, true, "databricks hostname");
    opts.addOption("j", JOB_ID, true, "integer job id to run");
    opts.addOption("n", JOB_NAME, true, "name of databricks job");
    opts.addOption("pj", JOB_JAR_PARAMS, false, "parameters for jar spark");
    opts.addOption("pn", JOB_NOTEBOOK_PARAMS, false, "parameters for notebook");
    opts.addOption("ps", JOB_SPARK_SUBMIT_PARAMS, false, "parameters for spark-submit");
    opts.addOption("py", JOB_PYTHON_PARAMS, false, "parameters for python");
    opts.addOption("to", JOB_TIMEOUT, false, "job timeout milliseconds");
    opts.addOption("ci", JOB_CHECK_INTERVAL, false, "job check interval milliseconds");

    return opts;
  }

  private String getOptionValue(String optionValue) {
    return commandLine.getOptionValue(optionValue).trim();
  }

  private boolean hasValidArguments() {
    return commandLine.hasOption(USERNAME)
        && commandLine.hasOption(PASSWORD)
        && commandLine.hasOption(HOSTNAME)
        && (commandLine.hasOption(JOB_ID) || commandLine.hasOption(JOB_NAME));
  }
}
