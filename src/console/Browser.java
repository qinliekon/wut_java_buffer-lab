package console;

import java.io.IOException;
import java.util.Scanner;

public class Browser extends AbstractUser {
    public Browser (String name,String password,String role) {
        super(name,password,role);
    }

    @Override
    public void showMenu() {
        System.out.println("****��ӭ���뵵�����Ա�˵�****");
        System.out.println("1.���ص���");
        System.out.println("2.�����б�");
        System.out.println("3.�޸ĸ�������");
        System.out.println("4.�˳���¼");
        System.out.println("*****************************");
    }

    @Override
    public void func () {
        Scanner s = new Scanner(System.in);
        int flag = 0;
        boolean status = true;
        String name,psw,role,FileName;

        while (status) {
            System.out.println("��ѡ��˵���");
            flag = s.nextInt();
            s.nextLine();
            switch (flag) {
                case 1:
                    System.out.println("�������ļ����ƣ�");
                    FileName = s.nextLine();
                    try {
                        this.downloadFile(FileName);
                    } catch (IOException e) {
                        System.out.println("�ļ����ʴ���" + e.getMessage());
                        continue;
                    }
                    break;
                case 2:
                    System.out.println("�����б����£�");
                    break;
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
                    System.out.println("�˳���¼�ɹ���");
                    status = false;
                    break;
                default:
                    System.out.println("��Ų����ڣ�����������");
                    break;
            }
        }
    }
}
