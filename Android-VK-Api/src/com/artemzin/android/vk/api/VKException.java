package com.artemzin.android.vk.api;

/**
 * Exception, which explains vk.com error status
 * It could be query error, etc...
 * Could be with attached captcha object
 * @author Artem Zinnatullin
 *
 */
public class VKException extends Exception {
	
	/**
	 * Auto generated serial for correct serializing
	 */
	private static final long serialVersionUID = 7588760944264937573L;

	/**
	 * vk.com error code for query
	 */
	public final int errorCode;
	
	/**
	 * Capthca object, attached to VKException
	 */
	private VKCaptcha captcha;
	
	/**
	 * Creating new VKException
	 * @param errorCode of query
	 * @param errorMessage text, explaning error code
	 */
	public VKException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}
	
	/**
	 * Attaching captcha object to exception
	 * @param captcha is capthca, which is needs to attach
	 */
	public void attachCaptcha(VKCaptcha captcha) {
		this.captcha = captcha;
	}
	
	/**
	 * If captcha attached to exception
	 * @return true if captcha attached, false if no captcha attached
	 */
	public boolean isCaptchaAttached() {
        return captcha != null;
	}
	
	/**
	 * Get captcha object, attached to exception
	 * @return attached captcha object or null, if no captcha was attached
	 */
	public VKCaptcha getCaptchaAttached() {
		return this.captcha;
	}
}
