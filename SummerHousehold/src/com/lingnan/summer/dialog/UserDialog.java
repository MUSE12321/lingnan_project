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

	// 添加
	public UserDialog(JFrame parent, UserView userview) {
		super(parent, "添加");
		this.userView = userView;

		setSize(350, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	// 修改
	public UserDialog(JFrame parent, UserView userView, User user) {
		this(parent, userView);
		this.user = user;
		setTitle("修改");
		id = user.getId();// 后来添加获取的
		this.nameTF.setText(user.getName());
		this.passwordTF.setText(user.getPassword());
		this.addressTF.setText(user.getAddress());
		this.phoneTF.setText(user.getPhone());
		this.namePanel.setVisible(false);
	}

	private void initView() {
		namePanel = new JPanel();
		nameLabel = new JLabel("姓名");
		nameTF = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);

		passwordPanel = new JPanel();
		passwordLabel = new JLabel("密码");
		passwordTF = new JTextField(15);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTF);

		addressPanel = new JPanel();
		addressLabel = new JLabel("地址");
		addressTF = new JTextField(15);
		addressPanel.add(addressLabel);
		addressPanel.add(addressTF);

		phonePanel = new JPanel();
		phoneLabel = new JLabel("手机");
		phoneTF = new JTextField(15);
		phonePanel.add(phoneLabel);
		phonePanel.add(phoneTF);

		opePanel = new JPanel();
		saveBtn = new JButton("保存");
		cancelBtn = new JButton("取消");
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
			// TODO 参数校验
			if (this.user == null) {
				User user = new User();
				user.setName(name);
				user.setPassword(password);
				user.setAddress(address);
				user.setPhone(phone);
				int result = userService.add(user);
				if (result == 1) {
					this.dispose();
					JOptionPane.showMessageDialog(this, "添加成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					// 刷新
					//userView.refresh(); 在view更新了
				} else {
					JOptionPane.showMessageDialog(this, "添加失败", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {// 更新
				User user = new User();
				user.setAddress(address);
				user.setPassword(password);
				user.setPhone(phone);
				user.setId(this.user.getId());

				int result = userService.update(user);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "更新成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					//userView.refresh();
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
