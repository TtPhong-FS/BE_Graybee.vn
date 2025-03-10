package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.MotherBoardDetail;
import vn.graybee.projections.collections.MotherboardDetailProjection;
import vn.graybee.repositories.collections.MotherboardRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.MotherboardDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class MotherboardServiceImp implements ProductDetailService {

    private final EntityManager entityManager;

    private final MotherboardRepository motherboardRepository;

    public MotherboardServiceImp(EntityManager entityManager, MotherboardRepository motherboardRepository) {
        this.entityManager = entityManager;
        this.motherboardRepository = motherboardRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String detailType, DetailDtoRequest request) {
        if (!detailType.equalsIgnoreCase("motherboard")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_MOTHERBOARD_TYPE);
        }
        MotherboardDetailCreateRequest motherboardDto = (MotherboardDetailCreateRequest) request;
        MotherBoardDetail motherboard = new MotherBoardDetail(
                productId,
                motherboardDto.getChipset(),
                motherboardDto.getSocket(),
                motherboardDto.getCpuSupport(),
                motherboardDto.getMemorySupport(),
                motherboardDto.getIntegratedGraphics(),
                motherboardDto.getSoundSupport(),
                motherboardDto.getExpansionSlots(),
                motherboardDto.getStorageSupport(),
                motherboardDto.getUsbSupport(),
                motherboardDto.getWirelessConnectivity(),
                motherboardDto.getOperatingSystemSupport(),
                motherboardDto.getInternalInputOutputConnectivity(),
                motherboardDto.getRearInputOutputConnectivity(),
                motherboardDto.getSupportingSoftware(),
                motherboardDto.getBios(),
                motherboardDto.getAccessory()
        );
        entityManager.persist(motherboard);
    }

    @Override
    public String getDetailType() {
        return "MotherboardDetailCreateRequest";
    }


    public BasicMessageResponse<List<MotherboardDetailProjection>> fetchAll() {
        List<MotherboardDetailProjection> motherboards = motherboardRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách MOTHERBOARD: ", motherboards);
    }

}
