package com.webjjang.board.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class BoardDeleteService implements Service {

private BoardDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (BoardDAO) dao;
	}
	
	@Override
	public Integer service(Object obj) throws Exception {
		
		// DB board에서 조회수 1증가 하고 글보기 데이터 가져와서 리턴
		
		
		return dao.delete((BoardVO)obj);

	}
}
