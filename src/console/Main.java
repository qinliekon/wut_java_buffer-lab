package console;

import java.sql.SQLException;
import java.util.Scanner;

/**
* TODO MAIN函数
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
                        System.out.println("欢迎再次使用");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("序号错误，请重新输入");
                        break;
                }
            }


    }
    
    public static void showmenu () {
        System.out.println("****欢迎进入档案管理系统****");
        System.out.println("1.登录");
        System.out.println("2.退出");
        System.out.println("***************************");
        System.out.println("请选择菜单：");
    }

    public static AbstractUser login () {
        String username,psw;
        AbstractUser abstractUser;
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("请输入用户名：");
            username = s.nextLine();

            System.out.println("请输入口令：");
            psw = s.nextLine();

            try {
                abstractUser = DataProcessing.searchUser(username, psw);
            } catch (SQLException e) {
                System.out.println("数据库异常：" + e.getMessage());
                abstractUser = null;
            }
            break;
        }


            return abstractUser;
    }

}