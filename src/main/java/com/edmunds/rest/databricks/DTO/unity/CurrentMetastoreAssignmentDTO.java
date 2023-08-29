package com.edmunds.rest.databricks.DTO.unity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentMetastoreAssignmentDTO {

  public static final String JSON_PROPERTY_METASTORE_ID = "metastore_id";
  public static final String JSON_PROPERTY_WORKSPACE_ID = "workspace_id";
  public static final String JSON_PROPERTY_DEFAULT_CATALOG_NAME = "default_catalog_name";

  @JsonProperty(JSON_PROPERTY_METASTORE_ID)
  private String metastoreId;

  @JsonProperty(JSON_PROPERTY_WORKSPACE_ID)
  private long workspaceId;

  @JsonProperty(JSON_PROPERTY_DEFAULT_CATALOG_NAME)
  private String defaultCatalogName;
}
