package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.MotherboardDetailProjection;
import vn.graybee.serviceImps.collections.MotherboardServiceImp;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/motherboard")
public class AdminMotherboardController {

    private final MotherboardServiceImp motherboardServiceImp;

    public AdminMotherboardController(MotherboardServiceImp motherboardServiceImp) {
        this.motherboardServiceImp = motherboardServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<MotherboardDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(motherboardServiceImp.fetchAll());
    }

}