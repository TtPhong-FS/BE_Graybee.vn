package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.LaptopDetailProjection;
import vn.graybee.serviceImps.collections.LaptopServiceImp;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/laptop")
public class AdminLaptopController {

    private final LaptopServiceImp laptopServiceImp;

    public AdminLaptopController(LaptopServiceImp laptopServiceImp) {
        this.laptopServiceImp = laptopServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<LaptopDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(laptopServiceImp.fetchAll());
    }

}