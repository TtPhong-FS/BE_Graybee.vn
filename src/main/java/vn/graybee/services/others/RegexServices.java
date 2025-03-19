package vn.graybee.services.others;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import vn.graybee.models.others.RegexPattern;
import vn.graybee.repositories.others.RegexPatternRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegexServices {

    private final RegexPatternRepository regexRepository;

    private Map<String, String> regexCache;

    public RegexServices(RegexPatternRepository regexRepository) {
        this.regexRepository = regexRepository;
    }

    @PostConstruct
    public void loadRegex() {
        regexCache = regexRepository.findAll()
                .stream()
                .collect(Collectors.toMap(RegexPattern::getTypeName, RegexPattern::getPattern));
    }

    public String getRegex(String type) {
        return regexCache.get(type);
    }

    public void updateRegex(String type, String newPattern) {
        RegexPattern regexPattern = regexRepository.findByTypeName(type)
                .orElse(new RegexPattern(type, newPattern));

        regexPattern.setPattern(newPattern);
        regexRepository.save(regexPattern);

        regexCache.put(type, newPattern);
    }

}
