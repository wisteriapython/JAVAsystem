package com.jzgx.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jzgx.app.TestFrame;
import com.jzgx.app.TestPanel;
import com.jzgx.data.DataSource;
import com.jzgx.model.ExamTest;
import com.jzgx.model.TestResult;

public class BtnYesAction implements ActionListener{
	
	private TestFrame testFrame;
	private int index;
	public BtnYesAction(int index,JFrame frame){
		this.testFrame=(TestFrame) frame;
		this.index=index;
	}

	public void actionPerformed(ActionEvent arg0) {
		TestPanel tp=(TestPanel) testFrame.jsplitPanle.getRightComponent();
		String examId=tp.labExamno.getText().substring(3);
		if("".equals(examId)){
			JOptionPane.showMessageDialog(testFrame, "��ǰ�޿ɲ�����Ŀ��","����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		ExamTest stuExamTest=DataSource.getInstance().getStuExamTestMap().get(examId);
		
		JCheckBox[] ck=tp.options;
		String str="";
		for(int i=0;i<ck.length;i++){
			if(ck[i].isSelected()){
				str+=ck[i].getName();
			}
		}
		if("".equals(str)){
			JOptionPane.showMessageDialog(testFrame, "��ѡ�����ȷ����","����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		stuExamTest.setStuAnswer(str);
		stuExamTest.setFlag(1);
		if(stuExamTest.getExam().getAnswer().contains(str)&&stuExamTest.getExam().getAnswer().length()==str.length()){
			stuExamTest.setMsg("�ش���ȷ");
		}else{
			stuExamTest.setMsg("�ش����");
		}
		
		//ͳ�ƴ���������
		TestResult tr=DataSource.getInstance().getTestResult();
		
		Map<String,ExamTest> stuExamTestMap=DataSource.getInstance().getStuExamTestMap();
		int successNum=0;
		int okNum=0;
		int errorNum=0;
		for(Iterator it=stuExamTestMap.keySet().iterator();it.hasNext();){
			String key=(String) it.next();
			ExamTest et=stuExamTestMap.get(key);
			if(et.getFlag()==1){
				successNum++;
				if("�ش���ȷ".equals(et.getMsg())){
					okNum++;
				}else if("�ش����".equals(et.getMsg())){
					errorNum++;
				}
			}
		}
		tr.setSuccNum(successNum);
		tr.setOkNum(okNum);
		tr.setErrorNum(errorNum);
		
		DecimalFormat df=new DecimalFormat("####.##");
		double percent1=(double)successNum/(double)tr.getExamNum()*100;
		double percent2=(double)okNum/(double)tr.getExamNum()*100;
		
		String str1=df.format(percent1);
		tr.setSuccPercent(str1);
		String str2=df.format(percent2);
		tr.setOkPercent(str2);
		
		tp=new TestPanel(index,testFrame);
		testFrame.jsplitPanle.setRightComponent(tp);
	}

}
