package vn.graybee.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/admin/cloudinary")
public class AdminCloudinaryController {

    @GetMapping("/images")
    public ResponseEntity<BasicMessageResponse<?>> getImagesFromCloudinary(@RequestParam(required = false) String cursor) throws IOException {
        String cloudName = "dqntp2s9q";
        String apiKey = "787225953362793";
        String apiSecret = "S9eaLOVmOV70GfeB42eaawXGil4";

        String credentials = "Basic " + Base64.getEncoder()
                .encodeToString((apiKey + ":" + apiSecret).getBytes(StandardCharsets.UTF_8));

        String url = "https://api.cloudinary.com/v1_1/" + cloudName + "/resources/image";
        if (cursor != null && !cursor.isEmpty()) {
            url = "https://api.cloudinary.com/v1_1/" + cloudName + "/resources/image?next_cursor=" + cursor;
        }


        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", credentials);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String jsonResponse = response.toString();

        return ResponseEntity.ok(new BasicMessageResponse<>(200, "Get images from cloudinary success", jsonResponse));
    }

}
