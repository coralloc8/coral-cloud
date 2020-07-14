package com.example.spring.web.auth.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.spring.service.auth.enums.AuthErrorMessageEnum;
import com.example.spring.web.core.enums.OauthErrorMessageEnum;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.response.Results;
import com.example.spring.web.core.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 重新定义error接口返回错误
 * 
 * @author huss
 */
@Slf4j
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomBasicErrorController extends BasicErrorController {

    private final ErrorProperties errorProperties;

    public CustomBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
        this.errorProperties = errorProperties;
        DefaultOAuth2ExceptionRenderer d;

    }

    public CustomBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
        List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
        this.errorProperties = errorProperties;
    }

    @RequestMapping
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Result result = new Results().failure();
        if (status == HttpStatus.NO_CONTENT) {
            String resultJson = JsonUtil.toJson(result);
            return new ResponseEntity<>(JsonUtil.toMap(resultJson), status);
        }
        Map<String, Object> body = this.getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));

        log.error(">>>>>body error:{}", body);

        result = new Results().failure(OauthErrorMessageEnum.UNAUTHORIZED);
        String resultJson = JsonUtil.toJson(result);
        Map<String, Object> resultJsonMap = JsonUtil.toMap(resultJson);

        return new ResponseEntity<>(resultJsonMap, status);
    }

    @Override
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> mediaTypeNotAcceptable(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Result result = new Results().failure(OauthErrorMessageEnum.HTTP_MEDIA_TYPE_NOT_ACCEPT);
        String resultJson = JsonUtil.toJson(result);
        return new ResponseEntity<>(resultJson, status);
    }

    /**
     * Provide access to the error properties.
     * 
     * @return the error properties
     */
    @Override
    protected ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }

}
