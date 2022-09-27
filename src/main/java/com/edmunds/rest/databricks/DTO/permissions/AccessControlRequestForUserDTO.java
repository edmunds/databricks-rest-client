package com.edmunds.rest.databricks.DTO.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class AccessControlRequestForUserDTO extends AccessControlRequestDTO implements Serializable {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("permission_level")
    private PermissionLevelDTO permissionLevel;
}
