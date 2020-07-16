package com.lingnan.summer.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
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

import com.lingnan.summer.dialog.DeviceDialog;
import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.DeviceQuery;
import com.lingnan.summer.service.DeviceService;
import com.lingnan.summer.service.impl.DeviceServiceImpl;
import com.lingnan.summer.table.DeviceTableModel;
import com.lingnan.summer.view.base.BaseView;

public class DeviceView extends BaseView implements ActionListener {
	// 上面
	private JPanel toolBarPanel;
	private JPanel searchPanel;
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JTextField nameSearchTF;// 设备名查找
	private JTextField typeSearchTF;

	private JButton searchBtn;

	private JPanel opePanel;
	private JButton addBtn, updateBtn, deleteBtn;

	// 中间
	private JScrollPane tableScrollPane;// 滑动，显示出列名
	private JTable deviceTable;

	// 下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private DeviceTableModel deviceTableModel;

	private JFrame jFrame;

	private DeviceService deviceService = new DeviceServiceImpl();

	public DeviceView() {
		super(1000, 700, "设备管理");
	}

	@Override
	protected void initView() {

		toolBarPanel = new JPanel(new BorderLayout());

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
		ImageIcon addicon = new ImageIcon("static\\icon\\添加.png");
		addicon.setImage(addicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		addBtn = new JButton("添加", addicon);
		ImageIcon updateicon = new ImageIcon("static\\icon\\修改.png");
		updateicon.setImage(updateicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		updateBtn = new JButton("修改", updateicon);
		ImageIcon deleteicon = new ImageIcon("static\\icon\\删除.png");
		deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		deleteBtn = new JButton("删除", deleteicon);

		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

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
		deviceTable = new JTable();
		tableScrollPane = new JScrollPane(deviceTable);

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
			DeviceDialog deviceDialog = new DeviceDialog(jFrame, this);
			deviceDialog.setVisible(true);
		} else if (updateBtn == source) {
			int rowIndex = deviceTable.getSelectedRow();
			if (rowIndex == -1) {
				JOptionPane.showMessageDialog(this, "请选择至少一条记录!");
				return;
			}
			int did = (Integer) deviceTable.getValueAt(rowIndex, 0);
			Device device = deviceService.findDeviceById(did);

			if (device == null) {
				JOptionPane.showMessageDialog(this, "数据不存在,请刷新");
				return;
			}

			DeviceDialog deviceDialog = new DeviceDialog(jFrame, this, device);
			deviceDialog.setVisible(true);
		} else if (deleteBtn == source) {
			// 获取选中记录
			int rowIndex = deviceTable.getSelectedRow();
			if (rowIndex == -1) {
				JOptionPane.showMessageDialog(this, "请选中一条");
				return;
			}
			int did = (Integer) deviceTable.getValueAt(rowIndex, 0);
			if (deviceService.deleteById(did) == 1) {
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
	}

	public void refresh() {
		String name = nameSearchTF.getText();
		String tid = typeSearchTF.getText();

		DeviceQuery deviceQuery = new DeviceQuery();
		deviceQuery.setDname(name);
		deviceQuery.setTid(tid);

		DeviceTableModel deviceTableModel = new DeviceTableModel();
		deviceTableModel.query(deviceQuery);

		Page<Device> page = deviceTableModel.getPage();
		countInfoLabel.setText("总共" + page.getCount() + "条记录");

		deviceTable.setModel(deviceTableModel);
	}
}