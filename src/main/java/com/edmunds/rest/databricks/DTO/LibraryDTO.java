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

import java.io.Serializable;
import lombok.Data;

/**
 *
 */
@Data
public class LibraryDTO implements Serializable {

  private PythonPyPiLibraryDTO pypi;
  private String egg;
  private String jar;
  private MavenLibraryDTO maven;
  private RCranLibraryDTO cran;

  public String getEgg() {
    return egg;
  }

  public void setEgg(String egg) {
    this.egg = egg;
  }

  public RCranLibraryDTO getCran() {
    return cran;
  }

  public void setCran(RCranLibraryDTO cran) {
    this.cran = cran;
  }

  public PythonPyPiLibraryDTO getPypi() {
    return pypi;
  }

  public void setPypi(PythonPyPiLibraryDTO pypi) {
    this.pypi = pypi;
  }

  public String getJar() {
    return jar;
  }

  public void setJar(String jar) {
    this.jar = jar;
  }

  public MavenLibraryDTO getMaven() {
    return maven;
  }

  public void setMaven(MavenLibraryDTO maven) {
    this.maven = maven;
  }
}