package com.lingnan.summer.view;

/**
 * @author yinrui
 */


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.lingnan.summer.componet.BGPanel;
import com.lingnan.summer.domain.User;
import com.lingnan.summer.service.UserService;
import com.lingnan.summer.service.impl.UserServiceImpl;
import com.lingnan.summer.view.base.BaseView;



public class LoginView extends BaseView implements ActionListener{
	
	public LoginView(){
		super(1000,700,"Login");
		this.setResizable(false);
	}
	
	private JPanel containerPanel,namePanel,passwordPanel;
	private JLabel nameLabel,pwdLabel;
	private JTextField nameTF;
    private JPasswordField pwdTF;
    private JButton loginBtn;
	
	@Override
	protected void initView() {
		Image background = null;
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		try {
			background = ImageIO.read(new File("static\\background\\background3.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//顶层容器
		containerPanel = new BGPanel(background);
		containerPanel.setLayout(null);
		
		//用户名容器
		namePanel = new JPanel();
		namePanel.setOpaque(false);
		ImageIcon lnicon = new ImageIcon("static\\icon\\loginname1.png");
		//设置icon图标大小
		lnicon.setImage(lnicon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		nameLabel = new JLabel(lnicon);
		nameTF = new JTextField("zs",15);
		nameTF.setPreferredSize(new Dimension (15,40)); 
		nameTF.setFont(new Font("Microsoft Uighur",Font.ITALIC,18));
		nameTF.setBorder(null);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);
		namePanel.setBounds(340, 270, 300, 100);
		
		//密码容器
		passwordPanel = new JPanel();
		passwordPanel.setOpaque(false);
		ImageIcon lpicon = new ImageIcon("static\\icon\\loginpwd.png");
		lpicon.setImage(lpicon.getImage().getScaledInstance(38, 38, Image.SCALE_DEFAULT));
		pwdLabel = new JLabel(lpicon);
		pwdTF = new JPasswordField("123",15);
		pwdTF.setPreferredSize(new Dimension (15,40)); 
		pwdTF.setFont(new Font("Microsoft Uighur",Font.ITALIC,18));
		pwdTF.setBorder(null);
		passwordPanel.add(pwdLabel);
		passwordPanel.add(pwdTF);
		passwordPanel.setBounds(340, 370, 300, 100);
		
		//按钮
		loginBtn = new JButton("Login");
		loginBtn.setBounds(455, 470, 100, 40);
		loginBtn.setContentAreaFilled(false);//透明
		loginBtn.setFont(new java.awt.Font("华文行楷",1,27));//字体
		loginBtn.setForeground(Color.white);//字体颜色
		//loginBtn.setBackground(new Color(128,209,214));//按钮颜色
		//绑定监听
		loginBtn.addActionListener(this);
		
		containerPanel.add(namePanel);
		containerPanel.add(passwordPanel);
		containerPanel.add(loginBtn);
		
		Container container = getContentPane();
		container.add(containerPanel);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = nameTF.getText();
		String password = new String(pwdTF.getPassword());
		//参数检查
		UserService userService = new UserServiceImpl();
		User user = userService.login(name, password);
		if(user == null){
			JOptionPane.showMessageDialog(this, "账号或密码错误");
		}else {
			//跳转主界面
			new MainView();
			this.dispose();
		}
	}
	
	public static void main(String[] args) {
		LoginView loginview = new LoginView();
	}
}
