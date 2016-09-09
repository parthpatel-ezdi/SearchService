
package com.ezdi.cac.autosuggest.response;


public class Header {

	private boolean success;
	private int code = 0;
	private String message = "";

    public Header(){

    }

    public Header(String message, int code, Boolean success) {
        this.message = message;
        this.code = code;
        this.success = success;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
