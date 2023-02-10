package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.token.CreateTokenDTO;
import com.edmunds.rest.databricks.DTO.token.CreateTokenResponseDTO;
import com.edmunds.rest.databricks.DTO.token.TokenInfoDTO;
import com.edmunds.rest.databricks.DTO.token.TokenListResponseDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TokenServiceImpl extends DatabricksService implements TokenService {

  private static final String TOKEN_API_ENDPOINT = "/token";

  private static final String TOKEN_API_CREATE_ENDPOINT = TOKEN_API_ENDPOINT + "/create";
  private static final String TOKEN_API_LIST_ENDPOINT = TOKEN_API_ENDPOINT + "/list";
  private static final String TOKEN_API_REVOKE_ENDPOINT = TOKEN_API_ENDPOINT + "/delete";

  /**
   * Given a client, create a wrapper around it.
   *
   * @param client - databricks client
   */
  public TokenServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public CreateTokenResponseDTO<TokenInfoDTO> createToken(CreateTokenDTO createTokenDTO)
      throws IOException, DatabricksRestException {
    String marshalled = mapper.writeValueAsString(createTokenDTO);
    Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
    });
    byte[] response = client.performQuery(RequestMethod.POST, TOKEN_API_CREATE_ENDPOINT, data);
    return mapper.readValue(response, new TypeReference<CreateTokenResponseDTO<TokenInfoDTO>>() {
    });
  }

  @Override
  public List<TokenInfoDTO> listTokens() throws IOException, DatabricksRestException {

    byte[] response = client.performQuery(RequestMethod.GET, TOKEN_API_LIST_ENDPOINT);
    TokenListResponseDTO<TokenInfoDTO> tokenList =
        mapper.readValue(response, new TypeReference<TokenListResponseDTO<TokenInfoDTO>>() {
        });

    return Optional
        .ofNullable(tokenList)
        .map(TokenListResponseDTO::getTokenInfos)
        .orElseGet(ArrayList::new);
  }


  @Override
  public void revokeToken(String tokenId) throws DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("token_id", tokenId);
    client.performQuery(RequestMethod.POST, TOKEN_API_REVOKE_ENDPOINT, data);
  }
}


