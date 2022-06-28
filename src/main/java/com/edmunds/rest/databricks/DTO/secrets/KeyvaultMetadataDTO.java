package com.edmunds.rest.databricks.DTO.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeyvaultMetadataDTO {

    public static final String JSON_PROPERTY_RESOURCE_ID = "resource_id";
    public static final String JSON_PROPERTY_DNS_NAME = "dns_name";

    @JsonProperty(JSON_PROPERTY_RESOURCE_ID)
    private String resourceId;

    @JsonProperty(JSON_PROPERTY_DNS_NAME)
    private String dnsName;
}
