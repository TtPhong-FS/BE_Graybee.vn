package vn.graybee.controllers.business;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.services.business.ManufacturerService;

@RestController
@RequestMapping("/api/v1/admin/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostMapping("")
    public ResponseEntity<?> insertManufacturer(@RequestBody @Valid ManufacturerCreateRequest request) {
        manufacturerService.insertManufacturer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Manufacturer created");
    }

}
