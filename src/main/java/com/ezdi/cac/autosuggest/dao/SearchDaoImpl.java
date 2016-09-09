package com.ezdi.cac.autosuggest.dao;

import com.ezdi.cac.autosuggest.util.SearchUtil;
import com.ezdi.cac.autosuggest.util.SolrUtility;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SearchUtil searchUtil;

	@Autowired
	private SolrUtility solrUtility;

	@Autowired
	private SolrClient solrClient;


	@Override
	public QueryResponse getSearchSuggestion(String searchKey, String filterCategory, String flQuery, int start, int max){

		SolrQuery highlightQuery = searchUtil.createHighlightQuery(solrUtility.getSolrSearchHandler(), searchKey, flQuery, start, max, filterCategory);
		QueryResponse response = null;
		try {
			response = solrClient.query(highlightQuery);
		}catch (SolrException e) {
			//TODO

		} catch (Exception e) {
			//TODO 
		}
		return response;
	}


}
