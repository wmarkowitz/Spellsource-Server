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


package com.hiddenswitch.spellsource.client.api;

import com.hiddenswitch.spellsource.client.ApiException;
import com.hiddenswitch.spellsource.client.models.AcceptInviteRequest;
import com.hiddenswitch.spellsource.client.models.AcceptInviteResponse;
import com.hiddenswitch.spellsource.client.models.ChangePasswordRequest;
import com.hiddenswitch.spellsource.client.models.ChangePasswordResponse;
import com.hiddenswitch.spellsource.client.models.CreateAccountRequest;
import com.hiddenswitch.spellsource.client.models.CreateAccountResponse;
import com.hiddenswitch.spellsource.client.models.DecksGetAllResponse;
import com.hiddenswitch.spellsource.client.models.DecksGetResponse;
import com.hiddenswitch.spellsource.client.models.DecksPutRequest;
import com.hiddenswitch.spellsource.client.models.DecksPutResponse;
import com.hiddenswitch.spellsource.client.models.DecksUpdateCommand;
import com.hiddenswitch.spellsource.client.models.DraftState;
import com.hiddenswitch.spellsource.client.models.DraftsChooseCardRequest;
import com.hiddenswitch.spellsource.client.models.DraftsChooseHeroRequest;
import com.hiddenswitch.spellsource.client.models.DraftsPostRequest;
import com.hiddenswitch.spellsource.client.models.FriendPutRequest;
import com.hiddenswitch.spellsource.client.models.FriendPutResponse;
import com.hiddenswitch.spellsource.client.models.GetAccountsRequest;
import com.hiddenswitch.spellsource.client.models.GetAccountsResponse;
import com.hiddenswitch.spellsource.client.models.GetCardsResponse;
import com.hiddenswitch.spellsource.client.models.GetGameRecordIdsResponse;
import com.hiddenswitch.spellsource.client.models.GetGameRecordResponse;
import com.hiddenswitch.spellsource.client.models.InviteGetResponse;
import com.hiddenswitch.spellsource.client.models.InvitePostRequest;
import com.hiddenswitch.spellsource.client.models.InviteResponse;
import com.hiddenswitch.spellsource.client.models.LoginRequest;
import com.hiddenswitch.spellsource.client.models.LoginResponse;
import com.hiddenswitch.spellsource.client.models.MatchCancelResponse;
import com.hiddenswitch.spellsource.client.models.MatchmakingQueuesResponse;
import com.hiddenswitch.spellsource.client.models.SpellsourceException;
import com.hiddenswitch.spellsource.client.models.UnfriendResponse;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DefaultApi
 */
@Ignore
public class DefaultApiTest {

    private final DefaultApi api = new DefaultApi();

    
    /**
     * 
     *
     * Accepts the invite. If this is an invite to friend the user, this method will perform the friending path for you. If this is an invite to play a match and a matchmaking queue put is specified (with the deck ID), this method will enter you into the special invite matchmaking queue. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void acceptInviteTest() throws ApiException {
        String inviteId = null;
        AcceptInviteRequest request = null;
        AcceptInviteResponse response = api.acceptInvite(inviteId, request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Changes your password. Does not log you out after the password is changed. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void changePasswordTest() throws ApiException {
        ChangePasswordRequest request = null;
        ChangePasswordResponse response = api.changePassword(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Create an account with Spellsource. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createAccountTest() throws ApiException {
        CreateAccountRequest request = null;
        CreateAccountResponse response = api.createAccount(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Deletes the specified deck by ID. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void decksDeleteTest() throws ApiException {
        String deckId = null;
        api.decksDelete(deckId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Gets a deck. Only viewable for the owner of the deck or players in the alliance. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void decksGetTest() throws ApiException {
        String deckId = null;
        DecksGetResponse response = api.decksGet(deckId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Gets all the user&#39;s decks. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void decksGetAllTest() throws ApiException {
        DecksGetAllResponse response = api.decksGetAll();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Creates a new deck with optionally specified inventory IDs, a name and a hero class. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void decksPutTest() throws ApiException {
        DecksPutRequest request = null;
        DecksPutResponse response = api.decksPut(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Updates the deck by adding or removing cards, changing the hero class, or renaming the deck. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void decksUpdateTest() throws ApiException {
        String deckId = null;
        DecksUpdateCommand updateCommand = null;
        DecksGetResponse response = api.decksUpdate(deckId, updateCommand);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * When this user is the sender, cancels the invite. When this user is the recipient, rejects the specified invite. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteInviteTest() throws ApiException {
        String inviteId = null;
        InviteResponse response = api.deleteInvite(inviteId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Make a selection for the given draft index. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void draftsChooseCardTest() throws ApiException {
        DraftsChooseCardRequest request = null;
        DraftState response = api.draftsChooseCard(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Choose a hero from your hero selection. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void draftsChooseHeroTest() throws ApiException {
        DraftsChooseHeroRequest request = null;
        DraftState response = api.draftsChooseHero(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Gets your latest state of the draft. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void draftsGetTest() throws ApiException {
        DraftState response = api.draftsGet();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Starts a draft, or make a change to your draft, like retiring early. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void draftsPostTest() throws ApiException {
        DraftsPostRequest request = null;
        DraftState response = api.draftsPost(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Removes the friend relationship between two users. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void friendDeleteTest() throws ApiException {
        String friendId = null;
        UnfriendResponse response = api.friendDelete(friendId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Adds a specified user to your friend list. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void friendPutTest() throws ApiException {
        FriendPutRequest request = null;
        FriendPutResponse response = api.friendPut(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Get a specific account. Contains more information if the userId matches the requesting user. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAccountTest() throws ApiException {
        String targetUserId = null;
        GetAccountsResponse response = api.getAccount(targetUserId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Get a list of accounts including user profile information. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAccountsTest() throws ApiException {
        GetAccountsRequest request = null;
        GetAccountsResponse response = api.getAccounts(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Gets a complete catalogue of all the cards available in Spellsource as a list of CardRecords 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getCardsTest() throws ApiException {
        String ifNoneMatch = null;
        GetCardsResponse response = api.getCards(ifNoneMatch);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Retrieves a record of a game this player played. Games against bots retrieve a complete game record, while games against other players only receive this player&#39;s point of view. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getGameRecordTest() throws ApiException {
        String gameId = null;
        GetGameRecordResponse response = api.getGameRecord(gameId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Retrieves a list of game IDs corresponding to all the games this player played. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getGameRecordIdsTest() throws ApiException {
        GetGameRecordIdsResponse response = api.getGameRecordIds();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Retrieves information about a specific invite, as long as this user is either the sender or recipient. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getInviteTest() throws ApiException {
        String inviteId = null;
        InviteResponse response = api.getInvite(inviteId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Retrieve all invites where this user is either the sender or recipient. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getInvitesTest() throws ApiException {
        InviteGetResponse response = api.getInvites();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Retrieves the semver server version 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getVersionTest() throws ApiException {
        String response = api.getVersion();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Returns an empty body if the server is available. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void healthCheckTest() throws ApiException {
        api.healthCheck();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Login with a username and password, receiving an authentication token to use for future sessions. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void loginTest() throws ApiException {
        LoginRequest request = null;
        LoginResponse response = api.login(request);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Removes your client from the matchmaking queue, regardless of which queue it is in.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void matchmakingDeleteTest() throws ApiException {
        MatchCancelResponse response = api.matchmakingDelete();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Gets a list of queues available for matchmaking. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void matchmakingGetTest() throws ApiException {
        MatchmakingQueuesResponse response = api.matchmakingGet();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Send an invite. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postInviteTest() throws ApiException {
        InvitePostRequest request = null;
        InviteResponse response = api.postInvite(request);

        // TODO: test validations
    }
    
}
