package com.edmunds.rest.databricks.DTO.instance_pools;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstancePoolsDTO {

    @JsonProperty("instance_pools")
    private InstancePoolAndStatsDTO[] instancePools;

}
