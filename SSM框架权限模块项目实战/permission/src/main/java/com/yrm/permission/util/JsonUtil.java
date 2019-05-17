package com.yrm.permission.util;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @despc 利用Jackson 来解析json字符串和对象之间的转换
 * @className JsonUtil
 * @createTime 2019年03月22日 15:35:00
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }


    public JsonUtil() {
    }

    public static <T> String obj2String(T target) {
        if (target == null) {
            return null;
        }
        try {
            return target instanceof String ? (String) target : objectMapper.writeValueAsString(target);
        } catch (Exception e) {
            logger.error("convert object to String error,target===>{},error===>{}",target,e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> reference){
        if (str == null || reference ==null){
            return null;
        }
        try {
            return  (T) (reference.getType().equals(String.class)?str:objectMapper.readValue(str,reference));
        } catch (Exception e) {
            logger.error("String convert to Object error,str===>{},referenceType===>{},error===>{}",str,reference.getType(),e );
            return null;
        }
    }
}
