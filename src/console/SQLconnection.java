package console;

import java.sql.SQLException;

public class SQLconnection {
    // 声明数据库的URL
    static String Url = "jdbc:mysql://localhost:3306/datalibrary"; //不用安全连接

    // 数据库用户
    static String User = "root";
    static String Password = "Qc040718";

    // 连接数据库函数
    public static void Connect() throws ClassNotFoundException, SQLException {
        DataProcessing.connectToDatabase(Url, User, Password);
    }

    // 断开数据库函数
    public static void Disconnect() throws SQLException {
        DataProcessing.disconnectFromDatabase();
    }

}
