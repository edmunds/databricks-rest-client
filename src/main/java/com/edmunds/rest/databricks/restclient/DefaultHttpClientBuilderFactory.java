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

import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

/**
 * A default http client builder implementation.
 */
public class DefaultHttpClientBuilderFactory implements HttpClientBuilderFactory {

  private static Logger logger = Logger.getLogger(DefaultHttpClientBuilderFactory.class.getName());
  protected static final int HTTPS_PORT = 443;
  protected DatabricksServiceFactory.Builder builder;

  /**
   * Creates a default http client builder factory using a DatabricksServiceFactory builder for config.
   *
   * @param builder the databricks service factory builder object.
   */
  public DefaultHttpClientBuilderFactory(DatabricksServiceFactory.Builder builder) {
    this.builder = builder;
  }

  /**
   * Creates a request config.
   */
  public RequestConfig createRequestConfig() {
    return RequestConfig.custom()
        .setExpectContinueEnabled(true)
        .setSocketTimeout(builder.getSoTimeout())
        .setConnectTimeout(builder.getConnectionTimeout())
        .setConnectionRequestTimeout(builder.getConnectionRequestTimeout())
        .build();
  }

  @Override
  public HttpClientBuilder createClientBuilder() {
    return createDefaultClientBuilder();
  }

  /**
   * Creates a default http client builder object.
   */
  public HttpClientBuilder createDefaultClientBuilder() {
    HttpClientBuilder clientBuilder = HttpClients.custom().useSystemProperties()
        .setRetryHandler(getRetryHandler())
        .setServiceUnavailableRetryStrategy(getServiceUnavailableRetryStrategy())
        .setDefaultRequestConfig(createRequestConfig());

    List<Header> headers = new ArrayList<>();
    if (isNotEmpty(builder.getToken())) {
      Header authHeader = new BasicHeader("Authorization", String.format("Bearer %s", builder.getToken()));
      headers.add(authHeader);
    } else { // password authorization
      CredentialsProvider credsProvider = new BasicCredentialsProvider();
      credsProvider.setCredentials(
          new AuthScope(builder.getHost(), HTTPS_PORT),
          new UsernamePasswordCredentials(builder.getUsername(), builder.getPassword()));

      clientBuilder.setDefaultCredentialsProvider(credsProvider);

    }

    String userAgent = builder.getUserAgent();
    if (userAgent != null && userAgent.length() > 0) {
      Header userAgentHeader = new BasicHeader("User-Agent", userAgent);
      headers.add(userAgentHeader);
    }

    if (!headers.isEmpty()) {
      clientBuilder.setDefaultHeaders(headers);
    }

    try {
      SSLContext ctx = SSLContext.getDefault();
      // Allow TLSv1.2 protocol only
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
          ctx,
          new String[]{"TLSv1.2"},
          null,
          SSLConnectionSocketFactory.getDefaultHostnameVerifier());
      clientBuilder = clientBuilder.setSSLSocketFactory(sslsf);
    } catch (Exception e) {
      logger.error("", e);
    }
    return clientBuilder;
  }

  static boolean isNotEmpty(String str) {
    return str != null && str.length() > 0;
  }

  /**
   * Creates a retry handler.
   */
  public HttpRequestRetryHandler getRetryHandler() {
    return new StandardHttpRequestRetryHandler(builder.getMaxRetries(), builder.isRequestSentRetryEnabled());
  }

  /**
   * Creates a service unavailable strategy.
   */
  public ServiceUnavailableRetryStrategy getServiceUnavailableRetryStrategy() {
    return new HttpServiceUnavailableRetryStrategy(builder.getMaxRetries(), builder.getRetryInterval());
  }
}
