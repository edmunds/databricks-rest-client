package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.token.CreateTokenDTO;
import com.edmunds.rest.databricks.DTO.token.CreateTokenResponseDTO;
import com.edmunds.rest.databricks.DTO.token.TokenInfoDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;
import java.util.List;

/**
 * The wrapper around the databricks Token API.
 *
 * @see <a href="https://docs.databricks.com/dev-tools/api/latest/tokens.html#">https://docs.databricks.com/dev-tools/api/latest/tokens.html#</a>
 */
public interface TokenService {

  /**
   * Create a databricks token.
   *
   * @param createTokenDTO createToken object
   * @return POJO representing a token
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/tokens.html#create">https://docs.databricks.com/dev-tools/api/latest/tokens.html#create</a>
   */
  CreateTokenResponseDTO<TokenInfoDTO> createToken(CreateTokenDTO createTokenDTO)
      throws IOException, DatabricksRestException;

  /**
   * List databricks tokens.
   *
   * @return List of tokens
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/tokens.html#list">https://docs.databricks.com/dev-tools/api/latest/tokens.html#list</a>
   */
  List<TokenInfoDTO> listTokens() throws IOException, DatabricksRestException;

  /**
   * Revoke databricks token by id.
   *
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/tokens.html#revoke">https://docs.databricks.com/dev-tools/api/latest/tokens.html#revoke</a>
   */
  void revokeToken(String tokenId) throws DatabricksRestException;
}

