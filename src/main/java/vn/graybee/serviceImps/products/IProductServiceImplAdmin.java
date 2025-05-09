package vn.graybee.serviceImps.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantManufacturer;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.constants.ConstantSubcategory;
import vn.graybee.constants.ConstantTag;
import vn.graybee.enums.ProductStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.messages.other.PaginationInfo;
import vn.graybee.messages.other.SortInfo;
import vn.graybee.models.directories.Category;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.models.products.Inventory;
import vn.graybee.models.products.Product;
import vn.graybee.models.products.ProductDescription;
import vn.graybee.models.products.ProductImage;
import vn.graybee.models.products.ProductStatistic;
import vn.graybee.models.products.ProductSubcategory;
import vn.graybee.models.products.ProductTag;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.repositories.products.InventoryRepository;
import vn.graybee.repositories.products.ProductDescriptionRepository;
import vn.graybee.repositories.products.ProductImageRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.repositories.products.ProductStatisticRepository;
import vn.graybee.repositories.products.ProductSubcategoryRepository;
import vn.graybee.repositories.products.ProductTagRepository;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductRelationUpdateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.admin.directories.subcate.SubcateDto;
import vn.graybee.response.admin.directories.tag.TagResponse;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductQuantityResponse;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryDto;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;
import vn.graybee.response.admin.products.ProductTagDto;
import vn.graybee.response.admin.products.ProductUpdateResponse;
import vn.graybee.services.products.IProductServiceAdmin;
import vn.graybee.services.products.RedisProductService;
import vn.graybee.utils.TextUtils;

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
public class IProductServiceImplAdmin implements IProductServiceAdmin {

    private static final String PRODUCT_DETAIL_KEY = "product:detail:";

    private final ProductDocumentService productDocumentService;

    private final ProductDescriptionRepository productDescriptionRepository;

    private final TagRepository tagRepository;

    private final SubCategoryRepository subCategoryRepository;

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

    public IProductServiceImplAdmin(ProductDocumentService productDocumentService, ProductDescriptionRepository productDescriptionRepository, TagRepository tagRepository, SubCategoryRepository subCategoryRepository, ProductStatisticRepository productStatisticRepository, ProductSubcategoryRepository productSubcategoryRepository, InventoryRepository inventoryRepository, ProductTagRepository productTagRepository, ProductCodeGenerator codeGenerator, ProductRepository productRepository, ProductImageRepository productImageRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository, RedisProductService redisProductService) {
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

    public ProductResponse getProductResponse(Product product, String categoryName, String manufacturerName, int quantity, Boolean isStock) {
        return new ProductResponse(
                product,
                categoryName,
                manufacturerName,
                quantity,
                isStock

        );
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

        Integer categoryId = categoryRepository.findIdByName(request.getCategoryName())
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryName, ConstantCategory.does_not_exists));

        String manufacturerName = manufacturerRepository.getNameById(request.getManufacturerId())
                .orElseThrow(() -> new BusinessCustomException(ConstantManufacturer.manufacturerId, ConstantCategory.does_not_exists));

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
        product.setCategoryId(categoryId);
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

        ProductStatistic statistic = new ProductStatistic();
        statistic.setProductId(productId);
        statistic.setViewCount(0);
        statistic.setPurchaseCount(0);
        statistic.setHasPromotion(request.isHasPromotion());

        productStatisticRepository.save(statistic);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<ProductImage> productImages = request.getImages()
                    .stream().skip(1).map(i -> new ProductImage(productId, i)).collect(Collectors.toList());
            productImageRepository.saveAll(productImages);
        }

        Inventory inventory = new Inventory();

        inventory.setStock(request.isStock() && request.getQuantity() > 0);
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

        updateProductCountCategory(categoryId, true);
        updateProductCountManufacturer(request.getManufacturerId(), true);

        redisProductService.deleteProductListPattern(request.getCategoryName());

        ProductResponse response = getProductResponse(product, request.getCategoryName(), manufacturerName, inventory.getQuantity(), inventory.getStock());


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

        if (request.getManufacturerId() != 0 && request.getManufacturerId() != product.getManufacturerId()) {
            if (product.getManufacturerId() != 0) {
                updateProductCountManufacturer(product.getManufacturerId(), false);
            }
            product.setManufacturerId(request.getManufacturerId());
            updateProductCountManufacturer(request.getManufacturerId(), true);
        }

        String categoryName = categoryRepository.getNameById(product.getCategoryId())
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryId, ConstantCategory.does_not_exists));

        String manufacturerName = manufacturerRepository.getNameById(request.getManufacturerId())
                .orElseThrow(() -> new BusinessCustomException(ConstantManufacturer.manufacturerId, ConstantCategory.does_not_exists));

        BigDecimal finalPrice = BigDecimal.ZERO;

        if (request.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            finalPrice = calculateFinalPrice(request.getPrice(), request.getDiscountPercent());
        }

        ProductStatistic statistic = productStatisticRepository.findByProductId(product.getId());
        statistic.setHasPromotion(request.isHasPromotion());
        statistic.setUpdatedAt(LocalDateTime.now());

        productStatisticRepository.save(statistic);

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

        inventory = inventoryRepository.save(inventory);

        ProductResponse response = getProductResponse(product, categoryName, manufacturerName, inventory.getQuantity(), inventory.getStock());

        String key = PRODUCT_DETAIL_KEY + productId;

        redisProductService.deleteProduct(key);
        redisProductService.deleteProductListPattern(categoryName);

        return new BasicMessageResponse<>(200, ConstantProduct.success_update, response);
    }

    @Override
    public BasicMessageResponse<ProductSubcategoryAndTagResponse> updateSubcategoriesAndTagIds(long id, ProductRelationUpdateRequest request) {

        checkExistById(id);

        List<TagResponse> tags = tagRepository.findByIds(request.getTags());
        Set<Integer> tagIds = tags.stream().map(TagResponse::getId).collect(Collectors.toSet());

        if (!tagIds.containsAll(request.getTags())) {
            throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
        }

        List<SubcateDto> subcategories = subCategoryRepository.findByIds(request.getSubcategories());
        Set<Integer> subcategoryIds = subcategories.stream().map(SubcateDto::getId).collect(Collectors.toSet());

        if (!subcategoryIds.containsAll(request.getSubcategories())) {
            throw new BusinessCustomException(ConstantSubcategory.subcategories, ConstantSubcategory.does_not_exists);
        }

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
        response.setSubcategories(subcategories);

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

        Map<Long, List<SubcateDto>> productSubcategoryMap = productSubcategories
                .stream()
                .collect(Collectors.groupingBy(ProductSubcategoryDto::getProductId, Collectors.mapping(s -> new SubcateDto(s.getId(), s.getName()), Collectors.toList())));

        Map<Long, List<TagResponse>> productTagMap = productTags.stream()
                .collect(Collectors.groupingBy(
                        ProductTagDto::getProductId,
                        Collectors.mapping(tag -> new TagResponse(tag.getId(), tag.getTagName()), Collectors.toList())
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

        List<TagResponse> tagResponses = productTagRepository.getTagsByProductId(response.getId());
        List<SubcateDto> subcategories = productSubcategoryRepository.findSubcategoriesByProductId(response.getId());
        List<String> images = productImageRepository.findImageUrlsByProductId(response.getId());

        response.setSubcategories(subcategories);
        response.setImages(images);
        response.setTags(tagResponses);

        return new BasicMessageResponse<>(200, ConstantProduct.success_find_by_id, response);
    }

    public BigDecimal calculateFinalPrice(BigDecimal price, int discount) {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
        );
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProductCountCategory(int CategoryId, boolean isIncrease) {
        Category category = categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCategory.does_not_exists));

        if (isIncrease) {
            category.increaseProductCount();
        } else {
            category.decreaseProductCount();
        }

        categoryRepository.save(category);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProductCountManufacturer(int ManufacturerId, boolean isIncrease) {
        Manufacturer manufacturer = manufacturerRepository.findById(ManufacturerId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general
                        , ConstantManufacturer.does_not_exists));
        if (isIncrease) {
            manufacturer.increaseProductCount();
        } else {
            manufacturer.decreaseProductCount();
        }

        manufacturerRepository.save(manufacturer);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductStatusResponse> updateStatus(long id, ProductStatus status) {

        checkExistById(id);

        ProductStatus currentStatus = productRepository.findStatusById(id);

        if (currentStatus == ProductStatus.REMOVED && status == ProductStatus.DELETED) {

            productRepository.updateStatusById(id, status);

            ProductStatusResponse response = new ProductStatusResponse(id, status, LocalDateTime.now());

            return new BasicMessageResponse<>(200, ConstantGeneral.success_delete, response);

        } else if (currentStatus == ProductStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantProduct.removed);

        } else if (currentStatus == ProductStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantProduct.soft_delete);

        } else if (currentStatus == ProductStatus.PUBLISHED && (status == ProductStatus.DRAFT || status == ProductStatus.REMOVED || status == ProductStatus.COMING_SOON || status == ProductStatus.DELETED)) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantProduct.published + status.getDisplayName());
        }

        if ((currentStatus == ProductStatus.PENDING || currentStatus == ProductStatus.COMING_SOON) && status == ProductStatus.PUBLISHED) {

            productRepository.updateStatusById(id, status);

            ProductStatusResponse response = new ProductStatusResponse(id, status, LocalDateTime.now());

            return new BasicMessageResponse<>(200, ConstantGeneral.success_published, response);
        } else if (status == ProductStatus.PUBLISHED) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantProduct.pending_to_published);
        }

        productRepository.updateStatusById(id, status);

        ProductStatusResponse response = new ProductStatusResponse(id, status, LocalDateTime.now());

        return new BasicMessageResponse<>(200, ConstantGeneral.success_update_status, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> restoreProduct(long id, UserPrincipal userPrincipal) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        ProductStatus status = product.getStatus();

        if (userPrincipal != null && !userPrincipal.getUser().isSuperAdmin() && status == ProductStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.not_super_admin);
        }

        if (status != ProductStatus.DELETED && status != ProductStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.not_removed);
        }

        productRepository.updateStatusById(id, ProductStatus.INACTIVE);

        String categoryName = categoryRepository.getNameById(product.getCategoryId())
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryId, ConstantCategory.does_not_exists));

        String manufacturerName = manufacturerRepository.getNameById(product.getManufacturerId())
                .orElseThrow(() -> new BusinessCustomException(ConstantManufacturer.manufacturerId, ConstantCategory.does_not_exists));

        ProductResponse response = getProductResponse(product, categoryName, manufacturerName, 0, false);

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
