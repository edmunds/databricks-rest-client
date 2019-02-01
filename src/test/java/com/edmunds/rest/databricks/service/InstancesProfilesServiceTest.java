package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.InstanceProfileDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class InstancesProfilesServiceTest {

  private InstanceProfilesService service;
  private static final String testArn = "arn:aws:iam::123456789012:instance-profile/test";

  @BeforeClass
  public void setUpOnce() throws IOException, DatabricksRestException {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();

    service = factory.getInstanceProfilesService();

    // Clean up
    service.remove(testArn);
  }

  @AfterMethod
  public void cleanUp() throws IOException, DatabricksRestException {
    service.remove(testArn);
  }

  @AfterClass(alwaysRun = true)
  public void tearDownOnce() throws IOException, DatabricksRestException {
    service.remove(testArn);
  }

  @Test
  public void add_whenCalled_correctlyAddsProfile() throws IOException, DatabricksRestException {
    service.add(testArn, true);
    assertTrue(service.exists(testArn));
  }

  @Test
  public void list_whenCalled_returnsNonEmptyArray() throws IOException, DatabricksRestException {
    service.add(testArn, true);
    assertTrue(service.list().length > 0);
  }

  @Test
  public void remove_whenCalled_correctlyRemovesProfile() throws IOException, DatabricksRestException {
    service.add(testArn, true);
    service.remove(testArn);
    assertFalse(service.exists(testArn));
  }
}
