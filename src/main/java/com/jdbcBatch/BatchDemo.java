package com.jdbcBatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 2017-08-07
 * 
 * */
public class BatchDemo {
	
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/jdbcbatch";
	    String username = "root";
	    String password = "root";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	
	/** 
     * 批量执行预定义模式的SQL 
	 * @throws SQLException 
     */ 
    public static void preBatch() throws SQLException { 
            Connection conn = null; 
            try { 
            	 conn = BatchDemo.getConn(); 
                    String sql = "insert into test (id, name,type) values (?,?,?)"; 
                    PreparedStatement pstmt = conn.prepareStatement(sql); 
                    pstmt.setString(1, "22"); 
                    pstmt.setString(2, "xiaobao"); 
                    pstmt.setString(3, "66");
                    pstmt.addBatch();                     
                    pstmt.setString(1, "33"); 
                    pstmt.setString(2, "xiaolan"); 
                    pstmt.setString(3, "88"); 
                    pstmt.addBatch();                     
                    pstmt.executeBatch(); 
                    pstmt.close();
                    conn.close();
            } catch (SQLException e) { 
                    e.printStackTrace(); 
            }
    } 

	
	
	 /** 
     * 执行批量静态的SQL 
	 * @throws SQLException 
     */ 
    public static void stmBatch() throws SQLException { 
            Connection conn = null; 
            try { 
                    conn = BatchDemo.getConn(); 
                    Statement stmt = conn.createStatement(); 
                    //连续添加多条静态SQL 
                    stmt.addBatch("insert into test (id, name,type) values ('12', '小明','00')"); 
                    stmt.addBatch("insert into test (id, name,type) values ('23', '小红','11')"); 
                    stmt.addBatch("delete from test where id ='1'"); 
                    stmt.addBatch("update test set id = '2' where name='sunguang'"); 
//                    stmt.addBatch("select count(*) from testdb.book");                //批量执行不支持Select语句 
                    //执行批量执行 
                    stmt.executeBatch(); 
                    stmt.close();
                    conn.close();
            } catch (SQLException e) { 
                    e.printStackTrace(); 
            } 
            
    } 

    
    public static void main(String[] args) throws SQLException {
    	//stmBatch();
    	preBatch();
	}
	
}
