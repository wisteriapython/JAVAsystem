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
			testFrame.setTitle("欢迎 "+testFrame.configFrame.stuName+" 使用国信笔试选择题练习系统, 用时"+cnt+"秒");
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
