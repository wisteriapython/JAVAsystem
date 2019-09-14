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
	private Font myFont=new Font("宋体",Font.BOLD,14);

	public TestPanel(int index,JFrame frame){
		this.testFrame=(TestFrame) frame;
		this.setLayout(null);
		
		labResult=new JLabel();
		labResult.setBounds(20, 5, 600, 60);
		labResult.setText("测试题目总数："+DataSource.getInstance().getTestExams().size()+"");
		this.add(labResult);
		
		//题目外层面板
		JPanel panel=new JPanel();
		panel.setBounds(20, 70, 600, 320);
		panel.setBorder(new TitledBorder("题目"));
		panel.setLayout(null);
		this.add(panel);
		
		//里面套一个滚动面板
		JScrollPane sp1=new JScrollPane();
		sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp1.setBounds(10, 20, 580, 300);
		panel.add(sp1);
		
		//然后在套上一个显示题目和选项的面板
		p1=new JPanel();
		BoxLayout boxLayout=new BoxLayout(p1,BoxLayout.Y_AXIS);
		p1.setLayout(boxLayout);
		p1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		sp1.getViewport().add(p1);
		
		//获得当前选择的题目
		List<Exam> exams=DataSource.getInstance().getTestExams();
		Exam exam=null;	//当前测试题目
		ExamTest examTest=null;	//当前测试结果
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
		
		//显示题目信息
		labExamno=new JLabel("题号："+exam.getExamId());
		labExamno.setHorizontalAlignment(SwingConstants.LEFT);
		p1.add(labExamno);
		p1.add(new JLabel(" "));
		
		labQuestion=new JTextArea();
		labQuestion.setLineWrap(true);
		labQuestion.setAlignmentX(0);
		labQuestion.setBackground(p1.getBackground());
		labQuestion.setEditable(false);
		labQuestion.setText("题目：  "+exam.getQuestion());
		labQuestion.setFont(myFont);
		p1.add(labQuestion);
		p1.add(new JSeparator());
		
		options=new JCheckBox[exam.getOptions().size()];		//选项
		JPanel[] optPanel=new JPanel[exam.getOptions().size()];	//选择面板，包括checkbox和提示选项内容textarea
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
		
		
		
		
		btnUp=new JButton("上一题");
		btnUp.setBounds(40, 395, 80, 30);
		this.add(btnUp);
		btnUp.addActionListener(new BtnUpAction(index,testFrame));
		
		btnNext=new JButton("下一题");
		btnNext.setBounds(140, 395, 80, 30);
		this.add(btnNext);
		btnNext.addActionListener(new BtnNextAction(index,testFrame));
		
		btnYes=new JButton("确定");
		btnYes.setBounds(240, 395, 80, 30);
		btnYes.addActionListener(new BtnYesAction(index,testFrame));
		this.add(btnYes);
		
		labCurrent=new JLabel();
		labCurrent.setBounds(340, 395, 100, 30);
		this.add(labCurrent);
		
		btnBack=new JButton("结束测试");
		btnBack.setBounds(500, 395, 90, 30);
		btnBack.addActionListener(new BtnBackAction(testFrame));
		this.add(btnBack);
	
		//答案面板
		p2=new JPanel();
		p2.setBounds(20, 430, 600, 130);
		p2.setBorder(new TitledBorder("提示答案"));
		p2.setLayout(null);
		JScrollPane jsp2=new JScrollPane();
		jsp2.setBounds(10, 20, 580, 100);
		p2.add(jsp2);
		this.add(p2);
		
		//内嵌一个面板
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
		
		
		//如果是已经答过的题，将学生选择答案显示在面板上
		if(null!=examTest){
			if(examTest.getFlag()==1){
				this.labCurrent.setText(examTest.getMsg());
				this.labDesc.setText("知识点分析："+examTest.getExam().getDesc());
				this.labAnswer.setText("正确答案："+examTest.getExam().getAnswer());
			}
			//讲左边窗口题目颜色改变
			if("回答正确".equals(examTest.getMsg())){
				this.testFrame.examPanel.examBtn[index].setBackground(Color.GREEN);
				this.labCurrent.setForeground(Color.blue);
			}else if("回答错误".equals(examTest.getMsg())){
				this.testFrame.examPanel.examBtn[index].setBackground(Color.RED);
				this.labCurrent.setForeground(Color.RED);
			}
			this.testFrame.jsplitPanle.setDividerLocation(250);
			if(examTest.getFlag()==1){
				this.btnYes.setEnabled(false);
			}
			
			//恢复学生答题选项
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
			String result="<html>测试科目："+DataSource.getInstance().getTestCourse()+", 测试总题目数: "+tr.getExamNum()+" 题,";
			
			result+="<br>现在完成："+tr.getSuccNum()+" 题，完成  "+tr.getSuccPercent()+"%,  其中正确："+tr.getOkNum()+" 题,错误："+tr.getErrorNum()+" 题,正确率： "+tr.getOkPercent()+"%</html>";
			labResult.setText(result);
			labResult.setFont(myFont);
		}
	}
}
