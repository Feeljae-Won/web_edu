package com.webjjang.member.service;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.member.dao.MemberDAO;

public class MemberCheckIdService implements Service {

	private MemberDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (MemberDAO) dao;
	}
	
	@Override
	public String service(Object obj) throws Exception {
		
		// DB 처리는 DAO에서 처리 =
		return dao.checkId((String) obj);
	}

}
