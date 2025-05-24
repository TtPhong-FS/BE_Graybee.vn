package vn.graybee.product.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.security.UserDetail;
import vn.graybee.common.constants.ConstantCategory;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantManufacturer;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.constants.ConstantSubcategory;
import vn.graybee.common.constants.ConstantTag;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.inventory.model.Inventory;
import vn.graybee.inventory.repository.InventoryRepository;
import vn.graybee.models.products.ProductTag;
import vn.graybee.product.dto.request.ProductCreateRequest;
import vn.graybee.product.dto.request.ProductRelationUpdateRequest;
import vn.graybee.product.dto.request.ProductUpdateRequest;
import vn.graybee.product.dto.response.ProductResponse;
import vn.graybee.product.enums.ProductStatus;
import vn.graybee.product.model.Product;
import vn.graybee.product.model.ProductDescription;
import vn.graybee.product.model.ProductImage;
import vn.graybee.product.repository.ProductDescriptionRepository;
import vn.graybee.product.repository.ProductImageRepository;
import vn.graybee.product.repository.ProductRepository;
import vn.graybee.product.service.AdminProductService;
import vn.graybee.product.service.ProductCodeGenerator;
import vn.graybee.product.service.ProductDocumentService;
import vn.graybee.product.service.RedisProductService;
import vn.graybee.repositories.products.ProductSubcategoryRepository;
import vn.graybee.repositories.products.ProductTagRepository;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductQuantityResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryDto;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;
import vn.graybee.response.admin.products.ProductTagDto;
import vn.graybee.response.admin.products.ProductUpdateResponse;
import vn.graybee.taxonomy.category.dto.response.CategoryStatusDto;
import vn.graybee.taxonomy.category.repository.CategoryRepository;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerStatusDto;
import vn.graybee.taxonomy.manufacturer.repository.ManufacturerRepository;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto;
import vn.graybee.taxonomy.subcategory.repository.SubcategoryRepository;
import vn.graybee.taxonomy.tag.dto.response.TagDto;
import vn.graybee.taxonomy.tag.repository.TagRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final ProductDescriptionRepository productDescriptionRepository;

    private final TagRepository tagRepository;

    private final SubcategoryRepository subCategoryRepository;

    private final ProductStatisticRepository productStatisticRepository;

    private final ProductSubcategoryRepository productSubcategoryRepository;

    private final InventoryRepository inventoryRepository;

    private final ProductTagRepository productTagRepository;

    private final ProductCodeGenerator codeGenerator;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final CategoryRepository categoryRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final RedisProductService redisProductService;

    public AdminProductServiceImpl(MessageSourceUtil messageSourceUtil, ProductDocumentService productDocumentService, ProductDescriptionRepository productDescriptionRepository, TagRepository tagRepository, SubcategoryRepository subCategoryRepository, ProductStatisticRepository productStatisticRepository, ProductSubcategoryRepository productSubcategoryRepository, InventoryRepository inventoryRepository, ProductTagRepository productTagRepository, ProductCodeGenerator codeGenerator, ProductRepository productRepository, ProductImageRepository productImageRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository, RedisProductService redisProductService) {
        this.messageSourceUtil = messageSourceUtil;
        this.productDocumentService = productDocumentService;
        this.productDescriptionRepository = productDescriptionRepository;
        this.tagRepository = tagRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productStatisticRepository = productStatisticRepository;
        this.productSubcategoryRepository = productSubcategoryRepository;
        this.inventoryRepository = inventoryRepository;
        this.productTagRepository = productTagRepository;
        this.codeGenerator = codeGenerator;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.redisProductService = redisProductService;
    }

    public ProductResponse getProductResponse(Product product, String categoryName, String manufacturerName) {
        return new ProductResponse(
                product,
                categoryName,
                manufacturerName

        );
    }

    private BigDecimal calculateFinalPrice(BigDecimal price, int discount) {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
        );
    }

    private CategoryStatusDto getCategory(String categoryName) {
        CategoryStatusDto category = categoryRepository.findNameAndStatusByName(categoryName).orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryName, ConstantCategory.does_not_exists));

        if (category.getStatus().equals(TaxonomyStatus.REMOVED)) {
            throw new BusinessCustomException(ConstantCategory.categoryName, ConstantCategory.in_removed);
        }

        if (category.getStatus().equals(TaxonomyStatus.DELETED)) {
            throw new BusinessCustomException(ConstantCategory.categoryName, ConstantCategory.in_deleted);
        }

        return category;
    }

    private ManufacturerStatusDto getManufacturer(Integer manufacturerId) {

        ManufacturerStatusDto manufacturer = manufacturerRepository.findNameAndStatusById(manufacturerId)
                .orElseThrow(() -> new BusinessCustomException(ConstantManufacturer.manufacturerId, ConstantManufacturer.does_not_exists));

        if (manufacturer.getStatus() == TaxonomyStatus.REMOVED) {
            throw new BusinessCustomException(ConstantManufacturer.manufacturerId, ConstantCategory.in_removed);
        }

        if (manufacturer.getStatus() == TaxonomyStatus.DELETED) {
            throw new BusinessCustomException(ConstantManufacturer.manufacturerId, ConstantCategory.in_deleted);
        }

        return manufacturer;
    }

    @Override
    public void checkExistById(long id) {
        if (!productRepository.checkExistsById(id)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> create(ProductCreateRequest request) {

        ProductStatus status = request.getStatusEnum();

        if (productRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantProduct.name, ConstantProduct.name_exists);
        }

        Set<Integer> tagIds = new HashSet<>();
        Set<Integer> subcategoryIds = new HashSet<>();

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            tagIds = tagRepository.findAllByIds(request.getTags());
            if (!tagIds.containsAll(request.getTags())) {
                throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
            }
        }
        if (request.getSubcategories() != null && !request.getSubcategories().isEmpty()) {
            subcategoryIds = subCategoryRepository.findAllByIds(request.getSubcategories());
            if (!subcategoryIds.containsAll(request.getSubcategories())) {
                throw new BusinessCustomException(ConstantSubcategory.subcategories, ConstantSubcategory.does_not_exists);
            }
        }

        CategoryStatusDto category = getCategory(request.getCategoryName());

        ManufacturerStatusDto manufacturer = getManufacturer(request.getManufacturerId());

        String productCode = codeGenerator.generateProductCode(request.getCategoryName());

        BigDecimal finalPrice = BigDecimal.ZERO;

        if (request.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            finalPrice = calculateFinalPrice(request.getPrice(), request.getDiscountPercent());
        }

        Product product = new Product();
        product.setStatus(status);

        product.setName(TextUtils.capitalizeEachWord(request.getName()));
        product.setCode(productCode);
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(finalPrice);
        product.setCategoryId(category.getId());
        product.setManufacturerId(request.getManufacturerId());

        product.setConditions(request.getConditions().toUpperCase());


        product.setDimension(request.getDimension());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setWarranty(request.getWarranty());

        product.setThumbnail(request.getImages() != null && !request.getImages().isEmpty() ? request.getImages().get(0) : null);


        product = productRepository.save(product);

        productDocumentService.insertProduct(product);

        long productId = product.getId();

//        ProductStatistic statistic = new ProductStatistic();
//        statistic.setProductId(productId);
//        statistic.setViewCount(0);
//        statistic.setPurchaseCount(0);
//        statistic.setHasPromotion(request.isHasPromotion());
//
//        productStatisticRepository.save(statistic);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<ProductImage> productImages = request.getImages()
                    .stream().skip(1).map(i -> new ProductImage(productId, i)).collect(Collectors.toList());
            productImageRepository.saveAll(productImages);
        }

        Inventory inventory = new Inventory();

        inventory.setQuantity(request.isStock() ? request.getQuantity() : 0);
        inventory.setProductId(product.getId());

        inventory = inventoryRepository.save(inventory);

        if (!tagIds.isEmpty()) {
            List<ProductTag> productTags = tagIds
                    .stream()
                    .map(tagId -> new ProductTag(productId, tagId))
                    .toList();
            if (!productTags.isEmpty()) {
                productTagRepository.saveAll(productTags);
            }
        }

        if (!subcategoryIds.isEmpty()) {
            List<ProductSubcategory> productSubcategories = subcategoryIds
                    .stream()
                    .map(subcategory -> new ProductSubcategory(productId, subcategory))
                    .toList();
            if (!productSubcategories.isEmpty()) {
                productSubcategoryRepository.saveAll(productSubcategories);
            }
        }

        ProductDescription description = new ProductDescription();

        description.setProductId(productId);
        description.setDescription(request.getDescription() != null && !request.getDescription().isEmpty() ? request.getDescription() : null);

        productDescriptionRepository.save(description);

        updateProductCountCategory(category.getId(), true);
        updateProductCountManufacturer(request.getManufacturerId(), true);

        redisProductService.deleteProductListPattern(request.getCategoryName());

        ProductResponse response = getProductResponse(product, category.getName(), manufacturer.getName());

        return new BasicMessageResponse<>(201, ConstantProduct.success_create, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> update(long id, ProductUpdateRequest request) {

        ProductStatus status = request.getStatusEnum();

        Set<Integer> tagIds = new HashSet<>();
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            tagIds = tagRepository.findAllByIds(request.getTags());
            if (!tagIds.containsAll(request.getTags())) {
                throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
            }
        }

        Set<Integer> subcategoryIds = new HashSet<>();
        if (request.getSubcategories() != null && !request.getSubcategories().isEmpty()) {
            subcategoryIds = subCategoryRepository.findAllByIds(request.getSubcategories());
            if (!subcategoryIds.containsAll(request.getSubcategories())) {
                throw new BusinessCustomException(ConstantSubcategory.subcategories, ConstantSubcategory.does_not_exists);
            }
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        if (!product.getName().equals(request.getName()) && productRepository.existsByNameAndNotId(request.getName(), product.getId())) {
            throw new BusinessCustomException(ConstantProduct.name, ConstantProduct.name_exists);
        }

        ManufacturerStatusDto manufacturer = getManufacturer(request.getManufacturerId());

        if (manufacturer.getId() != product.getManufacturerId()) {
            updateProductCountManufacturer(product.getManufacturerId(), false);

            product.setManufacturerId(manufacturer.getId());
            updateProductCountManufacturer(manufacturer.getId(), true);
        }

        String categoryName = categoryRepository.getNameById(product.getCategoryId(), TaxonomyStatus.ACTIVE)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryId, ConstantCategory.does_not_exists));

        BigDecimal finalPrice = BigDecimal.ZERO;

        if (request.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            finalPrice = calculateFinalPrice(request.getPrice(), request.getDiscountPercent());
        }

//        ProductStatistic statistic = productStatisticRepository.findByProductId(product.getId());
//        statistic.setHasPromotion(request.isHasPromotion());
//        statistic.setUpdatedAt(LocalDateTime.now());
//
//        productStatisticRepository.save(statistic);

        product.setName(TextUtils.capitalizeEachWord(request.getName()));
        product.setWarranty(request.getWarranty());
        product.setWeight(request.getWeight());
        product.setDimension(request.getDimension());

        if (request.getImages() != null && !request.getImages().isEmpty() && product.getThumbnail().isEmpty()) {
            product.setThumbnail(request.getImages().get(0));
        }

        product.setStatus(status);
        product.setPrice(request.getPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setFinalPrice(finalPrice);
        product.setColor(request.getColor());

        product.setUpdatedAt(LocalDateTime.now());

        product = productRepository.save(product);
        long productId = product.getId();

        if (request.getImages() != null && !request.getImages().isEmpty()) {

            List<String> currentImageUrls = productImageRepository.findImageUrlsByProductId(productId);

            Set<String> newSet = new HashSet<>(request.getImages());

            productImageRepository.deleteByProductIdAndImageUrlNotIn(productId, new ArrayList<>(newSet));

            List<ProductImage> image = newSet.stream().skip(1)
                    .filter(i -> !currentImageUrls.contains(i))
                    .map(i -> new ProductImage(productId, i))
                    .toList();

            if (!image.isEmpty()) {
                productImageRepository.saveAll(image);
            }
        }

        if (!subcategoryIds.isEmpty()) {
            List<Integer> currentSubcategoryIds = productSubcategoryRepository.findSubcategoryIdsByProductId(productId);

            productSubcategoryRepository.deleteByProductIdAndSubcategoryIdNotIn(productId, new ArrayList<>(subcategoryIds));

            List<ProductSubcategory> newRelations = subcategoryIds.stream()
                    .filter(s -> !currentSubcategoryIds.contains(s))
                    .map(s -> new ProductSubcategory(productId, s))
                    .toList();

            if (!newRelations.isEmpty()) {
                productSubcategoryRepository.saveAll(newRelations);
            }
        }

        if (!tagIds.isEmpty()) {
            List<Integer> currentTagIds = productTagRepository.findTagIdsByProductId(productId);

            productTagRepository.deleteByProductIdAndTagIdNotIn(productId, new ArrayList<>(tagIds));

            List<ProductTag> newRelations = tagIds.stream()
                    .filter(t -> !currentTagIds.contains(t))
                    .map(t -> new ProductTag(productId, t))
                    .toList();

            if (!newRelations.isEmpty()) {
                productTagRepository.saveAll(newRelations);
            }
        }

        ProductDescription description = productDescriptionRepository.findByProductId(productId)
                .orElseGet(ProductDescription::new);

        boolean isNewDescription = description.getId() == null;

        if (isNewDescription) {
            description.setProductId(productId);
        }

        description.setDescription(request.getDescription() != null && !request.getDescription().isEmpty() ? request.getDescription() : null);

        productDescriptionRepository.save(description);

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(Inventory::new);

        boolean isNewInventory = inventory.getId() == null;

        inventory.setStock(request.isStock() && request.getQuantity() > 0);
        inventory.setQuantity(request.isStock() ? request.getQuantity() : 0);
        inventory.setUpdatedAt(LocalDateTime.now());

        if (isNewInventory) {
            inventory.setProductId(product.getId());
        }

        inventoryRepository.save(inventory);

        ProductResponse response = getProductResponse(product, categoryName, manufacturer.getName());

        String key = PRODUCT_DETAIL_KEY + productId;

        redisProductService.deleteProduct(key);
        redisProductService.deleteProductListPattern(categoryName);

        return new BasicMessageResponse<>(200, ConstantProduct.success_update, response);
    }

    @Override
    public BasicMessageResponse<ProductSubcategoryAndTagResponse> updateSubcategoriesAndTagIds(long id, ProductRelationUpdateRequest request) {

        checkExistById(id);

        List<TagDto> tags = tagRepository.findByIds(request.getTags());
        Set<Integer> tagIds = tags.stream().map(TagDto::getId).collect(Collectors.toSet());

        if (!tagIds.containsAll(request.getTags())) {
            throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
        }

//        List<SubcategoryDto> subcategories = subCategoryRepository.findByIds(request.getSubcategories());
//        Set<Integer> subcategoryIds = subcategories.stream().map(SubcategoryDto::getId).collect(Collectors.toSet());
//
//        if (!subcategoryIds.containsAll(request.getSubcategories())) {
//            throw new BusinessCustomException(ConstantSubcategory.subcategories, ConstantSubcategory.does_not_exists);
//        }

        if (request.getSubcategories() != null && !request.getSubcategories().isEmpty()) {

            List<Integer> currentSubcategoryIds = productSubcategoryRepository.findSubcategoryIdsByProductId(id);

            Set<Integer> newSubcategoryIds = new HashSet<>(request.getSubcategories());

            productSubcategoryRepository.deleteByProductIdAndSubcategoryIdNotIn(id, new ArrayList<>(newSubcategoryIds));

            List<ProductSubcategory> newRelations = newSubcategoryIds.stream()
                    .filter(s -> !currentSubcategoryIds.contains(s))
                    .map(s -> new ProductSubcategory(id, s))
                    .toList();

            if (!newRelations.isEmpty()) {
                productSubcategoryRepository.saveAll(newRelations);
            }
        }

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            List<Integer> currentTagIds = productTagRepository.findTagIdsByProductId(id);

            Set<Integer> newSet = new HashSet<>(request.getTags());

            productTagRepository.deleteByProductIdAndTagIdNotIn(id, new ArrayList<>(newSet));

            List<ProductTag> newRelations = newSet.stream()
                    .filter(t -> !currentTagIds.contains(t))
                    .map(t -> new ProductTag(id, t))
                    .toList();

            if (!newRelations.isEmpty()) {
                productTagRepository.saveAll(newRelations);
            }
        }

        ProductSubcategoryAndTagResponse response = new ProductSubcategoryAndTagResponse();
        response.setId(id);
        response.setTags(tags);
//        response.setSubcategories(subcategories);

        return new BasicMessageResponse<>(200, ConstantProduct.success_update_relation, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Long> delete(long id) {

        ProductQuantityResponse product = inventoryRepository.findQuantityByProductId(id);

        if (product.getQuantity() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.still_inventory);
        }

        productRepository.deleteById(product.getId());

        productDocumentService.deleteProduct(product.getId());
        return new BasicMessageResponse<>(200, ConstantProduct.success_delete, product.getId());
    }

    @Override
    public MessageResponse<List<ProductResponse>> fetchAll(String status, String category, String manufacturer, int page, int size, String sortBy, String order) {

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
                categoryName,
                manufacturerName,
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
    public BasicMessageResponse<List<ProductSubcategoryAndTagResponse>> fetchAllWithSubcategoriesAndTags() {

        List<ProductSubcategoryAndTagResponse> products = productRepository.fetchProductsWithoutSubcategoryAndTag();

        if (products.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, products);
        }

        List<Long> productIds = products.stream().map(ProductSubcategoryAndTagResponse::getId).toList();
        List<ProductTagDto> productTags = productTagRepository.findTagsByProductIds(productIds);
        List<ProductSubcategoryDto> productSubcategories = productSubcategoryRepository.findSubcategoriesByProductIds(productIds);

        Map<Long, List<SubcategoryBasicDto>> productSubcategoryMap = productSubcategories
                .stream()
                .collect(Collectors.groupingBy(ProductSubcategoryDto::getProductId, Collectors.mapping(s -> new SubcategoryBasicDto(s.getId(), s.getName()), Collectors.toList())));

        Map<Long, List<TagDto>> productTagMap = productTags.stream()
                .collect(Collectors.groupingBy(
                        ProductTagDto::getProductId,
                        Collectors.mapping(tag -> new TagDto(tag.getId(), tag.getTagName()), Collectors.toList())
                ));

        products.forEach(product -> {
            product.setTags(productTagMap.getOrDefault(product.getId(), Collections.emptyList()));
            product.setSubcategories(productSubcategoryMap.getOrDefault(product.getId(), Collections.emptyList()));
        });

        return new BasicMessageResponse<>(200, ConstantProduct.success_fetch_relation_products, products);
    }

    @Override
    public BasicMessageResponse<ProductUpdateResponse> getById(long productId) {

        ProductUpdateResponse response = productRepository.findToUpdate(productId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        List<TagDto> tagRespons = productTagRepository.getTagsByProductId(response.getId());
        List<SubcategoryBasicDto> subcategories = productSubcategoryRepository.findSubcategoriesByProductId(response.getId());
        List<String> images = productImageRepository.findImageUrlsByProductId(response.getId());

        response.setSubcategories(subcategories);
        response.setImages(images);
        response.setTags(tagRespons);

        return new BasicMessageResponse<>(200, ConstantProduct.success_find_by_id, response);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProductCountCategory(int categoryId, boolean isIncrease) {
        if (isIncrease) {
            categoryRepository.increaseProductCountById(categoryId);
        } else {
            categoryRepository.decreaseProductCountById(categoryId);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProductCountManufacturer(int manufacturerId, boolean isIncrease) {
        if (isIncrease) {
            manufacturerRepository.increaseProductCountById(manufacturerId);
        } else {
            manufacturerRepository.decreaseProductCountById(manufacturerId);
        }
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
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        ProductStatus status = product.getStatus();

        if (userDetail != null && !userDetail.getUser().isSuperAdmin() && status == ProductStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.not_super_admin);
        }

        if (status != ProductStatus.DELETED && status != ProductStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.not_removed);
        }

        productRepository.updateStatusById(id, ProductStatus.INACTIVE);

        String categoryName = categoryRepository.getNameById(product.getCategoryId(), TaxonomyStatus.ACTIVE)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryId, ConstantCategory.does_not_exists));

        String manufacturerName = manufacturerRepository.getNameById(product.getManufacturerId(), TaxonomyStatus.ACTIVE)
                .orElseThrow(() -> new BusinessCustomException(ConstantManufacturer.manufacturerId, ConstantCategory.does_not_exists));

        ProductResponse response = getProductResponse(product, categoryName, manufacturerName);

        return new BasicMessageResponse<>(200, ConstantProduct.success_restore, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductIdAndTagIdResponse> deleteRelationByProductIdAndTagId(long productId, int tagId) {
        ProductIdAndTagIdResponse relations = productTagRepository.findRelationByProductAndTagId(productId, tagId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.tag_relation_does_not_exists));

        productTagRepository.deleteByProductIdAndTagId(relations.getProductId(), relations.getTagId());

        return new BasicMessageResponse<>(200, ConstantProduct.success_delete_tag_relation, relations);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductSubcategoryIDResponse> deleteRelationByProductIdAndSubcategoryId(long productId, int subcategoryId) {
        ProductSubcategoryIDResponse relations = productSubcategoryRepository.findRelationByProductAndSubcategoryId(productId, subcategoryId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.subcategory_relation_does_not_exists));

        productSubcategoryRepository.deleteByProductIdAndSubcategoryId(relations.getProductId(), relations.getSubcategoryId());

        return new BasicMessageResponse<>(200, ConstantProduct.success_delete_subcategory_relation, relations);
    }


}
