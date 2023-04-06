package com.edmunds.rest.databricks.DTO.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StorageConfigurationDTO {
  public static final String JSON_PROPERTY_STORAGE_CONFIGURATION_ID = "storage_configuration_id";
  public static final String JSON_PROPERTY_STORAGE_CONFIGURATION_NAME = "storage_configuration_name";
  public static final String JSON_PROPERTY_ROOT_BUCKET_INFO = "root_bucket_info";
  public static final String JSON_PROPERTY_ACCOUNT_ID = "account_id";
  public static final String JSON_PROPERTY_CREATION_TIME = "creation_time";

  @JsonProperty(JSON_PROPERTY_STORAGE_CONFIGURATION_ID)
  private String storageConfigurationId;

  @JsonProperty(JSON_PROPERTY_STORAGE_CONFIGURATION_NAME)
  private String storageConfigurationName;

  @JsonProperty(JSON_PROPERTY_ROOT_BUCKET_INFO)
  private BucketInfoDTO rootBucketInfo;

  @JsonProperty(JSON_PROPERTY_ACCOUNT_ID)
  private String accountId;

  @JsonProperty(JSON_PROPERTY_CREATION_TIME)
  private Long creationTime;
}
