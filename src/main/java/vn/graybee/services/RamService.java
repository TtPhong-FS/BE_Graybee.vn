package vn.graybee.services;

import vn.graybee.models.Ram;
import vn.graybee.requests.rams.RamCreateRequest;

import java.util.Optional;

public interface RamService {

    void insertRam(RamCreateRequest request);

    Optional<Ram> findById(long id);

}
