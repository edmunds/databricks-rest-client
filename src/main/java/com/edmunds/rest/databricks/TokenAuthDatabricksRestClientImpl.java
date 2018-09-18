package com.edmunds.rest.databricks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

public class TokenAuthDatabricksRestClientImpl extends DatabricksRestClientImpl {
  private static Logger logger = Logger.getLogger(DatabricksRestClientImpl.class.getName());


  public TokenAuthDatabricksRestClientImpl(String username, String personalToken, String host, String apiVersion,
      int maxRetry, long retryInterval) {
    super(username, personalToken, host, apiVersion, maxRetry, retryInterval);
  }

  @Override
  protected void init() {

    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(
        new AuthScope(host, HTTPS_PORT),
        new UsernamePasswordCredentials(username, password));

    RequestConfig defaultRequestConfig = RequestConfig.custom()
        .setExpectContinueEnabled(true)
        .setSocketTimeout(SOCKET_TIMEOUT)
        .setConnectTimeout(CONNECTION_TIMEOUT)
        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
        .build();

    List<Header> headers = new ArrayList<>();
    Header authHeader = new BasicHeader("Authorization", String.format("Bearer %s", password));
    headers.add(authHeader);

    HttpClientBuilder clientBuilder = HttpClients.custom()
        //.setDefaultCredentialsProvider(credsProvider)
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
}
