package com.webjjang.member.service;

import com.webjjang.member.dao.MemberDAO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class MemberConUpdateService implements Service {

	private MemberDAO dao;
	
	@Override
	public void setDAO(DAO dao) {
		this.dao = (MemberDAO) dao;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Integer service(Object obj) throws Exception {
		
		// MemberController = (Execute) - [MemberLoginService] - MemberDAO.login()
		return dao.conUpdate((String)obj);
	}

}
