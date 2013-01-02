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
	 * Group id
	 */
	public long gId;
	
	/**
	 * Information update time
	 */
	public long updateTime;
	
	/**
	 * Group name
	 */
	public String name;
	
	/**
	 * Group screen name
	 */
	public String screenName;
	
	/**
	 * Group description
	 */
	public String description;
	
	/**
	 * Is group closed
	 */
	public Boolean isClosed;
	
	/**
	 * Is current user admin of group
	 */
	public Boolean isAdmin;
	
	/**
	 * Is current user could post to group wall
	 */
	public Boolean isUserAbleToPost;
	
	/**
	 * Count of group members
	 */
	public long membersCount;
	
	/**
	 * Url to 50 * 50 px group photo
	 */
	public String photoUrl;
	
	/**
	 * Url to 100 * 100 px group photo
	 */
	public String photoMediumUrl;
	
	/**
	 * Url to 250 * 250 px group photo
	 */
	public String photoBigUrl;
	
	{
		// To prevent NullPointerException
		name = "";
		screenName = "";
		description = "";
		photoUrl = "";
		photoMediumUrl = "";
		photoBigUrl = "";
	}
	
	/**
	 * Parsing json object and creating VKGroup object
	 * @param json object to parse from
	 * @return VKGroup with fields from json
	 */
	public static VKGroup parseFromJSON(JSONObject json) {
		VKGroup group = new VKGroup();
		group.gId = json.optLong("gid");
		group.name = json.optString("name");
		group.screenName = json.optString("screen_name");
		group.description = json.optString("description");
		group.isClosed = 1 == json.optInt("is_closed");
		group.isAdmin = 1 == json.optInt("is_admin");
		group.isUserAbleToPost = 1 == json.optInt("can_post");
		group.membersCount = json.optLong("members_count");
		group.photoUrl = json.optString("photo");
		group.photoMediumUrl = json.optString("photo_medium");
		group.photoBigUrl = json.optString("photo_big");
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
