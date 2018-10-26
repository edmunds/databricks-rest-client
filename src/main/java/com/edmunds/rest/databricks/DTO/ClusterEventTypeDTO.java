package com.edmunds.rest.databricks.DTO;

import java.io.Serializable;

/**
 *
 */
public enum ClusterEventTypeDTO implements Serializable {
    CREATING("CREATING"),
    STARTING("STARTING"),
    RESTARTING("RESTARTING"),
    TERMINATING("TERMINATING"),
    EDITED("EDITED"),
    RUNNING("RUNNING"),
    RESIZING("RESIZING"),
    UPSIZE_COMPLETED("UPSIZE_COMPLETED"),
    NODES_LOST("NODES_LOST");

    private String value;

    ClusterEventTypeDTO(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
