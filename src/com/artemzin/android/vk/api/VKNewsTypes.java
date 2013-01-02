package com.artemzin.android.vk.api;

/**
 * Types of news in vk.com
 * @author Artem Zinnatullin
 * @see <a href="https://vk.com/developers.php?oid=-1&p=newsfeed.get">VK NewsFeed api</a>
 */
public enum VKNewsTypes {
	/**
	 * New post from wall
	 */
	POST,
	/**
	 * New photo
	 */
	PHOTO,
	/**
	 * New tags on photos
	 */
	PHOTO_TAG,
	/**
	 * New photo from wall
	 */
	WALL_PHOTO,
	/**
	 * New friend
	 */
	FRIEND,
	/**
	 * New note
	 */
	NOTE,
}
