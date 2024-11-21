package console;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Operator extends AbstractUser {
    public Operator (String name,String password,String role) {
        super(name,password,role);
    }

    @Override
    public void showMenu(){
        System.out.println("****��ӭ���뵵������Ա�˵�****");
        System.out.println("1.�ϴ�����");
        System.out.println("2.���ص���");
        System.out.println("3.�����б�");
        System.out.println("4.�޸ĸ�������");
        System.out.println("5.�˳���¼");
        System.out.println("*****************************");
    }

    @Override
    public void func () {
        Scanner s = new Scanner(System.in);
        int flag = 0;
        boolean status = true;
        String name,psw,role,FileName,id,des,filepath;

        while (status) {
            System.out.println("��ѡ��˵���");
            flag = s.nextInt();
            s.nextLine();
            switch (flag) {
                case 1:
                    System.out.println("�������ļ�·��");
                    filepath = s.nextLine();
                    System.out.println("�������ļ�����");
                    FileName = s.nextLine();

                    try {
                        fileOperate(filepath,uploadpath + FileName);
                    } catch (IOException e) {
                        System.err.println("�ϴ�ʧ�ܣ�" + e.getMessage());
                    }

                    System.out.println("�������ļ������ţ�");
                    id = s.nextLine();
                    try {
                        if (DataProcessing.searchDoc(id) != null) {
                            System.out.println("�õ������Ѿ����ڣ�");
                            continue;
                        }
                    } catch (SQLException e) {
                        System.out.println("�ļ��ϴ�ʧ�ܣ�" + e.getMessage());
                        continue;
                    }
                    System.out.println("�������ļ�������");
                    des = s.nextLine();




                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    try {
                        DataProcessing.insertDoc(id, this.getName(), timestamp, des, FileName);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("�ϴ��ɹ���");
                    break;
                case 2:
                    System.out.println("�����뵵���ţ�");
                    FileName = s.nextLine();
                    try {
                        this.downloadFile(FileName);
                    } catch (IOException e) {
                        System.out.println("�ļ����ʴ���" + e.getMessage());
                        continue;
                    } catch (SQLException e) {
                        System.out.println("���ݿ����" +e.getMessage());
                        continue;
                    }
                    System.out.println("���سɹ���");
                    break;
                case 3:
                    try {
                        this.showFileList();
                    } catch (SQLException e) {
                        System.out.println("���ݿ��쳣��" + e.getSQLState());
                    }
                    break;
                case 4:
                    while (true) {
                        System.out.println("������Ҫ���ĵ��û������޸ĺ��������û����ͣ�Administrator/Operator/Browser����");
                        name = s.next();
                        psw = s.next();
                        role = s.next();
                        if (this.resetRole(name, psw, role)) {
                            break;
                        }
                        System.out.println("δ�ҵ����û�������������");
                    }
                    break;
                case 5:
                    System.out.println("�˳���¼�ɹ���");
                    status = false;
                    break;
                default:
                    System.out.println("��Ų����ڣ����������룡");
                    break;
            }
        }
    }
}
