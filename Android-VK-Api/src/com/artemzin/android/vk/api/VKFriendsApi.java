package com.artemzin.android.vk.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Implements api working with friends
 * @author Artem Zinnatullin
 */
public class VKFriendsApi {

    private final VKApi2 api;

    VKFriendsApi (VKApi2 api) {
        this.api = api;
    }

    public enum Order {
        name,
        hints,
    }

    /**
     * Gets list of friends uIds
     * @param uId which friends uIds you want to get, put null to get friends of current user
     * @return ArrayList with friends uIds, or null if answer from vk.com was incorrect
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=friends.get">Documentation on vk.com</a>
     */
    public ArrayList<Long> get(Long uId) throws Exception {
        VKRequestParams params = new VKRequestParams("friends.get");

        if (uId != null)
            params.putParam("uid", uId.toString());

        JSONArray jsonFriendsIds = api.sendRequest(params).optJSONArray("response");

        if (jsonFriendsIds == null)
            return null;

        ArrayList<Long> friendsIds = new ArrayList<Long>(jsonFriendsIds.length());

        for (int i = 0; i < jsonFriendsIds.length(); i++) {
            Long friendUId = VKUtils.convertObjectToLong(jsonFriendsIds.get(i));
            if (friendUId != null)
                friendsIds.add(friendUId);
        }

        return friendsIds;
    }

    /**
     * Gets friends list of user
     * @param uId of user to get his friends, put null if you want to get friends of current user, null is allowed
     * @param fields of friends to get, put null if you want to get default fields VKUser.uId, VKUser.firstName, VKUser.lastName
     * @param nameCase of VKUser.firstName and VKUser.lastName fields, null is allowed
     * @param count of friends to get, put null if you want to get all friends, null is allowed
     * @param offset for selecting a subset of friends, null is allowed
     * @param lId of friends list, you can use this only if uId == uId of current user, null is allowed
     * @param order in which you want to get friends list, null is allowed
     * @return ArrayList of VKUsers, or null if answer from vk.com was incorrect
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=friends.get">Documentation on vk.com</a>
     */
    public ArrayList<VKUser> get(Long uId, String[] fields,
                                 VKUser.NameCase nameCase, Integer count,
                                 Integer offset, Long lId, Order order) throws Exception {
        VKRequestParams params = new VKRequestParams("friends.get");

        if (uId != null)
            params.putParam("uid", uId.toString());

        if (fields == null)
            params.putParam("fields", VKUtils.arrayToParams(new String[] {"uid", "first_name", "last_name"}));
        else
            params.putParam("fields", VKUtils.arrayToParams(fields));

        if (nameCase != null)
            params.putParam("name_case", nameCase.toString());

        if (count != null)
            params.putParam("count", count.toString());

        if (offset != null)
            params.putParam("offset", offset.toString());

        if (lId != null)
            params.putParam("lid", lId.toString());

        if (order != null)
            params.putParam("order", order.toString());

        JSONArray jsonFriends = api.sendRequest(params).optJSONArray("response");

        if (jsonFriends == null)
            return null;

        ArrayList<VKUser> friends = new ArrayList<VKUser>(jsonFriends.length());

        for (int i = 0; i < jsonFriends.length(); i++) {
            JSONObject jsonFriend = (JSONObject) jsonFriends.get(i);
            friends.add(VKUser.parseFromJSON(jsonFriend));
        }

        return friends;
    }

    /**
     * Gets list of current user`s friends uIds, who installed this application
     * @return ArrayList of friends uIds, or null if answer from vk.com was incorrect
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=friends.getAppUsers">Documentation on vk.com</a>
     */
    public ArrayList<Long> getAppUsers() throws Exception {
        VKRequestParams params = new VKRequestParams("friends.getAppUsers");

        JSONArray jsonFriendsAppUsersUIds = api.sendRequest(params).optJSONArray("response");

        if (jsonFriendsAppUsersUIds == null)
            return null;

        ArrayList<Long> friendsAppUsersUIds = new ArrayList<Long>(jsonFriendsAppUsersUIds.length());

        for (int i = 0; i < jsonFriendsAppUsersUIds.length(); i++) {
            Long friendAppUserUId = VKUtils.convertObjectToLong(jsonFriendsAppUsersUIds.get(i));
            if (friendAppUserUId != null)
                friendsAppUsersUIds.add(friendAppUserUId);
        }

        return friendsAppUsersUIds;
    }

    /**
     * Gets list of online friends uIds
     * @param uId of user, which online friends list you want to get, put null to use uId of current user
     * @return ArrayList of online friends uIds, or null if answer from vk.com was incorrect
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=friends.getOnline">Documentation on vk.com</a>
     */
    public ArrayList<Long> getOnline(Long uId) throws Exception {
        VKRequestParams params = new VKRequestParams("friends.getOnline");

        if (uId != null)
            params.putParam("uid", uId.toString());

        JSONArray jsonOnlineFriendsUIds = api.sendRequest(params).optJSONArray("response");

        if (jsonOnlineFriendsUIds == null)
            return null;

        ArrayList<Long> onlineFriendsUIds = new ArrayList<Long>(jsonOnlineFriendsUIds.length());

        for (int i = 0; i < jsonOnlineFriendsUIds.length(); i++) {
            Long onlineFriendUId = VKUtils.convertObjectToLong(jsonOnlineFriendsUIds.get(i));
            if (onlineFriendUId != null)
                onlineFriendsUIds.add(onlineFriendUId);
        }

        return onlineFriendsUIds;
    }

    /**
     * Gets list of mutual friends uIds
     * @param targetUid of user, which mutual friends you want to get, null is denied
     * @param sourceUid of user, to search mutual friends with, put null to use current user uId
     * @return ArrayList of mutual friends uIds, or null if answer from vk.com was incorrect
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=friends.getMutual">Documentation on vk.com</a>
     */
    public ArrayList<Long> getMutual(Long targetUid, Long sourceUid) throws Exception {
        VKRequestParams params = new VKRequestParams("friends.getMutual");

        if (targetUid == null)
            throw new IllegalArgumentException("targetUid param could not be null");

        params.putParam("target_uid", targetUid.toString());

        if (sourceUid != null)
            params.putParam("source_uid", sourceUid.toString());

        JSONArray jsonMutualFriendsUIds = api.sendRequest(params).optJSONArray("response");

        if (jsonMutualFriendsUIds == null)
            return null;

        ArrayList<Long> mutualFriendsUIds = new ArrayList<Long>(jsonMutualFriendsUIds.length());

        for (int i = 0; i < jsonMutualFriendsUIds.length(); i++) {
            Long mutualFriendUId = VKUtils.convertObjectToLong(jsonMutualFriendsUIds.get(i));
            if (mutualFriendUId != null)
                mutualFriendsUIds.add(mutualFriendUId);
        }

        return mutualFriendsUIds;
    }

    /**
     * Container for friendship information
     * @see <a href="http://vk.com/developers.php?oid=-1&p=friends.areFriends">Documentation on vk.com</a>
     */
    public static class FriendShipStatus {

        public Long uId;

        /**
         * 0 - not a friend <br/>
         * 1 - request/subscription was sent to user <br/>
         * 2 - incoming request/subscription from user <br/>
         * 3 - user is a friend
         */
        public Integer friendStatus;

        /**
         * If exists request message from/to user, by default is null
         */
        public String requestMessage;

        private FriendShipStatus() {}

        /**
         * Creating FriendShipStatus object from json
         * @param json to parse
         * @return FriendShipStatus object
         */
        public static FriendShipStatus parseFromJSON(JSONObject json) {
            FriendShipStatus friendShipStatus = new FriendShipStatus();
            if (!json.isNull("uid"))
                friendShipStatus.uId = json.optLong("uid");
            if (!json.isNull("friend_status"))
                friendShipStatus.friendStatus = json.optInt("friend_status");
            try {
                friendShipStatus.requestMessage = json.getString("request_message");
            } catch (JSONException ignored) {
                // To keep requestMessage null, if no request_message exists
                // Because json.optString() return "" if no such mapping exists
            }
            return friendShipStatus;
        }
    }

    /**
     * Gets information about friendship status
     * @param uIds of users, which friendship status with current user you want to get, null is denied
     * @return ArrayList of FriendShipStatus containers, or null if answer from vk.com was incorrect
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=friends.areFriends">Documentation on vk.com</a>
     */
    public ArrayList<FriendShipStatus> areFriends(ArrayList<Long> uIds) throws Exception {
        VKRequestParams params = new VKRequestParams("friends.areFriends");

        if (uIds == null)
            throw new IllegalArgumentException("uIds param could not be null");

        if (uIds.size() == 0)
            throw new InvalidParameterException("uIds param should contain at least one uId");

        params.putParam("uids", VKUtils.listToParams(uIds));

        JSONArray jsonAreFriends = api.sendRequest(params).optJSONArray("response");

        if (jsonAreFriends == null)
            return null;

        ArrayList<FriendShipStatus> friendShipStatuses = new ArrayList<FriendShipStatus>(jsonAreFriends.length());

        for (int i = 0; i < jsonAreFriends.length(); i++) {
            JSONObject jsonIsFriend = (JSONObject) jsonAreFriends.get(i);
            friendShipStatuses.add(FriendShipStatus.parseFromJSON(jsonIsFriend));
        }

        return friendShipStatuses;
    }
}
