package console;

import java.sql.SQLException;
import java.util.Scanner;

/**
* TODO MAIN����
* @author qingliu
* @date 2024/11/14
* */
public class Main {

    public static void main (String[] args) throws Exception{

        int mainMenuflag;
        AbstractUser abstractUser;
        Scanner s = new Scanner(System.in);

            while (true) {
                showmenu();
                mainMenuflag = s.nextInt();
                switch (mainMenuflag) {
                    case 1:
                        abstractUser = login();
                        try {
                            abstractUser.showMenu();
                        } catch (NullPointerException e) {
                            continue;
                        }

                        abstractUser.func();
                        break;
                    case 2:
                        DataProcessing.disconnectFromDatabase();
                        System.out.println("��ӭ�ٴ�ʹ��");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("��Ŵ�������������");
                        break;
                }
            }


    }
    
    public static void showmenu () {
        System.out.println("****��ӭ���뵵������ϵͳ****");
        System.out.println("1.��¼");
        System.out.println("2.�˳�");
        System.out.println("***************************");
        System.out.println("��ѡ��˵���");
    }

    public static AbstractUser login () {
        String username,psw;
        AbstractUser abstractUser;
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("�������û�����");
            username = s.nextLine();

            System.out.println("��������");
            psw = s.nextLine();

            try {
                abstractUser = DataProcessing.searchUser(username, psw);
            } catch (SQLException e) {
                System.out.println("���ݿ��쳣��" + e.getMessage());
                abstractUser = null;
            }
            break;
        }


            return abstractUser;
    }

}