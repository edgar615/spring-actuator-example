package com.github.edgar615.spring.example.actuator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomHealthTest {

  static {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 9000;
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//    RestAssured.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset)  -> objectMapper));
  }

  @Test
  public void testCustomHealth() {
    Map<String, Object> map = RestAssured.get("/actuator/custom-health")
        .as(Map.class);
    System.out.println(map);
    Assert.assertEquals(1, map.size());

    Assert.assertTrue(map.containsKey("CustomHealthStatus"));

    Object customHealthStatus = RestAssured.get("/actuator/custom-health/CustomHealthStatus")
        .then().extract().body().asString();
    System.out.println(customHealthStatus);
    Assert.assertEquals("Everything looks good", customHealthStatus);

    Object foo = RestAssured.get("/actuator/custom-health/foo")
        .then().extract().body().asString();
    Assert.assertTrue(StringUtils.isEmpty(foo));

    Map<String, Object> body = new HashMap<>();
    body.put("value", "bar");
    Object write = RestAssured.given().contentType(ContentType.JSON)
        .body(body)
        .post("/actuator/custom-health/foo")
        .then().extract().body().asString();
    System.out.println(write);

    foo = RestAssured.get("/actuator/custom-health/foo")
        .then().extract().body().asString();
    System.out.println(foo);
    Assert.assertEquals("bar", foo);

    Object delete = RestAssured.given()
        .delete("/actuator/custom-health/foo")
        .then().extract().body().asString();

    foo = RestAssured.get("/actuator/custom-health/foo")
        .then().extract().body().asString();
    Assert.assertTrue(StringUtils.isEmpty(foo));
  }
}
