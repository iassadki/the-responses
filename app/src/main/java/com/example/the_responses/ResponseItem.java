package com.example.the_responses;

public class ResponseItem {
    private String responseText;
    private boolean isSpam;
    private boolean isAutoResponse;

    public ResponseItem(String responseText, boolean isSpam, boolean isAutoResponse) {
        this.responseText = responseText;
        this.isSpam = isSpam;
        this.isAutoResponse = isAutoResponse;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public boolean isAutoResponse() {
        return isAutoResponse;
    }

    public void setAutoResponse(boolean autoResponse) {
        isAutoResponse = autoResponse;
    }
}
