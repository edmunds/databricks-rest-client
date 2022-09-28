package com.edmunds.rest.databricks.DTO.dlt;

import java.io.Serializable;

/**
 *
 */
public enum PipelineUpdateResultStateDTO implements Serializable {
  QUEUED("QUEUED"),
  CREATED("CREATED"),
  WAITING_FOR_RESOURCES("WAITING_FOR_RESOURCES"),
  INITIALIZING("INITIALIZING"),
  RESETTING("RESETTING"),
  SETTING_UP_TABLES("SETTING_UP_TABLES"),
  RUNNING("RUNNING"),
  STOPPING("STOPPING"),
  COMPLETED("COMPLETED"),
  FAILED("FAILED"),
  CANCELED("CANCELED");

  private String value;

  PipelineUpdateResultStateDTO(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
