package com.rkind.splity.dto;

import java.util.List;

public class CreatePollRequest {

    private Long groupId;
    private Long userId;
    private String question;
    private boolean multipleAnswer;
    private List<String> options;

    public CreatePollRequest() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isMultipleAnswer() {
        return multipleAnswer;
    }

    public void setMultipleAnswer(boolean multipleAnswer) {
        this.multipleAnswer = multipleAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}