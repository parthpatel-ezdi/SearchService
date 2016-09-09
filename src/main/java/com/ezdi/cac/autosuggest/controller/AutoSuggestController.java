package com.ezdi.cac.autosuggest.controller;

import com.ezdi.cac.autosuggest.business.AutoSuggestBusiness;
import com.ezdi.cac.autosuggest.constant.Constants;
import com.ezdi.cac.autosuggest.dto.SearchSuggestionRequest;
import com.ezdi.cac.autosuggest.dto.SearchSuggestionResponse;
import com.ezdi.cac.autosuggest.response.Header;
import com.ezdi.cac.autosuggest.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class AutoSuggestController {

	@Autowired
	private AutoSuggestBusiness autoSuggestBusiness;



	@RequestMapping(value="/autoSuggestion", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse autoSuggestion(@RequestBody SearchSuggestionRequest request){
		List<SearchSuggestionResponse> searchResponses = autoSuggestBusiness.getSearchSuggestion(request);
		JsonResponse jsonResponse = getResponse(true, 200, Constants.SUCCESS, searchResponses);
		return jsonResponse;
	}


	public JsonResponse getResponse(boolean isSuccess, Integer code,String message, Object data) {
		JsonResponse returnBean = new JsonResponse();
		Header header = new Header();
		header.setCode(code);
		header.setSuccess(isSuccess);
		header.setMessage(message);
		returnBean.setHeader(header);
		returnBean.setData(data);
		return returnBean;
	}
}
