package com.artemzin.android.vk.api.attachments;

/**
 * Attachment class
 *
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D1%8F_attachments">VK Attachments api</a>
 */
public class VKAttachment {


    /**
     * Types of VKAttachments
     * I know, that I could use Enum, but i think it will be better
     * To avoid Enum.toString() every time
     */
    public static class Types {
        /**
         * Photo from album
         */
        public static final String PHOTO = "photo";

        /**
         * Uploaded directly from computer photo
         */
        public static final String POSTED_PHOTO = "posted_photo";

        /**
         * Video
         */
        public static final String VIDEO = "video";

        /**
         * Audio record
         */
        public static final String AUDIO = "audio";

        /**
         * Graffiti picture
         */
        public static final String GRAFFITI = "graffiti";

        /**
         * Link to web page
         */
        public static final String LINK = "link";

        /**
         * Note
         */
        public static final String NOTE = "note";

        /**
         * Image, uploaded by third-partly application
         */
        public static final String APP = "app";

        /**
         * Vote
         */
        public static final String POLL = "poll";

        /**
         * Vk Wiki page
         */
        public static final String PAGE = "page";

    }

}
