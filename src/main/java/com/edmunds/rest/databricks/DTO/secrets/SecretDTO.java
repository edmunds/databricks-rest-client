package com.edmunds.rest.databricks.DTO.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SecretDTO {

    public static final String JSON_PROPERTY_LAST_UPDATED_TIMESTAMP = "last_updated_timestamp";

    private String key;

    @JsonProperty(JSON_PROPERTY_LAST_UPDATED_TIMESTAMP)
    private long lastUpdatedTimestamp;

}
