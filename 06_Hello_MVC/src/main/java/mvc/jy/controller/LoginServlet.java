package mvc.jy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.jy.model.dto.Member;
import mvc.jy.service.Service;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", urlPatterns="/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		// 아이디 저장기능
		String saveId= request.getParameter("saveId"); // null || on
		if(saveId != null) {
			Cookie saveIdCookie = new Cookie("saveId", userId);
			saveIdCookie.setMaxAge(60*60*24*7);
			response.addCookie(saveIdCookie);
		} else {
			Cookie saveIdCookie = new Cookie("saveId",userId);
			saveIdCookie.setMaxAge(0);
			saveId = null;
			response.addCookie(saveIdCookie);
		}
		
		if(userId.equals("")||password.equals("")) {
			request.setAttribute("msg", "아이디나 패스워드를 입력하세요.");
			request.setAttribute("loc", "/");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			rd.forward(request, response);
		} else {
			
			Member m = new Service().login(userId, password);
			if(m!=null) {
				// 로그인한 정보를 HttpSession 에 저장
				HttpSession session = request.getSession(); // 여기서는 폴스든 트루든 상관없음 외냐 jsp 에는 이미 내장객체로 session 을 가지고 잇기 때문
				session.setAttribute("loginMember", m);
				// 화면전환
				response.sendRedirect(request.getContextPath());
			} else {
				// 앰이 널임 
				request.setAttribute("msg", "아이디나 패스워드가 일치하지 않습니다.");
				request.setAttribute("loc", "/");
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			}
			System.out.println(m);
		}
		
		
//		request.setAttribute("member", m);
//		
//		RequestDispatcher rd = request.getRequestDispatcher(request.getContextPath());
//		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
