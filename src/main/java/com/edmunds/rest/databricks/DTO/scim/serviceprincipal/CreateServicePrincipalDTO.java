package com.edmunds.rest.databricks.DTO.scim.serviceprincipal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = "schemas")
//note - it's not very clear what's the schema to be used right now - the documentation shows a different
//value compared with the actual implementation so in this version schemas is not used for eq/hashcode
@ToString
public class CreateServicePrincipalDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String[] schemas = new String[]{"urn:ietf:params:scim:schemas:core:2.0:User"};

    private String applicationId;

    private String displayName;

    private List<CreateServicePrincipalInnerMappingDTO> groups;

    @JsonProperty("entitlements")
    private List<CreateServicePrincipalInnerMappingDTO> entitlements = new ArrayList<>();

    public CreateServicePrincipalDTO(String applicationId, String displayName, List<CreateServicePrincipalInnerMappingDTO> groups) {
        this.applicationId = applicationId;
        this.displayName = displayName;
        this.groups = groups;
    }
}
