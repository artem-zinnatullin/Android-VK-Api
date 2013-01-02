package com.artemzin.android.vk.api;

/**
 * Sometimes vk.com wants user to confirm that he/she not a robot
 * So, we need to show user captcha img
 * @author Artem Zinnatullin
 *
 */
public class VKCaptcha {
	
	/**
	 * VKCaptcha session id
	 */
	public final String captchaSId;
	
	/**
	 * VKCaptcha img url
	 */
	public final String captchaImgUrl;
	
	/**
	 * Creating new VKCaptcha object
	 * @param captchaSId captcha session id
	 * @param captchaImgResource captcha image resource
	 */
	public VKCaptcha(String captchaSId, String captchaImgResource) {
		this.captchaSId = captchaSId;
		this.captchaImgUrl = captchaImgResource;
	}
}
