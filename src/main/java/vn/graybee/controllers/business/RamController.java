//package vn.graybee.controllers.business;
//
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import vn.graybee.messages.MessageResponse;
//import vn.graybee.models.business.RamDetail;
//import vn.graybee.requests.ram.RamDetailCreateRequest;
//import vn.graybee.services.business.RamService;
//
//@RestController
//@RequestMapping("/api/v1/admin")
//public class RamController {
//
//    private final RamService ramService;
//
//    public RamController(RamService ramService) {
//        this.ramService = ramService;
//    }
//
//    @PostMapping("/add-ram")
//    public ResponseEntity<MessageResponse> createRamDetail(@RequestBody @Valid RamDetailCreateRequest request) {
//        RamDetail savedRam = ramService.createRamDetail(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(
//                "201", "Create ram successfully", savedRam
//        ));
//    }
//
//}
