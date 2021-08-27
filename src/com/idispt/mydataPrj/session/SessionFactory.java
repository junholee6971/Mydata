package com.idispt.mydataPrj.session;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionFactory.class);
    
    private SessionFactory() {}
    
    static
    {
            sessionFactory = setSessionFactory();
    }
    
    static SqlSessionFactory sessionFactory;
    
    private static SqlSessionFactory setSessionFactory(){
        String resource = "com/idispt/mydataPrj/session/Mybatis.xml"; //데이터베이스의 설정정보를 입력해준 xml 파일
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return sessionFactory;
    }
    
    public static SqlSessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
    public static SqlSession getSqlSession() {
       
        SqlSession session = sessionFactory.openSession(false);   //false = Not autoCommit
        return session;
    }
     
}
