package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.MonitorDetailProjection;
import vn.graybee.serviceImps.collections.MonitorServiceImp;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/monitor")
public class AdminMonitorController {

    private final MonitorServiceImp monitorServiceImp;

    public AdminMonitorController(MonitorServiceImp monitorServiceImp) {
        this.monitorServiceImp = monitorServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<MonitorDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(monitorServiceImp.fetchAll());
    }

}