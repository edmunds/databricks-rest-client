package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.dlt.PipelineDetailsDTO;
import com.edmunds.rest.databricks.DTO.dlt.PipelineEventsDTO;
import com.edmunds.rest.databricks.DTO.dlt.PipelinesDTO;
import com.edmunds.rest.databricks.DTO.dlt.UpdateDetailsResponseDTO;
import com.edmunds.rest.databricks.DTO.dlt.UpdateInfoWrapperDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;
import java.util.Map;

public interface DLTService {

  PipelinesDTO listPipelines() throws IOException, DatabricksRestException;

  PipelineDetailsDTO getPipelineDetails(String pipelineId) throws IOException, DatabricksRestException;

  PipelineDetailsDTO updatePipeline(String pipelineId, Map<String, Object> data)
          throws IOException, DatabricksRestException;

  PipelineEventsDTO listPipelineEvents(String pipelineId, String since, String until, String pageToken)
          throws IOException, DatabricksRestException;

  UpdateDetailsResponseDTO getUpdateDetails(String pipelineId, String updateId)
          throws IOException, DatabricksRestException;

  UpdateInfoWrapperDTO listPipelineUpdates(String pipelineId, String pageToken)
          throws IOException, DatabricksRestException;

}
