package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.dlt.PipelineDetailsDTO;
import com.edmunds.rest.databricks.DTO.dlt.PipelineEventsDTO;
import com.edmunds.rest.databricks.DTO.dlt.PipelinesDTO;
import com.edmunds.rest.databricks.DTO.dlt.UpdateDetailsResponseDTO;
import com.edmunds.rest.databricks.DTO.dlt.UpdateInfoWrapperDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of DLTService.
 */
public class DLTServiceImpl extends DatabricksService implements DLTService {

  public DLTServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public PipelinesDTO listPipelines() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/pipelines", new HashMap<>());
    return this.mapper.readValue(responseBody, PipelinesDTO.class);
  }

  @Override
  public PipelineDetailsDTO getPipelineDetails(String pipelineId) throws IOException, DatabricksRestException {
    String path = String.format("/pipelines/%s", pipelineId);
    byte[] responseBody = client.performQuery(RequestMethod.GET, path, new HashMap<>());
    return this.mapper.readValue(responseBody, PipelineDetailsDTO.class);
  }

  @Override
  public PipelineDetailsDTO updatePipeline(String pipelineId, Map<String, Object> data)
          throws IOException, DatabricksRestException {
    String path = String.format("/pipelines/%s", pipelineId);
    byte[] responseBody = client.performQuery(RequestMethod.PUT, path, data);
    return this.mapper.readValue(responseBody, PipelineDetailsDTO.class);
  }

  @Override
  public PipelineEventsDTO listPipelineEvents(String pipelineId, String since, String until, String pageToken)
          throws IOException, DatabricksRestException {

    Map<String, Object> data = new HashMap<>();
    data.put("max_results", 100);
    data.put("level", "INFO");
    if (pageToken != null) {
      data.put("page_token", pageToken);
    } else if (since != null || until != null) {
      String timestampFilterString;
      if (since != null && until != null) {
        timestampFilterString = String.format("timestamp >= '%s' AND timestamp < '%s'", since, until);
      } else if (since != null) {
        timestampFilterString = String.format("timestamp >= '%s'", since);
      } else {
        timestampFilterString = String.format("timestamp < '%s'", until);
      }
      String timestampFilterEncoded = URLEncoder.encode(timestampFilterString, StandardCharsets.UTF_8.toString());
      data.put("filter", timestampFilterEncoded);
    }
    //TODO: implement filter by event type if DeltaLiveTableAPI will allow it in the future

    String path = String.format("/pipelines/%s/events", pipelineId);
    byte[] responseBody = client.performQuery(RequestMethod.GET, path, data);
    return this.mapper.readValue(responseBody, PipelineEventsDTO.class);
  }

  @Override
  public UpdateDetailsResponseDTO getUpdateDetails(String pipelineId, String updateId)
          throws IOException, DatabricksRestException {
    String path = String.format("/pipelines/%s/updates/%s", pipelineId, updateId);
    byte[] responseBody = client.performQuery(RequestMethod.GET, path, new HashMap<>());
    return this.mapper.readValue(responseBody, UpdateDetailsResponseDTO.class);
  }

  @Override
  public UpdateInfoWrapperDTO listPipelineUpdates(String pipelineId, String pageToken)
          throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("max_results", 100);
    data.put("page_token", pageToken);

    String path = String.format("/pipelines/%s/updates", pipelineId);
    byte[] responseBody = client.performQuery(RequestMethod.GET, path, data);
    return this.mapper.readValue(responseBody, UpdateInfoWrapperDTO.class);
  }
}
