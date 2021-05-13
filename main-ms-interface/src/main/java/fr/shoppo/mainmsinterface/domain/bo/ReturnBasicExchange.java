package fr.shoppo.mainmsinterface.domain.bo;

public class ReturnBasicExchange {
    private String status;

    private String content;

    public ReturnBasicExchange(String status, String content) {
        this.status = status;
        this.content = content;
    }

    public ReturnBasicExchange() {
        /*binding*/
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
