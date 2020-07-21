package com.alibaba.chaosblade.exec.common.model.example;

/**
 * @author yefei
 */
public class ExampleCommand {

    private String annotation;

    private String command;

    private String commandResult;

    private String imageURL;

    public static class Builder {

        private ExampleCommand exampleCommand;

        public Builder() {
            exampleCommand = new ExampleCommand();
        }

        public Builder setAnnotation(String annotation) {
            exampleCommand.annotation = annotation;
            return this;
        }

        public Builder setCommand(String command) {
            exampleCommand.command = command;
            return this;
        }

        public Builder setCommandResult(String commandResult) {
            exampleCommand.commandResult = commandResult;
            return this;
        }

        public Builder setImageURL(String imageURL) {
            exampleCommand.imageURL = imageURL;
            return this;
        }

        public ExampleCommand build() {
            return exampleCommand;
        }
    }

    public static Builder builder() {
        return new ExampleCommand.Builder();
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setCommandResult(String commandResult) {
        this.commandResult = commandResult;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getCommand() {
        return command;
    }

    public String getCommandResult() {
        return commandResult;
    }

    public String getImageURL() {
        return imageURL;
    }

}
