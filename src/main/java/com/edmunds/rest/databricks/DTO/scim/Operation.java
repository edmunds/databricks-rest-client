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

package com.edmunds.rest.databricks.DTO.scim;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Used for SCIM patch operations.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7644#section-3.5.2">https://tools.ietf.org/html/rfc7644#section-3.5.2</a>
 * Each operation has one "op" member (add, remove or replace), path (optional for add/replace, mandatory for remove)
 *      and value (specific for each particular operation).
 */
@Data
public abstract class Operation {

  @JsonProperty("op")
  protected String op;
  @JsonProperty("path")
  protected String path;
  @JsonProperty("value")
  protected Object value;
}
