/**
 * 
 */
package com.ezdi.cac.autosuggest.response;

/**
 * @author akash.p
 * 
 */
public class JsonResponse {

	private Header header;

	private Object data;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
