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
import com.hiddenswitch.spellsource.client.models.Replay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Information about a game.  Statistics about the game will be stored at a later point in time. 
 */
@ApiModel(description = "Information about a game.  Statistics about the game will be stored at a later point in time. ")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class GetGameRecordResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("replay")
  private Replay replay = null;

  @JsonProperty("isBotGame")
  private Boolean isBotGame = null;

  @JsonProperty("completedAtLocalized")
  private String completedAtLocalized = null;

  @JsonProperty("completedAt")
  private Long completedAt = null;

  @JsonProperty("playerNames")
  private List<String> playerNames = null;

  public GetGameRecordResponse replay(Replay replay) {
    this.replay = replay;
    return this;
  }

   /**
   * Get replay
   * @return replay
  **/
  @ApiModelProperty(value = "")
  public Replay getReplay() {
    return replay;
  }

  public void setReplay(Replay replay) {
    this.replay = replay;
  }

  public GetGameRecordResponse isBotGame(Boolean isBotGame) {
    this.isBotGame = isBotGame;
    return this;
  }

   /**
   * True if this game was played against a bot (or was played entirely by bots) 
   * @return isBotGame
  **/
  @ApiModelProperty(value = "True if this game was played against a bot (or was played entirely by bots) ")
  public Boolean isIsBotGame() {
    return isBotGame;
  }

  public void setIsBotGame(Boolean isBotGame) {
    this.isBotGame = isBotGame;
  }

  public GetGameRecordResponse completedAtLocalized(String completedAtLocalized) {
    this.completedAtLocalized = completedAtLocalized;
    return this;
  }

   /**
   * The date and time when this game was finished in the client&#39;s local time. 
   * @return completedAtLocalized
  **/
  @ApiModelProperty(value = "The date and time when this game was finished in the client's local time. ")
  public String getCompletedAtLocalized() {
    return completedAtLocalized;
  }

  public void setCompletedAtLocalized(String completedAtLocalized) {
    this.completedAtLocalized = completedAtLocalized;
  }

  public GetGameRecordResponse completedAt(Long completedAt) {
    this.completedAt = completedAt;
    return this;
  }

   /**
   * A timestamp for when this game was finished (approximate). 
   * @return completedAt
  **/
  @ApiModelProperty(value = "A timestamp for when this game was finished (approximate). ")
  public Long getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(Long completedAt) {
    this.completedAt = completedAt;
  }

  public GetGameRecordResponse playerNames(List<String> playerNames) {
    this.playerNames = playerNames;
    return this;
  }

  public GetGameRecordResponse addPlayerNamesItem(String playerNamesItem) {
    if (this.playerNames == null) {
      this.playerNames = new ArrayList<>();
    }
    this.playerNames.add(playerNamesItem);
    return this;
  }

   /**
   * The names of the players in this game, without their privacy tokens 
   * @return playerNames
  **/
  @ApiModelProperty(value = "The names of the players in this game, without their privacy tokens ")
  public List<String> getPlayerNames() {
    return playerNames;
  }

  public void setPlayerNames(List<String> playerNames) {
    this.playerNames = playerNames;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetGameRecordResponse getGameRecordResponse = (GetGameRecordResponse) o;
    return Objects.equals(this.replay, getGameRecordResponse.replay) &&
        Objects.equals(this.isBotGame, getGameRecordResponse.isBotGame) &&
        Objects.equals(this.completedAtLocalized, getGameRecordResponse.completedAtLocalized) &&
        Objects.equals(this.completedAt, getGameRecordResponse.completedAt) &&
        Objects.equals(this.playerNames, getGameRecordResponse.playerNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(replay, isBotGame, completedAtLocalized, completedAt, playerNames);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetGameRecordResponse {\n");
    
    sb.append("    replay: ").append(toIndentedString(replay)).append("\n");
    sb.append("    isBotGame: ").append(toIndentedString(isBotGame)).append("\n");
    sb.append("    completedAtLocalized: ").append(toIndentedString(completedAtLocalized)).append("\n");
    sb.append("    completedAt: ").append(toIndentedString(completedAt)).append("\n");
    sb.append("    playerNames: ").append(toIndentedString(playerNames)).append("\n");
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

