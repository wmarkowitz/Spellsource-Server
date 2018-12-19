/*
 * Hidden Switch Spellsource API
 * The Spellsource API for matchmaking, user accounts, collections management and more.  To get started, create a user account and make sure to include the entirety of the returned login token as the X-Auth-Token header. You can reuse this token, or login for a new one.  ClientToServerMessage and ServerToClientMessage are used for the realtime game state and actions two-way websocket interface for actually playing a game. Envelope is used for the realtime API services. 
 *
 * OpenAPI spec version: 4.0.0
 * Contact: ben@hiddenswitch.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.hiddenswitch.spellsource.client.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hiddenswitch.spellsource.client.models.Invite;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The invites where this user is either the sender or recipient. 
 */
@ApiModel(description = "The invites where this user is either the sender or recipient. ")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class InviteGetResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("invites")
  private List<Invite> invites = null;

  public InviteGetResponse invites(List<Invite> invites) {
    this.invites = invites;
    return this;
  }

  public InviteGetResponse addInvitesItem(Invite invitesItem) {
    if (this.invites == null) {
      this.invites = new ArrayList<>();
    }
    this.invites.add(invitesItem);
    return this;
  }

   /**
   * Get invites
   * @return invites
  **/
  @ApiModelProperty(value = "")
  public List<Invite> getInvites() {
    return invites;
  }

  public void setInvites(List<Invite> invites) {
    this.invites = invites;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InviteGetResponse inviteGetResponse = (InviteGetResponse) o;
    return Objects.equals(this.invites, inviteGetResponse.invites);
  }

  @Override
  public int hashCode() {
    return Objects.hash(invites);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InviteGetResponse {\n");
    
    sb.append("    invites: ").append(toIndentedString(invites)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

