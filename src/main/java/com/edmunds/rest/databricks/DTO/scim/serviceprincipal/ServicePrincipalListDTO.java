package com.edmunds.rest.databricks.DTO.scim.serviceprincipal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServicePrincipalListDTO {

    @JsonProperty("Resources")
    private List<ServicePrincipalDTO> resources = new ArrayList<>();

}
