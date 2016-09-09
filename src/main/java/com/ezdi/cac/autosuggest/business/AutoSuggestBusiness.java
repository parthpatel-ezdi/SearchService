package com.ezdi.cac.autosuggest.business;


import java.util.List;

import com.ezdi.cac.autosuggest.dto.SearchSuggestionRequest;
import com.ezdi.cac.autosuggest.dto.SearchSuggestionResponse;


public interface AutoSuggestBusiness {

    public List<SearchSuggestionResponse> getSearchSuggestion(SearchSuggestionRequest searchRequest);

  
    
}
