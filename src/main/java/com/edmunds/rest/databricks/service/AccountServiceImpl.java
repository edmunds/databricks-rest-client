package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.account.BillableUsageDTO;
import com.edmunds.rest.databricks.DTO.account.WorkspaceDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class AccountServiceImpl extends DatabricksService implements AccountService {
  private static final String ACCOUNT_API_ENDPOINT = "/accounts/";

  private static final String getAccountApiEndpoint(String accountId, String commandName) {
    return ACCOUNT_API_ENDPOINT + accountId + "/" + commandName;
  }

  /**
   * Given a client, create a wrapper around it.
   *
   * @param client - databricksRestClient instance.
   * @see <a href = "https://docs.databricks.com/dev-tools/api/latest/account.html#section/Authentication">the client shall be authenticated using databricks account user/password</a>
   */
  public AccountServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  private Map<String, String> parseJson(String field) throws RuntimeException {
    try {
      return mapper.readValue(field, new TypeReference<java.util.Map<String, String>>() {});
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private BillableUsageDTO parse(CSVRecord record) throws RuntimeException {
    BillableUsageDTO usageDto = new BillableUsageDTO();
    usageDto.setWorkspaceId(record.get(BillableUsageDTO.CSV_PROPERTY_WORKSPACE_ID));
    usageDto.setTimestamp(OffsetDateTime.parse(record.get(BillableUsageDTO.CSV_PROPERTY_TIMESTAMP)));
    usageDto.setClusterId(record.get(BillableUsageDTO.CSV_PROPERTY_CLUSTER_ID));
    usageDto.setClusterName(record.get(BillableUsageDTO.CSV_PROPERTY_CLUSTER_NAME));
    usageDto.setClusterNodeType(record.get(BillableUsageDTO.CSV_PROPERTY_CLUSTER_NODE_TYPE));
    usageDto.setClusterOwnerUserId(record.get(BillableUsageDTO.CSV_PROPERTY_CLUSTER_OWNER_USER_ID));
    usageDto.setClusterCustomTags(parseJson(record.get(BillableUsageDTO.CSV_PROPERTY_CLUSTER_CUSTOM_TAGS)));
    usageDto.setSku(record.get(BillableUsageDTO.CSV_PROPERTY_SKU));
    usageDto.setDbus(java.lang.Double.valueOf(record.get(BillableUsageDTO.CSV_PROPERTY_DBUS)));
    usageDto.setMachineHours(java.lang.Double.valueOf(record.get(BillableUsageDTO.CSV_PROPERTY_MACHINE_HOURS)));
    usageDto.setClusterOwnerUserName(record.get(BillableUsageDTO.CSV_PROPERTY_CLUSTER_OWNER_USER_NAME));
    usageDto.setTags(parseJson(record.get(BillableUsageDTO.CSV_PROPERTY_TAGS)));

    return usageDto;
  }

  @Override
  public List<BillableUsageDTO> usages(String accountId, String startMonth, String endMonth, boolean personalData)
      throws IOException, DatabricksRestException {
    String commandName = String.format("%s?start_month=%s&end_month=%s&personal_data=%b",
        getAccountApiEndpoint(accountId, "usage/download"),
        startMonth, endMonth, personalData);
    byte[] response = client.performQuery(RequestMethod.GET, commandName);
    Reader responseReader = new InputStreamReader(new ByteArrayInputStream(response));
    CSVParser parser = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(responseReader);
    return parser.stream()
        .map(this::parse)
        .collect(Collectors.toList());
  }

  @Override
  public List<WorkspaceDTO> workspaces(String accountId) throws IOException, DatabricksRestException {
    byte[] response = client.performQuery(RequestMethod.GET, getAccountApiEndpoint(accountId, "workspaces"));
    List<WorkspaceDTO> workspacesDTO = mapper.readValue(response, new TypeReference<List<WorkspaceDTO>>() {});
    if (workspacesDTO == null) {
      return new ArrayList<>();
    }
    return workspacesDTO;
  }
}
