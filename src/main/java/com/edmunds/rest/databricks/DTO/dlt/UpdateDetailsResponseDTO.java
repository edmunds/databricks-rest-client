package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateDetailsResponseDTO {
    public static final String JSON_PROPERTY_UPDATE = "update";

    @JsonProperty(JSON_PROPERTY_UPDATE)
    private UpdateDetailsDTO update;

}
