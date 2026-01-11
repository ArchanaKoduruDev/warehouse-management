package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusIntegrationTest
public class WarehouseEndpointIT {

  @Test
  public void testSimpleListWarehouses() {

    // Uncomment the following lines to test the WarehouseResourceImpl implementation

    final String path = "warehouse";

    // List all, should have all 3 products the database has initially:
     given()
         .when()
         .get(path)
         .then()
         .statusCode(200)
         .body(
             containsString("MWH.001"),
             containsString("MWH.012"),
             containsString("MWH.023"),
             containsString("ZWOLLE-001"),
             containsString("AMSTERDAM-001"),
             containsString("TILBURG-001"));

    // // Archive the ZWOLLE-001:
     given().when().delete(path + "/1").then().statusCode(204);

     // List all, ZWOLLE-001 should be missing now:
     given()
         .when()
         .get(path)
         .then()
         .statusCode(200)
         .body(
             not(containsString("ZWOLLE-001")),
             containsString("AMSTERDAM-001"),
             containsString("TILBURG-001"));
  }

    @Test
    public void getUnknownWarehouseShouldReturn400WithErrorPayload() {
        given()
                .when().get("warehouse/UNKNOWN")
                .then()
                .statusCode(400)
                .body("error", containsString("not found"))
                .body("exceptionType", equalTo("WarehouseNotFoundException"));
    }
}
