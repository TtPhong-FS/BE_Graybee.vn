package vn.graybee.serviceImps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.graybee.models.business.Category;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.ProductRepository;
import vn.graybee.requests.product.ProductCreateRequest;
import vn.graybee.response.ProductResponseByCategoryName;
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

    public ProductServiceImp(CategoryValidation categoryValidation, ManufactureValidation manufactureValidation, ProductValidation productValidation, ProductRepository productRepository) {
        this.categoryValidation = categoryValidation;
        this.manufactureValidation = manufactureValidation;
        this.productValidation = productValidation;
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductCreateRequest request) {

        productValidation.ensureProductNameBeforeCreate(request.getName());
        productValidation.checkProductModelExists(request.getModel());
        Category category = categoryValidation.ensureCategoryBeforeCreateProduct(request.getCategoryId());
        Manufacturer manufacturer = manufactureValidation.ensureManufactureBeforeCreateProduct(request.getManufacturerId());

        logger.info("Creating product with model: " + request.getModel() + "name: " + request.getName());

        Product product = new Product(
                request.getModel(),
                request.getProductType().toUpperCase(),
                request.getName(),
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
        return savedProduct;
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductResponseByCategoryName> findProductsByCategoryName(String name) {
        return productRepository.findProductByCategory_Name(name);
    }

}
