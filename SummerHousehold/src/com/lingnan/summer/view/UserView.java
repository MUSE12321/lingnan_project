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

import com.lingnan.summer.dialog.UserDialog;
import com.lingnan.summer.domain.User;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.UserQuery;
import com.lingnan.summer.service.UserService;
import com.lingnan.summer.service.impl.UserServiceImpl;
import com.lingnan.summer.table.UserTableModel;

public class UserView extends JPanel implements ActionListener{
		
		
		//����
		private JPanel toolBarPanel;
		
		private JPanel searchPanel;
		private JLabel nameLabel;
		private JTextField nameSearchTF;
		private JButton searchBtn;
		
		private JPanel opePanel;
		private JButton addBtn,updateBtn,deleteBtn;
		
		//�м�
		private JScrollPane tableScrollPane;
		private JTable userTable;
		
		//����
		private JPanel bottomPanel;
		private JLabel countInfoLabel;
		
		private UserTableModel userTableModel;
		
		private JFrame jFrame;
		
		private UserService userService=new UserServiceImpl();
		
		public UserView(JFrame jFrame) {
			this.setLayout(new BorderLayout());
			initView();
			this.jFrame = jFrame;
		}
		
		private void initView() {
			 
			toolBarPanel = new JPanel(new BorderLayout()); 
			
			searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
			nameLabel = new JLabel("����");
			nameSearchTF = new JTextField(10);
			
			ImageIcon serchicon = new ImageIcon("static\\icon\\����.png");
			serchicon.setImage(serchicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			searchBtn = new JButton("����",serchicon);
			searchBtn.addActionListener(this);
			
			opePanel =  new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
			
			ImageIcon addicon = new ImageIcon("static\\icon\\�û����.png");
			addicon.setImage(addicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			addBtn =new JButton("���",addicon);
			
			ImageIcon updateicon = new ImageIcon("static\\icon\\�޸�.png");
			updateicon.setImage(updateicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			updateBtn =new JButton("�޸�",updateicon);
			
			ImageIcon deleteicon = new ImageIcon("static\\icon\\�û�ɾ��.png");
			deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			deleteBtn =new JButton("ɾ��",deleteicon);
			
			addBtn.addActionListener(this);
			updateBtn.addActionListener(this);
			deleteBtn.addActionListener(this);
			
			opePanel.add(addBtn);
			opePanel.add(updateBtn);
			opePanel.add(deleteBtn);
			 
			searchPanel.add(nameLabel);
			searchPanel.add(nameSearchTF);
			searchPanel.add(searchBtn);
			
			
			toolBarPanel.add(searchPanel,"West");
			toolBarPanel.add(opePanel,"East");
			
			
			//�м�
			userTable = new JTable();
			UserTableModel userTableModel = new UserTableModel();
			UserQuery userQuery = new UserQuery();
			userTableModel.query(userQuery);
			userTable = new JTable(userTableModel);
			tableScrollPane = new JScrollPane(userTable);
			
			//����
			bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			countInfoLabel = new JLabel("�ܹ�10��");
			bottomPanel.add(countInfoLabel);
			
			
			this.add(toolBarPanel,"North");
			this.add(tableScrollPane,"Center");
			this.add(bottomPanel,"South");
			
			setVisible(true);
			
			refresh();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(addBtn==source) {
				UserDialog userDialog = new UserDialog(jFrame,this);
				userDialog.setVisible(true);
				refresh();
			}else if(updateBtn==source) {
				int rowIndex = userTable.getSelectedRow();
				if(rowIndex==-1){
					JOptionPane.showMessageDialog(this, "��ѡ������һ����¼!");
					return;
				}
				int id = (Integer)userTable.getValueAt(rowIndex,0);
				User user = userService.findUserById(id);
				
				if(user==null){
					JOptionPane.showMessageDialog(this,"��¼�����ڣ���ˢ��!");
					return;
				}
				UserDialog userDialog = new UserDialog(jFrame,this,user);
				userDialog.setVisible(true);
				refresh();
			}else if(deleteBtn==source) {
				// ��ȡѡ�м�¼
				int rowIndex = userTable.getSelectedRow();
				if (rowIndex == -1) {
					JOptionPane.showMessageDialog(this, "��ѡ��һ��");
					return;
				}
				int id = (Integer) userTable.getValueAt(rowIndex, 0);
				if (userService.deleteById(id) == 1) {
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
		
		public void refresh(){
			String name = nameSearchTF.getText();
			
			UserQuery userQuery = new UserQuery();
			userQuery.setName(name);
			
			UserTableModel userTableModel = new UserTableModel();
			userTableModel.query(userQuery);
			
			Page<User> page = userTableModel.getPage();
			countInfoLabel.setText("�ܹ�"+page.getCount()+"����¼");
			
			userTable.setModel(userTableModel);
		}
}
