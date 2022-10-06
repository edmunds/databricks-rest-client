package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScripsDetailsDTO;
import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptDTO;
import com.edmunds.rest.databricks.DTO.globalInitScript.GlobalInitScriptIdDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;

public interface GlobalInitScriptService {

  GlobalInitScripsDetailsDTO listGlobalInitScripts() throws IOException, DatabricksRestException;

  GlobalInitScriptIdDTO createGlobalInitScript(GlobalInitScriptDTO globalInitScript) 
          throws IOException, DatabricksRestException;

  void deleteGlobalInitScript(String scriptId) throws DatabricksRestException;

}
