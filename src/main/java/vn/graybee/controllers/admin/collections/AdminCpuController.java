package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.CpuDetailProjection;
import vn.graybee.serviceImps.collections.CpuServiceImp;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/cpu")
public class AdminCpuController {

    private final CpuServiceImp cpuServiceImp;

    public AdminCpuController(CpuServiceImp cpuServiceImp) {
        this.cpuServiceImp = cpuServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CpuDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(cpuServiceImp.fetchAll());
    }

}
