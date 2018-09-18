package com.edmunds.rest.databricks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class TokenAuthDatabricksRestClientImpl extends AbstractDatabricksRestClientImpl {
  private static Logger logger = Logger.getLogger(TokenAuthDatabricksRestClientImpl.class.getName());

  protected final String personalToken;

  public TokenAuthDatabricksRestClientImpl(String personalToken, String host,
      String apiVersion, int maxRetry, long retryInterval) {
    super(host, apiVersion, maxRetry, retryInterval);
    this.personalToken = personalToken;
    init();
  }

  @Override
  protected void init() {

    RequestConfig defaultRequestConfig = RequestConfig.custom()
        .setExpectContinueEnabled(true)
        .setSocketTimeout(SOCKET_TIMEOUT)
        .setConnectTimeout(CONNECTION_TIMEOUT)
        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
        .build();

    List<Header> headers = new ArrayList<>();
    Header authHeader = new BasicHeader("Authorization", String.format("Bearer %s", personalToken));
    headers.add(authHeader);

    HttpClientBuilder clientBuilder = HttpClients.custom()
        .setRetryHandler(retryHandler)
        .setServiceUnavailableRetryStrategy(retryStrategy)
        .setDefaultRequestConfig(defaultRequestConfig)
        .setDefaultHeaders(headers);

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
