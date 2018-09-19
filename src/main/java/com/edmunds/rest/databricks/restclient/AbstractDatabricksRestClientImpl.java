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
import com.edmunds.rest.databricks.HttpServiceUnavailableRetryStrategy;
import com.edmunds.rest.databricks.RequestMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.log4j.Logger;

/**
 * This is the abstract databricks rest client that contains base functionality.
 */
public abstract class AbstractDatabricksRestClientImpl implements DatabricksRestClient {

  protected static final int HTTPS_PORT = 443;
  protected static final int SOCKET_TIMEOUT = 10000;
  protected static final int CONNECTION_TIMEOUT = 10000;
  protected static final int CONNECTION_REQUEST_TIMEOUT = 10000;
  private static Logger logger = Logger.getLogger(AbstractDatabricksRestClientImpl.class.getName());
  protected final String apiVersion;
  protected final String host;

  protected String url;
  protected ObjectMapper mapper;
  protected HttpClient client;

  protected HttpRequestRetryHandler retryHandler;
  protected ServiceUnavailableRetryStrategy retryStrategy;

  /**
   * Creates a rest client.
   * @param host databricks host
   * @param apiVersion databricks api version
   * @param maxRetry how many retries
   * @param retryInterval interval btween retries
   */
  public AbstractDatabricksRestClientImpl(String host, String apiVersion, int maxRetry, long retryInterval) {
    this.host = host;
    this.apiVersion = apiVersion;
    this.retryHandler = new StandardHttpRequestRetryHandler(maxRetry, false);
    this.retryStrategy = new HttpServiceUnavailableRetryStrategy(maxRetry, retryInterval);
  }

  protected byte[] extractContent(HttpResponse httpResponse)
      throws IOException, DatabricksRestException {

    int status = httpResponse.getStatusLine().getStatusCode();
    if (status != HttpStatus.SC_OK) {
      logger.error("HTTP Response error : " + httpResponse.getStatusLine());
      String response = IOUtils.toString(httpResponse.getEntity().getContent(), "utf-8");
      throw new DatabricksRestException("Databricks Rest API returned error: \"" + response + "\"");
    }

    return IOUtils.toByteArray(httpResponse.getEntity().getContent());
  }

  public String getHost() {
    return host;
  }

  protected HttpRequestBase makeHttpMethod(RequestMethod requestMethod, String path,
      Map<String, Object> data)
      throws UnsupportedEncodingException, JsonProcessingException {
    if (requestMethod == RequestMethod.GET) {
      return makeGetMethod(path, data);
    }

    if (requestMethod == RequestMethod.POST) {
      return makePostMethod(path, data);
    }

    throw new IllegalArgumentException(requestMethod + " is not a valid request method");
  }

  protected HttpGet makeGetMethod(String path, Map<String, Object> data) {
    String commands = makeCommands(data);
    HttpGet method = new HttpGet(url + path + commands);
    logger.info(method.toString());

    return method;
  }

  protected String makeCommands(Map<String, Object> data) {
    if (data == null || data.isEmpty()) {
      return "";
    }

    StringBuilder commands = new StringBuilder();
    commands.append("?");

    String commandSeparator = "";

    for (Map.Entry<String, Object> entry : data.entrySet()) {
      commands.append(commandSeparator);
      commandSeparator = "&";
      commands.append(entry.getKey());
      commands.append("=");
      commands.append(entry.getValue());
    }

    return commands.toString();
  }

  protected HttpPost makePostMethod(String path, Map<String, Object> data)
      throws UnsupportedEncodingException, JsonProcessingException {
    HttpPost method = new HttpPost(url + path);
    logger.info(method.toString());

    StringEntity requestEntity = makeStringRequestEntity(data);
    method.setEntity(requestEntity);
    method.setHeader("Accept", "application/json");
    method.setHeader("Content-type", "application/json");
    return method;
  }

  protected StringEntity makeStringRequestEntity(Map<String, Object> data)
      throws UnsupportedEncodingException, JsonProcessingException {
    String body = mapper.writeValueAsString(data);
    logger.info(body);

    return new StringEntity(body);
  }
}