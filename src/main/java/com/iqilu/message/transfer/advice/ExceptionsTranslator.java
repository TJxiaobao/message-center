package com.iqilu.message.transfer.advice;

import com.iqilu.message.transfer.controller.result.Result;
import com.iqilu.message.transfer.exception.CustomException;
import com.iqilu.message.transfer.exception.InterfaceFrequencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author 卢斌
 */
@Slf4j
@CrossOrigin
@ResponseBody
@ControllerAdvice
public class ExceptionsTranslator {


    /**
     * 处理自定义事务异常
     */
    @ExceptionHandler(value = {CustomException.class})
    public Result<?> customerExceptionHandler(HttpServletRequest request, CustomException customException) {
        handleLogInfo(request);
        return Result.fail(customException.getMessage());
    }


    /**
     * 处理无效参数和空参数
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Result<?> messageNotReadExceptionHandler(HttpServletRequest request) {
        handleLogWarn(request);
        return Result.fail("请检查您的参数是否有效");
    }

    @ExceptionHandler(value = {ValidationException.class})
    public Result<Object> validationExceptionHandler(HttpServletRequest request, ValidationException validationException) {
        handleLogWarn(request);
        String[] temp = validationException.getMessage().split(":");
        return Result.fail(temp[temp.length - 1]);
    }


    /**
     * 处理服务器压力过大
     */
    @ExceptionHandler(value = {InterfaceFrequencyException.class})
    public Result<?> interfaceFrequencyExceptionHandler(InterfaceFrequencyException interfaceFrequencyException) {
        return Result.error(interfaceFrequencyException);
    }


    /**
     * 处理参数格式错误
     */
    @ExceptionHandler({ServletException.class, MethodArgumentTypeMismatchException.class})
    public Result<?> requestExceptionHandler(HttpServletRequest request) {
        handleLogWarn(request);
        return Result.error("请求参数缺失或格式错误，请检查请求路径或者请求参数格式是否合法");
    }


    @ExceptionHandler({Exception.class})
    public Result<?> errorHandler(HttpServletRequest request, Exception e) {
        handleLogError(request);
        log.error(e.getMessage());
        return Result.error("后端BUG，联系开发者");
    }


    private String buildRequestPathLogContent(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        Map<String, String[]> storage = request.getParameterMap();
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("请求路径：").append(url);
        for (String i : storage.keySet()) {
            stringBuffer.append("参数").append(i).append(":");
            String[] para = storage.get(i);
            if (para == null) {
                continue;
            }
            for (String j : para) {
                stringBuffer.append(j).append(" ");
            }
        }
        return stringBuffer.toString();
    }

    private void handleLogWarn(HttpServletRequest request) {
        log.warn(this.buildRequestPathLogContent(request));
    }

    private void handleLogError(HttpServletRequest request) {
        log.error(this.buildRequestPathLogContent(request));
    }

    private void handleLogInfo(HttpServletRequest request) {
        log.info(this.buildRequestPathLogContent(request));
    }


}
