<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.route.model.*"%>
<%@ page import="com.routecollection.model.*"%>
<%@ page import="java.sql.*" %>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">

	
	 <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

	    <!--    jQueryUI select-->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript">
		var $jUI = $.noConflict(true);
	</script>
	
    <title>創建揪團</title>
  </head>
  <body>
  
  		<%@ include file="/sources/file/Home/NavBar.file" %>
  		
  
  			<!-- 設定圖片尺寸的Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">設定寬和高</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      		<div class="form-group">
		        		<input class="form-control inline-Block" id="imgWidth" style="width:20%"type="number" min="1" max="500" placeholder="px"/>
		        		<span style="width:10%;">x</span>
		        		<input class="form-control inline-Block" id="imgHeight" style="width:20%" type="number" min="1" max="500" placeholder="px"/>
		        	</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary" id="dimensionConfirm">確認</button>
		      </div>
		    </div>
		  </div>
		</div>
  
  
    <!--                            路線選擇-->
        <div id="lightBox">
            <div id="routeSelect">
                <h2 class="title">我收藏的路線</h2>
                <hr>
                <div id="routeSection" class="routeSection">
                
                		<% 
                			RouteCollectionService rotcolSrc = new RouteCollectionService();
                			List<RouteVO> rotList = new ArrayList<>();
                			rotList = rotcolSrc.findRouteCollectionList(((MemVO)request.getSession().getAttribute("memVO")).getMem_id()," !=2");
                			pageContext.setAttribute("rotList", rotList);
                		%>
                			<% int routeRanking = 0; %>
                		<c:forEach var="rotVO" items="${rotList}" begin="0" end="${rotList.size()}">
							<% routeRanking++; %>
		                			<!-- 路線的格子 -->
						  <div class="container border rounded" id="block" style="background-color: #ecf5ff; margin-top:15px; box-shadow: 7px 7px 2px rgba(0, 0, 0, 0.2); -webkit-transition-duration: 0.4s; /* Safari */
						    transition-duration: 0.4s;" onmouseover="this.style.background='#c5def9'" onmouseout="this.style.background='#ecf5ff'">
						    <div class="row align-items-center section" style="height: 180px;" id="${rotVO.rot_id}" rotName = "${rotVO.rot_name}">
						      <!-- 放圖片 -->
						      <div class="col-5" name="divForImg" style="overflow:hidden;padding:0px;text-align:center;width:470px;height:170px;">
								<img id="${ rotVO.rot_id }" style="padding:5px; vertical-align:middle; width:470px; margin-top:${ rotVO.rot_photo_loc }px" src="<%=request.getContextPath()%>/Route/RouteServlet.do?action=getPhoto&rot_id=${ rotVO.rot_id }">
						      </div>
						      <%-- ${rotVO.rot_photo} --%>
						      <!-- 圖片右邊的說明 -->
						      <div class="col-7" style="padding:5px;height:170px ">
						  
						        <!-- 右邊最上面的說明 -->
						        <div class="container">
						          <div class="row">
						            <!-- 路線名稱 -->
										<div class="col-1" style="padding:0px;">
											<p style="font-size:22px;font-family: Comic Sans MS,arial,helvetica,sans-serif;"><i class="fas fa-bicycle" style="color:#007bff"></i></p>
										</div>
							            <div class="col-8 routename" style="padding:0px;">
											<p style="font-size:22px;font-family: Comic Sans MS,arial,helvetica,sans-serif;margin-bottom:0px">${rotVO.rot_name}</p>
										</div>
						            <!-- 創建的會員暱稱 -->
						            <div class="col-3" style="padding:0px;">
						            	創建者：
						            	<c:forEach var="memVO" items="${memList}">
							            	<c:if test="${rotVO.mem_id == memVO.mem_id}">
							          			${memVO.mem_nickname}
							          		</c:if>
						          		</c:forEach>
						          		<c:if test="${ rotVO.mem_id == null}">
							          			官方建立
							          	</c:if>
						            </div>
						          </div>
						        </div>
						        
						        <!-- 右邊中間的說明 -->
						        <div class="container">
						          <div class="row" style="height:65px; margin-top:5px;">
						            <!-- 路線說明 -->
						            <div class="col-10" style="padding:0px;font-size:14px"><b>路線說明：</b>${rotVO.rot_describe}</div>
						            <div class="col-2 text-right" style="padding:0px;font-size:70px; margin-top:15px; color:#007BFF;opacity:0.5;text-shadow: black 0.1em 0.1em 0.2em" >${rotList.indexOf(rotVO) + 1}</div>
						          </div>
						        </div>
						        
						        <!-- 右邊下面的說明 -->
						        <div class="container">
						          <div class="row" style="margin-top: 5px;">
						            <!-- 平均坡度 -->
						            <div class="col-2" style="padding:0px;">
						            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="路線坡度">
						            		<i class="fas fa-chart-line">
						            			：${rotVO.rot_slope_ave} %
						            		</i>
						            	</span>
						            </div>
						            <!-- 路線長度 -->
						            <div class="col-2" style="padding:0px;">
						            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="路線距離">
						            		<i class="fas fa-map-marker-alt">
						            			：${rotVO.rot_dis} m
						            		</i>
						            	</span>
						            </div>
						            <!-- 路線難度 -->
						            <div class="col-2" style="padding:0px;">
						            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="路線難度">
						            		<i class="fas fa-exclamation-triangle">
						            			：${rotVO.rot_hard}
						            		</i>
						            	</span>
						            </div>
						            <!-- 空格 -->
						            <div class="col-2" style="padding:0px;"></div>
						            <!-- 熱門排名 -->
						            <div class="col-3" style="padding:0px;">
						            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="點閱數">
						            		<i class="fab fa-hotjar">
						            			：${rotVO.rot_popu}
						            		</i>
						            	</span>　
						            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="留言數">
						            		<i class="far fa-comment-dots">
						            			<c:if test="${routeMessageCountMap.get(rotVO.rot_id)!=null}">
												：${routeMessageCountMap.get(rotVO.rot_id)}
												</c:if>
												<c:if test="${routeMessageCountMap.get(rotVO.rot_id)==null}">
												：0
												</c:if>
						            		</i>
						            	</span>
						            </div>
						            <div class="col-1" style="padding:0px;">
						            </div>
						            
						            
						          </div>
						        </div>
						      </div>
						    </div>
						  </div>
                		</c:forEach>
                </div>
                <div class="rotSubmit">
                    <a class="btn btn-primary" id="submit">確定</a>
                    <a class="btn btn-primary" id="cancel">取消</a>
                </div>
                
            </div>
        </div>
		
		<form action="<%=request.getContextPath() %>/gp.do" method="post" enctype="multipart/form-data" id="GPcreForm">
		<div class="container-fluid paddingTop" style="margin-bottom:250px;">
			<div class="row">
				<div class="col-md-2 col-xl-2">
					<a class="btn btn-warning" href="<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp" style="margin-bottom:5px;">回瀏覽頁面</a>
					<a class="btn btn-warning" id="magicBtn">按鈕</a>
					
				</div>				
				<div class="col-xl-4 offset-xl-2 col-md-5 offset-md-1">
					<h1 style="text-align:center; margin-bottom:20px;">開始創建您的揪團</h1>
				
				</div>
			</div>
		
			<div class="row">

<!-- 			左邊 -->
				<div class="col-md-4">
					
                            <div class="form-group">
                                <a class="btn btn-success" id="routeBtn">請先選擇路線</a>
                                <input class="border-0 btn" type="hidden" name="ROUTE" id="ROUTE" value="${(gpVOcre==null)?'':gpVOcre.rot_id}">
                                <input class="border-0 btn" type="text" id="routeName" name="routeName" value="${routeName}" readonly>
                                
                                <div class="error" id="error1">請選擇路線!!!</div>
                            </div>
                            
                            <div class="form-group">
                                <label for="GP_TITLE">揪團標題</label>
                                <input type="text" class="form-control" id="GP_TITLE" name="GP_TITLE" maxlength="16" value="${(gpVOcre==null)?'':gpVOcre.gp_title}" placeholder="">
                             	
                                <div class="error" id="error2">欄位請勿空白。長度請小於16</div>
                            </div>
                            
                            <div class="form-group">
                                <label for="GP_DATE" style="width:100%;">揪團日期、時間</label>
                                <input class="form-control inline-Block" type="text" id="GP_DATE" name="GP_DATE" value="${(gpVOcre==null)?'':gpVOcre.gp_date}" style="width:42%;" placeholder="揪團日期">

                                <select class="form-control inline-Block" id="GP_HOUR" name="GP_HOUR"  style="width:22%;" placeholder="">
                                	<c:set var="flag" value="false"></c:set>
                                    <c:forEach var="myData" begin="0" end="24" >
                                        <option value="${myData}" 
                                         <c:if test="${myData==gpVOcre.gp_hour}" var="result">
                                         	<c:set var="flag" value="true"></c:set>
                                         	selected
                                         </c:if>
                                         ${ (myData == 9) and (!flag) ? 'selected':''}>
                                        
                                        ${myData}:00 </option>
                                        			   			  
                                    </c:forEach>
                                </select>
                                <label for="GP_TIME" class="inline-Block" style="width:10%;text-align:center;">持續</label>
                                <input type="text" class="form-control inline-Block" id="GP_TIME" name="GP_TIME" value="${gpVOcre==null?'':gpVOcre.gp_time}" style="width:10%;">
                                <span class="inline-Block" style="width:10%; text-align:center;">小時</span>
                                
                                <div class="error" id="error3">欄位請勿空白，且輸入正確格式</div>
                            </div>
                            
                            <div class="form-group">
                                <label for="SIGN_UP_DD" style="width:100%;">報名截止日期</label>
                                <input class="form-control inline-Block" type="text" id="SIGN_UP_DD" name="SIGN_UP_DD" value="${(gpVOcre==null)?'':gpVOcre.sign_up_DD}" style="width:42%;" placeholder="截止日期">
                                
                                <label for="MIN_NUM">至少</label>
                                <input type="text" class="form-control inline-Block" id="MIN_NUM" name="MIN_NUM" value="${(gpVOcre==null)?'':gpVOcre.min_num}" style="width:10%;">
                                <span style="width:10%;">人，</span>
                                <label for="MAX_NUM">最多</label>
                                <input type="text" class="form-control inline-Block" id="MAX_NUM" name="MAX_NUM" value="${(gpVOcre==null)?'':gpVOcre.max_num}" style="width:10%;" placeholder="">
                                <span style="width:10%;">人</span>
                                
                                <div class="error" id="error4">欄位請勿空白，且輸入正確格式</div>
                            </div>
                            
                            
                            <div class="form-group">
                                <label for="GP_DESC">揪團描述</label>
                                <input type="text" class="form-control" id="GP_DESC" name="GP_DESC" maxlength="66" value="${(gpVOcre==null)?'':gpVOcre.gp_desc}" placeholder="">
                                
                                <div class="error" id="error5">欄位請勿空白。長度請小於66</div>
                            </div>
                           
<!--                             <div class="form-group"> -->
<!--                                 <label for="GP_CONTENT">揪團詳細內容</label> -->
<%--                                 <textarea class="form-control AutoHeight" id="GP_CONTENT" name="GP_CONTENT" style="width:100%">${(gpVOcre==null)?'':gpVOcre.gp_content}</textarea> --%>
<!--                             </div> -->
                            
                            <div class="form-group">
                                <label for="GP_PHOTO">揪團封面照片</label>
                                <input type="file" class="form-control" id="GP_PHOTO" name="GP_PHOTO" placeholder="">
                                <img id="imgPreview" src="" alt="" style="width:200;height:200px;"/>
                            </div>
                            <p>揪團公開設定</p>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="PUB_SET" id="inlineRadio1" value="0" checked>
                                <label class="form-check-label" for="inlineRadio1">公開</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="PUB_SET" id="inlineRadio2" value="1">
                                <label class="form-check-label" for="inlineRadio2">不公開</label>
                            </div>
                            
                            <div class="error" id="error0">${exceptionMessage}</div>
                            <input type="hidden" name="action" value="GP_CREATING">
                            <button class="btn btn-primary creBtn d-flex ml-auto" type="button" id="creSubmit">創建揪團</button>
                	  
                	  
                	
                	
                	
                	
				</div>
				
<!-- 				下面都是右邊 -->
				<div class="col-md-4">
					<div class="form-group">
                        <label for="GP_CONTENT_INPUT">輸入揪團行程</label>
                        <label class="btn btn-info" for="" id="imgInsertReplace"><i class="far fa-file-image"></i> 插入圖片</label>
                        <input type='file' id="imgInsert" style="display: none;"/>
                        <textarea class="form-control" id="GP_CONTENT_INPUT" name="GP_CONTENT_EDIT" style="width:100%">${(gpVOcre==null)?'':gpVOcre.gp_content_edit}</textarea>
                    </div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
                        <label for="GP_CONTENT_VIEW">預覽</label>
                        <div id="GP_CONTENT_VIEW" class="form-control" style="margin-top:13px;word-break:break-all;width:170%;"></div>
                        <input type="hidden" id="GP_CONTENT" name="GP_CONTENT" />
                    </div>
				</div>
				<input type="hidden" id="arrMap" name="GP_CONTENT_PHOTO"/>
			
			</div>
		
		</div>
	</form>	

		
   
      
    <link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_Creating.css">
    
    <script src="<%=request.getContextPath() %>/sources/js/gp/GP_Creating.js"></script>  
      
      
      <script>
      		var arrMap = new Array();

			$(function(){
				<c:forEach begin="0" end="5" varStatus="loop">
					if(${(errorIndex==null)?false:errorIndex.contains(loop.index)}){
						
						$('#error${loop.index}')[0].style.display='block';
					}
				</c:forEach>
				
			});
			
			
			$(function(){
				var arrMap0 = "${gpVOcre.gp_content_photo}".split(',');
    			for(var i=0;i<arrMap0.length/2;i++){
        			arrMap[i] = arrMap0[2*i].concat(',',arrMap0[2*i+1]);
        			
    			}
    			$("#GP_CONTENT_INPUT").keyup();//使預覽載入
    			
			});
			
			//一鍵創建揪團
			$(function(){
				$("#magicBtn").click(function(){
					$('#GP_TITLE').val("【十一月份夜騎】");
					$('#GP_DATE').val("2018-11-29");
					$('#GP_HOUR').val("5");
					$('#GP_TIME').val("12");
					$('#SIGN_UP_DD').val("2018-11-29");
					$('#MIN_NUM').val("0");
					$('#MAX_NUM').val("50");
					$('#GP_DESC').val("【十一月份夜騎】11/29 星期四 汐萬百客幼幼班(河濱路線:南湖橋下~三腳渡)");
					$('#GP_CONTENT_INPUT').val("路線:南湖橋下<-->三腳渡(or承德橋下,視路況而定)\n日期:每周二, 四 \n時間:晚上20:00 出發時間:20:10(晚到者請加足馬力跟上)\n集合地點:過南湖橋下,左邊(原自行車道入口處,無燈光有點危險)\n騎乘時間:約1小時30分(不含休息&LDS)\n距離:約30km\n裝備:1.前後車燈 2.水 3.安全帽 4.簡易補胎工具 5.健保卡+少量的錢(補給用)\n最近天氣開始漸漸變冷,請各位參加夜騎的同學注意保暖,最好帶件防風衣\n遇雨取消\n\n原則上分為兩團,1.勇腳團(時速25~30) 2.肉腳團(時速20~25)\n請各位依個人腳力選擇,到達定點休息後,在一起回程\n途中如有不適須要休息者,請跟我說,我會留下陪您一起走(避免有人落單)\n\n以上是小弟目前的規劃\n如果有任何意見(如時間,路線,),歡迎提出\n謝謝0o0\n\n集合點地圖");
					$('#GP_CONTENT_INPUT').keyup();
				});
			});
    </script>
    
    
  </body>
</html>