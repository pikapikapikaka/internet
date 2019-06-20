package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import bean.Picture;
import util.HibernateUtil;

/**
 * Servlet implementation class upload
 */
@WebServlet("/upload")
public class upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * 不能使用，和struts冲突
		 * 
		 * */
		Picture p = new Picture();
		@SuppressWarnings("deprecation")
		String path = request.getRealPath("picture");//都是书上代码，看书，我也不懂
		File save = new File(path);
		FileRenamePolicy policy = (FileRenamePolicy)new DefaultFileRenamePolicy();
		MultipartRequest multi;
		int size = 5*1024*1024;
		String lujing = save.getAbsolutePath();
		String na = save.getPath();
		System.out.println("absolote:"+lujing);
		multi = new MultipartRequest(request, path,size,"utf-8",policy);
		Enumeration<String> name = multi.getFileNames();
		String now = name.nextElement();
		//System.out.println("name:"+name.toString());
		String pic_name = multi.getFilesystemName(now);
		p.setName(pic_name);
		p.setAddress("pic"+"\\"+pic_name);
		//System.out.println("pic_name:"+pic_name);
		//dao.add(p);
		HibernateUtil.add(p);
		request.getRequestDispatcher("/lesson_choose/pic.action").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
