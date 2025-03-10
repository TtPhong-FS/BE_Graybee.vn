package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.KeyboardDetailProjection;
import vn.graybee.serviceImps.collections.KeyboardServiceImp;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/keyboard")
public class AdminKeyboardController {

    private final KeyboardServiceImp keyboardServiceImp;

    public AdminKeyboardController(KeyboardServiceImp keyboardServiceImp) {
        this.keyboardServiceImp = keyboardServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<KeyboardDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(keyboardServiceImp.fetchAll());
    }

}