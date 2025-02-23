package vn.graybee.serviceImps.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ErrorGeneralConstants;
import vn.graybee.constants.products.ErrorProductConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.response.DetailDtoResponse;
import vn.graybee.response.products.ProductResponse;
import vn.graybee.response.publics.ProductBasicResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;
import vn.graybee.services.ProductDetailFactory;
import vn.graybee.services.products.ProductDetailService;
import vn.graybee.services.products.ProductService;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.CategoryValidation;
import vn.graybee.validation.ManufactureValidation;
import vn.graybee.validation.ProductValidation;

import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final CategoryValidation categoryValidation;

    private final ManufactureValidation manufactureValidation;

    private final ProductValidation productValidation;

    private final ProductRepository productRepository;

    private final ProductDetailFactory productDetailFactory;

    public ProductServiceImp(CategoryValidation categoryValidation, ManufactureValidation manufactureValidation, ProductValidation productValidation, ProductRepository productRepository, ProductDetailFactory productDetailFactory) {
        this.categoryValidation = categoryValidation;
        this.manufactureValidation = manufactureValidation;
        this.productValidation = productValidation;
        this.productRepository = productRepository;
        this.productDetailFactory = productDetailFactory;
    }

    @Override
    @Transactional
    public BasicMessageResponse<ProductResponse> createProduct(ProductCreateRequest request) {

        productValidation.validateModelExists(request.getModel());
        productValidation.validateNameExists(request.getName());
        Category category = categoryValidation.validateCategoryExistsByName(request.getCategoryName());
        Manufacturer manufacturer = manufactureValidation.findToCreateProduct(request.getManufacturerName());

        logger.info("Creating product with model: " + request.getModel() + ", name: " + request.getName());

        Product product = new Product(
                TextUtils.capitalize(request.getModel()),
                TextUtils.capitalizeEachWord(request.getName()),
                request.getWarranty(),
                request.getWeight(),
                request.getDimension(),
                request.getPrice(),
                request.getDiscount_percent(),
                request.getNewPrice(),
                request.getColor(),
                request.getDescription(),
                request.getThumbnail()
        );
        product.setConditions(request.getConditions().getDescription());
        product.setDeleted(false);
        product.setCategory(category);
        product.setManufacturer(manufacturer);

        Product savedProduct = productRepository.save(product);

        logger.info("Creating product successfully with ID: {}", savedProduct.getId());

        DetailDtoResponse detailDto = null;
        if (request.getDetail() != null) {
            String detailKey = request.getDetail().getClass().getSimpleName();
            System.out.println(detailKey);
            ProductDetailService service = productDetailFactory.getService(detailKey);
            System.out.println(service);
            if (service != null) {
                service.saveDetail(savedProduct, request.getDetail());

                detailDto = service.getDetail(savedProduct);
            } else {
                throw new BusinessCustomException(ErrorGeneralConstants.DETAIL_TYPE_ERROR, ErrorGeneralConstants.MISSING_DETAIL_TYPE);
            }
        }

        ProductResponse productResponse = new ProductResponse(savedProduct, detailDto);

        return new BasicMessageResponse<>(201, "Create Product success ", productResponse);
    }

    @Override
    public Optional<Product> getById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public PcSummaryResponse findPCByCategoryName_PUBLIC(String categoryName) {
        ProductBasicResponse product = productRepository.findByCategoryName_PUBLIC(categoryName)
                .orElseThrow(() -> new CustomNotFoundException(ErrorProductConstants.GENERAL, ErrorProductConstants.PRODUCT_DOES_NOT_EXISTS));


        return null;
    }

}
