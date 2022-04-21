package com.pepcus.employe.health.healthService;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component("getHealthService")
@Service
public class HealthService {
    public String checkHealth() {
        String result;
        RestTemplate restTemplate = new RestTemplate();
        try {
            result = (restTemplate.exchange("http://localhost:8080/actuator/health",
                                            HttpMethod.GET, null, String.class).getBody());
        } catch (Exception e){
            result = "Application is not working";
        }
        return result;
    }
}
