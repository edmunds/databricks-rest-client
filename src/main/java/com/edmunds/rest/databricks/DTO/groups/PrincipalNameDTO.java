package com.edmunds.rest.databricks.DTO.groups;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 */
@Data
public class PrincipalNameDTO implements Serializable {

  @JsonProperty("user_name")
  private String userName;
  @JsonProperty("group_name")
  private String groupName;

}
