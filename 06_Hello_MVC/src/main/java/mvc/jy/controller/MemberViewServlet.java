package mvc.jy.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.jy.common.AESEncryptor;
import mvc.jy.model.dto.Member;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet( name = "memberView", urlPatterns="/member/memberview.do")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 방법 1. 
//		Member m = new Service().selectMemberById(request.getParameter("userId"));
//		request.setAttribute("member", m);
		// 방법 2.
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		// 복호화
		String email = loginMember.getEmail();
		String phone = loginMember.getPhone();
		
		try {
			email = AESEncryptor.decryptData(email);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("암호화 안된값");
		}
		
		try {
			phone = AESEncryptor.decryptData(phone);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("암호화 안된값");

		}
		
		loginMember.setEmail(email);
		loginMember.setPhone(phone);
		
		request.getRequestDispatcher(getServletContext().getInitParameter("viewpath")+"member/memberView.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
