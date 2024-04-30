package mvc.jy.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.jy.admin.model.service.AdminService;
import mvc.jy.model.dto.Member;

/**
 * Servlet implementation class AdminMemberListServlet
 */
@WebServlet("/admin/memberlist.do")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cPage =0; 
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));			
		} catch(NumberFormatException e) {
			cPage=1;
		}
		int numPerpage = 5;
		
		
		// pageBar 만들기
		StringBuffer pageBar = new StringBuffer();
		int totalData = new AdminService().selectMemberAllCount();
		int totalPage = (int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize =5;
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize + 1;
		int pageEnd = pageNo + pageBarSize -1;
		
		// pageBar html 
		if(pageNo==1) {
			pageBar.append("<span>[이전]</span>");
		} else {
			pageBar.append("<a href='"+request.getRequestURI()+"?cPage="+(pageNo-1)+"'>이전</a>");
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo == cPage) {
				pageBar.append("<span>"+pageNo+"</span>");
			} else {
				pageBar.append("<a href='"+request.getRequestURI()+"?cPage="+(pageNo)+"'>" + pageNo +"</a>");
			}
			pageNo ++;
		}
		if(pageNo > totalPage) {
			pageBar.append("<span>[다음]</span>");
		} else {
			pageBar.append("<a href='"+request.getRequestURI()+"?cPage="+(pageNo-1)+"'>[다음]</a>");
		}
		
		request.setAttribute("pageBar", pageBar);
		
		List<Member> members = new AdminService().selectMemberAll(cPage,numPerpage);
		
		request.setAttribute("members", members);
		
		request.getRequestDispatcher(request.getServletContext().getInitParameter("viewpath")+"/member/memberList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
