package com.artemzin.android.vk.api.elements;

import org.json.JSONObject;

/**
 * VKUser class
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D0%B5%D0%B9_%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0_fields">
 *     VK User docs</a>
 */
public class VKUser {

    /**
     * All fields, which are supported at the moment by this library <br/>
     * Needed for VKApi queries <br/>
     * @see com.artemzin.android.vk.api.VKApi#friends
     * @see com.artemzin.android.vk.api.VKApi#users
     * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D0%B5%D0%B9_%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0_fields">
     *     Documentation on vk.com</a>
     */
    public static final String[] ALL_FIELDS = {
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
     * firstName and lastName name cases <br/>
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

    private Long updateTime;

    private Long uId;

    private String firstName;

    private String lastName;

    private Integer sex;

    private String bDate;

    private Long city;

    private Long country;

    private String photo50;

    private String photo100;

    private String photo200Orig;

    private String photo200;

    private String photo400Orig;

    private String photoMax;

    private String photoMaxOrig;

    private Boolean online;

    private Boolean onlineMobile;

    private Long onlineApp;

    // TODO Add lists

    private String screenName;

    private Boolean hasMobile;

    // TODO Add contacts

    // TODO Add education

    // TODO Add universities

    // TODO Add schools

    // TODO Add can_post

    // TODO Add can_see_all_posts

    // TODO Add can_write_private_message

    private String activity;

    private Long lastSeen;

    private Integer relation;

    // TODO Add counters

    private String nickName;

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
     * Information update time, needed for most apps
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * vk.com user id
     */
    public Long getUId() {
        return uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * First name
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Last name
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Oh yeah baby! <br/>
     * User`s sex <br/>
     * 1 - female <br/>
     * 2 - male <br/>
     * 0 - unknown
     */
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * Birthday date <br/>
     * For example: "23.11.1981" or "21.9" if year is hidden
     */
    public String getBDate() {
        return bDate;
    }

    public void setBDate(String bDate) {
        this.bDate = bDate;
    }

    /**
     * Id of user`s city
     * @see com.artemzin.android.vk.api.VKApi# getCities
     */
    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * Id of user`s country
     * @see com.artemzin.android.vk.api.VKApi# getCountries
     */
    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    /**
     * Url to square photo with width == height == 50px
     */
    public String getPhoto50() {
        return photo50;
    }

    public void setPhoto50(String photo50) {
        this.photo50 = photo50;
    }

    /**
     * Url to square photo with width == height == 100px
     */
    public String getPhoto100() {
        return photo100;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }

    /**
     * Url to square photo with width == height == 200px
     */
    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }

    /**
     * Url to not necessarily square photo with width = 200px
     */
    public String getPhoto200Orig() {
        return photo200Orig;
    }

    public void setPhoto200Orig(String photo200Orig) {
        this.photo200Orig = photo200Orig;
    }

    /**
     * Url to not necessarily square photo with width == 400px
     */
    public String getPhoto400Orig() {
        return photo400Orig;
    }

    public void setPhoto400Orig(String photo400Orig) {
        this.photo400Orig = photo400Orig;
    }

    /**
     * Url to square photo with maximum of possible resolution
     */
    public String getPhotoMax() {
        return photoMax;
    }

    public void setPhotoMax(String photoMax) {
        this.photoMax = photoMax;
    }

    /**
     * Url to not necessarily square photo with maximum of possible resolution
     */
    public String getPhotoMaxOrig() {
        return photoMaxOrig;
    }

    public void setPhotoMaxOrig(String photoMaxOrig) {
        this.photo400Orig = photoMaxOrig;
    }

    /**
     * Is user online
     */
    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    /**
     * If user is online from mobile device
     */
    public Boolean getOnlineMobile() {
        return onlineMobile;
    }

    public void setOnlineMobile(Boolean onlineMobile) {
        this.onlineMobile = onlineMobile;
    }

    /**
     * Id of application, from which user is online <br/>
     * Exists only if user using some application, not web site
     */
    public Long getOnlineApp() {
        return onlineApp;
    }

    public void setOnlineApp(Long onlineApp) {
        this.onlineApp = onlineApp;
    }

    /**
     * Part of link after http://vk.com/... <br/>
     * Example: id172672179 or "andrew"
     */
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * Is user`s mobile phone number known
     */
    public Boolean getHasMobile() {
        return hasMobile;
    }

    public void setHasMobile(Boolean hasMobile) {
        this.hasMobile = hasMobile;
    }

    /**
     * User`s activity status <br/>
     * Example: "Walking with my friends"
     */
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * Last seen time when user was online in UNIX TIMESTAMP format / 1000
     */
    public Long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Long lastSeen) {
        this.lastSeen = lastSeen;
    }

    /**
     * Kind of relationship of user <br/>
     * 1 - Single <br/>
     * 2 - have a boyfriend / girlfriend <br/>
     * 3 - Engaged <br/>
     * 4 - Married <br/>
     * 5 - It is difficult <br/>
     * 6 - In active search <br/>
     * 7 - In love
     */
    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    /**
     * User`s nickname
     */
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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
