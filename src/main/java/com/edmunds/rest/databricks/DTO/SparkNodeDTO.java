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
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Data
public class SparkNodeDTO implements Serializable {

  @Getter @Setter @JsonProperty("private_ip")
  private String privateIp;
  @Getter @Setter @JsonProperty("public_dns")
  private String publicDns;
  @Getter @Setter @JsonProperty("node_id")
  private String nodeId;
  @Getter @Setter @JsonProperty("instance_id")
  private String instanceId;
  @Getter @Setter @JsonProperty("start_timestamp")
  private long startTimestamp;
  @Getter @Setter @JsonProperty("node_aws_attributes")
  private SparkNodeAwsAttributesDTO nodeAwsAttributes;
  @Getter @Setter @JsonProperty("host_private_ip")
  private String hostPrivateIp;
}
