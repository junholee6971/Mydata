package com.idispt.mydataPrj.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.ibatis.session.SqlSession;

import com.idispt.mydataPrj.Interface.ProcMessage;
import com.idispt.mydataPrj.session.SessionFactory;
import com.idispt.mydataPrj.util.ComUtil;

public abstract class ProcMessageTemplate implements ProcMessage {
    
    private static final Logger logger = LoggerFactory.getLogger(ProcMessageTemplate.class);
    
    protected int maxlen =  0;
    protected String sqlid = "";
    protected String ci; 
    protected String msg;
    
    protected void setMsg(String msg) {
        this.msg = msg;
    }
    
    protected String getMsg() {
        if (msg == null ) return "";
        return msg;
    }
    
    protected void setCi(String ci) {
        this.ci = ci;
    }
    
    public String getCi() {
        if (ci == null ) return "";
        return ci;
    }

    public void setMaxlen(int maxlen) {
        this.maxlen = maxlen;
    }
    public int getMaxlen() {
        return maxlen;
    }

    public void setSqlid(String sqlid) {
        this.sqlid = sqlid;
    }
     
    public String getSqlid() {
        if (sqlid == null ) return "";
        return sqlid;
    }
    
    @Override
    public byte[] process(String msg) {

        init();
        
        if (!checkMsg(msg)) return ComUtil.errorProc(msg, "50004");
        
        if (!parseMsgAndSet(msg)) return ComUtil.errorProc(msg, "50004");
        
        return excuteQuery(getSqlid());
        
    }
    
    abstract protected void init();
    
    
    protected boolean checkMsg(String msg) {
        
        // 마지막 문자가 개행문자가 아닌 경우
        if( !"\r".equals(msg.substring(getMaxlen()-1))) { 
            logger.info("carrage return 이 없음");
            return false;
        }

        //길이가 안될때 
        if(msg.length() != getMaxlen()) {
            logger.info("******************* length오류");
            return false;
        }

        setMsg(msg);
        return true;
    }


    abstract protected boolean parseMsgAndSet(String msg);

    @SuppressWarnings("rawtypes")
    protected byte[] excuteQuery(String sqlId) {
         
        Map map = setParameter();
                 
         SqlSession session = null;
         
         byte[] retbyte = null;
         
         try {
             session = SessionFactory.getSqlSession();
             
             /* 각자 구현 */
             List res = session.selectList(sqlId, map);            
             retbyte = makeSubMsg(res);
                          
         }catch(Exception e) {
             logger.error(e.getMessage());
             retbyte = ComUtil.errorProc(getMsg(), "50004");
         } 
         
         session.close();
         
         return retbyte;
         
    }
       
    
    @SuppressWarnings("rawtypes")
    abstract protected Map setParameter() ;

    @SuppressWarnings("rawtypes")
    abstract protected byte[] makeSubMsg(List res) ;
    
}
