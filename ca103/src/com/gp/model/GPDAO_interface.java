package com.gp.model;
import java.sql.Date;
import java.util.List;

import com.mem.model.MemVO;
import com.route.model.RouteVO;

public interface GPDAO_interface {
	public List<GPVO> getAllGP();
	public List<GPVO> searchByKw(String...arrKw);
	public List<GPVO> searchCreGP(MemVO meberVO);
	public List<GPVO> search(int maxNum);
	public List<GPVO> search(Date dateStart,Date dateEnd);
	public GPVO searchGPVO(GPVO gpVO);
	public void add(GPVO gpVO);
	public void update(GPVO gpVO);//maybe後臺更改資訊
	public void updateinfo(GPVO gpVO);//更改使用者資訊
    public void updateStatus(GPVO gpVO);//更改狀態
    
    public List<GPVO> search(RouteVO rotVO); 
    
    public List<GPVO> searchByCoondition(String str);
    
    public void updateContent(GPVO gpVO);
    
    public List<GPVO> forGP_Manager(String str);
}