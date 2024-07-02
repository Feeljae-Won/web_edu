package com.webjjang.image.service;

import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class ImageViewService implements Service {

	private ImageDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (ImageDAO) dao;
	}
	
	@Override
	public ImageVO service(Object obj) throws Exception {
		
		// 넘어오는 데이터의 구조 obj - Long[] : obj[0] - no, obj[1] - inc
		Long[] objs = (Long[]) obj; // 데이터를 넣은 때 Long[]로 저장했어야만 한다.
		Long no = objs[0];
		Long inc = objs[1];
		
		// 1. 조회수 1증가 : inc == 1
		if(inc == 1) dao.increase(no);
		
		return dao.view(no);
	}

}
