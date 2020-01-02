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
import com.edmunds.rest.databricks.DTO.scim.group.GroupDTO;
import com.edmunds.rest.databricks.DTO.scim.user.UserDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import java.io.IOException;

/**
 * The wrapper around the databricks SCIM API. Note that currently the API is in public preview mode - modifications are
 * expected.
 *
 * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#">https://docs.databricks.com/dev-tools/api/latest/scim.html#</a>
 */
public interface ScimService {

  /**
   * Gets an user by id.
   *
   * @param id user id
   * @return POJO representing an user
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#get-user-by-id">https://docs.databricks.com/dev-tools/api/latest/scim.html#get-user-by-id</a>
   */
  UserDTO getUser(long id) throws IOException, DatabricksRestException;

  /**
   * Inactivates an user.
   *
   * @param id user id
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#delete-user-by-id">https://docs.databricks.com/dev-tools/api/latest/scim.html#delete-user-by-id</a>
   */
  void deleteUser(long id) throws IOException, DatabricksRestException;

  /**
   * Retrieve a list of users associated with a Databricks workspace.
   *
   * @param filters    query filters, can be null @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#scim-filters">https://docs.databricks.com/dev-tools/api/latest/scim.html#scim-filters</a>
   * @return query response
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#get-users">https://docs.databricks.com/dev-tools/api/latest/scim.html#get-users</a>
   */
  ListResponseDTO<UserDTO> listUsers(String filters) throws IOException, DatabricksRestException;

  /**
   * Retrieve a list of users associated with a Databricks workspace. No search criteria are provided.
   *
   * @return query response
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#get-users">https://docs.databricks.com/dev-tools/api/latest/scim.html#get-users</a>
   */
  ListResponseDTO<UserDTO> listUsers() throws IOException, DatabricksRestException;

  /**
   * Create a user in the Databricks workspace.
   *
   * @param userDTO POJO representing an user
   * @return the user id
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#create-user">https://docs.databricks.com/dev-tools/api/latest/scim.html#create-user</a>
   */
  long createUser(UserDTO userDTO) throws IOException, DatabricksRestException;

  /**
   * Updates an user.
   *
   * @param userDTO POJO representing an user
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#update-user-by-id-put">https://docs.databricks.com/dev-tools/api/latest/scim.html#update-user-by-id-put</a>
   */
  void editUser(UserDTO userDTO) throws IOException, DatabricksRestException;

  /**
   * Gets a group by id.
   *
   * @param id the group id
   * @return POJO representing an group
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#get-group-by-id">https://docs.databricks.com/dev-tools/api/latest/scim.html#get-group-by-id</a>
   */
  GroupDTO getGroup(long id) throws IOException, DatabricksRestException;

  /**
   * Removes a group from the workspace. The users associated are not removed
   *
   * @param id group id
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#delete-group">https://docs.databricks.com/dev-tools/api/latest/scim.html#delete-group</a>
   */
  void deleteGroup(long id) throws IOException, DatabricksRestException;

  /**
   * Retrieve a list of groups associated with a Databricks workspace.
   *
   * @param filters    query filters, can be null @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#scim-filters">https://docs.databricks.com/dev-tools/api/latest/scim.html#scim-filters</a>
   * @return query response
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#get-groups">https://docs.databricks.com/dev-tools/api/latest/scim.html#get-groups</a>
   */
  ListResponseDTO<GroupDTO> listGroups(String filters) throws IOException, DatabricksRestException;


  /**
   * Retrieve a list of groups associated with a Databricks workspace. No search criteria are provided.
   *
   * @return query response
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#get-groups">https://docs.databricks.com/dev-tools/api/latest/scim.html#get-groups</a>
   */
  ListResponseDTO<GroupDTO> listGroups() throws IOException, DatabricksRestException;

  /**
   * Creates a group in the Databricks workspace.
   *
   * @param group POJO representing an group
   * @return the group id
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#create-group">https://docs.databricks.com/dev-tools/api/latest/scim.html#create-group</a>
   */
  long createGroup(GroupDTO group) throws IOException, DatabricksRestException;

  /**
   * Update a group in Databricks by adding new members.
   *
   * @param groupId group id
   * @param userIds user ids
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#update-group">https://docs.databricks.com/dev-tools/api/latest/scim.html#update-group</a>
   */
  void addUsersToGroup(long groupId, long[] userIds) throws IOException, DatabricksRestException;

  /**
   * Update a group in Databricks by removing members.
   *
   * @param groupId group id
   * @param userIds user ids
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/scim.html#update-group">https://docs.databricks.com/dev-tools/api/latest/scim.html#update-group</a>
   */
  void removeUsersFromGroup(long groupId, long[] userIds) throws IOException, DatabricksRestException;

}
