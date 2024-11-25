package console;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class DataProcessing {

    private static boolean connectedToDatabase = false;
    //数据连接
    private static Connection connection;
    //获取数据库操作对象
    private static Statement statement;
    //处理查询结果集
    private static ResultSet resultSet;

    public static void connectToDatabase(String driverName, String url, String user, String password)
            throws SQLException, ClassNotFoundException {
        // 加载驱动
        Class.forName(driverName);
        // 建立数据库连接
        connection = DriverManager.getConnection(url, user, password);
        connectedToDatabase = true;
    }
    //断开连接
    public static void disconnectFromDatabase() throws SQLException {
        if (connectedToDatabase) {
            // 释放资源,从后到前
            resultSet.close();
            statement.close();
            connection.close();
            connectedToDatabase = false;
        }
    }

    public static Doc searchDoc(String ID) throws SQLException {

        Doc doc = null;

        if (!connectedToDatabase)
            throw new SQLException("数据库未连接！");
//type_scroll_insensitive_   concur_read_n=only
        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "select * from doc_info where FileID='" + ID + "'"; // 获取FileID列中值为ID的数据，变量前后用+和'"、"'连接
        // 处理对象,数据操作集
        resultSet = statement.executeQuery(sql);
        // 获取数据库信息
        if (resultSet.next()) {

            String FileID = resultSet.getString("FileID");
            String Creator = resultSet.getString("Creator");
            Timestamp timestamp = Timestamp.valueOf(resultSet.getString("Timestamp")); // string转Timestamp
            String Description = resultSet.getString("Description");
            String FileName = resultSet.getString("FileName");

            doc = new Doc(FileID, Creator, timestamp, Description, FileName);
        }

        return doc;
    }

    public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename)
            throws SQLException {

        if (!connectedToDatabase)
            throw new SQLException("数据库未连接！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "INSERT INTO doc_info VALUES('" + ID + "','" + creator + "','" + timestamp + "','" + description
                + "','" + filename + "')";
        // 更新列表
        statement.executeUpdate(sql);

        return true;
    }

    public static Enumeration<Doc> getAllDocs() throws SQLException {

        if (!connectedToDatabase)
            throw new SQLException("数据库未连接！");

        // 建立哈希表
        Hashtable<String, Doc> docs = new Hashtable<String, Doc>();

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "select * from doc_info "; // 获取doc_info所有值

        // 处理对象
        resultSet = statement.executeQuery(sql);
        // 获取数据库信息
        while (resultSet.next()) {

            // 遍历存放
            String FileID = resultSet.getString("FileID");
            String Creator = resultSet.getString("Creator");
            Timestamp timestamp = Timestamp.valueOf(resultSet.getString("Timestamp")); // string转Timestamp
            String Description = resultSet.getString("Description");
            String FileName = resultSet.getString("FileName");

            docs.put(FileID, new Doc(FileID, Creator, timestamp, Description, FileName));
        }

        // 返回枚举容器
        Enumeration<Doc> e = docs.elements();
        return e;
    }

    public static AbstractUser searchUser(String name) throws SQLException {

        AbstractUser user = null;

        if (!connectedToDatabase)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "select * from user_info where UserName='" + name + "'"; // 获取UserName列中值为name的数据,变量前后用+和'"、"'连接

        // 处理对象
        resultSet = statement.executeQuery(sql);
        // 获取数据库信息
        if (resultSet.next()) {

            String UserName = resultSet.getString("UserName");
            String Password = resultSet.getString("Password");
            String Role = resultSet.getString("Role");

            user = new AbstractUser(UserName, Password, Role);
        }
//用找到的信息建立一个user
        return user;
    }

    public static AbstractUser searchUser(String name, String password) throws SQLException {

        AbstractUser user = null;

        if (!connectedToDatabase)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "select * from user_info where UserName='" + name + "'and password='" + password + "'"; // 获取UserName列中值为name的数据,变量前后用+和'"、"'连接

        // 处理对象
        resultSet = statement.executeQuery(sql);
        // 获取数据库信息
        if (resultSet.next()) {

            String UserName = resultSet.getString("UserName");
            String Password = resultSet.getString("Password");
            String Role = resultSet.getString("Role");

            user = new AbstractUser(UserName, Password, Role);

            if (Password.equals(password)) {
                return user;
            } else {
                return null;
            }
        }

        return null;
    }

    public static Enumeration<AbstractUser> getAllUser() throws SQLException {

        if (!connectedToDatabase)
            throw new SQLException("未连接到数据库！");

        // 建立哈希表
        Hashtable<String, AbstractUser> users = new Hashtable<String, AbstractUser>();

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "select * from user_info "; // 获取user_info所有值

        // 处理对象
        resultSet = statement.executeQuery(sql);
        // 获取数据库信息
        while (resultSet.next()) {

            // 遍历存放
            String UserName = resultSet.getString("UserName");
            String Password = resultSet.getString("Password");
            String Role = resultSet.getString("Role");

            users.put(UserName, new AbstractUser(UserName, Password, Role));
        }

        // 返回枚举容器
        Enumeration<AbstractUser> e = users.elements();
        return e;
    }

    public static boolean updateUser(String name, String password, String role) throws SQLException {

        if (!connectedToDatabase)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "UPDATE user_info SET Password='" + password + "',Role='" + role + "'WHERE UserName='" + name
                + "'"; // 更改user_info相关数据
        // 更新列表
        statement.executeUpdate(sql);

        return true;
    }

    public static boolean insertUser(String name, String password, String role) throws SQLException {

        if (!connectedToDatabase)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "INSERT INTO user_info VALUES('" + name + "','" + password + "','" + role + "')";
        // 更新列表
        statement.executeUpdate(sql);

        return true;
    }

    public static boolean deleteUser(String name) throws SQLException {

        if (!connectedToDatabase)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "DELETE FROM user_info WHERE UserName='" + name + "'";
        // 更新列表
        statement.executeUpdate(sql);

        return true;
    }
}
