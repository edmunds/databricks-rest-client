package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.groups.PrincipalNameDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * The implementation of GroupsService.
 */
public class GroupsServiceImpl extends DatabricksService implements GroupsService {

  private static Logger log = Logger.getLogger(GroupsServiceImpl.class);
  private static final PrincipalNameDTO[] EMPTY_MEMBERS_ARRAY = {};
  private static final String[] EMPTY_STRING_ARRAY = {};

  public GroupsServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  @Override
  public void addUserToGroup(String userName, String parentName) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("user_name", userName);
    data.put("parent_name", parentName);
    client.performQuery(RequestMethod.POST, "/groups/add-member", data);
  }

  @Override
  public void addGroupToGroup(String groupName, String parentName) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("group_name", groupName);
    data.put("parent_name", parentName);
    client.performQuery(RequestMethod.POST, "/groups/add-member", data);
  }

  @Override
  public String createGroup(String groupName) throws IOException, DatabricksRestException {
    boolean groupExists = groupExists(groupName);
    // Doing this check and logging rather than letting API throw a "RESOURCE_ALREADY_EXISTS" exception
    if (!groupExists) {
      Map<String, Object> data = new HashMap<>();
      data.put("group_name", groupName);
      byte[] responseBody = client.performQuery(RequestMethod.POST, "/groups/create", data);
      Map<String, Object> response = this.mapper.readValue(responseBody, Map.class);
      Object returnedGroupName = response.get("group_name");
      if (returnedGroupName != null) {
        return (String) returnedGroupName;
      } else {
        throw new DatabricksRestException(String.format("There was an issue creating group [%s]. "
                + "No group_name was returned. You may need to reach out to Databricks Support for further diagnosis.",
            groupName));
      }
    } else {
      log.info(String.format("Group with name [%s] already exists", groupName));
      return groupName;
    }
  }

  @Override
  public PrincipalNameDTO[] listMembers(String groupName) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("group_name", groupName);
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/groups/list-members", data);
    Map<String, PrincipalNameDTO[]> jsonObject = this.mapper
        .readValue(responseBody, new TypeReference<Map<String, PrincipalNameDTO[]>>() {
        });
    PrincipalNameDTO[] members = jsonObject.get("members");
    return members != null ? members : EMPTY_MEMBERS_ARRAY;
  }

  @Override
  public String[] listGroups() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/groups/list", null);
    Map<String, String[]> jsonObject = this.mapper
        .readValue(responseBody, new TypeReference<Map<String, String[]>>() {
        });
    String[] groups = jsonObject.get("group_names");
    return groups != null ? groups : EMPTY_STRING_ARRAY;
  }

  @Override
  public String[] listParentsOfUser(String userName) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("user_name", userName);
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/groups/list-parents", data);

    Map<String, String[]> jsonObject = this.mapper
        .readValue(responseBody, new TypeReference<Map<String, String[]>>() {
        });
    String[] groups = jsonObject.get("group_names");
    return groups != null ? groups : EMPTY_STRING_ARRAY;
  }

  @Override
  public String[] listParentsOfGroup(String groupName) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("group_name", groupName);
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/groups/list-parents", data);

    Map<String, String[]> jsonObject = this.mapper
        .readValue(responseBody, new TypeReference<Map<String, String[]>>() {
        });
    String[] groups = jsonObject.get("group_names");
    return groups != null ? groups : EMPTY_STRING_ARRAY;
  }

  @Override
  public void removeUserFromGroup(String userName, String parentName) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("user_name", userName);
    data.put("parent_name", parentName);
    client.performQuery(RequestMethod.POST, "/groups/remove-member", data);
  }

  @Override
  public void removeGroupFromGroup(String groupName, String parentName) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("group_name", groupName);
    data.put("parent_name", parentName);
    client.performQuery(RequestMethod.POST, "/groups/remove-member", data);
  }

  @Override
  public void deleteGroup(String groupName) throws IOException, DatabricksRestException {
    boolean groupExists = groupExists(groupName);
    // Doing this check and logging rather than letting API throw a "RESOURCE_DOES_NOT_EXISTS" exception
    if (groupExists) {
      Map<String, Object> data = new HashMap<>();
      data.put("group_name", groupName);
      client.performQuery(RequestMethod.POST, "/groups/delete", data);
    } else {
      log.info(String.format("Group with name [%s] does not exist", groupName));
    }
  }

  @Override
  public boolean groupExists(String groupName) throws IOException, DatabricksRestException {
    String[] groups = listGroups();
    if (groups.length == 0) {
      return false;
    }
    for (String group : groups) {
      if (group.equals(groupName)) {
        return true;
      }
    }
    return false;
  }
}
