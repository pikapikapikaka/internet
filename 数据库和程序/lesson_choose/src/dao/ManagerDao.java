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
	private File file;//����ϴ���file
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
			request.setCharacterEncoding("UTF-8");//����request�ı����ʽ
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
	 * �鿴������Ƿ��Ѿ�ѡ��
	 * */
	public String s_ischecked()
	{
		int id = Integer.parseInt(request.getParameter("id"));
		String condition = " where th = "+id;
		List<Student> list = HibernateUtil.query("bean.Student", condition, "");
		if(list == null)
		{
			request.setAttribute("msg", "û��ѡ��");
		}
		else
		{
		request.setAttribute("checked_student", list.get(0));
		}
		return "success";
	}
	/**
	 * ��ѧ�����
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
				request.setAttribute("fail", "�޴�ѧ��");
				return "fail";
			}
			else if(s.getTh() != 0)
			{
				request.setAttribute("fail", "���ͬѧ�Ѿ�ѡ�����ˣ�");
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
			request.setAttribute("fail", "������Ѿ�������ѡ�ˣ�����ϵ��ʦ��");
			return "fail";
		}
		
		
	}
	/**
	 * �����ѯ
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
				request.setAttribute("fail", "�޴�ѧ�ţ�");
				return "fail";
			}
			else
			{
				if(s.getTh() == 0)
				{
					request.setAttribute("fail", "��ͬѧ��δѡ��");
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
				request.setAttribute("fail", "�޴��˺ţ���");
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
	 * �����ѯ
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
	 * ѧ����Ϣ����
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
		list = HibernateUtil.query("bean.Student", "", "",page,10);//��ѯ�����ݿ���pic������Ϣ
		session.setAttribute("student", list);//�浽session������
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
	 * ѧ����Ϣɾ��
	 * */
	public String stu_delete()
	{
		HibernateUtil.delete(HibernateUtil.get(Student.class, request.getParameter("id")));
		return "success";
	}
	/**
	 * ����ѧ����Ϣ���
	 * */
	public String stu_add()
	{
		stu.setPwd(stu.getId());
		HibernateUtil.add(stu);
		return "success";
	}
	/**
	 * ��������ѧ����Ϣ����
	 * */
	public String stu_in()
	{
		String address = request.getParameter("name");
		load.f(address);
		return "success";
	}
	/**
	 * ����Ա��Ϣ���µ���
	 * */
	public String update()
	{
		Manager man = (Manager) session.getAttribute("manager");
		String pwd = request.getParameter("oldpwd");//�õ���������
		if(man.getPwd().equals(pwd) == false)
		{
			request.setAttribute("msg", "�������");//����һ�����ԣ����ص���һ������
			return "fail";
		}
		else
		{
			/**
			 * ��ȡ�����ø������ԣ��ύ
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
	 * ������ֲ�ͼƬ����
	 * */
	public String pic_upload()
	{
		Picture pic = new Picture();//�洢���ݿ�
		/**
		 * ���ϴ���
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
	 * ɾ��ͼƬ�ķ���
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
	 * �����ݿ���ȡ�ֲ�ͼƬ����
	 * */
	public String pic_man()
	{
		List<Picture> list = new ArrayList<>();
		list = HibernateUtil.query("bean.Picture", "", "");//��ѯ�����ݿ���pic������Ϣ
		request.setAttribute("pic", list);//�浽session������
		return "success";
	}
	/**
	 * ��ʦ��Ϣɾ��
	 * */
	public String tea_delete()
	{
		HibernateUtil.delete(HibernateUtil.get(Teacher.class, request.getParameter("id")));
		return "success";
	}
	/**
	 * ��ʦ��Ϣ�޸�
	 * */
	public String tea_update()
	{
		Teacher teacher = (Teacher)HibernateUtil.get(Teacher.class, request.getParameter("id"));
		request.setAttribute("teacher",teacher);
		return "success";
	}
	/**
	 * ����״̬�ĸı�
	 * */
	public String con_gg()
	{
		Control c = new Control();
		
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		System.out.println(name+"   "+value);
		if(value.equals("����"))
			value="�ر�";
		else
			value = "����";
		c.setName(name);
		c.setValue(value);
		HibernateUtil.update(c);
		return "success";
	}
	/**
	 * ������������
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
	 * ��ʦ������ѯ
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
	 * ��ʦ��Ϣ��ѯ
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
		list = HibernateUtil.query("bean.Teacher", "", "",page,10);//��ѯ�����ݿ���Teacher������Ϣ
		session.setAttribute("teacher", list);//�浽session������
		return "success";
	}
	/**
	 * ��ʦ��Ϣ����
	 * */
	public String teacher_update()
	{
		//System.out.println(tea.getId());
		HibernateUtil.update(tea);
		return "success";
	}
	/**
	 * ��ӵ�����ʦ��Ϣ
	 * */
	public String tea_add()
	{
		/*String id = tea.getId();
		System.out.println("id: "+id);
		Teacher tt =  (Teacher)HibernateUtil.get(Teacher.class, id);
		if()
	    {
				request.setAttribute("fail", "��ְ���Ų��ԣ�");
				return "fail";}
				else
		{
				
		}*/
		HibernateUtil.add(tea);	
		return "success";
	}
	/**
	 * ��ʦ��Ϣ��������
	 * */
	public String tea_in()
	{
		String address = request.getParameter("name");
		load.tea(address);
		return "success";
	}
	/**
	 * ��֤����Ա��½����
	 * */
	public String yz()
	{
		String id = request.getParameter("name");//��ȡ�˺ţ�������
		String pwd = request.getParameter("pwd");//��ȡ����
		String code = request.getParameter("code");//��ȡ��֤��
		String code2 = (String)session.getAttribute("code");//��ȡ���ɵ���֤�룬
		if(code2.equals(code))//������
			{
				m = (Manager) HibernateUtil.get(Manager.class, id);//�����ݿ����Ҷ�Ӧ������
				if(m == null)
				{
					session.setAttribute("fail", "�û��������ڣ�");
					return "fail";
				}
				else
				{
					if(m.getId().equals(id)&&m.getPwd().equals(pwd))//���벻��
					{
						//System.out.println("��ɣ�");
						session.setAttribute("manager", m);
						session.setAttribute("fail", "");
						//session.setAttribute("manager", m);
						return "success";
					}
					else
					{
						session.setAttribute("fail", "�������");
						return "fail";
					}
				}
			}
		else
		{
			session.setAttribute("fail", "��֤����󣡣�");
			return "fail";
		}
		
		
	}

}
