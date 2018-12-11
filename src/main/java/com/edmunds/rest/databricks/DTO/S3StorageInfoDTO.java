/*
 * Copyright 2018 Edmunds.com, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.edmunds.rest.databricks.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 */
@Data
public class S3StorageInfoDTO implements Serializable {

  @JsonProperty("destination")
  private String destination;
  @JsonProperty("region")
  private String region;
  @JsonProperty("endpoint")
  private String endpoint;
  @JsonProperty("enable_encryption")
  private boolean enableEncryption;
  @JsonProperty("encryption_type")
  private String encryptionType;
  @JsonProperty("kms_key")
  private String kmsKey;
  @JsonProperty("canned_acl")
  private String cannedAcl;

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public boolean isEnableEncryption() {
    return enableEncryption;
  }

  public void setEnableEncryption(boolean enableEncryption) {
    this.enableEncryption = enableEncryption;
  }

  public String getEncryptionType() {
    return encryptionType;
  }

  public void setEncryptionType(String encryptionType) {
    this.encryptionType = encryptionType;
  }

  public String getKmsKey() {
    return kmsKey;
  }

  public void setKmsKey(String kmsKey) {
    this.kmsKey = kmsKey;
  }

  public String getCannedAcl() {
    return cannedAcl;
  }

  public void setCannedAcl(String cannedAcl) {
    this.cannedAcl = cannedAcl;
  }
}
