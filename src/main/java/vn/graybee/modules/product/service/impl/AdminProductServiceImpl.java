package vn.graybee.modules.product.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.SlugUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicValueDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;
import vn.graybee.modules.catalog.enums.CategoryType;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.inventory.service.InventoryService;
import vn.graybee.modules.product.dto.request.ProductRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.request.ValidationProductRequest;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;
import vn.graybee.modules.product.repository.ProductRepository;
import vn.graybee.modules.product.service.AdminProductService;
import vn.graybee.modules.product.service.ProductAttributeValueService;
import vn.graybee.modules.product.service.ProductCategoryService;
import vn.graybee.modules.product.service.ProductClassifyViewService;
import vn.graybee.modules.product.service.ProductDescriptionService;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.modules.product.service.ProductImageService;
import vn.graybee.modules.product.service.RedisProductService;
import vn.graybee.modules.product.service.ReviewCommentSerivce;
import vn.graybee.response.admin.products.ProductStatusResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    private static final String PRODUCT_DETAIL_KEY = "product:detail:";

    private static final Set<ProductStatus> INVALID_PUBLISH_TARGET_STATUSES = Set.of(
            ProductStatus.DRAFT,
            ProductStatus.REMOVED,
            ProductStatus.COMING_SOON,
            ProductStatus.PENDING
    );

    private static final Set<ProductStatus> CONDITIONAL_PUBLISHABLE_STATUSES = Set.of(
            ProductStatus.OUT_OF_STOCK,
            ProductStatus.COMING_SOON,
            ProductStatus.PENDING
    );

    private final InventoryService inventoryService;

    private final MessageSourceUtil messageSourceUtil;

    private final ProductDocumentService productDocumentService;

    private final ProductDescriptionService productDescriptionService;

    private final ProductImageService productImageService;

    private final ProductCategoryService productCategoryService;

    private final ProductAttributeValueService productAttributeValueService;

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ReviewCommentSerivce reviewCommentSerivce;

    private final RedisProductService redisProductService;

    private final ProductClassifyViewService productClassifyViewService;

    public AdminProductServiceImpl(MessageSourceUtil messageSourceUtil, ProductDocumentService productDocumentService, ProductDescriptionService productDescriptionService, ProductImageService productImageService, ProductRepository productRepository, CategoryService categoryService, RedisProductService redisProductService, ProductCategoryService productCategoryService, ProductClassifyViewService productClassifyViewService, ProductAttributeValueService productAttributeValueService, @Lazy InventoryService inventoryService, ReviewCommentSerivce reviewCommentSerivce) {
        this.messageSourceUtil = messageSourceUtil;
        this.productAttributeValueService = productAttributeValueService;
        this.productClassifyViewService = productClassifyViewService;
        this.productCategoryService = productCategoryService;
        this.productDocumentService = productDocumentService;
        this.productDescriptionService = productDescriptionService;
        this.productImageService = productImageService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.redisProductService = redisProductService;
        this.inventoryService = inventoryService;
        this.reviewCommentSerivce = reviewCommentSerivce;
    }

    public ProductResponse getProductResponse(Product product, String categoryName, String brandName, List<String> tagNames) {
        return new ProductResponse(
                product,
                categoryName,
                brandName,
                tagNames
        );
    }

    private BigDecimal calculateFinalPrice(BigDecimal price, int discount) {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
        );
    }


    public void checkExistById(long id) {
        if (!productRepository.checkExistsById(id)) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.does_not_exists"));
        }
    }


    private void checkExistsByName(String name) {

        if (productRepository.existsByName(name)) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("product.name_exists", new Object[]{name}));
        }
    }

    private void checkExistsBySlug(String slug) {
        if (slug == null || slug.isEmpty()) return;
        if (productRepository.existsBySlug(slug)) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("product.name_exists", new Object[]{slug}));
        }
    }

    private void checkExistsByNameNotId(String name, long id) {
        if (productRepository.existsByNameAndNotId(name, id)) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("product.name_exists", new Object[]{name}));
        }
    }

    private void checkExistsBySlugNotId(String slug, long id) {
        if (slug == null || slug.isEmpty()) return;
        if (productRepository.existsBySlugAndNotId(slug, id)) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("product.name_exists", new Object[]{slug}));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductResponse createProduct(ProductRequest request) {

        ProductStatus status = ProductStatus.getStatus(request.getStatus(), messageSourceUtil);

        CategorySummaryDto category = categoryService.checkType(request.getCategoryName(), CategoryType.CATEGORY);

        CategorySummaryDto brand = categoryService.checkType(request.getBrandName(), CategoryType.BRAND);

        checkExistsByName(request.getName());
        checkExistsBySlug(request.getSlug());

        Product product = new Product();
        product.setCategoryId(category.getId());
        product.setBrandId(brand.getId());

        if (request.getSlug() != null && !request.getSlug().isEmpty()) {
            product.setSlug(SlugUtil.toSlug(request.getSlug()));
        } else {
            product.setSlug(SlugUtil.toSlug(request.getName()));
        }

        product.setStatus(status);
        product.setName(TextUtils.capitalizeEachWord(request.getName().trim()));
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(product.calculateFinalPrice(request.getPrice(), request.getDiscountPercent()));
        product.setConditions(request.getConditions().toUpperCase());
        product.setWarranty(request.getWarranty());
        product.setThumbnail(request.getImages() != null && !request.getImages().isEmpty() ? request.getImages().get(0) : null);

        product = productRepository.save(product);

        productCategoryService.createProductCategoryByTags(product.getId(), request.getTagNames());

        productImageService.saveProductImages(product.getId(), request.getImages());

//        Create Detail information for Product
        productAttributeValueService.createProductAttributeValue(product.getId(), product.getCategoryId(), request.getCategoryName(), request.getSpecifications());

        productDescriptionService.saveProductDescription(product.getId(), request.getDescription());

        inventoryService.saveInventoryByProductId(product.getId(), request.getQuantity());

//        redisProductService.deleteProductListPattern(request.getCategoryName());

        return getProductResponse(product, category.getName(), brand.getName(), request.getTagNames());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductResponse updateProduct(long id, ProductUpdateRequest request) {

        ProductStatus status = ProductStatus.getStatus(request.getStatus(), messageSourceUtil);

        CategorySummaryDto brand = categoryService.checkType(request.getBrandName(), CategoryType.BRAND);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.does_not_exists")));

        checkExistsByNameNotId(request.getName(), product.getId());
        checkExistsBySlugNotId(request.getSlug(), product.getId());

        String categoryName = categoryService.findNameById(product.getCategoryId());

        if (request.getSlug() != null && !request.getSlug().isEmpty()) {
            product.setSlug(SlugUtil.toSlug(request.getSlug()));
        } else {
            product.setSlug(SlugUtil.toSlug(request.getName()));
        }

        product.setBrandId(brand.getId());
        product.setName(TextUtils.capitalizeEachWord(request.getName()));
        product.setWarranty(request.getWarranty());
        product.setThumbnail(request.getImages() != null && !request.getImages().isEmpty() ? request.getImages().get(0) : null);
        product.setStatus(status);
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(product.calculateFinalPrice(request.getPrice(), request.getDiscountPercent()));
        product.setUpdatedAt(LocalDateTime.now());

        product = productRepository.save(product);

        productCategoryService.updateProductCategoryByTags(product.getId(), request.getTagNames());
        productImageService.saveProductImages(product.getId(), request.getImages());

        productAttributeValueService.updateProductAttributeValue(product.getId(), request.getSpecifications());

        productDescriptionService.updateProductDescription(product.getId(), request.getDescription());
        inventoryService.saveInventoryByProductId(product.getId(), request.getQuantity());

        return getProductResponse(product, categoryName, brand.getName(), request.getTagNames());
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long deleteProductById(long id) {

        int leftQuantity = inventoryService.getAvailableQuantity(id);

        if (leftQuantity > 0) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.left_quantity", new Object[]{leftQuantity}));
        }

        productRepository.deleteById(id);
        productDocumentService.deleteProduct(id);

        return id;
    }

    @Override
    public List<ProductResponse> getAllProductDtoForDashboard() {
        return productRepository.getAllProductResponse();
    }

    @Override
    public ProductUpdateDto getProductUpdateDtoById(long productId) {

        ProductUpdateDto productUpdateDto = productRepository.findProductUpdateDtoById(productId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found")));

        List<String> images = productImageService.getProductImages(productUpdateDto.getId());

        Optional<Long> categoryId = productRepository.getCategoryIdById(productId);

        List<AttributeBasicValueDto> attributeBasicValueDtos = productAttributeValueService.getAllAttributeValueByCategoryAndProduct(categoryId.get(), productUpdateDto.getId());
        productUpdateDto.setImages(images);
        productUpdateDto.setSpecifications(attributeBasicValueDtos);

        return productUpdateDto;
    }


    private ProductStatusResponse updateStatus(Long id, ProductStatus status, String message) {
        productRepository.updateStatusById(id, status);
        return new ProductStatusResponse(id, status, LocalDateTime.now());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductStatusResponse updateStatus(long id, String status) {

        ProductStatus newStatus = ProductStatus.getStatus(status, messageSourceUtil);

        checkExistById(id);

        ProductStatus currentStatus = productRepository.findStatusById(id);

        if (currentStatus == newStatus) {
            return null;
        }

        if (currentStatus == ProductStatus.REMOVED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.in_trash"));
        }

        if (INVALID_PUBLISH_TARGET_STATUSES.contains(newStatus) && currentStatus == ProductStatus.PUBLISHED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.published", new Object[]{newStatus.getDisplayName()}));
        }

        if (CONDITIONAL_PUBLISHABLE_STATUSES.contains(currentStatus) &&
                newStatus == ProductStatus.PUBLISHED) {
            return updateStatus(id, newStatus, messageSourceUtil.get("product.success_published"));
        }

        if (newStatus == ProductStatus.PUBLISHED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.pending_to_published"));
        }

        return updateStatus(id, newStatus, messageSourceUtil.get("product.success.update.status"));

    }

    @Override
    public InventoryProductDto getProductBasicDtoById(long productId) {
        return productRepository.findProductBasicDtoById(productId)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.does_not_exists")));
    }

    @Override
    public void checkBeforeCreate(Long id, ValidationProductRequest request) {
        ProductStatus.getStatus(request.getStatus(), messageSourceUtil);

        categoryService.checkType(request.getCategoryName(), CategoryType.CATEGORY);
        categoryService.checkType(request.getBrandName(), CategoryType.BRAND);


        if (id == null) {
            checkExistsByName(request.getName());
            checkExistsBySlug(request.getSlug());

        }


        if (id != null) {
            checkExistsByNameNotId(request.getName(), id);
            checkExistsBySlugNotId(request.getSlug(), id);
        }

    }

    @Override
    public ProductDetailDto getProductDetailById(long id) {

        ProductDetailDto productDetailDto = productRepository.findProductDetailDtoById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found")));

        List<AttributeDisplayDto> attributeDisplayDtos = productAttributeValueService.getAttributeDisplayDtosByProductId(productDetailDto.getProductId());

        List<ReviewCommentDto> reviewCommentDtos = reviewCommentSerivce.getReviewCommentDtosByProductId(productDetailDto.getProductId());


        productDetailDto.setReviews(reviewCommentDtos);
        productDetailDto.setSpecifications(attributeDisplayDtos);
        return productDetailDto;

    }


}
