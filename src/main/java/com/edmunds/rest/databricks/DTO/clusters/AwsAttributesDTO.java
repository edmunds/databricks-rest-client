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

package com.edmunds.rest.databricks.DTO.clusters;

import com.edmunds.rest.databricks.DTO.EbsVolumeTypeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 */
@Data
public class AwsAttributesDTO implements Serializable {

  @JsonProperty("first_on_demand")
  private int firstOnDemand;
  @JsonProperty("availability")
  private AwsAvailabilityDTO availability;
  @JsonProperty("zone_id")
  private String zoneId;
  @JsonProperty("instance_profile_arn")
  private String instanceProfileArn;
  @JsonProperty("spot_bid_price_percent")
  private int spotBidPricePercent;
  @JsonProperty("ebs_volume_type")
  private EbsVolumeTypeDTO ebsVolumeType;
  @JsonProperty("ebs_volume_count")
  private int ebsVolumeCount;
  @JsonProperty("ebs_volume_size")
  private int ebsVolumeSize;

}
