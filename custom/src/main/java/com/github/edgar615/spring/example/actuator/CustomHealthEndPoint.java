package com.github.edgar615.spring.example.actuator;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom-health")
public class CustomHealthEndPoint {

  private final CustomHealth health = new CustomHealth();

  @PostConstruct
  public void init() {
    Map<String, Object> details = new LinkedHashMap<>();
    details.put("CustomHealthStatus", "Everything looks good");
    health.setHealthDetails(details);
  }

  // GET /actuator/custom-health
  @ReadOperation
  public CustomHealth health() {
    return health;
  }

  // GET /actuator/custom-health/{arg0}
  // 注意 @Selector的参数要设为arg0，还没有找到原因
  @ReadOperation
  public Object customEndPointByName(@Selector String arg0) {
    return health.getHealthDetails().get(arg0);
  }

  // POST
  @WriteOperation
  public void writeOperation(@Selector String arg0, String value) {
    health.getHealthDetails().put(arg0, value);
  }

  // 删除 DELETE actuator/custom-health/{arg0}
  @DeleteOperation
  public void deleteOperation(@Selector String arg0) {
    health.getHealthDetails().remove(arg0);
  }
}
