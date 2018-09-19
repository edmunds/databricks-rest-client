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

import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * The main implementation of databricks rest client, which uses up to date httpclient.
 */
public class DatabricksRestClientImpl extends AbstractDatabricksRestClientImpl {

  private static Logger logger = Logger.getLogger(DatabricksRestClientImpl.class.getName());

  private DatabricksRestClientImpl(String host, String apiVersion, int maxRetry,
      long retryInterval) {
    super(host, apiVersion, maxRetry, retryInterval);
  }

  /**
   * Constructs a rest client with user and password authentication.
   */
  public static DatabricksRestClientImpl createClientWithUserPassword(String username,
      String password, String host,
      String apiVersion, int maxRetry, long retryInterval) {
    DatabricksRestClientImpl client = new DatabricksRestClientImpl(host, apiVersion, maxRetry,
        retryInterval);
    client.initClientWithUserPassword(username, password);
    return client;
  }

  /**
   * Constructs a rest client with token authentication.
   */
  public static DatabricksRestClientImpl createClientWithTokenAuthentication(String token,
      String host,
      String apiVersion, int maxRetry, long retryInterval) {
    DatabricksRestClientImpl client = new DatabricksRestClientImpl(host, apiVersion, maxRetry,
        retryInterval);
    client.initClientWithToken(token);
    return client;
  }

  protected void initClientWithUserPassword(String username, String password) {
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(
        new AuthScope(host, HTTPS_PORT),
        new UsernamePasswordCredentials(username, password));

    HttpClientBuilder clientBuilder = HttpClients.custom()
        .setDefaultCredentialsProvider(credsProvider)
        .setRetryHandler(retryHandler)
        .setServiceUnavailableRetryStrategy(retryStrategy)
        .setDefaultRequestConfig(createRequestConfig());

    commonInit(clientBuilder);
  }

  protected void initClientWithToken(String personalToken) {
    List<Header> headers = new ArrayList<>();
    Header authHeader = new BasicHeader("Authorization", String.format("Bearer %s", personalToken));
    headers.add(authHeader);

    HttpClientBuilder clientBuilder = HttpClients.custom()
        .setRetryHandler(retryHandler)
        .setServiceUnavailableRetryStrategy(retryStrategy)
        .setDefaultRequestConfig(createRequestConfig())
        .setDefaultHeaders(headers);

    commonInit(clientBuilder);
  }

  protected void commonInit(HttpClientBuilder clientBuilder) {
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

    client = clientBuilder.build(); //CloseableHttpClient

    url = String.format("https://%s/api/%s", host, apiVersion);
    mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
  }

  private RequestConfig createRequestConfig() {
    RequestConfig defaultRequestConfig = RequestConfig.custom()
        .setExpectContinueEnabled(true)
        .setSocketTimeout(SOCKET_TIMEOUT)
        .setConnectTimeout(CONNECTION_TIMEOUT)
        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
        .build();
    return defaultRequestConfig;
  }

  @Override
  public byte[] performQuery(RequestMethod requestMethod, String path, Map<String, Object> data)
      throws
      DatabricksRestException {

    CloseableHttpResponse httpResponse = null;
    try {
      HttpRequestBase method = makeHttpMethod(requestMethod, path, data);
      httpResponse = ((CloseableHttpClient) client).execute(method);

      byte[] response = extractContent(httpResponse);

      EntityUtils.consumeQuietly(httpResponse.getEntity());

      return response;
    } catch (DatabricksRestException dre) {
      throw dre;
    } catch (Exception e) {
      throw new DatabricksRestException(e);
    } finally {
      try {
        if (httpResponse != null) {
          httpResponse.close();
        }
      } catch (IOException ioe) {
        logger.debug("ignore close error", ioe);
      }
    }
  }
}