package com.poppo.toby.simpleSamples.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UppercaseAdvice  implements MethodInterceptor {
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        // 타깃 오브젝트 정보를 알고 있기 때문에 전달할 필요 없다.
        String proceeded = (String) invocation.proceed();
        return proceeded.toUpperCase();
    }
}
