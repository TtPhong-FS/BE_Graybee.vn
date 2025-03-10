package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.PcDetailProjection;
import vn.graybee.serviceImps.collections.PcServiceImp;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/pc")
public class AdminPcController {

    private final PcServiceImp pcServiceImp;

    public AdminPcController(PcServiceImp pcServiceImp) {
        this.pcServiceImp = pcServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<PcDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(pcServiceImp.fetchAll());
    }

}