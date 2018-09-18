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
public class ObjectInfoDTO implements Serializable {
  @JsonProperty("object_type")
  private ObjectTypeDTO objectType;
  private String path;
  private LanguageDTO language;

  public ObjectTypeDTO getObjectType() {
    return objectType;
  }

  public void setObjectType(ObjectTypeDTO objectType) {
    this.objectType = objectType;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public LanguageDTO getLanguage() {
    return language;
  }

  public void setLanguage(LanguageDTO language) {
    this.language = language;
  }
}
