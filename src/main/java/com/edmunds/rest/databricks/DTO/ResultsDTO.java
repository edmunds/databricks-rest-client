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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@SuppressWarnings("PMD")
@Data
public class ResultsDTO implements Serializable {

  @Getter @Setter @JsonProperty("resultType")
  private String resultType;
  @Getter @Setter @JsonProperty("data")
  private Object data;
  @Getter @Setter @JsonProperty("schema")
  private Object schema;
  @Getter @Setter @JsonProperty("truncated")
  private boolean truncated;
  @Getter @Setter @JsonProperty("isJsonSchema")
  private boolean isJsonSchema;
  @Getter @Setter @JsonProperty("summary")
  private String summary;
  @Getter @Setter @JsonProperty("cause")
  private String cause;
}
