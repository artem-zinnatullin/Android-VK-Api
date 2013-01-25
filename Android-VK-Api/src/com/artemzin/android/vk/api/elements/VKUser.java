package com.artemzin.android.vk.api.elements;

import org.json.JSONObject;

/**
 * VK VKUser class
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D0%B5%D0%B9_%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0_fields">
 *     VK User docs</a>
 */
public class VKUser {

    /**
     * All fields, which are supported at the moment by this library
     * Needed for VKApi queries
     * @see com.artemzin.android.vk.api.VKApi2#friends
     * @see com.artemzin.android.vk.api.VKApi2#users
     * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D0%B5%D0%B9_%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0_fields">
     *     Documentation on vk.com</a>
     */
    public final static String[] ALL_FIELDS = {
            "uid",
            "first_name",
            "last_name",
            "sex",
            "bdate",
            "city",
            "country",
            "photo_50",
            "photo_100",
            "photo_200_orig",
            "photo_200",
            "photo_400_orig",
            "photo_max",
            "photo_max_orig",
            "online",

            // "lists", Not supported at the moment

            "screen_name",
            "has_mobile",

            // Not supported at the moment
            // "contacts,"
            // "education",
            // "universities",
            // "schools",
            // "can_post",
            // "can_see_all_posts",
            // "can_write_private_message",

            "activity",
            "last_seen",
            "relation",

            // Not supported at the moment
            // "counters",

            "nickname",

            // Not supported at the moment
            // "exports",
            // "wall_comments",
            // "relatives",
            // "interests",
            // "movies",
            // "tv",
            // "books",
            // "games",
            // "about",
            // "connections",
    };

    /**
     * firstName and lastName name cases
     * could be used as param in some api methods
     */
    public enum NameCase {
        nom,
        gen,
        dat,
        acc,
        ins,
        abl,
    }

    /**
     * Information update time, needed for most apps
     */
    public Long updateTime;

    /**
	 * vk.com user id
	 */
	public Long uId;

    /**
     * First name
     */
    public String firstName;

    /**
     * Last name
     */
    public String lastName;

    /**
     * User`s sex
     * 1 - female
     * 2 - male
     * 0 - unknown
     */
    public Integer sex;

    /**
     * Birthday date
     * For example: "23.11.1981" or "21.9" if year is hidden
     */
    public String bDate;

    /**
     * Id of user`s city
     * @see com.artemzin.android.vk.api.VKApi# getCities
     */
    public Long city;

    /**
     * Id of user`s country
     * @see com.artemzin.android.vk.api.VKApi# getCountries
     */
    public Long country;

    /**
     * Url to square photo with width == height == 50px
     */
    public String photo50;

    /**
     * Url to square photo with width == height == 100px
     */
    public String photo100;

    /**
     * Url to not necessarily square photo with width = 200px
     */
    public String photo200Orig;

    /**
     * Url to square photo with width == height == 200px
     */
    public String photo200;

    /**
     * Url to not necessarily square photo with width == 400px
     */
    public String photo400Orig;

    /**
     * Url to square photo with maximum of possible resolution
     */
    public String photoMax;

    /**
     * Url to not necessarily square photo with maximum of possible resolution
     */
    public String photoMaxOrig;

    /**
     * Is user online
     */
    public Boolean online;

    /**
     * If user is online from mobile device
     */
    public Boolean onlineMobile;

    /**
     * Id of application, from which user is online
     * True only if user using some application, not web site
     */
    public Long onlineApp;

    // TODO Add lists

    /**
     * Part of link after http://vk.com/...
     * Example: id172672179 or "andrew"
     */
    public String screenName;

    /**
     * Is user`s mobile phone number known
     */
    public Boolean hasMobile;

    // TODO Add contacts

    // TODO Add education

    // TODO Add universities

    // TODO Add schools

    // TODO Add can_post

    // TODO Add can_see_all_posts

    // TODO Add can_write_private_message

    /**
     * User`s activity status
     * Example: "Walking with my friends"
     */
    public String activity;

    /**
     * Last seen time when user was online
     */
    public Long lastSeen;

    /**
     * Kind of relationship of user <br>
     * 1 - Single <br>
     * 2 - have a boyfriend / girlfriend <br>
     * 3 - Engaged <br>
     * 4 - Married <br>
     * 5 - It is difficult <br>
     * 6 - In active search <br>
     * 7 - In love <br>
     */
    public Integer relation;

    // TODO Add counters

    /**
     * User`s nickname
     */
    public String nickName;

    // TODO Add exports

    // TODO Add wall_comments

    // TODO Add relatives

    // TODO Add interests

    // TODO Add movies

    // TODO Add tv

    // TODO Add books

    // TODO Add games

    // TODO Add about

    // TODO Add connections

    /**
     * @see <a href="https://vk.com/developers.php?oid=-1&p=friends.areFriends">VK isFriend docs</a>
     * Is user a friend
     * 0 is not a friend
     * 1 sended request to be friends
     * 2 incoming request to be friends from outer user
     * 3 is a friend
     */
    public Integer isFriend;
	
	/**
	 * Parsing VKUser object from JSON
	 * @param json with user`s data
	 * @return VKUser object with parsed fields
	 */
	public static VKUser parseFromJSON(JSONObject json) {
		VKUser user = new VKUser();
		if (!json.isNull("uid"))
            user.uId = json.optLong("uid");
		if (!json.isNull("first_name"))
			user.firstName = json.optString("first_name");
		if (!json.isNull("last_name"))
			user.lastName = json.optString("last_name");
        if (!json.isNull("sex"))
            user.sex = json.optInt("sex");
        if (!json.isNull("bdate"))
            user.bDate = json.optString("bdate");
        if (!json.isNull("city"))
            user.city = json.optLong("city");
        if (!json.isNull("country"))
            user.country = json.optLong("country");
        if (!json.isNull("photo_50"))
            user.photo50 = json.optString("photo_50");
        if (!json.isNull("photo_100"))
            user.photo100 = json.optString("photo_100");
        if (!json.isNull("photo_200"))
            user.photo200 = json.optString("photo_200");
        if (!json.isNull("photo_200_orig")) {
            String response = json.optString("photo_200_orig");
            if (!response.equals("false"))
                user.photo200Orig = response;
        }
        if (!json.isNull("photo_400_orig"))
            user.photo400Orig = json.optString("photo_400_orig");
        if (!json.isNull("photo_max"))
            user.photoMax = json.optString("photo_max");
        if (!json.isNull("photo_max_orig"))
            user.photoMaxOrig = json.optString("photo_max_orig");
        if (!json.isNull("online"))
            user.online = json.optInt("online") == 1;
        if (json.has("online_mobile"))
            user.onlineMobile = true;
        if (!json.isNull("online_app"))
            user.onlineApp = json.optLong("online_app");

        // Add lists

        if (!json.isNull("screen_name"))
            user.screenName = json.optString("screen_name");
        if (!json.isNull("has_mobile"))
            user.hasMobile = json.optInt("has_mobile") == 1;

        // Add contacts

        // Add education

        // Add universities

        // Add schools

        // Add can_post

        // Add can_see_all_posts

        // Add can_write_private_message

        if (!json.isNull("activity"))
            user.activity = json.optString("activity");
        if (!json.isNull("last_seen")) {
            JSONObject o = json.optJSONObject("last_seen");
            if (!o.isNull("time")) {
                user.lastSeen = o.optLong("time");
            }
        }
        if (!json.isNull("relation"))
            user.relation = json.optInt("relation");

        // Add counters

        if (!json.isNull("nickname"))
			user.nickName = json.optString("nickname");

        // Add exports

        // Add wall_comments

        // Add interests

        // Add movies

        // Add tv

        // Add books

        // Add games

        // Add about

        // Add connections

		return user;
	}

}
