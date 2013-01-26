package com.artemzin.android.vk.api.elements;

import java.util.ArrayList;

import com.artemzin.android.vk.api.VKUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class for vk message <br/>
 * ATTENTION: By default all fields are null
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/pages?oid=-1&p=%D0%A4%D0%BE%D1%80%D0%BC%D0%B0%D1%82_%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BB%D0%B8%D1%87%D0%BD%D1%8B%D1%85_%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D0%BD%D0%B8%D0%B9">
 *     VK Messages documentation</a>
 */
public class VKMessage {
	
	private Long mId;

    /**
     * Message mId
     */
    public Long getMId() {
        return mId;
    }

    private Long uId;

    /**
     * uId of user who sent message
     */
    public Long getUId() {
        return uId;
    }

    private Long date;

    /**
     * Message date
     */
    public Long getDate() {
        return date;
    }

    private Boolean readState;

    /**
     * Read state
     */
    public Boolean getReadState() {
        return readState;
    }

    private Boolean out;

    /**
     * Is message out (to send)
     */
    public Boolean getOut() {
        return out;
    }

	private String title;

    /**
     * Message title
     */
    public String getTitle() {
        return title;
    }

    private String body;

    /**
     * Message body
     */
    public String getBody() {
        return body;
    }

    // TODO Add Attachments

    private ArrayList<VKMessage> fwdMessages;

    /**
     * ArrayList of forwarded messages
     */
    public ArrayList<VKMessage> getFwdMessages() {
        return fwdMessages;
    }

    private Long chatId;

    /**
     * Only for group dialogs, chat id
     */
    public Long getChatId() {
        return chatId;
    }

    private ArrayList<Long> chatActive;

    /**
     * Only for group dialogs, ArrayList of last 6 uIds in chat
     */
    public ArrayList<Long> getChatActive() {
        return chatActive;
    }

    private Integer usersCount;

    /**
     * Only for group dialogs, count of users in chat
     */
    public Integer getUsersCount() {
        return usersCount;
    }

    private Long adminId;

    /**
     * Only for group dialogs, chat admin`s uId
     */
    public Long getAdminId() {
        return adminId;
    }

    private Boolean deleted;

    /**
     * Is message was deleted by user
     */
    public Boolean getDeleted() {
        return deleted;
    }

    private Boolean emoji;

    /**
     * Is message contains emoji smiles
     */
    public Boolean getEmoji() {
        return emoji;
    }

    /**
	 * Creating VKMessage object from JSON
	 * @param json with message data
	 */
	public static VKMessage parseFromJSON(JSONObject json) {
		VKMessage message = new VKMessage();
        if (!json.isNull("mid"))
            message.mId = json.optLong("mid");
        if (!json.isNull("uid"))
            message.uId = json.optLong("uid");
        if (!json.isNull("date"))
            message.date = json.optLong("date");
        if (!json.isNull("read_state"))
            message.readState = json.optInt("read_state") == 1;
        if (!json.isNull("out"))
            message.out = json.optInt("out") == 1;
        if (!json.isNull("title"))
            message.title = VKUtils.unescape(json.optString("title"));
        if (!json.isNull("body"))
            message.body = VKUtils.unescape(json.optString("body"));

        // Add attachments

        if (!json.isNull("fwd_messages")) {
            JSONArray jsonForwardedMessagesArray = json.optJSONArray("fwd_messages");
            message.fwdMessages = new ArrayList<VKMessage>();
            for (int i = 0; i < jsonForwardedMessagesArray.length(); i++) {
                VKMessage fwdMessage = VKMessage.parseFromJSON((JSONObject)jsonForwardedMessagesArray.opt(i));
                message.fwdMessages.add(fwdMessage);
            }
        }
        if (!json.isNull("chat_id"))
            message.chatId = json.optLong("chat_id");
        if (!json.isNull("chat_active")) {
            String chatActive = json.optString("chat_active");
            message.chatActive = new ArrayList<Long>();
            String[] uIds = chatActive.split(",");
            for(String uId: uIds) {
                message.chatActive.add(Long.parseLong(uId));
            }
        }
        if (!json.isNull("users_count"))
            message.usersCount = json.optInt("users_count");
        if (!json.isNull("admin_id"))
            message.adminId = json.optLong("admin_id");
        if (json.has("deleted"))
			message.deleted = true;
		if (json.has("emoji"))
			message.emoji = true;
		return message;
	}
}
