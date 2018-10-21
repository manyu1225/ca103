<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.emp.controller.*"%>
<%
	EmpService empSvc = new EmpService();
	EmpVO empVO = (EmpVO) request.getAttribute("MasterUpdate");
	pageContext.setAttribute("MasterUpdate", empVO);
%>
<!DOCTYPE html>
<html>
<head>
<title>自転車-修改資料</title>
<meta charset="UTF-8" />
</head>
<body>


	<div id="content">
		<div id="content-header">
			
			<h1>修改員工資料</h1>
		</div>
		<div class="container-fluid">
			<hr>
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-info-sign"></i>
							</span>
						</div>
						<div class="widget-content nopadding">
							<form class="form-horizontal" method="post"
								action="emp.do"
								novalidate>

								<div class="control-group">
									<label class="control-label">姓氏</label>
									<div class="controls">
										<input type="text" name="emp_lastname"
											value="${MasterUpdate.emp_lastname}">

									</div>
								</div>
								<div class="control-group">
									<label class="control-label">名子</label>
									<div class="controls">
										<input type="text" name="emp_firstname"
											value="${MasterUpdate.emp_firstname}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">電子郵件</label>
									<div class="controls">
										<input type="text" name="emp_email"
											value="${MasterUpdate.emp_email}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">權限</label>
									<div class="controls">
										<select name="emp_pr">

											<option ${MasterUpdate.emp_pr == '超級管理員'? 'selected':''}>超級管理員</option>
											<option ${MasterUpdate.emp_pr == '路線管理員'? 'selected':''}>路線管理員</option>
											<option ${MasterUpdate.emp_pr == '揪團管理員'? 'selected':''}>揪團管理員</option>
											<option ${MasterUpdate.emp_pr == '拍賣管理員'? 'selected':''}>拍賣管理員</option>
											<option ${MasterUpdate.emp_pr == '文字管理員'? 'selected':''}>文字管理員</option>

										</select>
										<%--                   <input type="text"  name="emp_pr" value="<%=empVO.getEmp_pr()%>"> --%>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">地址</label>
									<div class="controls">
										<input type="text" style="width: 400px" name="emp_ad"
											value="${MasterUpdate.emp_ad}">
									</div>
								</div>

								<div class="form-actions">
									<input type="hidden" name="emp_id"
										value="${MasterUpdate.emp_id}"> <input type="submit"
										value="更新" class="btn btn-success"> <input
										type="hidden" name="action" value="update_one_emp_a">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--End-breadcrumbs-->

	<!--end-main-container-part-->

	<!--Footer-part-->

	<div class="row-fluid">
		<div id="footer" class="span12">
			2018 &copy; 自転車版權所有 <a href="#"></a>
		</div>
	</div>

	<!--end-Footer-part-->

	<script
		src="<%=request.getContextPath()%>/back-end/source/js/excanvas.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.ui.custom.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.flot.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.flot.resize.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.peity.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/fullcalendar.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/matrix.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/matrix.dashboard.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/matrix.chat.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.validate.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/matrix.form_validation.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.wizard.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.uniform.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/select2.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/matrix.popover.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/jquery.dataTables.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/source/js/matrix.tables.js"></script>

	<script type="text/javascript">
		// This function is called from the pop-up menus to transfer to
		// a different page. Ignore if the value returned is a null string:
		function goPage(newURL) {

			// if url is empty, skip the menu dividers and reset the menu selection to default
			if (newURL != "") {

				// if url is "-", it is this page -- reset the menu:
				if (newURL == "-") {
					resetMenu();
				}
				// else, send page to designated URL            
				else {
					document.location.href = newURL;
				}
			}
		}

		// resets the menu selection upon entry to this page:
		function resetMenu() {
			document.gomenu.selector.selectedIndex = 2;
		}
	</script>
</body>
</html>
