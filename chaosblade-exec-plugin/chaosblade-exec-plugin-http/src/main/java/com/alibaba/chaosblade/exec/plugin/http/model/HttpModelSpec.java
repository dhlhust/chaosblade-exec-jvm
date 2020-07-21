package com.alibaba.chaosblade.exec.plugin.http.model;

import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.action.ActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.delay.DelayActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.exception.ThrowCustomExceptionActionSpec;
import com.alibaba.chaosblade.exec.common.model.example.Example;
import com.alibaba.chaosblade.exec.common.model.example.ExampleCommand;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuhan
 * @package: com.alibaba.chaosblade.exec.plugin.http.model
 * @Date 2019-05-21 10:10
 */
public class HttpModelSpec extends FrameworkModelSpec {

    public HttpModelSpec() {
        addActionExample();
    }

    private void addActionExample() {
        List<ActionSpec> actions = getActions();
        for (ActionSpec action : actions) {
            if (action instanceof DelayActionSpec) {
                action.setLongDesc("HTTP client delay experiment");
                action.setExample(Example.builder()
                        .addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("Do a delay 3s experiment with HTTP request URI = https://www.taobao.com/ for HttpClient4")
                                .setCommand("blade create http delay --httpclient4 --uri https://www.taobao.com/ --time 3000")
                                .build()
                        ).addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("Do a delay 3s experiment with HTTP request URI = https://www.taobao.com/ for HttpClient3")
                                .setCommand("blade create http delay --httpclient3 --uri https://www.taobao.com/ --time 3000")
                                .build()
                        ).addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("Do a delay 3s experiment with HTTP request URI = https://www.taobao.com/ for RestTemplate")
                                .setCommand("blade create http delay --rest --uri https://www.taobao.com/ --time 3000")
                                .build()
                        ).addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("Do a delay 3s experiment with HTTP request URI = https://www.taobao.com/ for OkHttp3")
                                .setCommand("blade create http delay --okhttp3 --uri https://www.taobao.com/ --time 3000")
                                .build()
                        ).build());
            }
            if (action instanceof ThrowCustomExceptionActionSpec) {
                action.setLongDesc("HTTP client throws custom exception experiment");
                action.setExample(Example.builder()
                        .addExampleCommand(ExampleCommand.builder()
                                .setAnnotation("Do a throws custom exception with HTTP request URI = https://www.taobao.com/ for HttpClient4")
                                .setCommand("blade c http throwCustomException --httpclient4 --exception=java.lang.Exception --exception-message=customException --uri=https://www.taobao.com/")
                                .build()
                        ).build());
            }
        }
    }

    @Override
    public String getShortDesc() {
        return "http experiment";
    }

    @Override
    public String getLongDesc() {
        return "Experiment with the http client, support --httpclient3 --httpclient4 --okhttp3 --rest.";
    }

    @Override
    protected List<MatcherSpec> createNewMatcherSpecs() {
        ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
        matcherSpecs.add(new RestTemplateMatcherSpec());
        matcherSpecs.add(new HttpClient4MatcherSpec());
        matcherSpecs.add(new HttpClient3MatcherSpec());
        matcherSpecs.add(new UriMatcherDefSpec());
        return matcherSpecs;
    }

    @Override
    public String getTarget() {
        return "http";
    }
}
