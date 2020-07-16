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

import com.lingnan.summer.dialog.KitchenDialog;
import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.KitchenQuery;
import com.lingnan.summer.service.KitchenService;
import com.lingnan.summer.service.impl.KitchenServiceImpl;
import com.lingnan.summer.setdialog.FridgeSetDialog;
import com.lingnan.summer.setdialog.LampSetDialog;
import com.lingnan.summer.table.KitchenTableModel;

public class KitchenView extends JPanel implements ActionListener {
	// ����
	private JPanel setPanel;
	private JPanel toolBarPanel;
	private JPanel searchPanel;
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JTextField nameSearchTF;// �豸������
	private JTextField typeSearchTF;

	private JButton setBtn, searchBtn;

	private JPanel opePanel;
	private JButton addBtn, updateBtn, deleteBtn;

	// �м�
	private JScrollPane tableScrollPane;// ��������ʾ������
	private JTable kitchenTable;

	// ����
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private KitchenTableModel kitchenTableModel;

	private JFrame jFrame;

	private KitchenService kitchenService = new KitchenServiceImpl();

	public KitchenView(JFrame jFrame) {
		this.setLayout(new BorderLayout());
		initView();
		this.jFrame = jFrame;
	}

	private void initView() {

		toolBarPanel = new JPanel(new BorderLayout());

		setPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

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
		ImageIcon seticon = new ImageIcon("static\\icon\\����.png");
		seticon.setImage(seticon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		setBtn = new JButton("����", seticon);

		opePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ImageIcon addicon = new ImageIcon("static\\icon\\���.png");
		addicon.setImage(addicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		addBtn = new JButton("���", addicon);

		ImageIcon updateicon = new ImageIcon("static\\icon\\�޸�.png");
		updateicon.setImage(updateicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		updateBtn = new JButton("����", updateicon);
		ImageIcon deleteicon = new ImageIcon("static\\icon\\ɾ��.png");
		deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		deleteBtn = new JButton("ɾ��", deleteicon);

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

		// �м�
		kitchenTable = new JTable();
		tableScrollPane = new JScrollPane(kitchenTable);

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
			KitchenDialog kitchenDialog = new KitchenDialog(jFrame, this);
			kitchenDialog.setVisible(true);
		} else if (updateBtn == source) {
			int rowIndex = kitchenTable.getSelectedRow();
			if (rowIndex == -1) {
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return;
			}

			int did = (Integer) kitchenTable.getValueAt(rowIndex, 0);
			Device device = kitchenService.findDeviceById(did);

			if (device == null) {
				JOptionPane.showMessageDialog(this, "���ݲ�����,��ˢ��");
				return;
			}

			KitchenDialog livingDialog = new KitchenDialog(jFrame, this, device);
			livingDialog.setVisible(true);
		} else if (deleteBtn == source) {
			// ��ȡѡ�м�¼
			int rowIndex = kitchenTable.getSelectedRow();
			if (rowIndex == -1) {
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return;
			}
			int did = (Integer) kitchenTable.getValueAt(rowIndex, 0);
			if (kitchenService.deleteById(did) == 1) {
				JOptionPane.showMessageDialog(this, "ɾ���ɹ�", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				refresh();
			} else {
				JOptionPane.showMessageDialog(this, "ɾ��ʧ��", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (searchBtn == source) {
			refresh();
		} else if (setBtn == source) {
			int rowIndex2 = kitchenTable.getSelectedRow();
			if (rowIndex2 == -1) {
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return;
			}
			int did = (Integer) kitchenTable.getValueAt(rowIndex2, 0);
			Device device = kitchenService.findDeviceById(did);

			if (device == null) {
				JOptionPane.showMessageDialog(this, "���ݲ�����,��ˢ��");
				return;
			}
			String newtid = device.getTid();
			if (newtid.equals("t07")) {
				FridgeSetDialog fridgeSetDialog = new FridgeSetDialog(jFrame,
						this, device);// *
				fridgeSetDialog.setVisible(true);// *
			} else if (newtid.equals("t03")) {
				LampSetDialog setDialog = new LampSetDialog(jFrame, this,
						device);
				setDialog.setVisible(true);
			}

		}
	}

	public void refresh() {
		String name = nameSearchTF.getText();
		String tid = typeSearchTF.getText();

		KitchenQuery kitchenQuery = new KitchenQuery();
		kitchenQuery.setDname(name);
		kitchenQuery.setTid(tid);

		KitchenTableModel kitchenTableModel = new KitchenTableModel();
		kitchenTableModel.query(kitchenQuery);

		Page<Device> page = kitchenTableModel.getPage();
		countInfoLabel.setText("�ܹ�" + page.getCount() + "����¼");

		kitchenTable.setModel(kitchenTableModel);
	}
}
