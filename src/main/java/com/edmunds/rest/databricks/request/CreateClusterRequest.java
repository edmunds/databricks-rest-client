/*
 * Copyright 2018 Edmunds.com, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.edmunds.rest.databricks.request;

import com.edmunds.rest.databricks.DTO.AutoScaleDTO;
import com.edmunds.rest.databricks.DTO.AwsAttributesDTO;
import com.edmunds.rest.databricks.DTO.ClusterLogConfDTO;
import com.edmunds.rest.databricks.DTO.ClusterTagDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * A Cluster Request object.
 * TODO Should be deprecated in favor of using DTO objects.
 */
public class CreateClusterRequest extends DatabricksRestRequest {

  private CreateClusterRequest(Map<String, Object> data) {
    super(data);
  }

  /**
   * Builder.
   */
  public static class CreateClusterRequestBuilder {

    private Map<String, Object> data = new HashMap<>();

    /**
     * Constructor.
     */
    public CreateClusterRequestBuilder(int numWorkers, String clusterName, String sparkVersion,
        String nodeTypeId) {
      data.put("num_workers", numWorkers);
      data.put("cluster_name", clusterName);
      data.put("spark_version", sparkVersion);
      data.put("node_type_id", nodeTypeId);
    }

    /**
     * Constructor.
     */
    public CreateClusterRequestBuilder(AutoScaleDTO autoscale, String clusterName,
        String sparkVersion, String nodeTypeId) {
      data.put("autoscale", autoscale);
      data.put("cluster_name", clusterName);
      data.put("spark_version", sparkVersion);
      data.put("node_type_id", nodeTypeId);
    }

    public CreateClusterRequestBuilder withSparkConf(Map<String, String> sparkConf) {
      data.put("spark_conf", sparkConf);
      return this;
    }

    public CreateClusterRequestBuilder withAwsAttributes(AwsAttributesDTO awsAttributes) {
      data.put("aws_attributes", awsAttributes);
      return this;
    }

    public CreateClusterRequestBuilder withDriverNodeTypeId(String driverNodeTypeId) {
      data.put("driver_node_type_id", driverNodeTypeId);
      return this;
    }

    public CreateClusterRequestBuilder withSshPublicKeys(String[] sshPublicKeys) {
      data.put("ssh_public_keys", sshPublicKeys);
      return this;
    }

    public CreateClusterRequestBuilder withCustomTags(ClusterTagDTO[] customTags) {
      data.put("custom_tags", customTags);
      return this;
    }

    public CreateClusterRequestBuilder withClusterLogConf(ClusterLogConfDTO clusterLogConf) {
      data.put("cluster_log_conf", clusterLogConf);
      return this;
    }

    public CreateClusterRequestBuilder withSparkEnvVars(Map<String, String> sparkEnvVars) {
      data.put("spark_env_vars", sparkEnvVars);
      return this;
    }

    public CreateClusterRequestBuilder withAutoterminationMinutes(int autoterminationMinutes) {
      data.put("autotermination_minutes", autoterminationMinutes);
      return this;
    }

    public CreateClusterRequest build() {
      return new CreateClusterRequest(this.data);
    }
  }
}
