package com.lingnan.summer.util;

/**
 * @author yinrui
 */

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class BaseView extends JFrame {
	
	public BaseView(int initWidth,int initHeight,String title){
		this.setTitle(title);
		this.setSize(initWidth, initHeight);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width-getWidth()/2,height-getHeight()/2);
		
		initView();
		
		this.setVisible(true);
		//this.setResizable(false);
		//int i = JOptionPane.showConfirmDialog(null, "确定要退出吗？","退出",JOptionPane.YES_NO_OPTION);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	protected abstract void initView();
	
}
