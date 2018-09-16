
# How To Run Integration Tests
You need to set the following environment properties in your .bash_profile
export DB_USER=myuser@domain.com
export DB_PASSWORD=mypassword
export DB_URL=my-databricks-account.databricks.com


# To execute the integration tests please run:
mvn clean install org.apache.maven.plugins:maven-failsafe-plugin:integration-test

