package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhonebookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	// 필드
	private static final long serialVersionUID = 1L;

	// 생성자 -기본생성자 사용

	// 메소드 -gs

	// 메소드 -일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ****
		System.out.println("PhonebookController.goGet()");

		String action = request.getParameter("action");
		System.out.println(action);

		if ("wform".equals(action)) {
			System.out.println("wform:등록폼");

			// jsp 한테 html그리기 응답해라 ==>포워드
			WebUtil.forward(request, response, "/WEB-INF/writeForm.jsp");
			

		} else if ("insert".equals(action)) {
			System.out.println("insert:등록");

			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			// vo로 묶기
			PersonVo personVo = new PersonVo(name, hp, company);
			System.out.println(personVo.toString());

			
			// db관련 업무
			PhonebookDao phoneDao = new PhonebookDao();
			
			// db에 저장
			phoneDao.personInsert(personVo);
			
			/* 리다이렉트
			http://localhost:8080/phonebook4/pbc?action=list  엔터 효과를 낸다
			*/
			WebUtil.redirect(request, response, "/phonebook4/pbc?action=list");
			
			/*
			// db에서 전체 데이터 가져오기
			List<PersonVo> personList = phoneDao.personSelect();
			
			// request에 담기
			request.setAttribute("personList", personList);
			
			// 포워드
			RequestDispatcher rd= request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			*/
			
		}else if("delete".equals(action)) {
			System.out.println("delete:삭제");
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);
			
			// db사용
			PhonebookDao phoneDao = new PhonebookDao();
			
			// 삭제
			phoneDao.personDelete(no);
			
			// 리다이렉트
			/*
			response.sendRedirect("/phonebook4/pbc?action=list");
			*/
			//WebUtil webUtil = new WebUtil();
			WebUtil.redirect(request, response, "/phonebook4/pbc?action=list");
			
			
			
		}else if("mform".equals(action)) {
			System.out.println("modifyForm:수정폼");
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);
			
			// db사용
			PhonebookDao phoneDao = new PhonebookDao();
			
			// 선택한 데이터 가져오기
			PersonVo personVo = phoneDao.personSelectOne(no);
			
			// request attrbute에 추가
			request.setAttribute("personVo", personVo);
			
			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/modifyForm.jsp");
			
		}else if("modify".equals(action)) {
			System.out.println("modify:수정");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			// vo로 묶기
			PersonVo personVo = new PersonVo(no, name, hp, company);
			
			// db사용
			PhonebookDao phoneDao = new PhonebookDao();
			
			// 데이터 수정
			phoneDao.personUpdate(personVo);
			
			// 리다이렉트
			WebUtil.redirect(request, response, "/phonebook4/pbc?action=list");
		
		}else {
			
			System.out.println("list:리스트");
			
			// db사용
			PhonebookDao phoneDao = new PhonebookDao();
			
			// 리스트가져오기
			List<PersonVo> personList = phoneDao.personSelect();
			
			// 데이터담기  포워드
			request.setAttribute("personList", personList);
			
			/*
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			*/
			//WebUtil webUtil = new WebUtil();
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
