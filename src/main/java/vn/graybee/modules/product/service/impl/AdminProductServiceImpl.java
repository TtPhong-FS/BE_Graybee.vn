package vn.graybee.modules.product.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.SlugUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.dto.request.ProductRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.dto.response.ProductWithClassifyDto;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;
import vn.graybee.modules.product.model.ProductClassifyView;
import vn.graybee.modules.product.repository.ProductRepository;
import vn.graybee.modules.product.service.AdminProductService;
import vn.graybee.modules.product.service.ProductAttributeValueService;
import vn.graybee.modules.product.service.ProductCategoryService;
import vn.graybee.modules.product.service.ProductClassifyViewService;
import vn.graybee.modules.product.service.ProductDescriptionService;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.modules.product.service.ProductImageService;
import vn.graybee.modules.product.service.ProductInventoryHelperService;
import vn.graybee.modules.product.service.RedisProductService;
import vn.graybee.response.admin.products.ProductStatusResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    private static final String PRODUCT_DETAIL_KEY = "product:detail:";

    private static final Set<ProductStatus> INVALID_PUBLISH_TARGET_STATUSES = Set.of(
            ProductStatus.DRAFT,
            ProductStatus.REMOVED,
            ProductStatus.COMING_SOON,
            ProductStatus.DELETED,
            ProductStatus.PENDING
    );

    private static final Set<ProductStatus> CONDITIONAL_PUBLISHABLE_STATUSES = Set.of(
            ProductStatus.OUT_OF_STOCK,
            ProductStatus.COMING_SOON,
            ProductStatus.PENDING
    );

    private final MessageSourceUtil messageSourceUtil;

    private final ProductDocumentService productDocumentService;

    private final ProductDescriptionService productDescriptionService;

    private final ProductImageService productImageService;

    private final ProductCategoryService productCategoryService;

    private final ProductInventoryHelperService productInventoryHelperService;

    private final ProductAttributeValueService productAttributeValueService;

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final RedisProductService redisProductService;

    private final ProductClassifyViewService productClassifyViewService;

    public AdminProductServiceImpl(MessageSourceUtil messageSourceUtil, ProductDocumentService productDocumentService, ProductDescriptionService productDescriptionService, ProductImageService productImageService, @Lazy ProductInventoryHelperService productInventoryHelperService, ProductRepository productRepository, CategoryService categoryService, RedisProductService redisProductService, ProductCategoryService productCategoryService, ProductClassifyViewService productClassifyViewService, ProductAttributeValueService productAttributeValueService) {
        this.messageSourceUtil = messageSourceUtil;
        this.productAttributeValueService = productAttributeValueService;
        this.productClassifyViewService = productClassifyViewService;
        this.productCategoryService = productCategoryService;
        this.productDocumentService = productDocumentService;
        this.productDescriptionService = productDescriptionService;
        this.productImageService = productImageService;
        this.productInventoryHelperService = productInventoryHelperService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.redisProductService = redisProductService;
    }

    public ProductResponse getProductResponse(Product product) {
        return new ProductResponse(
                product
        );
    }

    private ProductWithClassifyDto getProductWithClassifyDto(Product product, ProductClassifyView classifyView) {
        return new ProductWithClassifyDto(product, classifyView);
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

    public CategorySummaryDto getCategorySummaryDtoByNameOrId(String name, Long id) {
        return categoryService.getCategorySummaryByNameOrId(name, id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductWithClassifyDto createProduct(ProductRequest request) {

        ProductStatus status = ProductStatus.getStatus(request.getStatus(), messageSourceUtil);

        if (productRepository.existsByName(request.getName())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("product.name_exists", new Object[]{request.getName()}));
        }

        Product product = new Product();

        if (request.getSlug() != null && !request.getSlug().isEmpty()) {
            product.setSlug(SlugUtil.toSlug(request.getSlug()));
        } else {
            product.setSlug(SlugUtil.toSlug(request.getName()));
        }

        product.setStatus(status);
        product.setName(TextUtils.capitalizeEachWord(request.getName()));
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(product.calculateFinalPrice(request.getPrice(), request.getDiscountPercent()));
        product.setConditions(request.getConditions().toUpperCase());
        product.setDimension(request.getDimension());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setWarranty(request.getWarranty());
        product.setThumbnail(request.getImages() != null && !request.getImages().isEmpty() ? request.getImages().get(0) : null);

        product = productRepository.save(product);

        productCategoryService.createProductCategory(product.getId(), request.getCategoryName(), request.getBrandName(), request.getTagNames());

//        Create Detail information for Product
        productAttributeValueService.createProductAttributeValue(product.getId(), request.getCategoryName(), request.getAttributes());

        ProductClassifyView productClassifyView = productClassifyViewService.createProductClassifyView(product.getId(), product.getName(), product.getSlug(), request.getCategoryName(), request.getBrandName(), request.getTagNames());

        productDescriptionService.saveProductDescription(product.getId(), request.getDescription());

        productImageService.saveProductImages(product.getId(), request.getImages());

        productInventoryHelperService.saveInventoryByProductId(product.getId(), request.isStock(), request.getQuantity());

        productDocumentService.insertProduct(product);

//        redisProductService.deleteProductListPattern(request.getCategoryName());

        return getProductWithClassifyDto(product, productClassifyView);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductWithClassifyDto updateProduct(long id, ProductUpdateRequest request) {

        ProductStatus status = ProductStatus.getStatus(request.getStatus(), messageSourceUtil);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.does_not_exists")));

        if (!product.getName().equals(request.getName()) && productRepository.existsByNameAndNotId(request.getName(), product.getId())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("product.name_exists", new Object[]{request.getName()}));
        }

        if (request.getSlug() != null && !request.getSlug().isEmpty()) {
            product.setSlug(SlugUtil.toSlug(request.getSlug()));
        } else {
            product.setSlug(SlugUtil.toSlug(request.getName()));
        }

        product.setName(TextUtils.capitalizeEachWord(request.getName()));
        product.setWarranty(request.getWarranty());
        product.setWeight(request.getWeight());
        product.setDimension(request.getDimension());

        if (product.getThumbnail() == null || product.getThumbnail().isEmpty()) {
            product.setThumbnail(request.getImages() != null && !request.getImages().isEmpty() ? request.getImages().get(0) : null);
        }

        product.setStatus(status);
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(product.calculateFinalPrice(request.getPrice(), request.getDiscountPercent()));
        product.setColor(request.getColor());

        product.setUpdatedAt(LocalDateTime.now());

        product = productRepository.save(product);

        productCategoryService.updateProductCategory(product.getId(), request.getBrandName(), request.getTagNames());

        productAttributeValueService.updateProductAttributeValue(product.getId(), request.getAttributes());

        ProductClassifyView productClassifyView = productClassifyViewService.updateProductClassifyViewByProductId(product.getId(), product.getName(), product.getSlug(), request.getBrandName(), request.getTagNames());

        productDescriptionService.updateProductDescription(product.getId(), request.getDescription());

        productImageService.updateProductImages(product.getId(), request.getImages());

        productInventoryHelperService.updateInventoryByProductId(product.getId(), request.isStock(), request.getQuantity());

//        String key = PRODUCT_DETAIL_KEY + product.getId();

//        redisProductService.deleteProduct(key);
//        redisProductService.deleteProductListPattern(null);

        return getProductWithClassifyDto(product, productClassifyView);
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long deleteProductById(long id) {

        int leftQuantity = productInventoryHelperService.getAvailableStock(id);

        if (leftQuantity > 0) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.left_quantity", new Object[]{leftQuantity}));
        }

        productRepository.deleteById(id);
        productDocumentService.deleteProduct(id);

        return id;
    }

    @Override
    public Page<ProductResponse> getAllProductDtoForDashboard(String status, String category, String manufacturer, int page, int size, String sortBy, String order) {

        ProductStatus statusEnum = null;


        if (status != null && !status.isEmpty() && !"all".equals(status)) {
            statusEnum = ProductStatus.getStatus(status, messageSourceUtil);
        }


        String categoryName = category.equals("all") ? null : category;
        String manufacturerName = manufacturer.equals("all") ? null : manufacturer;

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<ProductResponse> productPage = productRepository.fetchProducts(
                statusEnum,
                pageRequest);

        PaginationInfo paginationInfo = new PaginationInfo(

        );

        SortInfo sortInfo = new SortInfo(sortBy, order);

        String message = productPage.getContent().isEmpty() ? messageSourceUtil.get("") : messageSourceUtil.get("s");


        return productPage;
    }

    @Override
    public ProductUpdateDto getById(long productId) {

        ProductUpdateDto response = productRepository.findProductUpdateDtoById(productId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found")));

        List<String> images = productImageService.getProductImages(response.getId());

        response.setImages(images);

        return response;
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

        if (currentStatus == ProductStatus.REMOVED && newStatus == ProductStatus.DELETED) {
            return updateStatus(id, newStatus, messageSourceUtil.get("product.success_delete"));
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

        throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("common.status.invalid", new Object[]{newStatus.getCode()}));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductWithClassifyDto restoreProduct(long id, UserDetail userDetail) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.does_not_exists")));

        ProductStatus status = product.getStatus();

        if (userDetail != null && !userDetail.user().isSuperAdmin() && status == ProductStatus.DELETED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("auth.not_super_admin"));
        }

        if (status != ProductStatus.DELETED && status != ProductStatus.REMOVED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.not_in_trash"));
        }

        productRepository.updateStatusById(id, ProductStatus.INACTIVE);

        ProductClassifyView productClassifyView = productClassifyViewService.findByProductId(product.getId());

        return getProductWithClassifyDto(product, productClassifyView);
    }

    @Override
    public InventoryProductDto getProductBasicDtoById(long productId) {
        return productRepository.findProductBasicDtoById(productId)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("product.does_not_exists")));
    }


}
