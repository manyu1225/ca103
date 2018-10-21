package com.secondShop.product.browserRec;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;
import com.mem.model.MemVO;

/**
 * Servlet implementation class deleteRedis
 */
@WebServlet(urlPatterns={"/deleteRedis"},loadOnStartup = 1)
public class deleteRedis extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
		List<MemVO>  memVOlist= new MemService().getAll();
		BrowserRec browserRec = new BrowserRec(); 
		for(MemVO memVO :memVOlist) {
			browserRec.delBrowserRec(memVO.getMem_id());
		}
		System.out.println("deleteRedis-inir():刪除會員瀏覽記錄完成！！");
	}

	private static final long serialVersionUID = 1L;

    public deleteRedis() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
