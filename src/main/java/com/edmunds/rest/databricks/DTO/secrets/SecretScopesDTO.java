package com.edmunds.rest.databricks.DTO.secrets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SecretScopesDTO implements Serializable {
    private SecretScopeDTO[] scopes = new SecretScopeDTO[]{};
}
