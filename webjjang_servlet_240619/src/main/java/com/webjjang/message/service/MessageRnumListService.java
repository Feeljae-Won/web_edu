package com.webjjang.message.service;

import java.util.List;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.util.page.PageObject;

public class MessageRnumListService implements Service {
	private BoardDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (BoardDAO) dao;
	}

	@Override
	public List<BoardVO> service(Object obj) throws Exception {
		
		Long rnum = (Long) obj;
		return dao.rnumList(rnum);
	}

}
