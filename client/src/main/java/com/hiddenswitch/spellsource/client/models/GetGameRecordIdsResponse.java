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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Retrieves an array of game IDs played by this player. Eventually will require paging. 
 */
@ApiModel(description = "Retrieves an array of game IDs played by this player. Eventually will require paging. ")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class GetGameRecordIdsResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("gameIds")
  private List<String> gameIds = null;

  public GetGameRecordIdsResponse gameIds(List<String> gameIds) {
    this.gameIds = gameIds;
    return this;
  }

  public GetGameRecordIdsResponse addGameIdsItem(String gameIdsItem) {
    if (this.gameIds == null) {
      this.gameIds = new ArrayList<>();
    }
    this.gameIds.add(gameIdsItem);
    return this;
  }

   /**
   * All the game IDs ever played by this player 
   * @return gameIds
  **/
  @ApiModelProperty(value = "All the game IDs ever played by this player ")
  public List<String> getGameIds() {
    return gameIds;
  }

  public void setGameIds(List<String> gameIds) {
    this.gameIds = gameIds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetGameRecordIdsResponse getGameRecordIdsResponse = (GetGameRecordIdsResponse) o;
    return Objects.equals(this.gameIds, getGameRecordIdsResponse.gameIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameIds);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetGameRecordIdsResponse {\n");
    
    sb.append("    gameIds: ").append(toIndentedString(gameIds)).append("\n");
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

