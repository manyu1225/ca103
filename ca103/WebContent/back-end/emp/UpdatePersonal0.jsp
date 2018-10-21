<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>自転車-個人資料修改</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/source/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/source/css/bootstrap-responsive.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/source/css/fullcalendar.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/source/css/matrix-style.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/source/css/matrix-media.css" />
<link
	href="<%=request.getContextPath()%>/back-end/source/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/source/css/jquery.gritter.css" />
<!--<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>-->
</head>
<body>
	<div id="content">
		<!--breadcrumbs 主頁面編輯區-->
		<div class="row-fluid">
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-align-justify"></i>
						</span>
						<h5>員工資料更新</h5>
					</div>
					<div class="widget-content nopadding">
						<form id="update_by_one" action="<%=request.getContextPath()%>/back-end/emp/emp.do" method="post" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">員工編號:</label>
								<div class="controls">
									<input type="text" class="control" id="id" name="emp_id" disabled
										value="${empVO.emp_id}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">姓氏 :</label>
								<div class="controls">
									<input type="text"  name="emp_lastname" value="${empVO.emp_lastname}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">名子:</label>
								<div class="controls">
									<input type="text" 
									name="emp_firstname" value="${empVO.emp_firstname}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">帳號:</label>
								<div class="controls">
									<input type="text"  name="emp_account" value="${empVO.emp_account}" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">密碼:</label>
								<div class="controls">
									<input type="text"  name="emp_password" value="${empVO.emp_password}" /><br>
									請勿分享密碼給他人
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">電子信箱:</label>
								<div class="controls">
									<input type="text"  name="emp_email" value="${empVO.emp_email}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">地址:</label>
								<div class="controls">
									<input type="text" name="emp_ad" value="${empVO.emp_ad}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">權限:</label>
								<div class="controls">
									<input type="text" class="control" name="emp_pr" value="${empVO.emp_pr}"
										disabled />
								</div>
							</div>
							<div class="form-actions">
								<input type="hidden"  name="action" value="getOne_For_Update">
								<button type="button" class="btn btn-success" id="go">更新</button>
							</div>
						</form>
					</div>
				</div>
				<div class="widget-box"></div>
			</div>
		</div>
		<!--End-breadcrumbs-->
	</div>

	<div class="row-fluid">
		<div id="footer" class="span12">
			2018 &copy; 自転車版權所有 <a href="#"></a>
		</div>
	</div>

	<!--end-Footer-part-->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js " crossorigin="anonymous"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
	<script>
	$(function(){
		$("#go").click(function(){
			console.log("=============================");
			
			$(".control").prop("disabled",false);
			$('#update_by_one').submit();
		});
	});
		
	
<!-- 	</script> -->

<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/excanvas.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.ui.custom.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/bootstrap.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.flot.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.flot.resize.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.peity.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/fullcalendar.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/matrix.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/matrix.dashboard.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/matrix.chat.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.validate.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/matrix.form_validation.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.wizard.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.uniform.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/select2.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/matrix.popover.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/jquery.dataTables.min.js"></script> --%>
<!-- 	<script -->
<%-- 		src="<%=request.getContextPath()%>/back-end/source/js/matrix.tables.js"></script> --%>

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
