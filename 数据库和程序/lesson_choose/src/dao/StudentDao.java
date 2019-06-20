package dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import bean.Control;
import bean.Question;
import bean.Student;
import bean.Teacher;
import util.HibernateUtil;

public class StudentDao {
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	Student stu;
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	/**
	 * ���������һ����������ѽ����̨�Ϳ����ˣ��Ͻ�д���ȵľ�æ�ˣ�����
	 * */
	public String t_look()
	{
		String id = request.getParameter("id");
		String condition = " where tid like "+id;
		List<Question> list = HibernateUtil.query("bean.Question", condition, "");
		request.setAttribute("q_list", list);
		return "success";
	}
	/**
	 * ģ����ѯ
	 * */
	public String keti_mohu()
	{
		String condition = "";
		String tiaojian = request.getParameter("tiaojian");
		//System.out.println("tiaojian:"+tiaojian);
		if("name".equals(tiaojian))
		{
			String str = request.getParameter("con");
			condition = " where name like  '"+str+"'";
			System.out.println("con:"+str);
			List<Teacher> list = HibernateUtil.query("bean.Teacher", condition, "");
			request.setAttribute("t_list", list);
			return "teacher";		
		}
		else
		{
			condition = " where title like '%"+request.getParameter("con")+"%'";
			List<Question> list = HibernateUtil.query("bean.Question", condition, "");
			request.setAttribute("q_list", list);
			return "keti";
		}
	}
	/**
	 * ѧ��ȡ��ѡ��
	 * */
	public String keti_cancel()
	{
		Student s = (Student)session.getAttribute("student");
		int th = s.getTh();
		if(th == 0)
		{
			request.setAttribute("error", "ɵ���ӣ��㲢û��ѡ��");
		}
		else
		{
			Question q = (Question)HibernateUtil.get(Question.class, th);
			q.setZt(1);
			HibernateUtil.update(q);
			s.setTh(0);
			HibernateUtil.update(s);
		}
		return "success";
	}
	/**
	 * ѡ�����
	 * */
	public String keti_ack()
	{
		Student s = (Student)session.getAttribute("student");
		int th = s.getTh();
		if(th != 0)
		{
			request.setAttribute("error", "���Ѿ�ѡ���ˣ�����˼");
			return "fail";
		}
		else
		{
			int id = Integer.parseInt(request.getParameter("q_id"));
			Question q = (Question)HibernateUtil.get(Question.class, id);
			q.setZt(0);
			HibernateUtil.update(q);
			s.setTh(id);
			HibernateUtil.update(s);
			return "success";
		}
		
	}
	/**
	 * ѡȡ����
	 * */
	public String keti_select()
	{
		
		int page = Integer.parseInt(request.getParameter("page"));
		int all = HibernateUtil.recordCount("bean.Question", "");
		int allpage = all/10;
		if(all%10!=0)
			allpage++;
		session.setAttribute("que_endpage", allpage);
		 if(all == 0)
				page = 0;
		session.setAttribute("que_page", page);
		List<Question> list = HibernateUtil.query("bean.Question", "", "",page,10);
		System.out.println(list.size());
		request.setAttribute("ques", list);
		return "success";
	}
	/**
	 * ��������
	 * */
	public String stu_pwd()
	{
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		Student s = (Student)session.getAttribute("student");
		if(s.getPwd().equals(oldpwd))
		{
			s.setPwd(newpwd);
			HibernateUtil.update(s);
			return "success";
		}
		else
		{
			request.setAttribute("mess", "�������");
			return "fail";
		}
	}
	/**
	 * ���¸�������
	 * */
	public String stu_update()
	{
		HibernateUtil.update(stu);
		session.setAttribute("student",stu );
		return "success";		
	}
	/**
	 * ��¼��֤
	 * */
	public String yz()
	{
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String code = request.getParameter("code");
		Student s = (Student) HibernateUtil.get(Student.class, name);
		String s_code = (String)session.getAttribute("code");
		Control con = (Control)HibernateUtil.get(Control.class, "����ѧ��");
		System.out.println(con.getValue());
		if(con.getValue().equals("�ر�"))
		{
			session.setAttribute("fail", "ϵͳ��δ���ţ���");
			return "fail";
		}
		else if(s_code.equals(code)==false)
		{
			session.setAttribute("fail", "��֤�����");
			return "fail";
		}
		else if(s == null)
		{
			session.setAttribute("fail", "�û�������");
			return "fail";
		}
		else if(s.getId().equals(name)&&s.getPwd().equals(pwd))
		{
			session.setAttribute("student", s);
			session.setAttribute("fail", "");
			return "success";
		}
		else
		{
			
			session.setAttribute("fail", "�������");
			return "fail";
		}
	}

}
