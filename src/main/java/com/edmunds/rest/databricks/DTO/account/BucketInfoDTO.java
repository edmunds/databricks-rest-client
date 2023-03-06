package com.edmunds.rest.databricks.DTO.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BucketInfoDTO {
  public static final String JSON_PROPERTY_BUCKET_NAME = "bucket_name";

  @JsonProperty(JSON_PROPERTY_BUCKET_NAME)
  private String bucketName;

}
