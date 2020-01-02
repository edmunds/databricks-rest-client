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

package com.edmunds.rest.databricks.DTO.scim.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import lombok.Data;

/**
 * Databricks SCIM Group.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7643#section-4.2">https://tools.ietf.org/html/rfc7643#section-4.2</a> .
 */
@Data
public class GroupDTO {

  @JsonProperty("schemas")
  private final String[] schemas = new String[]{"urn:ietf:params:scim:schemas:core:2.0:Group"};
  @JsonProperty("id")
  private long id;
  @JsonProperty("displayName")
  private String display;
  @JsonProperty("members")
  private MemberDTO[] memberDTOS = new MemberDTO[0];

  public GroupDTO() {
  }

  /**
   * Creates a group from another one.
   *
   * @param from object to copy from
   */
  public GroupDTO(GroupDTO from) {
    this.id = from.id;
    this.display = from.display;
    if (from.memberDTOS != null) {
      memberDTOS = Arrays.copyOf(from.memberDTOS, from.memberDTOS.length);
    }
  }

}
