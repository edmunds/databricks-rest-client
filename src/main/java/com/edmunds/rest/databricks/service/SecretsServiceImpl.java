package com.edmunds.rest.databricks.service;


import com.edmunds.rest.databricks.DTO.secrets.*;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.*;

public class SecretsServiceImpl extends DatabricksService implements SecretsService {

    private static final String SECRETS_API_ENDPOINT = "/secrets/";
    private static final String SCOPES_API_ENDPOINT = SECRETS_API_ENDPOINT + "scopes/";
    private static final String ACL_API_ENDPOINT = SECRETS_API_ENDPOINT + "acls/";

    private static final String SCOPES_API_LIST_ENDPOINT = SCOPES_API_ENDPOINT + "list";
    private static final String SCOPES_API_CREATE_ENDPOINT = SCOPES_API_ENDPOINT + "create";
    private static final String SCOPES_API_DELETE_ENDPOINT = SCOPES_API_ENDPOINT + "delete";

    private static final String SECRETS_API_LIST_ENDPOINT = SECRETS_API_ENDPOINT + "list";
    private static final String SECRETS_API_CREATE_ENDPOINT = SECRETS_API_ENDPOINT + "put";
    private static final String SECRETS_API_DELETE_ENDPOINT = SECRETS_API_ENDPOINT + "delete";

    private static final String ACL_API_LIST_ENDPOINT = ACL_API_ENDPOINT + "list";
    private static final String ACL_API_GET_ENDPOINT = ACL_API_ENDPOINT + "get";
    private static final String ACL_API_CREATE_ENDPOINT = ACL_API_ENDPOINT + "put";
    private static final String ACL_API_DELETE_ENDPOINT = ACL_API_ENDPOINT + "delete";

    /**
     * Given a client, create a wrapper around it.
     *
     * @param client
     */
    public SecretsServiceImpl(DatabricksRestClient client) {
        super(client);
    }

    @Override
    public List<SecretScopeDTO> listSecretScopes() throws IOException, DatabricksRestException {
        byte[] response = client.performQuery(RequestMethod.GET, SCOPES_API_LIST_ENDPOINT);
        SecretScopesDTO secretScopesDTO = mapper.readValue(response, new TypeReference<SecretScopesDTO>() {});
        if (secretScopesDTO == null || secretScopesDTO.getScopes() == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(secretScopesDTO.getScopes());
    }

    @Override
    public void createSecretScope(NewSecretScopeDTO secretScopeDTO) throws IOException, DatabricksRestException {
        String marshalled = mapper.writeValueAsString(secretScopeDTO);
        Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
        });
        client.performQuery(RequestMethod.POST, SCOPES_API_CREATE_ENDPOINT, data);
    }

    @Override
    public void deleteSecretScope(String scope) throws DatabricksRestException {
        Map<String, Object> data = new HashMap<>();
        data.put("scope", scope);
        client.performQuery(RequestMethod.POST, SCOPES_API_DELETE_ENDPOINT, data);
    }

    @Override
    public void createSecret(NewSecretDTO newSecretDTO) throws IOException, DatabricksRestException {
        String marshalled = mapper.writeValueAsString(newSecretDTO);
        Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
        });
        client.performQuery(RequestMethod.POST, SECRETS_API_CREATE_ENDPOINT, data);
    }

    @Override
    public void deleteSecret(String scope, String secretKey) throws DatabricksRestException {
        Map<String, Object> data = new HashMap<>();
        data.put("scope", scope);
        data.put("key", secretKey);
        client.performQuery(RequestMethod.POST, SECRETS_API_DELETE_ENDPOINT, data);
    }

    @Override
    public List<SecretDTO> listSecrets(String scope) throws IOException, DatabricksRestException {
        byte[] response = client.performQuery(RequestMethod.GET, SECRETS_API_LIST_ENDPOINT + "?scope=" + scope);
        SecretsDTO secretsDTO = mapper.readValue(response, new TypeReference<SecretsDTO>() {});
        if (secretsDTO == null || secretsDTO.getSecrets() == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(secretsDTO.getSecrets());
    }


    @Override
    public void createSecretACL(CreateACLSecretDTO aclSecretDTO) throws IOException, DatabricksRestException {
        String marshalled = mapper.writeValueAsString(aclSecretDTO);
        Map<String, Object> data = mapper.readValue(marshalled, new TypeReference<Map<String, Object>>() {
        });
        client.performQuery(RequestMethod.POST, ACL_API_CREATE_ENDPOINT, data);
    }

    @Override
    public void deleteSecretACL(String scope, String principal) throws DatabricksRestException {
        Map<String, Object> data = new HashMap<>();
        data.put("scope", scope);
        data.put("principal", principal);
        client.performQuery(RequestMethod.POST, ACL_API_DELETE_ENDPOINT, data);

    }

    @Override
    public ACLSecretDTO getSecretACL(String scope, String principal) throws IOException, DatabricksRestException {
        byte[] response = client.performQuery(RequestMethod.GET, ACL_API_GET_ENDPOINT + "?scope=" + scope + "&principal=" + principal);
        return this.mapper.readValue(response, ACLSecretDTO.class);
    }

    @Override
    public List<ACLSecretDTO> listSecretACLs(String scope) throws IOException, DatabricksRestException {
        byte[] response = client.performQuery(RequestMethod.GET, ACL_API_LIST_ENDPOINT + "?scope=" + scope );
        ACLSecretsDTO aclSecretsDTO = this.mapper.readValue(response, ACLSecretsDTO.class);
        if (aclSecretsDTO == null || aclSecretsDTO.getItems() == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(aclSecretsDTO.getItems());
    }
}
