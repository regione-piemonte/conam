package it.csi.conam.conambl.integration.epay.rest.client;

import java.util.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionData;
import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionReference;

public class DebtPositionApi {

    private final String username;
    private final String password;
    private final RestTemplate restTemplate;

    public DebtPositionApi(String username, String password) {
        this.username = username;
        this.password = password;
        this.restTemplate = createConfiguredRestTemplate();
    }

    private RestTemplate createConfiguredRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        return restTemplate;
    }

    public DebtPositionReference createDebtPosition(String url, DebtPositionData requestBody) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", createBasicAuthHeader(username, password));

        String jsonBody = objectMapper.writeValueAsString(requestBody);
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            
            return objectMapper.readValue(responseEntity.getBody(), DebtPositionReference.class);
        
        } catch (HttpClientErrorException | HttpServerErrorException e) {
        	if (e.getStatusCode().value() >= 500 && e.getStatusCode().value() < 600) {
                throw new HttpServerErrorException(e.getStatusCode() , e.getMessage());
            } else {
            	DebtPositionReference errorResponse = objectMapper.readValue(e.getResponseBodyAsString(), DebtPositionReference.class);
                return errorResponse;
            }
        }
    }

    private static String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        return "Basic " + new String(encodedAuth);
    }
}
