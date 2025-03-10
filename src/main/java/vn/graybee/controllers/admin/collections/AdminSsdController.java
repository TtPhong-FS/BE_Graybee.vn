package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.SsdDetailProjection;
import vn.graybee.serviceImps.collections.SsdServiceImp;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/ssd")
public class AdminSsdController {

    private final SsdServiceImp ssdServiceImp;

    public AdminSsdController(SsdServiceImp ssdServiceImp) {
        this.ssdServiceImp = ssdServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<SsdDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(ssdServiceImp.fetchAll());
    }

}
