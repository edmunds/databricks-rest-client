package com.edmunds.rest.databricks.DTO.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


// TODO this should be abstract, and use implementations (DatabrickSecretScopeDTO, AWSSecretScopeDTO, Google, and Azure )
public abstract class NewSecretScopeDTO implements Serializable {

    public static final String JSON_PROPERTY_INITIAL_MANAGE_PRINCIPAL = "initial_manage_principal";
    protected String scope;

    @JsonProperty(JSON_PROPERTY_INITIAL_MANAGE_PRINCIPAL)
    protected String initialManagePrincipal;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getInitialManagePrincipal() {
        return initialManagePrincipal;
    }

    public void setInitialManagePrincipal(String initialManagePrincipal) {
        this.initialManagePrincipal = initialManagePrincipal;
    }
}
