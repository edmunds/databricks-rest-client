# Databricks Java Rest Client

_This is a simple java library that provides programmatic access to the [Databricks Rest Service](https://docs.databricks.com/api/latest/index.html)._


## API Overview

[![Javadocs](http://www.javadoc.io/badge/com.edmunds.databricks/databricks-rest-client.svg)](http://www.javadoc.io/doc/com.edmunds.databricks/databricks-rest-client)

This library only implements a percentage of all of the functionality that the Databricks Rest Interface provides.
The idea is to add functionality as users of this library need it.
Here are the current Endpoints that are supported:

- Cluster Service

- Dbfs Service

- Job Service

- Library Service

- Workspace Service

Please look at the javadocs for the specific service to get more detailed information on what
functionality is currently available.

If there is important functionality that is currently missing, please create a github issue.

## Examples
```java
public class MyClient {
  public static void main(String[] args) throws DatabricksRestException, IOException {
    // Construct a serviceFactory using token authentication
    DatabricksServiceFactory serviceFactory =
        DatabricksServiceFactory.Builder
            .createServiceFactoryWithTokenAuthentication("myToken", "myHost")
            .withMaxRetries(5)
            .withRetryInterval(10000L)
            .build();
    
    // Lets get our databricks job "myJob" and edit maxRetries to 5
    JobDTO jobDTO = serviceFactory.getJobService().getJobByName("myJob");
    JobSettingsDTO jobSettingsDTO = jobDTO.getSettings();
    jobSettingsDTO.setMaxRetries(5);
    serviceFactory.getJobService().upsertJob(jobSettingsDTO, true);

    // Lets install a jar to a specific cluster
    LibraryDTO libraryDTO = new LibraryDTO();
    libraryDTO.setJar("s3://myBucket/myJar.jar");
    for (ClusterInfoDTO clusterInfoDTO : serviceFactory.getClusterService().list()) {
      if (clusterInfoDTO.getClusterName().equals("myCluster")) {
        serviceFactory.getLibraryService().install(clusterInfoDTO.getClusterId(), new LibraryDTO[]{libraryDTO});
      }
    }
    }
}
```
For more examples, take a look at the service tests.

## Building, Installing and Running

### Getting Started and Prerequisites

- You will need Maven installed

### Building

*How to build the project locally:*
```mvn clean install```


## Unit Tests

There are currently no unit tests for this project. Our thoughts are that the only testable
functionality is the integration between our client on an actual databricks instance.
As such we currently only have integration tests.


## Integration Tests
### Setup
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

### Executing Integration Tests
mvn clean install org.apache.maven.plugins:maven-failsafe-plugin:integration-test

## Deployment

Please see the CONTRIBUTING.md about our release process.
As this is a library, there is no deployment operation needed.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for the process for merging code into master.
