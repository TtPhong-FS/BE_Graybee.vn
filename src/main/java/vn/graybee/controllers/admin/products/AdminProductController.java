package vn.graybee.controllers.admin.products;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.enums.ProductStatus;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductRelationUpdateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;
import vn.graybee.response.admin.products.ProductUpdateResponse;
import vn.graybee.services.products.IProductServiceAdmin;
import vn.graybee.services.products.ProductDocumentService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("${api.products}")
public class AdminProductController {


    private final ProductDocumentService productDocumentService;

    private final IProductServiceAdmin iProductServiceAdmin;

    public AdminProductController(ProductDocumentService productDocumentService, IProductServiceAdmin iProductServiceAdmin) {
        this.productDocumentService = productDocumentService;
        this.iProductServiceAdmin = iProductServiceAdmin;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductResponse>> create(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.ok(iProductServiceAdmin.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> update(@RequestParam("id") long id, @RequestBody @Valid ProductUpdateRequest request) {
        return ResponseEntity.ok(iProductServiceAdmin.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Long>> delete(@RequestParam("id") long id) {
        return ResponseEntity.ok(iProductServiceAdmin.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductUpdateResponse>> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(iProductServiceAdmin.getById(id));
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<ProductResponse>>> fetchAll(
            @RequestParam(value = "status", defaultValue = "all") String status,
            @RequestParam(value = "category", defaultValue = "all") String category,
            @RequestParam(value = "manufacturer", defaultValue = "all") String manufacturer,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "order", defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(iProductServiceAdmin.fetchAll(status, category, manufacturer, page, size, sortBy, order));
    }

    @PutMapping("/update/status/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<ProductStatusResponse>> updateStatusById(@PathVariable("id") long id, @PathVariable("status") ProductStatus status) {
        return ResponseEntity.ok(iProductServiceAdmin.updateStatus(id, status));
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> restoreProduct(@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(iProductServiceAdmin.restoreProduct(id, userPrincipal));
    }

    @GetMapping("/load-elastic")
    public ResponseEntity<BasicMessageResponse<String>> loadProductsPublishedIndexIntoElastic() throws IOException {
        return ResponseEntity.ok(productDocumentService.loadProductsPublishedIndexIntoElastic());
    }

//    subcategory - tag - product

    @GetMapping("/subcategories&tags")
    public ResponseEntity<BasicMessageResponse<List<ProductSubcategoryAndTagResponse>>> fetchProductsWithSubcategoriesAndTags() {
        return ResponseEntity.ok(iProductServiceAdmin.fetchAllWithSubcategoriesAndTags());
    }

    @PutMapping("/update/relation")
    public ResponseEntity<BasicMessageResponse<ProductSubcategoryAndTagResponse>> updateSubcategoriesAndTags(@RequestParam("id") long id, @RequestBody ProductRelationUpdateRequest request) {
        return ResponseEntity.ok(iProductServiceAdmin.updateSubcategoriesAndTagIds(id, request));
    }


    //    Subcategory - Product
    @DeleteMapping("/subcategories/delete")
    public ResponseEntity<BasicMessageResponse<ProductSubcategoryIDResponse>> deleteRelationByProductIdAndSubcategoryId(@RequestParam("productId") long productId, @RequestParam("subcategoryId") int subcategoryId) {
        return ResponseEntity.ok(iProductServiceAdmin.deleteRelationByProductIdAndSubcategoryId(productId, subcategoryId));
    }

    //    Tag - Product
    @DeleteMapping("/tags/delete")
    public ResponseEntity<BasicMessageResponse<ProductIdAndTagIdResponse>> deleteRelationByProductIdAndTagId(@RequestParam("productId") long productId, @RequestParam("tagId") int tagId) {
        return ResponseEntity.ok(iProductServiceAdmin.deleteRelationByProductIdAndTagId(productId, tagId));
    }

}
