package cn.com.maxim.portal.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ReflectHelper<T>
{
	
	//首写字母变大写
	  public static String firstUpperCase(String old){
		  
		  return old.substring(0, 1).toUpperCase()+old.substring(1);
	  }  
	  
	  
	  public static <T> List<T>  getBean(ResultSet rs, T object) throws Exception {
		  
	       
		  
	        Class<?> classType = object.getClass();
	        ArrayList<T> objList = new ArrayList<T>();
	        //SqlRowSet srs = jdbcTemplate.queryForRowSet(sql);
	        Field[] fields = classType.getDeclaredFields();//得到对象中的字段
	        int count=0;
	        while (rs.next()) {
	            //每次循环时，重新实例化一个与传过来的对象类型一样的对象
	     
	            T objectCopy = (T) classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
	            for (int i = 0; i < fields.length; i++) {
	                Field field = fields[i];
	                String fieldName = field.getName();
	      
	                Object value = null;
	                //根据字段类型决定结果集中使用哪种get方法从数据中取到数据
	                if (field.getType().equals(String.class)) {
	                    value = rs.getString(fieldName);
	                    if(value==null){
	                        value="";
	                    }
	               
	                }
	                if (field.getType().equals(int.class)) {
	                    value = rs.getInt(fieldName);
	                }
	                if (field.getType().equals(java.util.Date.class)) {
	                    value = rs.getDate(fieldName);
	                }
	   
	                // 获得属性的首字母并转换为大写，与setXXX对应
	                String firstLetter = fieldName.substring(0, 1).toUpperCase();
	                String setMethodName = "set" + firstLetter
	                        + fieldName.substring(1);
	             
	                Method setMethod = classType.getMethod(setMethodName,
	                        new Class[] { field.getType() });
	                setMethod.invoke(objectCopy, new Object[] { value });//调用对象的setXXX方法
	               
	                
	            }
	            count++;
	            objList.add(objectCopy);
	        }
	        if(rs != null){
	            rs.close();
	        }
	        return objList;
	    }
	  
	  
	  private   List<String> getAllMethods(Class c){  
          List<String> allMendth=new ArrayList<String>();  
          Field [] f=c.getDeclaredFields();  
          for(int i=0;i<f.length;i++){  
              String m="set";  
              m+=f[i].getName();  
              allMendth.add(m);  
          }  
          return allMendth;  
      }  
        
        
      public List<Object> excuteSelectSQL(Connection con,String sql,String [] parmates,Class clazz) throws Exception{  
               List<Object> all=new ArrayList<Object>();  
               PreparedStatement ps = null;
               ps=con.prepareStatement(sql);  
               if(parmates!=null && !parmates.equals("")){  
                     int index=1;  
                     for(String s:parmates){  
                          ps.setString(index, s);  
                         index++;  
                     }  
                }  
               ResultSet rs =ps.executeQuery();  
               while(rs.next()){  
                      Object o = clazz.newInstance();  
                      List<String> allMethods=getAllMethods(clazz);  
                      int index=1;  
                      for(String s:allMethods){  
                          clazz.getDeclaredMethod(s, String.class).invoke(o, rs.getString(index));  
                          index++;  
                      }  
                      all.add(o);  
               }  
              return all;  
      }  
      
      
  /*    public static void main(String [] rags){  
          SQLHepler SH=new SQLHepler();  
          String  sql="select * from DB_Image";  
          String [] p={"107"};  
          try {  
           List<Object> c=SH.excuteSelectSQL(sql, null, DBImage.class);  
            for (Object o:c) {  
                DBImage cam=(DBImage) o;  
                System.out.println(cam.getImageID()+" "+cam.getImageRealPath());  
                
           }  
          } catch (Exception e) {  
              // TODO Auto-generated catch block  
              e.printStackTrace();  
          }   
             
      }  
	  */
}
