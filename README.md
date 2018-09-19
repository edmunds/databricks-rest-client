

# Integration Tests
## Setup
You need to set the following environment properties in your .bash_profile
```bash
export DB_USER=myuser@domain.com
export DB_PASSWORD=mypassword
export DB_URL=my-databricks-account.databricks.com
export DB_TOKEN=my-token
```

In order for the integration tests to run, you must
have a valid token for the user in question.
Here is how to set it up: [Set up Tokens](https://docs.databricks.com/api/latest/authentication.html)

## Execution
mvn clean install org.apache.maven.plugins:maven-failsafe-plugin:integration-test

