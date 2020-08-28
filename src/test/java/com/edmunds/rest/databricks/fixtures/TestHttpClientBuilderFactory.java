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

package com.edmunds.rest.databricks.fixtures;

import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy;
import com.edmunds.rest.databricks.restclient.DefaultHttpClientBuilderFactory;
import java.lang.reflect.Field;
import java.util.Set;
import org.apache.http.client.ServiceUnavailableRetryStrategy;

public class TestHttpClientBuilderFactory extends DefaultHttpClientBuilderFactory {

  int statusCode;
  HttpServiceUnavailableRetryStrategy strategy;

  public TestHttpClientBuilderFactory(int statusCode, DatabricksServiceFactory.Builder builder) {
    super(builder);
    this.statusCode = statusCode;
    this.strategy = createTestStrategy();
  }

  @Override
  public ServiceUnavailableRetryStrategy getServiceUnavailableRetryStrategy() {
    return strategy;
  }

  public HttpServiceUnavailableRetryStrategy createTestStrategy() {
    HttpServiceUnavailableRetryStrategy retryStrategy = (HttpServiceUnavailableRetryStrategy) super.getServiceUnavailableRetryStrategy();
    try {
      Field maxRetries = retryStrategy.getClass().getDeclaredField("maxRetries");
      maxRetries.setAccessible(true);
      maxRetries.set(retryStrategy, 1);

      Field retryInterval = retryStrategy.getClass().getDeclaredField("retryInterval");
      retryInterval.setAccessible(true);
      retryInterval.set(retryStrategy, 0l);

      Field fieldSet = retryStrategy.getClass().getDeclaredField("retryStatusSet");
      fieldSet.setAccessible(true);
      Set<Integer> set = (Set<Integer>) fieldSet.get(retryStrategy);
      set.add(statusCode);
    } catch (NoSuchFieldException | IllegalAccessException e) {

    }
    return retryStrategy;
  }
}
