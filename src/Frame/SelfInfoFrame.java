package Frame;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import CS.Client;
import console.*;

@SuppressWarnings("serial")
public class SelfInfoFrame extends JFrame {

    private JPanel contentPane;
    private JLabel Username_Label;
    private JLabel OldPassword_Label;
    private JLabel NewPassword_Label;
    private JLabel ConfirmPassword_Label;
    private JLabel Role_Label;

    private JTextField Username_Txt;
    private JPasswordField OldPassword_Txt;
    private JPasswordField NewPassword_Txt;
    private JPasswordField ConfirmPassword_Txt;
    private JTextField Role_Txt;

    private JButton Confirm_Button;
    private JButton Return_Button;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 实验6无法实现单独调用界面, 不能实现用户与服务器的消息传递！
                    // 请从登录界面开始跳转

                    // 单独调用连接数据库
                    try {
                        SQLconnection.Connect();
                    } catch (ClassNotFoundException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    }

                    SelfInfoFrame frame = new SelfInfoFrame(new Administrator("kate", "123", "Administrator"));
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SelfInfoFrame(User user) {
        // 传入用户参数
        // 框架
        setResizable(false);
        setTitle("\u4E2A\u4EBA\u4FE1\u606F\u7BA1\u7406");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 502, 384);

        // 中间容器
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 用户名标签
        Username_Label = new JLabel("\u7528\u6237\u540D:");
        Username_Label.setFont(new Font("黑体", Font.PLAIN, 18));
        Username_Label.setBounds(100, 51, 72, 30);
        contentPane.add(Username_Label);

        // 旧密码标签
        OldPassword_Label = new JLabel("\u539F\u5BC6\u7801:");
        OldPassword_Label.setFont(new Font("黑体", Font.PLAIN, 18));
        OldPassword_Label.setBounds(100, 93, 72, 30);
        contentPane.add(OldPassword_Label);

        // 新密码标签
        NewPassword_Label = new JLabel("\u65B0\u5BC6\u7801:");
        NewPassword_Label.setFont(new Font("黑体", Font.PLAIN, 18));
        NewPassword_Label.setBounds(100, 135, 72, 30);
        contentPane.add(NewPassword_Label);

        // 确认密码标签
        ConfirmPassword_Label = new JLabel("\u786E\u8BA4\u65B0\u5BC6\u7801:");
        ConfirmPassword_Label.setFont(new Font("黑体", Font.PLAIN, 18));
        ConfirmPassword_Label.setBounds(63, 178, 109, 30);
        contentPane.add(ConfirmPassword_Label);

        // 角色标签
        Role_Label = new JLabel("\u89D2\u8272:");
        Role_Label.setFont(new Font("黑体", Font.PLAIN, 18));
        Role_Label.setBounds(118, 221, 57, 30);
        contentPane.add(Role_Label);

        // 用户名文本域
        Username_Txt = new JTextField();
        // 自动设置文本为用户名
        Username_Txt.setText(user.getName());
        Username_Txt.setEditable(false);
        Username_Txt.setBounds(186, 56, 154, 24);
        contentPane.add(Username_Txt);
        Username_Txt.setColumns(10);

        // 旧密码文本域
        OldPassword_Txt = new JPasswordField();
        OldPassword_Txt.setBounds(186, 98, 154, 24);
        contentPane.add(OldPassword_Txt);

        // 新密码文本域
        NewPassword_Txt = new JPasswordField();
        NewPassword_Txt.setBounds(186, 140, 154, 24);
        contentPane.add(NewPassword_Txt);

        // 确认密码文本域
        ConfirmPassword_Txt = new JPasswordField();
        ConfirmPassword_Txt.setBounds(186, 183, 154, 24);
        contentPane.add(ConfirmPassword_Txt);

        // 角色文本域
        Role_Txt = new JTextField();
        // 自动设置用户身份
        Role_Txt.setText(user.getRole());
        Role_Txt.setEditable(false);
        Role_Txt.setColumns(10);
        Role_Txt.setBounds(186, 226, 154, 24);
        contentPane.add(Role_Txt);

        // 确认按钮
        Confirm_Button = new JButton("\u786E\u8BA4");
        Confirm_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 修改密码事件
                ChangeSelfInfoActionPerformed(user, e);
            }
        });
        Confirm_Button.setFont(new Font("黑体", Font.PLAIN, 18));
        Confirm_Button.setBounds(118, 288, 113, 27);
        contentPane.add(Confirm_Button);

        // 返回按钮
        Return_Button = new JButton("\u8FD4\u56DE");
        Return_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 返回事件
                ReturnActionPerformed(e);
            }
        });
        Return_Button.setFont(new Font("黑体", Font.PLAIN, 18));
        Return_Button.setBounds(278, 288, 113, 27);
        contentPane.add(Return_Button);
    }

    // 修改密码
    private synchronized void ChangeSelfInfoActionPerformed(User user, ActionEvent evt) {

        String oldpassword = new String(OldPassword_Txt.getPassword());
        String newpassword = new String(NewPassword_Txt.getPassword());
        String confirmpassword = new String(ConfirmPassword_Txt.getPassword());

        // 检查是否为空
        if (StringUtil.isEmpty(oldpassword)) {
            JOptionPane.showMessageDialog(null, "未输入旧密码！");
            return;
        }
        if (StringUtil.isEmpty(newpassword)) {
            JOptionPane.showMessageDialog(null, "未输入新密码！");
            return;
        }
        if (StringUtil.isEmpty(confirmpassword)) {
            JOptionPane.showMessageDialog(null, "请输入确认密码！");
            return;
        }

        // 密码匹配
        try {
            if (DataProcessing.searchUser(user.getName(), oldpassword) == null) {
                JOptionPane.showMessageDialog(null, "用户名与原密码不匹配！");
                return;
            }
            if (!newpassword.equals(confirmpassword)) {
                JOptionPane.showMessageDialog(null, "两次输入的新密码不相同！");
                return;
            }

            // 修改密码
            if (user.changeSelfInfo(newpassword)) {

                // 发送修改密码消息
                try {
                    Client.SendMessage("修改密码");
                    Client.ReceiveMessage();
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    return;
                }

                // 清空
                this.OldPassword_Txt.setText("");
                this.NewPassword_Txt.setText("");
                this.ConfirmPassword_Txt.setText("");

                JOptionPane.showMessageDialog(null, "修改成功!");
                return;

            } else {
                JOptionPane.showMessageDialog(null, "修改失败!");
                return;
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    // 返回
    private void ReturnActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

