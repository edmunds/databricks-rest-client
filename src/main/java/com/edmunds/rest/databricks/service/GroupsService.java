package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.groups.PrincipalNameDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import java.io.IOException;

/**
 * A wrapper around Databricks' Groups API.
 *
 * @see <a href="https://docs.databricks.com/api/latest/groups.html">https://docs.databricks.com/api/latest/groups.html</a>
 */
public interface GroupsService {

  /**
   * Adds a user to a group.
   *
   * @param userName   the userName to be added
   * @param parentName name of the parent group to which the new member will be added
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#add-member">https://docs.databricks.com/api/latest/groups.html#add-member</a>
   */
  void addUserToGroup(String userName, String parentName) throws IOException, DatabricksRestException;

  /**
   * Adds a group to a group.
   *
   * @param groupName  the groupName to be added
   * @param parentName name of the parent group to which the new member will be added
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#add-member">https://docs.databricks.com/api/latest/groups.html#add-member</a>
   */
  void addGroupToGroup(String groupName, String parentName) throws IOException, DatabricksRestException;

  /**
   * Creates a new group.
   *
   * @param groupName the groupName to be created
   * @return the groupName
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#create">https://docs.databricks.com/api/latest/groups.html#create</a>
   */
  String createGroup(String groupName) throws IOException, DatabricksRestException;

  /**
   * Returns all of the members of a particular group.
   *
   * @param groupName the groupName whose members we want to retrieve
   * @return all users and groups that belong to a given group
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#list-members">https://docs.databricks.com/api/latest/groups.html#list-members</a>
   */
  PrincipalNameDTO[] listMembers(String groupName) throws IOException, DatabricksRestException;

  /**
   * Returns all the groups in an organization.
   *
   * @return the groupNames of all groups
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#list">https://docs.databricks.com/api/latest/groups.html#list</a>
   */
  String[] listGroups() throws IOException, DatabricksRestException;

  /**
   * Retrieves all groups in which a given user is a member.
   *
   * @param userName name of the user
   * @return the groupNames of all groups the user belongs to
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#list-parents">https://docs.databricks.com/api/latest/groups.html#list-parents</a>
   */
  String[] listParentsOfUser(String userName) throws IOException, DatabricksRestException;

  /**
   * Retrieves all groups in which a given group is a member.
   *
   * @param groupName name of the group
   * @return the groupNames of all groups the group belongs to
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#list-parents">https://docs.databricks.com/api/latest/groups.html#list-parents</a>
   */
  String[] listParentsOfGroup(String groupName) throws IOException, DatabricksRestException;

  /**
   * Removes a user from a group.
   *
   * @param userName   name of the user
   * @param parentName name of parent group
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#remove-member">https://docs.databricks.com/api/latest/groups.html#remove-member</a>
   */
  void removeUserFromGroup(String userName, String parentName) throws IOException, DatabricksRestException;

  /**
   * Removes a group from a group.
   *
   * @param groupName  name of the group
   * @param parentName name of parent group
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#remove-member">https://docs.databricks.com/api/latest/groups.html#remove-member</a>
   */
  void removeGroupFromGroup(String groupName, String parentName) throws IOException, DatabricksRestException;

  /**
   * Removes a group from this organization.
   *
   * @param groupName name of the group to be deleted
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/latest/groups.html#delete">https://docs.databricks.com/api/latest/groups.html#delete</a>
   */
  void deleteGroup(String groupName) throws IOException, DatabricksRestException;

  /**
   * Check to see if a group exists with a given name.
   *
   * @param groupName name to check
   * @return whether or not the groupName was found in the list of groups
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  boolean groupExists(String groupName) throws IOException, DatabricksRestException;
}
