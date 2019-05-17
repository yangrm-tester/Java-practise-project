package com.yrm.permission.common;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yrm.permission.exception.PermissionException;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * 这个类  是对参数校验，不咋用，自己去判断入参
 * @author 杨汝明
 * @version 1.0.0
 * @className BeanVaildator
 * @createTime 2019年03月22日 14:10:00
 */
public class BeanValidator {
    private static final Logger logger = LoggerFactory.getLogger(BeanValidator.class);
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violationSet = validator.validate(t, groups);
        if (violationSet.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap mapErrors = Maps.newLinkedHashMap();
            Iterator<ConstraintViolation<T>> iterator = violationSet.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                mapErrors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return mapErrors;
        }
    }

    public static Map<String, String> validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map<String, String> mapErrors;
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            mapErrors = validate(object, new Class[0]);

        } while (mapErrors.isEmpty());
        return mapErrors;
    }

    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }

    public static void check(Object param) throws PermissionException{
        Map<String, String> map = validateObject(param);
        if (MapUtils.isNotEmpty(map)){
            throw  new PermissionException(map.toString());
        }
    }
}
