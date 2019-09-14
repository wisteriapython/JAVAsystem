package com.jzgx.app;

import javax.swing.JFrame;

import com.jzgx.data.DataSource;
import com.jzgx.model.TestResult;

public class ExamTestThread extends Thread{
	
	private TestFrame testFrame;
	public boolean flag;
	private int cnt=0;
	public ExamTestThread(JFrame frame){
		this.testFrame=(TestFrame) frame;
	}

	@Override
	public void run() {
		while(!flag){
			testFrame.setTitle("��ӭ "+testFrame.configFrame.stuName+" ʹ�ù��ű���ѡ������ϰϵͳ, ��ʱ"+cnt+"��");
			cnt++;
			TestResult tr=DataSource.getInstance().getTestResult();
			tr.setUseTime(cnt+"");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
