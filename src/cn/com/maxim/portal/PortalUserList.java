package cn.com.maxim.portal;

public class PortalUserList {
    
    public static  void  UserListPut(String key, UserDescriptor value){
   	
	PortalManager.UserList.put(key,value);
	
   }
}
