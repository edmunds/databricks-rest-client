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

import com.edmunds.rest.databricks.DTO.DbfsReadDTO;
import com.edmunds.rest.databricks.DTO.FileInfoDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 *
 */
public class DbfsServiceTest {

    private DbfsService service;

    @BeforeClass
    public void setUpOnce() throws IOException, DatabricksRestException {
        DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();
        service = factory.getDbfsService();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownOnce() throws IOException, DatabricksRestException {
        service.rm("/restApiTestDir", true);
    }

    @Test
    public void ls_whenCalled_returnsNonZeroLength() throws IOException, DatabricksRestException {
        FileInfoDTO[] fileInfoDTOs = service.ls("/");
        assertTrue(fileInfoDTOs.length > 0);
    }

    @Test
    public void getInfo_whenCalledOnDirectory_returnsDirectory() throws IOException, DatabricksRestException {
        FileInfoDTO fileInfoDTO = service.getInfo("/");
        assertTrue(fileInfoDTO.isDir());
    }

    @Test(dependsOnMethods = {"getInfo_whenCalledOnDirectory_returnsDirectory"},
        expectedExceptions = {DatabricksRestException.class})
    public void createAndDeleteDirs() throws IOException, DatabricksRestException {
        service.mkdirs("/restApiTestDir/testParentDir/childDir");
        FileInfoDTO parentDir = service.getInfo("/restApiTestDir/testParentDir");
        FileInfoDTO childDir = service.getInfo("/restApiTestDir/testParentDir/childDir");

        assertTrue(parentDir.isDir());
        assertTrue(childDir.isDir());

        service.rm("/restApiTestDir", true);
        service.getInfo("/restApiTestDir/testParentDir"); // Should throw
    }

    @Test(dependsOnMethods = {"getInfo_whenCalledOnDirectory_returnsDirectory"})
    public void readAndWriteStream() throws IOException, DatabricksRestException {
        String contents = "abcd";
        String location = "/restApiTestDir/testWrite";
        InputStream is = new ByteArrayInputStream( contents.getBytes() );

        service.write(location, is, true);

        FileInfoDTO info = service.getInfo(location);
        assertFalse(info.isDir());

        DbfsReadDTO result = service.read(location);
        assertEquals(contents, new String(result.getData()));
    }

    @Test(dependsOnMethods = {"readAndWriteStream"})
    public void mv_whenCalled_movesFile() throws IOException, DatabricksRestException {
        String contents = "abcd";
        String location = "/restApiTestDir/testWrite";
        String newLocation = "/restApiTestDir/testWriteMv";
        InputStream is = new ByteArrayInputStream( contents.getBytes() );

        service.write(location, is, true);

        service.mv(location, newLocation);

        assertFalse(service.getInfo(newLocation).isDir());
    }
}