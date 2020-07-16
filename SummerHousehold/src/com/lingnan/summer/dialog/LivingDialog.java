package com.lingnan.summer.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lingnan.summer.domain.Device;
import com.lingnan.summer.service.DeviceService;
import com.lingnan.summer.service.LivingService;
import com.lingnan.summer.service.impl.LivingServiceImpl;
import com.lingnan.summer.view.LivingView;

public class LivingDialog extends JDialog implements ActionListener {
	private JPanel dnamePanel, tidPanel, locationPanel, paramPanel, opePanel,statusPanel;
	
	private JLabel dnameLabel, tidLabel, locationLabel, paramLabel,statusLabel;
	
	private JTextField dnameTF, tidTF, locationTF, paramTF, statusTF;

	private JButton saveBtn, cancelBtn;

	private LivingService livingService = new LivingServiceImpl();

	private LivingView livingView;

	private Device device;

	public LivingDialog(JFrame parent, LivingView livingView) {
		super(parent, "添加");
		this.livingView = livingView;

		setSize(350, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	public LivingDialog(JFrame parent, LivingView livingView, Device device) {
		this(parent, livingView);
		this.device = device;
		setTitle("修改");
		 //回显
		this.dnameTF.setText(device.getDname());
		this.tidTF.setText(device.getTid());
		this.locationTF.setText(device.getLocation());
		this.paramTF.setText(device.getParam());
		this.statusTF.setText(device.getStatus());
		this.tidPanel.setVisible(false);
	}

	private void initView() {
		dnamePanel = new JPanel();
		dnameLabel = new JLabel("设备名称");
		dnameTF = new JTextField(15);
		dnamePanel.add(dnameLabel);
		dnamePanel.add(dnameTF);

		tidPanel = new JPanel();
		tidLabel = new JLabel("设备类型");
		tidTF = new JTextField(15);
		tidPanel.add(tidLabel);
		tidPanel.add(tidTF);

		locationPanel = new JPanel();
		locationLabel = new JLabel("位置");
		locationTF = new JTextField(15);
		locationPanel.add(locationLabel);
		locationPanel.add(locationTF);

		paramPanel = new JPanel();
		paramLabel = new JLabel("参数");
		paramTF = new JTextField(15);
		paramPanel.add(paramLabel);
		paramPanel.add(paramTF);

		statusPanel = new JPanel();
		statusLabel = new JLabel("状态");
		statusTF = new JTextField(15);
		statusPanel.add(statusLabel);
		statusPanel.add(statusTF);

		opePanel = new JPanel();
		saveBtn = new JButton("保存");
		cancelBtn = new JButton("取消");
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(saveBtn);
		opePanel.add(cancelBtn);

		Container container = getContentPane();
		container.add(dnamePanel);
		container.add(tidPanel);
		container.add(locationPanel);
		container.add(paramPanel);
		container.add(statusPanel);
		container.add(opePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == saveBtn) {
			String dname = dnameTF.getText();
			String tid = tidTF.getText();
			String location = locationTF.getText();
			String param = paramTF.getText();
			String status = statusTF.getText();

			if (this.device == null) {

				// TODO 参数校验
				Device device = new Device();
				device.setDname(dname);
				device.setTid(tid);
				device.setLocation(location);
				device.setParam(param);
				device.setStatus(status);

				int result = livingService.add(device);
				if (result == 1) {
					this.dispose();
					JOptionPane.showMessageDialog(this, "添加成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					livingView.refresh();
				} else {
					JOptionPane.showMessageDialog(this, "添加失败", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// 更新
				Device device = new Device();
				device.setDname(dname);
				device.setLocation(location);
				device.setParam(param);
				device.setStatus(status);
				device.setDid(this.device.getDid());

				int result = livingService.update(device);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "更新成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					// 刷新
					livingView.refresh();
				} else {
					JOptionPane.showMessageDialog(this, "更新失败", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (source == cancelBtn) {

			this.dispose();
		}
	}

}
