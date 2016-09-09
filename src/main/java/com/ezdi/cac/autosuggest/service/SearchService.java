package com.ezdi.cac.autosuggest.service;

import org.apache.solr.client.solrj.response.QueryResponse;


public interface SearchService {

    public QueryResponse getSearchSuggestion(String searchKey, String filterCategory, String flQuery, int start, int max);

   }
