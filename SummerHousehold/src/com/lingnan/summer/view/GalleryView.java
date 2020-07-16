package com.lingnan.summer.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.lingnan.summer.dialog.GalleryDialog;
import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.KitchenQuery;
import com.lingnan.summer.service.KitchenService;
import com.lingnan.summer.service.impl.GalleryServiceImpl;
import com.lingnan.summer.setdialog.WasherSetDialog;
import com.lingnan.summer.table.GalleryTableModel;


public class GalleryView extends JPanel implements ActionListener {
	// 上面
	private JPanel setPanel;
	private JPanel toolBarPanel;
	private JPanel searchPanel;
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JTextField nameSearchTF;// 设备名查找
	private JTextField typeSearchTF;
	
	private JButton setBtn,searchBtn;

	private JPanel opePanel;
	private JButton addBtn, updateBtn, deleteBtn;

	// 中间
	private JScrollPane tableScrollPane;// 滑动，显示出列名
	private JTable kitchenTable;

	// 下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private GalleryTableModel galleryTableModel;

	private JFrame jFrame;

	private KitchenService kitchenService = new GalleryServiceImpl();

	public GalleryView(JFrame jFrame) {
		this.setLayout(new BorderLayout());
		initView();
		this.jFrame = jFrame;
	}

	private void initView() {

		toolBarPanel = new JPanel(new BorderLayout());
		
		setPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nameLabel = new JLabel("设备名称");
		nameSearchTF = new JTextField(10);

		typeLabel = new JLabel("设备类型");
		typeSearchTF = new JTextField(10);

		ImageIcon serchicon = new ImageIcon("static\\icon\\搜索.png");
		serchicon.setImage(serchicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		searchBtn = new JButton("搜索", serchicon);
		searchBtn.addActionListener(this);

		opePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ImageIcon seticon = new ImageIcon("static\\icon\\设置.png");
		seticon.setImage(seticon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		setBtn = new JButton("设置", seticon);
		
		opePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ImageIcon addicon = new ImageIcon("static\\icon\\添加.png");
		addicon.setImage(addicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		addBtn = new JButton("添加", addicon);
		
		ImageIcon updateicon = new ImageIcon("static\\icon\\修改.png");
		updateicon.setImage(updateicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		updateBtn = new JButton("更新", updateicon);
		ImageIcon deleteicon = new ImageIcon("static\\icon\\删除.png");
		deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		deleteBtn = new JButton("删除", deleteicon);

		setBtn.addActionListener(this);
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

		opePanel.add(setBtn);
		opePanel.add(addBtn);
		opePanel.add(updateBtn);
		opePanel.add(deleteBtn);

		searchPanel.add(nameLabel);
		searchPanel.add(nameSearchTF);
		searchPanel.add(typeLabel);
		searchPanel.add(typeSearchTF);
		searchPanel.add(searchBtn);
		
		toolBarPanel.add(searchPanel, "West");
		toolBarPanel.add(opePanel, "East");

		// 中间
		kitchenTable = new JTable();
		tableScrollPane = new JScrollPane(kitchenTable);

		// 下面
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countInfoLabel = new JLabel();
		bottomPanel.add(countInfoLabel);

		this.add(toolBarPanel, "North");
		this.add(tableScrollPane, "Center");
		this.add(bottomPanel, "South");

		setVisible(true);
		refresh();
	}

@Override
public void actionPerformed(ActionEvent e) {
	Object source = e.getSource();
	if (addBtn == source) {
		GalleryDialog galleryDialog = new GalleryDialog(jFrame, this);
		galleryDialog.setVisible(true);
	} else if (updateBtn == source) {
		int rowIndex = kitchenTable.getSelectedRow();
		if (rowIndex == -1) {
			JOptionPane.showMessageDialog(this, "请选中一条");
			return;
		}

		int did = (Integer) kitchenTable.getValueAt(rowIndex, 0);
		Device device = kitchenService.findDeviceById(did);

		if (device == null) {
			JOptionPane.showMessageDialog(this, "数据不存在,请刷新");
			return;
		}

		GalleryDialog galleryDialog = new GalleryDialog(jFrame, this, device);
		galleryDialog.setVisible(true);
	} else if (deleteBtn == source) {
		// 获取选中记录
		int rowIndex = kitchenTable.getSelectedRow();
		if (rowIndex == -1) {
			JOptionPane.showMessageDialog(this, "请选中一条");
			return;
		}
		int did = (Integer) kitchenTable.getValueAt(rowIndex, 0);
		if (kitchenService.deleteById(did) == 1) {
			JOptionPane.showMessageDialog(this, "删除成功", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			refresh();
		} else {
			JOptionPane.showMessageDialog(this, "删除失败", "提示",
					JOptionPane.ERROR_MESSAGE);
		}
	} else if (searchBtn == source) {
		refresh();
	}
	else if (setBtn == source) {
		int rowIndex2 = kitchenTable.getSelectedRow();
		if (rowIndex2 == -1) {
			JOptionPane.showMessageDialog(this, "请选中一条");
			return;
		}
		int did = (Integer) kitchenTable.getValueAt(rowIndex2, 0);
		Device device = kitchenService.findDeviceById(did);

		if (device == null) {
			JOptionPane.showMessageDialog(this, "数据不存在,请刷新");
			return;
		}
        WasherSetDialog washerSetDialog = new WasherSetDialog(jFrame, this, device);//*
        washerSetDialog.setVisible(true);//*
	}
}

	public void refresh() {
		String name = nameSearchTF.getText();
		String tid = typeSearchTF.getText();

		KitchenQuery kitchenQuery = new KitchenQuery();
		kitchenQuery.setDname(name);
		kitchenQuery.setTid(tid);
		
		GalleryTableModel galleryTableModel = new GalleryTableModel();
		galleryTableModel.query(kitchenQuery);

		Page<Device> page = galleryTableModel.getPage();
		countInfoLabel.setText("总共" + page.getCount() + "条记录");

		kitchenTable.setModel(galleryTableModel);
	}
}




