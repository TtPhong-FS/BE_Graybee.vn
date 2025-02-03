package vn.graybee.serviceImps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Category;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.ProductRepository;
import vn.graybee.requests.ProductCreateRequest;
import vn.graybee.response.ProductResponseByCategoryName;
import vn.graybee.services.ProductDetailFactory;
import vn.graybee.services.business.ProductDetailService;
import vn.graybee.services.business.ProductService;
import vn.graybee.validation.CategoryValidation;
import vn.graybee.validation.ManufactureValidation;
import vn.graybee.validation.ProductValidation;

import java.util.List;
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
    public Product createProduct(ProductCreateRequest request) {

        productValidation.ensureProductNameBeforeCreate(request.getProductName());
        productValidation.checkProductModelExists(request.getModel());
        Category category = categoryValidation.ensureCategoryBeforeCreateProduct(request.getCategoryId());
        Manufacturer manufacturer = manufactureValidation.ensureManufactureBeforeCreateProduct(request.getManufacturerId());

        logger.info("Creating product with model: " + request.getModel() + ", name: " + request.getProductName());

        Product product = new Product(
                request.getModel(),
                request.getProductName(),
                request.getConditions().toUpperCase(),
                request.getWarranty(),
                request.getWeight(),
                request.getDimension(),
                request.getPrice(),
                request.getColor(),
                request.getDescription(),
                request.getThumbnail(),
                request.getIsDelete()
        );
        product.setCategory(category);
        product.setManufacturer(manufacturer);
        
        Product savedProduct = productRepository.save(product);
        logger.info("Creating product successfully with ID: {}", savedProduct.getId());

        if (request.getDetail() != null) {
            String detailKey = request.getDetail().getClass().getSimpleName();
            System.out.println(detailKey);
            ProductDetailService service = productDetailFactory.getService(detailKey);
            System.out.println(service);
            if (service != null) {
                service.saveDetail(savedProduct, request.getDetail());
            }
            throw new BusinessCustomException(ErrorGeneralConstants.DETAIL_TYPE_ERROR, ErrorGeneralConstants.MISSING_DETAIL_TYPE);
        }

        return savedProduct;
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductResponseByCategoryName> findProductsByCategoryName(String name) {
        return null;
    }

}
