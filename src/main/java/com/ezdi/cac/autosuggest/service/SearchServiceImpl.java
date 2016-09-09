package com.ezdi.cac.autosuggest.service;


import com.ezdi.cac.autosuggest.dao.SearchDao;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchDao searchDao;

   

    @Override
    public QueryResponse getSearchSuggestion(String searchKey, String filterCategory, String flQuery, int start, int max){
     
        QueryResponse searchSuggestion = searchDao.getSearchSuggestion(searchKey, filterCategory, flQuery, start, max);
        return searchSuggestion;
    }

  
}
