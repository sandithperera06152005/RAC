package com.heavenscode.rac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.heavenscode.rac.config.ApplicationProperties;
import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MobileAppWebhookService {

    private static final Logger LOG = LoggerFactory.getLogger(MobileAppWebhookService.class);

    public static final String OPEN_JOB = "OpenJob";
    public static final String TEMPORY_INVOICE = "TemporyInvoice";
    public static final String INVOICE = "Invoice";

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public MobileAppWebhookService(
        ApplicationProperties applicationProperties,
        ObjectMapper objectMapper,
        RestTemplateBuilder restTemplateBuilder
    ) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10)).setReadTimeout(Duration.ofSeconds(30)).build();
    }

    public void send(String eventName, Object data) {
        ApplicationProperties.MobileAppWebhook webhook = applicationProperties.getMobileAppWebhook();

        if (StringUtils.isBlank(webhook.getUrl()) || StringUtils.isBlank(webhook.getKey())) {
            LOG.warn("Skipping mobile app webhook because application.mobile-app-webhook.url/key is not configured");
            return;
        }

        try {
            ObjectNode payload = objectMapper.convertValue(data, ObjectNode.class);
            payload.put("EventName", eventName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, webhook.getKey());

            HttpEntity<ObjectNode> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(webhook.getUrl(), request, String.class);
            LOG.info("Mobile app webhook {} sent for id {}. Status: {}", eventName, payload.get("id"), response.getStatusCode());
        } catch (Exception ex) {
            LOG.error("Mobile app webhook {} failed", eventName, ex);
        }
    }
}
