package com.lingnan.summer.view.base;

import java.awt.Toolkit;

import javax.swing.JFrame;

public abstract class BaseView extends JFrame {
	
	public BaseView(int initWidth,int initHeigth,String title) {
		this.setTitle(title);
		this.setSize(initWidth,initHeigth);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width-getWidth())/2,(height-getHeight())/2);
		
		initView();
		
		
		this.setVisible(true);
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	protected abstract void initView();
}
