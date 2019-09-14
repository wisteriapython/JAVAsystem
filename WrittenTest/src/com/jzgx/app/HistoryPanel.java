package com.jzgx.app;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.jzgx.data.XmlUtil;
import com.jzgx.model.TestResult;

public class HistoryPanel extends JPanel{

	public HistoryPanel(){
		this.setLayout(null);
		this.setBorder(new TitledBorder("历史成绩"));
		this.setBounds(10, 220, 775, 340);
		
		JScrollPane jsp=new JScrollPane();
		jsp.setBounds(5, 20, 765, 315);
		this.add(jsp);
		JTable scoreTable=new JTable();
		jsp.getViewport().add(scoreTable);
		
		List<TestResult> records=XmlUtil.readStuScore();
		
		DefaultTableModel dtm=new DefaultTableModel();
		dtm.addColumn("测试人");
		dtm.addColumn("测试科目");
		dtm.addColumn("测试日期");
		dtm.addColumn("用时");
		dtm.addColumn("总数量");
		dtm.addColumn("完成%");
		dtm.addColumn("正确%");
		
		for(TestResult tr:records){
			dtm.addRow(new Object[]{tr.getTestStu(),tr.getTestCourse(),tr.getTestDate(),tr.getUseTime(),tr.getExamNum(),tr.getSuccPercent(),tr.getOkPercent()});
		}
		scoreTable.setModel(dtm);
	}
}
