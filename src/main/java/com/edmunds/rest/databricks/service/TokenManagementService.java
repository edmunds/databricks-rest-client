package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.token.CreateTokenResponseDTO;
import com.edmunds.rest.databricks.DTO.token.management.CreateOnBehalfOfTokenDTO;
import com.edmunds.rest.databricks.DTO.token.management.TokenManagementInfoDTO;
import com.edmunds.rest.databricks.DTO.token.management.TokenManagementListRequestParams;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;
import java.util.List;


/**
 * The wrapper around the databricks Token Management API.
 *
 * @see <a href="https://docs.databricks.com/dev-tools/api/latest/token-management.html#">https://docs.databricks.com/dev-tools/api/latest/token-management.html#</a>
 */
public interface TokenManagementService {

  /**
   * Create a databricks token.
   *
   * @param onBehalfOfTokenDTO create on behalf of token object
   * @return POJO representing an obo token
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/create-obo-token">https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/create-obo-token</a>
   */
  CreateTokenResponseDTO<TokenManagementInfoDTO> createOnBehalfOfToken(CreateOnBehalfOfTokenDTO onBehalfOfTokenDTO)
      throws IOException, DatabricksRestException;

  /**
   * List databricks tokens.
   *
   * @param listRequestParams - Used to filter tokens by creator id and/or creator username
   * @return List of all tokens in workspace
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/get-tokens">https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/get-tokens</a>
   */
  List<TokenManagementInfoDTO> listTokens(TokenManagementListRequestParams listRequestParams)
      throws IOException, DatabricksRestException;

  /**
   * List databricks tokens.
   *
   * @return List of all tokens in workspace
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/get-tokens">https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/get-tokens</a>
   */
  List<TokenManagementInfoDTO> listTokens() throws IOException, DatabricksRestException;

  /**
   * Delete databricks token by id.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/delete-token">https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/delete-token</a>
   */
  void deleteToken(String tokenId) throws IOException, DatabricksRestException;

  /**
   * Get databricks token by id.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/get-token">https://docs.databricks.com/dev-tools/api/latest/token-management.html#operation/get-token</a>
   */
  TokenManagementInfoDTO getToken(String tokenId) throws IOException, DatabricksRestException;
}
