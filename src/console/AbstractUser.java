package console;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import  java.util.Enumeration;
import java.util.Scanner;

/**
 * TODO �����û��࣬Ϊ���û������ṩģ��
 *
 * @author gongjing
 * @date 2016/10/13
 */
public  abstract  class  AbstractUser implements Serializable {
	private  String name;
	private  String password;
	private  String role;

	public static final String uploadpath = "C:\\Users\\liekon\\Desktop\\��ѧ\\�γ�\\���\\java���߳�\\java-buffer\\uploadfile\\";
	public static final String downloadpath = "C:\\Users\\liekon\\Desktop\\��ѧ\\�γ�\\���\\java���߳�\\java-buffer\\donwloadfile\\";

	AbstractUser(String name,String password,String role){
		this .name=name;
		this .password=password;
		this .role=role;
	}


	/**
	 * TODO �޸��û�������Ϣ
	 *
	 * @param password ����
	 * @return boolean �޸��Ƿ�ɹ�
	 * @throws SQLException
	 */
	public  boolean  changeSelfInfo(String password) throws  SQLException{
		if  (DataProcessing.updateUser(name, password, role)){
			this .password=password;
			System.out.println("�޸ĳɹ�");
			return  true ;
		}else  {
			return  false ;
		}
	}

	/**
	 * TODO ���ص����ļ�
	 *
	 * @param id �������
	 * @return boolean �����Ƿ�ɹ�
	 * @throws SQLException,IOException
	 */
	public  boolean  downloadFile(String id) throws SQLException,IOException{
//boolean result=false;
		byte[] buffer = new byte[1024];
		Doc doc = DataProcessing.searchDoc(id);

		if  (doc == null) {
			return false;
		}

		File tempFile = new File(uploadpath + doc.getFilename());
		String filename = tempFile.getName();


		BufferedInputStream infile = new  BufferedInputStream(new FileInputStream(tempFile));
		BufferedOutputStream targetfile = new  BufferedOutputStream(new FileOutputStream(downloadpath + filename));

		while (true) {
			int byteRead = infile.read(buffer);
			if (byteRead == -1) {
				break;
			}
			targetfile.write(buffer,0,byteRead);
		}
		infile.close();
		targetfile.close();

		return true;
	}

	public static void fileOperate(String uploadpath,String downloadpath) throws IOException
	{
		File downfile = new File(downloadpath);
		File upfile = new File(uploadpath);
		int i, j=0;
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(upfile));
		BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(downfile,true));

		while ((i=fis.read())!=-1) {
			fos.write((byte)i);
			j++;
		}
		fis.close();
		fos.close();
	}

	/**
	 * TODO չʾ�����ļ��б�
	 *
	 * @param
	 * @return void
	 * @throws SQLException
	 */
	public  void  showFileList() throws  SQLException{
		Enumeration<Doc> e= DataProcessing.listDoc();
		Doc doc;
		while ( e.hasMoreElements() ){
			doc=e.nextElement();
			System.out.println("Id:"+doc.getId()+"\t Creator:" +doc.getCreator()+"\t Time:" +doc.getTimestamp()+"\t Filename:"+doc.getFilename());
			System.out.println("Description:"+doc.getDescription());
		}

	}

	/**
	 * TODO չʾ�˵�����������Ը���
	 *
	 * @param
	 * @return void
	 * @throws
	 */
	public  abstract  void  showMenu();

	/**
	 * TODO �˳�ϵͳ
	 *
	 * @param
	 * @return void
	 * @throws
	 */
	public  void  exitSystem(){
		System.out.println("ϵͳ�˳�, ллʹ�� ! ");
		System.exit(0);
	}

	public  String getName() {
		return  name;
	}

	public  void  setName(String name) {
		this .name = name;
	}

	public  String getPassword() {
		return  password;
	}

	public  void  setPassword(String password) {
		this .password = password;
	}

	public  String getRole() {
		return  role;
	}

	public  void  setRole(String role) {
		this .role = role;
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
