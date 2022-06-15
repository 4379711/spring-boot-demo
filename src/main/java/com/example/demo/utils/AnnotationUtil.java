package com.example.demo.utils;

import cn.hutool.core.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yaLong
 */
public class AnnotationUtil {

    /**
     * 扫描某个包下所有包含某个注解的类的注解值
     *
     * @param packageName     要扫描的包名
     * @param annotationClass 要扫描的注解名
     * @return 结果
     */
    public static List<Map<String, Object>> getAnnotationValuesFromPackage(
            String packageName,
            final Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(packageName, annotationClass);
        return classes.stream().map(i ->
                cn.hutool.core.annotation.AnnotationUtil.getAnnotationValueMap(i, annotationClass)
        ).collect(Collectors.toList());
    }
}
