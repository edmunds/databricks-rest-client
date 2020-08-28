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
import java.util.Collections;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
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
  public DatabricksRestClientImpl(DatabricksServiceFactory.Builder builder, HttpClientBuilderFactory clientFactory) {
    super(
        builder.getHost(),
        builder.getApiVersion()
    );

    if (isNotEmpty(builder.getToken())
        || (isNotEmpty(builder.getUsername()) && isNotEmpty(builder.getPassword()))) {
      initClient(clientFactory);

    } else {
      throw new IllegalArgumentException("Token or username/password must be set!");
    }

  }


  protected void initClient(HttpClientBuilderFactory httpClientBuilderFactory) {
    client = httpClientBuilderFactory.createClientBuilder().build();

    url = String.format("https://%s/api/%s", host, apiVersion);
    mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
  }

  public byte[] performQuery(RequestMethod requestMethod, String path) throws DatabricksRestException {
    return performQuery(requestMethod, path, Collections.emptyMap());
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