package mvc.jy.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.jy.model.dto.Member;
import mvc.jy.service.Service;

/**
 * Servlet implementation class EnrollMemberEndServlet
 */
@WebServlet("/member/enrollMemberEnd.do")
public class EnrollMemberEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollMemberEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		Member m = Member.builder().userId(request.getParameter("userId"))
				.password(request.getParameter("password"))
				.userName(request.getParameter("userName"))
				.age(Integer.parseInt(request.getParameter("age")))
				.email(request.getParameter("email"))
				.phone(request.getParameter("phone"))
				.address(request.getParameter("address"))
				.gender(request.getParameter("gender"))
				.hobby(request.getParameterValues("hobby"))
				.build();
			
		System.out.println(m.getUserId()+m.getPassword()+m.getUserName()+m.getAge()+m.getGender()+m.getEmail()+m.getPhone()+m.getAddress()+m.getEnrollDate());
		int result = new Service().signup(m);
		
		String msg = "", loc="";
		if(result>0) {
			msg = "회원가입을 축하드립니다.";
			loc = "/";
		} else {
			msg = "회원가입에 실패햇읍니다. . .  :(";
			loc = "/member/enrollmember.do";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
