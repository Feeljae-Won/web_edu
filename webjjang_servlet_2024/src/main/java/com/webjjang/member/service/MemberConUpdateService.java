package com.webjjang.member.service;

import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class MemberConUpdateService implements Service {

	@Override
	public MemberVO service(Object obj) throws Exception {
		
		// MemberController = (Execute) - [MemberLoginService] - MemberDAO.login()
		return new MemberDAO().view((String)obj);
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		
	}

}
