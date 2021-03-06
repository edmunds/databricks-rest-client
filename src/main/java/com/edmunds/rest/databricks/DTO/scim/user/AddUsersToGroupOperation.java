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

import com.edmunds.rest.databricks.DTO.scim.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddUsersToGroupOperation extends Operation {

  private long[] members;

  /**
   * Builds a new AddUsersToGroupOperation.
   * @param members array of user identifiers (id's)
   */
  public AddUsersToGroupOperation(long[] members) {
    this.members = members;
    op = "add";

    HashMap<String, Object> membersMap = new HashMap<>();
    List<HashMap<String, String>> values = new ArrayList<>(members.length);
    for (long id : members) {
      HashMap<String, String> map = new HashMap<>();
      map.put("value", "" + id);
      values.add(map);
    }
    membersMap.put("members", values);

    value = membersMap;
  }

}
