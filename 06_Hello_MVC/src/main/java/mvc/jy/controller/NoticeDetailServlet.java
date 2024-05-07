package mvc.jy.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.jy.model.dto.Notice;
import mvc.jy.service.Service;

/**
 * Servlet implementation class NoticeDetailServlet
 */
@WebServlet("/notice/noticedetail.do")
public class NoticeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// notice 상세화면 서블릿임 .. 여기에서 이제 jsp 로 넘겨주면 된다 ~ ,, 
		// 해당되는 notice 만 . . 상세화면에 표시되게 해야 한다 .. . 
		// click 시 .. javascript 에서 .. 
		
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		Notice notice = new Service().selectNoticeByNo(noticeNo);
		
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/views/notice/noticeDetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
