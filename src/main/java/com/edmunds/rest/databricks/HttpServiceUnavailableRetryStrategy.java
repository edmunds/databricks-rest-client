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

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Retry Strategy when get HTTP response status code.
 * ref> https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
 */
public class HttpServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy {

    private static Logger logger = Logger.getLogger(HttpServiceUnavailableRetryStrategy.class.getName());

    private static final Set<Integer> retryStatusSet = new HashSet<>();
    static {
        retryStatusSet.add(408); // Request Timeout
        retryStatusSet.add(429); // Too Many Requests

        retryStatusSet.add(500); // Internal Server Error
        retryStatusSet.add(503); // Service Unavailable
        retryStatusSet.add(504); // Gateway Timeout
    }

    // milliseconds
    private long retryInterval;
    private int maxRetries;

    private int executeCount = 0;

    public HttpServiceUnavailableRetryStrategy() {
        this(3, 10000L);
    }

    public HttpServiceUnavailableRetryStrategy(int maxRetries, long retryInterval){
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
    }

    @Override
    public boolean retryRequest(final HttpResponse response, final int executionCount, final HttpContext context) {
        this.executeCount = executionCount;

        boolean isRetry = executionCount <= this.maxRetries && retryStatusSet.contains(response.getStatusLine().getStatusCode());
        if(isRetry) {
            logger.warn("Retry HttpRequest " + executionCount + "th. statusCode=" + response.getStatusLine().getStatusCode());
        }

        return isRetry;
    }

    @Override
    public long getRetryInterval() {
        return retryInterval;
    }

    public int getExecuteCount(){
        return executeCount;
    }
}
