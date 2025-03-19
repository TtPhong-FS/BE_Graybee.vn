package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;
import vn.graybee.serviceImps.collections.PcServiceImp;
import vn.graybee.services.products.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/collections")
public class PublicPcController {

    private final PcServiceImp pcServiceImp;

    private final ProductService productId;

    private final ProductService productService;

    public PublicPcController(PcServiceImp pcServiceImp, ProductService productId, ProductService productService) {
        this.pcServiceImp = pcServiceImp;
        this.productId = productId;
        this.productService = productService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<BasicMessageResponse<List<PcSummaryResponse>>> findByCategoryName_V1(@PathVariable("name") String name) {
        return pcServiceImp.findByCategoryName_PUBLIC(name);
    }


}
