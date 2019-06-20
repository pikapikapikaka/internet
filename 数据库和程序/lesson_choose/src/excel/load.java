package excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import bean.Student;
import bean.Teacher;
import util.HibernateUtil;

public class load {
	 HSSFCell cell = null;
	public static void f(String address)
	{
		//List<Student> list = new ArrayList<Student>();
	try
	{
		POIFSFileSystem fs=new POIFSFileSystem(new File(address));   
	//�õ�Excel����������    
		HSSFWorkbook wb = new HSSFWorkbook(fs);  
	//�õ�Excel���������    
		HSSFSheet sheet = wb.getSheetAt(0);   
	//�õ�Excel���������    
		int minRow = sheet.getFirstRowNum();  //��С��
		int maxRow = sheet.getLastRowNum();  //�����
		for(int i = minRow;i<=maxRow;i++ )
		{
			Student s = new Student();
			HSSFRow row = sheet.getRow(i);  
	//�õ�Excel������ָ���еĵ�Ԫ��    
			String id = sheet.getRow(i).getCell(0).toString();//�õ�ѧ��
			//name = "1";
			String name= sheet.getRow(i).getCell(1).toString();//�õ�����
			if((id == null) || (name == null))
			{
				continue;
			}
			else
			//System.out.println("ѧ�ţ�"+id+"������"+name);
			s.setId(id);
			s.setName(name);
			s.setPwd(id);
			HibernateUtil.add(s);
			}
	}
	catch(Exception e)
	{
		
	}
	
}
	public static void tea(String address)
	{
		//List<Student> list = new ArrayList<Student>();
		System.out.println(address);
	try
	{
		POIFSFileSystem fs=new POIFSFileSystem(new File(address));   
	//�õ�Excel����������    
		HSSFWorkbook wb = new HSSFWorkbook(fs);  
	//�õ�Excel���������    
		HSSFSheet sheet = wb.getSheetAt(0);   
	//�õ�Excel���������    
		int minRow = sheet.getFirstRowNum();  //��С��
		int maxRow = sheet.getLastRowNum();  //�����
		for(int i = minRow;i<=maxRow;i++ )
		{
			Teacher t = new Teacher();
			HSSFRow row = sheet.getRow(i);  
	//�õ�Excel������ָ���еĵ�Ԫ��    
			String id = sheet.getRow(i).getCell(0).toString();//�õ�ѧ��
			//name = "1";
			String name= sheet.getRow(i).getCell(1).toString();//�õ�����
			String rank = sheet.getRow(i).getCell(2).toString();//�õ�ְ��
			//String rank = tea.getRank();
			if("����".equals(rank))
				t.setNum(4);
			else if("������".equals(rank))
				t.setNum(3);
			else if("��ʦ".equals(rank))
				t.setNum(2);
			else if("����".equals(rank))
				t.setNum(1);
			else
			{
				t.setNum(0);
			}
			if((id == null) || (name == null))
			{
				continue;
			}
			else
			//System.out.println("ѧ�ţ�"+id+"������"+name);
			t.setId(id);
			t.setName(name);
			t.setPwd(id);
			t.setRank(rank);
			HibernateUtil.add(t);
			}
	}
	catch(Exception e)
	{
		
	}
	
}
}
