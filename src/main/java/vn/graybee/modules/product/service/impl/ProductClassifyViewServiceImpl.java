package vn.graybee.modules.product.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.modules.product.model.ProductClassifyView;
import vn.graybee.modules.product.repository.ProductClassifyViewRepository;
import vn.graybee.modules.product.service.ProductClassifyViewService;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductClassifyViewServiceImpl implements ProductClassifyViewService {

    private final ProductClassifyViewRepository productClassifyViewRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductClassifyView createProductClassifyView(Long productId, String productName, String productSlug, String categoryName, String brandName, List<String> tagNames) {
        ProductClassifyView productClassifyView = new ProductClassifyView();
        productClassifyView.setProductId(productId);
        productClassifyView.setProductName(productName);
        productClassifyView.setProductSlug(productSlug);
        productClassifyView.setCategoryName(categoryName);
        productClassifyView.setBrandName(brandName);
        productClassifyView.setTagNames(tagNames);

        return productClassifyViewRepository.save(productClassifyView);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductClassifyView updateProductClassifyViewByProductId(Long productId, String productName, String productSlug, String brandName, List<String> tagNames) {
        ProductClassifyView productClassifyView = productClassifyViewRepository.findByProductId(productId)
                .orElseGet(ProductClassifyView::new);

        if (productClassifyView.getId() == null) {
            productClassifyView.setProductId(productId);
        }

        productClassifyView.setBrandName(brandName);
        productClassifyView.setProductName(productName);
        productClassifyView.setProductSlug(productSlug);
        productClassifyView.setTagNames(tagNames);

        return productClassifyViewRepository.save(productClassifyView);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void removeTagByTagName(String tagName) {
        try {
            String jsonTag = new ObjectMapper().writeValueAsString(List.of(tagName));
            List<ProductClassifyView> productClassifyViews = productClassifyViewRepository.findAllByTagName(jsonTag);

            for (ProductClassifyView pcv : productClassifyViews) {
                if (pcv.getTagNames() != null && pcv.getTagNames().contains(jsonTag)) {
                    pcv.getTagNames().removeIf(t -> t.equalsIgnoreCase(jsonTag));
                    productClassifyViewRepository.save(pcv);
                }
            }
        } catch (JsonProcessingException e) {
            throw new BusinessCustomException(Constants.Common.global, e.getMessage());
        }
    }

    @Override
    public ProductClassifyView findByProductId(long productId) {
        return productClassifyViewRepository.findByProductId(productId).orElse(null);
    }

}
