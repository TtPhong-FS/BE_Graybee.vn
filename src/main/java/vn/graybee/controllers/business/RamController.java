package vn.graybee.controllers.business;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.models.business.RamDetail;
import vn.graybee.requests.ram.RamDetailCreateRequest;
import vn.graybee.services.business.RamDetailService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/rams")
public class RamController {

    private final RamDetailService ramDetailService;

    public RamController(RamDetailService ramDetailService) {
        this.ramDetailService = ramDetailService;
    }

    @GetMapping("")
    public ResponseEntity<Optional<RamDetail>> findById(@RequestParam("id") long id) {
        return ResponseEntity.ok(ramDetailService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> insertRam(@RequestBody @Valid RamDetailCreateRequest request) {
        ramDetailService.createRamDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ram created");
    }

}
