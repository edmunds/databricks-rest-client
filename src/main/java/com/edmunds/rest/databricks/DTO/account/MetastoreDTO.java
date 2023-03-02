package com.edmunds.rest.databricks.DTO.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetastoreDTO {
  public static final String JSON_PROPERTY_NAME = "name";
  public static final String JSON_PROPERTY_STORAGE_ROOT = "storage_root";
  public static final String JSON_PROPERTY_DEFAULT_DATA_ACCESS_CONFIG_ID = "default_data_access_config_id";
  public static final String JSON_PROPERTY_METASTORE_ID = "metastore_id";
  public static final String JSON_PROPERTY_CREATED_AT = "created_at";
  public static final String JSON_PROPERTY_CREATED_BY = "created_by";
  public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
  public static final String JSON_PROPERTY_UPDATED_BY = "updated_by";
  public static final String JSON_PROPERTY_OWNER = "owner";
  public static final String JSON_PROPERTY_REGION = "region";
  public static final String JSON_PROPERTY_STORAGE_ROOT_CREDENTIAL_ID = "storage_root_credential_id";
  public static final String JSON_PROPERTY_CLOUD = "cloud";
  public static final String JSON_PROPERTY_GLOBAL_METASTORE_ID = "global_metastore_id";
  public static final String JSON_PROPERTY_DELTA_SHARING_SCOPE = "delta_sharing_scope";
  public static final String JSON_PROPERTY_PRIVILEGE_MODEL_VERSION = "privilege_model_version";
  public static final String JSON_PROPERTY_STORAGE_ROOT_CREDENTIAL_NAME = "storage_root_credential_name";
  public static final String JSON_PROPERTY_FULL_NAME = "full_name";
  public static final String JSON_PROPERTY_SECURABLE_TYPE = "securable_type";
  public static final String JSON_PROPERTY_SECURABLE_KIND = "securable_kind";

  @JsonProperty(JSON_PROPERTY_NAME)
  private String name;

  @JsonProperty(JSON_PROPERTY_STORAGE_ROOT)
  private String storageRoot;

  @JsonProperty(JSON_PROPERTY_DEFAULT_DATA_ACCESS_CONFIG_ID)
  private String defaultDataAccessConfigId;

  @JsonProperty(JSON_PROPERTY_METASTORE_ID)
  private String metastoreId;

  @JsonProperty(JSON_PROPERTY_CREATED_AT)
  private String createdAt;

  @JsonProperty(JSON_PROPERTY_CREATED_BY)
  private String createdBy;

  @JsonProperty(JSON_PROPERTY_UPDATED_AT)
  private String updatedAt;

  @JsonProperty(JSON_PROPERTY_UPDATED_BY)
  private String updatedBy;

  @JsonProperty(JSON_PROPERTY_OWNER)
  private String owner;

  @JsonProperty(JSON_PROPERTY_REGION)
  private String region;

  @JsonProperty(JSON_PROPERTY_STORAGE_ROOT_CREDENTIAL_ID)
  private String storageRootCredentialId;

  @JsonProperty(JSON_PROPERTY_CLOUD)
  private String cloud;

  @JsonProperty(JSON_PROPERTY_GLOBAL_METASTORE_ID)
  private String globalMetastoreId;

  @JsonProperty(JSON_PROPERTY_DELTA_SHARING_SCOPE)
  private String deltaSharingScope;

  @JsonProperty(JSON_PROPERTY_PRIVILEGE_MODEL_VERSION)
  private String privilegeModelVersion;

  @JsonProperty(JSON_PROPERTY_STORAGE_ROOT_CREDENTIAL_NAME)
  private String storageRootCredentialName;

  @JsonProperty(JSON_PROPERTY_FULL_NAME)
  private String fullName;

  @JsonProperty(JSON_PROPERTY_SECURABLE_TYPE)
  private String securableType;

  @JsonProperty(JSON_PROPERTY_SECURABLE_KIND)
  private String securableKind;
}
