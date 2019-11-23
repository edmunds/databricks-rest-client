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

package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.scim.ListResponseDTO;
import com.edmunds.rest.databricks.DTO.scim.Operation;
import com.edmunds.rest.databricks.DTO.scim.OperationsDTO;
import com.edmunds.rest.databricks.DTO.scim.group.GroupDTO;
import com.edmunds.rest.databricks.DTO.scim.user.AddUsersToGroupOperation;
import com.edmunds.rest.databricks.DTO.scim.user.RemoveUserFromGroupOperation;
import com.edmunds.rest.databricks.DTO.scim.user.UserDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class ScimServiceImpl extends DatabricksService implements ScimService {

  private static final String SCIM_USERS = "/preview/scim/v2/Users";
  private static final String SCIM_GROUPS = "/preview/scim/v2/Groups";

  public ScimServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  private String path(String path, long id) {
    return path + "/" + id;
  }

  @Override
  public UserDTO getUser(long id) throws IOException, DatabricksRestException {
    byte[] response = client.performQuery(RequestMethod.GET, path(SCIM_USERS, id));
    return this.mapper.readValue(response, UserDTO.class);
  }

  @Override
  public void deleteUser(long id) throws DatabricksRestException {
    client.performQuery(RequestMethod.DELETE, path(SCIM_USERS, id));
  }

  @Override
  public ListResponseDTO<UserDTO> listUsers() throws IOException, DatabricksRestException {
    return listUsers(null, 1);
  }

  @Override
  public ListResponseDTO<UserDTO> listUsers(String filters, int startIndex)
      throws IOException, DatabricksRestException {
    Map<String, Object> params = new HashMap<>();
    if (filters != null) {
      params.put("filter", URLEncoder.encode(filters, "UTF-8"));
    }
    params.put("startIndex", startIndex);
    byte[] response = client.performQuery(RequestMethod.GET, SCIM_USERS, params);
    return mapper.readValue(response, new TypeReference<ListResponseDTO<UserDTO>>() {
    });
  }

  @Override
  public long createUser(UserDTO userDTO) throws IOException, DatabricksRestException {
    String marshalled = mapper.writeValueAsString(userDTO);
    Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
    });
    byte[] responseBody = client.performQuery(RequestMethod.POST, SCIM_USERS, data);
    UserDTO response = mapper.readValue(responseBody, UserDTO.class);
    userDTO.setId(response.getId());
    return response.getId();
  }

  @Override
  public void editUser(UserDTO userDTO) throws IOException, DatabricksRestException {
    UserDTO userToEdit = new UserDTO(userDTO);
    userToEdit.setId(0);
    String marshalled = mapper.writeValueAsString(userToEdit);
    Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
    });
    client.performQuery(RequestMethod.PUT, path(SCIM_USERS, userDTO.getId()), data);
  }

  @Override
  public GroupDTO getGroup(long id) throws IOException, DatabricksRestException {
    byte[] response = client.performQuery(RequestMethod.GET, path(SCIM_GROUPS, id));
    return this.mapper.readValue(response, GroupDTO.class);
  }

  @Override
  public void deleteGroup(long id) throws DatabricksRestException {
    client.performQuery(RequestMethod.DELETE, path(SCIM_GROUPS, id));
  }

  @Override
  public ListResponseDTO<GroupDTO> listGroups() throws IOException, DatabricksRestException {
    return listGroups(null, 1);
  }

  @Override
  public ListResponseDTO<GroupDTO> listGroups(String filters, int startIndex)
      throws IOException, DatabricksRestException {
    Map<String, Object> params = new HashMap<>();
    if (filters != null) {
      params.put("filter", URLEncoder.encode(filters, "UTF-8"));
    }
    params.put("startIndex", startIndex);
    byte[] response = client.performQuery(RequestMethod.GET, SCIM_GROUPS, params);
    return mapper.readValue(response, new TypeReference<ListResponseDTO<GroupDTO>>() {
    });
  }

  @Override
  public long createGroup(GroupDTO group) throws IOException, DatabricksRestException {
    String marshalled = mapper.writeValueAsString(group);
    Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
    });
    byte[] responseBody = client.performQuery(RequestMethod.POST, SCIM_GROUPS, data);
    GroupDTO response = mapper.readValue(responseBody, GroupDTO.class);
    group.setId(response.getId());
    return response.getId();
  }

  @Override
  public void addUsersToGroup(long groupId, long[] userIds) throws IOException, DatabricksRestException {
    Operation operation = new AddUsersToGroupOperation(userIds);
    OperationsDTO operationsDTO = new OperationsDTO();
    operationsDTO.setOperations(new Operation[]{operation});
    String marshalled = mapper.writeValueAsString(operationsDTO);
    Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
    });
    client.performQuery(RequestMethod.PATCH, path(SCIM_GROUPS, groupId), data);
  }

  @Override
  public void removeUsersFromGroup(long groupId, long[] userIds) throws IOException, DatabricksRestException {
    Operation[] operations = new RemoveUserFromGroupOperation[userIds.length];
    for (int i = 0; i < operations.length; i++) {
      operations[i] = new RemoveUserFromGroupOperation(userIds[i]);
    }
    OperationsDTO operationsDTO = new OperationsDTO();
    operationsDTO.setOperations(operations);
    String marshalled = mapper.writeValueAsString(operationsDTO);
    Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
    });
    client.performQuery(RequestMethod.PATCH, path(SCIM_GROUPS, groupId), data);
  }

}
