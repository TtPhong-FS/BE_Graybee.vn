//package vn.graybee.config;
//
//import org.apache.catalina.util.RateLimiter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    private RateLimiter rateLimitInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(rateLimitInterceptor);
//        rateLimitInterceptor.setRequests();
//    }
//
//}