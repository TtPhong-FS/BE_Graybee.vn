package vn.graybee.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.MessageResponse;
import vn.graybee.requests.LoginRequest;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/sign-in")
    public ResponseEntity<MessageResponse> signIn(@RequestBody LoginRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(new MessageResponse("200", "Login successfully", request));
    }


}
