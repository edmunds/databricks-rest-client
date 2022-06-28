package com.edmunds.rest.databricks.DTO.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewSecretDTO {
    public static final String JSON_PROPERTY_STRING_VALUE = "string_value";

    private String scope;
    private String key;

    @JsonProperty(JSON_PROPERTY_STRING_VALUE)
    private String stringValue;

}
