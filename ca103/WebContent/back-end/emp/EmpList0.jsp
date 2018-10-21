<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*" %>
<%@ page import="com.emp.controller.*" %>
<%@ page import="java.util.*"%>
<%
	EmpService empSvc = new EmpService();
	EmpVO empVO = new EmpVO();
	Map errorMsgs = (Map) request.getAttribute("errorMsgs");
	List<EmpVO> list = new ArrayList<EmpVO>();
	list = empSvc.getAll();
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("errorMsgs", errorMsgs);
%>
    
<!DOCTYPE html>
<html>
<head>
<title>自転車-員工列表</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="source/css/bootstrap.min.css" />
<link rel="stylesheet" href="source/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="source/css/fullcalendar.css" />
<link rel="stylesheet" href="source/css/matrix-style.css" />
<link rel="stylesheet" href="source/css/matrix-media.css" />
<link href="source/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="source/css/jquery.gritter.css" />
<!--<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>-->
</head>
<body>

<!--main-container-part-->
<div id="content">
<!--breadcrumbs 主頁面編輯區-->
  <div id="content-header">
     <table id="example" class="hover" style="width:100%">
        <thead>
            <tr>
                <th>姓名</th>
                <th>職位</th>
                <th>帳號</th>
                <th>信箱</th>
                <th>地址</th>
                
            </tr>
        </thead>
        <tbody>
        <c:forEach var="list" items="${list}" begin="0" end="${list.size()}">
            <tr>
                <th class="border border-primary">${list.emp_lastname}${list.emp_firstname}</th>
                <th class="border border-primary">${list.emp_pr}</th>
                <th class="border border-primary">${list.emp_account}</th>
                <th class="border border-primary">${list.emp_email}</th>
                <th class="border border-primary">${list.emp_ad}</th>
                <th><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emp/emp.do" style="margin-bottom: 0px;">
			     <input type="hidden" name="emp_id"      value="${list.emp_id}">
			     <input type="submit" value="修改資料" class="btn btn-success"> 
			     <input type="hidden" name="action"	 value="go_to_update_one_emp"></FORM></th>
			    <th><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emp/emp.do">
			     <input type="hidden" name="emp_id"      value="${list.emp_id}">
			     <input type="submit" value="刪除員工" class="btn btn-danger">
			     <input type="hidden" name="action"	 value="delete_emp"> 
			    </FORM></th>
            </tr>
        </c:forEach>    
         
        </tbody>
    </table>
  </div>
  <div class="control-group">
<!--   <input type="button" value="新增員工" onclick="location.href='/back-end/emp/EmpAdd.jsp'" class="btn btn-success"> -->
  </div>
<!--End-breadcrumbs-->
</div>

<!--end-main-container-part-->

<!--Footer-part-->

<div class="row-fluid">
<span>${errorMsgs.ok}</span>
  <div id="footer" class="span12"> 2018 &copy; 自転車版權所有 <a href="#"></a> </div>
</div>

<!--end-Footer-part-->

<script src="source/js/excanvas.min.js"></script> 
<script src="source/js/jquery.min.js"></script> 
<script src="source/js/jquery.ui.custom.js"></script> 
<script src="source/js/bootstrap.min.js"></script> 
<script src="source/js/jquery.flot.min.js"></script> 
<script src="source/js/jquery.flot.resize.min.js"></script> 
<script src="source/js/jquery.peity.min.js"></script> 
<script src="source/js/fullcalendar.min.js"></script> 
<script src="source/js/matrix.js"></script> 
<script src="source/js/matrix.dashboard.js"></script> 
<script src="source/js/matrix.chat.js"></script> 
<script src="source/js/jquery.validate.js"></script> 
<script src="source/js/matrix.form_validation.js"></script> 
<script src="source/js/jquery.wizard.js"></script> 
<script src="source/js/jquery.uniform.js"></script> 
<script src="source/js/select2.min.js"></script> 
<script src="source/js/matrix.popover.js"></script> 
<script src="source/js/jquery.dataTables.min.js"></script> 
<script src="source/js/matrix.tables.js"></script> 

<script type="text/javascript">
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
  function goPage (newURL) {

      // if url is empty, skip the menu dividers and reset the menu selection to default
      if (newURL != "") {
      
          // if url is "-", it is this page -- reset the menu:
          if (newURL == "-" ) {
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