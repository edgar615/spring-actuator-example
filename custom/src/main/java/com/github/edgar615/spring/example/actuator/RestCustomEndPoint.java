package com.github.edgar615.spring.example.actuator;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RestControllerEndpoint(id = "rest-end-point")
public class RestCustomEndPoint {

  // GET /actuator/rest-end-point/custom
  @GetMapping("/custom")
  public @ResponseBody
  CustomHealth customEndPoint() {
    CustomHealth health = new CustomHealth();
    Map<String, Object> details = new LinkedHashMap<>();
    details.put("CustomHealthStatus", "Everything looks good");
    health.setHealthDetails(details);
    return health;
  }
}
