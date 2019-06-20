package util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ThreadLocal<Session> session = new ThreadLocal<Session>();

	private HibernateUtil() {
	}

	static {
		Configuration cfg = new Configuration();
		cfg.configure();
		sessionFactory = cfg.buildSessionFactory();
	}

	public static Session getThreadLocalSession() {
		Session s = (Session) session.get();
		if (s == null) {
			s = getSession();
			session.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = (Session) session.get();
		if (s != null) {
			s.close();
			session.set(null);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() {
		return sessionFactory.openSession();
	}

	public static void add(Object entity) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getThreadLocalSession();
			tx = s.beginTransaction();
			s.save(entity);
			tx.commit();
		} finally {
			closeSession();
		}
	}

	public static void update(Object entity) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getThreadLocalSession();
			tx = s.beginTransaction();
			s.update(entity);
			tx.commit();
		} finally {
			closeSession();
		}
	}

	public static void delete(Object entity) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getThreadLocalSession();
			tx = s.beginTransaction();
			s.delete(entity);
			tx.commit();
		} finally {
			closeSession();
		}
	}
	public static void delete(Class clazz,int id) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getThreadLocalSession();
			tx = s.beginTransaction();
			s.delete(s.load(clazz,id));
			tx.commit();
		} finally {
			closeSession();
		}
	}

	public static Object get(Class clazz, Serializable id) {
		Session s = null;
		try {
			s = HibernateUtil.getThreadLocalSession();
			Object obj = s.get(clazz, id);
			return obj;
		} finally {
			closeSession();
		}
	}
	public static List query(String clazz,String condition,String order,int  pageno,int pagesize) {
		
		
		 Session s = null;
			try {
				 s =  HibernateUtil.getThreadLocalSession();;
				 int startrecno=(pageno-1)*pagesize;		
				 String hql="from "+clazz+" "+condition+" "+order;
				 Query query=s.createQuery(hql);
				 query.setFirstResult(startrecno);
				 query.setMaxResults(pagesize);
				 return query.list();
			} finally {
				closeSession();
			}	
		}
	public static List query(String clazz,String condition,String order) {		

		 Session s = null;
			try {
				 s =  HibernateUtil.getThreadLocalSession();;
			 		
				 String hql="from "+clazz+" "+condition+" "+order;
				 Query query=s.createQuery(hql);
				 return query.list();
				
			} finally {
				closeSession();
			}	
		}
	public static int recordCount(String clazz,String condition) {		

		 Session s = null;
			try {
				 s =  HibernateUtil.getThreadLocalSession();;
			 		
				 String hql=" select count(*) from "+clazz+" "+condition;
				 Query query=s.createQuery(hql);
				 return ((Long)query.uniqueResult()).intValue();
				
			} finally {
				closeSession();
			}	
		}
}
