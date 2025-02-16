package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.publics.pc.PcSummaryResponse;
import vn.graybee.serviceImps.PcServiceImp;

@RestController
@RequestMapping("/api/v1/public/collections")
public class PublicPcController {

    private final PcServiceImp pcServiceImp;

    public PublicPcController(PcServiceImp pcServiceImp) {
        this.pcServiceImp = pcServiceImp;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BasicMessageResponse<PcSummaryResponse>> findByProductId_V1(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(pcServiceImp.findByProductId(productId));
    }

}
