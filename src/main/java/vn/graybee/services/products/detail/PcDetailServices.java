package vn.graybee.services.products.detail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.detail.PcDetail;
import vn.graybee.repositories.products.detail.IPcDetailRepository;
import vn.graybee.requests.products.DetailTemplateRequest;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.requests.products.detail.PcDetailRequest;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.publics.products.DetailTemplateResponse;
import vn.graybee.services.products.IProductServiceAdmin;

@Service
public class PcDetailServices implements DetailFetcher {


    private final IPcDetailRepository iPcDetailRepository;

    private final IProductServiceAdmin iProductServiceAdmin;

    public PcDetailServices(IPcDetailRepository iPcDetailRepository, IProductServiceAdmin iProductServiceAdmin) {
        this.iPcDetailRepository = iPcDetailRepository;
        this.iProductServiceAdmin = iProductServiceAdmin;
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
        ProductResponse product = iProductServiceAdmin.create(productRequest).getData();

        if (product.getCategoryName().equals("pc")) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.missing_type_detail + product.getCategoryName());
        }

        PcDetail pcDetail = getPcDetail((PcDetailRequest) template, product);

        iPcDetailRepository.save(pcDetail);

        return new BasicMessageResponse<>(200, ConstantProduct.success_create + " cho danh mục " + product.getCategoryName(), product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ProductResponse> updateDetail(Long productId, ProductUpdateRequest productRequest, DetailTemplateRequest template) {
        ProductResponse product = iProductServiceAdmin.update(productId, productRequest).getData();

        PcDetail pcDetail = getPcDetail((PcDetailRequest) template, product);

        iPcDetailRepository.save(pcDetail);

        return new BasicMessageResponse<>(200, ConstantProduct.success_update + " cho danh mục " + product.getCategoryName(), product);
    }


    @Override
    public String getDetailType() {
        return "pc";
    }

    @Override
    public DetailTemplateResponse fetchDetail(Long productId) {
        return iPcDetailRepository.findByProductId(productId);
    }

}
