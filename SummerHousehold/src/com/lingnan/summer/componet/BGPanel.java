package com.lingnan.summer.componet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class BGPanel extends JPanel {
	
	private Image image;
	public BGPanel(Image image){
		this.image = image;
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image,0,0,getWidth(),getHeight(),this);
		
	}
	
}
