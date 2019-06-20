package encode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Encode
 */
@WebServlet("/Encode")
public class Encode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Encode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("image/jpeg");//从属关系，jpeg是image的子类型
		HttpSession session = request.getSession();
		//String a = request.getParameter("a");
		int width = 80;
		int height = 25;
		//设置楼兰器不要缓存此图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires",0);
		//创建内存图像并获得其图形上下文
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();//相当于一个画笔
		String str = "0123456789";//设置字符串
		char rand[] = new char[4];//显示四个字符
		for(int i = 0;i<4;i++)
		{
			rand[i] = str.charAt((int)(Math.random()*10));
		}
		g.setColor(new Color(0xDCDCDC));//设置背景颜色
		g.fillRect(0, 0, width, height);//绘画出背景
		for(int i = 0;i<100;i++)//画烦扰点
		{
			g.setColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
			g.drawOval((int)(Math.random()*width), (int)(Math.random()*height), 1, 1);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font(null,Font.ITALIC|Font.BOLD,18));
		g.drawString(""+rand[0], 1, 17);
		g.drawString(""+rand[1], 16, 15);
		g.drawString(""+rand[2], 31, 18);
		g.drawString(""+rand[3], 46, 16);//把随机生成的四个单词画到上面
		g.dispose();
		//String old = (String)session.getAttribute("code0");
		//System.out.println("old: "+old);
		String ss = new String(rand);
		//System.out.println("new: "+ss);
		session.setAttribute("code",ss );//将变量存储到session ,以后方便比对
		ServletOutputStream sos = response.getOutputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", baos);
		byte[] bytes = baos.toByteArray();
		response.setContentLength(bytes.length);
		sos.write(bytes);
		baos.close();
		sos.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
