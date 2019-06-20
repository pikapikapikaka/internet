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
	//得到Excel工作簿对象    
		HSSFWorkbook wb = new HSSFWorkbook(fs);  
	//得到Excel工作表对象    
		HSSFSheet sheet = wb.getSheetAt(0);   
	//得到Excel工作表的行    
		int minRow = sheet.getFirstRowNum();  //最小行
		int maxRow = sheet.getLastRowNum();  //最大行
		for(int i = minRow;i<=maxRow;i++ )
		{
			Student s = new Student();
			HSSFRow row = sheet.getRow(i);  
	//得到Excel工作表指定行的单元格    
			String id = sheet.getRow(i).getCell(0).toString();//得到学号
			//name = "1";
			String name= sheet.getRow(i).getCell(1).toString();//得到姓名
			if((id == null) || (name == null))
			{
				continue;
			}
			else
			//System.out.println("学号："+id+"姓名："+name);
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
	//得到Excel工作簿对象    
		HSSFWorkbook wb = new HSSFWorkbook(fs);  
	//得到Excel工作表对象    
		HSSFSheet sheet = wb.getSheetAt(0);   
	//得到Excel工作表的行    
		int minRow = sheet.getFirstRowNum();  //最小行
		int maxRow = sheet.getLastRowNum();  //最大行
		for(int i = minRow;i<=maxRow;i++ )
		{
			Teacher t = new Teacher();
			HSSFRow row = sheet.getRow(i);  
	//得到Excel工作表指定行的单元格    
			String id = sheet.getRow(i).getCell(0).toString();//得到学号
			//name = "1";
			String name= sheet.getRow(i).getCell(1).toString();//得到姓名
			String rank = sheet.getRow(i).getCell(2).toString();//得到职称
			//String rank = tea.getRank();
			if("教授".equals(rank))
				t.setNum(4);
			else if("副教授".equals(rank))
				t.setNum(3);
			else if("讲师".equals(rank))
				t.setNum(2);
			else if("助教".equals(rank))
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
			//System.out.println("学号："+id+"姓名："+name);
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
