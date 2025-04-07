//package vn.graybee.utils;
//
//import org.springframework.stereotype.Component;
//import vn.graybee.services.others.RegexServices;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Component
//public class HardwareExtractor {
//
//    private final RegexServices regexServices;
//
//    public HardwareExtractor(RegexServices regexServices) {
//        this.regexServices = regexServices;
//    }
//
//    public String extract(String input, String type) {
//        String patternStr = regexServices.getRegex(type);
//        if (patternStr == null) return input;
//
//        Pattern pattern = Pattern.compile(patternStr);
//        Matcher matcher = pattern.matcher(input);
//        return matcher.find() ? matcher.group() : input;
//    }
//
//}
