package vn.graybee.modules.product.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
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
import vn.graybee.modules.product.dto.request.ProductCreateRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;
import vn.graybee.modules.product.repository.ProductRepository;
import vn.graybee.modules.product.service.AdminProductService;
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


    private final ProductInventoryHelperService productInventoryHelperService;


    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final RedisProductService redisProductService;

    public AdminProductServiceImpl(MessageSourceUtil messageSourceUtil, ProductDocumentService productDocumentService, ProductDescriptionService productDescriptionService, ProductImageService productImageService, @Lazy ProductInventoryHelperService productInventoryHelperService, ProductRepository productRepository, CategoryService categoryService, RedisProductService redisProductService) {
        this.messageSourceUtil = messageSourceUtil;
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

    private BigDecimal calculateFinalPrice(BigDecimal price, int discount) {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
        );
    }


    public void checkExistById(long id) {
        if (!productRepository.checkExistsById(id)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> createProduct(ProductCreateRequest request) {


        ProductStatus status = ProductStatus.getStatus(request.getStatus(), messageSourceUtil);

        if (productRepository.existsByName(request.getName())) {
            throw new BusinessCustomException(ConstantProduct.name, messageSourceUtil.get("product.name_exists", new Object[]{request.getName()}));
        }

        CategorySummaryDto categorySummaryDto = categoryService.getCategorySummaryByName(request.getProductType());

        BigDecimal finalPrice = BigDecimal.ZERO;

        if (request.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            finalPrice = calculateFinalPrice(request.getPrice(), request.getDiscountPercent());
        }

        Product product = new Product();

        product.setStatus(status);
        product.setName(TextUtils.capitalizeEachWord(request.getName()));
        product.setSlug(SlugUtil.toSlug(request.getName()));
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(finalPrice);
        product.setConditions(request.getConditions().toUpperCase());
        product.setDimension(request.getDimension());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setWarranty(request.getWarranty());
        product.setThumbnail(request.getImages() != null && !request.getImages().isEmpty() ? request.getImages().get(0) : null);

        product = productRepository.save(product);

        productDescriptionService.saveProductDescription(product.getId(), request.getDescription());
        productImageService.saveProductImages(product.getId(), request.getImages().stream().skip(0).toList());

        productInventoryHelperService.saveInventoryByProductId(product.getId(), request.isStock(), request.getQuantity());

        productDocumentService.insertProduct(product);

        categoryService.updateProductCount(categorySummaryDto.getId(), true);

        redisProductService.deleteProductListPattern(request.getProductType());

        ProductResponse response = getProductResponse(product);

        return new BasicMessageResponse<>(201, ConstantProduct.success_create, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> updateProduct(long id, ProductUpdateRequest request) {

        ProductStatus status = ProductStatus.getStatus(request.getStatus(), messageSourceUtil);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.does_not_exists")));

        if (!product.getName().equals(request.getName()) && productRepository.existsByNameAndNotId(request.getName(), product.getId())) {
            throw new BusinessCustomException(ConstantProduct.name, messageSourceUtil.get("product.name_exists", new Object[]{request.getName()}));
        }

        BigDecimal finalPrice = BigDecimal.ZERO;

        if (request.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            finalPrice = calculateFinalPrice(request.getPrice(), request.getDiscountPercent());
        }

        product.setName(TextUtils.capitalizeEachWord(request.getName()));
        product.setSlug(SlugUtil.toSlug(request.getName()));
        product.setWarranty(request.getWarranty());
        product.setWeight(request.getWeight());
        product.setDimension(request.getDimension());
        product.setThumbnail(request.getImages() != null && !request.getImages().isEmpty() ? request.getImages().get(0) : null);
        product.setStatus(status);
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(finalPrice);
        product.setColor(request.getColor());

        product.setUpdatedAt(LocalDateTime.now());

        product = productRepository.save(product);


        productDescriptionService.updateProductDescription(product.getId(), request.getDescription());

        productImageService.updateProductImages(product.getId(), request.getImages());

        productInventoryHelperService.updateInventoryByProductId(product.getId(), request.isStock(), request.getQuantity());

        ProductResponse response = getProductResponse(product);

        String key = PRODUCT_DETAIL_KEY + product.getId();

        redisProductService.deleteProduct(key);
        redisProductService.deleteProductListPattern(null);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("product.success_update_by_id", new Object[]{product.getName()}), response);
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Long> deleteProductById(long id) {

        int leftQuantity = productInventoryHelperService.getAvailableStock(id);

        if (leftQuantity > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.left_quantity", new Object[]{leftQuantity}));
        }

        productRepository.deleteById(id);
        productDocumentService.deleteProduct(id);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("product.success_delete"), id);
    }

    @Override
    public MessageResponse<List<ProductResponse>> getAllProductDtoForDashboard(String status, String category, String manufacturer, int page, int size, String sortBy, String order) {

        ProductStatus statusEnum = null;

        try {
            if (status != null && !status.isEmpty() && !"all".equals(status)) {
                statusEnum = ProductStatus.valueOf(status.toUpperCase());

            }
        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantGeneral.status_invalid);
        }

        String categoryName = category.equals("all") ? null : category;
        String manufacturerName = manufacturer.equals("all") ? null : manufacturer;

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<ProductResponse> productPage = productRepository.fetchProducts(
                statusEnum,
                pageRequest);

        PaginationInfo paginationInfo = new PaginationInfo(
                productPage.getNumber(),
                productPage.getTotalPages(),
                productPage.getTotalElements(),
                productPage.getSize()
        );

        SortInfo sortInfo = new SortInfo(sortBy, order);


        String message = productPage.getContent().isEmpty() ? ConstantGeneral.empty_list : ConstantProduct.success_fetch_products;


        return new MessageResponse<>(200, message, productPage.getContent(), paginationInfo, sortInfo);
    }

    @Override
    public BasicMessageResponse<ProductUpdateDto> getById(long productId) {

        ProductUpdateDto response = productRepository.findProductUpdateDtoById(productId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        List<String> images = productImageService.getProductImages(response.getId());

        response.setImages(images);

        return new BasicMessageResponse<>(200, ConstantProduct.success_find_by_id, response);
    }


    private BasicMessageResponse<ProductStatusResponse> updateStatus(Long id, ProductStatus status, String message) {
        productRepository.updateStatusById(id, status);
        ProductStatusResponse response = new ProductStatusResponse(id, status, LocalDateTime.now());
        return new BasicMessageResponse<>(200, message, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductStatusResponse> updateStatus(long id, String status) {

        ProductStatus newStatus = ProductStatus.getStatus(status, messageSourceUtil);

        checkExistById(id);

        ProductStatus currentStatus = productRepository.findStatusById(id);

        if (currentStatus == newStatus) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("product.status.not_changed"), null);
        }

        if (currentStatus == ProductStatus.REMOVED && newStatus == ProductStatus.DELETED) {
            return updateStatus(id, newStatus, messageSourceUtil.get("product.success_delete"));
        }

        if (currentStatus == ProductStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.removed"));
        }

        if (currentStatus == ProductStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.soft_delete"));
        }

        if (INVALID_PUBLISH_TARGET_STATUSES.contains(newStatus) && currentStatus == ProductStatus.PUBLISHED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.published", new Object[]{newStatus.getDisplayName()}));
        }

        if (CONDITIONAL_PUBLISHABLE_STATUSES.contains(currentStatus) &&
                newStatus == ProductStatus.PUBLISHED) {
            return updateStatus(id, newStatus, messageSourceUtil.get("product.success_published"));
        }

        if (newStatus == ProductStatus.PUBLISHED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.pending_to_published"));
        }

        throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("common.status.invalid", new Object[]{newStatus.getCode()}));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> restoreProduct(long id, UserDetail userDetail) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.does_not_exists")));

        ProductStatus status = product.getStatus();

        if (userDetail != null && !userDetail.user().isSuperAdmin() && status == ProductStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("auth.not_super_admin"));
        }

        if (status != ProductStatus.DELETED && status != ProductStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.not_in_trash"));
        }

        productRepository.updateStatusById(id, ProductStatus.INACTIVE);


        ProductResponse response = getProductResponse(product);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("product.success.restore", new Object[]{product.getName()}), response);
    }

    @Override
    public InventoryProductDto getProductBasicDtoById(long productId) {
        InventoryProductDto inventoryProductDto = productRepository.findProductBasicDtoById(productId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("product.does_not_exists")));
        return inventoryProductDto;
    }


}
