package com.artemzin.android.vk.api;

import org.json.JSONObject;

/**
 * VK VKUser class
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D0%B5%D0%B9_%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0_fields">
 *     VK User docs</a>
 */
public class VKUser {

	/**
	 * vk.com user id
	 */
	public long id;
	
	/**
	 * Information update time
	 */
	public long updateTime;
	
	/**
	 * @see <a href="https://vk.com/developers.php?oid=-1&p=friends.areFriends">VK isFriend docs</a>
	 * Is user a friend
	 * 0 is not a friend
	 * 1 sended request to be friends
	 * 2 incoming request to be friends from outer user
	 * 3 is a friend
	 */
	public int isFriend;
	
	/**
	 * VKUser first name
	 */
	public String firstName;
	
	/**
	 * VKUser last name
	 */
	public String lastName;
	
	/**
	 * VKUser nickname
	 */
	public String nickName;
	
	/**
	 * VKUser sex
	 * 1 - female
	 * 2 - male
	 * 0 - unknown
	 */
	public int sex;
	
	/**
	 * VKUser online status
	 */
	public boolean isOnline;
	
	/**
	 * If user is online from mobile device
	 */
	public boolean isOnlineMobile;
	
	/**
	 * Birthday date
	 */
	public String birthDate;
	
	/**
	 * Url to photo with width==50px
	 */
	public String photoUrl;
	
	/**
	 * Url to photo with width==100px
	 */
	public String photoMediumUrl;
	
	/**
	 * Url to square photo with witdh==height==100px 
	 */
	public String photoMediumRecUrl;
	
	/**
	 * Url to photo with witdh==200px
	 */
	public String photoBigUrl;
	
	/**
	 * Url to square photo with width==height==50px
	 */
	public String photoRecUrl;
	
	/**
	 * Code of user city
	 */
	public int cityCode;
	
	/**
	 * Code of user country
	 */
	public int countryCode;
	
	/**
	 * VKUser`s timezone
	 */
	public int timezone;
	
	/**
	 * Is user has mobile phone
	 */
	public boolean isHasMobilePhone;
	
	/**
	 * Part of link after http://vk.com/...
	 * Example: id172672179
	 */
	public String screenName;
	
	/**
	 * VKUser rate
	 */
	public String rate;
	
	/**
	 * VKUser activity status
	 * Example: Walking with my friends
	 */
	public String activity;
	
	/**
	 * Last seen time
	 */
	public long lastSeen;
	
	/**
	 * Mobile phone number
	 */
	public String mobilePhoneNumber;
	
	/**
	 * Home phone number
	 */
	public String homePhoneNumber;
	
	{
		firstName  = "";
		lastName   = "";
	}
	
	/**
	 * Parsing VKUser object from JSON
	 * @param json with user data
	 * @return VKUser object with parsed fields
	 */
	public static VKUser parseFromJSON(JSONObject json) {
		VKUser user = new VKUser();
		user.id = json.optLong("uid");
		if (!json.isNull("first_name")) {
			user.firstName = json.optString("first_name");
		}
		if (!json.isNull("last_name")) {
			user.lastName = json.optString("last_name");
		}
		if (!json.isNull("nickname")) {
			user.nickName = json.optString("nickname");
		}
		if (!json.isNull("nickname")) {
			user.nickName = json.optString("nickname");
		}
		if (!json.isNull("online")) {
			user.isOnline = json.optInt("online") == 1;
		}
		if (!json.isNull("online_mobile")) {
			user.isOnlineMobile = json.optInt("online_mobile") == 1;
		}
		if (!json.isNull("sex")) {
			user.sex = json.optInt("sex");
		}
		if (!json.isNull("bdate")) {
			user.birthDate = json.optString("bdate");
		}
		if (!json.isNull("city")) {
			user.cityCode = json.optInt("city");
		}
		if (!json.isNull("country")) {
			user.countryCode = json.optInt("country");
		}
		if (!json.isNull("timezone")) {
			user.timezone = json.optInt("timezone");
		}
		if (!json.isNull("photo")) {
			user.photoUrl = json.optString("photo");
		}
		if (!json.isNull("photo_medium")) {
			user.photoMediumUrl = json.optString("photo_medium");
		}
		if (!json.isNull("photo_medium_rec")) {
			user.photoMediumRecUrl = json.optString("photo_medium_rec");
		}
		if (!json.isNull("photo_medium_rec")) {
			user.photoMediumRecUrl = json.optString("photo_medium_rec");
		}
		if (!json.isNull("photo_big")) {
			user.photoBigUrl = json.optString("photo_big");
		}
		if (!json.isNull("photo_rec")) {
			user.photoRecUrl = json.optString("photo_rec");
		}
		if (!json.isNull("has_mobile")) {
			user.isHasMobilePhone = json.optInt("has_mobile") == 1;
		}
		if (!json.isNull("screen_name")) {
			user.screenName = json.optString("screen_name");
		}
		if (!json.isNull("rate")) {
			user.rate = json.optString("rate");
		}
		if (!json.isNull("activity")) {
			user.activity = json.optString("activity");
		}
		if (!json.isNull("last_seen")) {
			JSONObject o = json.optJSONObject("last_seen");
			if (!o.isNull("time")) {
				user.lastSeen = o.optLong("time");
			}
		}
		if (!json.isNull("mobile_phone")) {
			user.mobilePhoneNumber = json.optString("mobile_phone");
		}
		if (!json.isNull("home_phone")) {
			user.homePhoneNumber = json.optString("home_phone");
		}
		return user;
	}
	
}
