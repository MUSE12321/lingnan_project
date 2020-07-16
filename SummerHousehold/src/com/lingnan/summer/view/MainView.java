package com.lingnan.summer.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import com.lingnan.summer.componet.BGPanel;
import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.domain.Record;
import com.lingnan.summer.service.RecordService;
import com.lingnan.summer.service.impl.RecordServiceImpl;
import com.lingnan.summer.util.DateUtil;
import com.lingnan.summer.util.FontUtil;
import com.lingnan.summer.view.base.BaseView;

public class MainView extends BaseView implements ActionListener, MouseListener {

	public static final Font afterfont = new Font("宋体", Font.BOLD, 22);

	JMenuBar menuBar;
	JMenu settingMenu, helpMenu;

	JMenuItem equipmentMenuItem, alarmMenuItem;

	JSplitPane containerPanel;

	CardLayout rightPanelLayout;
	JPanel leftPanel, rightPanel;

	JLabel logoLabel, livingHomeLabel, kitchenLabel, bedroomLabel,
			balconyLabel, toiletLabel;

	JPanel bottomPanel;

	JLabel timeLabel;

	Timer timer;

	java.util.Timer analyzeTimer;

	public MainView() {
		super(1000, 700, "主界面");
		timer = new Timer(1000, this);
		timer.start();
		analyzeTimer = new java.util.Timer();
		analyzeTimer.schedule(new TimerTask() {                    //*

			@Override
			public void run() {
				// 遍历record -->风险risk =高？ -->报警(在报警表生成一条记录)
				
				  RecordService recordService = new RecordServiceImpl(); 
				  List<Record> records = recordService.query(); 
				  for(int i=0;i<records.size();i++){
				  System.out.println(records.get(i)); }
				 
			}
		}, 1000, 60000);                                         // *
	}

	@Override
	protected void initView() {

		menuBar = new JMenuBar();

		settingMenu = new JMenu("设置");

		helpMenu = new JMenu("帮助");

		equipmentMenuItem = new JMenuItem("设备管理", new ImageIcon(""));
		alarmMenuItem = new JMenuItem("警报管理", new ImageIcon(""));

		settingMenu.add(equipmentMenuItem);
		settingMenu.add(alarmMenuItem);

		// 监听
		equipmentMenuItem.addActionListener(this);
		alarmMenuItem.addActionListener(this);

		menuBar.add(settingMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		leftPanel = new JPanel();
		leftPanel.setBackground(new Color(0x020B10));
		leftPanel.setLayout(new GridLayout(7, 1));

		ImageIcon logo = new ImageIcon("static\\icon\\User1.png");
		logo.setImage(logo.getImage().getScaledInstance(80, 80,
				Image.SCALE_DEFAULT));
		logoLabel = new JLabel(logo);
		logoLabel.addMouseListener(this);
		leftPanel.add(logoLabel);

		livingHomeLabel = new JLabel("客厅",
				new ImageIcon("static\\icon\\客厅.png"), JLabel.LEFT);
		livingHomeLabel.setFont(FontUtil.menuFont);
		livingHomeLabel.addMouseListener(this);
		leftPanel.add(livingHomeLabel);

		kitchenLabel = new JLabel("厨房", new ImageIcon("static\\icon\\厨房.png"),
				JLabel.LEFT);
		kitchenLabel.setFont(FontUtil.menuFont);
		kitchenLabel.addMouseListener(this);
		leftPanel.add(kitchenLabel);

		bedroomLabel = new JLabel("卧室", new ImageIcon("static\\icon\\卧室.png"),
				JLabel.LEFT);
		bedroomLabel.setFont(FontUtil.menuFont);
		bedroomLabel.addMouseListener(this);
		leftPanel.add(bedroomLabel);

		balconyLabel = new JLabel("阳台", new ImageIcon("static\\icon\\走廊.png"),
				JLabel.LEFT);
		balconyLabel.setFont(FontUtil.menuFont);
		balconyLabel.addMouseListener(this);
		leftPanel.add(balconyLabel);

		toiletLabel = new JLabel("洗手间", new ImageIcon("static\\icon\\卫生间.png"),
				JLabel.LEFT);
		toiletLabel.setFont(FontUtil.menuFont);
		toiletLabel.addMouseListener(this);
		leftPanel.add(toiletLabel);

		rightPanelLayout = new CardLayout();
		// 用户
		Image bgImage = null;
		try {
			bgImage = ImageIO.read(new File(
					"static\\background\\background3.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JPanel mainPanel = new BGPanel(bgImage);

		rightPanel = new JPanel(rightPanelLayout);
		rightPanel.add(mainPanel, "0");

		// 客厅表视图
		JPanel livingPanel = new LivingView(this);
		rightPanel.add(livingPanel, "1");

		// 厨房表视图
		JPanel kitchenPanel = new KitchenView(this);
		rightPanel.add(kitchenPanel, "2");

		// 卧室表视图
		JPanel bedPanel = new BedView(this);
		rightPanel.add(bedPanel, "3");

		// 阳台表视图
		JPanel galleryPanel = new GalleryView(this);
		rightPanel.add(galleryPanel, "4");

		// 洗手间视图
		JPanel bathPanel = new BathView(this);
		rightPanel.add(bathPanel, "5");

		// 用户表视图
		JPanel logoPanel = new UserView(this);
		rightPanel.add(logoPanel, "6");

		containerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
				rightPanel);
		containerPanel.setDividerLocation(160);
		containerPanel.setDividerSize(0);

		bottomPanel = new JPanel();// 默认的布局是流式布局

		bottomPanel.setBackground(new Color(0xE4E4E4));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		timeLabel = new JLabel(DateUtil.dateToString(new Date(), null));

		bottomPanel.add(timeLabel);
		Container container = getContentPane();
		container.add(containerPanel, "Center");
		container.add(bottomPanel, "South");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == equipmentMenuItem) {
			new DeviceView();
		} else if (source == alarmMenuItem) {
			new AlarmView();
		} else if (timer == source) {
			timeLabel.setText(DateUtil.dateToString(new Date(), null));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {// 下面是改过的
		Object source = e.getSource();
		if (source == livingHomeLabel) {
			rightPanelLayout.show(rightPanel, "1");
		} else if (source == kitchenLabel) {
			rightPanelLayout.show(rightPanel, "2");
		} else if (source == bedroomLabel) {
			rightPanelLayout.show(rightPanel, "3");
		} else if (source == balconyLabel) {
			rightPanelLayout.show(rightPanel, "4");
		} else if (source == toiletLabel) {
			rightPanelLayout.show(rightPanel, "5");
		} else if (source == logoLabel) {
			rightPanelLayout.show(rightPanel, "6");
		} else {
			rightPanelLayout.show(rightPanel, "0");
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {// 这里加了icon路径
		Object source = e.getSource();
		if (source == livingHomeLabel) {
			livingHomeLabel.setIcon(new ImageIcon("static\\icon\\客厅1.png"));
			livingHomeLabel.setFont(afterfont);

			livingHomeLabel.setForeground(Color.white);
		}
		if (source == kitchenLabel) {
			kitchenLabel.setIcon(new ImageIcon("static\\icon\\厨房1.png"));
			kitchenLabel.setFont(afterfont);
			kitchenLabel.setForeground(Color.white);
		}
		if (source == bedroomLabel) {
			bedroomLabel.setIcon(new ImageIcon("static\\icon\\卧室1.png"));
			bedroomLabel.setFont(afterfont);
			bedroomLabel.setForeground(Color.white);
		}
		if (source == balconyLabel) {
			balconyLabel.setIcon(new ImageIcon("static\\icon\\阳台1.png"));
			balconyLabel.setFont(afterfont);
			balconyLabel.setForeground(Color.white);
		}
		if (source == toiletLabel) {
			toiletLabel.setIcon(new ImageIcon("static\\icon\\卫生间1.png"));
			toiletLabel.setFont(afterfont);
			toiletLabel.setForeground(Color.white);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source == livingHomeLabel) {
			livingHomeLabel.setIcon(new ImageIcon("static\\icon\\客厅.png"));
			livingHomeLabel.setForeground(Color.lightGray);
		} else if (source == kitchenLabel) {
			kitchenLabel.setIcon(new ImageIcon("static\\icon\\厨房.png"));
			kitchenLabel.setForeground(Color.lightGray);
		} else if (source == bedroomLabel) {
			bedroomLabel.setIcon(new ImageIcon("static\\icon\\卧室.png"));
			bedroomLabel.setForeground(Color.lightGray);
		} else if (source == balconyLabel) {
			balconyLabel.setIcon(new ImageIcon("static\\icon\\走廊.png"));
			balconyLabel.setForeground(Color.lightGray);
		} else if (source == toiletLabel) {
			toiletLabel.setIcon(new ImageIcon("static\\icon\\卫生间.png"));
			toiletLabel.setForeground(Color.lightGray);
		}
	}
}
