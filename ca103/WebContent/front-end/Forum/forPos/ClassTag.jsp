







<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.forPos.model.*"%>
<%@ page import = "redis.clients.jedis.Jedis" %>


<!-- 取得全部文章編號物件 -->
<%

Forum_post_Service forPosSvc = new Forum_post_Service();

List<Forum_post_VO> list = forPosSvc.getAllForPos();

%>

<!-- 開啟Redis連線 -->

<%

Jedis jedis = new Jedis("localhost", 6379);
jedis.auth("123456");

%>



<%
List<String> tagDisplay = new LinkedList<String>();

Long tagNum = null;
%>







<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tag Column</title>
</head>
<body>



<c:forEach var="forPosVO" items="${list}">
<c:forEach var="forPosVO" items="${list}">

	String tagStr = jedis.smembers("post:" + forPosVO.getForPost_ID() +  ":" + "tag")  
		

tagNum = jedis.scard("tag:" + tag +  ":" + "post");
System.out.println("tagNummmmmmmm=" + tagNum);

%>
</c:forEach>

</c:forEach>



</body>
</html>