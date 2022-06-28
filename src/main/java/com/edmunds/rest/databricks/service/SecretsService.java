package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.secrets.ACLSecretDTO;
import com.edmunds.rest.databricks.DTO.secrets.CreateACLSecretDTO;
import com.edmunds.rest.databricks.DTO.secrets.NewSecretDTO;
import com.edmunds.rest.databricks.DTO.secrets.NewSecretScopeDTO;
import com.edmunds.rest.databricks.DTO.secrets.SecretDTO;
import com.edmunds.rest.databricks.DTO.secrets.SecretScopeDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;
import java.util.List;

/**
 * The wrapper around the databricks Secrets API.
 *
 * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#">https://docs.databricks.com/dev-tools/api/latest/secrets.html#</a>
 */
public interface SecretsService {

  /**
   * List all secret scopes available in the workspace.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#list-secret-scopes">https://docs.databricks.com/dev-tools/api/latest/secrets.html#list-secret-scopes</a>
   */
  List<SecretScopeDTO> listSecretScopes() throws IOException, DatabricksRestException;

  /**
   * Create a Databricks-backed secret scope in which secrets are stored in Databricks-managed
   * storage and encrypted with a cloud-based specific encryption key.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#create-secret-scope">https://docs.databricks.com/dev-tools/api/latest/secrets.html#create-secret-scope</a>
   */
  void createSecretScope(NewSecretScopeDTO newSecretScopeDTO) throws IOException, DatabricksRestException;

  /**
   * Delete a secret scope.
   *
   * @param scope scope to be deleted
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#delete-secret-scope">https://docs.databricks.com/dev-tools/api/latest/secrets.html#delete-secret-scope</a>
   */
  void deleteSecretScope(String scope) throws IOException, DatabricksRestException;

  /**
   * Insert a secret under the provided scope with the given name. If a secret already exists with the same name,
   * this command overwrites the existing secret’s value.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#put-secret">https://docs.databricks.com/dev-tools/api/latest/secrets.html#put-secret</a>
   */
  void createSecret(NewSecretDTO newSecretDTO) throws IOException, DatabricksRestException;

  /**
   * Delete the secret stored in this secret scope. You must have WRITE or MANAGE permission on the secret scope.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#delete-secret">https://docs.databricks.com/dev-tools/api/latest/secrets.html#delete-secret</a>
   */
  void deleteSecret(String scope, String secretKey) throws IOException, DatabricksRestException;

  /**
   * List the secret keys that are stored at this scope.
   * This is a metadata-only operation; you cannot retrieve secret data using this API.
   * You must have READ permission to make this call.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#list-secrets">https://docs.databricks.com/dev-tools/api/latest/secrets.html#list-secrets</a>
   */
  List<SecretDTO> listSecrets(String scope) throws IOException, DatabricksRestException;

  /**
   * Create or overwrite the ACL associated with the given principal (user, service principal, or group)
   * on the specified scope point.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#put-secret-acl">https://docs.databricks.com/dev-tools/api/latest/secrets.html#put-secret-acl</a>
   */
  void createSecretACL(CreateACLSecretDTO aclSecretDTO) throws IOException, DatabricksRestException;

  /**
   * Delete the given ACL on the given scope.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#delete-secret-acl">https://docs.databricks.com/dev-tools/api/latest/secrets.html#delete-secret-acl</a>
   */
  void deleteSecretACL(String scope, String principal) throws IOException, DatabricksRestException;

  /**
   * Describe the details about the given ACL, such as the group and permission.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#get-secret-acl">https://docs.databricks.com/dev-tools/api/latest/secrets.html#get-secret-acl</a>
   */
  ACLSecretDTO getSecretACL(String scope, String principal) throws IOException, DatabricksRestException;

  /**
   * List the ACLs set on the given scope.
   *
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with request
   * @see <a href="https://docs.databricks.com/dev-tools/api/latest/secrets.html#list-secret-acls">https://docs.databricks.com/dev-tools/api/latest/secrets.html#list-secret-acls</a>
   */
  List<ACLSecretDTO> listSecretACLs(String scope) throws IOException, DatabricksRestException;

}
