package ekenya.co.ke.dbapiloadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class Controller {

   @Autowired
    LoadBalancerClient loadBalancerClient;


    @PostMapping(value = "db-api/execute-raw-query",produces = "application/json")
    public String executeRawSql(@RequestBody String body){

        ServiceInstance serviceInstance = loadBalancerClient.choose("db-api-spring");

        System.out.println(serviceInstance.getUri());

        System.out.println("BODY : "+body);
        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/db-api/execute-raw-query";

        return routeRequest(body, baseUrl);
    }
    @PostMapping(value = "db-api/execute-stored-procedure",produces = "application/json")
    public String executeStoredProcedure(@RequestBody String body){

        ServiceInstance serviceInstance = loadBalancerClient.choose("db-api-spring");

        System.out.println(serviceInstance.getUri());

        System.out.println("BODY : "+body);
        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/db-api/execute-stored-procedure";

        return routeRequest(body, baseUrl);
    }

    @PostMapping(value = "db-api/execute-operation",produces = "application/json")
    public String executeServicedQuery(@RequestBody String body){

        ServiceInstance serviceInstance = loadBalancerClient.choose("db-api-spring");

        System.out.println(serviceInstance.getUri());

        System.out.println("BODY : "+body);
        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/db-api/execute-operation";

        return routeRequest(body, baseUrl);
    }

    @PostMapping(value = "db-api/fetch-stored-procedures",produces = "application/json")
    public String executeStoredProcedure(){

        ServiceInstance serviceInstance = loadBalancerClient.choose("db-api-spring");

        System.out.println(serviceInstance.getUri());


        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/db-api/fetch-stored-procedures";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response=null;

        HttpEntity<String> entity = new HttpEntity<>("");
        try{

            response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

//            response=restTemplate.exchange(baseUrl,
//                    HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println(response.getBody());
        return response.getBody();
    }

    private String routeRequest(@RequestBody String body, String baseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response=null;

        HttpEntity<String> entity = new HttpEntity<>(body);
        try{

            response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);
//            response=restTemplate.exchange(baseUrl,
//                    HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println(response.getBody());
        return response.getBody();
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
