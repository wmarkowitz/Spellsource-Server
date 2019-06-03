/*
 * Hidden Switch Spellsource API
 * The Spellsource API for matchmaking, user accounts, collections management and more.  To get started, create a user account and make sure to include the entirety of the returned login token as the X-Auth-Token header. You can reuse this token, or login for a new one.  ClientToServerMessage and ServerToClientMessage are used for the realtime game state and actions two-way websocket interface for actually playing a game. Envelope is used for the realtime API services.  For the routes that correspond to the paths in this file, visit the Gateway.java file in the Spellsource-Server GitHub repository located in this definition file. 
 *
 * OpenAPI spec version: 3.0.4
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
import com.hiddenswitch.spellsource.client.models.EntityState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Entity
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class Entity implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Integer id = -1;

  @JsonProperty("cardId")
  private String cardId = null;

  /**
   * Broad categories describing this entity and how it should be rendered. 
   */
  public enum EntityTypeEnum {
    PLAYER("PLAYER"),
    
    HERO("HERO"),
    
    CARD("CARD"),
    
    MINION("MINION"),
    
    WEAPON("WEAPON"),
    
    SECRET("SECRET"),
    
    QUEST("QUEST"),
    
    ENCHANTMENT("ENCHANTMENT");

    private String value;

    EntityTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static EntityTypeEnum fromValue(String text) {
      for (EntityTypeEnum b : EntityTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("entityType")
  private EntityTypeEnum entityType = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("state")
  private EntityState state = null;

  public Entity id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * The entity&#39;s ID in the game.
   * @return id
  **/
  @ApiModelProperty(value = "The entity's ID in the game.")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Entity cardId(String cardId) {
    this.cardId = cardId;
    return this;
  }

   /**
   * The entity&#39;s Card ID. When null, it typically should not be rendered.
   * @return cardId
  **/
  @ApiModelProperty(value = "The entity's Card ID. When null, it typically should not be rendered.")
  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public Entity entityType(EntityTypeEnum entityType) {
    this.entityType = entityType;
    return this;
  }

   /**
   * Broad categories describing this entity and how it should be rendered. 
   * @return entityType
  **/
  @ApiModelProperty(value = "Broad categories describing this entity and how it should be rendered. ")
  public EntityTypeEnum getEntityType() {
    return entityType;
  }

  public void setEntityType(EntityTypeEnum entityType) {
    this.entityType = entityType;
  }

  public Entity name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The text that would go into the entity&#39;s name field. 
   * @return name
  **/
  @ApiModelProperty(value = "The text that would go into the entity's name field. ")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Entity description(String description) {
    this.description = description;
    return this;
  }

   /**
   * The text that would go into the entity&#39;s description field. 
   * @return description
  **/
  @ApiModelProperty(value = "The text that would go into the entity's description field. ")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Entity state(EntityState state) {
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @ApiModelProperty(value = "")
  public EntityState getState() {
    return state;
  }

  public void setState(EntityState state) {
    this.state = state;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entity entity = (Entity) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.cardId, entity.cardId) &&
        Objects.equals(this.entityType, entity.entityType) &&
        Objects.equals(this.name, entity.name) &&
        Objects.equals(this.description, entity.description) &&
        Objects.equals(this.state, entity.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cardId, entityType, name, description, state);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Entity {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cardId: ").append(toIndentedString(cardId)).append("\n");
    sb.append("    entityType: ").append(toIndentedString(entityType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

