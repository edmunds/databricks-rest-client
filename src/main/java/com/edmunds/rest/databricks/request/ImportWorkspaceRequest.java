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
import com.edmunds.rest.databricks.DTO.LanguageDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * Import Workspace Request object.
 * Deprecated in favor of using DTOs
 */
@Deprecated
public class ImportWorkspaceRequest extends DatabricksRestRequest {

  private ImportWorkspaceRequest(Map<String, Object> data) {
    super(data);
  }

  /**
   * Builder.
   */
  public static class ImportWorkspaceRequestBuilder {

    private Map<String, Object> data = new HashMap<>();

    public ImportWorkspaceRequestBuilder(String path) {
      data.put("path", path);
    }

    public ImportWorkspaceRequestBuilder withFormat(ExportFormatDTO exportFormat) {
      data.put("format", exportFormat);
      return this;
    }

    public ImportWorkspaceRequestBuilder withLanguage(LanguageDTO language) {
      data.put("language", language);
      return this;
    }

    public ImportWorkspaceRequestBuilder withContent(byte[] content) {
      data.put("content", content);
      return this;
    }

    public ImportWorkspaceRequestBuilder withOverwrite(boolean overwrite) {
      data.put("overwrite", overwrite);
      return this;
    }

    public ImportWorkspaceRequest build() {
      return new ImportWorkspaceRequest(data);
    }
  }
}
