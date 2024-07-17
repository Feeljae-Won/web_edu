package com.webjjang.message.service;

import java.util.List;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.util.page.PageObject;

public class MessageListService implements Service {
	private MessageDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (MessageDAO) dao;
	}

	@Override
	public List<MessageVO> service(Object obj) throws Exception {
		
		PageObject pageObject = (PageObject) obj;
		
		// 전체 데이터의 개수
		pageObject.setTotalRow(dao.getTotalRow(pageObject));
		
		// DB 처리는 DAO에서 처리 - MessageDAO.list()
		// MessageController - (Execute) - [MessageListService] - MessageDAO.list()
		return dao.list(pageObject);
	}

}
