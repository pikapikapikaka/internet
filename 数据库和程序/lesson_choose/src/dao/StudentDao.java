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
	 * 呼，，最后一个，，真累呀，后台就考试了，赶紧写，等的就忙了！！！
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
	 * 模糊查询
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
	 * 学生取消选题
	 * */
	public String keti_cancel()
	{
		Student s = (Student)session.getAttribute("student");
		int th = s.getTh();
		if(th == 0)
		{
			request.setAttribute("error", "傻狍子，你并没有选题");
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
	 * 选择课题
	 * */
	public String keti_ack()
	{
		Student s = (Student)session.getAttribute("student");
		int th = s.getTh();
		if(th != 0)
		{
			request.setAttribute("error", "你已经选题了！请三思");
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
	 * 选取课题
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
	 * 更新密码
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
			request.setAttribute("mess", "密码错误！");
			return "fail";
		}
	}
	/**
	 * 更新个人资料
	 * */
	public String stu_update()
	{
		HibernateUtil.update(stu);
		session.setAttribute("student",stu );
		return "success";		
	}
	/**
	 * 登录验证
	 * */
	public String yz()
	{
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String code = request.getParameter("code");
		Student s = (Student) HibernateUtil.get(Student.class, name);
		String s_code = (String)session.getAttribute("code");
		Control con = (Control)HibernateUtil.get(Control.class, "对于学生");
		System.out.println(con.getValue());
		if(con.getValue().equals("关闭"))
		{
			session.setAttribute("fail", "系统暂未开放！！");
			return "fail";
		}
		else if(s_code.equals(code)==false)
		{
			session.setAttribute("fail", "验证码错误！");
			return "fail";
		}
		else if(s == null)
		{
			session.setAttribute("fail", "用户名错误！");
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
			
			session.setAttribute("fail", "密码错误！");
			return "fail";
		}
	}

}
