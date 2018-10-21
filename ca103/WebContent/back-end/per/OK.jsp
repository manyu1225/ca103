<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<title>自転車-員工首頁</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/source/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/source/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/source/css/fullcalendar.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/source/css/matrix-style.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/source/css/matrix-media.css" />
<link href="<%=request.getContextPath()%>/back-end/source/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/source/css/jquery.gritter.css" />
<!--<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>-->
</head>
<body>

<!--Header-part-->
<div id="header">
  <h1><a href="dashboard.html">自轉車</a></h1>
</div>
<!--close-Header-part--> 

<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li  class="dropdown" id="profile-messages" ><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>  <span class="text">員工登入</span><b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a href="#"><i class="icon-user"></i> My Profile</a></li>
        <li class="divider"></li>
        <li><a href="#"><i class="icon-check"></i> My Tasks</a></li>
        <li class="divider"></li>
        <li><a href="login.html"><i class="icon-key"></i>登出</a></li>
      </ul>
    </li>
<!--     <li class="dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">Messages</span> <span class="label label-important">5</span> <b class="caret"></b></a> -->
<!--       <ul class="dropdown-menu">
        <li><a class="sAdd" title="" href="#"><i class="icon-plus"></i> new message</a></li>
        <li class="divider"></li>
        <li><a class="sInbox" title="" href="#"><i class="icon-envelope"></i> inbox</a></li>
        <li class="divider"></li>
        <li><a class="sOutbox" title="" href="#"><i class="icon-arrow-up"></i> outbox</a></li>
        <li class="divider"></li>
        <li><a class="sTrash" title="" href="#"><i class="icon-trash"></i> trash</a></li>
      </ul> -->
  <!--   </li>
    <li class=""><a title="" href="#"><i class="icon icon-cog"></i> <span class="text">Settings</span></a></li> -->
    <li class=""><a title="" href="login.html"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
  </ul>
</div>
<!--close-top-Header-menu-->
<!--start-top-serch-->
</div>
<!--close-top-serch-->
<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
  <ul>
    <li class="active"><a href="index.html"><i class="icon icon-home"></i> <span>首頁</span></a> </li>
    <li class="submenu"> <a href="#"><i class="icon icon-user"></i> <span>員工管理</span>
     <span class="label label-important">5</span></a>
      <ul>
        <li><a href="EmpList.jsp">瀏覽員工列表</a></li>
        <li><a href="#">員工權限管理</a></li>
      </ul>
    </li>
    <li><a href="#"><i class="icon icon-dashboard"></i> <span>路線專區管理</span></a></li>
    <li><a href="#"><i class="icon icon-bullhorn"></i> <span>討論區管理</span></a></li>
    <li><a href="#"><i class="icon icon-trophy"></i> <span>成就條件管理</span></a></li>
    <li><a href="#"><i class="icon icon-legal"></i> <span>二手拍賣管理</span></a></li>
    <li><a href="#"><i class="icon icon-picture"></i> <span>廣告輪播管理</span></a></li>
    <li><a href="#"><i class="icon icon-map-marker"></i> <span>全台活動管理</span></a></li>
     <li class="submenu"> <a href="#"><i class="icon icon-th-list"></i> <span>社群管理</span>
      <span class="label label-important">3</span></a>
      <ul>
        <li><a href="#">Basic Form</a></li>
        <li><a href="#">Form with Validation</a></li>
        <li><a href="#">Form with Wizard</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-calendar"></i> <span>揪團管理</span>
     <span class="label label-important">5</span></a>
      <ul>
        <li><a href="index2.html">Dashboard</a></li>
        <li><a href="gallery.html">Gallery</a></li>
        <li><a href="calendar.html">Calendar</a></li>
        <li><a href="invoice.html">Invoice</a></li>
        <li><a href="chat.html">Chat option</a></li>
      </ul>
    </li>
    <li><a href="#"><i class="icon icon-pencil"></i> <span>站內訊息通知</span></a></li>
  </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">
<!--breadcrumbs 主頁面編輯區-->

  <div id="content-header">
    <div id="breadcrumb"> <a href="index.html" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
   <div>修改完成</div>
  </div>
<!--End-breadcrumbs-->
</div>

<!--end-main-container-part-->

<!--Footer-part-->

<div class="row-fluid">
  <div id="footer" class="span12"> 2018 &copy; 自転車版權所有 <a href="#"></a> </div>
</div>

<!--end-Footer-part-->

<script src="<%=request.getContextPath()%>/back-end/source/js/excanvas.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.ui.custom.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/bootstrap.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.flot.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.flot.resize.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.peity.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/fullcalendar.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/matrix.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/matrix.dashboard.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/matrix.chat.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.validate.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/matrix.form_validation.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.wizard.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.uniform.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/select2.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/matrix.popover.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/jquery.dataTables.min.js"></script> 
<script src="<%=request.getContextPath()%>/back-end/source/js/matrix.tables.js"></script> 

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
