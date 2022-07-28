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

package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.account.BillableUsageDTO;
import com.edmunds.rest.databricks.DTO.account.WorkspaceDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;
import java.util.List;

/**
 * A Wrapper around the account part of the databricks rest api.
 *
 * @see <a href="https://docs.databricks.com/dev-tools/api/latest/account.html">https://docs.databricks.com/dev-tools/api/latest/account.html</a>
 */
public interface AccountService {

  /**
   * Return billable usage logs.
   *
   * @param accountId    Databricks account ID of any type. For non-E2 account types, get your account ID from the <a href="https://docs.databricks.com/administration-guide/account-settings/usage.html">Accounts Console</a>
   * @param startMonth   Format: YYYY-MM. First month to return billable usage logs for.
   * @param endMonth     Format: YYYY-MM. Last month to return billable usage logs for.
   * @param personalData Specify whether to include personally identifiable information in the billable usage logs,
   *                     for example the email addresses of cluster creators. Handle this information with care.
   *                     Defaults to false.
   * @return list of billable usages
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/account.html#operation/download-billable-usage">https://docs.databricks.com/dev-tools/api/latest/account.html#operation/download-billable-usage</a>
   */
  List<BillableUsageDTO> usages(String accountId, String startMonth, String endMonth, boolean personalData)
      throws IOException, DatabricksRestException;

  /**
   * Get a list of all workspaces associated with an account, specified by ID.
   * This operation is available only if your account is on the E2 version of the platform or on a select custom plan
   * that allows multiple workspaces per account.
   *
   * @param accountId Databricks account ID of any type. For non-E2 account types, get your account ID from the <a href="https://docs.databricks.com/administration-guide/account-settings/usage.html">Accounts Console</a>
   * @return list of workspaces belonging to the databricks account
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/account.html#operation/get-workspaces">https://docs.databricks.com/dev-tools/api/latest/account.html#operation/get-workspaces</a>
   */
  List<WorkspaceDTO> workspaces(String accountId) throws IOException, DatabricksRestException;

}
