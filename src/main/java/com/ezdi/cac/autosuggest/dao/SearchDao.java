package com.ezdi.cac.autosuggest.dao;


import org.apache.solr.client.solrj.response.QueryResponse;

import com.ezdi.cac.autosuggest.enums.ProductApplication;

public interface SearchDao {

    public QueryResponse getSearchSuggestion(String searchKey, String filterCategory, String flQuery, int start, int max);
    
}
