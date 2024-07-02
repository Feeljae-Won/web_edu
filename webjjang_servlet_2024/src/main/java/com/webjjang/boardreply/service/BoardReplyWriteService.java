package com.webjjang.boardreply.service;

import com.webjjang.boardreply.dao.BoardReplyDAO;
import com.webjjang.boardreply.vo.BoardReplyVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class BoardReplyWriteService implements Service {

	private BoardReplyDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (BoardReplyDAO) dao;
	}
	
	@Override
	public Integer service(Object obj) throws Exception {
		
		// DB board에서 조회수 1증가 하고 글보기 데이터 가져와서 리턴
		
		
		return dao.write((BoardReplyVO)obj);

	}
}
