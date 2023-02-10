package com.edmunds.rest.databricks.DTO.token.management;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenManagementListRequestParams {

  private String createdByUsername;
  private Long createdById;

}
