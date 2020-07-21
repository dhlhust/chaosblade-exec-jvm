/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.chaosblade.exec.common.plugin;

import com.alibaba.chaosblade.exec.common.aop.PredicateResult;
import com.alibaba.chaosblade.exec.common.constant.ModelConstant;
import com.alibaba.chaosblade.exec.common.model.BaseModelSpec;
import com.alibaba.chaosblade.exec.common.model.Model;
import com.alibaba.chaosblade.exec.common.model.action.delay.DelayActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.exception.ThrowCustomExceptionActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.exception.ThrowDeclaredExceptionActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.returnv.ReturnValueActionSpec;
import com.alibaba.chaosblade.exec.common.model.example.Example;
import com.alibaba.chaosblade.exec.common.model.example.ExampleCommand;

/**
 * @author Changjun Xiao
 */
public class MethodModelSpec extends BaseModelSpec {

    public MethodModelSpec() {
        addThrowExceptionActionDef();
        addReturnValueAction();
        addDelayAction();
        addMethodMatcherDef();
    }

    private void addDelayAction() {
        DelayActionSpec actionSpec = new DelayActionSpec();
        actionSpec.setLongDesc("The Java method delays the experiment");
        actionSpec.setExample(Example.builder()
                .addExampleCommand(ExampleCommand.builder()
                        .setAnnotation("Inject a 4-second delay failure on the sayHello method")
                        .setCommand("blade c jvm delay --time 4000 --classname=com.example.controller.DubboController --methodname=sayHello")
                        .build()
                ).build());
        addActionSpec(actionSpec);
    }

    private void addReturnValueAction() {
        ReturnValueActionSpec returnValueActionSpec = new ReturnValueActionSpec();
        returnValueActionSpec.setExample(Example.builder()
                .addExampleCommand(ExampleCommand.builder()
                        .setAnnotation("Inject a tamper return value failure on the com.example.controller.DubboController.hello() method")
                        .setCommand("blade c jvm return --value hello-chaosblade --classname com.example.controller.DubboController --methodname hello")
                        .build()
                ).build());
        addActionSpec(returnValueActionSpec);
    }

    private void addMethodMatcherDef() {
        addMatcherDefToAllActions(new ClassNameMatcherSpec());
        addMatcherDefToAllActions(new MethodNameMatcherSpec(true));
        addMatcherDefToAllActions(new MethodAfterMatcherSpec());
    }

    private void addThrowExceptionActionDef() {
        ThrowCustomExceptionActionSpec throwCustomExceptionActionDef = new ThrowCustomExceptionActionSpec();
        throwCustomExceptionActionDef.setExample(Example.builder()
                .addExampleCommand(ExampleCommand.builder()
                        .setAnnotation("Inject a custom exception failure on the com.example.controller.DubboController.hello() method, effect the two requests")
                        .setCommand("blade c jvm throwCustomException --exception java.lang.Exception --classname com.example.controller.DubboController --methodname sayHello --effect-count 2")
                        .build()
                ).build()

        );
        ThrowDeclaredExceptionActionSpec throwDeclaredExceptionActionDef = new ThrowDeclaredExceptionActionSpec();
        throwDeclaredExceptionActionDef.setExample(Example.builder()
                .addExampleCommand(ExampleCommand.builder()
                        .setAnnotation("Throw the first declared exception of method, effect the two requests")
                        .setCommand("blade c jvm throwDeclaredException --classname com.example.controller.DubboController --methodname sayHello --effect-count 2")
                        .build()
                ).build()

        );
        addActionSpec(throwCustomExceptionActionDef);
        addActionSpec(throwDeclaredExceptionActionDef);
    }

    @Override
    protected PredicateResult preMatcherPredicate(Model matcherSpecs) {
        return PredicateResult.success();
    }

    @Override
    public String getTarget() {
        return ModelConstant.JVM_TARGET;
    }

    @Override
    public String getShortDesc() {
        return "Experiment with the JVM";
    }

    @Override
    public String getLongDesc() {
        return "Experiment with the JVM, and you can specify classes, method injection delays, return values, exception failure scenarios, or write Groovy and Java scripts to implement complex scenarios.";
    }

}
