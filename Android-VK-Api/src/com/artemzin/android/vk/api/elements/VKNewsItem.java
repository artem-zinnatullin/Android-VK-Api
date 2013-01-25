package com.artemzin.android.vk.api.elements;

import org.json.JSONObject;

/**
 * Element of VK news feed
 * @author Artem Zinnatullin
 * @see <a href="https://vk.com/developers.php?oid=-1&p=newsfeed.get">VK NewsFeed item docs</a>
 */
public class VKNewsItem {
	
	/**
	 * Tags for vk news
	 * @author Artem Zinnatullin
	 * @see <a href="https://vk.com/developers.php?oid=-1&p=newsfeed.get">VK NewsItem docs</a>
	 */
	public static class Tags {
		public static final String PHOTO = "photo";
		public static final String PHOTOS = "photos";
		public static final String PHOTO_TAGS = "photo_tags";
		public static final String FRIENDS = "friends";
		public static final String NOTE = "note";
		public static final String NOTES = "notes";
		public static final String ATTACHMENT = "attachment";
		public static final String COMMENTS = "comments";
		public static final String LIKES = "likes";
		public static final String AUDIO = "audio";
		public static final String VIDEO = "video";
		public static final String REPOSTS = "reposts";
	}
	
	/**
	 * @see <a href="https://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D1%8F_likes">VK Likes docs</a>
	 */
	public static class Likes {
		public int count;
		public boolean isUserLikes;
		public boolean isUserAbleToLike;
		public boolean isUserAbleToPublish;
	}
	
	/**
	 * @see <a href="https://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D1%8F_reposts">VK Reposts docs</a>
	 */
	public static class Reposts {
		public int count;
		public boolean isUserReposted;
	}
	
	/**
	 * @see <a href="https://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE%D0%BB%D1%8F_comments">VK Comments docs</a>
	 */
	public static class Comments {
		public int count;
		public boolean isUserAbleToComment;
	}
	
	/**
	 * If news is in somebody`s wall, postId contains its wallId
	 */
	public long postId;
	
	/**
	 * Type of news
	 */
	public VKNewsTypes type;
	
	/**
	 * Id of news source
	 * If positive -> news from user;
	 * If negative -> news from group;
	 */
	public long sourceId;
	
	/**
	 * Publish date in UNIXTIME
	 */
	public long date;
	
	/**
	 * If news was copied from somebody`s wall, 
	 * it will contain author uId
	 */
	public long copyOwnerId;
	
	/**
	 * If news was copied from somebody`s wall,
	 * it will contain original itemId
	 */
	public long copyPostId;
	
	/**
	 * If news was copied from somebody`s wall, 
	 * it will contains original news publish date
	 */
	public long copyPostDate;
	
	/**
	 * News text
	 */
	public String text;
	
	/**
	 * Contains information about news comments.
	 * Null by default
	 */
	public Comments comments;
	
	/**
	 * Contains information about news likes.
	 * Null by default
	 */
	public Likes likes;
	
	/**
	 * Contains information about news reposts.
	 * Null by default
	 */
	public Reposts reposts;
	
	{
		text = "";
	}
	
	/**
	 * Parsing json object and returning VKNewsObject
	 * @param json to parse
	 * @return parsed VKNewsItem
	 */
	public static VKNewsItem parseFromJSON(JSONObject json) {
		VKNewsItem newsObject = new VKNewsItem();
		
		newsObject.type = VKNewsTypes.valueOf(json.optString("type").toUpperCase());
		newsObject.sourceId = json.optLong("source_id");
		newsObject.date = json.optLong("date");
		newsObject.postId = json.optLong("post_id");
		newsObject.copyOwnerId = json.optLong("copy_owner_id");
		newsObject.copyPostId = json.optLong("copy_post_id");
		newsObject.copyPostDate = json.optLong("copy_post_date");
		newsObject.text = json.optString("text");

		if (json.has(Tags.COMMENTS)) {
			JSONObject jComment = json.optJSONObject(Tags.COMMENTS);
			if (jComment != null) {
				newsObject.comments = new Comments();
				newsObject.comments.count = jComment.optInt("count");
				newsObject.comments.isUserAbleToComment = 1 == jComment.optInt("can_post", 0); // One guy once time got null here
			}
		}
		
		if (json.has(Tags.LIKES)) {
			JSONObject jLikes = json.optJSONObject(Tags.LIKES);
			if (jLikes != null) {
				newsObject.likes = new Likes();
				newsObject.likes.count = jLikes.optInt("count");
				newsObject.likes.isUserLikes = 1 == jLikes.optInt("user_likes");
				newsObject.likes.isUserAbleToLike = 1 == jLikes.optInt("can_like");
				newsObject.likes.isUserAbleToPublish = 1 == jLikes.optInt("can_publish");
			}
		}
		
		if (json.has(Tags.REPOSTS)) {
			JSONObject jReposts = json.optJSONObject(Tags.REPOSTS);
			if (jReposts != null) {
				newsObject.reposts = new Reposts();
				newsObject.reposts.count = jReposts.optInt("count");
				newsObject.reposts.isUserReposted = 1 == jReposts.optInt("user_reposted");
			}
		}
		
		return newsObject;
	}
}
