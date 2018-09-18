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

package com.edmunds.rest.databricks;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TokenAuthDatabricksRestClientTest {
  private DatabricksRestClient client;
  private ObjectMapper mapper;

  @BeforeClass
  public void setUpOnce() throws IOException {
    client = DatabricksFixtures.createTokenAuthRestClient();
    mapper = new ObjectMapper();
  }

  @Test
  public void getRequest_whenCalled_returnsValidResponse()
      throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/libraries/all-cluster-statuses", null);
    Map<String, Object> response = this.mapper.readValue(responseBody, Map.class);

    assertNotNull(response);
    System.out.println(response);
  }

  @Test(expectedExceptions = DatabricksRestException.class, expectedExceptionsMessageRegExp = ".*ENDPOINT_NOT_FOUND" +
      ".*")
  public void performQuery_withInvalidPath_throwsDatabricksRestException() throws Exception {
    client.performQuery(RequestMethod.GET, "/fake_path", null);
  }

  @Test(expectedExceptions = DatabricksRestException.class, expectedExceptionsMessageRegExp = ".*INVALID_PARAMETER_VALUE.*")
  public void performQuery_withInvalidParameter_throwsDatabricksRestException() throws Exception {
    client.performQuery(RequestMethod.GET, "/clusters/get", null);
  }
}
