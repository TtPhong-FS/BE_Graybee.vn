package vn.graybee.modules.catalog.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategorySimpleDto;
import vn.graybee.modules.catalog.dto.response.CategoryUpdateDto;
import vn.graybee.modules.catalog.dto.response.UpdateStatusDto;
import vn.graybee.modules.catalog.service.CategoryService;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("${api.adminApi.categories}")
public class CategoryController {

    private final CategoryService categoryService;

    private final MessageSourceUtil messageSourceUtil;

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryDto>>> findAllCategoryDto() {

        List<CategoryDto> categoryDtos = categoryService.getAllCategoryDtoForDashboard();

        final String message = categoryDtos.isEmpty() ? messageSourceUtil.get("catalog.category.empty.list") : messageSourceUtil.get("catalog.category.success.fetch.list");

        return ResponseEntity.ok(

                MessageBuilder.ok(categoryDtos, message)
        );
    }

    @GetMapping("/for-update/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryUpdateDto>> getCategoryUpdateDtoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.getCategoryUpdateDtoById(id), null)
        );
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<CategorySimpleDto>> create(
            @RequestBody @Valid CategoryRequest request
    ) {
        final String message = messageSourceUtil.get("catalog.category.success.create");

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.createCategory(request), message)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> deleteById(@PathVariable("id") Long id) {

        final String message = messageSourceUtil.get("catalog.category.success.delete");

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.deleteById(id), message)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategorySimpleDto>> update(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest request) {
        final String message = messageSourceUtil.get("catalog.category.success.update");

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.updateCategory(id, request), message)
        );
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<UpdateStatusDto>> updateStatusById(@PathVariable("id") Long id, @PathVariable("status") String status) {
        final String message = messageSourceUtil.get("catalog.category.success.update.status");

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.updateStatusById(id, status), message)
        );
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryDto>> restoreById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetail userDetail) {
        final String message = messageSourceUtil.get("catalog.category.success.restore");

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.restoreById(id, userDetail), message)
        );
    }


}
