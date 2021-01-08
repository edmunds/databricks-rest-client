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

import static org.testng.Assert.assertNull;

import com.edmunds.rest.databricks.DTO.clusters.TerminationReasonDTO;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DatabricksServiceTest {

  DatabricksService service;

  @BeforeClass
  public void before() {
    service = new DatabricksService(Mockito.mock(DatabricksRestClient.class)) {};
  }

  @Test
  public void objectMapper_ignoreUnknownEnums() throws JsonProcessingException {
    String terminationInput = "{\"code\":\"unknownSillyCode\"}";
    TerminationReasonDTO test = service.mapper.readValue(terminationInput, TerminationReasonDTO.class);
    assertNull(test.getCode());
  }
}
