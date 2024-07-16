package com.webjjang.qna.service;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.qna.vo.QnaVO;

public class QnaViewService implements Service {

	private QnaDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (QnaDAO) dao;
	}
	
	@Override
	public QnaVO service(Object obj) throws Exception {
		
		// QnaController - (Execute) - [QnaViewService] - QnaDAO.view()
		return dao.view((Long) obj);
	}

}
