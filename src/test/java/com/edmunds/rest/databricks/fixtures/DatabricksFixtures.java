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

import com.edmunds.rest.databricks.DatabricksRestClient;
import com.edmunds.rest.databricks.DatabricksRestClientImpl;
import com.edmunds.rest.databricks.DatabricksRestClientImpl425;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Set;

/**
 * Created by shong on 7/21/16.
 */
public class DatabricksFixtures {
    private final static String API_VERSION = "2.0";

    private static DatabricksRestClient client;
    private static DatabricksServiceFactory factory;

    public static String USERNAME;
    public static String PASSWORD;
    public static String HOSTNAME;

    static {
        USERNAME = System.getenv("DB_USER");
        PASSWORD = System.getenv("DB_PASSWORD");
        HOSTNAME = System.getenv("DB_URL");
    }

    public static DatabricksRestClient getDatabricksRestClient() throws IOException {
        if (client == null) {
            client =  new DatabricksRestClientImpl(USERNAME, PASSWORD, HOSTNAME, API_VERSION, 1, 10);
        }
        return client;
    }

    /**
     * This is for retry test.
     * @param httpStatusCode
     * @return
     * @throws Exception
     */
    public static DatabricksRestClient createDatabricksRestClientWithRetryCode(int httpStatusCode) throws Exception {
        DatabricksRestClient databricksClient = new DatabricksRestClientImpl(USERNAME, PASSWORD, HOSTNAME,
            API_VERSION, 1, 10);

        addHttpStatus(databricksClient, httpStatusCode);

        return databricksClient;
    }

    public static DatabricksRestClient createDatabricksRestClient425WithRetryCode(int httpStatusCode) throws Exception {
        DatabricksRestClientImpl425 databricksClient = new DatabricksRestClientImpl425(USERNAME, PASSWORD, HOSTNAME,
            API_VERSION, 1, 10);

        addHttpStatus(databricksClient, httpStatusCode);

        return databricksClient;
    }

    private static void addHttpStatus(DatabricksRestClient databricksClient, int httpStatusCode) throws Exception {
        HttpServiceUnavailableRetryStrategy retryStrategy  = getHttpServiceUnavailableRetryStrategy(databricksClient);

        Field maxRetries = retryStrategy.getClass().getDeclaredField("maxRetries");
        maxRetries.setAccessible(true);
        maxRetries.set(retryStrategy, 1);

        Field retryInterval = retryStrategy.getClass().getDeclaredField("retryInterval");
        retryInterval.setAccessible(true);
        retryInterval.set(retryStrategy, 0l);

        Field fieldSet = retryStrategy.getClass().getDeclaredField("retryStatusSet");
        fieldSet.setAccessible(true);
        Set<Integer> set = (Set<Integer>)fieldSet.get(retryStrategy);
        set.add(httpStatusCode);
    }

    public static HttpServiceUnavailableRetryStrategy getHttpServiceUnavailableRetryStrategy(DatabricksRestClient clientImpl) throws Exception {
        Field field = clientImpl.getClass().getSuperclass().getDeclaredField("retryStrategy");
        field.setAccessible(true);
        return (HttpServiceUnavailableRetryStrategy)field.get(clientImpl);
    }

    public static DatabricksServiceFactory getDatabricksServiceFactory() {
        if (factory == null) {
            factory = new DatabricksServiceFactory(USERNAME, PASSWORD, HOSTNAME);
        }

        return factory;
    }
}
