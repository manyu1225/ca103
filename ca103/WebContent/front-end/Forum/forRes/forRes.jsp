<form action="<%=request.getContextPath()%>/forPos/forPos2.do">
					<div class="card shadow-sm round-0 mt-3  bg-info">

						<div class="card-body">
							<div class="row">

								<div class="col-md-2 col-4">
									<img id="user-img"
										src="https://api.fnkr.net/testimg/90x90/00CED1/FFF/?text=img+placeholder">
								</div>

								<div class="col-md-7 col-8 my-auto">

								<c:if test="${not empty cli}">
										<input type="text" class="form-control form-control-md"
											placeholder="çä¸ä½ çè¶³è·¡å§" name="forRes_content">
									</c:if>
									<p><font color="red">${errorMsgs.content}</font></p>

									<input type="hidden" name="action" value="addOneForRes">
									<input type="hidden" name="forPost_ID"
										value="${forPosVO.forPost_ID}">
								</div>

								<div class="d-flex flex-column align-content-center">
									<button class="button btn-lg" name="posRes">
										<i class="fa fa-thumbs-up"></i>
										<!-- <i class="fas fa-thumbs-up"></i>è©å¹å -->
									</button>

									<button class="button btn-lg" name="negRes">
										<i class="fa fa-thumbs-down"></i>
									</button>
									
								</div>


							</div>
							<!-- 							<hr> -->
							<%-- 							<p class="text-muted">åææ¼ ${forResVO.forRes_time}</p> --%>
							<div class="d-flex justify-content-end">
								<input type="submit" class="btn btn-warning mt-3 "
									name="sendResponse" value="çè¨">
							</div>
						</div>
					</div>
				</form>
					
		<form action="<%=request.getContextPath()%>/forPos/forPos2.do">

					<!-- //////////çè¨ç¼å¸å /////////-->
					--########${forResVO.forRes_content}


					<c:forEach var="forResVO" items="${list}">

						<div class="card shadow-sm round-0 mt-3">

							<div class="card-body">
								<div class="row">

									<div class="col-md-2 col-4">
										<img id="user-img"
											src="https://api.fnkr.net/testimg/90x90/00CED1/FFF/?text=img+placeholder">
									</div>


									<div class="col-md-7 col-8 my-auto">
										<span>${forResVO.forRes_content}</span> <input type="hidden"
											name="forPost_ID" value="${forPosVO.forPost_ID}">
									</div>

									<div class="d-flex flex-column align-content-center">
										<button class="button btn-lg" name="posRes">
											<i class="fa fa-thumbs-up"></i>
											<!-- <i class="fas fa-thumbs-up"></i>è©å¹å -->
										</button>

										<button class="button btn-lg" name="negRes">
											<i class="fa fa-thumbs-down"></i>
										</button>
									</div>
								</div>
								<hr>

								<p class="text-muted">${forResVO.mem_ID} 回應於
									${forResVO.forRes_time}</p>

								<div class="d-flex justify-content-end" id="res-footer">
									
								</div>


							</div>
						</div>


					</c:forEach>
					
					</form>