package com.ezdi.cac.autosuggest.util;


import com.ezdi.cac.autosuggest.constant.Constants;
import com.ezdi.cac.autosuggest.dto.SearchSuggestionResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.*;



@Configuration
public class SearchUtil {
	


	private Map<String, String> searchFieldMap = new HashMap<String, String>();
	private static Map<String, String> controllerStatusMap = new HashMap<String, String>();

	private List<String> searchFieldKeys;




	public void setSearchFieldMap(){
		try {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Properties properties = PropertiesLoaderUtils.loadProperties(resourceLoader.getResource(Constants.SEARCH_FIELD_PROP_FILE +Constants.PROPERTIES_EXT));
			searchFieldKeys = (List<String>) Collections.list(properties.propertyNames());
			searchFieldMap.clear();
			for(String property:searchFieldKeys){
				searchFieldMap.put(property,properties.getProperty(property));
			}
		} catch (Exception e) {

		}
	}

	public Map<String, String> getSearchFieldMap() {
		return searchFieldMap;
	}

	public String[] getSearchKeys() {
		return searchFieldKeys.toArray(new String[searchFieldKeys.size()]);
	}

	public SolrQuery createHighlightQuery(String searchHandle, String query, String flQuery, Integer start,Integer rows, String hlField){


		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setRequestHandler(searchHandle);
		solrQuery.setHighlight(true);
		solrQuery.setHighlightSimplePre(Constants.LESS_THAN_OPERATOR);
		solrQuery.setHighlightSimplePost(Constants.GREATER_THAN_OPERATOR);
		solrQuery.setFields(Constants.SEARCH_SELECT_DEFAULT_FIELD);
		solrQuery.setQuery(Constants.DOUBLE_QUOTES+query+Constants.DOUBLE_QUOTES);
		solrQuery.setFilterQueries(flQuery);
		solrQuery.setStart(start);
		solrQuery.setRows(rows);
		solrQuery.set(Constants.HIGHLIGHT_FIELD,getSearchKeys());
		return solrQuery;
	}

	public Set<SearchSuggestionResponse> getSearchSuggestionFields(String key){
		Set<SearchSuggestionResponse> searchSuggestionResponse = new LinkedHashSet<SearchSuggestionResponse>();
		for(Map.Entry<String,String> field :searchFieldMap.entrySet()){
			String value = field.getValue().toUpperCase();
			String upperCaseKey = key.toUpperCase();
			if (value.contains(upperCaseKey)) {
				SearchSuggestionResponse response = processSearchResponseField(field.getKey(), upperCaseKey, value);
				searchSuggestionResponse.add(response);
			}
		}

		return searchSuggestionResponse;
	}

	private SearchSuggestionResponse processSearchResponseField(String categoryKey,String searchKey,String searchValue) {
		SearchSuggestionResponse response = new SearchSuggestionResponse();
		response.setCategoryId(categoryKey);
		response.setSuggestionId(searchValue);
		StringBuffer returnSearchValue = getHighlightingString(searchKey, searchValue);
		response.setSuggestionString(returnSearchValue.toString());
		response.setSuggestionCategory(true);
		response.setSuggestionCategoryUrl(Constants.SEARCH_SUGGESTION_URL);
		response.setSuggestionCategoryUrlMethod(Constants.POST_METHOD);
		response.setSuggestionCategoryType(Constants.VALUE);
		response.setSuggestionLabel(searchValue);
		return response;
	}


	public SearchSuggestionResponse processSearchResponseSolr(String categoryKey,String searchKey,	String searchValue) {		
		searchValue=searchValue.replaceAll(Constants.LESS_THAN_OPERATOR,Constants.EMPTY_STRING).replaceAll(Constants.GREATER_THAN_OPERATOR, Constants.EMPTY_STRING);
		String categoryValue = searchFieldMap.get(categoryKey);
		SearchSuggestionResponse response = new SearchSuggestionResponse();
		response.setCategoryId(categoryKey);
		response.setSuggestionId(searchValue);
		StringBuffer returnSearchValue = getHighlightingString(searchKey, searchValue);
		response.setSuggestionString(returnSearchValue.toString() + Constants.IN_WITH_SPACE + categoryValue.toUpperCase());
		response.setSuggestionCategory(false);
		response.setSuggestionCategoryUrl(Constants.EMPTY_STRING);
		response.setSuggestionCategoryUrlMethod(Constants.EMPTY_STRING);
		response.setSuggestionCategoryType(Constants.VALUE);
		response.setSuggestionLabel(searchValue);
		
		return response;
	}

	private StringBuffer getHighlightingString(String searchKey, String searchValue) {
		StringBuffer returnSearchValue = new StringBuffer(searchValue);
		String searchValueLowerCase = searchValue.toLowerCase();
		String searchKeyLowerCase = searchKey.toLowerCase();
		int bOpenLength = Constants.HTML_B_OPEN_TAG.length();
		int bCloseLength = Constants.HTML_B_CLOSE_TAG.length();
		int searchKeyBoldTagLength = searchKey.length() + bOpenLength;
		int extraCount=0;

		int skipCharacters = -1;
		for (int i = -1; (i = searchValueLowerCase.indexOf(searchKeyLowerCase, i+ skipCharacters)) != -1; ) {
			extraCount+=bOpenLength+bCloseLength;
			skipCharacters = searchKey.length();
		}
		return returnSearchValue;
	}





}
