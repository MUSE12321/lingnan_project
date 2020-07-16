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
import com.lingnan.summer.view.GalleryView;

public class WasherSetDialog extends JDialog implements ActionListener {

	   private JPanel idPanel,namePanel,measurePanel,opePanel,modelPanel;
		
		private JLabel idLabel,nameLabel,measureLabel,modelLabel;
		private JTextField idTF,nameTF,measureTF,modelTF,tempTF;//tempTF�¶�
		
		private JButton saveBtn,cancelBtn,addBtn,subBtn;
		
		private TaskService taskService = new TaskServiceImpl();
		
	//	private AlarmView alarmView;
		private GalleryView galleryView;
		
	//private Alarm alarm;
		private Device device;
		private Task task;
		
		String mm;
		
		
		//����
		private JLabel addLabel,subLabel;
		public JComboBox<String>combo;
		public String[] model = {"��ϴģʽ","��׼ģʽ","��ˮģʽ","����ģʽ"};
		

		public WasherSetDialog(JFrame parent,GalleryView galleryView) {
			super(parent,"����");
			this.galleryView = galleryView;
			
			setSize(250,300);
			
			setLocationRelativeTo(null);
			
			setModal(true);
			setResizable(false);
			 
			this.setLayout(new FlowLayout());
			
			initView();
		}
		
		public WasherSetDialog(JFrame parent,GalleryView galleryView,Device device) {
			 this(parent,galleryView);
			 this.device = device;
			 setTitle("����");
			 //����
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
			nameLabel = new JLabel("�豸����");
			nameTF = new JTextField(15);
			namePanel.add(nameLabel);
			namePanel.add(nameTF);
			
			measurePanel = new JPanel();
			measureLabel = new JLabel("ˮλ");
			measurePanel.add(measureLabel);
			measureTF = new JTextField("5", 5);
			measurePanel.add(measureTF);
						
		ImageIcon addicon = new ImageIcon("static\\icon\\����.png");
		addicon.setImage(addicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		addBtn = new JButton(addicon);
		addBtn.setContentAreaFilled(false);// ͸��

		ImageIcon subicon = new ImageIcon("static\\icon\\��С.png");
		subicon.setImage(subicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		subBtn = new JButton(subicon);
		subBtn.setContentAreaFilled(false);// ͸��
			
			
			addBtn.addActionListener(this);
			subBtn.addActionListener(this);
			measurePanel.add(addBtn);
			measurePanel.add(subBtn);
		

			
//����ģʽ
			modelPanel = new JPanel();
			modelLabel = new JLabel("ģʽ");
			modelPanel.add(modelLabel);
			combo = new JComboBox<String>(model);
		    modelPanel.setBounds(120, 190, 300, 100);
		    modelPanel.add(combo);
			
			
			opePanel = new JPanel();
			saveBtn = new JButton("����");
			cancelBtn = new JButton("ȡ��");
			saveBtn.addActionListener(this);
			cancelBtn.addActionListener(this);
			opePanel.add(saveBtn);
			opePanel.add(cancelBtn);
			
		
			
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
		int temp = Integer.parseInt(measureTF.getText()) ;
		measureTF.setText(temp+"");
		if (source == addBtn) {
			// Ƶ������
			temp = temp + 1;
			measureTF.setText(temp + "");
		} else if (source == subBtn) {
			// Ƶ����С
			temp = temp - 1;
			measureTF.setText(temp + "");
		} else if (source == saveBtn) {
			String name = nameTF.getText();
			String measure = measureTF.getText();

			if (this.task == null) {
				// TODO ����У��
				Device device = new Device();
				device.setDname(name);
				device.setTemp(temp);
				Task task = new Task();
				task.setDname(name);
				task.setInstruction("ˮλ:"  +temp +","+(String) combo.getSelectedItem());		
				
				int result = taskService.add(task);
				if (result == 1) {
					this.dispose();
					JOptionPane.showMessageDialog(this, "���óɹ�", "��ʾ",JOptionPane.INFORMATION_MESSAGE);
					// ˢ��
					galleryView.refresh();

				} else {
					JOptionPane.showMessageDialog(this, "����ʧ��", "��ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}

		}else if (source == cancelBtn) {
				this.dispose();
			}
		}

	}


