package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptDTO;
import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptDetailsDTO;
import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptIdDTO;
import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptsDetailsDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;

public interface GlobalInitScriptService {

  GlobalInitScriptsDetailsDTO listGlobalInitScripts() throws IOException, DatabricksRestException;

  GlobalInitScriptDetailsDTO getGlobalInitScript(String scriptId) throws IOException, DatabricksRestException;

  GlobalInitScriptIdDTO createGlobalInitScript(GlobalInitScriptDTO globalInitScript)
          throws IOException, DatabricksRestException;

  GlobalInitScriptIdDTO updateGlobalInitScript(String scriptId, GlobalInitScriptDTO globalInitScript)
          throws IOException, DatabricksRestException;

  void deleteGlobalInitScript(String scriptId) throws DatabricksRestException;

}
