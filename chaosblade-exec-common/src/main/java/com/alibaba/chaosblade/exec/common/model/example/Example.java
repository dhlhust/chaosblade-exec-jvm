package com.alibaba.chaosblade.exec.common.model.example;

import com.alibaba.chaosblade.exec.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yefei
 */
public class Example {

    private String introduction;

    private List<ExampleCommand> exampleCommands;

    private String summary;

    public static class Builder {

        private Example example;

        private Builder() {
            example = new Example();
        }

        public Builder setIntroduction(String introduction) {
            if (StringUtils.isNotBlank(introduction)) {
                example.introduction = introduction;
            }
            return this;
        }

        public Builder addExampleCommand(ExampleCommand exampleCommand) {
            if (example.exampleCommands == null) {
                example.exampleCommands = new ArrayList<ExampleCommand>();
            }
            example.exampleCommands.add(exampleCommand);
            return this;
        }

        public Builder setSummary(String summary) {
            example.summary = summary;
            return this;
        }

        public Example build() {
            return example;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setExampleCommands(List<ExampleCommand> exampleCommands) {
        this.exampleCommands = exampleCommands;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<ExampleCommand> getExampleCommands() {
        return exampleCommands;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getSummary() {
        return summary;
    }

}
