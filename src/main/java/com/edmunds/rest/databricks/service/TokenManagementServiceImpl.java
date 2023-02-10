package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.token.CreateTokenResponseDTO;
import com.edmunds.rest.databricks.DTO.token.TokenGetResponseDTO;
import com.edmunds.rest.databricks.DTO.token.TokenListResponseDTO;
import com.edmunds.rest.databricks.DTO.token.management.CreateOnBehalfOfTokenDTO;
import com.edmunds.rest.databricks.DTO.token.management.TokenManagementInfoDTO;
import com.edmunds.rest.databricks.DTO.token.management.TokenManagementListRequestParams;
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

public class TokenManagementServiceImpl extends DatabricksService implements TokenManagementService {

  private static final String TOKEN_MANAGEMENT_API = "/token-management";
  private static final String TOKEN_MANAGEMENT_TOKENS_API = TOKEN_MANAGEMENT_API + "/tokens";
  private static final String TOKEN_MANAGEMENT_ON_BEHALF_OF_API = TOKEN_MANAGEMENT_API + "/on-behalf-of/tokens";

  public TokenManagementServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public CreateTokenResponseDTO<TokenManagementInfoDTO> createOnBehalfOfToken(CreateOnBehalfOfTokenDTO obo)
      throws IOException, DatabricksRestException {
    String marshalled = mapper.writeValueAsString(obo);
    Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
    });
    byte[] response = client.performQuery(RequestMethod.POST, TOKEN_MANAGEMENT_ON_BEHALF_OF_API, data);
    return mapper.readValue(response, new TypeReference<CreateTokenResponseDTO<TokenManagementInfoDTO>>() {
    });
  }

  @Override
  public void deleteToken(String tokenId) throws DatabricksRestException {
    client.performQuery(RequestMethod.DELETE, TOKEN_MANAGEMENT_TOKENS_API + "/" + tokenId);
  }

  @Override
  public List<TokenManagementInfoDTO> listTokens(TokenManagementListRequestParams listRequestParams)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();

    Optional
        .ofNullable(listRequestParams)
        .ifPresent(params -> {
          Optional.ofNullable(params.getCreatedById())
              .ifPresent(cr -> data.put("created_by_id", cr));
          Optional.ofNullable(params.getCreatedByUsername())
              .ifPresent(cr -> data.put("created_by_username", cr));
        });

    byte[] response = client.performQuery(RequestMethod.GET, TOKEN_MANAGEMENT_TOKENS_API, data);
    TokenListResponseDTO<TokenManagementInfoDTO> tokenList =
        mapper.readValue(response, new TypeReference<TokenListResponseDTO<TokenManagementInfoDTO>>() {
        });

    return Optional
        .ofNullable(tokenList)
        .map(TokenListResponseDTO::getTokenInfos)
        .orElseGet(ArrayList::new);
  }

  public List<TokenManagementInfoDTO> listTokens() throws IOException, DatabricksRestException {
    return listTokens(null);
  }

  @Override
  public TokenManagementInfoDTO getToken(String tokenId) throws IOException, DatabricksRestException {
    byte[] response = client.performQuery(RequestMethod.GET, TOKEN_MANAGEMENT_TOKENS_API + "/" + tokenId);
    TokenGetResponseDTO<TokenManagementInfoDTO> responseDTO =
        mapper.readValue(response, new TypeReference<TokenGetResponseDTO<TokenManagementInfoDTO>>() {
        });
    return Optional.ofNullable(responseDTO).map(TokenGetResponseDTO::getTokenInfo).orElse(null);
  }

}
