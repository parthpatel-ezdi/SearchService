package com.ezdi.cac.autosuggest.util;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrUtility {

	@Value("${solr.server.url}")
	private String solrUrl;

	@Value("${solr.search.core}")
	private String solrSearchCore;



	@Value("${solr.search.handler}")
	private String solrSearchHandler;

	@Bean
	public SolrClient solrClient() {
		SolrClient solrClient=new HttpSolrClient(solrUrl + "/" + solrSearchCore);
		return solrClient;
	}

	public String getSolrSearchHandler() {
		return solrSearchHandler;
	}


}
