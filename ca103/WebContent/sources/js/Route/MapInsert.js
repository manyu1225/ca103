



      var map;
      var count = 0;
      var directionsDisplay;
      //總距離長度,跨方法使用故宣告實體變數
        
      google.load('visualization', '1', {packages: ['columnchart']});
      //為了shoe高度圖必須,不能省略
        
      function initMap() {
      //初始化地圖
        directionsDisplay = new google.maps.DirectionsRenderer({draggable: true});
        //draggable: true 是為了讓路線和起點終點是可拖曳的狀態
        directionsService = new google.maps.DirectionsService;
        elevator = new google.maps.ElevationService;
        //為了畫出路線圖與高度圖
               
          
          
        map = new google.maps.Map(document.getElementById('map'), {
        //建立地圖本體的主要方法,起始位置與起始地圖大小
          center: {lat: 25.101, lng: 121.452},
          zoom: 15
        });
          
          
          
       var listenerHandle = google.maps.event.addListener(map, 'click', function(event) {
        //事件處理器：在地圖上的點擊事件
            findLatLng(event.latLng);
            //自定方法,目的在找到「在地圖上點擊的座標」
//            console.log('觸發點擊事件');
            //呼叫findLatLng()方法取得起始點座標與終點座標
                      
            if(count == 1){
                 marker = new google.maps.Marker({
                    position: {lat: Slat, lng: Slng},
                    map: map,
                    title: 'Hello World!'
                }); //如果是第一次發生點擊事件,放置一個紅點標記代表路線的起始點位置
            }else {
//                console.log(count);
                calculateAndDisplayRoute(directionsService, directionsDisplay);
                marker.setMap(null);
                //當在地圖上點擊次數>=2的時候,才會有兩個點(起點/終點)可以計算路線
                //呼叫calculateAndDisplayRoute()方法計算路徑並且顯示在地圖上
                //後續路線被建立時會自動產生紅點標記,所以這邊的紅點標記要清除
            }
        });
          

          
        directionsDisplay.addListener('directions_changed', function() {
            computeTotalDistance(directionsDisplay.getDirections());
//            console.log('觸發拖曳事件');
            google.maps.event.clearListeners(map, 'click');
        });
          
        function computeTotalDistance(result) {
            var total = 0;
            var myroute = result.routes[0];
            var newpath = myroute.overview_path;
//            console.log(myroute.legs[0].distance.value);
            distanceAll=myroute.legs[0].distance.value;
            //路線的總距離(後續計算坡度使用,單位公尺)
//            console.log("distanceAll = " + distanceAll);
            document.getElementById("rot_dis").value = distanceAll/1000;
            document.getElementById("rot_disText").innerText = distanceAll/1000 + " Km";
            
            document.getElementById("rot_gps").value = newpath;
//            console.log('newpath = ' + newpath);
            displayPathElevation(newpath, elevator, map);
//            console.log('觸發拖曳事件2');
        }
          
         
        directionsDisplay.setMap(map);
        //把路線資料顯示在地圖上!!
        
        directionsDisplay.setPanel(document.getElementById('directionsPanel'));
        //顯示路線距離,不過這個太詳細了,不是我想要的,後續要再改
        
        //取得使用者現在位置
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                var marker = new google.maps.Marker({
                    position: pos,
                    icon:'marker.png',
                    map: map
                });
                map.setZoom(17);
                map.setCenter(pos);
            });
        } else {
            // Browser doesn't support Geolocation
            alert("Sorry, no position available.");
        }
        
      }
        
        
        
        
        
        function calculateAndDisplayRoute(directionsService, directionsDisplay) {
        //計算路徑並且顯示在地圖上,顯示起點、終點和之間的路徑規劃，有詳細路線距離但是沒有高度圖
            var options = {
                origin: {lat: Slat, lng: Slng},
                destination: {lat: Elat, lng: Elng},
                travelMode: 'DRIVING',
                avoidFerries: true,
                avoidHighways: true,
                avoidTolls: true,
            };//設定起點經緯度 / 終點經緯度 / 旅行模式為：走路
            
            directionsService.route(options, function(response, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                    directionsDisplay.setDirections(response);
                    
                    var allPath;
                    var routes = response.routes[0];
                    var path = routes.overview_path;
                    document.getElementById("rot_gps").value = path;
                    var legs = routes.legs;
                    displayPathElevation(path, elevator, map);
                    //呼叫displayPathElevation()方法把沿途路徑傳送進去求得高度
                    
                    for (i = 0; i < legs.length; i++) {
                    //i代表的是只有一段路(指一個A點到B點)
                        
                        var steps = legs[i].steps;
                        for (j = 0; j < steps.length; j++) {
                        //j代表的是這一段路(一個A點到B點)裡面總共拆成了幾個小路段
                        
                            allPath = steps[j].path;
                        }//把每一個小路段傳送給displayPathElevation()方法做繪圖
                    }
                }else {window.alert('UNKNOWN_ERROR,PLEASE TRY AGAIN.');}
                
               
          
         
                
                
            });
        }
        
        function displayPathElevation(path, elevator, map) {
            
         //使用此數組創建PathElevationRequest對象。沿著該路徑詢問256個樣本。啟動路徑請求。
            elevator.getElevationAlongPath({
                'path': path,
                'samples': 512,
            }, plotElevation);
        }   //繼續呼叫plotElevation()方法把路線資料傳入畫高度圖
        
        
        function plotElevation(elevations, status) {
            if (status == google.maps.DirectionsStatus.OK) {
            var charta = new google.visualization.AreaChart(document.getElementById('chart_div'));
            //chart_div DIV中創建一個新圖表。面積高度圖長出來的位置
            var data = new google.visualization.DataTable();
            //提取填充圖表的數據。
                                  
            data.addColumn('number', '高度');
            //Y軸為下面取出來的資料,資料為number格式,放進來
            var heightUp = 0;
            //上升的海拔高度
            var heightDown = 0;
            //下降的海拔高度
            var countPoint = 0;
            //512個點中有幾個點是上升的
            var distancePoint = distanceAll / (elevations.length);
            console.log("distancePoint = " + distancePoint);
            console.log("(elevations.length) = " + (elevations.length));
            //上升的海拔高度陣列,用以求得最高海拔
            var arrheightest = new Array();
            //起點的海拔高度,用以求得平均坡度
            var startHeight = 0;
            
            for (var i = 0; i < elevations.length; i++) {
                //X軸為i跑回圈的數值
                data.addRow([elevations[i].elevation]);
                if(i != 0){
                    var heightDiff = elevations[i].elevation-elevations[i-1].elevation
                    //兩點經緯度的高度差,下面的if把正差和負差分開加總計算上升總海拔高度和下降總海拔高度
                    if(heightDiff >= 0){
                        heightUp = heightUp + heightDiff;
                        countPoint++;
                        arrheightest.push(elevations[i].elevation);
                    }else {
                        heightDown = heightDown + (heightDiff*(-1));
                    }
                }
            }
            
            if(arrheightest.length > 1){
	            arrheightest.sort(function (a, b) {
	            	  return b - a //順序反轉
//	            console.log("arrheightest = " + arrheightest[0]);
	            });
            }else{
            	arrheightest.push(elevations[0].elevation);
            }
//            console.log("arrheightest = " + arrheightest.length);
//            console.log("arrheightest = " + arrheightest[0]);
            
            }else {window.alert('Directions request failed due to ' + status);}
            
            //	若全程下坡會造成爬升高度計算錯誤,故全程下坡直接設定累計坡度為0
            var slopeUp = heightUp / (distancePoint * countPoint) *100;
            if(countPoint < 1){
            	var slopeUp = 0;
            }
            var slopeUpAve = heightUp / distanceAll *100;
            var slopeAve = (arrheightest[0] - elevations[0].elevation) / distanceAll *100 ;
//            console.log("arrheightest[0] = " + arrheightest[0]);
//            console.log("elevations[0].elevation = " + elevations[0].elevation);
//            console.log("distanceAll = " + distanceAll);
            var heightAve = (heightUp + heightDown) / 2;
            
            
            
//            console.log("爬升高度heightUp = " + heightUp);
            document.getElementById("rot_height_up").value = heightUp.toFixed(2);
            document.getElementById("rot_height_upText").innerText = heightUp.toFixed(2) + " m";
//            console.log("下降高度heightDown = " + heightDown);
            document.getElementById("rot_height_down").value = heightDown.toFixed(2);
            document.getElementById("rot_height_downText").innerText = heightDown.toFixed(2) + " m";
            document.getElementById("rot_heightest").value = arrheightest[0].toFixed(2);
            document.getElementById("rot_heightestText").innerText = arrheightest[0].toFixed(2) + " m";
            
//            console.log("爬升點數countPoint = " + countPoint);
            
//            console.log("爬升坡度slopeUp = " + slopeUp);
            document.getElementById("rot_slope_up").value = slopeUp.toFixed(2);
            document.getElementById("rot_slope_upText").innerText = slopeUp.toFixed(2) + " %";
//            console.log("下降坡度slopeUpAve = " + slopeUpAve);
            document.getElementById("rot_slope_down").value = slopeUpAve.toFixed(2);
            document.getElementById("rot_slope_downText").innerText = slopeUpAve.toFixed(2) + " %";
            document.getElementById("rot_slope_ave").value = slopeAve.toFixed(2);
            document.getElementById("rot_slope_aveText").innerText = slopeAve.toFixed(2) + " %";
            
            //	難度計算
            //	距離參數：0.2 爬升高度參數：0.002 坡度參數：0.5 常數：1
            var rot_hard = (distanceAll/1000)*0.4 + heightUp*0.05 + slopeUp*1.5;
            document.getElementById("rot_hard").value = rot_hard.toFixed(0);
            document.getElementById("rot_hardText").innerText = rot_hard.toFixed(0);
            
            var chartOptions = {
        		title: '',
                titleX: '距離(Km)',
                titleY: '海拔高度(m)',
                legend: 'none',
                curveType: 'function',
                dataOpacity:1.0,
                fontSize:8,
                pointSize: 0,
            };
            
            charta.draw(data,chartOptions);
            //面積圖繪圖
      }
        
        
        
        
        function findLatLng(location) {
            //點擊次數單數-起點 / 雙數-終點
           if(count % 2 == 0){
                Slat = location.lat();
                Slng = location.lng();
                document.getElementById("Slocation").value = location;
           }else{
                Elat = location.lat();
                Elng = location.lng();
                document.getElementById("Elocation").value = location;
            }
            count++;
         }
         
