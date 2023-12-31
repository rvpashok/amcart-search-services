package com.amcart.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String elasticsearchDBHost;
    @Value("${elasticsearch.port}")
    private String elasticsearchDBPort;
    @Value("${elasticsearch.username}")
    private String elasticsearchDBUsername;
    @Value("${elasticsearch.password}")
    private String elasticsearchDBPassword;
    @Value("${elasticsearch.protocol}")
    private String elasticsearchDBProtocol;

    public ElasticSearchConfig() {
        System.out.println("\n\n\n\nTTTTTTTTTTTTTTTT\n\n\n\n");
    }

    /*@Bean
    public RestHighLevelClient client() {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticsearchDBUsername, elasticsearchDBPassword));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticsearchDBHost,
                        Integer.parseInt(elasticsearchDBPort), elasticsearchDBProtocol)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
                    }
                }));
        return restHighLevelClient;
    }*/


    /*public ElasticsearchClient elasticsearchClient(){

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200", "localhost:9291")
                .usingSsl()
                .withPathPrefix("ela")
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                .withBasicAuth(elasticsearchDBUsername, elasticsearchDBPassword)
                .withClientConfigurer(ElasticsearchClients.ElasticsearchRestClientConfigurationCallback.from(restClientBuilder -> {
                            // configure the Elasticsearch RestClient

                            return restClientBuilder;
                        }))
                .build();

        ElasticsearchClient elasticsearchClient = new ElasticsearchClient();
    }

    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(elasticsearchClient());
    }*/

}
