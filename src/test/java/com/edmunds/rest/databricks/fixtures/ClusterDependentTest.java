package com.edmunds.rest.databricks.fixtures;

import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.TestUtil;
import com.edmunds.rest.databricks.service.ClusterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class ClusterDependentTest {
  private final Logger logger = LogManager.getLogger(this.getClass().getName());
  protected DatabricksServiceFactory factory;
  protected ClusterService service;
  protected String clusterId;


  @BeforeClass
  public void setUpOnce() throws IOException, DatabricksRestException, InterruptedException {
    factory = DatabricksFixtures.getDatabricksServiceFactory();
    service = factory.getClusterService();
    clusterId = TestUtil.getTestClusterId(service);
    // Most operations will take at least 10 seconds to complete
    Awaitility.setDefaultPollDelay(10, SECONDS);
  }

  // We want to reuse the cluster accross tests, so we have after suite here
  @AfterSuite(alwaysRun = true)
  public void tearDownOnce() throws IOException, DatabricksRestException {
    //We remove all clusters with the prefix
    TestUtil.cleanupTestClusters(service, TestUtil.getTestClusters(service));
  }
}
