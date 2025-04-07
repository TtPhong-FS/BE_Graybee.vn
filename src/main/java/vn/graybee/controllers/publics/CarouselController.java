package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.services.products.ProductService_public;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/carousel/product")
public class CarouselController {

    private final ProductService_public productServicePublic;

    public CarouselController(ProductService_public productServicePublic) {
        this.productServicePublic = productServicePublic;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> getByCategoryLaptop(@RequestParam("category") String category) {
        return ResponseEntity.ok(productServicePublic.findByCategoryName(category));
    }

}
