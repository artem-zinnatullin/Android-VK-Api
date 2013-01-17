package com.artemzin.android.vk.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * VK News Feed. Constains VKNewsObject
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php/newsfeed.get">VK NewsFeed api</a>
 */
public class VKNewsFeed {
	
	/**
	 * News items of news feed
	 */
	public ArrayList<VKNewsItem> items = new ArrayList<VKNewsItem>();
	
	/**
	 * Users, whose uId`s in source of some news items
	 */
	public ArrayList<VKUser> profiles = new ArrayList<VKUser>();
	
	/**
	 * Groups, whose uId`s in source of some news items
	 */
	public ArrayList<VKGroup> groups = new ArrayList<VKGroup>();
	
	/**
	 * Parsing VKNewsFeed from JSONObject
	 * @param json to parse from
	 * @return VKNewsFeed with feed items, null if error occurred
	 */
	public static VKNewsFeed parseFromJSON(JSONObject json) {
		VKNewsFeed newsFeed = new VKNewsFeed();
		try {
			json = json.optJSONObject("response");
			JSONArray jNewsItems    = json.optJSONArray("items");
			JSONArray jNewsProfiles = json.optJSONArray("profiles");
			JSONArray jNewsGroups   = json.optJSONArray("groups");
			{
				for (int i = 0; i < jNewsItems.length(); i++) {
					VKNewsItem feedItem = VKNewsItem.parseFromJSON(jNewsItems.optJSONObject(i));
					newsFeed.items.add(feedItem);
				}
				
				if (jNewsProfiles != null) {
					for (int i = 0; i < jNewsProfiles.length(); i++) {
						VKUser feedProfile = VKUser.parseFromJSON(jNewsProfiles.optJSONObject(i));
						newsFeed.profiles.add(feedProfile);
					}
				}
				
				if (jNewsGroups != null) {
					newsFeed.groups = VKGroup.parseFromJSON(jNewsGroups);
				}
			}
		}
		catch (Exception e) {
			Log.e("VKNewsFeed", "Exception catched: " + e.getMessage());
			return null;
		}
		return newsFeed;
	}
}
