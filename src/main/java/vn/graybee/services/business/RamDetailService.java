package vn.graybee.services.business;

import vn.graybee.models.business.RamDetail;
import vn.graybee.requests.ram.RamDetailCreateRequest;

import java.util.Optional;

public interface RamDetailService {

    void createRamDetail(RamDetailCreateRequest request);

    Optional<RamDetail> findById(long id);

}
