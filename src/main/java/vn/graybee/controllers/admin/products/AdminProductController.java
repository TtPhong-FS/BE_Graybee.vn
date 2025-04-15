package vn.graybee.controllers.admin.products;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.enums.ProductStatus;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductRelationUpdateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.admin.products.ProductDto;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;
import vn.graybee.serviceImps.products.ProductDocumentService;
import vn.graybee.services.products.ProductService_admin;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("${api.products}")
public class AdminProductController {


    private final ProductDocumentService productDocumentService;

    private final ProductService_admin productServiceADMIN;

    public AdminProductController(ProductDocumentService productDocumentService, ProductService_admin productServiceADMIN) {
        this.productDocumentService = productDocumentService;
        this.productServiceADMIN = productServiceADMIN;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductResponse>> create(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.ok(productServiceADMIN.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> update(@RequestParam("id") long id, @RequestBody @Valid ProductUpdateRequest request) {
        return ResponseEntity.ok(productServiceADMIN.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Long>> delete(@RequestParam("id") long id) {
        return ResponseEntity.ok(productServiceADMIN.delete(id));
    }

    @DeleteMapping("/subcategories/delete")
    public ResponseEntity<BasicMessageResponse<ProductSubcategoryIDResponse>> deleteRelationByProductIdAndSubcategoryId(@RequestParam("productId") long productId, @RequestParam("subcategoryId") int subcategoryId) {
        return ResponseEntity.ok(productServiceADMIN.deleteRelationByProductIdAndSubcategoryId(productId, subcategoryId));
    }

    @DeleteMapping("/tags/delete")
    public ResponseEntity<BasicMessageResponse<ProductIdAndTagIdResponse>> deleteRelationByProductIdAndTagId(@RequestParam("productId") long productId, @RequestParam("tagId") int tagId) {
        return ResponseEntity.ok(productServiceADMIN.deleteRelationByProductIdAndTagId(productId, tagId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductDto>> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(productServiceADMIN.getById(id));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductResponse>>> fetchAll() {
        return ResponseEntity.ok(productServiceADMIN.fetchAll());
    }

    @GetMapping("/subcategories&tags")
    public ResponseEntity<BasicMessageResponse<List<ProductSubcategoryAndTagResponse>>> fetchProductsWithSubcategoriesAndTags() {
        return ResponseEntity.ok(productServiceADMIN.fetchAllWithSubcategoriesAndTags());
    }

    @PutMapping("/update/relation")
    public ResponseEntity<BasicMessageResponse<ProductSubcategoryAndTagResponse>> updateSubcategoriesAndTags(@RequestParam("id") long id, @RequestBody ProductRelationUpdateRequest request) {
        return ResponseEntity.ok(productServiceADMIN.updateSubcategoriesAndTagIds(id, request));
    }

    @PutMapping("/update/status")
    public ResponseEntity<BasicMessageResponse<ProductStatusResponse>> updateStatusById(@RequestParam("id") long id, @RequestParam("status") ProductStatus status) {
        return ResponseEntity.ok(productServiceADMIN.updateStatus(id, status));
    }

    @GetMapping("/load-elastic")
    public ResponseEntity<BasicMessageResponse<?>> loadProductsIndexIntoElastic() throws IOException {
        productDocumentService.loadProductsIndexIntoElastic();
        return ResponseEntity.ok(new BasicMessageResponse<>(200, ConstantGeneral.success_load_product_into_elastic, null));
    }

}
