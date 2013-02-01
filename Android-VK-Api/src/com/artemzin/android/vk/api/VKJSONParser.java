package com.artemzin.android.vk.api;

import com.artemzin.android.vk.api.VKUtils;
import com.artemzin.android.vk.api.elements.VKGroup;
import com.artemzin.android.vk.api.elements.VKMessage;
import com.artemzin.android.vk.api.elements.VKUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * JSON parser <br/>
 * Parsing JSON to -> elements objects
 */
public class VKJSONParser {

    /**
	 * Parsing VKUser object from JSON
	 * @param json with user`s data
	 * @return VKUser object with parsed fields
	 */
	public static VKUser parseUserFromJSON(JSONObject json) {
		VKUser user = new VKUser();
		if (!json.isNull("uid"))
            user.setUId(json.optLong("uid"));
		if (!json.isNull("first_name"))
			user.setFirstName(json.optString("first_name"));
		if (!json.isNull("last_name"))
			user.setLastName(json.optString("last_name"));
        if (!json.isNull("sex"))
            user.setSex(json.optInt("sex"));
        if (!json.isNull("bdate"))
            user.setBDate(json.optString("bdate"));
        if (!json.isNull("city"))
            user.setCity(json.optLong("city"));
        if (!json.isNull("country"))
            user.setCountry(json.optLong("country"));
        if (!json.isNull("photo_50"))
            user.setPhoto50(json.optString("photo_50"));
        if (!json.isNull("photo_100"))
            user.setPhoto100(json.optString("photo_100"));
        if (!json.isNull("photo_200"))
            user.setPhoto200(json.optString("photo_200"));
        if (!json.isNull("photo_200_orig")) {
            String response = json.optString("photo_200_orig");
            if (!response.equals("false"))
                user.setPhoto200Orig(response);
        }
        if (!json.isNull("photo_400_orig"))
            user.setPhoto400Orig(json.optString("photo_400_orig"));
        if (!json.isNull("photo_max"))
            user.setPhotoMax(json.optString("photo_max"));
        if (!json.isNull("photo_max_orig"))
            user.setPhotoMaxOrig(json.optString("photo_max_orig"));
        if (!json.isNull("online"))
            user.setOnline(json.optInt("online") == 1);
        if (json.has("online_mobile"))
            user.setOnlineMobile(true);
        if (!json.isNull("online_app"))
            user.setOnlineApp(json.optLong("online_app"));

        // Add lists

        if (!json.isNull("screen_name"))
            user.setScreenName(json.optString("screen_name"));
        if (!json.isNull("has_mobile"))
            user.setHasMobile(json.optInt("has_mobile") == 1);

        // Add contacts

        // Add education

        // Add universities

        // Add schools

        // Add can_post

        // Add can_see_all_posts

        // Add can_write_private_message

        if (!json.isNull("activity"))
            user.setActivity(json.optString("activity"));
        if (!json.isNull("last_seen")) {
            JSONObject o = json.optJSONObject("last_seen");
            if (!o.isNull("time")) {
                user.setLastSeen(o.optLong("time"));
            }
        }
        if (!json.isNull("relation"))
            user.setRelation(json.optInt("relation"));

        // Add counters

        if (!json.isNull("nickname"))
			user.setNickName(json.optString("nickname"));

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

    /**
	 * Parsing json object and creating VKGroup object
	 * @param json object to parse from
	 * @return VKGroup with fields from json
	 */
	public static VKGroup parseGroupFromJSON(JSONObject json) {
		VKGroup group = new VKGroup();
		if (!json.isNull("gid"))
            group.setGId(json.optLong("gid"));
		if (!json.isNull("name"))
            group.setName(json.optString("name"));
        if (!json.isNull("is_closed"))
            group.setIsClosed(1 == json.optInt("is_closed"));
        if (!json.isNull("is_admin"))
            group.setIsAdmin(1 == json.optInt("is_admin"));
        if (!json.isNull("photo"))
            group.setPhoto(json.optString("photo"));
        if (!json.isNull("photo_medium"))
            group.setPhotoMedium(json.optString("photo_medium"));
        if (!json.isNull("photo_big"))
            group.setPhotoBig(json.optString("photo_big"));
        if (!json.isNull("screen_name"))
            group.setScreenName(json.optString("screen_name"));
        if (!json.isNull("city"))
            group.setCity(json.optLong("city"));
        if (!json.isNull("country"))
            group.setCountry(json.optLong("country"));

        // Add place

        if (!json.isNull("description"))
            group.setDescription(json.optString("description"));
        if (!json.isNull("wiki_page"))
            group.setWikiPage(json.optString("wiki_page"));
        if (!json.isNull("members_count"))
            group.setMembersCount(json.optLong("members_count"));

        // Add counters

        // Add start_date

        // Add end_date

        if (!json.isNull("can_post"))
            group.setCanPost(1 == json.optInt("can_post"));
        if (!json.isNull("activity"))
            group.setActivity(json.optString("activity"));

		return group;
	}

    /**
	 * Parsing json array with groups list
	 * @param jsonArray to parse
	 * @return array list with VKGroups. Always check ArrayList size!
	 */
	public static ArrayList<VKGroup> parseGroupsFromJSON(JSONArray jsonArray) {
		ArrayList<VKGroup> groups = new ArrayList<VKGroup>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			// Skipping non JSONObject elements;
			// VK put array length in first element
			try {
				if (jsonArray.get(i) instanceof JSONObject) {
					JSONObject jGroup = (JSONObject) jsonArray.get(i);
					groups.add(parseGroupFromJSON(jGroup));
				}
			} catch (JSONException ignored) {
			}
		}
		return groups;
	}

    /**
	 * Creating VKMessage object from JSON
	 * @param json with message data
	 */
	public static VKMessage parseMessageFromJSON(JSONObject json) {
		VKMessage message = new VKMessage();
        if (!json.isNull("mid"))
            message.setMId(json.optLong("mid"));
        if (!json.isNull("uid"))
            message.setUId(json.optLong("uid"));
        if (!json.isNull("date"))
            message.setDate(json.optLong("date"));
        if (!json.isNull("read_state"))
            message.setReadState(json.optInt("read_state") == 1);
        if (!json.isNull("out"))
            message.setOut(json.optInt("out") == 1);
        if (!json.isNull("title"))
            message.setTitle(VKUtils.unescape(json.optString("title")));
        if (!json.isNull("body"))
            message.setBody(VKUtils.unescape(json.optString("body")));

        // Add attachments

        if (!json.isNull("fwd_messages")) {
            JSONArray jsonForwardedMessagesArray = json.optJSONArray("fwd_messages");
            ArrayList<VKMessage> fwdMessages = new ArrayList<VKMessage>();
            for (int i = 0; i < jsonForwardedMessagesArray.length(); i++) {
                VKMessage fwdMessage = parseMessageFromJSON((JSONObject) jsonForwardedMessagesArray.opt(i));
                fwdMessages.add(fwdMessage);
            }
            message.setFwdMessages(fwdMessages);
        }
        if (!json.isNull("chat_id"))
            message.setChatId(json.optLong("chat_id"));
        if (!json.isNull("chat_active")) {
            String chatActiveString = json.optString("chat_active");
            ArrayList<Long> chatActive = new ArrayList<Long>();
            String[] uIds = chatActiveString.split(",");
            for(String uId: uIds) {
                chatActive.add(Long.parseLong(uId));
            }
            message.setChatActive(chatActive);
        }
        if (!json.isNull("users_count"))
            message.setUsersCount(json.optInt("users_count"));
        if (!json.isNull("admin_id"))
            message.setAdminId(json.optLong("admin_id"));
        if (json.has("deleted"))
			message.setDeleted(true);
		if (json.has("emoji"))
			message.setEmoji(true);
		return message;
	}
}
