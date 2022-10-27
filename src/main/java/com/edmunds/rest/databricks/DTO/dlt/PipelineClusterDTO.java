package com.edmunds.rest.databricks.DTO.dlt;

import com.edmunds.rest.databricks.DTO.clusters.AutoScaleDTO;
import com.edmunds.rest.databricks.DTO.clusters.AwsAttributesDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterLogConfDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PipelineClusterDTO {
    public static final String JSON_PROPERTY_LABEL = "label";
    public static final String JSON_PROPERTY_NODE_TYPE_ID = "node_type_id";
    public static final String JSON_PROPERTY_DRIVER_NODE_TYPE_ID = "driver_node_type_id";
    public static final String JSON_PROPERTY_AUTOSCADE = "autoscale";
    public static final String JSON_PROPERTY_NUM_WORKERS = "num_workers";
    public static final String JSON_PROPERTY_SPARK_CONF = "spark_conf";
    public static final String JSON_PROPERTY_AWS_ATTRIBUTES = "aws_attributes";
    public static final String JSON_PROPERTY_SSH_PUBLIC_KEYS = "ssh_public_keys";
    public static final String JSON_PROPERTY_CUSTOM_TAGS = "custom_tags";
    public static final String JSON_PROPERTY_CLUSTER_LOG_CONF = "cluster_log_conf";
    public static final String JSON_PROPERTY_SPARK_ENV_VARS = "spark_env_vars";
    public static final String JSON_PROPERTY_INSTANCE_POOL_ID = "instance_pool_id";
    public static final String JSON_PROPERTY_DRIVER_INSTANCE_POOL_ID = "driver_instance_pool_id";
    public static final String JSON_PROPERTY_POLICY_ID = "policy_id";
    public static final String JSON_PROPERTY_APPLY_POLICY_DEFAULT_VALUES = "apply_policy_default_values";

    @JsonProperty(JSON_PROPERTY_LABEL)
    private String label;

    @JsonProperty(JSON_PROPERTY_NODE_TYPE_ID)
    private String nodeTypeId;

    @JsonProperty(JSON_PROPERTY_DRIVER_NODE_TYPE_ID)
    private String driverNodeTypeId;

    @JsonProperty(JSON_PROPERTY_AUTOSCADE)
    private AutoScaleDTO autoscale;

    @JsonProperty(JSON_PROPERTY_NUM_WORKERS)
    private Integer numWorkers;

    @JsonProperty(JSON_PROPERTY_SPARK_CONF)
    private Map<String, String> sparkConf ;

    @JsonProperty(JSON_PROPERTY_AWS_ATTRIBUTES)
    private AwsAttributesDTO awsAttributes;

    @JsonProperty(JSON_PROPERTY_SSH_PUBLIC_KEYS)
    private String[] sshPublicKeys ;

    @JsonProperty(JSON_PROPERTY_CUSTOM_TAGS)
    private Map<String, String> customTags ;

    @JsonProperty(JSON_PROPERTY_CLUSTER_LOG_CONF)
    private ClusterLogConfDTO clusterLogConf ;

    @JsonProperty(JSON_PROPERTY_SPARK_ENV_VARS)
    private Map<String, String> sparkEnvVars;

    @JsonProperty(JSON_PROPERTY_INSTANCE_POOL_ID)
    private String instancePoolId;

    @JsonProperty(JSON_PROPERTY_DRIVER_INSTANCE_POOL_ID)
    private String driverInstancePoolId;

    @JsonProperty(JSON_PROPERTY_POLICY_ID)
    private String policyId;

    @JsonProperty(JSON_PROPERTY_APPLY_POLICY_DEFAULT_VALUES)
    private Boolean applyPolicyDefaultValues;
}