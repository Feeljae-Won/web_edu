package com.webjjang.notice.service;

import java.util.List;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.util.page.PageObject;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class NoticeListService implements Service {
	private NoticeDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (NoticeDAO) dao;
	}
	
	@Override
	public List<NoticeVO> service(Object obj) throws Exception {
		
		PageObject pageObject = (PageObject) obj;
		
		// 전체 데이터의 개수
		pageObject.setTotalRow(dao.getTotalRow(pageObject));

		return dao.list(pageObject);
	}

}
