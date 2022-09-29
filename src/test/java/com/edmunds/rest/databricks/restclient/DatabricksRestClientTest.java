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

package com.edmunds.rest.databricks.restclient;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import com.edmunds.rest.databricks.fixtures.TestHttpClientBuilderFactory;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.edmunds.rest.databricks.restclient.DatabricksRestClientImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DatabricksRestClientTest {
  private ObjectMapper mapper;

  @BeforeClass
  public void setUpOnce() throws IOException {
    mapper = new ObjectMapper();
  }

  @DataProvider(name = "Clients")
  public Object[][] getClients() throws IOException {
    return new Object[][]{
        {DatabricksFixtures.getDatabricksRestClient()},
        {DatabricksFixtures.createTokenAuthRestClient()}
    };
  }

  @Test(dataProvider = "Clients")
  public void getRequest_whenCalled_returnsValidResponse(DatabricksRestClient client)
      throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/libraries/all-cluster-statuses", null);
    Map<String, Object> response = this.mapper.readValue(responseBody, Map.class);

    assertNotNull(response);
  }

  // I ignored this test as this run id doesn't appear to be available anymore.
  @Test(enabled = false, dataProvider = "Clients")
  public void getRequest_whenCalledWithData_returnsValidResponse(DatabricksRestClient client)
      throws IOException, DatabricksRestException {
    int id = 1452843;
    Map<String, Object> data = new HashMap<>();
    data.put("run_id", String.valueOf(id));

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/jobs/runs/get", data);
    Map<String, Object> response = this.mapper.readValue(responseBody, Map.class);
    int result = (int) response.get("run_id");

    assertEquals(result, result);
  }

  @Test(dataProvider = "Clients", expectedExceptions = DatabricksRestException.class, expectedExceptionsMessageRegExp =
      ".*ENDPOINT_NOT_FOUND" +
      ".*")
  public void performQuery_withInvalidPath_throwsDatabricksRestException(
      DatabricksRestClient client) throws Exception {
    client.performQuery(RequestMethod.GET, "/fake_path", null);
  }

  @Test(dataProvider = "Clients", expectedExceptions = DatabricksRestException.class, expectedExceptionsMessageRegExp = ".*INVALID_PARAMETER_VALUE.*")
  public void performQuery_withInvalidParameter_throwsDatabricksRestException(
      DatabricksRestClient client) throws Exception {
    client.performQuery(RequestMethod.GET, "/clusters/get", null);
  }

  @Test
  public void performQuery_retry_when_404() throws Exception {
    DatabricksServiceFactory.Builder builder = DatabricksFixtures.createDatabricksServiceBuilder();
    TestHttpClientBuilderFactory factory = new TestHttpClientBuilderFactory(404, builder);
    HttpServiceUnavailableRetryStrategy retryStrategy = (HttpServiceUnavailableRetryStrategy)factory.getServiceUnavailableRetryStrategy();
    DatabricksRestClientImpl notFoundClient = new DatabricksRestClientImpl(builder, factory, "2.0");
    try {
      notFoundClient.performQuery(RequestMethod.GET, "/fake_path", null);
    } catch (DatabricksRestException e) {
      assertEquals(retryStrategy.getExecuteCount(), 2);
    }
  }
}
