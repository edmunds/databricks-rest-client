package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateInfoWrapperDTO {
    public static final String JSON_PROPERTY_UPDATES = "updates";
    public static final String JSON_PROPERTY_NEXT_PAGE_TOKEN = "next_page_token";
    public static final String JSON_PROPERTY_PREV_PAGE_TOKEN = "prev_page_token";

    @JsonProperty(JSON_PROPERTY_UPDATES)
    private UpdateInfoDTO[] updates;

    @JsonProperty(JSON_PROPERTY_NEXT_PAGE_TOKEN)
    private String nextPageToken;

    @JsonProperty(JSON_PROPERTY_PREV_PAGE_TOKEN)
    private String prevPageToken;

}