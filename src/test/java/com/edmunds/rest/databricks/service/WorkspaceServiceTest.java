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

import com.edmunds.rest.databricks.DTO.ExportFormatDTO;
import com.edmunds.rest.databricks.DTO.LanguageDTO;
import com.edmunds.rest.databricks.DTO.ObjectInfoDTO;
import com.edmunds.rest.databricks.DTO.ObjectTypeDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import com.edmunds.rest.databricks.request.ExportWorkspaceRequest;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest;
import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 *
 */
public class WorkspaceServiceTest {

  private WorkspaceService service;

  @BeforeClass
  public void setUpOnce() throws IOException, DatabricksRestException {
    DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();

    service = factory.getWorkspaceService();
  }

  @Test
  public void listStatus_whenCalled_returnsNonZeroLengthList()
      throws IOException, DatabricksRestException {
    ObjectInfoDTO[] objectInfo = service.listStatus("/Users/");
    assertTrue(objectInfo.length > 0);
  }

  @Test
  public void getStatus_whenCalledOnDirectory_returnsDirectory()
      throws IOException, DatabricksRestException {
    ObjectInfoDTO objectInfo = service.getStatus("/Users/");
    assertEquals(objectInfo.getPath(), "/Users");
    assertEquals(objectInfo.getObjectType(), ObjectTypeDTO.DIRECTORY);
  }

  @Test(dependsOnMethods = {"listStatus_whenCalled_returnsNonZeroLengthList",
      "getStatus_whenCalledOnDirectory_returnsDirectory"}, expectedExceptions = DatabricksRestException.class)
  public void mkdirAndRecursivelyDelete() throws IOException, DatabricksRestException {
    service.mkdirs("/Users/dwhrestapi@edmunds.com/test/directory");
    ObjectInfoDTO baseDirectory = service.getStatus("/Users/dwhrestapi@edmunds.com/test/");
    assertEquals(baseDirectory.getObjectType(), ObjectTypeDTO.DIRECTORY);
    assertEquals(baseDirectory.getPath(), "/Users/dwhrestapi@edmunds.com/test");

    ObjectInfoDTO childDirectory = service.getStatus("/Users/dwhrestapi@edmunds.com/test/directory");
    assertEquals(childDirectory.getObjectType(), ObjectTypeDTO.DIRECTORY);
    assertEquals(childDirectory.getPath(), "/Users/dwhrestapi@edmunds.com/test/directory");

    service.delete("/Users/dwhrestapi@edmunds.com/test", true);
    service.getStatus("/Users/dwhrestapi@edmunds.com/test/"); // Expected exception thrown since directory does
    // not exist
  }

  @Test(dependsOnMethods = {"mkdirAndRecursivelyDelete"})
  public void importExportWorkspace() throws IOException, DatabricksRestException {
    String workSpacePath = "/Users/dwhrestapi@edmunds.com/testWorkspace";
    Path path = Paths.get("./src/test/resources/test_command.scala");
    byte[] data = Files.readAllBytes(path);

    ImportWorkspaceRequest importWorkspaceRequest = new ImportWorkspaceRequest.ImportWorkspaceRequestBuilder
        (workSpacePath)
        .withLanguage(LanguageDTO.SCALA)
        .withFormat(ExportFormatDTO.SOURCE)
        .withContent(data)
        .build();

    service.importWorkspace(importWorkspaceRequest);

    ExportWorkspaceRequest exportWorkspaceRequest = new ExportWorkspaceRequest.ExportWorkspaceRequestBuilder
        (workSpacePath)
        .withFormat(ExportFormatDTO.SOURCE)
        .build();

    byte[] response = service.exportWorkspace(exportWorkspaceRequest);
    service.delete(workSpacePath, false);

    byte[] decodedResponse = Base64.decodeBase64(response);
    String responseCode = new String(decodedResponse);
    String sourceCode = new String(data);
    assertTrue(responseCode.contains(sourceCode)); //Contains not equals because Databricks adds comments
  }

}
