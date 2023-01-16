package com.edmunds.rest.databricks.DTO.scim.serviceprincipal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ServicePrincipalDTO {

    private long id;

    private String applicationId;

    private String displayName;

    private boolean active;

    private List<ServicePrincipalGroupDTO> groups;
}
