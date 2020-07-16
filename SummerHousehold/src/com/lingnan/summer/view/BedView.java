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




import com.lingnan.summer.dialog.BedDialog;
import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.BedQuery;
import com.lingnan.summer.service.BedService;
import com.lingnan.summer.service.impl.BedServiceImpl;
import com.lingnan.summer.setdialog.AirSetDialog;
import com.lingnan.summer.setdialog.CurtainSetDialog;
import com.lingnan.summer.setdialog.LampSetDialog;
import com.lingnan.summer.setdialog.LightSetDialog;
import com.lingnan.summer.setdialog.TVSetDialog;
import com.lingnan.summer.setdialog.WaterSetDialog;
import com.lingnan.summer.table.BedTableModel;


public class BedView extends JPanel implements ActionListener {
	// 上面
	private JPanel setPanel;
	private JPanel toolBarPanel;
	private JPanel searchPanel;
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JTextField nameSearchTF;// 设备名查找
	private JTextField typeSearchTF;
	
	
	private JButton setBtn,searchBtn,controlBtn;

	private JPanel opePanel;
	private JButton addBtn, updateBtn, deleteBtn;

	// 中间
	private JScrollPane tableScrollPane;// 滑动，显示出列名
	private JTable livingTable;

	// 下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private BedTableModel bedTableModel;

	private JFrame jFrame;

	private BedService livingService = new BedServiceImpl();

	public BedView(JFrame jFrame) {
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

		
		// 设置
		ImageIcon controlicon = new ImageIcon("static\\icon\\设置.png");
		controlicon.setImage(controlicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		controlBtn = new JButton("设置", controlicon);
		controlBtn.addActionListener(this);

		
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

		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

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

		// 中间
		livingTable = new JTable();
		// LivingTableModel.query(LivngQuery livingQuery);//报错，空指针 注意大小写

		tableScrollPane = new JScrollPane(livingTable);

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
		BedDialog livingDialog = new BedDialog(jFrame, this);
		livingDialog.setVisible(true);
	} else if (updateBtn == source) {
		int rowIndex = livingTable.getSelectedRow();
		if (rowIndex == -1) {
			JOptionPane.showMessageDialog(this, "请选中一条");
			return;
		}

		int did = (Integer) livingTable.getValueAt(rowIndex, 0);
		Device device = livingService.findDeviceById(did);

		if (device == null) {
			JOptionPane.showMessageDialog(this, "数据不存在,请刷新");
			return;
		}

		BedDialog livingDialog = new BedDialog(jFrame, this, device);
		livingDialog.setVisible(true);
	} else if (deleteBtn == source) {
		// 获取选中记录
		int rowIndex = livingTable.getSelectedRow();
		if (rowIndex == -1) {
			JOptionPane.showMessageDialog(this, "请选中一条");
			return;
		}
		int did = (Integer) livingTable.getValueAt(rowIndex, 0);
		if (livingService.deleteById(did) == 1) {
			JOptionPane.showMessageDialog(this, "删除成功", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			refresh();
		} else {
			JOptionPane.showMessageDialog(this, "删除失败", "提示",
					JOptionPane.ERROR_MESSAGE);
		}
	}
		//设置
		else if (source==controlBtn){
			int rowIndex2 = livingTable.getSelectedRow();
			if(rowIndex2==-1){
				JOptionPane.showMessageDialog(this, "请选择至少一条记录!");
				return;
			}
			int id = (Integer)livingTable.getValueAt(rowIndex2,0);
			Device device = livingService.findDeviceById(id);
			if(device==null){
				JOptionPane.showMessageDialog(this,"记录不存在，请刷新!");
				return;
			}
			String newtid = device.getTid();
				if(newtid.equals("t02")){
					TVSetDialog setDialog = new TVSetDialog(jFrame,this,device);
					setDialog.setVisible(true);
				}else if(newtid.equals("t01")) {
					AirSetDialog setDialog = new AirSetDialog(jFrame,this,device);
					setDialog.setVisible(true);
				}
				else if(newtid.equals("t03")){
					LightSetDialog lightset = new LightSetDialog(jFrame, this, device);
					lightset.setVisible(true);
				}
				
			else if(searchBtn==source) {
				refresh();
			}
		}
			

}

	public void refresh() {
		String name = nameSearchTF.getText();
		String tid = typeSearchTF.getText();
		BedQuery bedQuery = new BedQuery();
		bedQuery.setDname(name);
		bedQuery.setTid(tid);
		

		BedTableModel livingTableModel = new BedTableModel();
		livingTableModel.query(bedQuery);

		Page<Device> page = livingTableModel.getPage();
		countInfoLabel.setText("总共" + page.getCount() + "条记录");

		livingTable.setModel(livingTableModel);
	}
}



