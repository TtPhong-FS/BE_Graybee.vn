package vn.graybee.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.models.Ram;
import vn.graybee.requests.rams.RamCreateRequest;
import vn.graybee.services.RamService;

import java.util.Optional;

@RestController
@RequestMapping("${api.rams}")
public class RamController {

    private final RamService ramService;

    public RamController(RamService ramService) {
        this.ramService = ramService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Ram>> findById(@PathVariable("id") long id) {

        return ResponseEntity.ok(ramService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> insertRam(@RequestBody @Valid RamCreateRequest request) {
        ramService.insertRam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ram created");
    }

}
