package com.idispt.mydataPrj.vo;

public class SubScribeListVo {
    private String said;
    private String subscno;
    private String teltype;
    private String status;
    private String remaincnt;

    public SubScribeListVo() {
        super();
    }
    
    public void setSaid(String said) {
        this.said = said;
    }
    
    public String getSaid() {
        return said;
    }
       
    public void setSubscno(String subscno) {
        this.subscno = subscno;
    }
    
    public  String getSubscno() {
        return subscno;
    }
    
    public void setTeltype(String teltype) {
        this.teltype = teltype;
    }
    
    public  String getTeltype() {
        return teltype;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public  String getStatus() {
        return status;
    }
    
    public void setRemaincnt(String remaincnt) {
        this.remaincnt = remaincnt;
    }
    
    public  String getRemaincnt() {
        return remaincnt;
    }
}
