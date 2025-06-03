package vn.graybee.modules.product.service.detail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.product.dto.request.DetailTemplateRequest;
import vn.graybee.modules.product.dto.request.PcDetailRequest;
import vn.graybee.modules.product.dto.request.ProductCreateRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.model.detail.PcDetail;
import vn.graybee.modules.product.repository.PcDetailRepository;
import vn.graybee.modules.product.service.AdminProductService;
import vn.graybee.response.publics.products.DetailTemplateResponse;

@Service
public class PcDetailServices implements DetailFetcher {

    private final PcDetailRepository pcDetailRepository;

    private final AdminProductService adminProductService;

    public PcDetailServices(PcDetailRepository pcDetailRepository, AdminProductService adminProductService) {
        this.pcDetailRepository = pcDetailRepository;
        this.adminProductService = adminProductService;
    }

    private static PcDetail getPcDetail(PcDetailRequest template, ProductResponse product) {
        PcDetail pcDetail = new PcDetail();
        pcDetail.setProductId(product.getId());
        pcDetail.setMainBoard(template.getMainBoard());
        pcDetail.setCpu(template.getCpu());
        pcDetail.setVga(template.getVga());
        pcDetail.setHdd(template.getHdd());
        pcDetail.setSsd(template.getSsd());
        pcDetail.setCaseName(template.getCaseName());
        pcDetail.setIoPorts(template.getIoPorts());
        pcDetail.setConnectivity(template.getConnectivity());
        pcDetail.setCooling(template.getCooling());
        pcDetail.setOs(template.getOs());
        return pcDetail;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> saveDetail(ProductCreateRequest productRequest, DetailTemplateRequest template) {
        ProductResponse product = adminProductService.createProduct(productRequest).getData();

//        if (product.getCategoryName().equals("pc")) {
//            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.missing_type_detail + product.getCategoryName());
//        }

        PcDetail pcDetail = getPcDetail((PcDetailRequest) template, product);

        pcDetailRepository.save(pcDetail);

        return new BasicMessageResponse<>(200, ConstantProduct.success_create + " cho danh mục ", product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> updateDetail(Long productId, ProductUpdateRequest productRequest, DetailTemplateRequest template) {
        ProductResponse product = adminProductService.updateProduct(productId, productRequest).getData();

        PcDetail pcDetail = getPcDetail((PcDetailRequest) template, product);

        pcDetailRepository.save(pcDetail);

        return new BasicMessageResponse<>(200, ConstantProduct.success_update + " cho danh mục ", product);
    }


    @Override
    public String getDetailType() {
        return "pc";
    }

    @Override
    public DetailTemplateResponse fetchDetail(Long productId) {
        return pcDetailRepository.findByProductId(productId);
    }

}
