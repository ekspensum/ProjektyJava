package integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testGetProcessors() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/ProcessorsXml");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 51; i < 60; i++)
			actual += (char) ctx[i];

		assertEquals("Processor", actual);
	}

}
