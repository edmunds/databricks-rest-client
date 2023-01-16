package com.edmunds.rest.databricks.DTO.scim.serviceprincipal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServicePrincipalGroupDTO {
    private String display;
    private String type;
    private String value;
}
