package com.example.hello.version;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc: 版本选择器
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    /**
    *extract the version part from url. example [v0-9]
    */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");

    private int apiVersion;

    public ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // latest defined would be take effect, that means, methods definition with
        // override the classes definition
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            Integer version = Integer.valueOf(m.group(1));
            // when applying version number bigger than configuration, then it will take
            if (version >= this.apiVersion){
                return this;
            }
        }
        return new ApiVersionCondition(5);
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // when more than one configured version number passed the match rule, then only
        // the biggest one will take effect.
        return other.getApiVersion() - this.apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

}