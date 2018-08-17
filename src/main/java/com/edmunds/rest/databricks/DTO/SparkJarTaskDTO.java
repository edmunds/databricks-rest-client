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
@SuppressWarnings("PMD")
public class SparkJarTaskDTO implements Serializable {
    @JsonProperty("jar_uri")
    private String jarUri;
    @JsonProperty("main_class_name")
    private String mainClassName;
    private String[] parameters;
    @JsonProperty("run_as_repl")
    private boolean runAsRepl;

    public boolean isRunAsRepl() {
        return runAsRepl;
    }

    public void setRunAsRepl(boolean runAsRepl) {
        this.runAsRepl = runAsRepl;
    }

    public String getJarUri() {
        return jarUri;
    }

    public void setJarUri(String jarUri) {
        this.jarUri = jarUri;
    }

    public String getMainClassName() {
        return this.mainClassName;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public String[] getParameters() {
        return this.parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
}
