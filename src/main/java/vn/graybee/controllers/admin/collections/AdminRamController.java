package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.RamDetailProjection;
import vn.graybee.serviceImps.collections.RamServiceImp;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/ram")
public class AdminRamController {

    private final RamServiceImp ramServiceImp;

    public AdminRamController(RamServiceImp ramServiceImp) {
        this.ramServiceImp = ramServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<RamDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(ramServiceImp.fetchAll());
    }

}
