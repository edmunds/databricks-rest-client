package com.edmunds.rest.databricks.DTO.scim.serviceprincipal;

public class CreateServicePrincipalInnerMappingDTO {
    private String value;

    public CreateServicePrincipalInnerMappingDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
