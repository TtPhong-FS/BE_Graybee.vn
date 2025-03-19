package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.PcDetail;
import vn.graybee.projections.collections.PcDetailProjection;
import vn.graybee.repositories.collections.PcRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.PcDetailCreateRequest;
import vn.graybee.response.publics.collections.PcSummaryResponse;
import vn.graybee.services.products.ProductDetailService;
import vn.graybee.utils.HardwareExtractor;
import vn.graybee.validation.CategoryValidation;

import java.util.List;

@Service
public class PcServiceImp implements ProductDetailService {

    private final CategoryValidation categoryValidation;

    private final EntityManager entityManager;

    private final ProductRepository productRepository;

    private final PcRepository pcRepository;

    private final HardwareExtractor extractor;

    public PcServiceImp(CategoryValidation categoryValidation, EntityManager entityManager, ProductRepository productRepository, PcRepository pcRepository, HardwareExtractor extractor) {
        this.categoryValidation = categoryValidation;
        this.entityManager = entityManager;
        this.productRepository = productRepository;
        this.pcRepository = pcRepository;
        this.extractor = extractor;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String detailType, DetailDtoRequest request) {

        if (!detailType.equalsIgnoreCase("pc")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_PC_TYPE);
        }
        PcDetailCreateRequest pcDto = (PcDetailCreateRequest) request;
        PcDetail pc = new PcDetail(
                productId,
                pcDto.getDemand(),
                pcDto.getCpu(),
                pcDto.getMotherboard(),
                pcDto.getRam(),
                pcDto.getSsd(),
                pcDto.getHdd(),
                pcDto.getOperatingSystem(),
                pcDto.getVga(),
                pcDto.getInputPort(),
                pcDto.getOutputPort(),
                pcDto.getCooling(),
                pcDto.getPsu(),
                pcDto.getCaseName()
        );
        entityManager.persist(pc);
    }

    @Override
    public String getDetailType() {
        return "PcDetailCreateRequest";
    }

    public ResponseEntity<BasicMessageResponse<List<PcSummaryResponse>>> findByCategoryName_PUBLIC(String categoryName) {

//        categoryValidation.validateNameExists(categoryName);
//
//        List<ProductBasicResponse> products = productRepository.fetchByCategoryName(categoryName);
//
//        if (products.isEmpty()) {
//            return ResponseEntity.ok(new BasicMessageResponse<>(204, "Danh mục chưa có sản phẩm nào!", null));
//        }
//
//        List<PcSummaryResponse> pcSummaryResponses = new ArrayList<>();
//
//        for (ProductBasicResponse product : products) {
//            PcResponse pcResponse = pcRepository.findByProductId(product.getId())
//                    .orElseThrow(() -> new CustomNotFoundException(ConstantProduct.GENERAL, ConstantProduct.PRODUCT_DOES_NOT_EXISTS));
//
//            PcSummaryResponse response = new PcSummaryResponse(
//                    product.getId(),
//                    product.getName(),
//                    product.getPrice(),
//                    product.getFinalPrice(),
//                    product.getThumbnail(),
//                    pcResponse,
//                    extractor
//            );
//            pcSummaryResponses.add(response);
//        }
//
//        return ResponseEntity.ok(new BasicMessageResponse<>(200, "Lấy danh sách PC thành công!", pcSummaryResponses));

        return null;

    }


    public BasicMessageResponse<List<PcDetailProjection>> fetchAll() {
        List<PcDetailProjection> pcs = pcRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách PC: ", pcs);
    }


}
