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
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.edmunds.rest.databricks.restclient.DatabricksRestClientImpl;
import com.edmunds.rest.databricks.restclient.DefaultHttpClientBuilderFactory;
import com.edmunds.rest.databricks.restclient.HttpClientBuilderFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Set;

/**
 * Created by shong on 7/21/16.
 */
public class DatabricksFixtures {
  private final static String API_VERSION = "2.0";
  public static String HOSTNAME;
  public static String TOKEN;
  private static DatabricksRestClient client;
  private static DatabricksRestClient tokenAuthClient;
  private static DatabricksRestClient db425Client;
  private static DatabricksServiceFactory factory;

  static {
    HOSTNAME = System.getenv("DB_URL");
    TOKEN = System.getenv("DB_TOKEN");
  }

  public static DatabricksRestClient getDatabricksRestClient() throws IOException {
    if (client == null) {
      DatabricksServiceFactory.Builder builder = DatabricksServiceFactory.Builder
              .createTokenAuthentication(TOKEN, HOSTNAME)
              .withMaxRetries(1)
              .withRetryInterval(10)
              .withApiVersion(API_VERSION);
      HttpClientBuilderFactory factory = new DefaultHttpClientBuilderFactory(builder);
      client = new DatabricksRestClientImpl(builder, factory);
    }

    return client;
  }

  public static DatabricksRestClient createTokenAuthRestClient() {
    if (tokenAuthClient == null) {
      DatabricksServiceFactory.Builder builder = DatabricksServiceFactory.Builder
              .createTokenAuthentication(TOKEN, HOSTNAME)
              .withMaxRetries(1)
              .withRetryInterval(10)
              .withApiVersion(API_VERSION);
      HttpClientBuilderFactory factory = new DefaultHttpClientBuilderFactory(builder);
      tokenAuthClient = new DatabricksRestClientImpl(builder, factory);
    }

    return tokenAuthClient;
  }

  /**
   * This is for retry test.
   *
   * @param httpStatusCode
   * @return
   * @throws Exception
   */
  public static DatabricksRestClient createDatabricksRestClientWithRetryCode(int httpStatusCode)
      throws Exception {

    DatabricksServiceFactory.Builder builder = DatabricksServiceFactory.Builder
            .createTokenAuthentication(TOKEN, HOSTNAME)
            .withMaxRetries(1)
            .withRetryInterval(10)
            .withApiVersion(API_VERSION);
    HttpClientBuilderFactory factory = new DefaultHttpClientBuilderFactory(builder);
    DatabricksRestClient databricksClient = new DatabricksRestClientImpl(builder, factory);

    addHttpStatus(databricksClient, httpStatusCode);

    return databricksClient;
  }

  private static void addHttpStatus(DatabricksRestClient databricksClient, int httpStatusCode)
      throws Exception {
    HttpServiceUnavailableRetryStrategy retryStrategy = getHttpServiceUnavailableRetryStrategy(databricksClient);

    Field maxRetries = retryStrategy.getClass().getDeclaredField("maxRetries");
    maxRetries.setAccessible(true);
    maxRetries.set(retryStrategy, 1);

    Field retryInterval = retryStrategy.getClass().getDeclaredField("retryInterval");
    retryInterval.setAccessible(true);
    retryInterval.set(retryStrategy, 0l);

    Field fieldSet = retryStrategy.getClass().getDeclaredField("retryStatusSet");
    fieldSet.setAccessible(true);
    Set<Integer> set = (Set<Integer>) fieldSet.get(retryStrategy);
    set.add(httpStatusCode);
  }

  public static HttpServiceUnavailableRetryStrategy getHttpServiceUnavailableRetryStrategy(DatabricksRestClient clientImpl)
      throws Exception {
    Field field = clientImpl.getClass().getSuperclass().getDeclaredField("retryStrategy");
    field.setAccessible(true);
    return (HttpServiceUnavailableRetryStrategy) field.get(clientImpl);
  }

  public static DatabricksServiceFactory getDatabricksServiceFactory() {
    if (factory == null) {
      factory = DatabricksServiceFactory.Builder.createTokenAuthentication(TOKEN, HOSTNAME)
              .withMaxRetries(3)
              .withSoTimeout((int)Duration.ofMinutes(1L).toMillis())
              .withConnectionRequestTimeout((int)Duration.ofMinutes(1L).toMillis())
              .withRetryInterval((int)Duration.ofSeconds(5L).toMillis())
              .build();
    }

    return factory;
  }
}
