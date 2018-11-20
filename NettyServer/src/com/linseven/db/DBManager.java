package com.linseven.db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBManager {
	
  private static String resource = "config/mybatis-config.xml";
  private static	SqlSessionFactory sqlSessionFactory;
  private static DBManager instance = new DBManager();
	private DBManager(){
		
		InputStream inputStream =null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
	}
  public static DBManager getInstance(){
	  if(instance ==null){
		  instance = new DBManager();
	  }
	  
	  return instance;
  }
  
  public synchronized SqlSession  openSession(){
	  
	  SqlSession session = sqlSessionFactory.openSession();
	  return session;
  }
  
  
	
}
