package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.dlt.PipelineDetailsDTO;
import com.edmunds.rest.databricks.DTO.dlt.PipelineEventsDTO;
import com.edmunds.rest.databricks.DTO.dlt.PipelinesDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;

public interface DLTService {

  PipelinesDTO listPipelines() throws IOException, DatabricksRestException;

  PipelineDetailsDTO getPipelineDetails(String pipelineId) throws IOException, DatabricksRestException;

  PipelineDetailsDTO updatePipeline(PipelineDetailsDTO pipelineDetailsDTO) throws IOException, DatabricksRestException;

  PipelineEventsDTO listPipelineEvents(String pipelineId, String since, String until, String pageToken)
          throws IOException, DatabricksRestException;

}
