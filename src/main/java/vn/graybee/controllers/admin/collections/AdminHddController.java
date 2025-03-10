package vn.graybee.controllers.admin.collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.collections.HddDetailProjection;
import vn.graybee.serviceImps.collections.HddServiceImp;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/collections/hdd")
public class AdminHddController {

    private final HddServiceImp hddServiceImp;

    public AdminHddController(HddServiceImp hddServiceImp) {
        this.hddServiceImp = hddServiceImp;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<HddDetailProjection>>> fetchAll() {
        return ResponseEntity.ok(hddServiceImp.fetchAll());
    }

}