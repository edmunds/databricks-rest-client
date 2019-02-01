package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.PrincipalNameDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;

/**
 * Unfortunately, we currently do not have a way of testing adding/removing Users to groups(as opposed to
 * adding/removing a group to a group) because databricks API does not support creation of users.
 */
public class GroupsServiceTest {

  private GroupsService service;
  private final String testGroupName = "dbRestClientTestGroup";
  private final String testGroupName2 = "dbRestClientTestGroup2";
  private final String testUser = "testUsers";

  @BeforeClass
  public void setUpOnce() throws IOException, DatabricksRestException {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();

    service = factory.getGroupsService();

    // Delete test groups if exists
    deleteAllTestGroups();
  }

  @AfterClass(alwaysRun = true)
  public void finalCleanUp() throws IOException, DatabricksRestException {
    deleteAllTestGroups();
  }

  @AfterMethod
  public void tearDown() throws IOException, DatabricksRestException {
    deleteAllTestGroups();
  }

  @Test
  public void createGroup_whenCalled_createsGroupWithCorrectName() throws IOException, DatabricksRestException {
    String returnedName = service.createGroup(testGroupName);
    assertEquals(returnedName, testGroupName);
  }

  @Test(dependsOnMethods = {"createGroup_whenCalled_createsGroupWithCorrectName"})
  public void groupExists_whenCalledWithExistingGroup_returnsTrue() throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);
    assertTrue(service.groupExists(testGroupName));
  }

  @Test(dependsOnMethods = {"createGroup_whenCalled_createsGroupWithCorrectName"})
  public void groupExists_whenCalledWithNonExistingGroup_returnsFalse() throws IOException, DatabricksRestException {
    assertFalse(service.groupExists(testGroupName));
  }

  @Test(dependsOnMethods = {"groupExists_whenCalledWithNonExistingGroup_returnsFalse"})
  public void deleteGroup_whenCalled_correctlyDeletesGroup() throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);
    service.deleteGroup(testGroupName);
    assertFalse(service.groupExists(testGroupName));
  }

  @Test(dependsOnMethods = {"createGroup_whenCalled_createsGroupWithCorrectName"})
  public void addGroupToGroup_whenCalled_correctlyAddsUserToGroup() throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);
    service.createGroup(testUser);

    service.addGroupToGroup(testUser, testGroupName);
    assertTrue(groupBelongsToGroup(testUser, testGroupName));
  }

  @Test(dependsOnMethods = {"createGroup_whenCalled_createsGroupWithCorrectName"})
  public void listGroups_whenCalled_returnsNonZeroArray() throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);

    String[] groups = service.listGroups();
    assertTrue(groups.length > 0);
  }

  @Test(dependsOnMethods = {"addGroupToGroup_whenCalled_correctlyAddsUserToGroup"})
  public void listMembers_whenCalledWithNonEmptyGroup_returnsAllMembers()
      throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);
    service.createGroup(testUser);
    service.addGroupToGroup(testUser, testGroupName);

    PrincipalNameDTO[] members = service.listMembers(testGroupName);
    assertEquals(members.length, 1);
  }

  @Test(dependsOnMethods = {"addGroupToGroup_whenCalled_correctlyAddsUserToGroup"})
  public void listParentsOfGroup_whenCalled_returnsCorrectParent() throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);
    service.createGroup(testUser);
    service.addGroupToGroup(testUser, testGroupName);

    String[] parents = service.listParentsOfGroup(testUser);
    assertEquals(parents.length, 1);
    assertEquals(parents[0], testGroupName);
  }

  @Test(dependsOnMethods = {"addGroupToGroup_whenCalled_correctlyAddsUserToGroup"})
  public void listParentsOfGroup_whenCalledWithMultipleParents_returnsCorrectParent()
      throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);
    service.createGroup(testGroupName2);
    service.createGroup(testUser);
    service.addGroupToGroup(testUser, testGroupName);
    service.addGroupToGroup(testUser, testGroupName2);

    String[] parents = service.listParentsOfGroup(testUser);
    assertEquals(parents.length, 2);
  }

  @Test(dependsOnMethods = {"addGroupToGroup_whenCalled_correctlyAddsUserToGroup"})
  public void removeUserFromGroup_whenCalled_removesUser() throws IOException, DatabricksRestException {
    service.createGroup(testGroupName);
    service.createGroup(testUser);
    service.addGroupToGroup(testUser, testGroupName);

    service.removeGroupFromGroup(testUser, testGroupName);

    assertFalse(groupBelongsToGroup(testUser, testGroupName));
  }

  private void deleteTestGroup(String group) throws IOException, DatabricksRestException {
    service.deleteGroup(group);
  }

  private void deleteAllTestGroups() throws IOException, DatabricksRestException {
    deleteTestGroup(testGroupName);
    deleteTestGroup(testGroupName2);
    deleteTestGroup(testUser);
  }

  private boolean groupBelongsToGroup(String userName, String groupName) throws IOException, DatabricksRestException {
    PrincipalNameDTO[] members = service.listMembers(groupName);
    if (members == null) {
      return false;
    }
    if (members.length == 0) {
      return false;
    }
    for (PrincipalNameDTO member : members) {
      if (member.getGroupName() != null && member.getGroupName().equals(userName)) {
        return true;
      }
    }
    return false;
  }
}
