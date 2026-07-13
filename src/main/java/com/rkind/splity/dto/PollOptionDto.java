package com.rkind.splity.dto;

public class PollOptionDto {

    private Long id;
    private String optionText;
    private int voteCount;
    private boolean voted;

    public PollOptionDto() {
    }

    public PollOptionDto(Long id,
                         String optionText,
                         int voteCount,
                         boolean voted) {
        this.id = id;
        this.optionText = optionText;
        this.voteCount = voteCount;
        this.voted = voted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}