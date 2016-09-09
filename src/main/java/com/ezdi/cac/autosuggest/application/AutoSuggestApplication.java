
package com.ezdi.cac.autosuggest.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages={"com.ezdi.cac"})
public class AutoSuggestApplication {

	public static void main(String[] args) 
	{

		SpringApplication.run(AutoSuggestApplication.class, args);
	}
}
