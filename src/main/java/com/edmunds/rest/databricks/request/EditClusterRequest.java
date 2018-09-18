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
 *
 */
public class EditClusterRequest extends DatabricksRestRequest {

  private EditClusterRequest(Map<String, Object> data) {
    super(data);
  }

  /**
   * Builder.
   */
  public static class EditClusterRequestBuilder {

    private Map<String, Object> data = new HashMap<>();

    public EditClusterRequestBuilder(int numWorkers, String clusterId, String clusterName, String
        sparkVersion, String nodeTypeId) {
      data.put("num_workers", numWorkers);
      data.put("cluster_id", clusterId);
      data.put("cluster_name", clusterName);
      data.put("spark_version", sparkVersion);
      data.put("node_type_id", nodeTypeId);
    }

    public EditClusterRequestBuilder(AutoScaleDTO autoscale, String clusterId, String clusterName,
        String
            sparkVersion, String nodeTypeId) {
      data.put("autoscale", autoscale);
      data.put("cluster_id", clusterId);
      data.put("cluster_name", clusterName);
      data.put("spark_version", sparkVersion);
      data.put("node_type_id", nodeTypeId);
    }

    public EditClusterRequestBuilder withSparkConf(Map<String, String> sparkConf) {
      data.put("spark_conf", sparkConf);
      return this;
    }

    public EditClusterRequestBuilder withAwsAttributes(AwsAttributesDTO awsAttributes) {
      data.put("aws_attributes", awsAttributes);
      return this;
    }

    public EditClusterRequestBuilder withDriverNodeTypeId(String driverNodeTypeId) {
      data.put("driver_node_type_id", driverNodeTypeId);
      return this;
    }

    public EditClusterRequestBuilder withSshPublicKeys(String[] sshPublicKeys) {
      data.put("ssh_public_keys", sshPublicKeys);
      return this;
    }

    public EditClusterRequestBuilder withCustomTags(ClusterTagDTO[] customTags) {
      data.put("custom_tags", customTags);
      return this;
    }

    public EditClusterRequestBuilder withClusterLogConf(ClusterLogConfDTO clusterLogConf) {
      data.put("cluster_log_conf", clusterLogConf);
      return this;
    }

    public EditClusterRequestBuilder withSparkEnvVars(Map<String, String> sparkEnvVars) {
      data.put("spark_env_vars", sparkEnvVars);
      return this;
    }

    public EditClusterRequestBuilder withAutoterminationMinutes(int autoterminationMinutes) {
      data.put("autotermination_minutes", autoterminationMinutes);
      return this;
    }

    public EditClusterRequestBuilder withEnableElasticDisk(boolean enableElasticDisk) {
      data.put("enable_elastic_disk", enableElasticDisk);
      return this;
    }

    public EditClusterRequest build() {
      return new EditClusterRequest(this.data);
    }
  }
}
