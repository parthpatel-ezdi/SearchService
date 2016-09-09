package com.ezdi.cac.autosuggest.business;


import com.ezdi.cac.autosuggest.constant.Constants;
import com.ezdi.cac.autosuggest.dto.SearchSuggestionRequest;
import com.ezdi.cac.autosuggest.dto.SearchSuggestionResponse;
import com.ezdi.cac.autosuggest.service.SearchService;
import com.ezdi.cac.autosuggest.util.SearchUtil;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class AutoSuggestBusinessImpl implements AutoSuggestBusiness {

	@Autowired
	private SearchService searchService;

	@Autowired
	private SearchUtil searchUtil;




	@Override
	public List<SearchSuggestionResponse> getSearchSuggestion(SearchSuggestionRequest searchRequest) {

		String searchKey = searchRequest.getCurrentSearchCriteria().getKey();
		searchKey = searchKey.replaceAll("\\\\","\\\\\\\\");
		String filterCategory = searchRequest.getCurrentSearchCriteria().getFilterCategory();

		//TODO I think  It will be your  PBACK Permission.
		
		String flQuery = Constants.EMPTY_STRING;

		Set<SearchSuggestionResponse> searchSuggestionResponse;
		if(filterCategory!=null){
			searchSuggestionResponse = new LinkedHashSet<SearchSuggestionResponse>();
		}else {
			searchSuggestionResponse = searchUtil.getSearchSuggestionFields(searchKey);
		}
		int start = 0;
		int max = Constants.MAX_SEARCH_SUGGESTION;
		QueryResponse queryResponse = searchService.getSearchSuggestion(searchKey, filterCategory, flQuery, start, max);
		Map<String, Map<String, List<String>>> highlightingResponse = queryResponse.getHighlighting();
			for (Map<String, List<String>> result : highlightingResponse.values()) {
				for (Map.Entry<String, List<String>> columns : result.entrySet()) {
					String key = columns.getKey();
					for (String row : columns.getValue()) {
						searchSuggestionResponse.add(searchUtil.processSearchResponseSolr(key, searchKey, row));
					}
				}
			}
		return new ArrayList<SearchSuggestionResponse>(searchSuggestionResponse);
	}







}
