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

import org.apache.commons.cli.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Objects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by shong on 8/4/16.
 */
public class RunJobCliParserTest {
    private final static String USERNAME = "test_user";
    private final static String PASSWORD = "test_password";
    private final static String HOSTNAME = "test_hostname";
    private final static int JOBID = 1;
    private final static String JOBNAME = "test_jobname";
    private JobRunnerCliParser parser;
    private String[] fullArgumentsWithJobName = new String[] {
        "-u " + USERNAME,
        "-p " + PASSWORD,
        "-h " + HOSTNAME,
        "-n " + JOBNAME
    };

    private String[] fullArgumentsWithSpaces = new String[] {
        "-u " + USERNAME,
        "-p " + PASSWORD,
        "-h " + HOSTNAME,
        "-j " + JOBID
    };

    private String[] fullArgumentsWithoutSpaces = new String[] {
        "-u",
        USERNAME,
        "-p",
        PASSWORD,
        "-h",
        HOSTNAME,
        "-j",
        String.valueOf(JOBID)
    };

    private String[] argumentsWithMissingJob = new String[] {
        "-u " + USERNAME,
        "-p " + PASSWORD,
        "-h " + HOSTNAME
    };

    @BeforeClass
    public void setUpOnce() {
        parser = new JobRunnerCliParser();
    }

    @Test
    public void parse_withArgumentsWithSpaces_parsesCorrectly() throws ParseException {
        parser.parse(fullArgumentsWithSpaces);

        assertTrue(isParsedCorrectly(parser));
    }

    @Test
    public void parse_withArgumentsWithoutSpaces_parsesCorrectly() throws ParseException {
        parser.parse(fullArgumentsWithoutSpaces);

        assertTrue(isParsedCorrectly(parser));
    }

    @Test
    public void parse_withoutJobIdWithJobName_parsesCorrectly() throws ParseException {
        parser.parse(fullArgumentsWithJobName);

        assertTrue(isParsedCorrectly(parser));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void parse_withMissingArgument_throwIllegalArgumentException() throws ParseException{
        parser.parse(argumentsWithMissingJob);
    }

    @Test
    public void getJobId_whenCalled_returnsJobId() throws ParseException {
        parser.parse(fullArgumentsWithSpaces);

        int result = parser.getJobId();

        assertEquals(result, JOBID);
    }

    @Test
    public void isHelp_whenCalledWithHelpArg_returnsTrue() throws ParseException {
        parser.parse(new String[] {"-help"});

        assertTrue(parser.hasHelp());
    }


    private boolean isParsedCorrectly(JobRunnerCliParser parser) {
        return Objects.equals(parser.getUsername(), USERNAME)
            && Objects.equals(parser.getPassword(), PASSWORD)
            && Objects.equals(parser.getHostname(), HOSTNAME)
            && (parser.hasJobId()
            ? Objects.equals(parser.getJobId(), JOBID) : Objects.equals(parser.getJobName(), JOBNAME));
    }
}
