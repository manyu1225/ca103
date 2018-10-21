package com.message.model;

import java.util.List;
import com.mem.model.*;


public interface MesDAO_interface {
	public void insert(MesVO mesVO);
	public void update(MesVO mesVO);
	public void delete(String chat_no);
	public MesVO findByPrimarKey(String chat_no);
	public List<MesVO> getAll(String receive_id);
	public MesVO findNickName(String chat_no);
}
