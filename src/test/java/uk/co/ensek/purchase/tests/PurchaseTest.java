package uk.co.ensek.purchase.tests;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import uk.co.ensek.purchase.endPoints.Constants;
import uk.co.ensek.purchase.model.FuelQuantity;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class PurchaseTest
{
    /*
    This api tests the buying quantity of fuel functionality
    */
    @Test
    public void testBuyQuantityOfFuel()
    {
        int id = 2;
        int quantity = 1;
        given()
                .baseUri(Constants.BASE_URI)
                .pathParam("id", id)
                .pathParam("quantity", quantity)
                .contentType(ContentType.JSON)
                .when()
                .put(Constants.BUY_FUEL_END_POINT)
                .then()
                .statusCode(200);
    }

    /*
    This api tests the negative scenario
    testing buy quantity of fuel with invalid data
    */
    @Test
    public void testBuyQuantityOfFuelWithInvalidId()
    {
        int id = 0;
        int quantity = 1;
        given()
                .baseUri(Constants.BASE_URI)
                .pathParam("id", id)
                .pathParam("quantity", quantity)
                .contentType(ContentType.JSON)
                .when()
                .put(Constants.BUY_FUEL_END_POINT)
                .then()
                .statusCode(400);
    }

    /*
     This api tests whether the orderId created in the orders list or not
    */
    @Test
    public void testAnOrderInFuelOrders()
    {
        int id = 2;
        int quantity = 1;
        String patternString = "Your order id is ";
        String response = given()
                .baseUri(Constants.BASE_URI)
                .pathParam("id", id)
                .pathParam("quantity", quantity)
                .contentType(ContentType.JSON)
                .when()
                .put(Constants.BUY_FUEL_END_POINT)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .toString();
        Assert.assertTrue("orderId not present in the response", response.contains(String.valueOf(quantity)));
        String orderId = response.substring(response.indexOf(patternString) + patternString.length(),
                                            response.lastIndexOf("."));

        String orderList = given()
                .baseUri(Constants.BASE_URI)
                .when()
                .get(Constants.GET_ORDERS_END_POINT)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .toString();
        Assert.assertTrue("orderId not present in the response", orderList.contains(orderId));
    }

    /*
    This api tests confirms how many orders were created before the current date
    */
    @Test
    public void testOrdersBeforeCurrentDate()
    {
        FuelQuantity[] orders = given()
                .baseUri(Constants.BASE_URI)
                .when()
                .get(Constants.GET_ORDERS_END_POINT)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .as(FuelQuantity[].class);
        List<FuelQuantity> fuelList = Arrays.asList(orders);
        List<FuelQuantity> filteredList = fuelList.stream().filter(fuel -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z");
            ZonedDateTime fuelOrderTime = ZonedDateTime
                    .parse(fuel.getTime(), formatter);
            ZonedDateTime todayDate = ZonedDateTime.now().minusDays(1);
            return fuelOrderTime.isBefore(todayDate);
        }).collect(Collectors.toList());
        Assert.assertTrue(filteredList.size() >= 5);
    }

    /*
    This api tests the login functionality
    */
    @Test
    public void testLoginFunctionality()
    {
        given()
                .baseUri(Constants.BASE_URI)
                .contentType(ContentType.JSON)
                .body("{\n"
                              + "  \"username\": \"test\",\n"
                              + "  \"password\": \"testing\"\n"
                              + "}")
                .when()
                .post(Constants.LOGIN_END_POINT)
                .then()
                .statusCode(200);
    }
}
