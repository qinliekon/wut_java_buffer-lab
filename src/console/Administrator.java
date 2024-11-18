package console;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

public class Administrator extends AbstractUser {
    public Administrator(String name,String password,String role) {
        super(name,password,role);
    }

    @Override
    public void showMenu() {
        System.out.println("****��ӭ���뵵������Ա�˵�****");
        System.out.println("1.�����û�");
        System.out.println("2.ɾ���û�");
        System.out.println("3.�޸��û�");
        System.out.println("4.�û��б�");
        System.out.println("5.���ص���");
        System.out.println("6.�����б�");
        System.out.println("7.�޸ĸ�������");
        System.out.println("8.�˳���¼");
        System.out.println("*****************************");
    }


    @Override
    public void func () {
        Scanner s = new Scanner(System.in);
        int flag = 0;
        boolean status = true;
        String name,psw,role;

        while (status) {
            System.out.println("��ѡ��˵���");
            flag = s.nextInt();

            switch (flag) {
                case 1:
                    System.out.println("��ֱ�������ӵ��û���������û����ͣ�Administrator/Operator/Browser��");
                    name = s.next();
                    psw = s.next();
                    role = s.next();
                    addRole(name,psw,role);
                    break;
                case 2:
                    System.out.println("������ɾ�����û���");
                    name = s.next();
                case 3:
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
                case 4:
                    try{
                        Enumeration e = DataProcessing.listUser();
                        System.out.println("�û���\t\t����\t\t��ɫ");
                        while (e.hasMoreElements()) {
                            AbstractUser abstractUser = (AbstractUser) e.nextElement();
                            System.out.println(abstractUser.getName() + "\t\t" + abstractUser.getPassword() + "\t\t" + abstractUser.getRole());
                        }
                    }catch (SQLException sqle) {

                }
                    break;
                case 5:
                    System.out.println("�������سɹ���");
                    break;
                case 6:
                    System.out.println("�����б����£�");
                    break;
                case 7:
                    System.out.println("�������޸ĺ�����룺");
                    psw = s.next();
                    this.resetRole(this.getName(),psw,this.getRole());
                    break;
                case 8:
                    System.out.println("�˳���½�ɹ���");
                    status = false;
                    break;
                default:
                    System.out.println("��Ų����ڣ����������룡");
                    break;
            }
        }
    }
}
