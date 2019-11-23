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

package com.edmunds.rest.databricks.DTO.scim.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameDTO {

  private String formatted;
  private String familyName;
  private String givenName;
  private String middleName;
  private String honorificPrefix;
  private String honorificSuffix;

  /**
   * Builds an namedto from another one.
   * @param from object to copy from
   */
  public NameDTO(NameDTO from) {
    this.formatted = from.formatted;
    this.familyName = from.familyName;
    this.givenName = from.givenName;
    this.middleName = from.middleName;
    this.honorificPrefix = from.honorificPrefix;
    this.honorificSuffix = from.honorificSuffix;
  }
}
