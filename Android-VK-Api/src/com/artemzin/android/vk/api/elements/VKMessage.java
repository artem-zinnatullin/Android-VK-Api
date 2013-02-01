package com.artemzin.android.vk.api.elements;

import java.util.ArrayList;

/**
 * Class for vk message <br/>
 * ATTENTION: By default all fields are null
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/pages?oid=-1&p=%D0%A4%D0%BE%D1%80%D0%BC%D0%B0%D1%82_%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BB%D0%B8%D1%87%D0%BD%D1%8B%D1%85_%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D0%BD%D0%B8%D0%B9">
 *     VK Messages documentation</a>
 */
public class VKMessage {
	
	private Long mId;

    private Long uId;

    private Long date;

    private Boolean readState;

    private Boolean out;

	private String title;

    private String body;

    // TODO Add Attachments

    private ArrayList<VKMessage> fwdMessages;

    private Long chatId;

    private ArrayList<Long> chatActive;

    private Integer usersCount;

    private Long adminId;

    private Boolean deleted;

    private Boolean emoji;

    /**
     * Message mId
     */
    public Long getMId() {
        return mId;
    }

    public void setMId(Long mId) {
        this.mId = mId;
    }

    /**
     * uId of user who sent message
     */
    public Long getUId() {
        return uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * Message date in UNIX TIMESTAMP format / 1000
     */
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * Read state
     */
    public Boolean getReadState() {
        return readState;
    }

    public void setReadState(Boolean readState) {
        this.readState = readState;
    }

    /**
     * Is message out (to send)
     */
    public Boolean getOut() {
        return out;
    }

    public void setOut(Boolean out) {
        this.out = out;
    }

    /**
     * Message title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Message body
     */
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /**
     * ArrayList of forwarded messages
     */
    public ArrayList<VKMessage> getFwdMessages() {
        return fwdMessages;
    }

    public void setFwdMessages(ArrayList<VKMessage> fwdMessages) {
        this.fwdMessages = fwdMessages;
    }

    /**
     * Only for group dialogs, chat id
     */
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    /**
     * Only for group dialogs, ArrayList of last 6 uIds in chat
     */
    public ArrayList<Long> getChatActive() {
        return chatActive;
    }

    public void setChatActive(ArrayList<Long> chatActive) {
        this.chatActive = chatActive;
    }

    /**
     * Only for group dialogs, count of users in chat
     */
    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    /**
     * Only for group dialogs, chat admin`s uId
     */
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * Is message was deleted by user
     */
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Is message contains emoji smiles
     */
    public Boolean getEmoji() {
        return emoji;
    }

    public void setEmoji(Boolean emoji) {
        this.emoji = emoji;
    }

}
