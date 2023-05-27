package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class APIexamples {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODUwNTg1OTcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY4NTEwMTc5NywidXNlcklkIjoiNTI2OCJ9.Tpv7NF31n_m1zu64kbfohfnAD-1qDQsX4sNYU6S7rag";

    @Test
    public void CreateAnEmployee(){
        RequestSpecification preparedRequest = given().
                header("Content-Type","application/json").
                header("Authorization",token).body("{\n" +
                        "    \"emp_firstname\": \"nickou\",\n" +
                        "    \"emp_lastname\": \"boujevic\",\n" +
                        "    \"emp_middle_name\": \"f\",\n" +
                        "    \"emp_gender\": \"M\",\n" +
                        "    \"emp_birthday\": \"2017-05-20\",\n" +
                        "    \"emp_status\": \"Confirmed\",\n" +
                        "    \"emp_job_title\": \"Napper\"\n" +
                        "}");

        // act as a send key
        // prepared request --> attached all the data for the request
        Response response = preparedRequest.when().post("/createEmployee.php");
        response.prettyPrint();

        // verification of status
        response.then().assertThat().statusCode(201);

        // verify the body or the data
        // in order to verify that the value of the key "Message" is "Employee created"
        // actualvalue = coming from the response
        String expectedValue = "Employee created";

        // get the actual value out of the response --> this is the only task that is challenging
        String actualValue=response.jsonPath().getString("Message");
        System.out.println(actualValue);

        // verify
        Assert.assertEquals(actualValue,expectedValue);

        // response.then().assertThat().body("Message", equalTo("Employee Created"));


        // verify that emp_job_title is Cloud Architect
        // actual = coming from the response
        String expectedJobTitle = "Napper";

        String actualJobTitle=response.jsonPath().getString("Employee.emp_job_title");

        Assert.assertEquals(expectedJobTitle,actualJobTitle);

        // response.then().assertThat().body("Employee.emp_job_title",equalTo("Napper"));



    }

}
