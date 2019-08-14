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

import com.edmunds.rest.databricks.DTO.ClusterLibraryStatusesDTO;
import com.edmunds.rest.databricks.DTO.LibraryDTO;
import com.edmunds.rest.databricks.DTO.LibraryFullStatusDTO;
import com.edmunds.rest.databricks.DTO.LibraryInstallStatusDTO;
import com.edmunds.rest.databricks.DTO.MavenLibraryDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.TestUtil;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class LibraryServiceTest {
  private String clusterId;
  private LibraryService service;

  @BeforeClass
  public void setUpOnce() throws IOException, DatabricksRestException, InterruptedException {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();

    service = factory.getLibraryService();
    clusterId = TestUtil.getTestClusterId(factory.getClusterService());
  }

  @Test
  public void testSetUpOnce() {
    assertNotNull(clusterId);
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void listAllLibraries_whenCalled_returnsNonZeroList()
      throws IOException, DatabricksRestException {
    ClusterLibraryStatusesDTO[] allLibraries = service.allClusterStatuses();
    assertTrue(allLibraries.length > 0);
  }

  private LibraryDTO getLibrary() {
    LibraryDTO libraryDTO = new LibraryDTO();
    MavenLibraryDTO mavenLibraryDTO = new MavenLibraryDTO();
    mavenLibraryDTO.setCoordinates("com.edmunds:databricks-rest-client:2.3.1");
    libraryDTO.setMaven(mavenLibraryDTO);
    return libraryDTO;
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void installLibrary_installsLibrary() throws IOException, DatabricksRestException {
    LibraryDTO libraryDTO = getLibrary();
    service.install(clusterId, new LibraryDTO[]{libraryDTO});
    ClusterLibraryStatusesDTO status = service.clusterStatus(clusterId);
    // There could be other libraries that are auto installed, so all we can do is to check if it exists.
    for (LibraryFullStatusDTO libraryFullStatusDTO : status.getLibraryFullStatuses()) {
      if (libraryFullStatusDTO.getLibrary().equals(libraryDTO)) {
        assertTrue(true);
        return;
      }
    }
    assertTrue(false);
  }

  @Test(dependsOnMethods = {"testSetUpOnce"})
  public void showLibraryStatuses_whenCalledWithValidClusterId_returnsCorrectClusterId()
      throws IOException,
             DatabricksRestException {
    ClusterLibraryStatusesDTO result = service.clusterStatus(clusterId);
    assertEquals(result.getClusterId(), clusterId);
  }

  @Test(dependsOnMethods = {"testSetUpOnce", "installLibrary_installsLibrary"})
  public void uninstallLibrary_whenCalledWithValidClusterIdLibraries_uninstallsAllLibraries()
      throws IOException,
      DatabricksRestException, InterruptedException {
    service.uninstallAll(clusterId);
    ClusterLibraryStatusesDTO status = service.clusterStatus(clusterId);
    LibraryDTO libraryDTO = getLibrary();
    for (LibraryFullStatusDTO libraryFullStatusDTO : status.getLibraryFullStatuses()) {
      if (libraryFullStatusDTO.getLibrary().equals(libraryDTO)) {
        System.out.println("Status is " + libraryFullStatusDTO.getStatus());
        await()
            .pollInterval(10, TimeUnit.SECONDS)
            .timeout(60, TimeUnit.SECONDS)
            .until(TestUtil.jarLibraryStatusHasChangedTo(
                LibraryInstallStatusDTO.UNINSTALL_ON_RESTART,
                clusterId, libraryFullStatusDTO.getLibrary(), service));
      }
    }
    Assert.assertTrue(true);
  }
}
