package console;

import javax.naming.Name;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.Timestamp;
import java.util.List;

/**
 * TODO 数据处理类
 *
 * @author gongjing
 * @date 2016/10/13
 */
public class DataProcessing {

	private static boolean connectToDB=false;

	public static final String USER_STORAGE_PATH="user.txt";

	public static final String DCO_STORAGE_PATH="doc.txt";

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
		inituser();
		
		initdoc();
	}
	
	/**
     * TODO 初始化，连接数据库
     *   
     * @param 
     * @return void
     * @throws  
    */
	public static void inituser() {
		connectToDB = true;
		File userStorageFile =new File(USER_STORAGE_PATH);
		users = new Hashtable<String, AbstractUser>();
		try {
			if(!userStorageFile.exists()){
				users.put("jack", new Operator("jack", "123", "operator"));
				users.put("rose", new Browser("rose", "123", "browser"));
				users.put("kate", new Administrator("kate", "123", "administrator"));
			}
			else{
				ObjectInputStream readUser=new ObjectInputStream(new FileInputStream(userStorageFile));
				List<AbstractUser> userlist=(List<AbstractUser>) readUser.readObject();
				for (AbstractUser user: userlist) {
					users.put(user.getName(),user);
				}
				readUser.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("错误类型："+e.getMessage());
		}
	}

	public static void initdoc() {
		connectToDB = true;
		File DocStorageFile =new File(DCO_STORAGE_PATH);
		docs = new Hashtable<String, Doc>();
		try {
			if(!DocStorageFile.exists()){
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				docs = new Hashtable<String,Doc>();
				docs.put("0001",new Doc("0001","jack",timestamp,"Doc Source Java","Doc.java"));
			}
			else{
				ObjectInputStream readdoc = new ObjectInputStream(new FileInputStream(DocStorageFile));
				List<Doc> doclist = (List<Doc>) readdoc.readObject();
				for (Doc doc: doclist) {
					docs.put(doc.getId(),doc);
				}
				readdoc.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("错误类型："+e.getMessage());
		}
	}
	
	/**
	 * TODO 按档案编号搜索档案信息，返回null时表明未找到
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
	 * TODO 列出所有档案信息
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
	 * TODO 插入新的档案
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
			try {
				storageDoc();
			} catch (IOException e) {
				System.err.println("文档存储异常：" + e.getMessage());
			}
			return true;
		}
	}

	public static void storageDoc() throws IOException {
		File DocStorageFile = new File(DCO_STORAGE_PATH);
		if(!DocStorageFile.exists()){
			DocStorageFile.createNewFile();
		}
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DocStorageFile));
		Enumeration<Doc> e = docs.elements();
		List<Doc> DocList = new ArrayList<>();
		while(e.hasMoreElements()) {
			DocList.add(e.nextElement());
		}
		out.writeObject(DocList);
		out.close();
	}

	/**
	 * TODO:序列化存储用户
	 *
	 * @return void
	 * @throws IOException
	 */

	public static void storageUser() throws IOException {
		File userStorageFile=new File(USER_STORAGE_PATH);
		if(!userStorageFile.exists()){
			userStorageFile.createNewFile();
		}
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(userStorageFile));
		Enumeration<AbstractUser> e = users.elements();
		List<AbstractUser> userlist=new ArrayList<>();
		while(e.hasMoreElements()) {
			userlist.add(e.nextElement());
		}
		out.writeObject(userlist);
		out.close();
	}
	
	/**
     * TODO 按用户名搜索用户，返回null时表明未找到符合条件的用户
     * 
     * @param name 用户名 
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
     * TODO 按用户名、密码搜索用户，返回null时表明未找到符合条件的用户
     * 
     * @param name 用户名
     * @param password  密码
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
     * TODO 取出所有的用户 
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
     * TODO 修改用户信息
     * 
     * @param name 用户名
     * @param password 密码
     * @param role 角色
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
			try {
				storageUser();
			} catch (IOException e) {
				System.err.println("用户存储异常：" + e.getMessage());
			}
            return true;
        }else {
            return false;
        }
	}
	
	/**
     * TODO 插入新用户
     * 
     * @param name 用户名
     * @param password 密码
     * @param role 角色
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
     * TODO 删除指定用户
     * 
     * @param name 用户名
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
     * TODO 关闭数据库连接
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
