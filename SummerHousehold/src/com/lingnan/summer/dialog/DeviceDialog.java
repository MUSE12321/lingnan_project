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
import com.lingnan.summer.service.impl.DeviceServiceImpl;
import com.lingnan.summer.view.DeviceView;

public class DeviceDialog extends JDialog implements ActionListener {
	private JPanel dnamePanel, tidPanel, locationPanel, paramPanel, opePanel,
			statusPanel;
	private JLabel dnameLabel, tidLabel, locationLabel, paramLabel,
			statusLabel;
	private JTextField dnameTF, tidTF, locationTF, paramTF, statusTF;

	private JButton saveBtn, cancelBtn;

	private DeviceService deviceService = new DeviceServiceImpl();

	private DeviceView deviceView;

	private Device device;

	public DeviceDialog(JFrame parent, DeviceView deviceView) {
		super(parent, "����");
		this.deviceView = deviceView;

		setSize(350, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	public DeviceDialog(JFrame parent, DeviceView deviceView, Device device) {
		this(parent, deviceView);
		this.device = device;
		setTitle("�޸�");
		// ����
		this.dnameTF.setText(device.getDname());
		this.tidTF.setText(device.getTid());
		this.locationTF.setText(device.getLocation());
		this.paramTF.setText(device.getParam());
		this.statusTF.setText(device.getStatus());
		this.tidPanel.setVisible(false);
	}

	private void initView() {
		dnamePanel = new JPanel();
		dnameLabel = new JLabel("�豸����");
		dnameTF = new JTextField(15);
		dnamePanel.add(dnameLabel);
		dnamePanel.add(dnameTF);

		tidPanel = new JPanel();
		tidLabel = new JLabel("�豸����");
		tidTF = new JTextField(15);
		tidPanel.add(tidLabel);
		tidPanel.add(tidTF);

		locationPanel = new JPanel();
		locationLabel = new JLabel("λ��");
		locationTF = new JTextField(15);
		locationPanel.add(locationLabel);
		locationPanel.add(locationTF);

		paramPanel = new JPanel();
		paramLabel = new JLabel("����");
		paramTF = new JTextField(15);
		paramPanel.add(paramLabel);
		paramPanel.add(paramTF);

		statusPanel = new JPanel();
		statusLabel = new JLabel("״̬");
		statusTF = new JTextField(15);
		statusPanel.add(statusLabel);
		statusPanel.add(statusTF);

		opePanel = new JPanel();
		saveBtn = new JButton("����");
		cancelBtn = new JButton("ȡ��");
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

				// TODO ����У��
				Device device = new Device();
				device.setDname(dname);
				device.setTid(tid);
				device.setLocation(location);
				device.setParam(param);
				device.setStatus(status);

				int result = deviceService.add(device);
				if (result == 1) {
					this.dispose();
					JOptionPane.showMessageDialog(this, "���ӳɹ�", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					deviceView.refresh();
				} else {
					JOptionPane.showMessageDialog(this, "����ʧ��", "��ʾ",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// ����
				Device device = new Device();
				device.setDname(dname);
				device.setLocation(location);
				device.setParam(param);
				device.setStatus(status);
				device.setDid(this.device.getDid());

				int result = deviceService.update(device);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "���³ɹ�", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					// ˢ��
					deviceView.refresh();
				} else {
					JOptionPane.showMessageDialog(this, "����ʧ��", "��ʾ",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (source == cancelBtn) {

			this.dispose();
		}
	}
}