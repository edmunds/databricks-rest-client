package com.edmunds.rest.databricks.DTO.secrets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SecretsDTO {

    private SecretDTO[] secrets = new SecretDTO[]{};
}
