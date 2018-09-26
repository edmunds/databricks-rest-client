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

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

/**
 * Created by shong on 7/21/16.
 */
public class DatabricksServiceFactoryTest {

  @Test
  public void testBuilder_withTokens() {
    DatabricksServiceFactory serviceFactory =
        DatabricksServiceFactory.Builder
            .createTokenAuthentication("myToken", "myHost")
            .withMaxRetries(5)
            .withRetryInterval(10000L)
            .build();
    assertNotNull(serviceFactory.getJobService());
    assertNotNull(serviceFactory.getLibraryService());
    assertNotNull(serviceFactory.getDbfsService());
    assertNotNull(serviceFactory.getJobService());
    assertNotNull(serviceFactory.getWorkspaceService());
  }

  @Test
  public void testBuilder_withPassword() {
    DatabricksServiceFactory serviceFactory =
        DatabricksServiceFactory.Builder
            .createUserPasswordAuthentication("myUser", "myPassword", "myHost")
            .withMaxRetries(5)
            .withRetryInterval(10000L)
            .build();
    assertNotNull(serviceFactory.getJobService());
    assertNotNull(serviceFactory.getLibraryService());
    assertNotNull(serviceFactory.getDbfsService());
    assertNotNull(serviceFactory.getJobService());
    assertNotNull(serviceFactory.getWorkspaceService());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testBuilderWithUsername_whenBlank_throwsIllegalArgumentException() {
    DatabricksServiceFactory serviceFactory =
        DatabricksServiceFactory.Builder
            .createUserPasswordAuthentication("", "", "myHost")
            .withMaxRetries(5)
            .withRetryInterval(10000L)
            .build();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testBuilderWithPassword_whenBlank_throwsIllegalArgumentException() {
    DatabricksServiceFactory serviceFactory =
        DatabricksServiceFactory.Builder
            .createTokenAuthentication("", "myHost")
            .withMaxRetries(5)
            .withRetryInterval(10000L)
            .build();
  }
}
