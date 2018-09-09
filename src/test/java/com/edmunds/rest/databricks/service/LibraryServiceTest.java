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
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.TestUtil;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class LibraryServiceTest {
    private String clusterId;
    private LibraryService service;

    @BeforeClass
    public void setUpOnce() throws IOException, DatabricksRestException {
        DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();

        service = factory.getLibraryService();
        clusterId = TestUtil.getDefaultClusterId(factory.getClusterService());
    }

    @Test
    public void testSetUpOnce() {
        assertNotNull(clusterId);
    }

    @Test(dependsOnMethods = {"testSetUpOnce"})
    public void listAllLibraries_whenCalled_returnsNonZeroList() throws IOException, DatabricksRestException {
        ClusterLibraryStatusesDTO[] allLibraries = service.allClusterStatuses();
        assertTrue(allLibraries.length > 0);
    }

    @Test(dependsOnMethods = {"testSetUpOnce"})
    public void showLibraryStatuses_whenCalledWithValidClusterId_returnsCorrectClusterId() throws IOException,
        DatabricksRestException {
        ClusterLibraryStatusesDTO result = service.clusterStatus(clusterId);
        assertEquals(result.getClusterId(), clusterId);
    }
}
