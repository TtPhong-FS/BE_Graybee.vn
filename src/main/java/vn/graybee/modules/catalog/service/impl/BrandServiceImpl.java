package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.catalog.repository.CategoryRepository;
import vn.graybee.modules.catalog.service.BrandService;

@AllArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {

    private final CategoryRepository categoryRepository;

    private final MessageSourceUtil messageSourceUtil;

}
