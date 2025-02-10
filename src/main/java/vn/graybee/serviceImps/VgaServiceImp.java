package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.VgaDetail;
import vn.graybee.repositories.business.VgaRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.vga.VgaDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class VgaServiceImp implements ProductDetailService {


    private final VgaRepository vgaRepository;

    public VgaServiceImp(VgaRepository vgaRepository) {
        this.vgaRepository = vgaRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getCategoryName().equalsIgnoreCase("vga")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_VGA_TYPE);
        }
        VgaDetailCreateRequest vgaDto = (VgaDetailCreateRequest) request;
        VgaDetail vga = new VgaDetail(
                product,
                vgaDto.getMemorySpeed(),
                vgaDto.getMemoryProtocol(),
                vgaDto.getMaximumResolution(),
                vgaDto.getMultipleScreen(),
                vgaDto.getProtocols(),
                vgaDto.getGpuClock(),
                vgaDto.getBusStandard(),
                vgaDto.getNumberOfProcessingUnit(),
                vgaDto.getPowerConsumption(),
                vgaDto.getPsuRecommend(),
                vgaDto.getDirectx(),
                vgaDto.isApplicationSupport()
        );
        vgaRepository.save(vga);
    }

    @Override
    public String getDetailType() {
        return "VgaDetailCreateRequest";
    }

}
