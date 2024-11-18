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
        System.out.println("****欢迎进入档案管理员菜单****");
        System.out.println("1.新增用户");
        System.out.println("2.删除用户");
        System.out.println("3.修改用户");
        System.out.println("4.用户列表");
        System.out.println("5.下载档案");
        System.out.println("6.档案列表");
        System.out.println("7.修改个人密码");
        System.out.println("8.退出登录");
        System.out.println("*****************************");
    }


    @Override
    public void func () {
        Scanner s = new Scanner(System.in);
        int flag = 0;
        boolean status = true;
        String name,psw,role;

        while (status) {
            System.out.println("请选择菜单：");
            flag = s.nextInt();

            switch (flag) {
                case 1:
                    System.out.println("请分别输入添加的用户名，口令，用户类型（Administrator/Operator/Browser）");
                    name = s.next();
                    psw = s.next();
                    role = s.next();
                    addRole(name,psw,role);
                    break;
                case 2:
                    System.out.println("请输入删除的用户名");
                    name = s.next();
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
                    try{
                        Enumeration e = DataProcessing.listUser();
                        System.out.println("用户名\t\t密码\t\t角色");
                        while (e.hasMoreElements()) {
                            AbstractUser abstractUser = (AbstractUser) e.nextElement();
                            System.out.println(abstractUser.getName() + "\t\t" + abstractUser.getPassword() + "\t\t" + abstractUser.getRole());
                        }
                    }catch (SQLException sqle) {

                }
                    break;
                case 5:
                    System.out.println("档案下载成功！");
                    break;
                case 6:
                    System.out.println("档案列表如下：");
                    break;
                case 7:
                    System.out.println("请输入修改后的密码：");
                    psw = s.next();
                    this.resetRole(this.getName(),psw,this.getRole());
                    break;
                case 8:
                    System.out.println("退出登陆成功！");
                    status = false;
                    break;
                default:
                    System.out.println("序号不存在，请重新输入！");
                    break;
            }
        }
    }
}
