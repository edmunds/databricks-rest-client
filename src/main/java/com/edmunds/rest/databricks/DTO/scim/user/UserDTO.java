/*
 * Copyright 2018 Edmunds.com, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.edmunds.rest.databricks.DTO.scim.user;

import com.edmunds.rest.databricks.DTO.scim.group.GroupDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Databricks SCIM user.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7643#section-4.1">https://tools.ietf.org/html/rfc7643#section-4.1</a>
 */
public class UserDTO {

  @JsonProperty("id")
  private long id;
  @JsonProperty("userName")
  private String userName;

  @JsonProperty("name")
  private NameDTO name;

  @JsonProperty("emails")
  private EmailDTO[] emails;//can't be actually customized. setting username will create an entry (work,username,primary)

  @JsonProperty("active")
  private boolean active = true;
  @JsonSerialize(using = UserGroupSerializer.class)
  @JsonDeserialize(using = UserGroupDeSerializer.class)
  private GroupDTO[] groups = new GroupDTO[0];
  @JsonProperty("entitlements")
  private EntitlementsDTO[] entitlements = new EntitlementsDTO[0];
  @JsonProperty("displayName")
  private String displayName;
  @JsonProperty("schemas")
  private final String[] schemas = new String[]{"urn:ietf:params:scim:schemas:core:2.0:User"};

  public UserDTO() {
  }

  public UserDTO(UserDTO from) {
    this.id = from.id;
    this.userName = from.userName;
      if (from.name != null) {
          this.name = new NameDTO(from.name);
      }
    if (from.emails != null) {
      emails = new EmailDTO[from.emails.length];
      for (int i = 0; i < emails.length; i++) {
        emails[i] = new EmailDTO(from.emails[i]);
      }
    }

    this.active = from.active;
    if (from.groups != null) {
      groups = new GroupDTO[from.groups.length];
      for (int i = 0; i < groups.length; i++) {
        groups[i] = new GroupDTO(from.groups[i]);
      }
    }
    if (from.entitlements != null) {
      entitlements = new EntitlementsDTO[from.entitlements.length];
      for (int i = 0; i < entitlements.length; i++) {
        entitlements[i] = new EntitlementsDTO(from.entitlements[i]);
      }
    }
    this.displayName = from.displayName;
  }

  public static class UserGroupSerializer extends JsonSerializer<GroupDTO[]> {

    public UserGroupSerializer() {
    }

    @Override
    public void serialize(GroupDTO[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeStartArray();
      for (GroupDTO groupDTO : value) {
        HashMap<String, String> map = new HashMap<>();
        map.put("display", groupDTO.getDisplay());
        map.put("value", "" + groupDTO.getId());
        gen.writeObject(map);
      }
      gen.writeEndArray();
    }
  }

  public static class UserGroupDeSerializer extends JsonDeserializer<GroupDTO[]> {

    public UserGroupDeSerializer() {
    }

    @Override
    public GroupDTO[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      ObjectCodec oc = p.getCodec();
      ArrayNode node = oc.readTree(p);
      GroupDTO[] groups = new GroupDTO[node.size()];
      for (int i = 0; i < node.size(); i++) {
        JsonNode current = node.get(i);
        groups[i] = new GroupDTO();
        groups[i].setId(Long.parseLong(current.get("value").asText()));
        groups[i].setDisplay(current.get("display").asText());
      }
      return groups;
    }
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    UserDTO userDTO = (UserDTO) o;
    return active == userDTO.active &&
        Objects.equals(userName, userDTO.userName) &&
        Objects.equals(name, userDTO.name) &&
        Arrays.equals(emails, userDTO.emails) &&
        Arrays.equals(groups, userDTO.groups) &&
        Arrays.equals(entitlements, userDTO.entitlements);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(userName, name, active);
    result = 31 * result + Arrays.hashCode(emails);
    result = 31 * result + Arrays.hashCode(groups);
    result = 31 * result + Arrays.hashCode(entitlements);
    return result;
  }

  @Override
  public String toString() {
    return "UserDTO{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        ", name=" + name +
        ", emails=" + Arrays.toString(emails) +
        ", active=" + active +
        ", groups=" + Arrays.toString(groups) +
        ", entitlements=" + Arrays.toString(entitlements) +
        ", displayName='" + displayName + '\'' +
        ", schemas=" + Arrays.toString(schemas) +
        '}';
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
    emails = new EmailDTO[]{new EmailDTO("work", userName, true)};
  }

  public void setNameDetails(String familyName, String givenName) {
    name = new NameDTO();
    name.setFamilyName(familyName);
    name.setGivenName(givenName);
    setDisplayName(givenName + " " + familyName);
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public GroupDTO[] getGroups() {
    return groups;
  }

  public void setGroups(GroupDTO[] groups) {
    this.groups = groups;
  }

  public EntitlementsDTO[] getEntitlements() {
    return entitlements;
  }

  public void setEntitlements(EntitlementsDTO[] entitlements) {
    this.entitlements = entitlements;
  }

  public String getDisplayName() {
    return displayName;
  }

  private void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
