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
 * Represents a query response as defined by @see <a href="https://tools.ietf.org/html/rfc7644#section-3.4.2">https://tools.ietf.org/html/rfc7644#section-3.4.2</a> .
 */
@Data
public class ListResponseDTO<V> {

  @JsonProperty("schemas")
  private final String[] schemas = new String[]{"urn:ietf:params:scim:api:messages:2.0:ListResponse"};
  @JsonProperty("totalResults")
  private int totalResults;
  @JsonProperty("startIndex")
  private int startIndex;
  @JsonProperty("itemsPerPage")
  private int itemsPerPage;
  @JsonProperty("Resources")
  private V[] resources;
}
