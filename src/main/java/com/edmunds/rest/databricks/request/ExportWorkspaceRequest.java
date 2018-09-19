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

import com.edmunds.rest.databricks.DTO.ExportFormatDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * Export Workspace Request object.
 * TODO Should be deprecated in favor of using DTOs.
 */
public class ExportWorkspaceRequest extends DatabricksRestRequest {

  private ExportWorkspaceRequest(Map<String, Object> data) {
    super(data);
  }

  /**
   * Builder.
   */
  public static class ExportWorkspaceRequestBuilder {

    private Map<String, Object> data = new HashMap<>();

    public ExportWorkspaceRequestBuilder(String path) {
      data.put("path", path);
    }

    public ExportWorkspaceRequestBuilder withFormat(ExportFormatDTO exportFormat) {
      data.put("format", exportFormat);
      return this;
    }

    public ExportWorkspaceRequestBuilder withDirectDownload(boolean directDownload) {
      data.put("direct_download", directDownload);
      return this;
    }

    public ExportWorkspaceRequest build() {
      return new ExportWorkspaceRequest(data);
    }
  }
}
