package com.edmunds.rest.databricks.DTO.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkspaceDTO {
  public static final String JSON_PROPERTY_WORKSPACE_ID = "workspace_id";
  public static final String JSON_PROPERTY_WORKSPACE_NAME = "workspace_name";
  public static final String JSON_PROPERTY_DEPLOYMENT_NAME = "deployment_name";
  public static final String JSON_PROPERTY_AWS_REGION = "aws_region";
  public static final String JSON_PROPERTY_CREDENTIALS_ID = "credentials_id";
  public static final String JSON_PROPERTY_STORAGE_CONFIGURATION_ID = "storage_configuration_id";
  public static final String JSON_PROPERTY_ACCOUNT_ID = "account_id";
  public static final String JSON_PROPERTY_WORKSPACE_STATUS = "workspace_status";
  public static final String JSON_PROPERTY_WORKSPACE_STATUS_MESSAGE = "workspace_status_message";
  public static final String JSON_PROPERTY_MANAGED_SERVICES_CUSTOMER_MANAGED_KEY_ID = "managed_services_customer_managed_key_id";
  public static final String JSON_PROPERTY_PRIVATE_ACCESS_SETTINGS_ID = "private_access_settings_id";
  public static final String JSON_PROPERTY_CREATION_TIME = "creation_time";
  public static final String JSON_PROPERTY_PRICING_TIER = "pricing_tier";

  @JsonProperty(JSON_PROPERTY_WORKSPACE_ID)
  private java.lang.Long workspaceId;

  @JsonProperty(JSON_PROPERTY_WORKSPACE_NAME)
  private String workspaceName;

  @JsonProperty(JSON_PROPERTY_DEPLOYMENT_NAME)
  private String deploymentName;

  @JsonProperty(JSON_PROPERTY_AWS_REGION)
  private String awsRegion;

  @JsonProperty(JSON_PROPERTY_CREDENTIALS_ID)
  private String credentialsId;

  @JsonProperty(JSON_PROPERTY_STORAGE_CONFIGURATION_ID)
  private String storageConfigurationId;

  @JsonProperty(JSON_PROPERTY_ACCOUNT_ID)
  private String accountId;

  @JsonProperty(JSON_PROPERTY_WORKSPACE_STATUS)
  private WorkspaceStatus workspaceStatus;

  @JsonProperty(JSON_PROPERTY_WORKSPACE_STATUS_MESSAGE)
  private String workspace_status_message;

  @JsonProperty(JSON_PROPERTY_MANAGED_SERVICES_CUSTOMER_MANAGED_KEY_ID)
  private String managedServicesCustomerManagedKeyId;

  @JsonProperty(JSON_PROPERTY_PRIVATE_ACCESS_SETTINGS_ID)
  private String privateAccessSettingsId;

  @JsonProperty(JSON_PROPERTY_CREATION_TIME)
  private java.lang.Long creationTime;

  @JsonProperty(JSON_PROPERTY_PRICING_TIER)
  private PricingTier pricingTier;
}
