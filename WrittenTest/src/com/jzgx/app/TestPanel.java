package com.jzgx.app;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.jzgx.action.BtnBackAction;
import com.jzgx.action.BtnNextAction;
import com.jzgx.action.BtnUpAction;
import com.jzgx.action.BtnYesAction;
import com.jzgx.data.DataSource;
import com.jzgx.model.Exam;
import com.jzgx.model.ExamTest;
import com.jzgx.model.TestResult;

public class TestPanel extends JPanel{
	
	private JPanel p1;
	public JPanel p2;
	
	public JLabel labExamno;
	private JTextArea labQuestion;
	public JCheckBox[] options;
	
	private JButton btnUp;
	private JButton btnNext;
	private JButton btnYes;
	private JButton btnBack;
	public JLabel labResult;
	public JLabel labCurrent;
	
	public JLabel labAnswer;
	public JTextArea labDesc;
	
	private TestFrame testFrame;
	private Font myFont=new Font("����",Font.BOLD,14);

	public TestPanel(int index,JFrame frame){
		this.testFrame=(TestFrame) frame;
		this.setLayout(null);
		
		labResult=new JLabel();
		labResult.setBounds(20, 5, 600, 60);
		labResult.setText("������Ŀ������"+DataSource.getInstance().getTestExams().size()+"");
		this.add(labResult);
		
		//��Ŀ������
		JPanel panel=new JPanel();
		panel.setBounds(20, 70, 600, 320);
		panel.setBorder(new TitledBorder("��Ŀ"));
		panel.setLayout(null);
		this.add(panel);
		
		//������һ���������
		JScrollPane sp1=new JScrollPane();
		sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp1.setBounds(10, 20, 580, 300);
		panel.add(sp1);
		
		//Ȼ��������һ����ʾ��Ŀ��ѡ������
		p1=new JPanel();
		BoxLayout boxLayout=new BoxLayout(p1,BoxLayout.Y_AXIS);
		p1.setLayout(boxLayout);
		p1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		sp1.getViewport().add(p1);
		
		//��õ�ǰѡ�����Ŀ
		List<Exam> exams=DataSource.getInstance().getTestExams();
		Exam exam=null;	//��ǰ������Ŀ
		ExamTest examTest=null;	//��ǰ���Խ��
		if(null==exams||exams.size()<1){
			exam=new Exam();
			exam.setExamId("");
			exam.setQuestion("");
			exam.setOptions(new ArrayList());
			exam.setValues(new ArrayList());
		}else{
			exam=DataSource.getInstance().getTestExams().get(index);
			examTest=DataSource.getInstance().getStuExamTestMap().get(exam.getExamId());
		}
		
		//��ʾ��Ŀ��Ϣ
		labExamno=new JLabel("��ţ�"+exam.getExamId());
		labExamno.setHorizontalAlignment(SwingConstants.LEFT);
		p1.add(labExamno);
		p1.add(new JLabel(" "));
		
		labQuestion=new JTextArea();
		labQuestion.setLineWrap(true);
		labQuestion.setAlignmentX(0);
		labQuestion.setBackground(p1.getBackground());
		labQuestion.setEditable(false);
		labQuestion.setText("��Ŀ��  "+exam.getQuestion());
		labQuestion.setFont(myFont);
		p1.add(labQuestion);
		p1.add(new JSeparator());
		
		options=new JCheckBox[exam.getOptions().size()];		//ѡ��
		JPanel[] optPanel=new JPanel[exam.getOptions().size()];	//ѡ����壬����checkbox����ʾѡ������textarea
		JTextArea[] optText=new JTextArea[exam.getOptions().size()];
		for(int i=0;i<exam.getOptions().size();i++){
			optPanel[i]=new JPanel();
			optPanel[i].setSize(500, 30);
			optPanel[i].setAlignmentX(0);
			p1.add(optPanel[i]);
			p1.add(new JSeparator());
			
			options[i]=new JCheckBox();
			options[i].setName(exam.getValues().get(i));
			options[i].setText(exam.getValues().get(i));
			optText[i]=new JTextArea();
			optText[i].setSize(500, 30);
			optText[i].setLineWrap(true);
			optText[i].setBackground(p1.getBackground());
			optText[i].setEditable(false);
			optText[i].setText(exam.getOptions().get(i));
			optText[i].setFont(myFont);
			
			optPanel[i].add(options[i]);
			optPanel[i].add(optText[i]);
		}
		
		
		
		
		btnUp=new JButton("��һ��");
		btnUp.setBounds(40, 395, 80, 30);
		this.add(btnUp);
		btnUp.addActionListener(new BtnUpAction(index,testFrame));
		
		btnNext=new JButton("��һ��");
		btnNext.setBounds(140, 395, 80, 30);
		this.add(btnNext);
		btnNext.addActionListener(new BtnNextAction(index,testFrame));
		
		btnYes=new JButton("ȷ��");
		btnYes.setBounds(240, 395, 80, 30);
		btnYes.addActionListener(new BtnYesAction(index,testFrame));
		this.add(btnYes);
		
		labCurrent=new JLabel();
		labCurrent.setBounds(340, 395, 100, 30);
		this.add(labCurrent);
		
		btnBack=new JButton("��������");
		btnBack.setBounds(500, 395, 90, 30);
		btnBack.addActionListener(new BtnBackAction(testFrame));
		this.add(btnBack);
	
		//�����
		p2=new JPanel();
		p2.setBounds(20, 430, 600, 130);
		p2.setBorder(new TitledBorder("��ʾ��"));
		p2.setLayout(null);
		JScrollPane jsp2=new JScrollPane();
		jsp2.setBounds(10, 20, 580, 100);
		p2.add(jsp2);
		this.add(p2);
		
		//��Ƕһ�����
		JPanel ansPanel=new JPanel();
		ansPanel.setLayout(new BoxLayout(ansPanel,BoxLayout.Y_AXIS));
		ansPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jsp2.getViewport().add(ansPanel);
		
		labAnswer=new JLabel();
		labAnswer.setHorizontalAlignment(SwingConstants.LEFT);
		ansPanel.add(labAnswer);
		ansPanel.add(new JLabel(" "));
		
		labDesc=new JTextArea();
		labDesc.setAlignmentX(0);
		labDesc.setLineWrap(true);
		labDesc.setEditable(false);
		labDesc.setBackground(ansPanel.getBackground());
		ansPanel.add(labDesc);
		
		
		//������Ѿ�������⣬��ѧ��ѡ�����ʾ�������
		if(null!=examTest){
			if(examTest.getFlag()==1){
				this.labCurrent.setText(examTest.getMsg());
				this.labDesc.setText("֪ʶ�������"+examTest.getExam().getDesc());
				this.labAnswer.setText("��ȷ�𰸣�"+examTest.getExam().getAnswer());
			}
			//����ߴ�����Ŀ��ɫ�ı�
			if("�ش���ȷ".equals(examTest.getMsg())){
				this.testFrame.examPanel.examBtn[index].setBackground(Color.GREEN);
				this.labCurrent.setForeground(Color.blue);
			}else if("�ش����".equals(examTest.getMsg())){
				this.testFrame.examPanel.examBtn[index].setBackground(Color.RED);
				this.labCurrent.setForeground(Color.RED);
			}
			this.testFrame.jsplitPanle.setDividerLocation(250);
			if(examTest.getFlag()==1){
				this.btnYes.setEnabled(false);
			}
			
			//�ָ�ѧ������ѡ��
			String str=examTest.getStuAnswer();
			char[] c=str.toCharArray();
			for(int i=0;i<c.length;i++){
				for(int j=0;j<options.length;j++){
					if(options[j].getName().equals(c[i]+"")){
						options[j].setSelected(true);
					}
				}
			}
			
			TestResult tr=DataSource.getInstance().getTestResult();
			String result="<html>���Կ�Ŀ��"+DataSource.getInstance().getTestCourse()+", ��������Ŀ��: "+tr.getExamNum()+" ��,";
			
			result+="<br>������ɣ�"+tr.getSuccNum()+" �⣬���  "+tr.getSuccPercent()+"%,  ������ȷ��"+tr.getOkNum()+" ��,����"+tr.getErrorNum()+" ��,��ȷ�ʣ� "+tr.getOkPercent()+"%</html>";
			labResult.setText(result);
			labResult.setFont(myFont);
		}
	}
}
