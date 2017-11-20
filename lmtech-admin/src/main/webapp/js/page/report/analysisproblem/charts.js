function setType(type, checkItemId) {
	$("#type").val(type);
	if (checkItemId) {
		$("#checkItemId").val(checkItemId);
	}
	$("form").submit();
}

function Charts(selector, title, data) {
	$(selector).highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		title : {
			text : title
		},
		tooltip : {
			pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f} %'
				},
				showInLegend : true
			}
		},
		exporting : {
			// enabled:true,默认为可用，当设置为false时，图表的打印及导出功能失效
			buttons : { // 配置按钮选项
				printButton : { // 配置打印按钮
					width : 50,
					symbolSize : 20,
					borderWidth : 2,
					borderRadius : 0,
					hoverBorderColor : 'red',
					height : 30,
					symbolX : 25,
					symbolY : 15,
					x : -200,
					y : 20

				},
				exportButton : { // 配置导出按钮
					width : 50,
					symbolSize : 20,
					borderWidth : 2,
					borderRadius : 0,
					hoverBorderColor : 'red',
					height : 30,
					symbolX : 25,
					symbolY : 15,
					x : -150,
					y : 20
				}

			},

			filename : 'problem',// 导出的文件名
			type : 'image/png',// 导出的文件类型
			width : 800
		// 导出的文件宽度

		},
		lang:{
			printChart: '打印图片',
			downloadPNG: '下载PNG',
			downloadJPEG: '下载JPEG',
			downloadPDF: '下载PDF',
			downloadSVG: '下载 SVG',
			contextButtonTitle: 'Chart context menu'
		},
		series : [ {
			type : 'pie',
			name : 'share',
			data : data
		} ]
	});

}
$(function() {

	initAutoComplete({
		url : URL_BASE + "/project/projectphaselist.do",
		selectId : "projectNameInput",
		onSelected : function(id, text) {
			var ids = id.split("|");
			$("#projectId").val(ids[0]);
			$("#phaseId").val(ids[1]);
			$('#batchId').val("");
			$('#projectName').val(text);
			$('#checkItemId').val("");
			var type = $("#type").val();
			if (type == 3)
				$("#type").val(1);
			$("form").submit();
		}
	});
	$('#projectBtn')
			.click(
					function() {
						var frame = top
								.showModal({
									url : URL_BASE
											+ "/project/projectphaselistpage.do?modal=setProjectPharse&projectName=",
									title : "项目分期查询",
									showFooter : false,
									opener : window,
									width : 800,
									height : 600,
									onFinished : function(exeResult) {

									}
								});

					});

	$('#batchId').change(function() {
		$("form").submit();
	});

});

function setProjectPharse(projectId, phaseId, name) {
	$("#projectId").val(projectId);
	$("#phaseId").val(phaseId);
	$('#projectName').val(name);
	$('#projectNameInput').val(name);
	$("form").submit();
}