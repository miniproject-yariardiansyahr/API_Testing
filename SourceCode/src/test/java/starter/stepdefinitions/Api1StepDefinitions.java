package starter.stepdefinitions;

import com.beust.ah.A;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.github.javafaker.Faker;
import starter.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class Api1StepDefinitions {

    String baseURL = "https://altashop-api.fly.dev/api";

    User user = new User();

    @Given("{actor} call an api {string} with method {string} with payload below")
    public void callApi(Actor actor, String path, String method, DataTable table) {
        actor.whoCan(CallAnApi.at(baseURL));

        // Create request body json instance
        JSONObject bodyRequest = new JSONObject();


        // Get headers
        List<List<String>> rowsList = table.asLists(String.class);
        List<String> headerList = rowsList.get(0);

        // Get values
        List<Map<String, String>> rowsMap = table.asMaps(String.class, String.class);
        Map<String, String> valueList = rowsMap.get(0);

        // Loop on every values and set value with key from header to request body
        for (int i = 0; i < valueList.size(); i++) {
            Faker faker = new Faker(new Locale("in-ID"));

            String key = headerList.get(i);

            // check if value correspond to random syntax
            switch (valueList.get(key)) {
                case "randomEmail" -> {
                    String randomEmail = faker.internet().emailAddress();
                    bodyRequest.put(key, randomEmail);
                    user.setEmail(randomEmail);
                }
                case "randomPassword" -> {
                    String randomPassword = faker.internet().password();
                    bodyRequest.put(key, randomPassword);
                    user.setPassword(randomPassword);
                }
                case "randomFullname" -> {
                    String randomFullname = faker.name().fullName();
                    bodyRequest.put(key, randomFullname);
                    user.setFullName(randomFullname);
                }
                case "randomProductName" -> bodyRequest.put(key, faker.commerce().productName());
                case "randomProductDescription" -> bodyRequest.put(key, faker.lorem().sentence());
                case "userEmail" -> bodyRequest.put(key, user.getEmail());
                case "userPassword" -> bodyRequest.put(key, user.getPassword());
                default -> bodyRequest.put(key, valueList.get(key));
            }
        }

        switch (method) {
            case "GET":
                actor.attemptsTo(Get.resource(path));
                break;
            case "POST":
                actor.attemptsTo(Post.to(path).with(request -> request.body(bodyRequest).log().all()));
                break;
            case "PUT":
                actor.attemptsTo(Put.to(path).with(request -> request.body(bodyRequest)));
                break;
            case "DELETE":
                actor.attemptsTo(Delete.from(path));
                break;
            default:
                throw new IllegalStateException("Unknown method");
        }
    }

    @Given("{actor} call an api {string} with method {string}")
    public void callApi(Actor actor, String path, String method) {
        actor.whoCan(CallAnApi.at(baseURL));

        switch (method) {
            case "GET":
                actor.attemptsTo(Get.resource(path));
                break;
            case "POST":
                actor.attemptsTo(Post.to(path));
                break;
            case "PUT":
                actor.attemptsTo(Put.to(path));
                break;
            case "DELETE":
                actor.attemptsTo(Delete.from(path));
                break;
            default:
                throw new IllegalStateException("Unknown method");
        }
    }

    @Then("{actor} verify {string} is exist")
    public void userVerifyIsExist(Actor actor, String data) {
        Response response = SerenityRest.lastResponse();
        response.then().body(data, not(emptyString()));
    }

    @Then("{actor} verify response is match with json schema {string}")
    public void userVerifyResponseIsMatchWithJsonSchema(Actor actor, String schema) {
        Response response = SerenityRest.lastResponse();
        response.then().body(matchesJsonSchemaInClasspath(schema));
    }

    @Then("{actor} verify status code is {int}")
    public void userVerifyStatusCodeIs(Actor actor, int statusCode) {
        Response response = SerenityRest.lastResponse();
        response.then().statusCode(statusCode).log().all();
    }

    @And("{actor} get auth token")
    public void userGetAuthToken(Actor actor){
        Response response = SerenityRest.lastResponse();
        user.setToken(response.path("data"));
    }

    @Given("{actor} is create a new product")
    public void userIsCreateANewProduct(Actor actor){
        actor.whoCan(CallAnApi.at(baseURL));

        JSONObject bodyRequest = new JSONObject();

        List<Integer> listCategories = new ArrayList<>();
        listCategories.add(0, 1);
        listCategories.add(1, 2);
        listCategories.add(2, 3);

        bodyRequest.put("name", "teri");
        bodyRequest.put("description", "bahan sambal");
        bodyRequest.put("price", 100);
        bodyRequest.put("categories", listCategories);

        actor.attemptsTo(Post.to("/products").with(request -> request.header("Authorization", user.getToken()).body(bodyRequest).log().all()));
    }
    @Given("{actor} get other users informations")
    public void getOtherUsersInformations(Actor actor) {
        actor.whoCan(CallAnApi.at(baseURL));
        actor.attemptsTo(Get.resource("/auth/info")
                .with(request -> request.header("Authorization", "Bearer "+user.getToken()).log().all()));
    }

    @Given("{actor} get all orders")
    public void userGetAllOrders(Actor actor) {
        actor.whoCan(CallAnApi.at(baseURL));
        actor.attemptsTo(Get.resource("/orders")
                .with(request -> request.header("Authorization", "Bearer "+user.getToken()).log().all()));
    }

    @Given("{actor} get all orders by id")
    public void userGetAllOrdersById(Actor actor) {
        actor.whoCan(CallAnApi.at(baseURL));
        actor.attemptsTo(Get.resource("/orders/9287")
                .with(request -> request.header("Authorization", "Bearer "+user.getToken()).log().all()));
    }

    @Given("{actor} create orders")
    public void userCreateOrders(Actor actor) {
        actor.whoCan(CallAnApi.at(baseURL));

        JSONArray bodyRequest = new JSONArray();
        JSONObject order1 = new JSONObject();
        order1.put("product_id", 11275);
        order1.put("quantity", 32);
        bodyRequest.add(order1);

        actor.attemptsTo(Post.to("/orders")
                .with(request -> request.header("Authorization", "Bearer "+user.getToken())
                        .body(bodyRequest).log().all()));
    }

    @Given("{actor} input a product rating")
    public void userInputAProductRating(Actor actor) {
        actor.whoCan(CallAnApi.at(baseURL));

        JSONObject bodyRequest = new JSONObject();

        bodyRequest.put("count", 4);

        actor.attemptsTo(Post.to("/products/11277/ratings")
                .with(request -> request.header("Authorization", "Bearer "+user.getToken())
                        .body(bodyRequest).log().all()));
    }

    @Given("{actor} create a comment on product")
    public void userCreateACommentOnProduct(Actor actor) {
        actor.whoCan(CallAnApi.at(baseURL));

        JSONObject bodyRequest = new JSONObject();

        bodyRequest.put("content", "test ini testing doang");

        actor.attemptsTo(Post.to("/products/11277/comments")
                .with(request -> request.header("Authorization", "Bearer "+user.getToken())
                        .body(bodyRequest).log().all()));
    }

    @Given("{actor} is create new category products")
    public void userIsCreateNewCategoryProducts(Actor actor) {
        actor.whoCan(CallAnApi.at(baseURL));

        JSONObject bodyRequest = new JSONObject();

        bodyRequest.put("name", "watching FILM");
        bodyRequest.put("description", "watching only");

        actor.attemptsTo(Post.to("/categories")
                .with(request -> request.header("Authorization", "Bearer "+user.getToken())
                        .body(bodyRequest).log().all()));
    }
}