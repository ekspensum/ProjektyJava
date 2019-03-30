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

	@Test
	public final void testGetHardDisks() throws IllegalStateException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/HardDisksXml");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 38; i < 100; i++)
			actual += (char) ctx[i];
		
		assertEquals("<HardDisks><HardDisk id=\"0\"><idPproduct>46</idPproduct><produc", actual);
	}

	@Test
	public final void testGetProductById() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/ProductXml/46");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 38; i < 100; i++)
			actual += (char) ctx[i];

		assertEquals("<Product><productName>Dysk twardy</productName><productDescrip", actual);
	}

	@Test
	public final void testGetProductsById() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/ProductsXml/46/47");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 38; i < 100; i++)
			actual += (char) ctx[i];
		
		assertEquals("<Products><Product id=\"0\"><idPproduct>46</idPproduct><productN", actual);
	}

	@Test
	public final void testGetMainBoardXmls() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/MainBoardsXml");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 55; i < 100; i++)
			actual += (char) ctx[i];
		
		assertEquals("<collection><mainBoardXml><base64Image></base", actual);
	}

	@Test
	public final void testGetRamMemoryXml() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/RamMemoryXml");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 55; i < 100; i++)
			actual += (char) ctx[i];
		
		assertEquals("<collection><ramMemoryXml><base64Image>/9j/4A", actual);
	}

	@Test
	public final void testGetAllProcessorsJson() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/ProcessorsJson");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 6; i++)
			actual += (char) ctx[i];
		
		assertEquals("[[45,\"", actual);
	}

	@Test
	public final void testGetAllMainBoardJson() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/MainBoardsJson");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 6; i++)
			actual += (char) ctx[i];
		
		assertEquals("[[47,\"", actual);
	}

	@Test
	public final void testGetAllRamMemoryJson() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/RamMemoryJson");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 6; i++)
			actual += (char) ctx[i];
		
		assertEquals("[[48,\"", actual);
	}

	@Test
	public final void testGetAllHardDisksJson() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/HardDisksJson");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 6; i++)
			actual += (char) ctx[i];
		
		assertEquals("[[46,\"", actual);
	}

	@Test
	public final void testGetAllCategory() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/AllCategoryJson");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 6; i++)
			actual += (char) ctx[i];
		
		assertEquals("[[23,\"", actual);
	}

	@Test
	public final void testGetProductJsonById() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/ProductByIdJson/46");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 6; i++)
			actual += (char) ctx[i];
		
		assertEquals("{\"id\":", actual);
	}

	@Test
	public final void testGetProductJsonByName() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/ProductsByNameJson/płyt");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 27; i++)
			actual += (char) ctx[i];
		
		assertEquals("[{\"name\":\"Pￅﾂyta gￅﾂￃﾳwna1\"", actual);
	}

	@Test
	public final void testGetProductsJsonByIdRange() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/ShopAppWebService/rest/ShopResource/ProductsJson/46/47");
		String actual = "";
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		byte[] ctx = new byte[100];
		response.getEntity().getContent().read(ctx);
		for (int i = 0; i < 6; i++)
			actual += (char) ctx[i];
		
		assertEquals("{\"Dysk", actual);
	}

}
