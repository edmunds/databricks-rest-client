package com.edmunds.rest.databricks.DTO.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SecretScopeDTO implements Serializable {
    public static final String JSON_PROPERTY_BACKEND_TYPE = "backend_type";
    public static final String JSON_PROPERTY_KEYVAULT_METADATA = "keyvault_metadata";

    private String name;

    @JsonProperty(JSON_PROPERTY_BACKEND_TYPE)
    private ScopeBackendType backendType;

    @JsonProperty(JSON_PROPERTY_KEYVAULT_METADATA)
    private KeyvaultMetadataDTO keyvaultMetadataDTO;
}
