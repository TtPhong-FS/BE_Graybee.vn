package vn.graybee.controllers.business;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.response.manufacturer.ManufacturerResponse;
import vn.graybee.services.business.ManufacturerService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ManufacturerResponse>>> getAllManufacturers() {
        BasicMessageResponse<List<ManufacturerResponse>> manufacturers = manufacturerService.getAllManufacturer();
        return ResponseEntity.ok(manufacturers);
    }


    @PostMapping
    public ResponseEntity<BasicMessageResponse<ManufacturerResponse>> createManufacturer(@RequestBody @Valid ManufacturerCreateRequest request) {
        BasicMessageResponse<ManufacturerResponse> manufacturerResponse = manufacturerService.insertManufacturer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                manufacturerResponse);
    }
//
//    @DeleteMapping("/delete-manufacturer")
//    public ResponseEntity<MessageResponse> deleteManufacturerById(@RequestParam("id") long id) {
//        manufacturerService.deleteManufacturerById(id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(
//                new MessageResponse("200", "Delete Manufacturer successfully", null)
//        );
//    }
//
//    @PutMapping("/update-manufacturer")
//    public ResponseEntity<MessageResponse> updateStatusDeleteRecord(@RequestParam("id") long id) {
//        manufacturerService.updateStatusDeleteRecord(id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("200", "Update status delete for Manufacturer with id = " + id + " successful", null));
//    }

}
