package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.account.BillableUsageDTO;
import com.edmunds.rest.databricks.DTO.account.WorkspaceDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.fixtures.DatabricksFixtures;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Test class for SecretsService
 * ACL test methods can be only tested against a premium workspace
 */
public class AccountServiceTest {
    private AccountService service;
    private String accountId;

    @BeforeClass
    public void setUpOnce() {
        DatabricksServiceFactory factory = DatabricksFixtures.getDatabricksUserPasswordClientFactory();
        service = factory.getAccountService();
        accountId = System.getenv("DB_ACCOUNT_ID");
    }

    @AfterClass(alwaysRun = true)
    public void cleanUp() throws DatabricksRestException, IOException {
    }

    @Test
    public void usagesTest() throws IOException, DatabricksRestException {
        // expects the account contains at least one workspace where DBU charges was applied within this or prev month
        LocalDate now = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = now.minusMonths(1).format(monthFormatter);
        String endMonth = now.format(monthFormatter);

        List<BillableUsageDTO> usages = service.usages(accountId, startMonth, endMonth, true);
        assertFalse(usages.isEmpty());
        assertTrue(usages.stream().allMatch(usage -> usage.getDbus() > 0));
        assertTrue(usages.stream().allMatch(usage -> usage.getMachineHours() > 0));
        assertTrue(usages.stream().allMatch(usage -> usage.getTags().size() > 0));
    }

    @Test
    public void testWorkspaces() throws IOException, DatabricksRestException {
        // expects the account contains at least one workspace
        List<WorkspaceDTO> workspacesDTO = service.workspaces(accountId);
        assertFalse(workspacesDTO.isEmpty());
        assertTrue(workspacesDTO.stream().allMatch(workspace -> workspace.getWorkspaceName().length() > 0));
    }
}
