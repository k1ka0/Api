package br.com.rest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.*;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;




public class OlaTest {

	@Test
	public void testeOla() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
	    Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
	    Assert.assertTrue(response.getStatusCode() == 200);
	    Assert.assertTrue("O status deveria ser 200", response.getStatusCode() == 200);
	    Assert.assertEquals(200, response.getStatusCode());

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void testeOutrasFormas() {
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
	}
	
	@Test
	public void testeOutrasFormasMelhorada() {
		given().when().get("http://restapi.wcaquino.me/ola").then().statusCode(200);
	}
	
	@Test
	public void testeOutrasFormasMelhorada2() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void conhecerMatchercomHancrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));
		Assert.assertThat(128, Matchers.isA(Integer.class));
	}
	
	@Test
	public void devoValidarBody() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.assertThat()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo!"))
			.body(is(not(nullValue())));
	}
}
