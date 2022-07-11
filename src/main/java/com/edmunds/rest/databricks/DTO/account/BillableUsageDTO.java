package com.edmunds.rest.databricks.DTO.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class BillableUsageDTO {
  public static final String CSV_PROPERTY_WORKSPACE_ID = "workspaceId";
  public static final String CSV_PROPERTY_TIMESTAMP = "timestamp";
  public static final String CSV_PROPERTY_CLUSTER_ID = "clusterId";
  public static final String CSV_PROPERTY_CLUSTER_NAME = "clusterName";
  public static final String CSV_PROPERTY_CLUSTER_NODE_TYPE = "clusterNodeType";
  public static final String CSV_PROPERTY_CLUSTER_OWNER_USER_ID = "clusterOwnerUserId";
  public static final String CSV_PROPERTY_CLUSTER_CUSTOM_TAGS = "clusterCustomTags";
  public static final String CSV_PROPERTY_SKU = "sku";
  public static final String CSV_PROPERTY_DBUS = "dbus";
  public static final String CSV_PROPERTY_MACHINE_HOURS = "machineHours";
  public static final String CSV_PROPERTY_CLUSTER_OWNER_USER_NAME = "clusterOwnerUserName";
  public static final String CSV_PROPERTY_TAGS = "tags";

  private String workspaceId;
  private OffsetDateTime timestamp;
  private String clusterId;
  private String clusterName;
  private String clusterNodeType;
  private String clusterOwnerUserId;
  private Map<String, String> clusterCustomTags;
  private String sku;
  private java.lang.Double dbus;
  private java.lang.Double machineHours;
  private String clusterOwnerUserName;
  private Map<String, String> tags;



}
