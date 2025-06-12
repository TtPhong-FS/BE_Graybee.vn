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
import vn.graybee.modules.catalog.dto.request.AttributeRequest;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdActiveResponse;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdAllCategoryIdName;
import vn.graybee.modules.catalog.service.AttributeService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.adminApi.attributes}")
public class AttributeController {

    private final AttributeService attributeService;

    private final MessageSourceUtil messageSourceUtil;

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<AttributeDto>> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                new BasicMessageResponse<>(200, null, attributeService.findById(id))
        );
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<AttributeDto>>> findAllAttributeDto() {

        List<AttributeDto> attributeDtos = attributeService.findAllAttributeDto();

        final String message = attributeDtos.isEmpty() ? messageSourceUtil.get("catalog.attribute.empty.list") : messageSourceUtil.get("catalog.attribute.success.fetch.list");

        return ResponseEntity.ok(
                new BasicMessageResponse<>(200, message, attributeService.findAllAttributeDto())
        );
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<AttributeDto>> createAttribute(@Valid @RequestBody AttributeRequest request) {
        AttributeDto attributeDto = attributeService.createAttribute(request);
        return ResponseEntity.ok(
                new BasicMessageResponse<>(200, messageSourceUtil.get("catalog.attribute.success.create", new Object[]{attributeDto.getName()}), attributeDto)
        );
    }

    @PostMapping("/{id}/categories")
    public ResponseEntity<BasicMessageResponse<AttributeIdAllCategoryIdName>> addCategoriesToAttribute(
            @PathVariable long id,
            @RequestBody List<String> categoryNames) {

        System.out.println(categoryNames);

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        attributeService.addCategoriesToAttributeById(id, categoryNames),
                        messageSourceUtil.get("catalog.attribute.success.add.category")
                )
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<AttributeDto>> updateAttribute(
            @PathVariable("id") Long id,
            @Valid @RequestBody AttributeRequest request) {

        AttributeDto attributeDto = attributeService.updateAttribute(id, request);

        return ResponseEntity.ok(
                new BasicMessageResponse<>(200, messageSourceUtil.get("catalog.attribute.success.update", new Object[]{attributeDto.getName()}), attributeDto)
        );
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<BasicMessageResponse<AttributeIdActiveResponse>> setShowOrHideById(
            @PathVariable("id") Long id) {

        AttributeIdActiveResponse attribute = attributeService.setShowOrHideById(id);

        final String message = attribute.isActive() ? messageSourceUtil.get("catalog.attribute.success.show") : messageSourceUtil.get("catalog.attribute.success.hide");

        return ResponseEntity.ok(
                MessageBuilder.ok(attribute, message
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                new BasicMessageResponse<>(200, messageSourceUtil.get("catalog.attribute.success.delete"), attributeService.deleteById(id))
        );
    }

}
