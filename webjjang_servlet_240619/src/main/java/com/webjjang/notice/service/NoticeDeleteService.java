package com.webjjang.notice.service;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class NoticeDeleteService implements Service {

private NoticeDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (NoticeDAO) dao;
	}
	
	@Override
	public Integer service(Object obj) throws Exception {
		
		// DB notice에서 조회수 1증가 하고 글보기 데이터 가져와서 리턴
		
		
		return dao.delete((NoticeVO)obj);

	}
}
