package br.com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.*;

public class UserJasonTest {

	@Test
	public void deveVerificarPrimeiro() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/1")
		.then()
			.assertThat()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age" , greaterThan(18));
	}
	
	@Test
	public void deveVerificarPrimeiroOutraManeira() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/users/1");
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s","id"));
		
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));

	}

	@Test
	public void deveVerificarSegundoNivel() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/2")
		.then()
			.assertThat()
			.statusCode(200)
			.body("id", is(2))
			.body("endereco.rua", containsString("bobos"))
			.body("age" , greaterThan(22));
	}
	
	@Test
	public void VerificarLista() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/3")
		.then()
			.assertThat()
			.statusCode(200)
			.body("id", is(3))
			.body("filhos", hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name", hasItem("Luizinho"))
			.body("filhos.name", hasItems("Zezinho","Luizinho"))
			;
	}
	
	
	@Test
	public void retornarErro() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/4")
		.then()
			.assertThat()
			.statusCode(404)
			.body("error", is("Usuário inexistente"))
			;
	}
	
}
