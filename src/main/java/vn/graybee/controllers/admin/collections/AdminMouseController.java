package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.MouseDetailProjection;
import vn.graybee.serviceImps.collections.MouseServiceImp;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/mouse")
public class AdminMouseController {

    private final MouseServiceImp mouseServiceImp;

    public AdminMouseController(MouseServiceImp mouseServiceImp) {
        this.mouseServiceImp = mouseServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<MouseDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(mouseServiceImp.fetchAll());
    }

}