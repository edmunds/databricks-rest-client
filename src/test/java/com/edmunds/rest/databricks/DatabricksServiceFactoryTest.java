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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Created by shong on 7/21/16.
 */
public class DatabricksServiceFactoryTest {
  private DatabricksServiceFactory factory;

  @BeforeClass
  public void setUpOnce() {
    factory = new DatabricksServiceFactory("", "", "");
  }

  @Test
  public void getService_whenCalled_returnsANotNullObject() {
    assertNotNull(factory.getClusterService());
    assertNotNull(factory.getLibraryService());
    assertNotNull(factory.getDbfsService());
    assertNotNull(factory.getJobService());
    assertNotNull(factory.getWorkspaceService());
  }
}
