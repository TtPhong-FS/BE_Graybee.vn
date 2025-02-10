package vn.graybee.controllers.business;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.services.business.ManufacturerService;

@RestController
@RequestMapping("/api/v1/admin")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

//    @GetMapping("/manufacturers")
//    public ResponseEntity<MessageResponse<List<ManufacturerResponse>>> createManufacturer() {
//        List<ManufacturerResponse> manufacturers = manufacturerService.getAllManufacturer();
//        return ResponseEntity.status(HttpStatus.CREATED).body(
//                new MessageResponse<>(200, "List Manufacturers", manufacturers, , "")
//        );
//    }
//
//
//    @PostMapping("/add-manufacturer")
//    public ResponseEntity<MessageResponse> createManufacturer(@RequestBody @Valid ManufacturerCreateRequest request) {
//        Manufacturer savedmanufacturer = manufacturerService.insertManufacturer(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(
//                new MessageResponse("201", "Create Manufacturer successfully", savedmanufacturer)
//        );
//    }
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
