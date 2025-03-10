package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.VgaDetailProjection;
import vn.graybee.serviceImps.collections.VgaServiceImp;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/vga")
public class AdminVgaController {

    private final VgaServiceImp vgaServiceImp;

    public AdminVgaController(VgaServiceImp vgaServiceImp) {
        this.vgaServiceImp = vgaServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<VgaDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(vgaServiceImp.fetchAll());
    }

}
