package com.artemzin.android.vk.api.elements;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Vk.com group object <br/>
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9F%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80_fields_%D0%B4%D0%BB%D1%8F_%D0%B3%D1%80%D1%83%D0%BF%D0%BF">VK Groups documentation</a>
 */
public class VKGroup {

    /**
     * All fields, which are supported at the moment by this library <br/>
     * Needed for VKApi queries <br/>
     * I am sorry about non java camelCase style, needed for converting enum elements to GET params
     * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9F%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80_fields_%D0%B4%D0%BB%D1%8F_%D0%B3%D1%80%D1%83%D0%BF%D0%BF">
     *     Documentation on vk.com</a>
     */
    public enum Fields {
        gid,
        name,
        is_closed,
        is_admin,
        photo,
        photo_medium,
        photo_big,
        screen_name,
        city,
        country,

        // Add place

        description,
        wiki_page,
        members_count,

        // Add counters

        // Add start_date

        // Add end_date

        can_post,
        activity,
    }

    /**
     * All fields, which are supported at the moment by this library <br/>
     * Needed for VKApi queries
     */
    public static final Fields[] ALL_FIELDS = Fields.values();

    /**
     * Default fields for VKGroup <br/>
     * Needed for VKApi queries
     */
    public static final Fields[] DEFAULT_FIELDS = new Fields[] {
            Fields.gid,
            Fields.city,
            Fields.country,

            // Add place

            Fields.description,
            Fields.wiki_page,
            Fields.members_count,

            // Add counters

            // Add start_date

            // Add end_date

            Fields.can_post,
            Fields.activity,
    };

    private Long updateTime;

    private Long gId;

    private String name;

    private Boolean isClosed;

    private Boolean isAdmin;

    private String photo;

    private String photoMedium;

    private String photoBig;

    private String screenName;

    private Long city;

    private Long country;

    // TODO Add place

    private String description;

    private String wikiPage;

    private Long membersCount;

    // TODO Add counters

    // TODO Add start_date

    // TODO Add end_date

    private Boolean canPost;

    private String activity;

    /**
     * Information update time, needed for most apps
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * Group id
     */
    public Long getGId() {
        return gId;
    }

    public void setGId(Long gId) {
        this.gId = gId;
    }

    /**
     * Group name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Is group closed
     */
    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * Is current user admin of this group
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Url to 50 * 50 px group photo
     */
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Url to 100 * 100 px group photo
     */
    public String getPhotoMedium() {
        return photoMedium;
    }

    public void setPhotoMedium(String photoMedium) {
        this.photoMedium = photoMedium;
    }

    /**
     * Url to 250 * 250 px group photo
     */
    public String getPhotoBig() {
        return photoBig;
    }

    public void setPhotoBig(String photoBig) {
        this.photoBig = photoBig;
    }

    /**
     * Group screen name
     */
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * Id of group`s city
     */
    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * Id of group`s country
     */
    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    /**
     * Group description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Name of main wiki page of group
     */
    public String getWikiPage() {
        return wikiPage;
    }

    public void setWikiPage(String wikiPage) {
        this.wikiPage = wikiPage;
    }

    /**
     * Count of group members
     */
    public Long getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Long membersCount) {
        this.membersCount = membersCount;
    }

    /**
     * Is current user able to post on group wall
     */
    public Boolean getCanPost() {
        return canPost;
    }

    public void setCanPost(Boolean canPost) {
        this.canPost = canPost;
    }

    /**
     * Group`s activity status or start date of event
     */
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

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
	 * @param jsonArray to parse
	 * @return array list with VKGroups. Always check ArrayList size!
	 */
	public static ArrayList<VKGroup> parseFromJSON(JSONArray jsonArray) {
		ArrayList<VKGroup> groups = new ArrayList<VKGroup>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			// Skipping non JSONObject elements;
			// VK put array length in first element
			try {
				if (jsonArray.get(i) instanceof JSONObject) {
					JSONObject jGroup = (JSONObject) jsonArray.get(i);
					groups.add(parseFromJSON(jGroup));
				}
			} catch (JSONException ignored) {
			}
		}
		return groups;
	}

}
