package com.edmunds.rest.databricks.DTO.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class AccessControlRequestForGroupDTO extends AccessControlRequestDTO implements Serializable {
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("permission_level")
    private PermissionLevelDTO permissionLevel;
}
