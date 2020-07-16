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

import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.service.AlarmService;
import com.lingnan.summer.service.impl.AlarmServiceImpl;
import com.lingnan.summer.view.AlarmView;

public class AlarmDialog extends JDialog implements ActionListener {
	
    private JPanel idPanel,namePanel,riskPanel,measurePanel,opePanel;
	
	private JLabel idLabel,nameLabel,riskLabel,measureLabel;
	private JTextField idTF,nameTF,riskTF,measureTF;
	
	private JButton saveBtn,cancelBtn;
	
	private AlarmService alarmService = new AlarmServiceImpl();
	
	private AlarmView alarmView;
	
	private Alarm alarm;
	
	public AlarmDialog(JFrame parent,AlarmView alarmView) {
		super(parent,"���");
		this.alarmView = alarmView;
		
		setSize(350,300);
		
		setLocationRelativeTo(null);
		
		setModal(true);
		setResizable(false);
		 
		this.setLayout(new FlowLayout());
		
		initView();
	}
	
	public AlarmDialog(JFrame parent,AlarmView alarmView,Alarm alarm) {
		 this(parent,alarmView);
		 this.alarm = alarm;
		 setTitle("�޸�");
		 //����
		 this.nameTF.setText(alarm.getDname());
		 this.riskTF.setText(alarm.getRisk());
		 this.measureTF.setText(alarm.getMeasure());
		 this.idPanel.setVisible(false);
	}
	
	private void initView() {
		idPanel = new JPanel();
		idLabel = new JLabel("id");
		idTF = new JTextField(15);
		idPanel.add(idLabel);
		idPanel.add(idTF);
		
		namePanel = new JPanel();
		nameLabel = new JLabel("�豸��");
		nameTF = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);
		
		riskPanel = new JPanel();
		riskLabel = new JLabel("����");
		riskTF = new JTextField(15);
		riskPanel.add(riskLabel);
		riskPanel.add(riskTF);
		
		measurePanel = new JPanel();
		measureLabel = new JLabel("��ʩ");
		measureTF = new JTextField(15);
		measurePanel.add(measureLabel);
		measurePanel.add(measureTF);
		
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
		container.add(riskPanel);
		container.add(measurePanel);
		container.add(opePanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==saveBtn){
			int id = Integer.valueOf(idTF.getText()).intValue();
			String name = nameTF.getText();
			String risk = riskTF.getText();
			String measure = measureTF.getText();
			
			if(this.alarm==null) {
				//TODO ����У��
				Alarm alarm = new Alarm();
				alarm.setDid(id);
				alarm.setDname(name);
				alarm.setRisk(risk);
				alarm.setMeasure(measure);
				
				int result = alarmService.add(alarm);
				if(result==1) {
					this.dispose();
					JOptionPane.showMessageDialog(this,"��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					//ˢ��
					alarmView.refresh();
				}else {
					JOptionPane.showMessageDialog(this,"���ʧ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}else {
				//����
				Alarm alarm = new Alarm();
				alarm.setDname(name);
				alarm.setRisk(risk);
				alarm.setMeasure(measure);
				alarm.setDid(this.alarm.getDid());
				alarm.setAid(this.alarm.getAid());
				
				int result = alarmService.update(alarm);
				if(result==1) {
					JOptionPane.showMessageDialog(this,"���³ɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					//ˢ��
					alarmView.refresh();
				}else {
					JOptionPane.showMessageDialog(this,"����ʧ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(source == cancelBtn) {
			
			this.dispose();
		}
	}
	
}
