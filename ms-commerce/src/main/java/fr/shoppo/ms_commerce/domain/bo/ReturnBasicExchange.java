package fr.shoppo.ms_commerce.domain.bo;

import java.util.Objects;

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

    public ReturnBasicExchange status(String status){
        setStatus(status);
        return this;
    }

    public ReturnBasicExchange content(String content){
        setContent(content);
        return this;
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

    @Override
    public String toString() {
        return "UpdatePanierOutput{" +
                "status='" + status + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnBasicExchange that = (ReturnBasicExchange) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, content);
    }
}
