package br.com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Test;

public class UserJsonLista {
	
	@Test
	public void VerificarLista() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.assertThat()
			.statusCode(200)
			.body("name", hasItems("João da Silva","Ana Júlia","Maria Joaquina"))
			.body("age[1]", is(25))
			.body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho")))
			.body("salary", contains(1234.5677f,2500,null))
			;
	}
	
	@Test
	public void VerificarListaEspecifica() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.assertThat()
			.statusCode(200)
			.body("$", hasSize(3))
			.body("age.findAll{it <= 25}.size()", is(2))
			.body("age.findAll{it <= 25 && it > 20}.size()", is(1))
			.body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
			.body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina"))
			.body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
			.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
			.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))

			;
	}

}
