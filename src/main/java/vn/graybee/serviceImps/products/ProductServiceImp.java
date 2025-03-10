package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.categories.ConstantCategory;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.enums.GeneralStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.models.products.Product;
import vn.graybee.projections.product.ProductProjection;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.response.products.ProductResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;
import vn.graybee.services.ProductDetailFactory;
import vn.graybee.services.products.ProductDetailService;
import vn.graybee.services.products.ProductService;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.CategoryValidation;
import vn.graybee.validation.ManufactureValidation;
import vn.graybee.validation.ProductValidation;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final CategoryValidation categoryValidation;

    private final ManufactureValidation manufactureValidation;

    private final ProductValidation productValidation;

    private final ProductRepository productRepository;

    private final ProductDetailFactory productDetailFactory;

    private final CategoryRepository categoryRepository;

    private final ManufacturerRepository manufacturerRepository;

    public ProductServiceImp(CategoryValidation categoryValidation, ManufactureValidation manufactureValidation, ProductValidation productValidation, ProductRepository productRepository, ProductDetailFactory productDetailFactory, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository) {
        this.categoryValidation = categoryValidation;
        this.manufactureValidation = manufactureValidation;
        this.productValidation = productValidation;
        this.productRepository = productRepository;
        this.productDetailFactory = productDetailFactory;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> create(ProductCreateRequest request) {

        String detailKey = request.getDetail().getClass().getSimpleName();
        ProductDetailService service = productDetailFactory.getService(detailKey);

        if (service == null) {
            throw new BusinessCustomException(ConstantCollections.DETAIL_TYPE_ERROR, ConstantCollections.MISSING_DETAIL_TYPE);
        }

        productValidation.validateNameExists(request.getName());

        int category = categoryValidation.getIdByName(request.getCategoryName());
        int manufacturer = manufactureValidation.getIdByName(request.getManufacturerName());

        float newPrice = 0;

        if (request.getPrice() != 0) {
            float price = request.getPrice();
            int discount = request.getDiscount_percent();

            newPrice = price - (price * discount / 100);

        }

        Product product = new Product(
                TextUtils.capitalizeEachWord(request.getName()),
                request.getWarranty(),
                request.getWeight(),
                request.getDimension(),
                request.getPrice(),
                request.getDiscount_percent(),
                request.getColor(),
                request.getDescription(),
                request.getThumbnail()
        );
        product.setNewPrice(newPrice);
        product.setConditions(request.getConditions().toUpperCase());
        product.setStatus(GeneralStatus.ACTIVE);
        product.setCategoryId(category);
        product.setManufacturerId(manufacturer);

        Product savedProduct = productRepository.save(product);

        if (request.getDetail() != null) {
            long productId = savedProduct.getId();

            service.saveDetail(productId, request.getCategoryName(), request.getDetail());

        }

        updateProductCountCategory(category, true);

        updateProductCountManufacturer(manufacturer, true);


        ProductResponse productResponse = new ProductResponse(
                savedProduct.getCreatedAt(),
                savedProduct.getUpdatedAt(),
                savedProduct.getId(),
                savedProduct.getCategoryId(),
                savedProduct.getManufacturerId(),
                savedProduct.getName(),
                savedProduct.getWarranty(),
                savedProduct.getWeight(),
                savedProduct.getDimension(),
                savedProduct.getPrice(),
                savedProduct.getDiscount_percent(),
                savedProduct.getNewPrice(),
                savedProduct.getColor(),
                savedProduct.getDescription(),
                savedProduct.getThumbnail(),
                savedProduct.getConditions()
        );

        return new BasicMessageResponse<>(201, "Tạo sản phẩm thành công", productResponse);
    }

    @Override
    @Transactional
    public BasicMessageResponse<List<ProductProjection>> fetchAll() {
        List<ProductProjection> products = productRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách sản phẩm", products);
    }

    @Override
    public Optional<Product> getById(long id) {
        return productRepository.findById(id);
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
