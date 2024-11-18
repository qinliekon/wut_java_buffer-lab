package console;

import java.io.IOException;
import java.util.Scanner;

public class Browser extends AbstractUser {
    public Browser (String name,String password,String role) {
        super(name,password,role);
    }

    @Override
    public void showMenu() {
        System.out.println("****欢迎进入档案浏览员菜单****");
        System.out.println("1.下载档案");
        System.out.println("2.档案列表");
        System.out.println("3.修改个人密码");
        System.out.println("4.退出登录");
        System.out.println("*****************************");
    }

    @Override
    public void func () {
        Scanner s = new Scanner(System.in);
        int flag = 0;
        boolean status = true;
        String name,psw,role,FileName;

        while (status) {
            System.out.println("请选择菜单：");
            flag = s.nextInt();
            s.nextLine();
            switch (flag) {
                case 1:
                    System.out.println("请输入文件名称：");
                    FileName = s.nextLine();
                    try {
                        this.downloadFile(FileName);
                    } catch (IOException e) {
                        System.out.println("文件访问错误：" + e.getMessage());
                        continue;
                    }
                    break;
                case 2:
                    System.out.println("档案列表如下：");
                    break;
                case 3:
                    while (true) {
                        System.out.println("请输入要更改的用户名，修改后的密码和用户类型（Administrator/Operator/Browser）：");
                        name = s.next();
                        psw = s.next();
                        role = s.next();
                        if (this.resetRole(name, psw, role)) {
                            break;
                        }
                        System.out.println("未找到该用户，请重新输入");
                    }
                    break;
                case 4:
                    System.out.println("退出登录成功！");
                    status = false;
                    break;
                default:
                    System.out.println("序号不存在，请重新输入");
                    break;
            }
        }
    }
}
