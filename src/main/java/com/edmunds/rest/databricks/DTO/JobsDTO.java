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

import com.edmunds.rest.databricks.DTO.jobs.JobDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 */
@Data
@SuppressWarnings("PMD")
public class JobsDTO implements Serializable {

  @JsonProperty("jobs")
  private JobDTO[] jobs;
  @JsonProperty("has_more")
  private Boolean hasMore;

  @JsonProperty("next_page_token")
  private String nextPageToken;

  @JsonProperty("previous_page_token")
  private String previousPageToken;

}
