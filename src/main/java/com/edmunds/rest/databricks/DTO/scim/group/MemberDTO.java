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
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {

  @JsonProperty("display")
  private String display;
  @JsonProperty("value")
  private long value;

  public MemberDTO() {
  }

  /**
   * Copy constructor.
   * @param from object to copy from
   */
  public MemberDTO(MemberDTO from) {
    this.display = from.display;
    this.value = from.value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MemberDTO memberDTO = (MemberDTO) o;
    return value == memberDTO.value
        && Objects.equals(display, memberDTO.display);
  }

  @Override
  public int hashCode() {
    return Objects.hash(display, value);
  }
}
