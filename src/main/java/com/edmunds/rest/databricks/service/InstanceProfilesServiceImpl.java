package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.instance_profiles.InstanceProfileDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstanceProfilesServiceImpl extends DatabricksService implements InstanceProfilesService {

  private static Logger log = LogManager.getLogger(InstanceProfilesServiceImpl.class);
  private static final InstanceProfileDTO[] EMPTY_PROFILE_ARRAY = {};

  public InstanceProfilesServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  @Override
  public void add(String instanceProfileArn) throws IOException, DatabricksRestException {
    add(instanceProfileArn, false);
  }

  @Override
  public void add(String instanceProfileArn, boolean skipValidation) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("instance_profile_arn", instanceProfileArn);
    data.put("skip_validation", skipValidation);
    client.performQuery(RequestMethod.POST, "/instance-profiles/add", data);
  }

  @Override
  public InstanceProfileDTO[] list() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/instance-profiles/list", null);
    Map<String, InstanceProfileDTO[]> jsonObject = this.mapper
        .readValue(responseBody, new TypeReference<Map<String, InstanceProfileDTO[]>>() {
        });
    InstanceProfileDTO[] profiles = jsonObject.get("instance_profiles");
    return profiles != null ? profiles : EMPTY_PROFILE_ARRAY;
  }

  @Override
  public void remove(String instanceProfileArn) throws IOException, DatabricksRestException {
    if (exists(instanceProfileArn)) {
      Map<String, Object> data = new HashMap<>();
      data.put("instance_profile_arn", instanceProfileArn);
      client.performQuery(RequestMethod.POST, "/instance-profiles/remove", data);
    } else {
      log.info(String.format("Instance profile with arn [%s] does not exist", instanceProfileArn));
    }
  }

  @Override
  public boolean exists(String instanceProfileArn) throws IOException, DatabricksRestException {
    return Arrays.stream(list())
            .anyMatch(x -> x.getInstanceProfileArn() != null && x.getInstanceProfileArn().equals(instanceProfileArn));
  }
}
