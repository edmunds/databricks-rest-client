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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the abstract databricks rest client that contains base functionality.
 */
public abstract class AbstractDatabricksRestClientImpl implements DatabricksRestClient {

  private static Logger logger = LogManager.getLogger(AbstractDatabricksRestClientImpl.class.getName());

  protected final String apiVersion;
  protected final String host;

  protected String url;
  protected ObjectMapper mapper;
  protected HttpClient client;

  /**
   * Creates a rest client.
   *
   * @param host       databricks host
   * @param apiVersion databricks api version
   */
  public AbstractDatabricksRestClientImpl(
      String host,
      String apiVersion
  ) {
    this.host = host;
    this.apiVersion = apiVersion;
  }

  static boolean isNotEmpty(String str) {
    return str != null && str.length() > 0;
  }

  protected byte[] extractContent(HttpResponse httpResponse)
      throws IOException, DatabricksRestException {

    int status = httpResponse.getStatusLine().getStatusCode();
    if ((status != HttpStatus.SC_OK) && (status != HttpStatus.SC_CREATED) && (status != HttpStatus.SC_NO_CONTENT)) {
      logger.error("HTTP Response error : " + httpResponse.getStatusLine());
      if (status == HttpStatus.SC_FORBIDDEN) {
        throw new DatabricksRestException("Databricks Rest API returned the error: User not authorized");
      } else {
        String response = IOUtils.toString(httpResponse.getEntity().getContent(), "utf-8");
        throw new DatabricksRestException("Databricks Rest API returned error: \"" + response + "\"");
      }
    }
    HttpEntity entity = httpResponse.getEntity();
    return entity == null ? null : IOUtils.toByteArray(entity.getContent());
  }

  public String getHost() {
    return host;
  }

  protected HttpRequestBase makeHttpMethod(RequestMethod requestMethod, String path,
      Map<String, Object> data)
      throws UnsupportedEncodingException, JsonProcessingException {

    switch (requestMethod) {
      case POST:
        return makePostMethod(path, data);
      case GET:
        return makeGetMethod(path, data);
      case PATCH:
        return makePatchMethod(path, data);
      case DELETE:
        return makeDeleteMethod(path);
      case PUT:
        return makePutMethod(path, data);
      default:
        throw new IllegalArgumentException(requestMethod + " is not a valid request method");
    }
  }

  protected HttpGet makeGetMethod(String path, Map<String, Object> data) {
    String commands = makeCommands(data);
    HttpGet method = new HttpGet(url + path + commands);
    logger.info(method.toString());

    return method;
  }

  protected HttpDelete makeDeleteMethod(String path) {
    HttpDelete method = new HttpDelete(url + path);
    logger.info(method.toString());

    method.setHeader("Accept", "application/json");
    method.setHeader("Content-type", "application/json");
    return method;
  }

  protected HttpPatch makePatchMethod(String path, Map<String, Object> data)
      throws UnsupportedEncodingException, JsonProcessingException {
    HttpPatch method = new HttpPatch(url + path);
    logger.info(method.toString());

    StringEntity requestEntity = makeStringRequestEntity(data);
    method.setEntity(requestEntity);
    method.setHeader("Accept", "application/json");
    method.setHeader("Content-type", "application/json");
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

  protected HttpPut makePutMethod(String path, Map<String, Object> data)
      throws UnsupportedEncodingException, JsonProcessingException {
    HttpPut method = new HttpPut(url + path);
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
