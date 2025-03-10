/*
 * Hidden Switch Spellsource API
 * The Spellsource API for matchmaking, user accounts, collections management and more.  To get started, create a user account and make sure to include the entirety of the returned login token as the X-Auth-Token header. You can reuse this token, or login for a new one.  ClientToServerMessage and ServerToClientMessage are used for the realtime game state and actions two-way websocket interface for actually playing a game. Envelope is used for the realtime API services.  For the routes that correspond to the paths in this file, visit the Gateway.java file in the Spellsource-Server GitHub repository located in this definition file. 
 *
 * OpenAPI spec version: 3.0.5
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
import com.hiddenswitch.spellsource.client.models.ClientToServerMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * MatchmakingQueuePutResponseUnityConnection
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class MatchmakingQueuePutResponseUnityConnection implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("firstMessage")
  private ClientToServerMessage firstMessage = null;

  public MatchmakingQueuePutResponseUnityConnection url(String url) {
    this.url = url;
    return this;
  }

   /**
   * The websocket URL to connect to. 
   * @return url
  **/
  @ApiModelProperty(value = "The websocket URL to connect to. ")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public MatchmakingQueuePutResponseUnityConnection firstMessage(ClientToServerMessage firstMessage) {
    this.firstMessage = firstMessage;
    return this;
  }

   /**
   * Get firstMessage
   * @return firstMessage
  **/
  @ApiModelProperty(value = "")
  public ClientToServerMessage getFirstMessage() {
    return firstMessage;
  }

  public void setFirstMessage(ClientToServerMessage firstMessage) {
    this.firstMessage = firstMessage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchmakingQueuePutResponseUnityConnection matchmakingQueuePutResponseUnityConnection = (MatchmakingQueuePutResponseUnityConnection) o;
    return Objects.equals(this.url, matchmakingQueuePutResponseUnityConnection.url) &&
        Objects.equals(this.firstMessage, matchmakingQueuePutResponseUnityConnection.firstMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, firstMessage);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchmakingQueuePutResponseUnityConnection {\n");
    
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    firstMessage: ").append(toIndentedString(firstMessage)).append("\n");
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

