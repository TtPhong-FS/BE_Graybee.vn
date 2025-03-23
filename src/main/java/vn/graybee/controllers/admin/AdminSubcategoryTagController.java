package vn.graybee.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse;
import vn.graybee.services.categories.SubcategoryTagServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/subcategories-tags")
public class AdminSubcategoryTagController {

    private final SubcategoryTagServices services;

    public AdminSubcategoryTagController(SubcategoryTagServices services) {
        this.services = services;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<SubcategoryTagIdResponse>> deleteRelation(@RequestParam("subcategoryId") int subcategoryId, @RequestParam("tagId") int tagId) {
        return ResponseEntity.ok(services.deleteRelationsBySubCategoryIdAndTagId(subcategoryId, tagId));
    }

}
