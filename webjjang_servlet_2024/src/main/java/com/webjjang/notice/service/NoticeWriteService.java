package com.webjjang.notice.service;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.board.dao.BoardDAO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class NoticeWriteService implements Service {

	private NoticeDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (NoticeDAO) dao;
	}
	
	@Override
	public Integer service(Object obj) throws Exception {
		
		
		return dao.write((NoticeVO)obj);

	}
}
