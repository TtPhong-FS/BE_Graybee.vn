package vn.graybee.services.others;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import vn.graybee.models.RegexPattern;
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
                .collect(Collectors.toMap(RegexPattern::getName, RegexPattern::getPattern));
    }

    public String getRegex(String name) {
        return regexCache.get(name);
    }

    public void updateRegex(String name, String newPattern) {
        RegexPattern regexPattern = regexRepository.findByName(name)
                .orElse(new RegexPattern(name, newPattern));

        regexPattern.setPattern(newPattern);
        regexRepository.save(regexPattern);

        regexCache.put(name, newPattern);
    }

}
