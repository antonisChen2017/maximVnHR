package cn.com.maxim.portal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
 

 

public class BaseDAO {

 
    /**
    * 查找多个对象
     * @param sqlString
     * @param clazz
     * @return
     */
    public List query(String sqlString,Class clazz,Connection conn) {
        List beans = null;
      //  Connection conn = null;
        try {
        //    conn = getConnection();
            QueryRunner qRunner = new QueryRunner();
            beans =
                (List) qRunner.query(
                    conn,
                    sqlString,
                    new BeanListHandler(clazz));
        } catch (SQLException e) {
          
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return beans;
    }
 
    /**
     * 查找对象
     * @param sqlString
     * @param clazz
     * @return
     */
    public Object get(String sqlString,Class clazz,Connection conn) {
        List beans = null;
        Object obj = null;
     //   Connection conn = null;
        try {
      //      conn = getConnection();
            QueryRunner qRunner = new QueryRunner();
            beans =
                (List) qRunner.query(
                    conn,
                    sqlString,
                    new BeanListHandler(clazz));
        } catch (Exception e) {

        } finally {
            DbUtils.closeQuietly(conn);
        }
        if(beans!=null &&!beans.isEmpty()){ //注意这里
             obj=beans.get(0);

        }

        return obj;
    }
 
    /**
     * 执行更新的sql语句,插入,修改,删除
     * @param sqlString
     * @return
     */
    public boolean update(String sqlString,Connection conn) {
      //  Connection conn = null;
        boolean flag = false;
        try {
        //    conn = getConnection();
            QueryRunner qRunner = new QueryRunner();
            int i =qRunner.update(conn,sqlString);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
           
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return flag;
    }
}
