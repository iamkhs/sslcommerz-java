package me.iamkhs.sslcommerzjava.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SslCommerzConfig {

    @Value("${sslcommerz.store-id}")
    private String storeId;

    @Value("${sslcommerz.store-password}")
    private String storePassword;

    public String baseApiUrl = "https://sandbox.sslcommerz.com/gwprocess/v4/api.php";
    public String validationApi = "https://sandbox.sslcommerz.com/validator/api/validationserverAPI.php";
}
