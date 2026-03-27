package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class UmidadeResourceTest {

    @Test
    void deveRetornarMensagemQuandoNaoExistiremLeituras() {
        given()
                .when().get("/umidade")
                .then()
                .statusCode(200)
                .body("mensagem", equalTo("Sem leituras registradas"));
    }

    @Test
    void deveRegistrarLeituraDeUmidade() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"umidade\":\"42%\"}")
                .when().post("/umidade")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("leitura", equalTo("42%"));
    }

    @Test
    void deveRetornarErroQuandoUmidadeNaoForInformada() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when().post("/umidade")
                .then()
                .statusCode(400)
                .body("erro", equalTo("Campo 'umidade' é obrigatório"));
    }
}
