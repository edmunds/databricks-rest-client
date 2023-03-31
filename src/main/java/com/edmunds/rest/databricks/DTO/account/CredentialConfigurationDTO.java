package com.edmunds.rest.databricks.DTO.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CredentialConfigurationDTO {
  public static final String JSON_PROPERTY_CREDENTIALS_ID = "credentials_id";
  public static final String JSON_PROPERTY_CREDENTIALS_NAME = "credentials_name";
  public static final String JSON_PROPERTY_ACCOUNT_ID = "account_id";
  public static final String JSON_PROPERTY_AWS_CREDENTIALS = "aws_credentials";
  public static final String JSON_PROPERTY_CREATION_TIME = "creation_time";

  @JsonProperty(JSON_PROPERTY_CREDENTIALS_ID)
  private String credentialsId;

  @JsonProperty(JSON_PROPERTY_CREDENTIALS_NAME)
  private String credentialsName;

  @JsonProperty(JSON_PROPERTY_ACCOUNT_ID)
  private String accountId;

  @JsonProperty(JSON_PROPERTY_AWS_CREDENTIALS)
  private AwsCredentialsDTO awsCredentials;

  @JsonProperty(JSON_PROPERTY_CREATION_TIME)
  private Long creationTime;
}
