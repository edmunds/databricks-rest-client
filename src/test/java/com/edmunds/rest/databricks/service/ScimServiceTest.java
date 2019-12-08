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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.edmunds.rest.databricks.DTO.scim.group.GroupDTO;
import com.edmunds.rest.databricks.DTO.scim.user.EntitlementDTO;
import com.edmunds.rest.databricks.DTO.scim.user.EntitlementsDTO;
import com.edmunds.rest.databricks.DTO.scim.user.UserDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ScimServiceTest {

  public static final String USERNAME = "testintegration@test.com";
  public static final String FAMILY_NAME = "klaus";
  public static final String GIVEN_NAME = "iohannis";
  private ScimService service;


  private long idUSer1;
  private final String ADMIN_GROUP = "admins";
  private String testGroup1 = "TestIntegration Group";
  private long idGroup1;


  @BeforeClass
  public void setUpOnce() {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();
    service = factory.getScimService();
  }

  @AfterClass(alwaysRun = true)
  public void cleanUp() {
    deleteAllTestUsers();
    deleteAllTestGroups();
  }

  @AfterMethod
  public void tearDown() {
    deleteAllTestUsers();
    deleteAllTestGroups();
  }

  public void deleteAllTestUsers() {
    try {
      if (idUSer1 != 0) {
        service.deleteUser(idUSer1);
        idUSer1 = 0;
      }
    } catch (Exception e) {
      System.err.println("exception during delete users, the test database was not properly cleaned up "+e.getMessage());
    }

  }

  public void deleteAllTestGroups() {
    try {
      if (idGroup1 != 0) {
        service.deleteGroup(idGroup1);
        idGroup1 = 0;
      }
    } catch (Exception e) {
      System.err.println("exception during delete groups, the test database was not properly cleaned up "+e.getMessage());
    }
  }


  @Test
  public void testGroupCreate() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);
    GroupDTO groupDTO2 = service.getGroup(idGroup1);
    assertEquals(groupDTO1.getDisplay(), groupDTO2.getDisplay());
  }

  @Test(dependsOnMethods = {"testGroupCreate"})
  public void testGroupList() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);
    GroupDTO[] groupDTOS = service.listGroups("displayName eq " + testGroup1).getResources();
    assertEquals(groupDTOS.length, 1);
    assertEquals(groupDTOS[0].getDisplay(), testGroup1);
  }

  @Test(dependsOnMethods = {"testGroupList"}, enabled = false) //pagination seems to not work for the moment
  public void testGroupListPagination() throws IOException, DatabricksRestException {

  }


  @Test(dependsOnMethods = {"testGroupCreate"})
  public void testUserCreate() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);

    UserDTO userDTO = createUserDTO(groupDTO1);

    idUSer1 = service.createUser(userDTO);
    assertTrue(idUSer1 > 0);
  }

  @Test(dependsOnMethods = {"testUserCreate"})
  public void testUserGet() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);

    UserDTO userDTO = createUserDTO(groupDTO1);
    idUSer1 = service.createUser(userDTO);

    UserDTO retrievedUser = service.getUser(idUSer1);
    assertEquals(userDTO, retrievedUser);
  }

  @Test(dependsOnMethods = {"testUserCreate"})
  public void testUserDelete() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);

    UserDTO userDTO = createUserDTO(groupDTO1);
    idUSer1 = service.createUser(userDTO);

    UserDTO retrievedUser = service.getUser(idUSer1);

    service.deleteUser(retrievedUser.getId());

    boolean notFound = false;
    try {
      service.getUser(idUSer1);
    } catch (DatabricksRestException d) {
      notFound = true;
    }
    assertTrue(notFound);
  }

  @Test(dependsOnMethods = {"testUserCreate"})
  public void testUserUpdate() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);

    UserDTO userDTO = createUserDTO(groupDTO1);
    idUSer1 = service.createUser(userDTO);

    UserDTO createdUser = service.getUser(idUSer1);

    createdUser.setNameDetails("ion", "iliescu");
    createdUser.setEntitlements(new EntitlementsDTO[]{});
    service.editUser(createdUser);

    UserDTO editedUser = service.getUser(idUSer1);

    assertEquals(editedUser, createdUser);
  }

  @Test(dependsOnMethods = {"testUserCreate"})
  public void testUserList() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);

    UserDTO userDTO = createUserDTO(groupDTO1);
    idUSer1 = service.createUser(userDTO);

    UserDTO[] userDTOS = service.listUsers("userName eq " + USERNAME).getResources();
    assertEquals(userDTOS.length, 1);
    assertEquals(userDTOS[0], userDTO);
  }

  @Test(dependsOnMethods = {"testUserCreate"})
  public void testAddUserToGroup() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);

    UserDTO userDTO = createUserDTO(null);
    idUSer1 = service.createUser(userDTO);

    service.addUsersToGroup(idGroup1, new long[]{idUSer1});

    groupDTO1 = service.getGroup(idGroup1);
    assertEquals(userDTO.getId(), groupDTO1.getMemberDTOS()[0].getValue());

  }

  @Test(dependsOnMethods = {"testUserCreate"})
  public void testRemoveUserFromGroup() throws IOException, DatabricksRestException {
    GroupDTO groupDTO1 = createGroupDTO();
    idGroup1 = service.createGroup(groupDTO1);

    UserDTO userDTO = createUserDTO(groupDTO1);
    idUSer1 = service.createUser(userDTO);

    service.removeUsersFromGroup(idGroup1, new long[]{idUSer1});

    groupDTO1 = service.getGroup(idGroup1);
    assertEquals(groupDTO1.getMemberDTOS().length, 0);

  }

  private UserDTO createUserDTO(GroupDTO groupDTO1) {
    UserDTO userDTO = new UserDTO();
    userDTO.setNameDetails(FAMILY_NAME, GIVEN_NAME);
    userDTO.setUserName(USERNAME);
    userDTO.setEntitlements(new EntitlementsDTO[]{new EntitlementsDTO(EntitlementDTO.ALLOW_CLUSTER_CREATE)});
      if (groupDTO1 != null) {
          userDTO.setGroups(new GroupDTO[]{groupDTO1});
      }
    return userDTO;
  }

  private GroupDTO getAdminGroupId() throws IOException, DatabricksRestException {
    return service.listGroups("displayName eq " + ADMIN_GROUP).getResources()[0];
  }

  private GroupDTO createGroupDTO() {
    GroupDTO groupDTO1 = new GroupDTO();
    groupDTO1.setDisplay(testGroup1);
    return groupDTO1;
  }

}
