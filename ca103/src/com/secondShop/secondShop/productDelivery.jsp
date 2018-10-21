<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.productDelivery.model.*"%>
<%@ page import="com.secondShop.productDeliveryAddrData.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.secondShop.product.model.*"%>

<html lang="en">
<%  
  
	System.out.println("viewProduct.jsp : 我來產品畫面囉");

	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id(); 

 	ProductService productSrv = new ProductService(); 
	List<DeliveryVO> deliveryList= new DeliveryService().allAddress(memId);
	pageContext.setAttribute("deliveryList",deliveryList); 

 	AddressDAO addressDAO = new AddressDAO();
 	List<CityVO> cityList = addressDAO.getCity();
 	pageContext.setAttribute("cityList",cityList); 
%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/productmem.css">
     <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
     <link rel="stylesheet" type="text/css" href=" https://cdn.jsdelivr.net/npm/pretty-checkbox@3.0/dist/pretty-checkbox.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet"  />
    <title>我的收貨地址</title>
    <style type="text/css">
      
      #joinSnackbar {
          visibility: hidden; /* Hidden by default. Visible on click */
          min-width: 250px; /* Set a default minimum width */
          margin-left: -125px; /* Divide value of min-width by 2 */
          background-color: #333; /* Black background color */
          color: #fff; /* White text color */
          text-align: center; /* Centered text */
          border-radius: 2px; /* Rounded borders */
          padding: 16px; /* Padding */
          position: fixed; /* Sit on top of the screen */
          z-index: 1; /* Add a z-index if needed */
          left: 50%; /* Center the snackbar */
          bottom: 30px; /* 30px from the bottom */
      }

      /* Show the snackbar when clicking on a button (class added with JavaScript) */
      #joinSnackbar.show {
          visibility: visible; /* Show the snackbar */
          /* Add animation: Take 0.5 seconds to fade in and out the snackbar. 
         However, delay the fade out process for 2.5 seconds */
         -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
         animation: fadein 0.5s, fadeout 0.5s 2.5s;
      }

    </style>
</head>

<body class="bodycolor">
<%@ include file="/sources/file/Home/NavBar.file"%>
     <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/productMem-sale.jsp">買家中心</a></li>
        <li class="breadcrumb-item active" aria-current="page">我的收貨地址</li>
      </ol>
    </nav>
    <div class="bg-light">
      <div class="container-floid mt-4" id="productlistbody">
        <div class="row mx-auto">
           <%@ include file="/sources/file/secondShop/productSideBar.file" %> 
          <div class="col-12 col-sm-10 mt-4">
            <h2 class="text-center bg-warning  p-4 text-white">
            <img src="<%=request.getContextPath()%>/sources/icon/secondShop/motorcycle-64.png"> 我的收貨地址
            </h2>
            <%@ include file="/sources/file/secondShop/p1.file"%>
            <div class="row align-items-center">
               <div class="col-3">
                  <button  class="btn btn-primary btn-block shodd" data-toggle="modal" data-target="#modalId">
                    <i class="fas fa-plus"></i>新增收件地址
                </button>
              </div>
            </div>
            <table class="table table-hover mt-2" style="background:#FFF">
                <thead>
                    <tr>
                        <th class="text-center">
                          <div class="pretty p-icon p-smooth">
                              <input id="delete" type="checkbox" />
                              <div class="state p-danger-o">
                                  <i class="icon fas fa-times"></i>
                                  <label></label>
                              </div>
                          </div>
                        </th>
                        <th>地址</th>
                        <th>姓名</th>
                        <th>電話</th>
                    </tr>
                </thead>
                <tbody>   
                  <c:forEach var="deliveryVO" items="${deliveryList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >          
                      <tr name="${deliveryVO.deliveryId}">
                          <td class="text-center">
                            <div class="pretty p-icon p-smooth">
                                <input name="delete" type="checkbox" data-deliverId="${deliveryVO.deliveryId}"/>
                                <div class="state p-danger-o">
                                    <i class="icon fas fa-times"></i>
                                    <label></label>
                                </div>
                            </div>
                          </td>
                          <td>${deliveryVO.deliveryAddress}</td>
                          <td>${deliveryVO.deliveryName}</td>
                          <td>${deliveryVO.deliveryPhone}</td>
                      </tr>
                  </c:forEach>    
                </tbody>
            </table>
            <div class="row">
              <div class="col-4">
                 <button class="btn btn-danger justify-content-start" id="deleteBtn">刪除</button>
              </div>
              <div class="col-4">
                 <nav aria-label="Page navigation example">
                  <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getRequestURI()%>?whichPage=1">First</a>&nbsp;</li>
                        <li class="page-item"><a class="page-link" href="<%=request.getRequestURI()%>?whichPage=<%=whichPage-1%>">
                          <i class="fas fa-angle-double-left"></i></a>&nbsp;
                        </li>
                  <%if (pageNumber>1) {%>
                         <%for (int i=1; i<=pageNumber; i++){%>
                             <li class="page-item">
                                <a class="page-link" href="<%=request.getRequestURI()%>?whichPage=<%=i%>">
                                <%=i%></a>&nbsp;</li>
                         <%}%>  
                  <%}%>
                    
                    <li class="page-item"><a class="page-link" href="<%=request.getRequestURI()%>?whichPage=<%=whichPage+1%>">
                      <i class="fas fa-angle-double-right"></i></a>&nbsp;
                    </li>
                    <li class="page-item"><a class="page-link" href="<%=request.getRequestURI()%>?whichPage=<%=pageNumber%>">Last</a>&nbsp;</li>
                  </ul>
                </nav>
              </div>
                <div class="col-4 d-flex justify-content-end">
                    <%if (pageNumber>0){%>
                    <b class="font-smll"><font color=red>第<%=whichPage%>頁 (總共<%=pageNumber%>頁)</font></b>
                    <%}%>
                    <b class="font-smll">共<font color=red><%=rowNumber%></font>筆</b>  
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>

      <div class="modal fade" id="modalId" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg modal-dialog-centered " role="document">
          <div class="modal-content">
            <div class="modal-header" id="modalbg">
              <h5 class="modal-title text-center" id="exampleModalLongTitle">新增收件地址</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>                              
            <div class="modal-body">
              <div class="container">
                <div class="text-center">
                  <div class="text-center card-header">收件人資料</div>
                  <div class="container">
                      <div class="row mt-2">
                          <div class="col-3 align-self-center">
                              <label>收件人姓名</label>
                          </div>
                          <div class="col-3 align-self-center">
                              <input type="text" class="form-control" id="passName" name="deliveryName" required>
                          </div>  
                          <div class="col-3 align-self-center">
                              <label>收件人電話</label>
                          </div>  
                          <div class="col-3 align-self-end">
                              <input type="number" class="form-control" id="passPhone" name="deliveryPhone"  maxlength="14" required>
                          </div>
                      </div>
                      <div class="row">
                        <div class="col-6"><p class="text-danger" id="errorName"></p></div>
                        <div class="col-6"><p class="text-danger" id="errorPhone"></p></div>
                      </div>
                      <div class="row mt-2">
                          <div class="col-3 align-self-center">
                              <label>收件人地址</label>
                          </div>
                          <div class="col-2 align-self-center"">
                              <select class="form-control" name="city" id="selectCity">
                                  <option value="0">請選擇縣市</option>
                                  <c:forEach var="cityVO" items="${cityList}">
                                  <option value="${cityVO.cityId}">${cityVO.cityName}</option>
                                  </c:forEach>
                              </select>
                          </div>   
                          <div class="col-2 align-self-center"">
                              <select class="form-control" name="area" id="selectArea">
                                      <option value="0">請選擇區</option>
                              </select>
                          </div>   
                          <div class="col-5 align-self-center"">
                              <select class="form-control" name="road" id="selectRoad">
                                    <option value="0">請選擇路</option>
                              </select>
                          </div>           
                      </div>
                      <div class="row mt-2 justify-content-end">
                          <div class="col-9 align-self-center">
                              <input type="text" class="form-control" id="buyAddress" name="deliveryAddress" required>
                          </div>
                           <p class="text-danger" id="errorAddr"></p>
                      </div>  
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" id="deliveryAdd">確認新增</button>
            </div>
          </div>
        </div>
      </div>
  <script src="https://code.jquery.com/jquery-3.2.1.min.js "></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

  <script type="text/javascript">

    /* ============================ 購買商品時,收件地址的ajax動態取出 地區/路名 ========================== */
    $('#selectCity').change(function(){                                     //selectCity 值改變的時候
      $('#selectArea').empty().append('<option value="0">請選擇區</option>') //找到區域select,刪除全部,新增第一個空選項
      $('#selectRoad').empty().append('<option value="0">請選擇路</option>')
      $('#buyAddress').val('')

      var cityIdValue = $(this).val()   //取到selectCity的value(值)     //$(this) 用選擇器選擇 近來“這個”方法的物件
      
      if(cityIdValue != 0){  //value不是0的時候(有選到city)   才做肚子的事情
        $.ajax({
           type: "POST",                                        //http 的 method(方法)種類
           url: "<%=request.getContextPath()%>/AddServlet",     //要溝通的位置，相當於<form>裡面的 action
           data: {"cityId":cityIdValue,"addrType":"getArea"}, //要傳出去的資料 直接用Json Object格式送出 
           dataType: "json", // 基本上ajax都是用json送   ex: {"K":"V" <,"2K":"2V">}  // <,"2K":"2V"> 可傳送多於一組的ＫＶ
           success: function (datass){      //成功的話(回拿到http Status = 200) , 拿收到的data(資料)執行肚子的程式碼
             
            console.log(datass.length)     // ====== 方法一 ======= 
            for(i=0;i<datass.length;i++){     //用for迴圈把資料一個一個拿出來,因為data是json陣列(陣列的一種)
              // console.log(datass[i])          // jsonArray[索引] ,可以拿到json物件
              var areaId = datass[i].areaId    //json物件裡面是(key:value),所以可以 "物件.key"拿到value
              var areaName = datass[i].areaName
              var appendAreaOption = '<option value="'+ areaId +'">'+ areaName +'</option>'
              $('#selectArea').append(appendAreaOption)  //把html格式的字串,放在 #selectArea子元素的最後一個
            }
            // $.each(datass,function(i,item){   // i是data裡面第幾個索引(0~n)的資料  ,item就是資料本身
            //   $('#selectArea').append('<option value="'+ item.areaId +'">'+ item.areaName +'</option>')
            // })
          },
           error: function(){
            alert("AJAX-class發生錯誤囉!")
          } //ajax則最後面了 不用加
         })
      }
    })

    $('#selectArea').change(function(){
      $('#selectRoad').empty().append('<option value="0">請選擇路</option>')
      
      var AreaIdValue = $(this).val();
      console.log(AreaIdValue)
      if(AreaIdValue !=0){
        $.ajax({
          type:"POST",
          url:"<%=request.getContextPath()%>/AddServlet", 
          data:{"AreaId":AreaIdValue,"addrType":"getRoad"},
          dataType:"json",
          success:function(road){
            for(i=0;i<road.length;i++){
              var roadId = road[i].roadId
              var roadName = road[i].roadName
              var roadEngName = road[i].roadEngName
              var appendRoadOption = '<option value="'+roadId+'">'+roadName+'</option>'
              $('#selectRoad').append(appendRoadOption)
            }         
          },
          error:function(){
            alert("AJAX-class發生錯誤囉!")
          }
        })
      }
    })

    $('#selectRoad').change(function(){
      if($('#selectRoad').val() !=0){
      var city = $('#selectCity option:selected').text() 
      var area = $('#selectArea option:selected').text() 
      var road = $('#selectRoad option:selected').text()
      console.log(city+area+road)
      $('#buyAddress').val(city+area+road);

      }
    })

    $('#deliveryAdd').click(function(){
      $('#errorName').empty()
      $('#errorPhone').empty()
      $('#errorAddr').empty()

      var deliveryAddress = $('#buyAddress').val();
      var deliveryPhone = $('#passPhone').val();
      var deliveryName =$('#passName').val();
      var memId = "${memVO.mem_id}";
        
      $.ajax({
        type:"POST",
        url:"<%=request.getContextPath()%>/front-end/secondShop/deliveryAdd",
        data:{"memId":memId,"deliveryPhone":deliveryPhone,"deliveryName":deliveryName,"action":"deliveryAdd","deliveryAddress":deliveryAddress},
        dataType:"json",
        success:function(data){
          if(data.yesOrNo == "no"){
            $('#errorName').append(data.Name)
            $('#errorPhone').append(data.Phone)
            $('#errorAddr').append(data.Address)     
          }else{
            $('#selectCity').children().after('checked','checked')
            $('#buyAddress').val('');
            $('#passPhone').val('');
            $('#passName').val('');
            $('#modalId').modal('hide')

            var ss=""
            ss=ss+'<tr name="'+data.deliveryId+'"><td class="text-center"><div class="pretty p-icon p-smooth">'
            ss=ss+'<input name="delete" type="checkbox" data-deliverId="'+data.deliveryId+'"/>'
            ss=ss+'<div class="state p-danger-o"><i class="icon fas fa-times"></i>'
            ss=ss+'<label></label></div></div></td>'
            ss=ss+'<td>'+data.deliveryAddress+'</td>'
            ss=ss+'<td>'+data.deliveryName+'</td>'
            ss=ss+'<td>'+data.deliveryPhone+'</td></tr>'

            Command: toastr["success"]("地址新增成功")
            toastr.options = {
              "closeButton": false,
              "debug": false,
              "newestOnTop": true,
              "progressBar": false,
              "positionClass": "toast-top-right",
              "preventDuplicates": false,
              "onclick": null,
              "showDuration": "300",
              "hideDuration": "1000",
              "timeOut": "2000",
              "extendedTimeOut": "1000",
              "showEasing": "swing",
              "hideEasing": "linear",
              "showMethod": "fadeIn",
              "hideMethod": "fadeOut"
            }
          }
        },
          error:function(){
            alert("地址新增失敗")
          }

      })
    })

    $('#delete').click(function(){
      if($('#delete').is(':checked')){
        $('input[name=delete]').attr('checked','checked')
      }else{
        $('input[name=delete]').attr('checked',false)
      }  
    })

    $('#deleteBtn').click(function(){
      var array =[];
      $('input[name=delete]:checked').each(function(){
        var deliverId =$(this).attr('data-deliverId')
        var deliverJson = {"deliverId":deliverId}
        array.push(deliverJson);
      })
      $.ajax({
        type:"POST",
        url:"<%=request.getContextPath()%>/front-end/secondShop/deliveryAdd",
        data:{"array":array,"action":"deliveryDelet"},
        dataType:"json",
        success:function(data){
          for(var i =0; i<data.length;i++){
             $('tr[name='+data[i].deliveryId+']').remove();
          }
          Command: toastr["info"]("地址刪除成功")

          toastr.options = {
            "closeButton": false,
            "debug": false,
            "newestOnTop": false,
            "progressBar": false,
            "positionClass": "toast-top-right",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
          }
        },
          error:function(){
            alert("刪除失敗")
          }


      })
    })

</script>
</body>

</html>