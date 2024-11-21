package console;

import javax.naming.Name;
import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.Timestamp;

/**
 * TODO ���ݴ�����
 *
 * @author gongjing
 * @date 2016/10/13
 */
public class DataProcessing {

	private static boolean connectToDB=false;
	
	static Hashtable<String, AbstractUser> users;
	static Hashtable<String, Doc> docs;

	static enum ROLE_ENUM {
        /**
         * administrator
         */
        administrator("administrator"), 
        /**
         * operator
         */
        operator("operator"), 
        /**
         * browser
         */
        browser("browser");
        
        private String role;
        
        ROLE_ENUM(String role) {
            this.role = role;
        }
        
        public String getRole() {
            return role;
        }
    }
	static {
		users = new Hashtable<String, AbstractUser >();
		users.put("rose", new Browser("rose","123","browser"));
        users.put("jack", new Operator("jack","123","operator"));
		users.put("kate", new Administrator("kate","123","administrator"));
		init();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		docs = new Hashtable<String,Doc>();
		docs.put("0001",new Doc("0001","jack",timestamp,"Doc Source Java","Doc.java"));
	}
	
	/**
     * TODO ��ʼ�����������ݿ�
     *   
     * @param 
     * @return void
     * @throws  
    */
	public static  void init(){
	    connectToDB = true;
	}
	
	/**
	 * TODO �������������������Ϣ������nullʱ����δ�ҵ�
	 * 
	 * @param id
	 * @return Doc
	 * @throws SQLException 
	*/
	public static Doc searchDoc(String id) throws SQLException {
	    if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
	    if (docs.containsKey(id)) {
			Doc temp =docs.get(id);
			return temp;
		}
		return null;
	}



	
	/**
	 * TODO �г����е�����Ϣ
	 * 
	 * @param 
	 * @return Enumeration<Doc>
	 * @throws SQLException 
	*/
	public static Enumeration<Doc> listDoc() throws SQLException{		
	    if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
	    
	    Enumeration<Doc> e  = docs.elements();
		return e;
	} 
	
	/**
	 * TODO �����µĵ���
	 * 
	 * @param id
	 * @param creator
	 * @param timestamp
	 * @param description
	 * @param filename
	 * @return boolean
	 * @throws SQLException  
	*/
	public static boolean insertDoc(String id, String creator, Timestamp timestamp, String description, String filename) throws SQLException{
		Doc doc;
		
		if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
		
		if (docs.containsKey(id))
			return false;
		else{
			doc = new Doc(id,creator,timestamp,description,filename);
			docs.put(id, doc);
			return true;
		}
	} 
	
	/**
     * TODO ���û��������û�������nullʱ����δ�ҵ������������û�
     * 
     * @param name �û��� 
     * @return AbstractUser
     * @throws SQLException 
    */
	public static AbstractUser searchUser(String name) throws SQLException{
	    if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
	    
	    if (users.containsKey(name)) {
			return users.get(name);			
		}
		return null;
	}
	
	/**
     * TODO ���û��������������û�������nullʱ����δ�ҵ������������û�
     * 
     * @param name �û���
     * @param password  ����
     * @return AbstractUser
     * @throws SQLException 
    */
	public static AbstractUser searchUser(String name, String password) throws SQLException {
	    if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
	    
	    if (users.containsKey(name)) {
		    AbstractUser temp =users.get(name);
			if ((temp.getPassword()).equals(password)) {
				return temp;
			}
		}
		return null;
	}
	
	/**
     * TODO ȡ�����е��û� 
     * 
     * @param 
     * @return Enumeration<AbstractUser>
     * @throws SQLException 
    */
	public static Enumeration<AbstractUser> listUser() throws SQLException{
	    if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
	    
	    Enumeration<AbstractUser> e = users.elements();
		return e;
	}
	
	/**
     * TODO �޸��û���Ϣ
     * 
     * @param name �û���
     * @param password ����
     * @param role ��ɫ
     * @return boolean
     * @throws SQLException 
    */
	public static boolean updateUser(String name, String password, String role) throws SQLException{
	    AbstractUser user;
	    if (users.containsKey(name)) {
            switch(ROLE_ENUM.valueOf(role.toLowerCase())) {
                case administrator:
                    user = new Administrator(name,password, role);
                    break;
                case operator:
                    user = new Operator(name,password, role);
                    break;
                default:
                    user = new Browser(name,password, role);    
            }
            users.put(name, user);
            return true;
        }else {
            return false;
        }
	}
	
	/**
     * TODO �������û�
     * 
     * @param name �û���
     * @param password ����
     * @param role ��ɫ
     * @return boolean
     * @throws SQLException 
    */
	public static boolean insertUser(String name, String password, String role) throws SQLException{
	    AbstractUser user;
	    if (users.containsKey(name)) {
            return false;
        }else{
            switch(ROLE_ENUM.valueOf(role.toLowerCase())) {
                case administrator:
                    user = new Administrator(name,password, role);
                    break;
                case operator:
                    user = new Operator(name,password, role);
                    break;
                default:
                    user = new Browser(name,password, role);    
            }
            users.put(name, user);
            return true;
        }
	}
	
	/**
     * TODO ɾ��ָ���û�
     * 
     * @param name �û���
     * @return boolean
     * @throws SQLException 
    */
	public static boolean deleteUser(String name) throws SQLException{
		if (users.containsKey(name)){
			users.remove(name);
			return true;
		}else {
			return false;
		}
	}	
    
	/**
     * TODO �ر����ݿ�����
     *   
     * @param 
     * @return void
     * @throws  
    */
	public static void disconnectFromDataBase() {
		if (connectToDB){
			// close Statement and Connection            
			try{

			}finally{                                            
				connectToDB = false;              
			}                             
		} 
   }           

}
