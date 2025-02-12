package vn.graybee.controllers.publics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.utils.DatetimeFormatted;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/errors")
public class ErrorController {

    @GetMapping("/not-found")
    public Map<String, Object> getNotFoundErrorInfo(
            @RequestParam(value = "resource", required = false, defaultValue = "unknown") String resource) {

        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("type", "http://localhost:8080/errors/not-found");
        errorInfo.put("title", "Not Found");
        errorInfo.put("status", 404);
        errorInfo.put("detail", "The requested resource (" + resource + ") was not found.");
        errorInfo.put("timestamp", DatetimeFormatted.formatted_datetime());
        return errorInfo;
    }

}
