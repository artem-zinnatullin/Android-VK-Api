package com.artemzin.android.vk.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class for vk message
 * ATTENTION: By default all fields are null
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/pages?oid=-1&p=%D0%A4%D0%BE%D1%80%D0%BC%D0%B0%D1%82_%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BB%D0%B8%D1%87%D0%BD%D1%8B%D1%85_%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D0%BD%D0%B8%D0%B9">
 *     VK Messages documentation</a>
 */
public class VKMessage {
	
	/**
	 * Message id
	 */
	public long id;
	
	/**
	 * User id who send message
	 */
	public long userId;
	
	/**
	 * Who sended message
	 */
	public long fromId;
	
	/**
	 * Message date
	 */
	public long date;
	
	/**
	 * Message title
	 */
	public String title;
	
	/**
	 * Message body
	 */
	public String body;
	
	/**
	 * Read state
	 */
	public boolean isReaded;
	
	/**
	 * Is message out (to send)
	 */
	public boolean isOut;
	
	// TODO Add Attachments
	
	/**
	 * Chat id
	 */
	public long chatId;
	
	/**
	 * Users ids in chat
	 */
	public ArrayList<Long> chatMembers;
	
	/**
	 * Chat admin user id
	 */
	public long chatAdminUId;
	
	/**
	 * ArrayList of forwarded messages
	 */
	public ArrayList<VKMessage> forwardedMessages;
	
	/**
	 * Is message contains emoji smiles
	 */
	public boolean isEmoji;
	
	/**
	 * Is message was deleted by user
	 */
	public boolean isDeleted;
	
	/**
	 * Creating VKMessage object from JSON
	 * @param json with message data
	 */
	public static VKMessage parseFromJSON(JSONObject json) {
		VKMessage message = new VKMessage();
		if (json.has("deleted")) {
			message.isDeleted = true;
		}
		if (!json.isNull("body")) {
			message.body = VKUtils.unescape(json.optString("body"));
		}
		if (!json.isNull("date")) {
			message.date = json.optLong("date");
		}
		if (!json.isNull("mid")) {
			message.id = json.optLong("mid");
		}
		if (!json.isNull("uid")) {
			message.userId = json.optLong("uid");
		}
		if (!json.isNull("title")) {
			message.title = VKUtils.unescape(json.optString("title"));
		}
		if (!json.isNull("out")) {
			message.isOut = json.optInt("out") == 1;
		}
		if (!json.isNull("from_id")) {
			message.fromId = json.optLong("from_id");
		}
		if (!json.isNull("read_state")) {
			message.isReaded = json.optInt("read_state") == 1;
		}
		if (json.has("emoji")) {
			message.isEmoji = true;
		}
		if (!json.isNull("chat_id")) {
			message.chatId = json.optLong("chat_id");
		}
		if (!json.isNull("admin_id")) {
			message.chatAdminUId = json.optLong("admin_id");
		}
		if (!json.isNull("chat_active")) {
			String chatActive = json.optString("chat_active");
			message.chatMembers = new ArrayList<Long>();
			String[] uIds = chatActive.split(",");
            for(String uId: uIds) {
            	message.chatMembers.add(Long.parseLong(uId));
            }
		}
		if (!json.isNull("fwd_messages")) {
			JSONArray jsonForwardedMessagesArray = json.optJSONArray("fwd_messages");
			message.forwardedMessages = new ArrayList<VKMessage>();
			for (int i = 0; i < jsonForwardedMessagesArray.length(); i++) {
				VKMessage forwardedMessage = VKMessage.parseFromJSON((JSONObject)jsonForwardedMessagesArray.opt(i));
				message.forwardedMessages.add(forwardedMessage);
			}
		}
		return message;
	}
}
