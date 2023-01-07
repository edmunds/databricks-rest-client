package com.edmunds.rest.databricks.DTO.scim.serviceprincipal;

public class ServicePrincipalInnerMappingDTO {
    private String value;

    public ServicePrincipalInnerMappingDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
