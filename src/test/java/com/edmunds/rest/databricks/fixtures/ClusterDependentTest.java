package com.edmunds.rest.databricks.fixtures;

import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.DatabricksServiceFactory;
import com.edmunds.rest.databricks.TestUtil;
import com.edmunds.rest.databricks.service.ClusterService;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class ClusterDependentTest {
  private final Logger logger = Logger.getLogger(this.getClass().getName());
  protected DatabricksServiceFactory factory;
  protected ClusterService service;
  protected String clusterId;


  @BeforeClass
  public void setUpOnce() throws IOException, DatabricksRestException, InterruptedException {
    factory = DatabricksFixtures.getDatabricksServiceFactory();
    service = factory.getClusterService();
    clusterId = TestUtil.getDefaultClusterId(service);
  }

  @AfterClass(alwaysRun = true)
  public void tearDownOnce() throws IOException, DatabricksRestException {
    if (clusterId != null) {
      logger.info("deleting test cluster...");
      service.delete(clusterId);
    }
  }
}
