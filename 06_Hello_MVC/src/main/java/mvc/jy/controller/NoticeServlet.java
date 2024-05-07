package mvc.jy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.jy.model.dto.Member;
import mvc.jy.model.dto.Notice;
import mvc.jy.service.Service;

/**
 * Servlet implementation class NoticeServlet
 */
@WebServlet("/notice/notice.do")
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("loginMember");
		if(m != null) {
			int cPage=1;
			try {
				cPage = Integer.parseInt(request.getParameter("cPage"));
			} catch(NumberFormatException e) {
				e.printStackTrace();
			} 
			
			int numPerpage = 5;
			
			try {
				numPerpage = Integer.parseInt(request.getParameter("numPerpage"));
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
			
			List<Notice> notice = new Service().searchNotice(cPage, numPerpage);
			
			request.setAttribute("notice", notice);
			
			int totalData = new Service().selectNoticeCount();
			int totalPage = (int)Math.ceil((double)totalData/numPerpage);
			int pageBarSize = 5;
			int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
			int pageEnd = pageNo + pageBarSize -1;
			
			StringBuffer sb = new StringBuffer();
			sb.append("<ul class='pagination justify-content-center'>");
			if(pageNo==1) {
				sb.append("<li class = 'page-item disabled'>");			
				sb.append("<a class = 'page-link' href = ''>prev</a>");
				sb.append("</li>");
			} else {
				sb.append("<li class = 'page-item'>");			
				sb.append("<a class = 'page-link' href ='"+request.getRequestURI()+"?cPage="+(pageNo-1)
						+"'>prev</a>");
				sb.append("</li>");
			}
			while(!(pageNo>pageEnd||pageNo>totalPage)) {
				if(pageNo == cPage) {
					sb.append("<li class = 'page-item active'>");			
					sb.append("<a class = 'page-link' href = ''>"+pageNo+"</a>");
					sb.append("</li>");
				} else {
					sb.append("<li class = 'page-item'>");			
					sb.append("<a class = 'page-link' href ='"+request.getRequestURI()+"?cPage="+pageNo
							+"'>" + pageNo + "</a>");
					sb.append("</li>");
				}
				pageNo++;
			}
			if(pageNo > totalPage) {
				sb.append("<li class = 'page-item disabled'>");			
				sb.append("<a class = 'page-link' href = ''>next</a>");
				sb.append("</li>");
			} else {
				sb.append("<li class = 'page-item'>");			
				sb.append("<a class = 'page-link' href ='"+request.getRequestURI()+"?cPage="+pageNo
						+"'>next</a>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			
			request.setAttribute("pageBar", sb);
			
			request.getRequestDispatcher(request.getServletContext().getInitParameter("viewpath")+"notice/noticeView.jsp").forward(request, response);
//			request.getRequestDispatcher("/WEB-INF/views/notice/noticeView.jsp").forward(request, response);
		} else {
			String msg ="로그인 후 이용하실 수 있읍니다.";
			String loc = "/";
			
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
