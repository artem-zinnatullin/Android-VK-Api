package com.artemzin.android.vk.api;

import com.artemzin.android.vk.api.elements.VKGroup;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Implements api working with groups
 * @author Artem Zinnatullin
 */
public class VKGroupsApi {

    private VKApi api;

    VKGroupsApi(VKApi api) {
        this.api = api;
    }

    /**
     * Filters for api requests
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.get">Documentation on vk.com</a>
     */
    public enum Filter {
        admin,
        groups,
        publics,
        events,
    }

    /**
     * Container for total gIds count and ArrayList of gIds
     */
    public static class GIdsWithCountContainer {

        private GIdsWithCountContainer() {}

        private int count;

        /**
         * @return total count of gIds, you can use it for offset param in next request
         */
        public int getCount() {
            return count;
        }

        private ArrayList<Long> gIds;

        /**
         * @return ArrayList with gIds
         */
        public ArrayList<Long> getGIds() {
            return gIds;
        }
    }

    /**
     * Gets list of user`s groups gIds
     * @param uId of user, whose groups gIds you want to get
     * @param filter to select only needed elements, put null to get all groups
     * @param offset to select a subset of groups, null is allowed
     * @param count of groups to get, max value is 1000, null is allowed
     * @return Container with total gIds count and ArrayList of groups gIds, never returns null
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.get">Documentation on vk.com</a>
     */
    public GIdsWithCountContainer get(Long uId, Filter[] filter,
                               Integer offset, Integer count) throws Exception {
        VKRequestParams params = new VKRequestParams("groups.get");

        if (uId != null)
            params.putParam("uid", uId.toString());

        if (filter != null)
            params.putParam("filter", VKUtils.arrayToParams(filter));


        if (offset != null)
            params.putParam("offset", offset.toString());

        if (count != null) {
            if (count > 1000)
                throw new InvalidParameterException("count param max value is 1000");
            params.putParam("count", count.toString());
        }

        JSONArray jsonGroupsGIds = api.sendRequest(params).optJSONArray("response");

        if (jsonGroupsGIds == null)
            throw new Exception(VKApi.EXCEPTION_MESSAGE_INCORRECT_RESPONSE);

        GIdsWithCountContainer container = new GIdsWithCountContainer();

        ArrayList<Long> groupsGIds = new ArrayList<Long>(jsonGroupsGIds.length());

        final int firstElement;

        if (count == null)
            firstElement = 0;
        else
            firstElement = 1;

        for (int i = firstElement; i < jsonGroupsGIds.length(); i++) {
            Long groupId = VKUtils.convertObjectToLong(jsonGroupsGIds.get(i));
            if (groupId != null)
                groupsGIds.add(groupId);
        }

        container.gIds = groupsGIds;

        if (count == null)
            container.count = groupsGIds.size();
        else
            container.count = (Integer) jsonGroupsGIds.get(0);

        return container;
    }

    /**
     * Container for total groups count and ArrayList of VKGroups
     */
    public static class VKGroupsWithCountContainer {

        private VKGroupsWithCountContainer() {}

        private int count;

        /**
         * @return total count of groups, you can use it for offset param in next request
         */
        public int getCount() {
            return count;
        }

        private ArrayList<VKGroup> groups;

        /**
         * @return ArrayList of VKGroups
         */
        public ArrayList<VKGroup> getGroups() {
            return groups;
        }
    }

    /**
     * Gets groups of needed user <br/>
     * I am sorry for method name, but this is better than "get" this is implementation of groups.get api<br/>
     * param extended is missing, because if you put 1 in extended
     * you will get VKGroup objects, else you will get only groups gIds
     * @param uId of user, whose groups you want to get, put null to use current user`s uId
     * @param filter to select only needed elements, put null to get all groups
     * @param fields of groups to get, put null to get all not extended fields
     * @param offset to select a subset of groups, null is allowed
     * @param count of groups to get, max value is 1000, null is allowed
     * @return Container with total count of groups and ArrayList of VKGroups, never returns null
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.get">Documentation on vk.com</a>
     */
    public VKGroupsWithCountContainer getExtended(Long uId, Filter[] filter, VKGroup.Fields[] fields,
                                                  Integer offset, Integer count) throws Exception {
        VKRequestParams params = new VKRequestParams("groups.get");

        if (uId != null)
            params.putParam("uid", uId.toString());

        params.putParam("extended", "1");

        if (filter != null)
            params.putParam("filter", VKUtils.arrayToParams(filter));

        if (fields == null) {
            params.putParam("fields", VKUtils.arrayToParams(VKGroup.DEFAULT_FIELDS));
        } else
            params.putParam("fields", VKUtils.arrayToParams(fields));

        if (offset != null)
            params.putParam("offset", offset.toString());

        if (count != null) {
            if (count > 1000)
                throw new InvalidParameterException("count param max value is 1000");
            params.putParam("count", count.toString());
        }

        JSONArray jsonGroups = api.sendRequest(params).optJSONArray("response");

        if (jsonGroups == null)
            throw new Exception(VKApi.EXCEPTION_MESSAGE_INCORRECT_RESPONSE);

        VKGroupsWithCountContainer container = new VKGroupsWithCountContainer();

        container.count = (Integer) jsonGroups.get(0);
        container.groups = VKJSONParser.parseGroupsFromJSON(jsonGroups);

        return container;
    }

    /**
     * Gets needed groups by their gIds
     * @param gIds of groups to get, could not be null, max size == 500
     * @param fields of groups to get
     * @return ArrayList of VKGroups, never returns null
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.getById">Documentation on vk.com</a>
     */
    public ArrayList<VKGroup> getById(ArrayList<Long> gIds,
                                      VKGroup.Fields[] fields) throws Exception {
        VKRequestParams params = new VKRequestParams("groups.getById");

        if (gIds == null)
            throw new IllegalArgumentException("gIds param could not be null");
        else {
            if (gIds.size() == 0)
                throw new InvalidParameterException("gId param should contain at least one gId");
            if (gIds.size() > 500)
                throw new InvalidParameterException("gIds max size == 500");

            params.putParam("gids", VKUtils.listToParams(gIds));
        }

        if (fields != null)
            params.putParam("fields", VKUtils.arrayToParams(fields));

        JSONArray jsonGroups = api.sendRequest(params).optJSONArray("response");

        if (jsonGroups == null)
            throw new Exception(VKApi.EXCEPTION_MESSAGE_INCORRECT_RESPONSE);

        return VKJSONParser.parseGroupsFromJSON(jsonGroups);
    }

    /**
     * Checks if user is member of group
     * @param gId short name of group
     * @param uId of user, information about you want to get
     * @return true if user is member of needed group
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.isMember">Documentation on vk.com</a>
     */
    public boolean isMember(String gId, Long uId) throws Exception {
        VKRequestParams params = new VKRequestParams("groups.isMember");

        if (gId == null)
            throw new IllegalArgumentException("gId can not be null");
        else {
            if (gId.length() == 0)
                throw new InvalidParameterException("gId can not be empty");
            params.putParam("gid", gId);
        }

        if (uId != null)
            params.putParam("uid", uId.toString());

        Integer isMember = api.sendRequest(params).optInt("response");

        if (isMember == null)
            throw new Exception(VKApi.EXCEPTION_MESSAGE_INCORRECT_RESPONSE);

        return isMember == 1;
    }

    /**
     * Checks if user is member of group
     * @param gId of group
     * @param uId of user, information about you want to get
     * @return true if user is member of needed group
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.isMember">Documentation on vk.com</a>
     */
    public boolean isMember(long gId, Long uId) throws Exception {
        return isMember(String.valueOf(gId), uId);
    }

    /**
     * Container for extended isMember request
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.isMember">Documentation on vk.com</a>
     */
    public static class IsMemberExtendedContainer {

        private IsMemberExtendedContainer() {}

        private Boolean member;

        /**
         * @return true if user is member of group, could be null
         */
        public Boolean getMember() {
            return member;
        }

        private Boolean request;

        /**
         * @return true if there are not accepted requests to join group, could be null
         */
        public Boolean getRequest() {
            return request;
        }

        private Boolean invitation;

        /**
         * @return true if user was invited to group or event, could be null
         */
        public Boolean getInvitation() {
            return invitation;
        }

        /**
         * Parsing json and return IsMemberExtendedContainer object
         * @param jsonObject to parse
         * @return IsMemberExtendedContainer object
         */
        static IsMemberExtendedContainer parseFromJSON(JSONObject jsonObject) {
            IsMemberExtendedContainer container = new IsMemberExtendedContainer();
            if (!jsonObject.isNull("member"))
                container.member = 1 == jsonObject.optInt("member");
            if (!jsonObject.isNull("request"))
                container.request = 1 == jsonObject.optInt("request");
            if (!jsonObject.isNull("invitation"))
                container.invitation = 1 == jsonObject.optInt("invitation");

            return container;
        }
    }

    /**
     * Gets extended information if user is member of group
     * @param gId group short name
     * @param uId of user, information about you want to get
     * @return IsMemberExtendedContainer with all parsed data, never returns null
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.isMember">Documentation on vk.com</a>
     */
    public IsMemberExtendedContainer isMemberExtended(String gId, Long uId) throws Exception {
        VKRequestParams params = new VKRequestParams("groups.isMember");

        if (gId == null)
            throw new IllegalArgumentException("gId can not be null");
        else {
            if (gId.length() == 0)
                throw new InvalidParameterException("gId can not be empty");
            params.putParam("gid", gId);
        }

        if (uId != null)
            params.putParam("uid", uId.toString());

        params.putParam("extended", "1");

        JSONObject jsonIsMemberExtended = api.sendRequest(params).optJSONObject("response");

        if (jsonIsMemberExtended == null)
            throw new Exception(VKApi.EXCEPTION_MESSAGE_INCORRECT_RESPONSE);

        return IsMemberExtendedContainer.parseFromJSON(jsonIsMemberExtended);
    }

    /**
     * Gets extended information if user is member of group
     * @param gId of group
     * @param uId of user, information about you want to get
     * @return IsMemberExtendedContainer with all parsed data, never returns null
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.isMember">Documentation on vk.com</a>
     */
    public IsMemberExtendedContainer isMemberExtended(long gId, Long uId) throws Exception {
        return isMemberExtended(String.valueOf(gId), uId);
    }

    /**
     * Searching groups <br/>
     * Not all fields will be available, please check documentation on vk.com
     * @param q string to search by
     * @param offset to select specific subset
     * @param count of groups to get
     * @return Container with total count of groups found and ArrayList of VKGroups, never returns null
     * @throws VKException
     * @throws Exception if something goes wrong
     * @see <a href="http://vk.com/developers.php?oid=-1&p=groups.search">Documentation on vk.com</a>
     */
    public VKGroupsWithCountContainer search(String q, Integer offset,
                                     Integer count) throws Exception {
        VKRequestParams params = new VKRequestParams("groups.search");

        if (q != null)
            params.putParam("q", q);

        if (offset != null)
            params.putParam("offset", offset.toString());

        if (count != null)
            params.putParam("count", count.toString());

        JSONArray jsonSearchResults = api.sendRequest(params).optJSONArray("response");

        if (jsonSearchResults == null)
            throw new Exception(VKApi.EXCEPTION_MESSAGE_INCORRECT_RESPONSE);

        VKGroupsWithCountContainer container = new VKGroupsWithCountContainer();

        container.count = (Integer) jsonSearchResults.get(0);
        container.groups = VKJSONParser.parseGroupsFromJSON(jsonSearchResults);

        return container;
    }
}
