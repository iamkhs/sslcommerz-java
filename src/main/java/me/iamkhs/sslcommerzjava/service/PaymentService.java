package me.iamkhs.sslcommerzjava.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.iamkhs.sslcommerzjava.config.SslCommerzConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${sslcommerz.successUrl}")
    private String successUrl;

    @Value("${sslcommerz.cancelUrl}")
    private String cancelUrl;

    @Value("${sslcommerz.fail_url}")
    private String failUrl;


    private final SslCommerzConfig config;
    private final RestClient restClient = RestClient.create();

    public String initiatePayment(){
        var data = new LinkedMultiValueMap<String, String>();
        data.add("store_id", config.getStoreId());
        data.add("store_passwd", config.getStorePassword());
        data.add("total_amount", "100");
        data.add("currency", "BDT");
        data.add("tran_id", UUID.randomUUID().toString());
        data.add("cus_add1", "dhaka");
        data.add("cus_city", "dhaka");
        data.add("cus_country", "bd");
        data.add("cus_phone", "34324324234");
        data.add("shipping_method", "NO");
        data.add("success_url", successUrl);
        data.add("fail_url", failUrl);
        data.add("cancel_url", cancelUrl);
        data.add("cus_name","Test");
        data.add("cus_email","test@example.com");
        data.add("product_name","TestProd");
        data.add("product_category","general");
        data.add("product_profile","general");
        data.add("multi_card_name", "visa,master");

        Map<?, ?> result = restClient.post()
                .uri(config.baseApiUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(data)
                .retrieve()
                .body(Map.class);

        if (result != null && result.containsKey("GatewayPageURL")) {
            log.info("ssl commerz response: {}", result);
            return (String) result.get("GatewayPageURL");
        }
        throw new RuntimeException("❌ Failed to initiate payment");
    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> validatePayment(String valId) {
        String url = String.format(
                "%s?val_id=%s&store_id=%s&store_passwd=%s&format=json",
                config.validationApi, valId, config.getStoreId(), config.getStorePassword()
        );

        ResponseEntity<Map> response = new RestTemplate().getForEntity(url, Map.class);
        log.info("ssl commerz validation response: {}", response);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        throw new RuntimeException("Failed to validate payment");
    }

}
