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

import com.lingnan.summer.dialog.BathDialog;
import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.BathQuery;
import com.lingnan.summer.service.BathService;
import com.lingnan.summer.service.impl.BathServiceImpl;
import com.lingnan.summer.setdialog.BolierSetDialog;
import com.lingnan.summer.setdialog.LampSetDialog;
import com.lingnan.summer.setdialog.LightSetDialog;
import com.lingnan.summer.setdialog.TVSetDialog;
import com.lingnan.summer.table.BathTableModel;


public class BathView extends JPanel implements ActionListener {
	// ����
	private JPanel setPanel;
	private JPanel toolBarPanel;
	private JPanel searchPanel;
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JTextField nameSearchTF;// �豸������
	private JTextField typeSearchTF;
	
	
	private JButton setBtn,searchBtn,controlBtn;

	private JPanel opePanel;
	private JButton addBtn, updateBtn, deleteBtn;

	// �м�
	private JScrollPane tableScrollPane;// ��������ʾ������
	private JTable livingTable;

	// ����
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private BathTableModel bathTableModel;

	private JFrame jFrame;

	private BathService livingService = new BathServiceImpl();

	public BathView(JFrame jFrame) {
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

		
		// ����
		ImageIcon controlicon = new ImageIcon("static\\icon\\����.png");
		controlicon.setImage(controlicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		controlBtn = new JButton("����", controlicon);
		controlBtn.addActionListener(this);
		
		
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

	//	setBtn.addActionListener(this);
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

//		opePanel.add(setBtn);
		opePanel.add(addBtn);
		opePanel.add(updateBtn);
		opePanel.add(deleteBtn);
		opePanel.add(controlBtn);
		
	

		searchPanel.add(nameLabel);
		searchPanel.add(nameSearchTF);
		searchPanel.add(typeLabel);
		searchPanel.add(typeSearchTF);
		searchPanel.add(searchBtn);
		

		toolBarPanel.add(searchPanel, "West");
		toolBarPanel.add(opePanel, "East");

		// �м�
		livingTable = new JTable();
		// LivingTableModel.query(LivngQuery livingQuery);//������ָ�� ע���Сд

		tableScrollPane = new JScrollPane(livingTable);

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
		BathDialog livingDialog = new BathDialog(jFrame, this);
		livingDialog.setVisible(true);
	} else if (updateBtn == source) {
		int rowIndex = livingTable.getSelectedRow();
		if (rowIndex == -1) {
			JOptionPane.showMessageDialog(this, "��ѡ��һ��");
			return;
		}

		int did = (Integer) livingTable.getValueAt(rowIndex, 0);
		Device device = livingService.findDeviceById(did);

		if (device == null) {
			JOptionPane.showMessageDialog(this, "���ݲ�����,��ˢ��");
			return;
		}

		BathDialog livingDialog = new BathDialog(jFrame, this, device);
		livingDialog.setVisible(true);
	} else if (deleteBtn == source) {
		// ��ȡѡ�м�¼
		int rowIndex = livingTable.getSelectedRow();
		if (rowIndex == -1) {
			JOptionPane.showMessageDialog(this, "��ѡ��һ��");
			return;
		}
		int did = (Integer) livingTable.getValueAt(rowIndex, 0);
		if (livingService.deleteById(did) == 1) {
			JOptionPane.showMessageDialog(this, "ɾ���ɹ�", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			refresh();
		} else {
			JOptionPane.showMessageDialog(this, "ɾ��ʧ��", "��ʾ",
					JOptionPane.ERROR_MESSAGE);
		}
	}
		//����
		else if (source==controlBtn){
			int rowIndex2 = livingTable.getSelectedRow();
			if(rowIndex2==-1){
				JOptionPane.showMessageDialog(this, "��ѡ������һ����¼!");
				return;
			}
			int id = (Integer)livingTable.getValueAt(rowIndex2,0);
			Device device = livingService.findDeviceById(id);
			if(device==null){
				JOptionPane.showMessageDialog(this,"��¼�����ڣ���ˢ��!");
				return;
			}
			String newtid = device.getTid();
		    if(newtid.equals("t04")) {
				BolierSetDialog setDialog = new BolierSetDialog(jFrame,this,device);
				setDialog.setVisible(true);
			}else{
			LightSetDialog lightset = new LightSetDialog(jFrame, this, device);
			lightset.setVisible(true);
		} 
			}
		else if (searchBtn == source) {
		refresh();
	    }

}

	public void refresh() {
		String name = nameSearchTF.getText();
		String tid = typeSearchTF.getText();
		BathQuery bedQuery = new BathQuery();
		bedQuery.setDname(name);
		bedQuery.setTid(tid);
		

		BathTableModel livingTableModel = new BathTableModel();
		livingTableModel.query(bedQuery);

		Page<Device> page = livingTableModel.getPage();
		countInfoLabel.setText("�ܹ�" + page.getCount() + "����¼");

		livingTable.setModel(livingTableModel);
	}
}
