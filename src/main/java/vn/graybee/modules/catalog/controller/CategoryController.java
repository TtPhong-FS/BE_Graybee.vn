package vn.graybee.modules.catalog.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategoryIdActiveResponse;
import vn.graybee.modules.catalog.dto.response.CategoryUpdateDto;
import vn.graybee.modules.catalog.model.Category;
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
    public ResponseEntity<BasicMessageResponse<Category>> create(
            @RequestBody @Valid CategoryRequest request
    ) {
        Category category = categoryService.createCategory(request);
        final String message = messageSourceUtil.get("catalog.category.success.create", new Object[]{category.getName()});

        return ResponseEntity.ok(
                MessageBuilder.ok(category, message)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Category>> update(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest request) {
        Category category = categoryService.updateCategory(id, request);
        final String message = messageSourceUtil.get("catalog.category.success.update", new Object[]{category.getName()});
        
        return ResponseEntity.ok(
                MessageBuilder.ok(category, message)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> deleteById(@PathVariable("id") Long id) {

        final String message = messageSourceUtil.get("catalog.category.success.delete");

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.deleteById(id), message)
        );
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryIdActiveResponse>> toggleActiveById(@PathVariable("id") Long id) {
        CategoryIdActiveResponse category = categoryService.toggleActiveById(id);

        final String message = category.isActive() ? messageSourceUtil.get("catalog.category.success.show") : messageSourceUtil.get("catalog.category.success.hide");
        return ResponseEntity.ok(
                MessageBuilder.ok(category, message)
        );
    }

}
