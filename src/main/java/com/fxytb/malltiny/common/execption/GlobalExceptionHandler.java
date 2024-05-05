package com.fxytb.malltiny.common.execption;

import com.fxytb.malltiny.common.result.CommonResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestControllerAdvice("com.fxytb.malltiny.controller")
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private CommonResult<String> validatorExceptionHandler(MethodArgumentNotValidException e) {
        return CommonResult.error(e
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors
                        .joining(",")));
    }


}
