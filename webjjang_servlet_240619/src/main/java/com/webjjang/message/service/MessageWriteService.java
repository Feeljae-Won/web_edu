package com.webjjang.message.service;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;

public class MessageWriteService implements Service {

	private MessageDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (MessageDAO) dao;
	}
	
	@Override
	public Integer service(Object obj) throws Exception {
		MessageVO vo = (MessageVO) obj;
		Integer result = dao.write(vo);
		
		// DB message에서 새로운메세지 1증가 처리
		dao.increaseNewMsgCnt(vo.getAccepterId());
		
		return result;

	}
}
