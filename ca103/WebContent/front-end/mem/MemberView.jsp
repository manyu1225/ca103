<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem.controller.*"%>
<%@ page import="java.util.*"%>

<%--
	MemService memSvc = new MemService();
	MemJDBCDAO memJDBCDAO = new MemJDBCDAO();
	List<MemVO> list = new ArrayList<MemVO>();
	MemVO memVO = memJDBCDAO.findByPrimarKey("678mkhyi");
--%>

<%
	MemVO memVO = (MemVO) request.getAttribute("mem_id");
	Map okMsg = new LinkedHashMap();
	okMsg = (Map) request.getAttribute("okMsg");
	MemVO memVO2 = (MemVO) session.getAttribute("memVO");
	pageContext.setAttribute("memVO2", memVO2);
	pageContext.setAttribute("mem_id", memVO);
	pageContext.setAttribute("okMsg", okMsg);
	
%>
<!doctype html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

<title>自轉車-會員專區</title>

<style type="text/css">
/* div { */
/* 	border-style: solid; */
/* } */
input[type="text"]:disabled {
	background: #FFFFFF;
}
</style>

</head>
<body style="background-color: #CCEEFF;">
<%@ include file="/sources/file/Home/NavBar.file" %>

	<div class="container">
		<div class="row">
		<div class="col-md-3 mt-5" style="padding-top: 90px;">
				<div class="list-group" style="border-radius: 50%">
					<div></div>
					<div style="background-color:#F7F7F7;border-width: 1px;border-color:#F5F5F5;border-radius:10px;">
						<img class="img-thumbnail"
							src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${mem_id.mem_id}" style="border-radius:50px;" width="100">
							<h5 style="border-style: none; display: inline;background-color:#F7F7F7;">${mem_id.mem_lastname}${mem_id.mem_firstname}</h5>
						
					</div>
				
				</div>
			</div>
			<div class="col-md-9" style="padding-top: 121px;margin-top: 16px;">
				<form action="<%=request.getContextPath()%>/front-end/mem/mes.do" method="post">
					<ul class="list-group list-group-flush">
					
						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 11px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">個人資料</h6>
						
						</li>
					
						<li class="list-group-item ">
							<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 11px;border-right-width: 100px;padding-right: 90px;padding-left: 22px;">ID</h6>
							<div class="col-md-10 row" style="display: inline;"><%=memVO.getMem_id()%></div>
							<input type="hidden" name="other_id" value="<%=memVO.getMem_id()%>"/>
						</li>

						<li class="list-group-item">
							<div style="display: inline; ">
								<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 75px;padding-left: 22px;">姓名</h6>
								
								<input type="text" style="border-style: none; display: inline;"
									id="lnUpdate" disabled name="mem_lastname" class="update"
									value="<%=memVO.getMem_lastname()%>" size="3" > 
									
								<input type="text" style="display: inline; border-style: none;"
									id="fsNameUpdate" disabled name="mem_firstname" class="update"
									value="<%=memVO.getMem_firstname()%>" size="5">
									</div>
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right:75px;padding-left: 22px;">暱稱</h6>
						<input type="text" style=" display: inline; border-style: none; width: 120px;"
							id="nickup" disabled name="mem_nickname" class="update" value="<%=memVO.getMem_nickname()%>"> 
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">電子郵件</h6>
						
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_email" disabled id="email_update" class="update"
							   value="<%=memVO.getMem_email()%>">
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">手機號碼</h6>
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_phone" disabled id="phone_update" class="update"
							   size="20" value="<%=memVO.getMem_phone()%>">
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">市話號碼 </h6>
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_tel" disabled id="tel_update" class="update"
							   value="<%=memVO.getMem_tel()%>">
						</li>


						<li class="list-group-item"> 
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 75px;padding-left: 22px;">生日</h6>
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_birthday" disabled id="birthday" class="update"
							   value="<%=memVO.getMem_birthday()%>">
						</li>
						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">自我介紹</h6>
						<input type="text" style="display: inline; border-style: none;"
							   name="mem_aboutme" disabled id="aboutme_update" class="update"
							   value="<%=memVO.getMem_aboutme()%>" size="60"> 
						</li>
					</ul>
					<input type="hidden" name="self_id" value="${memVO2.mem_id}">
					<input type="hidden" name="action" value="sendInvite">
					<input type="submit" value="發送好友邀請" class="btn btn-info"/>
					<span class="font-weight-bold">${okMsg.inviteOk}${okMsg.inviteFail}</span>

				</form>
			</div>
		</div>
	</div>


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script src="https://code.jq`uery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
