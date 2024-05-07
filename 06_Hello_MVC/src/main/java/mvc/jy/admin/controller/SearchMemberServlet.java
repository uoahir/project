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
 * Servlet implementation class SearchMemberServlet
 */
@WebServlet("/admin/searchMember")
public class SearchMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		int numPerpage = 5;
		
		String type = request.getParameter("searchType");
		String keyword = request.getParameter("searchKeyword");
		
		List<Member> searchMembers =  new AdminService().searchMember(type,keyword,cPage, numPerpage);
	
		request.setAttribute("members", searchMembers);
		
		int totalData = new AdminService().searchMemberCount(type,keyword);
		int totalPage = (int)Math.ceil(((double)totalData/numPerpage));
		int pageBarSize = 5;
		int pageNo = (cPage-1)/pageBarSize*pageBarSize+1;
		int pageEnd = pageNo + pageBarSize - 1;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<ul class='pagination justify-content-center'>");
		if(pageNo==1) {
			sb.append("<li class = 'page-item disabled'>");			
			sb.append("<a class = 'page-link' href = ''>prev</a>");
			sb.append("</li>");
		} else {
			sb.append("<li class = 'page-item'>");			
			sb.append("<a class = 'page-link' href ='"+request.getRequestURI()+"?cPage="+(pageNo-1)
					+"&" + "searchType=" + type
					+ "&" + "searchKeyword=" + keyword
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
						+"&" + "searchType=" + type
						+ "&" + "searchKeyword=" + keyword
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
					+"&" + "searchType=" + type
					+ "&" + "searchKeyword=" + keyword
					+"'>next</a>");
			sb.append("</li>");
		}
		sb.append("</ul>");
		
		request.setAttribute("pageBar", sb);
		
		request.getRequestDispatcher("/WEB-INF/views/member/memberList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
