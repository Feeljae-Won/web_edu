package com.webjjang.member.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;

public class MemberLoginService implements Service {

	private MemberDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (MemberDAO) dao;
	}
	
	@Override
	public LoginVO service(Object obj) throws Exception {
		

		// DB 처리는 DAO에서 처리 =
		return dao.login((LoginVO) obj);
	}

}
