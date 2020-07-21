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

package com.alibaba.chaosblade.exec.plugin.servlet;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.action.ActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.delay.DelayActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.exception.ThrowCustomExceptionActionSpec;
import com.alibaba.chaosblade.exec.common.model.example.Example;
import com.alibaba.chaosblade.exec.common.model.example.ExampleCommand;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

/**
 * @author Changjun Xiao
 */
public class ServletModelSpec extends FrameworkModelSpec {

    public ServletModelSpec() {
        addActionExample();
    }

    private void addActionExample() {
        List<ActionSpec> actions = getActions();
        for (ActionSpec action : actions) {
            if (action instanceof DelayActionSpec) {
                action.setLongDesc("Servlet delay experiment, support servlet springMVC webX");
                action.setExample(Example.builder()
                        .addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("Request to http://localhost:8080/dubbodemo/servlet/path?name=bob delays 3s, effect two requests")
                                .setCommand("blade c servlet delay --time 3000 --requestpath /dubbodemo/servlet/path --effect-count 2")
                                .build()
                        )
                        .addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("The request parameter is name=family, the delay is 2 seconds, the delay time floats up and down for 1 second, the impact range is 50% of the request, and the debug log is turned on to troubleshoot the problem")
                                .setCommand("blade c servlet delay --time 2000 --offset 1000 --querystring name=family --effect-percent 50 --debug")
                                .setImageURL("https://gblobscdn.gitbook.com/assets%2F-Lj_V1osBR9CfezpYfj_%2Fsync%2Fd5b20ec183111e829c281526a10f5197f4618716.jpg?alt=media")
                                .build()
                        ).build());
            } else if (action instanceof ThrowCustomExceptionActionSpec) {
                action.setLongDesc("Servlet throw custom exception experiment, support servlet springMVC webX");
                action.setExample(Example.builder()
                        .addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("Request to http://localhost:8080/dubbodemo/hello throws custom exception, effect three requests")
                                .setCommand("blade c servlet throwCustomException --exception org.springframework.beans.BeansException --exception-message mock-beans-exception --requestpath /hello --effect-count 3")
                                .build()
                        ).build());
            }
        }
    }

    @Override
    public String getTarget() {
        return ServletConstant.TARGET_NAME;
    }

    @Override
    public String getShortDesc() {
        return "java servlet experiment";
    }

    @Override
    public String getLongDesc() {
        return "Java servlet experiment, support path, query string, request method matcher";
    }

    @Override
    protected List<MatcherSpec> createNewMatcherSpecs() {
        ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
        matcherSpecs.add(new ServletQueryStringMatcherSpec());
        matcherSpecs.add(new ServletMethodMatcherSpec());
        matcherSpecs.add(new ServletRequestPathMatcherSpec());
        return matcherSpecs;
    }
}
