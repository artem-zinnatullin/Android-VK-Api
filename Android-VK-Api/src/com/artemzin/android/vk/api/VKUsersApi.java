package com.artemzin.android.vk.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Implements api working with users
 * @author Artem Zinnatullin
 */
class VKUsersApi {

    private final VKApi2 api;

    VKUsersApi(VKApi2 api) {
        this.api = api;
    }

    /**
     * Gets information about has user installed this application
     * @param uId of user, who you want to know about, put null to use uId of current user
     * @return true if user has installed this app
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=isAppUser">Documentation on vk.com</a>
     */
    public boolean isAppUser(Long uId) throws Exception {
        VKRequestParams params = new VKRequestParams("isAppUser");

        if (uId != null)
            params.putParam("uid", uId.toString());

        JSONObject jsonIsAppUser = api.sendRequest(params);

        final int isAppUser;

        try {
            isAppUser = jsonIsAppUser.getInt("response");
        } catch (Exception ignored) {
            throw new Exception("Incorrect answer from vk.com");
        }

        return isAppUser == 1;
    }

    /**
     * Gets users by uIds
     * @param uIds of users which you want to get, maximum count == 1000, null is denied
     * @param fields of users to get,
     *               put null if you want to get default fields only: VKUser.uId, VKUser.firstName, VKUser.lastName, null is allowed
     * @param nameCase of VKUser.firstName and VKUser.lastName which you needed, put null if you want to use default: VKUser.NameCase.nom, null is allowed
     * @return ArrayList with VKUsers, never returns null
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=users.get">Documentation on vk.com</a>
     * @see com.artemzin.android.vk.api.VKUser
     * @see com.artemzin.android.vk.api.VKUser.NameCase
     */
    public ArrayList<VKUser> get(ArrayList<Long> uIds,
                                 String[] fields,
                                 VKUser.NameCase nameCase) throws Exception {
        VKRequestParams params = new VKRequestParams("users.get");

        if (uIds == null)
            throw new IllegalArgumentException("uIds param can not be null");
        else if (uIds.size() == 0)
            throw new InvalidParameterException("uIds should contain at least one uId");
        else if (uIds.size() > 1000)
            throw new InvalidParameterException("Maximum count of uIds is 1000");

        params.putParam("uids", VKUtils.listToParams(uIds));

        if (fields != null)
            params.putParam("fields", VKUtils.arrayToParams(fields));

        if (nameCase != null)
            params.putParam("name_case", nameCase.toString());

        JSONArray jsonUsers = api.sendRequest(params).optJSONArray("response");

        ArrayList<VKUser> users = new ArrayList<VKUser>();

        if (jsonUsers == null)
            return users;

        for (int i = 0; i < jsonUsers.length(); i++) {
            JSONObject jsonUser = (JSONObject) jsonUsers.get(i);
            users.add(VKUser.parseFromJSON(jsonUser));
        }
        return users;
    }

    /**
     * Gets list of users found
     * @param q string to search, null is denied
     * @param fields to get, put null to get VKUser.uId, VKUser.firstName and VKUser.lastName by default
     * @param count of users to get, maximum is 1000, put null to use default value == 20
     * @param offset to select specific subset
     * @return ArrayList of VKUsers found
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=users.search">Documentation on vk.com</a>
     */
    public ArrayList<VKUser> search(String q, String[] fields,
                                    Integer count, Integer offset) throws Exception {
        VKRequestParams params = new VKRequestParams("users.search");

        if (q == null)
            throw new IllegalArgumentException("q param could not be null");

        if (q.length() == 0)
            throw new InvalidParameterException("q length could not be null");

        params.putParam("q", q);

        if (fields != null)
            params.putParam("fields", VKUtils.arrayToParams(fields));

        if (count != null && count > 1000)
            throw new InvalidParameterException("Maximum value of count param is 1000");

        if (count != null)
            params.putParam("count", count.toString());

        if (offset != null)
            params.putParam("offset", offset.toString());

        JSONArray jsonUsers = api.sendRequest(params).optJSONArray("response");

        ArrayList<VKUser> users = new ArrayList<VKUser>();

        if (jsonUsers == null)
            return users;

        // Skipping zero element, it contains count of users
        for (int i = 1; i < jsonUsers.length(); i++) {
            JSONObject jsonUser = (JSONObject) jsonUsers.get(i);
            users.add(VKUser.parseFromJSON(jsonUser));
        }
        return users;
    }

    /**
     * Gets bitmask of user`s settings
     * @param uId of user, whose settings you want to get, put null to use uId of current user
     * @return bitmask of user`s settings
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=getUserSettings">Documentation on vk.com</a>
     */
    public int getUserSettings(Long uId) throws Exception {
        VKRequestParams params = new VKRequestParams("getUserSettings");

        if (uId != null)
            params.putParam("uid", uId.toString());

        JSONObject jsonUserSettings = api.sendRequest(params);
        final int userSettings;

        try {
            userSettings = jsonUserSettings.getInt("response");
        } catch (Exception ignored) {
            throw new Exception("Incorrect response from vk.com");
        }

        return userSettings;
    }
}
