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

import com.lingnan.summer.domain.User;
import com.lingnan.summer.service.UserService;
import com.lingnan.summer.service.impl.UserServiceImpl;
import com.lingnan.summer.view.UserView;

public class UserDialog extends JDialog implements ActionListener {

	private JPanel namePanel, addressPanel, phonePanel, opePanel,passwordPanel;

	private JLabel nameLabel, addressLabel, phoneLabel, passwordLabel;
	private JTextField nameTF, addressTF, phoneTF, passwordTF;

	private JButton saveBtn, cancelBtn;

	private UserService userService = new UserServiceImpl();

	private UserView userView;

	private User user;

	private int id;

	// ���
	public UserDialog(JFrame parent, UserView userview) {
		super(parent, "���");
		this.userView = userView;

		setSize(350, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	// �޸�
	public UserDialog(JFrame parent, UserView userView, User user) {
		this(parent, userView);
		this.user = user;
		setTitle("�޸�");
		id = user.getId();// ������ӻ�ȡ��
		this.nameTF.setText(user.getName());
		this.passwordTF.setText(user.getPassword());
		this.addressTF.setText(user.getAddress());
		this.phoneTF.setText(user.getPhone());
		this.namePanel.setVisible(false);
	}

	private void initView() {
		namePanel = new JPanel();
		nameLabel = new JLabel("����");
		nameTF = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);

		passwordPanel = new JPanel();
		passwordLabel = new JLabel("����");
		passwordTF = new JTextField(15);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTF);

		addressPanel = new JPanel();
		addressLabel = new JLabel("��ַ");
		addressTF = new JTextField(15);
		addressPanel.add(addressLabel);
		addressPanel.add(addressTF);

		phonePanel = new JPanel();
		phoneLabel = new JLabel("�ֻ�");
		phoneTF = new JTextField(15);
		phonePanel.add(phoneLabel);
		phonePanel.add(phoneTF);

		opePanel = new JPanel();
		saveBtn = new JButton("����");
		cancelBtn = new JButton("ȡ��");
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(saveBtn);
		opePanel.add(cancelBtn);

		Container container = getContentPane();
		container.add(namePanel);
		container.add(passwordPanel);
		container.add(addressPanel);
		container.add(phonePanel);
		container.add(opePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == saveBtn) {

			String name = nameTF.getText();
			String password = passwordTF.getText();
			String phone = phoneTF.getText();
			String address = addressTF.getText();
			int newid = id;
			// TODO ����У��
			if (this.user == null) {
				User user = new User();
				user.setName(name);
				user.setPassword(password);
				user.setAddress(address);
				user.setPhone(phone);
				int result = userService.add(user);
				if (result == 1) {
					this.dispose();
					JOptionPane.showMessageDialog(this, "��ӳɹ�", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					// ˢ��
					//userView.refresh(); ��view������
				} else {
					JOptionPane.showMessageDialog(this, "���ʧ��", "��ʾ",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {// ����
				User user = new User();
				user.setAddress(address);
				user.setPassword(password);
				user.setPhone(phone);
				user.setId(this.user.getId());

				int result = userService.update(user);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "���³ɹ�", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					//userView.refresh();
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
