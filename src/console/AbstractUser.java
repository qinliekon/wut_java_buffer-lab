package console;

import java.io.IOException;
import java.sql.SQLException;

/**
 * TODO �����û��࣬Ϊ���û������ṩģ��
 *
 * @author gongjing
 * @date 2016/10/13
 */
public abstract class AbstractUser {
	private String name;
	private String password;
	private String role;
	static final double EXCEPTION_PROBABILITY=0.9;
	
	AbstractUser(String name,String password,String role){
		this.name=name;
		this.password=password;
		this.role=role;				
	}
	
	
	/**
	 * TODO �޸��û�������Ϣ
	 * 
	 * @param password ����
	 * @return boolean �޸��Ƿ�ɹ�
	 * @throws SQLException
	*/
	public boolean changeSelfInfo(String password) throws SQLException{
		if (DataProcessing.updateUser(name, password, role)){
			this.password=password;
			System.out.println("�޸ĳɹ�");
			return true;
		}else {
			return false;
		}
	}	
	
	/**
	 * TODO ���ص����ļ�
	 * 
	 * @param filename �ļ���
	 * @return boolean �����Ƿ�ɹ�
	 * @throws IOException
	*/
	public boolean downloadFile(String filename) throws IOException{
		double ranValue=Math.random();
		if (ranValue>EXCEPTION_PROBABILITY) {
			throw new IOException( "Error in accessing file" );}
		System.out.println("�����ļ���" + filename);
		return true;
	}
	
	/**
	 * TODO չʾ�����ļ��б�
	 * 
	 * @param 
	 * @return void
	 * @throws SQLException 
	*/
	public void showFileList() throws SQLException{
		double ranValue=Math.random();
		if (ranValue>EXCEPTION_PROBABILITY) {
			throw new SQLException( "Error in accessing file DB" );}
		System.out.println("�б�... ...");
	}
	
	
	
	/**
	 * TODO չʾ�˵�����������Ը���
	 *   
	 * @param 
	 * @return void
	 * @throws  
	*/
	public abstract void showMenu();
	
	/**
	 * TODO �˳�ϵͳ
	 *   
	 * @param 
	 * @return void
	 * @throws  
	*/
	public void exitSystem(){
		System.out.println("ϵͳ�˳�, ллʹ�� ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public abstract void func();

	public boolean addRole (String name, String password,String role) {
		try {
			return DataProcessing.insertUser(name, password,role);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean deleteRole (String name) {
		try {
			return DataProcessing.deleteUser(name);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public boolean resetRole (String name, String password, String role) {
		try {
			return DataProcessing.updateUser(name,password,role);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
}

	}
	

}
