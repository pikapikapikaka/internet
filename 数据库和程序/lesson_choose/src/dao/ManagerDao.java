package dao;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import bean.Control;
import bean.Manager;
import bean.Picture;
import bean.Question;
import bean.Student;
import bean.Teacher;
import excel.load;
import util.HibernateUtil;

public class ManagerDao {
	Manager m ;
	String fileFileName;
	String fileContentType;
	Student stu;
	Teacher tea;
	
	public Teacher getTea() {
		return tea;
	}
	public void setTea(Teacher tea) {
		this.tea = tea;
	}
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	private File file;//外边上传的file
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	public ManagerDao() {
		// TODO Auto-generated constructor stub
		try {
			request.setCharacterEncoding("UTF-8");//设置request的编码格式
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Manager getM() {
		return m;
	}
	public void setM(Manager m) {
		this.m = m;
	}
	//
	public String t_s()
	{
		String sid = (String)request.getParameter("sid");
		Student s = (Student)HibernateUtil.get(Student.class, sid);
		int th = s.getTh();
		Question q = (Question)HibernateUtil.get(Question.class, th);
		q.setZt(1);
		s.setTh(0);
		HibernateUtil.update(q);
		HibernateUtil.update(s);
		return "success";
	}
	/**
	 * 查看这个题是否已经选择
	 * */
	public String s_ischecked()
	{
		int id = Integer.parseInt(request.getParameter("id"));
		String condition = " where th = "+id;
		List<Student> list = HibernateUtil.query("bean.Student", condition, "");
		if(list == null)
		{
			request.setAttribute("msg", "没人选呢");
		}
		else
		{
		request.setAttribute("checked_student", list.get(0));
		}
		return "success";
	}
	/**
	 * 将学生添加
	 * */
	public String s_add()
	{
		
		String sid = request.getParameter("sid");
		int tid = Integer.parseInt(request.getParameter("tid"));
		//String condition = " where th = "+tid;
		//Student stu = (Student)HibernateUti;
		Student s = (Student)HibernateUtil.get(Student.class, sid);
		String condition = " where th = "+tid;
		List<Student> list = HibernateUtil.query("bean.Student", condition, "");
		if(list == null)
		{
			if(s == null)
			{
				request.setAttribute("fail", "无此学号");
				return "fail";
			}
			else if(s.getTh() != 0)
			{
				request.setAttribute("fail", "这个同学已经选课题了！");
				return "fail";
			}
			else
			{
				s.setTh(tid);
				HibernateUtil.update(s);
				return "success";
			}
		}
		else
		{
			request.setAttribute("fail", "这个题已经被别人选了，请联系教师！");
			return "fail";
		}
		
		
	}
	/**
	 * 课题查询
	 * */
	public String keti_select()
	{
		String condition = "";
		String tj = request.getParameter("tj");
		System.out.println("tj: "+tj);
		String str = request.getParameter("con");
		if("stu".equals(tj))
		{
			Student s = (Student)HibernateUtil.get(Student.class, str);
			if(s == null)
			{
				request.setAttribute("fail", "无此学号！");
				return "fail";
			}
			else
			{
				if(s.getTh() == 0)
				{
					request.setAttribute("fail", "该同学并未选题");
					return "fail";
				}
				else
				{
					Question q = (Question)HibernateUtil.get(Question.class, s.getTh());
					Teacher t = (Teacher)HibernateUtil.get(Teacher.class, q.getTid());
					request.setAttribute("question", q);
					request.setAttribute("student", s);
					request.setAttribute("teacher", t);
					return "student";
				}
			}
			}

		else
		{
			Teacher t = (Teacher)HibernateUtil.get(Teacher.class, str);
			if(t == null)
			{
				request.setAttribute("fail", "无此账号！！");
				return "fail";
			}
			else
			{
				condition = " where tid like '"+str+"'";
				List<Question> list = HibernateUtil.query("bean.Question", condition, "");
				request.setAttribute("question", list);
				request.setAttribute("teacher", t);
				return "teacher";
			}
		}
					
		
	}
	/**
	 * 分类查询
	 * */
	public String stu_select()
	{
		String tj = request.getParameter("leixing");
		String name = request.getParameter("name");
		String condition = "where "+tj+" like "+"'%"+name+"%'";
		List<Student> list = HibernateUtil.query("bean.Student", condition, "");
		request.setAttribute("select_student", list);
		return "success";
	}
	/**
	 * 学生信息管理
	 * */
	public String stu_man()
	{
		int all = HibernateUtil.recordCount("bean.Student", "");
		int allpage = all/10;
		if(all%10!=0)
			allpage++;
		session.setAttribute("stu_endpage", allpage);
		int page = Integer.parseInt(request.getParameter("page"));
		if(all == 0 )
		{
			page=0;
		}
		
		List<Student> list = new ArrayList<>();
		session.setAttribute("stu_page", page);
		list = HibernateUtil.query("bean.Student", "", "",page,10);//查询到数据库中pic所有信息
		session.setAttribute("student", list);//存到session属性里
		System.out.println(list.size());
		return "success";
	}
	public String delete_stu()
	{
		List<Student> list = HibernateUtil.query("bean.Student", "", "");
		for(int i = 0;i<list.size();i++)
		{
			HibernateUtil.delete(list.get(i));
		}
		return "success";
	}
	public String delete_check()
	{
		Student stu = new Student();
		String[] s = request.getParameterValues("ck");
		for(int i = 0;i<s.length;i++)
		{
			stu.setId(s[i]);
			HibernateUtil.delete(stu);
		}
		return "success";
	}
	/**
	 * 学生信息删除
	 * */
	public String stu_delete()
	{
		HibernateUtil.delete(HibernateUtil.get(Student.class, request.getParameter("id")));
		return "success";
	}
	/**
	 * 单个学生信息添加
	 * */
	public String stu_add()
	{
		stu.setPwd(stu.getId());
		HibernateUtil.add(stu);
		return "success";
	}
	/**
	 * 批量导入学生信息的类
	 * */
	public String stu_in()
	{
		String address = request.getParameter("name");
		load.f(address);
		return "success";
	}
	/**
	 * 管理员信息更新的类
	 * */
	public String update()
	{
		Manager man = (Manager) session.getAttribute("manager");
		String pwd = request.getParameter("oldpwd");//得到表单的密码
		if(man.getPwd().equals(pwd) == false)
		{
			request.setAttribute("msg", "密码错误！");//设置一个属性，返回到上一个界面
			return "fail";
		}
		else
		{
			/**
			 * 获取并设置各种属性，提交
			 * */
			request.setAttribute("msg", "");
			man.setName(request.getParameter("name"));
			man.setPwd(request.getParameter("newpwd"));
			man.setPhone(request.getParameter("phone"));
			HibernateUtil.update(man);
			return "success";
		}
		
		
	}
	/**
	 * 添加轮轮播图片的类
	 * */
	public String pic_upload()
	{
		Picture pic = new Picture();//存储数据库
		/**
		 * 书上代码
		 * */
		String realPath = ServletActionContext.getServletContext().getRealPath("/picture");
		//System.out.println(realPath);
		//System.out.println("name: "+fileFileName);
		String lujing = null;
		if(file!=null)
		{
			
			File save = new File(new File(realPath),fileFileName);
			lujing = save.getAbsolutePath();
			if(!save.getParentFile().exists())
				save.getParentFile().mkdirs();
			try {
				FileUtils.copyFile(file, save);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("abslutely :"+lujing);
		pic.setName(fileFileName);
		pic.setAddress("../picture"+"/"+fileFileName);
		HibernateUtil.add(pic);
		return "success";
	}
	/**
	 * 删除图片的方法
	 * */
	public String pic_delete()
	{
		Picture p = new Picture();
		
		String id = request.getParameter("id");
		p.setId(Integer.parseInt(id));
		HibernateUtil.delete(p);
		return "success";
	}
	/**
	 * 从数据库中取轮播图片的类
	 * */
	public String pic_man()
	{
		List<Picture> list = new ArrayList<>();
		list = HibernateUtil.query("bean.Picture", "", "");//查询到数据库中pic所有信息
		request.setAttribute("pic", list);//存到session属性里
		return "success";
	}
	/**
	 * 教师信息删除
	 * */
	public String tea_delete()
	{
		HibernateUtil.delete(HibernateUtil.get(Teacher.class, request.getParameter("id")));
		return "success";
	}
	/**
	 * 教师信息修改
	 * */
	public String tea_update()
	{
		Teacher teacher = (Teacher)HibernateUtil.get(Teacher.class, request.getParameter("id"));
		request.setAttribute("teacher",teacher);
		return "success";
	}
	/**
	 * 对于状态的改变
	 * */
	public String con_gg()
	{
		Control c = new Control();
		
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		System.out.println(name+"   "+value);
		if(value.equals("开放"))
			value="关闭";
		else
			value = "开放";
		c.setName(name);
		c.setValue(value);
		HibernateUtil.update(c);
		return "success";
	}
	/**
	 * 各种条件控制
	 * */
	public  String control()
	{
		List<Control> list;
		list = HibernateUtil.query("bean.Control", "", "");
		System.out.println("the size is "+list.size());
		request.setAttribute("control", list);
		return "success";
	}
	/**
	 * 教师类分类查询
	 * */
	public String tea_select()
	{

		String tj = request.getParameter("leixing");
		String name = request.getParameter("name");
		String condition = "where "+tj+" like "+"'%"+name+"%'";
		List<Student> list = HibernateUtil.query("bean.Teacher", condition, "");
		request.setAttribute("select_teacher", list);
		return "success";
	}
	/**
	 * 老师信息查询
	 * */
	public String tea_man()
	{
		int page;
		int all = HibernateUtil.recordCount("bean.Teacher", "");
		int allpage = all/10;
		
		if(all%10!=0)
			allpage++;
		session.setAttribute("tea_endpage", allpage);
		 page = Integer.parseInt(request.getParameter("page"));
		 if(all == 0)
				page = 0;
		List<Teacher> list = new ArrayList<>();
		session.setAttribute("tea_page", page);
		list = HibernateUtil.query("bean.Teacher", "", "",page,10);//查询到数据库中Teacher所有信息
		session.setAttribute("teacher", list);//存到session属性里
		return "success";
	}
	/**
	 * 教师信息更新
	 * */
	public String teacher_update()
	{
		//System.out.println(tea.getId());
		HibernateUtil.update(tea);
		return "success";
	}
	/**
	 * 添加单个教师信息
	 * */
	public String tea_add()
	{
		/*String id = tea.getId();
		System.out.println("id: "+id);
		Teacher tt =  (Teacher)HibernateUtil.get(Teacher.class, id);
		if()
	    {
				request.setAttribute("fail", "教职工号不对！");
				return "fail";}
				else
		{
				
		}*/
		HibernateUtil.add(tea);	
		return "success";
	}
	/**
	 * 教师信息批量导入
	 * */
	public String tea_in()
	{
		String address = request.getParameter("name");
		load.tea(address);
		return "success";
	}
	/**
	 * 验证管理员登陆的类
	 * */
	public String yz()
	{
		String id = request.getParameter("name");//获取账号（主键）
		String pwd = request.getParameter("pwd");//获取密码
		String code = request.getParameter("code");//获取验证码
		String code2 = (String)session.getAttribute("code");//获取生成的验证码，
		if(code2.equals(code))//如果相等
			{
				m = (Manager) HibernateUtil.get(Manager.class, id);//从数据库中找对应的数据
				if(m == null)
				{
					session.setAttribute("fail", "用户名不存在！");
					return "fail";
				}
				else
				{
					if(m.getId().equals(id)&&m.getPwd().equals(pwd))//密码不对
					{
						//System.out.println("完成！");
						session.setAttribute("manager", m);
						session.setAttribute("fail", "");
						//session.setAttribute("manager", m);
						return "success";
					}
					else
					{
						session.setAttribute("fail", "密码错误！");
						return "fail";
					}
				}
			}
		else
		{
			session.setAttribute("fail", "验证码错误！！");
			return "fail";
		}
		
		
	}

}
