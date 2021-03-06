/**
 * The MIT License (MIT)
 *
 * Copyright (c)  2014 CrossBusiness, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.crossbusiness.resiliency.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareError;
import org.aspectj.lang.annotation.DeclareWarning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Sumanth Chinthagunta <xmlking@gmail.com> on 3/16/14.
 */
@Component
@Order(117)
@Aspect
public class AnnotationAsyncAspect extends AbstractAsyncAspect {

    @Pointcut("execution(@com.crossbusiness.resiliency.annotation.Async (void || java.util.concurrent.Future+) *(..))")
    public void asyncAnnotatedMethod() {}

    @Pointcut("execution((void || java.util.concurrent.Future+) (@com.crossbusiness.resiliency.annotation.Async *).*(..)) " +
            "&& !com.crossbusiness.resiliency.aspect.SystemArchitecture.groovyMOPMethods()")
    public void asyncAnnotatedClass() {}

    @Pointcut("asyncAnnotatedMethod() || asyncAnnotatedClass()")
    public void asyncMethodExecution() {}

    @DeclareError("execution(@com.crossbusiness.resiliency.annotation.Async !(void||java.util.concurrent.Future) *(..))")
    static final String anError = "Only methods that return void or Future may have an @Async annotation";

    @DeclareWarning("execution(!(void||java.util.concurrent.Future) (@com.crossbusiness.resiliency.annotation.Async *).*(..))")
    static final String aMessage = "Methods in a class marked with @Async that do not return void or Future will be routed synchronously";
}


