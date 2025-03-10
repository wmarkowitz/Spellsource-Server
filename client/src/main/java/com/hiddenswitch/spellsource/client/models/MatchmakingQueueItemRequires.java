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
import com.hiddenswitch.spellsource.client.models.InventoryCollection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The arguments required for the matchmaking request. 
 */
@ApiModel(description = "The arguments required for the matchmaking request. ")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class MatchmakingQueueItemRequires implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("deck")
  private Boolean deck = null;

  @JsonProperty("heroClass")
  private Boolean heroClass = null;

  @JsonProperty("deckChoices")
  private List<InventoryCollection> deckChoices = null;

  @JsonProperty("deckIdChoices")
  private List<String> deckIdChoices = null;

  public MatchmakingQueueItemRequires deck(Boolean deck) {
    this.deck = deck;
    return this;
  }

   /**
   * Indicates that a deck choice is required. 
   * @return deck
  **/
  @ApiModelProperty(value = "Indicates that a deck choice is required. ")
  public Boolean isDeck() {
    return deck;
  }

  public void setDeck(Boolean deck) {
    this.deck = deck;
  }

  public MatchmakingQueueItemRequires heroClass(Boolean heroClass) {
    this.heroClass = heroClass;
    return this;
  }

   /**
   * Indicates that a hero class choice is required. When a deck choice is not required, the user only picks a hero. 
   * @return heroClass
  **/
  @ApiModelProperty(value = "Indicates that a hero class choice is required. When a deck choice is not required, the user only picks a hero. ")
  public Boolean isHeroClass() {
    return heroClass;
  }

  public void setHeroClass(Boolean heroClass) {
    this.heroClass = heroClass;
  }

  public MatchmakingQueueItemRequires deckChoices(List<InventoryCollection> deckChoices) {
    this.deckChoices = deckChoices;
    return this;
  }

  public MatchmakingQueueItemRequires addDeckChoicesItem(InventoryCollection deckChoicesItem) {
    if (this.deckChoices == null) {
      this.deckChoices = new ArrayList<>();
    }
    this.deckChoices.add(deckChoicesItem);
    return this;
  }

   /**
   * Indicates that the player must choose from the specified decks. 
   * @return deckChoices
  **/
  @ApiModelProperty(value = "Indicates that the player must choose from the specified decks. ")
  public List<InventoryCollection> getDeckChoices() {
    return deckChoices;
  }

  public void setDeckChoices(List<InventoryCollection> deckChoices) {
    this.deckChoices = deckChoices;
  }

  public MatchmakingQueueItemRequires deckIdChoices(List<String> deckIdChoices) {
    this.deckIdChoices = deckIdChoices;
    return this;
  }

  public MatchmakingQueueItemRequires addDeckIdChoicesItem(String deckIdChoicesItem) {
    if (this.deckIdChoices == null) {
      this.deckIdChoices = new ArrayList<>();
    }
    this.deckIdChoices.add(deckIdChoicesItem);
    return this;
  }

   /**
   * Indicates that the player must choose from the specified deck IDs in the player&#39;s account. 
   * @return deckIdChoices
  **/
  @ApiModelProperty(value = "Indicates that the player must choose from the specified deck IDs in the player's account. ")
  public List<String> getDeckIdChoices() {
    return deckIdChoices;
  }

  public void setDeckIdChoices(List<String> deckIdChoices) {
    this.deckIdChoices = deckIdChoices;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchmakingQueueItemRequires matchmakingQueueItemRequires = (MatchmakingQueueItemRequires) o;
    return Objects.equals(this.deck, matchmakingQueueItemRequires.deck) &&
        Objects.equals(this.heroClass, matchmakingQueueItemRequires.heroClass) &&
        Objects.equals(this.deckChoices, matchmakingQueueItemRequires.deckChoices) &&
        Objects.equals(this.deckIdChoices, matchmakingQueueItemRequires.deckIdChoices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deck, heroClass, deckChoices, deckIdChoices);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchmakingQueueItemRequires {\n");
    
    sb.append("    deck: ").append(toIndentedString(deck)).append("\n");
    sb.append("    heroClass: ").append(toIndentedString(heroClass)).append("\n");
    sb.append("    deckChoices: ").append(toIndentedString(deckChoices)).append("\n");
    sb.append("    deckIdChoices: ").append(toIndentedString(deckIdChoices)).append("\n");
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

