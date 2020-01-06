package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.instance_profiles.InstanceProfileDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import java.io.IOException;

/**
 * A Wrapper around the instance profiles part of the databricks rest api.
 * @see <a href="https://docs.databricks.com/api/latest/instance-profiles.html#instance-profiles-api">https://docs.databricks.com/api/latest/instance-profiles.html#instance-profiles-api</a>
 */
public interface InstanceProfilesService {

  /**
   * Registers an instance profile in Databricks. Does profile validation by default.
   * @param instanceProfileArn the AWS ARN of the instance profile to register
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  void add(String instanceProfileArn) throws IOException, DatabricksRestException;

  /**
   * Registers an instance profile in Databricks.
   * @param instanceProfileArn the AWS ARN of the instance profile to register
   * @param skipValidation skip validation that profile has sufficient permissions to launch instances
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  void add(String instanceProfileArn, boolean skipValidation) throws IOException, DatabricksRestException;

  /**
   * Lists the instance profiles that the calling user can use to launch a cluster.
   * @return list of instance profiles the user can access
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  InstanceProfileDTO[] list() throws IOException, DatabricksRestException;

  /**
   * Removes the instance profile with the provided ARN.
   * @param instanceProfileArn the arn of the instance profile to remove
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  void remove(String instanceProfileArn) throws IOException, DatabricksRestException;

  /**
   * Checks if the instance profile is registered.
   * @param instanceProfileArn arn of the instance profile to check
   * @return true if arn is in the list of registered profiles
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  boolean exists(String instanceProfileArn) throws IOException, DatabricksRestException;
}
