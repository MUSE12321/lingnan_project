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
	// ����
	private JPanel toolBarPanel;
	private JPanel searchPanel;
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JTextField nameSearchTF;// �豸������
	private JTextField typeSearchTF;

	private JButton searchBtn;

	private JPanel opePanel;
	private JButton addBtn, updateBtn, deleteBtn;

	// �м�
	private JScrollPane tableScrollPane;// ��������ʾ������
	private JTable deviceTable;

	// ����
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private DeviceTableModel deviceTableModel;

	private JFrame jFrame;

	private DeviceService deviceService = new DeviceServiceImpl();

	public DeviceView() {
		super(1000, 700, "�豸����");
	}

	@Override
	protected void initView() {

		toolBarPanel = new JPanel(new BorderLayout());

		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nameLabel = new JLabel("�豸����");
		nameSearchTF = new JTextField(10);

		typeLabel = new JLabel("�豸����");
		typeSearchTF = new JTextField(10);

		ImageIcon serchicon = new ImageIcon("static\\icon\\����.png");
		serchicon.setImage(serchicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		searchBtn = new JButton("����", serchicon);
		searchBtn.addActionListener(this);

		opePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ImageIcon addicon = new ImageIcon("static\\icon\\���.png");
		addicon.setImage(addicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		addBtn = new JButton("���", addicon);
		ImageIcon updateicon = new ImageIcon("static\\icon\\�޸�.png");
		updateicon.setImage(updateicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		updateBtn = new JButton("�޸�", updateicon);
		ImageIcon deleteicon = new ImageIcon("static\\icon\\ɾ��.png");
		deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		deleteBtn = new JButton("ɾ��", deleteicon);

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

		// �м�
		deviceTable = new JTable();
		tableScrollPane = new JScrollPane(deviceTable);

		// ����
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
				JOptionPane.showMessageDialog(this, "��ѡ������һ����¼!");
				return;
			}
			int did = (Integer) deviceTable.getValueAt(rowIndex, 0);
			Device device = deviceService.findDeviceById(did);

			if (device == null) {
				JOptionPane.showMessageDialog(this, "���ݲ�����,��ˢ��");
				return;
			}

			DeviceDialog deviceDialog = new DeviceDialog(jFrame, this, device);
			deviceDialog.setVisible(true);
		} else if (deleteBtn == source) {
			// ��ȡѡ�м�¼
			int rowIndex = deviceTable.getSelectedRow();
			if (rowIndex == -1) {
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return;
			}
			int did = (Integer) deviceTable.getValueAt(rowIndex, 0);
			if (deviceService.deleteById(did) == 1) {
				JOptionPane.showMessageDialog(this, "ɾ���ɹ�", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				refresh();
			} else {
				JOptionPane.showMessageDialog(this, "ɾ��ʧ��", "��ʾ",
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
		countInfoLabel.setText("�ܹ�" + page.getCount() + "����¼");

		deviceTable.setModel(deviceTableModel);
	}
}