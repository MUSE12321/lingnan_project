package com.lingnan.summer.setdialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lingnan.summer.domain.Device;
import com.lingnan.summer.domain.Task;
import com.lingnan.summer.service.TaskService;
import com.lingnan.summer.service.impl.TaskServiceImpl;
import com.lingnan.summer.view.BedView;
import com.lingnan.summer.view.LivingView;

public class AirSetDialog extends JDialog implements ActionListener {

	private JPanel idPanel, namePanel, measurePanel, opePanel, modelPanel;

	private JLabel idLabel, nameLabel, measureLabel, modelLabel;
	private JTextField idTF, nameTF, measureTF, modelTF, tempTF;// tempTF温度

	private JButton saveBtn, cancelBtn, addBtn, subBtn;

	private TaskService taskService = new TaskServiceImpl();

	private LivingView livingView;
	private BedView bedView;

	private Device device;
	private Task task;

	String mm;

	// 设置
	private JLabel addLabel, subLabel;

	// 下拉框

	public JComboBox<String> combo;
	public String[] model = { "制冷模式", "抽湿模式", "睡眠模式" };

	public AirSetDialog(JFrame parent, BedView bedView) {
		super(parent, "设置");
		this.bedView = bedView;

		setSize(250, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	public AirSetDialog(JFrame parent, BedView bedView, Device device) {
		this(parent, bedView);
		this.device = device;
		setTitle("设置");
		// 回显
		this.nameTF.setText(device.getDname());
		this.idPanel.setVisible(false);
	}

	public AirSetDialog(JFrame parent, LivingView livingView) {
		super(parent, "设置");
		this.livingView = livingView;

		setSize(250, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	public AirSetDialog(JFrame parent, LivingView livingView, Device device) {
		this(parent, livingView);
		this.device = device;
		setTitle("设置");
		// 回显
		this.nameTF.setText(device.getDname());
		this.idPanel.setVisible(false);
	}

	private void initView() {
		idPanel = new JPanel();
		idLabel = new JLabel("id");
		idTF = new JTextField(15);
		idPanel.add(idLabel);
		idPanel.add(idTF);

		namePanel = new JPanel();
		nameLabel = new JLabel("设备名称");
		nameTF = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);

		measurePanel = new JPanel();
		measureLabel = new JLabel("温度");
		measurePanel.add(measureLabel);
		measureTF = new JTextField("25", 5);
		measurePanel.add(measureTF);

		ImageIcon addicon = new ImageIcon("static\\icon\\增加.png");
		addicon.setImage(addicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		addBtn = new JButton(addicon);
		addBtn.setContentAreaFilled(false);// 透明

		ImageIcon subicon = new ImageIcon("static\\icon\\减小.png");
		subicon.setImage(subicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		subBtn = new JButton(subicon);
		subBtn.setContentAreaFilled(false);// 透明

		addBtn.addActionListener(this);
		subBtn.addActionListener(this);
		measurePanel.add(addBtn);
		measurePanel.add(subBtn);

		// 设置模式
		modelPanel = new JPanel();
		modelLabel = new JLabel("模式");
		modelPanel.add(modelLabel);

		opePanel = new JPanel();
		saveBtn = new JButton("保存");
		cancelBtn = new JButton("取消");
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(saveBtn);
		opePanel.add(cancelBtn);

		combo = new JComboBox<String>(model);
		modelPanel.add(combo);
		mm = (String) combo.getSelectedItem();
		modelPanel.setBounds(120, 190, 300, 100);

		Container container = getContentPane();
		container.add(idPanel);
		container.add(namePanel);
		container.add(measurePanel);
		container.add(modelPanel);
		container.add(opePanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		int temp = Integer.parseInt(measureTF.getText());
		measureTF.setText(temp + "");
		if (source == addBtn) {
			// 频道增加
			temp = temp + 1;
			measureTF.setText(temp + "");
		} else if (source == subBtn) {
			// 频道减小
			temp = temp - 1;
			measureTF.setText(temp + "");
		} else if (source == saveBtn) {
			String name = nameTF.getText();
			String measure = measureTF.getText();
			String model = mm;

			if (this.task == null) {
				// TODO 参数校验
				Device device = new Device();
				device.setDname(name);
				device.setTemp(temp);
				Task task = new Task();
				task.setDname(name);
				task.setInstruction("温度:" + temp + "," + (String) combo.getSelectedItem());

				int result = taskService.add(task);
				if (result == 1) {
					this.dispose();
					JOptionPane.showMessageDialog(this, "设置成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					// 刷新
					livingView.refresh();

				} else {
					JOptionPane.showMessageDialog(this, "设置失败", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		} else if (source == cancelBtn) {
			this.dispose();
		}
	}

}
