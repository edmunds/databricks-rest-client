package com.edmunds.rest.databricks.DTO.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.edmunds.rest.databricks.DTO.secrets.ScopeBackendType.AZURE_KEYVAULT;

@Getter
@Setter
@NoArgsConstructor
public class NewAzureSecretScopeDTO extends NewSecretScopeDTO {
    public static final String JSON_PROPERTY_BACKEND_AZURE_KEYVAULT = "backend_azure_keyvault";
    public static final String JSON_PROPERTY_SCOPE_BACKEND_TYPE = "scope_backend_type";

    @JsonProperty(JSON_PROPERTY_SCOPE_BACKEND_TYPE)
    private final ScopeBackendType scopeBackendType = AZURE_KEYVAULT;

    @JsonProperty(JSON_PROPERTY_BACKEND_AZURE_KEYVAULT)
    private KeyvaultMetadataDTO backendAzureKeyvault;
}
