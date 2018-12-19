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
import com.hiddenswitch.spellsource.client.models.DefaultMethodResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * When populated by the client and sent to the server, indicates a response to a request from the server. 
 */
@ApiModel(description = "When populated by the client and sent to the server, indicates a response to a request from the server. ")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class EnvelopeResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("isAlive")
  private DefaultMethodResponse isAlive = null;

  public EnvelopeResponse isAlive(DefaultMethodResponse isAlive) {
    this.isAlive = isAlive;
    return this;
  }

   /**
   * Get isAlive
   * @return isAlive
  **/
  @ApiModelProperty(value = "")
  public DefaultMethodResponse getIsAlive() {
    return isAlive;
  }

  public void setIsAlive(DefaultMethodResponse isAlive) {
    this.isAlive = isAlive;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EnvelopeResponse envelopeResponse = (EnvelopeResponse) o;
    return Objects.equals(this.isAlive, envelopeResponse.isAlive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isAlive);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EnvelopeResponse {\n");
    
    sb.append("    isAlive: ").append(toIndentedString(isAlive)).append("\n");
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

