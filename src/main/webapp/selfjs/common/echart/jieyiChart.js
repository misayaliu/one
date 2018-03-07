//抽象ECharts
var ECharts = {
	// 路径配置
	ChartRequireConfig : function(container, option) {
		//该路径只能指向echarts.js所在的文件处，缺一层多一层都不会显示图表
		var chart_path = "selfjs/common/echart"; // 配置图表请求路径
		require.config({// 引入常用的图表类型的配置
			paths : {
				echarts : chart_path
			}
		});

		this.option = {
			option : option,
			container : container
		};
		return this.option;

	},
	// 处理后台数据格式
	ChartDataFormate : {
		// 非分组数据
		FormateNOGroupData : function(data) {
			// data的格式如上的Result1，这种格式的数据，多用于饼图、单一的柱形图的数据源
			var group = [];
			for(var i =0;i < data.chartColumn.length;i++){
				group.push(data.chartColumn[i].chart_data);
			} ;
			
			 var series = [];
			for (var i = 0; i < data.dataStr.length; i++) {
				var temp = data.dataStr[i].seriesData;
				var series_temp = {
						name : data.dataStr[i].seriesName,
						data : temp,
						radius : '65%',
                        center: ['50%', '60%'],
						type : 'pie'
					};
				series.push(series_temp);
			}
			
			return {
				category : group,
				data : series
			};
		},

		// 分组数据
		FormateGroupData : function(data) {
			var chart_type = 'line';
			var type= data.chartType;
			if (type)
				chart_type = type || 'line';

			var xAxis = [];
			var group = [];
			for(var i =0;i < data.chartColumn.length;i++){
				group.push(data.chartColumn[i].chart_data);
			} ;
			 var series = [];
			for (var i = 0; i < data.dataStr.length; i++) {
				var temp = data.dataStr[i].seriesData;
				xAxis.push(data.dataStr[i].seriesName);
				var series_temp = {};
				switch (type) {
				case 'bar':
					series_temp = {
						name : data.dataStr[i].seriesName,
						data : temp,
						type : 'bar'
					};
					break;

				case 'line':
					series_temp = {
						name : data.dataStr[i].seriesName,
						data : temp,
						type : 'line'
					};
					break;
				default:
					 series_temp = {
						name : data.dataStr[i].seriesName,
						data : temp,
						type : chart_type
					};
				}
				series.push(series_temp);
			}
			return {
				category : group,
				xAxis : xAxis,
				series : series
			};
		}
	},
	ChartOption : {
		// 通用的图表基本配置
		CommonOption : {
			tooltip : {
				trigger : 'axis'// tooltip触发方式:axis以X轴线触发,item以每一个数据项触发
			},
			toolbox : {
				show : true, // 是否显示工具栏
				feature : {
					mark : true,
					dataView : {
						readOnly : false
					}, // 数据预览
					restore : true, // 复原
					saveAsImage : true
				// 是否保存图片
				}
			}
		},

		// 通用的折线图表的基本配置
		CommonLineOption : {
			tooltip : {
				trigger : 'axis'
			},
			calculable : true,
			toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		},
		Pie : function(data) {
			var text = data.text;
			var subtext = data.subtext;
			var jsonData = ECharts.ChartDataFormate
					.FormateNOGroupData(data);
			var option = {
				title : {
					text : text,
					subtext : subtext,
					x : 'center',
					height : '50%'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				color:[], 
				legend : {
					
					orient : 'vertical',
					x : 'left',
					data : jsonData.category
				},

				calculable : true,

				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						magicType : {
							show : true,
							type : [ 'pie', 'funnel' ],
							option : {
								funnel : {
									x : '25%',
									width : '50%',
									funnelAlign : 'left',
									max : 1548
								}
							}
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				series :jsonData.data
			};
			return $.extend({}, ECharts.ChartOption.CommonOption, option);
		},
		 Bars: function (data) {
	          //data:数据格式：{name：xxx,group:xxx,value:xxx}...
	          var bars_dates = ECharts.ChartDataFormate.FormateGroupData(data); 
	          var option = {
	                 legend:{
	                	 x:'center',
	                	data: bars_dates.xAxis
	                 },
	                 title : {
	       					text : data.text,
	       					subtext : data.subtext,
	       					x : 'left'
	       				},
	              xAxis: [{ 
	                  type: 'category', 
	                  data: bars_dates.category
	                  
	              }],

	              yAxis: [{ 
	                  type: 'value'
	                  
	              }], 
	              series: bars_dates.series 
	          }; 
	          return $.extend({}, ECharts.ChartOption.CommonLineOption, option); 
	      } 
	},
	 
	Charts : {
		RenderChart : function(option) {
			require([

			'echarts', 'echarts/chart/line', 'echarts/chart/bar',
					'echarts/chart/funnel', 'echarts/chart/pie',
					'echarts/chart/k', 'echarts/chart/scatter',
					'echarts/chart/radar', 'echarts/chart/chord',
					'echarts/chart/force', 'echarts/chart/map' ],

			function(ec) {
				var mychart = ec.init(option.container);
				mychart.setOption(option.option);
			});
		}
	}
};