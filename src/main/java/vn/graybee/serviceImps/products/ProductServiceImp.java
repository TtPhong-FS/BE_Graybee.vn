package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.categories.ConstantCategory;
import vn.graybee.constants.products.ConstantProduct;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.models.products.Inventory;
import vn.graybee.models.products.Product;
import vn.graybee.models.products.ProductDescription;
import vn.graybee.models.products.ProductImage;
import vn.graybee.models.products.ProductTag;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.repositories.products.InventoryRepository;
import vn.graybee.repositories.products.ProductDescriptionRepository;
import vn.graybee.repositories.products.ProductImageRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.repositories.products.ProductTagRepository;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.products.ProductDto;
import vn.graybee.response.products.ProductResponse;
import vn.graybee.response.products.ProductStatusResponse;
import vn.graybee.response.products.ProductTagDto;
import vn.graybee.response.publics.ProductBasicResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;
import vn.graybee.services.products.ProductService;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.CategoryValidation;
import vn.graybee.validation.ManufactureValidation;
import vn.graybee.validation.ProductValidation;
import vn.graybee.validation.TagValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    private final InventoryRepository inventoryRepository;

    private final TagValidation tagValidation;

    private final ProductTagRepository ptRepository;

    private final ProductCodeGenerator codeGenerator;

    private final CategoryValidation categoryValidation;

    private final ManufactureValidation manufactureValidation;

    private final ProductValidation productValidation;

    private final ProductRepository productRepository;

    private final ProductDescriptionRepository pdRepository;

    private final ProductImageRepository piRepository;


    private final CategoryRepository categoryRepository;

    private final ManufacturerRepository manufacturerRepository;

    public ProductServiceImp(InventoryRepository inventoryRepository, TagValidation tagValidation, ProductTagRepository ptRepository, ProductCodeGenerator codeGenerator, CategoryValidation categoryValidation, ManufactureValidation manufactureValidation, ProductValidation productValidation, ProductRepository productRepository, ProductDescriptionRepository pdRepository, ProductImageRepository piRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository) {
        this.inventoryRepository = inventoryRepository;
        this.tagValidation = tagValidation;
        this.ptRepository = ptRepository;
        this.codeGenerator = codeGenerator;
        this.categoryValidation = categoryValidation;
        this.manufactureValidation = manufactureValidation;
        this.productValidation = productValidation;
        this.productRepository = productRepository;
        this.pdRepository = pdRepository;
        this.piRepository = piRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> create(ProductCreateRequest request) {

        productValidation.validateNameExists(request.getProductName());
        int category = categoryValidation.getIdByName(request.getCategoryName());
        int manufacturer = manufactureValidation.getIdByName(request.getManufacturerName());

        double finalPrice = 0;

        if (request.getPrice() != 0) {
            finalPrice = calculateFinalPrice(request.getPrice(), request.getDiscountPercent());
        }

        Product product = new Product(
                TextUtils.capitalizeEachWord(request.getProductName()),
                request.getPrice(),
                request.getDiscountPercent()
        );

        product.setStock(request.isInStock());
        product.setFinalPrice(finalPrice);
        product.setConditions(request.getConditions().toUpperCase());
        product.setDimension(request.getDimension());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setWarranty(request.getWarranty());

        if (request.getStatus() == null || request.getStatus().isEmpty()) {
            product.setStatus("DRAFT");
        } else {
            product.setStatus(request.getStatus());
        }

        product.setCategoryId(category);
        product.setManufacturerId(manufacturer);

        String productCode = codeGenerator.generateProductCode(request.getCategoryName());

        product.setProductCode(productCode);

        Product savedProduct = productRepository.save(product);

        if (request.isInStock()) {
            Inventory inventory = new Inventory();
            inventory.setQuantity(request.getQuantity());
            inventory.setProductId(savedProduct.getId());

            inventoryRepository.save(inventory);
        } else {
            Inventory inventory = new Inventory();
            inventory.setQuantity(0);
            inventory.setProductId(savedProduct.getId());

            inventoryRepository.save(inventory);
        }

        List<String> tagNames = request.getTags();
        if (!request.getTags().isEmpty() || request.getTags() != null) {

            List<Integer> tagIds = tagValidation.validateByIds(tagNames);

            List<ProductTag> productTags = tagIds.stream().map(t -> new ProductTag(savedProduct.getId(), t, "ACTIVE")).collect(Collectors.toList());

            ptRepository.saveAll(productTags);
        }

        if (request.getDescription() != null) {
            ProductDescription description = new ProductDescription(request.getDescription());
            description.setProductId(savedProduct.getId());
            pdRepository.save(description);
        } else {
            ProductDescription description = new ProductDescription(null);
            description.setProductId(savedProduct.getId());
            pdRepository.save(description);
        }

        if (!request.getImages().isEmpty()) {
            List<String> images = request.getImages();

            product.setThumbnail(images.get(0));

            List<ProductImage> productImages = images
                    .stream().skip(1).map(i -> new ProductImage(savedProduct.getId(), i)).collect(Collectors.toList());

            piRepository.saveAll(productImages);

        }

        updateProductCountCategory(category, true);

        updateProductCountManufacturer(manufacturer, true);

        ProductResponse productResponse = new ProductResponse(
                savedProduct.getCreatedAt(),
                savedProduct.getUpdatedAt(),
                savedProduct,
                request.getCategoryName().toUpperCase(),
                request.getManufacturerName().toUpperCase(),
                tagNames,
                request.getQuantity()
        );

        return new BasicMessageResponse<>(201, "Tạo sản phẩm thành công", productResponse);
    }

    @Override
    public BasicMessageResponse<ProductResponse> update(long productId, ProductUpdateRequest request) {
        System.out.println("Update product");
        return null;
    }

    @Override
    @Transactional
    public BasicMessageResponse<List<ProductResponse>> getProductsForAdmin() {

        List<ProductResponse> products = productRepository.findProductsWithoutTags();

        List<Long> productIds = products.stream()
                .map(ProductResponse::getId)
                .toList();

        if (productIds.isEmpty()) {
            return new BasicMessageResponse<>(200, "Danh sách sản phẩm", products);
        }

        List<ProductTagDto> productTags = ptRepository.findTagsByProductIds(productIds);

        Map<Long, List<String>> productTagsMap = productTags.stream()
                .collect(Collectors.groupingBy(
                        ProductTagDto::getProductId,
                        Collectors.mapping(ProductTagDto::getTagName, Collectors.toList())
                ));

        for (ProductResponse product : products) {
            product.setTagNames(productTagsMap.getOrDefault(product.getId(), new ArrayList<>()));
        }

        return new BasicMessageResponse<>(200, "Danh sách sản phẩm", products);
    }

    @Override
    public BasicMessageResponse<ProductDto> findById(long productId) {

        ProductDto response = productRepository.findById_ADMIN(productId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantProduct.PRODUCT, ConstantProduct.PRODUCT_DOES_NOT_EXISTS));

        List<ProductTagDto> tagDto = ptRepository.findTagsByProductId(response.getId());

        Map<Long, List<String>> productTagsMap = tagDto.stream()
                .collect(Collectors.groupingBy(
                        ProductTagDto::getProductId,
                        Collectors.mapping(ProductTagDto::getTagName, Collectors.toList())
                ));

        response.setTagNames(productTagsMap.getOrDefault(response.getId(), new ArrayList<>()));

        return new BasicMessageResponse<>(200, "Tìm sản phẩm thành công!", response);
    }

    public double calculateFinalPrice(double price, int discount) {
        return price - (price * discount / 100);
    }


    @Override
    public PcSummaryResponse findPCByCategoryName_PUBLIC(String categoryName) {
//        ProductBasicResponse product = productRepository.findByCategoryName_PUBLIC(categoryName)
//                .orElseThrow(() -> new CustomNotFoundException(ConstantProduct.GENERAL, ConstantProduct.PRODUCT_DOES_NOT_EXISTS));


        return null;
    }

    @Override
    public void updateProductCountManufacturer(int ManufacturerId, boolean isIncrease) {
        Manufacturer manufacturer = manufacturerRepository.findById(ManufacturerId)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.MANUFACTURER_NAME
                        , ConstantCategory.MANUFACTURER_DOES_NOT_EXIST));
        if (isIncrease) {
            manufacturer.increaseProductCount();
        } else {
            manufacturer.decreaseProductCount();
        }

        manufacturerRepository.save(manufacturer);

    }

    @Override
    public BasicMessageResponse<List<ProductBasicResponse>> fetchByCategoryName(String categoryName) {

        categoryValidation.validateNameExists(categoryName);

        List<ProductBasicResponse> products = productRepository.fetchByCategoryName(categoryName);


        return new BasicMessageResponse<>(200, "Lấy danh sách sản phẩm theo loại " + categoryName + " Thành công", products);
    }

    @Override
    public BasicMessageResponse<ProductStatusResponse> updateStatus(long id, String status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantProduct.GENERAL, ConstantProduct.PRODUCT_DOES_NOT_EXISTS));

        product.setStatus(status);

        productRepository.save(product);

        ProductStatusResponse response = new ProductStatusResponse(product.getId(), product.getStatus());

        return new BasicMessageResponse<>(200, "Cập nhật trạng thái thành công!", response);
    }

    @Override
    public void updateProductCountCategory(int CategoryId, boolean isIncrease) {
        Category category = categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.CATEGORY_NAME, ConstantCategory.CATEGORY_DOES_NOT_EXIST));

        if (isIncrease) {
            category.increaseProductCount();
        } else {
            category.decreaseProductCount();
        }

        categoryRepository.save(category);
    }

}
