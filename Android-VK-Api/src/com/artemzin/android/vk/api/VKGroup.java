package com.artemzin.android.vk.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Vk.com group object
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9F%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80_fields_%D0%B4%D0%BB%D1%8F_%D0%B3%D1%80%D1%83%D0%BF%D0%BF">VK Groups documentation</a>
 */
public class VKGroup {

    /**
     * Information update time, needed for most apps
     */
    public Long updateTime;

	/**
	 * Group id
	 */
	public Long gId;

    /**
     * Group name
     */
    public String name;

    /**
     * Is group closed
     */
    public Boolean isClosed;

    /**
     * Is current user admin of this group
     */
    public Boolean isAdmin;

    /**
     * Url to 50 * 50 px group photo
     */
    public String photo;

    /**
     * Url to 100 * 100 px group photo
     */
    public String photoMedium;

    /**
     * Url to 250 * 250 px group photo
     */
    public String photoBig;

    /**
     * Group screen name
     */
    public String screenName;

    /**
     * Id of group`s city
     */
    public Long city;

    /**
     * Id of group`s country
     */
    public Long country;

    // TODO Add place

    /**
     * Group description
     */
    public String description;

    /**
     * Name of main wiki page of group
     */
    public String wikiPage;

    /**
     * Count of group members
     */
    public Long membersCount;
	
    // TODO Add counters

    // TODO Add start_date

    // TODO Add end_date

    /**
     * Is current user able to post on group wall
     */
    public Boolean canPost;

    /**
     * Group`s activity status or start date of event
     */
    public String activity;
	
	/**
	 * Parsing json object and creating VKGroup object
	 * @param json object to parse from
	 * @return VKGroup with fields from json
	 */
	public static VKGroup parseFromJSON(JSONObject json) {
		VKGroup group = new VKGroup();
		if (!json.isNull("gid"))
            group.gId = json.optLong("gid");
		if (!json.isNull("name"))
            group.name = json.optString("name");
        if (!json.isNull("is_closed"))
            group.isClosed = 1 == json.optInt("is_closed");
        if (!json.isNull("is_admin"))
            group.isAdmin = 1 == json.optInt("is_admin");
        if (!json.isNull("photo"))
            group.photo = json.optString("photo");
        if (!json.isNull("photo_medium"))
            group.photoMedium = json.optString("photo_medium");
        if (!json.isNull("photo_big"))
            group.photoBig = json.optString("photo_big");
        if (!json.isNull("screen_name"))
            group.screenName = json.optString("screen_name");
        if (!json.isNull("city"))
            group.city = json.optLong("city");
        if (!json.isNull("country"))
            group.country = json.optLong("country");

        // Add place

        if (!json.isNull("description"))
            group.description = json.optString("description");
        if (!json.isNull("wiki_page"))
            group.wikiPage = json.optString("wiki_page");
        if (!json.isNull("members_count"))
            group.membersCount = json.optLong("members_count");

        // Add counters

        // Add start_date

        // Add end_date

        if (!json.isNull("can_post"))
            group.canPost = 1 == json.optInt("can_post");
        if (!json.isNull("activity"))
            group.activity = json.optString("activity");

		return group;
	}
	
	/**
	 * Parsing json array with groups list
	 * @param json to parse
	 * @return array list with VKGroups. Always check ArrayList size!
	 */
	public static ArrayList<VKGroup> parseFromJSON(JSONArray json) {
		ArrayList<VKGroup> groups = new ArrayList<VKGroup>();
		for (int i = 0; i < json.length(); i++) {
			// Skipping non JSONObject elements;
			// VK put array length in first element
			try {
				if (json.get(i) instanceof JSONObject) {
					JSONObject jGroup = (JSONObject) json.get(i);
					groups.add(parseFromJSON(jGroup));
				}
			} catch (JSONException ignored) {
			}
		}
		return groups;
	}
}
