<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>



	<script type="text/javascript">

	$(function(){

		//创建单击事件
		$("#createActivityButton").click(
				function () {
					//初始化阶段
					//清空表单上的数据
					$("#createActivityFrom").get(0).reset();

					//弹出创建市场模态窗口
					$("#createActivityModal").modal("show");
				}
		);

		//给保存事件加单击事件,url="workbench/activity/saveCreateActivity.do",modalId="#createActivityModal"
		//queryActivityByConditionFroPage(1,$("#nav").bs_pagination("getOption","rowsPerPage"));
		$("#saveCreateActivity").click(
				function () {
					var name = $.trim($("#create-marketActivityName").val());
					var owner = $("#create-marketActivityOwner").val();
					console.log("选择的owener："+owner);
					var startDate = $("#create-startTime").val();
					var endDate = $("#create-endTime").val();
					var cost = $.trim($("#create-cost").val());
					var description = $.trim($("#create-describe").val());
					var url="workbench/activity/saveCreateActivity.do";
					var modalId="#createActivityModal";
					add(name,owner,startDate,endDate,cost,description,url,modalId,null,1);
				});

    //    写在$(function()里面表示整个页面元素加载完毕
	//	添加日历
		getDate(".myDate",new Date().toDateString());
		// $(".myDate").datetimepicker({
		// 	language:'zh-CN',//设置语言
		// 	format:'yyyy-mm-dd',//设置时间格式
		// 	minView:'month',//最小时间选择--->精确到日
		// 	initialDate:new Date(),//初始时间，默认是今天
		// 	autoclose:true,//是否自动关闭
		// 	todayBtn:true,//确定今天按钮
		// 	clearBtn:true//清空时间
		// });
		//不能选择两个时间的id，因为重复代码高，需要避免

		//当打开页面，初始化列出数据
		queryActivityByConditionFroPage(1,10);

		//点击查询按钮
		$("#queryButton").click(function()
		{
			//查询默认以标准形式输出,查询一页显示多少数据用上次查询的数据所表示的页数
			// queryActivityByConditionFroPage(1,10);
			queryActivityByConditionFroPage(1,$("#nav").bs_pagination("getOption","rowsPerPage"));
		});

		//设置选择框全选按钮
		$("#checkedAll").click(
				//当这个按钮选中，则表示全选
				function () {
					// if (this.checked)
					// {
					// 	//"#tBody>input[type='checkbox']"表示
					// 	//#tBody下的所有子标签，对于子标签的子标签无法选择
					// 	//"#tBody input[type='checkbox']"表示tBody下所有标签
					// 	$("#tBody input[type='checkbox']").prop("checked",true);
					// } else{
					// 	$("#tBody input[type='checkbox']").prop("checked",false);
					// }

				//	简化上面代码,this代表当前document的#checkedAll所代表的属性
					$("#tBody input[type='checkbox']").prop("checked",this.checked);
				}
		);
		//这种方法是给固有元素添加事件
		// $("#tBody input[type='checkbox']").click(
		// 		function () {
		// 		//	如果列表的checkbox有一个没选中，则全选框不选定
		// 			if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size())
		// 			{
		// 				$("#checkedAll").prop("checked",true);
		// 			}else{
		// 				$("#checkedAll").prop("checked",false);
		// 			}
		// 		}
		// );

		//对于动态元素添加事件,对于如果有一个选择框没选到，则全选框取消
		$("#tBody").on("click","input[type='checkbox']",function () {
			//	如果列表的checkbox有一个没选中，则全选框不选定
			if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size())
			{
				$("#checkedAll").prop("checked",true);
			}else{
				$("#checkedAll").prop("checked",false);
			}
		});

		//给删除按钮添加事件
		$("#deleteActivity").click(
				function () {
					console.log("删除")
					//获取参数(删除的id)
					var ids = $("#tBody input[type='checkbox']:checked");
					if (ids.size() == 0)
					{
						alert("请选择删除。。。。");
						return;
					}
					if (window.confirm("确定删除？"))
					{
						let idStr = "";
						//遍历id，获取到单个
						$.each(ids,function (index,obj) {
							//	this相当于obj
							idStr+="id="+obj.value+"&";
						});
						//去掉最后一个'&'字符
						idStr = idStr.substr(0,idStr.length-1);
						alert(idStr);
						console.log(idStr);
						$.ajax({
							url:"workbench/activity/deleteActivityByIds.do",
							data: id = idStr,
							dataType:'json',
							success:function (data) {
								if (data.code == 0)
								{
									alert(data.messge);
								} else{
									queryActivityByConditionFroPage(1,$("#nav").bs_pagination("getOption","rowsPerPage"));
								}
							}
						});
					}
				}
		);

		//添加修改功能
		//首先查询出具出来
		$("#updateActivity").click(
				function(){
					//获取被选中的框
					var checkid = $("#tBody input[type='checkbox']:checked");
					if (checkid.length != 1)
					{
						alert("请选择一个,且只能选择一个");
						return;
					}
					else{
						console.log(checkid.val());
						var id = checkid.val();
						console.log("id"+id);
						$.ajax({
							url:'workbench/activity/selectActivityById.do',
							data:{
								id:id
							},
							dataType:'json',
							success:function (data) {
								var numberId = data.id;
								//显示修改数据
								$("#editActivity").val(data.id);
								$("#edit-marketActivityName").val(data.owner);
								$("#edit-marketActivityName").val(data.name);
								console.log("选到的ownerID"+data.owner);
								//修改选择框，使其变为日历选择
								getDate("#edit-startTime",data.startDate);
								getDate("#edit-endTime",data.endDate);
								// $("#edit-startTime").val(data.startDate);
								// $("#edit-endTime").val(data.endDate);
								$("#edit-cost").val(data.cost);
								$("#edit-describe").val(data.description);
								//弹出修改模拟窗口
								$("#editActivityModal").modal("show");
							}
						});
					}
				}
		);

		//	当修改完成点击保存按钮
		$("#update").click(
				function () {
					//	保存到数据库中
					//	开始保存数据到数据库
					//获取参数 $("#tBody input[type='checkbox']:checked")
					var id = $("#tBody input[type='checkbox']:checked").val();
					var name = $.trim($("#edit-marketActivityName").val());
					var owner = $("#edit-marketActivityOwner").val();
					var startDate = $("#edit-startTime").val();
					var endDate = $("#edit-endTime").val();
					var cost = $("#edit-cost").val();
					var description = $.trim($("#edit-describe").val());
					var url="workbench/activity/updateActivity.do";
					var modalId="#editActivityModal";
					console.log("owner"+owner);
					add(name,owner,startDate,endDate,cost,description,url,modalId,id,$("#nav").bs_pagination("getOption","currentPage"));
				}
		);

	//	批量导出
		$("#exportActivityAllBtn").click(
				function () {
				window.location.href="workbench/activity/exprotAllActivity.do";
				}
		);

	//	部分导出
		$("#exportActivityXzBtn").click(
				function () {
					let vals = $("#tBody input[type='checkbox']:checked");
					if (vals.size() == 0)
					{
						alert("请选择要导出的数据");
						return;
					}
					let idStr="";
					$.each(vals,function (index,obj) {
						idStr+="id="+obj.value+'&';
					});
					idStr=idStr.substr(0,idStr.length-1);
					window.location.href="workbench/activity/exprotActivityById.do" + "?" +idStr;
				});


		//导入功能
		$("#importActivityBtn").click(
				function () {
					//收集参数
					var file = $("#activityFile").val();
					console.log("文件路径"+file);
					if((file.substr(file.lastIndexOf(".")+1)).toLocaleLowerCase() !="xls")
					{
						alert("文件格式不正确"+(file.substr(file.lastIndexOf("."))+1).toLocaleLowerCase());
						return;
					} else{
						//获取到文件本身,从document对象获取
						var activityFile = $("#activityFile")[0].files[0];
						// console.log("文件大小"+activityFile.size);
						if (activityFile.size>=5*1024*1024)
						{
							alert("文件大小不能超过5MB");
							return;
						}

						//ForData实际上是接口,里面的参数相当于键值对的形式
						var formData = new FormData();
						formData.append("activityFile",activityFile);
						// formData.append("name","value")
						//发送请求
						$.ajax({
							url:"workbench/activity/importActivity.do",
							dataType:'json',
							type: 'post',
							//ajax向后台传递数据，是否把参数封装为字符串---true，----false
							processData:false,
							//ajax向后台传递数据，是否按照统一编码
							contentType:false,
							//传递数据的三种方式
							//data:{}
							//data: xxx=xxxx
							//data:ForData,提交数据的格式广
							data:formData,
							success:function (data) {
								if (data.code >= 0)
								{
									alert(data.messge);
									$("#importActivityModal").modal("hide");
									queryActivityByConditionFroPage(1,$("#nav").bs_pagination("getOption","rowsPerPage"));
								} else {
									alert(data.messge);
								}
								$("#importActivityModal").modal("show");
							}
						});
					}
				}
		);

	});

	//要放在外面，因为这个函数和$.function()是同级函数
	function queryActivityByConditionFroPage(pageNo,pageSize){
		//当市场页面加载完毕的时候，就要加载所有数据
		//搜集参数
		var name = $("#query-name").val();
		var owner = $("#query-owner").val();
		getDate("#startTime",null);
		getDate("#endTime",null);
		var startDate = $("#startTime").val();
		var endDate = $("#endTime").val();
		//需求默认
		// var pageNo = 1;
		// var pageSize = 10;

		//	发送请求,查询数据并分页
		$.ajax({
			url:"workbench/activity/queryActivityCountByCountForPage.do",
			data:{
				name:name,
				owner:owner,
				startDate:startDate,
				endDate:endDate,
				pageNo:pageNo,
				pageSize:pageSize
			},
			dataType: "json",
			type:"post",
			success:function (data) {
				//	显示市场记录
				//	用js遍历json数据，如果要在jsp中遍历数据，必须是作用域里面的数据
				var htmlStr = "";
				$.each(data.activityList,function (index,obj) {
					//插入规则：<tr class="active">---><tr class=\"active\">
					htmlStr+="<tr class=\"active\">";
					htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>"
					htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/queryDetailAcitivityById.do?id="+obj.id+"'\">"+obj.name+"</a></td>"
					htmlStr+="<td>"+obj.owner+"</td>"
					htmlStr+="<td>"+obj.startDate+"</td>"
					htmlStr+="<td>"+obj.endDate+"</td>"
					htmlStr+="</tr>"
				});
				$("#tBody").html(htmlStr);

				//换页取消全选,当进入新的页面的时候，取消全选
				$("#checkedAll").prop("checked",false);

				//计算总页数
				var totalPages=1;
				if (data.totalRows%pageSize==0)
				{
					totalPages = data.totalRows/pageSize;
				}else {
					// parseInt()把小数变为整数，不按四舍五入
					totalPages = parseInt(data.totalRows/pageSize)+1;
				}
				//	翻页功能
				$("#nav").bs_pagination({
					currentPage:pageNo,//初始化的页数
					totalRows:data.totalRows,//总条数
					totalPages:totalPages,//总页数
					rowsPerPage:pageSize,//每页显示多少数据
					visiblePageLinks: 5,//导航栏显示页码
					showGoToPage: true,//是否有跳转
					showRowsPerPage: true,//是否显示”每页显示条数“
					showRowsInfo: true,//是否希纳是记录信息

					onChangePage:function (event,pageObj) {
					//	手动刷新列表，但是导航栏自动变换,由于插件可以自动获取变换后的页数和一页展示的数据数目
						queryActivityByConditionFroPage(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
			}
		});
	}

	//封装日历，使其方便使用
		function getDate(tClass,strDate) {
			//	添加日历
			$(tClass).datetimepicker({
				language:'zh-CN',//设置语言
				format:'yyyy-mm-dd',//设置时间格式
				minView:'month',//最小时间选择--->精确到日
				initialDate:new Date(strDate),//初始时间，默认是今天
				autoclose:true,//是否自动关闭
				todayBtn:true,//确定今天按钮
				clearBtn:true//清空时间
			});
		}

	//封装保存数据到数据库（添加数据、修改数据可用）
		function add(name,owner,startDate,endDate,cost,description,url,modalId,id,currentPage) {
				//表单验证
				if ( owner == "")
				{
					alert("所有者不能为空");
					return;
				}
				if ( name == "")
				{
					alert("名称不能为空");
					return;
				}
				if (startDate != "" && endDate != "")
				{
					//比较时间大小
					if (startDate>endDate)
					{
						alert("开始时间不能大于结束时间");
						return;
					}
					//正则表达式
					var regExp = /^(([1-9]\d*)|0)$/;
					if (!regExp.test(cost))
					{
						alert("成本不能是负数");
						return;
					}
					console.log("owner");
					console.log(owner);
					//发送请求
					$.ajax({
						url:url,
						data:{
							id:id,
							owner:owner,
							name:name,
							startDate:startDate,
							endDate:endDate,
							cost:cost,
							description:description
						},
						dataType:"json",
						success:function (data) {
							//重新查询数据
							queryActivityByConditionFroPage(currentPage,$("#nav").bs_pagination("getOption","rowsPerPage"));
							//接受成功添加的信息
							if (data.code == 1)
							{
								//隐藏
								$(modalId).modal("hide");
								//	刷新数据
							}
							else{
								//提示信息
								alert(data.messge);
							}
						}
					});
				}

		}

</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form" id="createActivityFrom">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-startTime" readonly="readonly">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label" >结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-endTime" readonly="readonly">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCreateActivity">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">
						<input type="hidden" id="editActivity">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10" readonly="readonly">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20" readonly="readonly">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="update">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>


	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="startTime" readonly="readonly"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="endTime" readonly="readonly">
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="queryButton">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
<%--					自己添加单击事件--%>
				  <button type="button" class="btn btn-primary" id="createActivityButton"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="updateActivity"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivity"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkedAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
<%--					用json的数据遍历出来--%>
					</tbody>
				</table>
				<%--				翻页导航栏--%>
				<div id="nav"></div>
			</div>
		</div>

	</div>
</body>
</html>
