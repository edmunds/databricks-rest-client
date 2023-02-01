package com.edmunds.rest.databricks.DTO.jobs;

import java.io.Serializable;

public enum DataSecurityModeDTO implements Serializable {
    SINGLE_USER("SINGLE_USER"),
    USER_ISOLATION("USER_ISOLATION"),
    LEGACY_PASSTHROUGH("LEGACY_PASSTHROUGH"),
    LEGACY_TABLE_ACL("LEGACY_TABLE_ACL");
    private String value;

    DataSecurityModeDTO(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
