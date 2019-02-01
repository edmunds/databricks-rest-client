package com.edmunds.rest.databricks.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */
@Data
public class PrincipalNameDTO implements Serializable {
    @Getter @Setter @JsonProperty("user_name")
    private String userName;
    @Getter @Setter @JsonProperty("group_name")
    private String groupName;
}
