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
		
		
		//上面
		private JPanel toolBarPanel;
		
		private JPanel searchPanel;
		private JLabel nameLabel;
		private JTextField nameSearchTF;
		private JButton searchBtn;
		
		private JPanel opePanel;
		private JButton addBtn,updateBtn,deleteBtn;
		
		//中间
		private JScrollPane tableScrollPane;
		private JTable userTable;
		
		//下面
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
			nameLabel = new JLabel("姓名");
			nameSearchTF = new JTextField(10);
			
			ImageIcon serchicon = new ImageIcon("static\\icon\\查找.png");
			serchicon.setImage(serchicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			searchBtn = new JButton("搜索",serchicon);
			searchBtn.addActionListener(this);
			
			opePanel =  new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
			
			ImageIcon addicon = new ImageIcon("static\\icon\\用户添加.png");
			addicon.setImage(addicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			addBtn =new JButton("添加",addicon);
			
			ImageIcon updateicon = new ImageIcon("static\\icon\\修改.png");
			updateicon.setImage(updateicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			updateBtn =new JButton("修改",updateicon);
			
			ImageIcon deleteicon = new ImageIcon("static\\icon\\用户删除.png");
			deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			deleteBtn =new JButton("删除",deleteicon);
			
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
			
			
			//中间
			userTable = new JTable();
			UserTableModel userTableModel = new UserTableModel();
			UserQuery userQuery = new UserQuery();
			userTableModel.query(userQuery);
			userTable = new JTable(userTableModel);
			tableScrollPane = new JScrollPane(userTable);
			
			//下面
			bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			countInfoLabel = new JLabel("总共10条");
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
					JOptionPane.showMessageDialog(this, "请选择至少一条记录!");
					return;
				}
				int id = (Integer)userTable.getValueAt(rowIndex,0);
				User user = userService.findUserById(id);
				
				if(user==null){
					JOptionPane.showMessageDialog(this,"记录不存在，请刷新!");
					return;
				}
				UserDialog userDialog = new UserDialog(jFrame,this,user);
				userDialog.setVisible(true);
				refresh();
			}else if(deleteBtn==source) {
				// 获取选中记录
				int rowIndex = userTable.getSelectedRow();
				if (rowIndex == -1) {
					JOptionPane.showMessageDialog(this, "请选中一条");
					return;
				}
				int id = (Integer) userTable.getValueAt(rowIndex, 0);
				if (userService.deleteById(id) == 1) {
					JOptionPane.showMessageDialog(this, "删除成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					refresh();
				} else {
					JOptionPane.showMessageDialog(this, "删除失败", "提示",
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
			countInfoLabel.setText("总共"+page.getCount()+"条记录");
			
			userTable.setModel(userTableModel);
		}
}
