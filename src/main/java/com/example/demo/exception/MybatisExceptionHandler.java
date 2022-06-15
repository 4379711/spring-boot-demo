package com.example.demo.exception;

import com.example.demo.constant.ErrorConstants;
import com.example.demo.constant.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author YaLong
 */
@RestControllerAdvice
@Order(100)
@Slf4j
public class MybatisExceptionHandler {

    @ExceptionHandler({BuilderException.class})
    public R argument(Exception e) {
        log.error(e.getMessage(), e);
        return R.fail(ErrorConstants.PARAM_ERROR);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.fail(ErrorConstants.DUPLICATE_KEY);
    }

    @ExceptionHandler(PersistenceException.class)
    public R handlePersistenceException(PersistenceException e) {
        log.error(e.getMessage(), e);
        if (e.getMessage().contains(ErrorConstants.NOT_LOGIN)) {
            return R.fail(ErrorConstants.NOT_LOGIN);
        }
        return R.fail(e.getMessage());
    }
}
