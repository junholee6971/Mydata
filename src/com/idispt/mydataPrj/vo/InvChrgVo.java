package com.idispt.mydataPrj.vo;

public class InvChrgVo {
    
    private String said;
    private String invamt;
    private String dueDay;

    
    public InvChrgVo() {
        super();
    }
    
    public void setSaid(String said) {
        this.said = said;
    }
    
    public String getSaid() {
        return said;
    }
    
    public void setInvamt(String invamt) {
        this.invamt = invamt;
    }
    
    public  String getInvamt() {
        return invamt;
    }
    
    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }
    
    public  String getDueDay() {
        return dueDay;
    }
}
