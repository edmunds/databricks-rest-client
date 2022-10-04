package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScripsDetailsDTO;
import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptDTO;
import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptIdDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GlobalInitScriptServiceImpl extends DatabricksService implements GlobalInitScriptService {

  public GlobalInitScriptServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public GlobalInitScripsDetailsDTO listGlobalInitScripts() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/global-init-scripts", new HashMap<>());
    return this.mapper.readValue(responseBody, GlobalInitScripsDetailsDTO.class);
  }

  @Override
  public GlobalInitScriptIdDTO createGlobalInitScript(GlobalInitScriptDTO globalInitScript)
          throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("enabled", globalInitScript.isEnabled());
    data.put("name", globalInitScript.getName());
    data.put("position", globalInitScript.getPosition());
    String encodedScript = Base64.getEncoder().encodeToString(globalInitScript.getScript().getBytes());
    data.put("script", encodedScript);

    byte[] responseBody = client.performQuery(RequestMethod.POST, "/global-init-scripts", data);
    return this.mapper.readValue(responseBody, GlobalInitScriptIdDTO.class);
  }

  @Override
  public void deleteGlobalInitScript(String scriptId) throws DatabricksRestException {
    String path = String.format("/global-init-scripts/%s", scriptId);
    client.performQuery(RequestMethod.DELETE, path, new HashMap<>());
  }
}
