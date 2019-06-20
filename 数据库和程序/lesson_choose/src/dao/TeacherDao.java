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
	 * �鿴ѡ���ѧ��
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
			request.setAttribute("msg", "��û��ѡ�����Ŀ");
		return "success";
	}
	/**
	 * ����ɾ��
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
	 * �������
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
	 * �������
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
		HibernateUtil.add(question);//��ӵ��������ݿ�
		t.setNum(t.getNum()-1);//��ʦÿ��һ���⣬ʹ��ʦ��num����һ��
		HibernateUtil.update(t);//������ʦ�����ݿ�
		return "success";
	}
	/**
	 * ��ʦ�����ѯ
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
	 * ��������
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
			request.setAttribute("mess", "�������");
			return "fail";
		}
	}
	
	/**
	 * ���½�ʦ��Ϣ
	 * */
	public String tea_update()
	{
		/**
		 * �õ����е��ĸ�����
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
	 * ��ʦ��֤��¼
	 * */
	public String yz()
	{
		Teacher t;
		String pwd = request.getParameter("pwd");
		Control con = (Control)HibernateUtil.get(Control.class, "������ʦ");
		if(con.getValue().equals("�ر�"))
		{
			session.setAttribute("fail", "ϵͳ����ά��");
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
					session.setAttribute("fail", "�û���������");
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
					session.setAttribute("fail", "�������");
					return "fail";
				}
			}
			else
			{
				session.setAttribute("fail", "��֤�����");
				return "fail";
			}
		}
		
	}

}
