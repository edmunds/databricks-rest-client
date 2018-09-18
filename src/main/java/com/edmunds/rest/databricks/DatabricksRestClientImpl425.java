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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.SecureRandom;
import java.util.Map;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * To run on CDH5.7.1, use httpclient4.2.5 version API
 */
public final class DatabricksRestClientImpl425 extends AbstractDatabricksRestClientImpl {

  private static Logger logger = Logger.getLogger(DatabricksRestClientImpl425.class.getName());


  public DatabricksRestClientImpl425(String username, String password, String host,
      String apiVersion, int maxRetry, long retryInterval) {
    super(username, password, host, apiVersion, maxRetry, retryInterval);
  }

  protected void init() {
    try {

      SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
      sslContext.init(null, null, new SecureRandom());

      SSLSocketFactory sf = new SSLSocketFactory(sslContext);
      Scheme httpsScheme = new Scheme("https", HTTPS_PORT, sf);
      SchemeRegistry schemeRegistry = new SchemeRegistry();
      schemeRegistry.register(httpsScheme);
      ClientConnectionManager cm = new BasicClientConnectionManager(schemeRegistry);

      HttpParams params = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
      HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);

      DefaultHttpClient defaultHttpClient = new DefaultHttpClient(cm, params);
      defaultHttpClient.setHttpRequestRetryHandler(retryHandler);

      defaultHttpClient.getCredentialsProvider().setCredentials(
          new AuthScope(host, HTTPS_PORT),
          new UsernamePasswordCredentials(username, password));

      client = new AutoRetryHttpClient(defaultHttpClient, retryStrategy);

    } catch (Exception e) {
      logger.error("", e);
    }

    url = String.format("https://%s/api/%s", host, apiVersion);
    mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
  }


  public byte[] performQuery(RequestMethod requestMethod, String path, Map<String, Object> data)
      throws
      DatabricksRestException {

    HttpRequestBase method = null;
    try {
      method = makeHttpMethod(requestMethod, path, data);
      HttpResponse httpResponse = client.execute(method);

      byte[] response = extractContent(httpResponse);

      EntityUtils.consumeQuietly(httpResponse.getEntity());

      return response;

    } catch (DatabricksRestException dre) {
      throw dre;
    } catch (Exception e) {
      throw new DatabricksRestException(e);
    } finally {
      if (method != null) {
        method.releaseConnection();
      }
    }
  }

}