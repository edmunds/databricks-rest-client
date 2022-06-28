package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.secrets.*;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Test class for SecretsService
 * ACL test methods can be only tested against a premium workspace
 */
public class SecretsServiceTest {
    private SecretsService service;

    @BeforeClass
    public void setUpOnce() {
        DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksServiceFactory();
        service = factory.getSecretsService();
    }

    @AfterClass(alwaysRun = true)
    public void cleanUp() throws DatabricksRestException, IOException {
        System.out.println("Clean up SecretsServiceTest resources");
        try {
            service.deleteSecretACL("test-scope", "users");
        } catch (Throwable throwable) {
            System.err.println("Failed to remove ACL secret. " + throwable.getLocalizedMessage());
        }

        try {
            service.deleteSecret("test-scope", "test-secret");
        } catch (Throwable throwable) {
            System.err.println("Failed to remove secret. " + throwable.getLocalizedMessage());
        }

        service.deleteSecretScope("test-scope");
    }

    @Test
    public void createSecretScopeTest() throws IOException, DatabricksRestException {
        NewSecretScopeDTO newSecretScopeDTO = new NewDatabricksSecretScopeDTO();
        newSecretScopeDTO.setScope("test-scope");
        newSecretScopeDTO.setInitialManagePrincipal("users");
        service.createSecretScope(newSecretScopeDTO);
    }

    @Test(dependsOnMethods = {"createSecretScopeTest"})
    public void testSecretScopesList() throws IOException, DatabricksRestException {

        List<SecretScopeDTO> secretScopesDTO = service.listSecretScopes();
        assertNotNull(secretScopesDTO);
        boolean isTestScopePresent = secretScopesDTO.stream().anyMatch(secretScopeDTO -> secretScopeDTO.getName().equals("test-scope"));
        assertTrue(isTestScopePresent);
    }

    @Test(dependsOnMethods = {"createSecretScopeTest"})
    public void createSecretTest() throws DatabricksRestException, IOException {
        NewSecretDTO secretDTO = new NewSecretDTO();
        secretDTO.setScope("test-scope");
        secretDTO.setKey("test-secret");
        secretDTO.setStringValue("test-secret-value");

        service.createSecret(secretDTO);
    }

    @Test(dependsOnMethods = {"createSecretTest"})
    public void testSecretList() throws DatabricksRestException, IOException {
        List<SecretDTO> secrets = service.listSecrets("test-scope");
        assertNotNull(secrets);

        boolean isSecretPresent = secrets.stream().anyMatch(secretScopeDTO -> secretScopeDTO.getKey().equals("test-secret"));
        assertTrue(isSecretPresent);
    }

//    @Test(dependsOnMethods = {"createSecretScopeTest"})
//    public void createACLsSecretsTests() throws DatabricksRestException, IOException {
//        CreateACLSecretDTO createACLSecretDTO = new CreateACLSecretDTO();
//        createACLSecretDTO.setScope("test-scope");
//        createACLSecretDTO.setPrincipal("users");
//        createACLSecretDTO.setPermission(ACLPermission.READ);
//        service.createSecretACL(createACLSecretDTO);
//    }
//
//    @Test(dependsOnMethods = {"createACLsSecretsTests"})
//    public void listACLsSecretsTests() throws DatabricksRestException, IOException {
//        List<ACLSecretDTO> secrets = service.listSecretACLs("test-scope");
//        assertNotNull(secrets);
//
//        assertEquals(secrets.size(), 1);
//
//    }
//
//    @Test(dependsOnMethods = {"createACLsSecretsTests"})
//    public void getACLsSecretsTests() throws DatabricksRestException, IOException {
//        ACLSecretDTO users = service.getSecretACL("test-scope", "users");
//        assertEquals(users.getPrincipal(), "users");
//    }




}
