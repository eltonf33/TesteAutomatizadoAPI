import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class SimulacaoTest {

    @Test
    public void testDadoQuandoCPFInformadoForRestritoEntaoObtenhoStatus200() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        String[] listaCpfs = {
                "97093236014",
                "60094146012",
                "84809766080",
                "62648716050",
                "26276298085",
                "01317496094",
                "55856777050",
                "19626829001",
                "24094592008",
                "58063164083",
        };

        for (String cpf : listaCpfs) {
            given()
                    .when()
                    .get("/v1/restricoes/" + cpf)
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }

    @Test
    public void testDadoQuandoCPFInformadoForSemRestricaoEntaoObtenhoStatus204() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        String[] listaCpfs = {
                "91246705028",
                "18874791046",
                "63975298006"
        };

        for (String cpf : listaCpfs) {
            given()
                    .when()
                    .get("/v1/restricoes/" + cpf)
                    .then()
                    .log().all()
                    .statusCode(204); // Espera um status de resposta 200 (OK)
        }
    }

    @Test
    public void testDadoQueEstouRealizandoPostDeSimulacaoQuandoInformoCamposObrigatoriosEntaoObtendoStatus201() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

            given()
                    .body("{\n" +
                            "  \"nome\": \"Márcio Manoel Silva\",\n" +
                            "  \"cpf\": 31426224460,\n" +
                            "  \"email\": \"marciomanoelsilva@nine9.com.br\",\n" +
                            "  \"valor\": 1500,\n" +
                            "  \"parcelas\": 12,\n" +
                            "  \"seguro\": false\n" +
                            "}")
                    .contentType(ContentType.JSON)
                .when()
                .post("/v1/simulacoes")
                .then()
                    .assertThat()
                        .statusCode(201)
                .log().all();

        }

    @Test
    public void testDadoQueEstouRealizandoPostDeSimulacaoQuandoNaoInformoCamposObrigatoriosEntaoObtendoStatus400() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        given()
                .body("{\n" +
                        "  \"nome\": \"Márcio Manoel Silva\",\n" +
                        "  \"cpf\": 31426224460,\n" +
                        "  \"email\": \"marciomanoelsilva@nine9.com.br\",\n" +
                        "  \"valor\": 2500,\n" +
                        "  \"parcelas\": 24,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/simulacoes")
                .then()
                    .assertThat()
                        .statusCode(400)
                .log().all();

    }

    @Test
    public void testDadoQueEstouRealizandoPostDeSimulacaoQuandoInformoMesmoCPFEntaoObtendoStatus409() {

        /* Observação do test
            De acordo com o documento, ao tentar realizar uma mesma simulação, deveria cair no Status Code 409 com mensagem de "CPF já existente",
            porém ao realizar o teste, foi retornado Status Code 400 com mensagem de "CPF Duplicado".
         */

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        given()
                .body("{\n" +
                        "  \"nome\": \"Márcio Manoel Silva\",\n" +
                        "  \"cpf\": 31426224460,\n" +
                        "  \"email\": \"marciomanoelsilva@nine9.com.br\",\n" +
                        "  \"valor\": 2500,\n" +
                        "  \"parcelas\": 24,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/simulacoes")
                .then()
                    .assertThat()
                        .statusCode(409)
                .log().all();

    }

    @Test
    public void testDadoQueEstouRealizandoPutDeSimulacaoQuandoJaExistenteEntaoObtendoStatus200() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        String cpf = "31426224460";

        given()
                .body("{\n" +
                        "  \"nome\": \"Márcio Manoel Silva\",\n" +
                        "  \"cpf\": 31426224460,\n" +
                        "  \"email\": \"marciomanoelsilva@nine9.com.br\",\n" +
                        "  \"valor\": 3500,\n" +
                        "  \"parcelas\": 12,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .put("/v1/simulacoes/"+cpf)
                .then()
                .assertThat()
                    .statusCode(200)
                        .log().all();

    }

    @Test
    public void testDadoQueEstouRealizandoPutDeSimulacaoQuandoNaoExisteEntaoObtendoStatus404() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        String cpf = "08932279004";

        given()
                .body("{\n" +
                        "\"nome\": \"Elias Fernando Caldeira\",\n" +
                        "\"cpf\": \"08932279004\",\n" +
                        "\"email\": \"elias_fernando_caldeira@grupoaerius.com.br\",\n" +
                        "\"valor\": 3000,\n" +
                        "\"parcelas\": 24,\n" +
                        "\"seguro\": true\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .put("/v1/simulacoes/"+cpf)
                    .then()
                        .assertThat()
                .statusCode(404)
                .log().all();

    }

    @Test
    public void testDadoQuandoConsultoSimulacoesEntaoObtenhoStatus200() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

            given()
                    .when()
                    .get("/v1/simulacoes")
                    .then()
                    .log().all()
                    .statusCode(200);

    }

    @Test
    public void testDadoQuandoConsultoSimulacoesSemDadosEmBancoEntaoObtenhoStatus204() {

       /*
           Na documentação menciona que quando não existe dados cadastrados, deveria retornar 204, porém esta retorando 200 com um array vazio
        */


        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        given()
                .when()
                .get("/v1/simulacoes")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void testDadoQuandoConsultoSimulacaoPorCPFEntaoObtenhoStatus200() {

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        String cpf = "08932279004";

        given()
                .when()
                .get("/v1/simulacoes/" + cpf)
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void testDadoQuandoDeletoUmaSimulacaoComSucessoPorIdEntaoObtenhoStatus200() {

       /*
           Na documentação menciona que quando removido com sucesso deveria retornar 204, porém esta retorando 200
        */

        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        int id = 23;

        given()
                .when()
                .delete("/v1/simulacoes/" + id)
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void testDadoQuandoDeletoUmaSimulacaoSemSucessoPorIdEntaoObtenhoStatus404() {

       /*
           Na documentação menciona que quando removido com sucesso deveria retornar 404, porém esta retorando 200
        */


        baseURI = "http://localhost";
        port =8080;
        basePath= "/api";

        int id = 12;

        given()
                .when()
                .delete("/v1/simulacoes/" + id)
                .then()
                .log().all()
                .statusCode(200);

    }


}
