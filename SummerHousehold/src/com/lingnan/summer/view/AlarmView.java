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

import com.lingnan.summer.dialog.AlarmDialog;
import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.AlarmQuery;
import com.lingnan.summer.service.AlarmService;
import com.lingnan.summer.service.impl.AlarmServiceImpl;
import com.lingnan.summer.table.AlarmTableModel;
import com.lingnan.summer.view.base.BaseView;

public class AlarmView extends BaseView implements ActionListener {

	// 上面
	private JPanel toolBarPanel;

	private JPanel searchPanel;
	private JLabel nameLabel;
	private JTextField nameSearchTF;
	private JButton searchBtn;

	private JPanel opePanel;
	private JButton  deleteBtn;

	// 中间
	private JScrollPane tableScrollPane;
	private JTable alarmTable;

	// 下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private AlarmTableModel alarmTableModel;

	private JFrame jFrame;

	private AlarmService alarmService = new AlarmServiceImpl();

	public AlarmView() {
		super(1000, 700, "设备报警");
	}

	@Override
	protected void initView() {
		toolBarPanel = new JPanel(new BorderLayout());

		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nameLabel = new JLabel("设备名");
		nameSearchTF = new JTextField(10);

		ImageIcon serchicon = new ImageIcon("static\\icon\\搜索.png");
		serchicon.setImage(serchicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		searchBtn = new JButton("搜索", serchicon);
		searchBtn.addActionListener(this);

		opePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ImageIcon deleteicon = new ImageIcon("static\\icon\\删除.png");
		deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		deleteBtn = new JButton("删除", deleteicon);

		deleteBtn.addActionListener(this);
		opePanel.add(deleteBtn);

		searchPanel.add(nameLabel);
		searchPanel.add(nameSearchTF);
		searchPanel.add(searchBtn);

		toolBarPanel.add(searchPanel, "West");
		toolBarPanel.add(opePanel, "East");

		// 中间
		alarmTable = new JTable();
		tableScrollPane = new JScrollPane(alarmTable);

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
		if (deleteBtn == source) {
			// 获取选中记录
			int rowIndex = alarmTable.getSelectedRow();
			if (rowIndex == -1) {
				JOptionPane.showMessageDialog(this, "请选中一条");
				return;
			}
			int id = (Integer) alarmTable.getValueAt(rowIndex, 0);
			if (alarmService.deleteById(id) == 1) {
				JOptionPane.showMessageDialog(this, "删除成功", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				refresh();
			} else {
				JOptionPane.showMessageDialog(this, "删除失败", "提示",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (searchBtn == source) {
			refresh();
		}
	}

	public void refresh() {
		String name = nameSearchTF.getText();

		AlarmQuery alarmQuery = new AlarmQuery();
		alarmQuery.setDname(name);

		AlarmTableModel alarmTableModel = new AlarmTableModel();
		alarmTableModel.query(alarmQuery);

		Page<Alarm> page = alarmTableModel.getPage();
		countInfoLabel.setText("总共" + page.getCount() + "条记录");

		alarmTable.setModel(alarmTableModel);
	}
}
