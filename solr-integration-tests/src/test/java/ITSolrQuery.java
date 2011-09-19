import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ListIterator;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Before;
import org.junit.Test;

public class ITSolrQuery {

    private CommonsHttpSolrServer solrServer;

    @Before
    public void initialize_solr_client() throws MalformedURLException {
        solrServer = new CommonsHttpSolrServer("http://localhost:8080/solr/");
    }

    @Test
    public void should_index_2_documents_and_query() throws SolrServerException, IOException {
        SolrInputDocument document1 = new SolrInputDocument();
        document1.addField("id", "1");
        document1.addField("name", "junit1");
        solrServer.add(document1);
        SolrInputDocument document2 = new SolrInputDocument();
        document2.addField("id", "2");
        document2.addField("name", "junit2");
        solrServer.add(document2);
        solrServer.commit();

        ModifiableSolrParams query = new SolrQuery("name:junit2");
        QueryResponse queryResponse = solrServer.query(query);

        assertThat(queryResponse).isNotNull();
        ListIterator<SolrDocument> responses = queryResponse.getResults().listIterator();
        assertThat(responses.hasNext()).isTrue();
        assertThat((Map<String, Object>) ((SolrDocument) queryResponse.getResults().listIterator().next())).includes(
                entry("id", "2"), entry("name", "junit2"));
    }
}
