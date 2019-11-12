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
import com.edmunds.rest.databricks.DatabricksServiceFactory;
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

  /**
   * Constructs a rest client.
   */
  public DatabricksRestClientImpl(DatabricksServiceFactory.Builder builder) {
    super(
            builder.getHost(),
            builder.getApiVersion(),
            builder.getMaxRetries(),
            builder.getRetryInterval(),
            builder.isRequestSentRetryEnabled()
    );

    if (isNotEmpty(builder.getToken())
            || (isNotEmpty(builder.getUsername()) && isNotEmpty(builder.getPassword()))) {
      initClient(builder);

    } else {
      throw new IllegalArgumentException("Token or username/password must be set!");
    }

  }



  protected void initClient(DatabricksServiceFactory.Builder builder) {

    HttpClientBuilder clientBuilder = HttpClients.custom().useSystemProperties()
            .setRetryHandler(retryHandler)
            .setServiceUnavailableRetryStrategy(retryStrategy)
            .setDefaultRequestConfig(createRequestConfig(builder));

    List<Header> headers = new ArrayList<>();
    if (isNotEmpty(builder.getToken())) {
      Header authHeader = new BasicHeader("Authorization", String.format("Bearer %s", builder.getToken()));
      headers.add(authHeader);
    } else { // password authorization
      CredentialsProvider credsProvider = new BasicCredentialsProvider();
      credsProvider.setCredentials(
              new AuthScope(host, HTTPS_PORT),
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

    client = clientBuilder.build(); //CloseableHttpClient

    url = String.format("https://%s/api/%s", host, apiVersion);
    mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
  }


  private RequestConfig createRequestConfig(DatabricksServiceFactory.Builder builder) {
    return RequestConfig.custom()
        .setExpectContinueEnabled(true)
        .setSocketTimeout(builder.getSoTimeout())
        .setConnectTimeout(builder.getConnectionTimeout())
        .setConnectionRequestTimeout(builder.getConnectionRequestTimeout())
        .build();
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