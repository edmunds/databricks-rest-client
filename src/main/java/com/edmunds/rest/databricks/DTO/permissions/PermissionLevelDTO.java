package com.edmunds.rest.databricks.DTO.permissions;

import java.io.Serializable;

public enum PermissionLevelDTO implements Serializable {
    CAN_MANAGE("CAN_MANAGE"), CAN_MANAGE_RUN("CAN_MANAGE_RUN"), CAN_VIEW("CAN_VIEW");
    private String value;

    PermissionLevelDTO(String value) {
    this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
