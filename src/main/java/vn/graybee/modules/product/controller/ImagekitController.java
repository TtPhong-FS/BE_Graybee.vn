package vn.graybee.modules.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.response.ImageKitAuth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("${api.admin.image-kit}")
public class ImagekitController {

    private final String privateKey = "private_470TGMpAKSBKA9F/d/Onck5xPVE=";


    @GetMapping("/auth")
    public ResponseEntity<BasicMessageResponse<ImageKitAuth>> getImageKitAuth() throws Exception {
        long expire = Instant.now().getEpochSecond() + 240;
        String token = UUID.randomUUID().toString();

        String signature = hmacSha1Hex(token + expire, privateKey);

        ImageKitAuth res = new ImageKitAuth(token, expire, signature);

        System.out.println(res);

        return ResponseEntity.ok(new BasicMessageResponse<>(200, "Get imagekit auth success", res));
    }

    private String hmacSha1Hex(String data, String key) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            hmac.init(secretKey);
            byte[] hash = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating signature", e);
        }
    }

}
