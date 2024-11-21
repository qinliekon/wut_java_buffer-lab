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
        System.out.println("****欢迎进入档案操作员菜单****");
        System.out.println("1.上传档案");
        System.out.println("2.下载档案");
        System.out.println("3.档案列表");
        System.out.println("4.修改个人密码");
        System.out.println("5.退出登录");
        System.out.println("*****************************");
    }

    @Override
    public void func () {
        Scanner s = new Scanner(System.in);
        int flag = 0;
        boolean status = true;
        String name,psw,role,FileName,id,des,filepath;

        while (status) {
            System.out.println("请选择菜单：");
            flag = s.nextInt();
            s.nextLine();
            switch (flag) {
                case 1:
                    System.out.println("请输入文件路径");
                    filepath = s.nextLine();
                    System.out.println("请输入文件名：");
                    FileName = s.nextLine();

                    try {
                        fileOperate(filepath,uploadpath + FileName);
                    } catch (IOException e) {
                        System.err.println("上传失败：" + e.getMessage());
                    }

                    System.out.println("请输入文件档案号：");
                    id = s.nextLine();
                    try {
                        if (DataProcessing.searchDoc(id) != null) {
                            System.out.println("该档案号已经存在！");
                            continue;
                        }
                    } catch (SQLException e) {
                        System.out.println("文件上传失败：" + e.getMessage());
                        continue;
                    }
                    System.out.println("请输入文件描述：");
                    des = s.nextLine();




                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    try {
                        DataProcessing.insertDoc(id, this.getName(), timestamp, des, FileName);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("上传成功！");
                    break;
                case 2:
                    System.out.println("请输入档案号：");
                    FileName = s.nextLine();
                    try {
                        this.downloadFile(FileName);
                    } catch (IOException e) {
                        System.out.println("文件访问错误：" + e.getMessage());
                        continue;
                    } catch (SQLException e) {
                        System.out.println("数据库错误：" +e.getMessage());
                        continue;
                    }
                    System.out.println("下载成功！");
                    break;
                case 3:
                    try {
                        this.showFileList();
                    } catch (SQLException e) {
                        System.out.println("数据库异常：" + e.getSQLState());
                    }
                    break;
                case 4:
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
                case 5:
                    System.out.println("退出登录成功！");
                    status = false;
                    break;
                default:
                    System.out.println("序号不存在，请重新输入！");
                    break;
            }
        }
    }
}
