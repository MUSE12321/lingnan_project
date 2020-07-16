package com.lingnan.summer.setdialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lingnan.summer.componet.BGPanel;
import com.lingnan.summer.domain.Device;
import com.lingnan.summer.domain.Task;
import com.lingnan.summer.service.TaskService;
import com.lingnan.summer.service.impl.TaskServiceImpl;
import com.lingnan.summer.view.LivingView;

public class CurtainSetDialog extends JDialog implements ActionListener{
	
	private Device device;
	private int did,nnewid;
	private String name;
	private LivingView livingView;
	
	public CurtainSetDialog(JFrame parent,LivingView livingView){
		super(parent,"Light");
		this.livingView = livingView;
		setSize(800,500);
		initView();
	}
	
	public CurtainSetDialog(JFrame parent,LivingView livingView,Device device){
		this(parent,livingView);
		this.device = device;
		did=device.getDid();
		name=device.getDname();
	}
	
	private JPanel containerPanel,switchPanel;
	private JLabel switchLabel;
	private JButton openBtn,offBtn,submitBtn;
	private String switchtext = "";
	private TaskService taskService = new TaskServiceImpl();
	
	private void initView(){
		//背景
		Image background = null;
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		try {
			background = ImageIO.read(new File("static\\background\\curtain.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//顶层容器
		containerPanel = new BGPanel(background);
		containerPanel.setLayout(null);
		
		//开关容器
		switchPanel = new JPanel();
		switchPanel.setOpaque(false);//控件透明
		ImageIcon switchicon = new ImageIcon("static\\icon\\开关3 白.png");
		switchicon.setImage(switchicon.getImage().getScaledInstance(38, 38, Image.SCALE_DEFAULT));
		switchLabel = new JLabel(switchicon);
		switchPanel.add(switchLabel);
		switchPanel.setBounds(120, 190, 300, 100);
		
	    //按钮开
		openBtn = new JButton("Open");
		openBtn.setContentAreaFilled(false);//透明
		openBtn.setFont(new java.awt.Font("Microsoft YaHei UI",1,15));//字体
		openBtn.setForeground(Color.WHITE);//字体颜色
		openBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));//按钮边框颜色
		openBtn.setBounds(340, 190, 80, 40);
		openBtn.addActionListener(this);//监听
		//按钮关
		offBtn = new JButton("Shut");
		offBtn.setContentAreaFilled(false);//透明
		offBtn.setFont(new java.awt.Font("Microsoft YaHei UI",1,15));//字体
		offBtn.setForeground(Color.WHITE);//字体颜色
		offBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));//按钮边框颜色
		offBtn.setBounds(430, 190, 80, 40);
		offBtn.addActionListener(this);//监听
		
		//提交按钮
		submitBtn = new JButton("Submit");
		submitBtn.setContentAreaFilled(false);//透明
		submitBtn.setFont(new java.awt.Font("Microsoft YaHei UI",1,15));//字体
		submitBtn.setForeground(Color.WHITE);//字体颜色
		submitBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));//按钮边框颜色
		submitBtn.setBounds(360, 280, 80, 40);
		submitBtn.addActionListener(this);//监听
		
		//添加至顶层容器
		containerPanel.add(switchPanel);
		containerPanel.add(openBtn);
		containerPanel.add(offBtn);
		containerPanel.add(submitBtn);
		
		Container container = getContentPane();
		container.add(containerPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String newname = name;
		Object source = e.getSource();
		
		if(source==openBtn){
			switchtext = "开";
		}
		else if(source==offBtn){
			switchtext ="关";
		}
		
		else if(source==submitBtn){
			Task task = new Task();
			task.setDname(newname);
			task.setInstruction(switchtext);
			int result = taskService.add(task);
			if(result==1) {
				this.dispose();
				JOptionPane.showMessageDialog(this,"提交成功","提示",JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this,"提交失败","提示",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
