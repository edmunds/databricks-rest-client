package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.unity.MetastoreDTO;
import com.edmunds.rest.databricks.DTO.unity.MetastoreUuidDTO;
import com.edmunds.rest.databricks.DTO.unity.WorkspaceIdDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnityCatalogServiceImpl extends DatabricksService implements UnityCatalogService {
  private static String ACCOUNT_API_ENDPOINT = "/accounts/";

  private static String getAccountApiEndpoint(String accountId, String commandName) {
    return ACCOUNT_API_ENDPOINT + accountId + "/" + commandName;
  }

  /**
   * Given a client, create a wrapper around it.
   *
   * @param client - databricksRestClient instance.
   * @see <a href = "https://docs.databricks.com/dev-tools/api/latest/account.html#section/Authentication">the client shall be authenticated using databricks account user/password</a>
   */
  public UnityCatalogServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public List<MetastoreDTO> metastores(String accountId) throws IOException, DatabricksRestException {
    byte[] response =
        client.performQuery(RequestMethod.GET, getAccountApiEndpoint(accountId, "metastores"));
    List<MetastoreDTO> metastoresDTO =
        mapper.readValue(response, new TypeReference<List<MetastoreDTO>>() {});
    if (metastoresDTO == null) {
      return new ArrayList<>();
    }
    return metastoresDTO;
  }

  @Override
  public MetastoreDTO metastore(String accountId, String metastoreId) throws IOException, DatabricksRestException {
    String commandName = String.format("metastores/%s", metastoreId);
    byte[] response =
        client.performQuery(RequestMethod.GET, getAccountApiEndpoint(accountId, commandName));
    return mapper.readValue(response, new TypeReference<MetastoreDTO>() {});
  }

  @Override
  public List<MetastoreUuidDTO> workspaceMetastoreId(String accountId, String workspaceId)
      throws IOException, DatabricksRestException {
    String commandName = String.format("workspaces/%s/metastore", workspaceId);
    byte[] response =
        client.performQuery(RequestMethod.GET, getAccountApiEndpoint(accountId, commandName));
    List<MetastoreUuidDTO> metastoreIds =
        mapper.readValue(response, new TypeReference<List<MetastoreUuidDTO>>() {});
    if (metastoreIds == null) {
      return new ArrayList<>();
    }
    return metastoreIds;
  }

  @Override
  public List<WorkspaceIdDTO> metastoreWorkspaceIds(String accountId, String metastoreId)
      throws IOException, DatabricksRestException {
    String commandName = String.format("metastores/%s/workspaces", metastoreId);
    byte[] response =
        client.performQuery(RequestMethod.GET, getAccountApiEndpoint(accountId, commandName));
    List<WorkspaceIdDTO> metastoreIds = mapper.readValue(response, new TypeReference<List<WorkspaceIdDTO>>() {});
    if (metastoreIds == null) {
      return new ArrayList<>();
    }
    return metastoreIds;
  }
}
