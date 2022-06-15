package com.example.demo.aop;

import com.example.demo.annotation.SingleUserDataScope;
import com.example.demo.exception.BaseException;
import com.example.demo.param.SingleDataScopeParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yaLong
 */
@Component
@Aspect
public class SingleUserDataScopeAop {

    @Pointcut("@annotation(com.example.demo.annotation.SingleUserDataScope)")
    public void singleUserDataScopePointCut() {
    }

    @Around("singleUserDataScopePointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取切入函数的参数,如果无参数,不需要生成sql
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return joinPoint.proceed();
        }

        //获取切入函数上注解的参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SingleUserDataScope annotation = method.getAnnotation(SingleUserDataScope.class);

        //找到切入点的入参对象
        Class<?>[] parameterTypes = method.getParameterTypes();
        Integer position = null;
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType == SingleDataScopeParam.class) {
                position = i;
                break;
            }
        }
        if (position == null) {
            throw new BaseException("mapper找不到SingleDataScopeParam参数");
        }

        //结果参数
        SingleDataScopeParam resultParam = new SingleDataScopeParam();

        //构造sql
        String accountAlias = annotation.accountAlias();
        String accountSql = "";

        //给源参数赋值
        resultParam.setAccountSql(accountSql);
        args[position] = resultParam;
        return joinPoint.proceed(args);
    }

}
