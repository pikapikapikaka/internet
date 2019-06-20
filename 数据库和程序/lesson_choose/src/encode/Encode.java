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
		response.setContentType("image/jpeg");//������ϵ��jpeg��image��������
		HttpSession session = request.getSession();
		//String a = request.getParameter("a");
		int width = 80;
		int height = 25;
		//����¥������Ҫ�����ͼƬ
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires",0);
		//�����ڴ�ͼ�񲢻����ͼ��������
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();//�൱��һ������
		String str = "0123456789";//�����ַ���
		char rand[] = new char[4];//��ʾ�ĸ��ַ�
		for(int i = 0;i<4;i++)
		{
			rand[i] = str.charAt((int)(Math.random()*10));
		}
		g.setColor(new Color(0xDCDCDC));//���ñ�����ɫ
		g.fillRect(0, 0, width, height);//�滭������
		for(int i = 0;i<100;i++)//�����ŵ�
		{
			g.setColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
			g.drawOval((int)(Math.random()*width), (int)(Math.random()*height), 1, 1);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font(null,Font.ITALIC|Font.BOLD,18));
		g.drawString(""+rand[0], 1, 17);
		g.drawString(""+rand[1], 16, 15);
		g.drawString(""+rand[2], 31, 18);
		g.drawString(""+rand[3], 46, 16);//��������ɵ��ĸ����ʻ�������
		g.dispose();
		//String old = (String)session.getAttribute("code0");
		//System.out.println("old: "+old);
		String ss = new String(rand);
		//System.out.println("new: "+ss);
		session.setAttribute("code",ss );//�������洢��session ,�Ժ󷽱�ȶ�
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
