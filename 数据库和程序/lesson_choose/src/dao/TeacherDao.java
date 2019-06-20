package dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import bean.Control;
import bean.Question;
import bean.Student;
import bean.Teacher;
import util.HibernateUtil;

public class TeacherDao {
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	/**
	 * 查看选题的学生
	 * */
	public String s_check()
	{
		int tid = Integer.parseInt(request.getParameter("tid"));
		String condition = " where th = "+tid;
		List<Student> list = HibernateUtil.query("bean.Student", condition, "");
		System.out.println(list.size());
		if(list.size()!=0)
		 request.setAttribute("student_check", list.get(0));
		else
			request.setAttribute("msg", "还没人选择该题目");
		return "success";
	}
	/**
	 * 课题删除
	 * */
	public String keti_delete()
	{
		Question q = (Question)HibernateUtil.get(Question.class, Integer.parseInt(request.getParameter("id")));
		String tid = q.getTid();
		Teacher t = (Teacher) HibernateUtil.get(Teacher.class, tid);
		t.setNum(t.getNum()+1);
		HibernateUtil.update(t);
		HibernateUtil.delete(q);
		return "success";
		
	}
	/**
	 * 课题更新
	 * */
	public String keti_update()
	{
		int res = 1;
		int id = Integer.parseInt(request.getParameter("id"));
		Question q = (Question)HibernateUtil.get(Question.class, id);
		String title = request.getParameter("title");
		String con = request.getParameter("con");
		System.out.println("con:"+con);
		String zt = request.getParameter("zt");
		if(zt.equals("no"))
			res = 0;
		q.setCon(con);
		q.setTitle(title);
		q.setZt(res);
		HibernateUtil.update(q);
		return "success";
	}
	/**
	 * 课题添加
	 * */
	public String keti_add()
	{
		Teacher t = (Teacher)session.getAttribute("teacher");
		Question question = new Question();
		question.setCon(request.getParameter("con"));
		question.setTitle(request.getParameter("name"));
		question.setTid(t.getId());
		String zt = request.getParameter("zt");
		if(zt.equals("yes"))
			question.setZt(1);
		else
			question.setZt(0);
		HibernateUtil.add(question);//添加到问题数据库
		t.setNum(t.getNum()-1);//老师每出一个题，使老师的num减少一个
		HibernateUtil.update(t);//更新老师的数据库
		return "success";
	}
	/**
	 * 教师课题查询
	 * */
	public String tea_keti()
	{
		Teacher t = (Teacher)session.getAttribute("teacher");
		String condition = " where tid = "+t.getId();
		List<Question> list = HibernateUtil.query("bean.Question", condition, "");
		//System.out.println(list.size());
		session.setAttribute("question", list);
		
		return "success";
	}
	/**
	 * 更噶密码
	 * */
	public String tea_pwd()
	{
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		Teacher s = (Teacher)session.getAttribute("teacher");
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
	 * 更新教师信息
	 * */
	public String tea_update()
	{
		/**
		 * 得到表单中的四个属性
		 * */
		Teacher t = (Teacher) session.getAttribute("teacher");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");
		String xy = request.getParameter("xy");
		t.setName(name);
		t.setSex(sex);
		t.setPhone(phone);
		t.setXy(xy);
		HibernateUtil.update(t);
		return "success";
	}
	/**
	 * 教师验证登录
	 * */
	public String yz()
	{
		Teacher t;
		String pwd = request.getParameter("pwd");
		Control con = (Control)HibernateUtil.get(Control.class, "对于老师");
		if(con.getValue().equals("关闭"))
		{
			session.setAttribute("fail", "系统正在维护");
			return "fail";
		}
		else
		{
			String encode = request.getParameter("code");
			String code = (String)session.getAttribute("code");
			if(encode.equals(code))
			{
				t = (Teacher)HibernateUtil.get(Teacher.class, request.getParameter("name"));
				if(t == null)
				{
					session.setAttribute("fail", "用户名不存在");
					return "fail";
				}
				else if(t.getPwd().equals(pwd))
				{
					session.setAttribute("fail", "");
					session.setAttribute("teacher", t);
					return "success";
				}
				else
				{
					session.setAttribute("fail", "密码错误！");
					return "fail";
				}
			}
			else
			{
				session.setAttribute("fail", "验证码错误！");
				return "fail";
			}
		}
		
	}

}
