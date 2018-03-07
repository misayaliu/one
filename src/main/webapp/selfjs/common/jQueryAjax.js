var WEBOPERATION = 'query';// query,add,edit

/*----下面的为Spring版本的----*/
function jQueryAjaxForJSON(url,submitData,callbackfunc1,callbackfunc2,callbackfunc3,fucnBefore,funAfter){
    // 注意：传入的JSON数组中不允许带&这个符号的
    // 转化为JSON的时候用这个函数 jQuery.parseJSON(str)
    var submitData_str = jQuery.toJSONString(submitData);
    submitData_str = encodeURIComponent(submitData_str);
    //alert(url+'          '+submitData_str);
    $.ajax({
		type: "post",
		url: url,
		timeout: 300000,
		//data :submitData_str,
		//data :$.param(submitData),
		data :submitData,
		dataType: "json",
		contentType:"application/x-www-form-urlencoded;charset=utf-8", 
		beforeSend: function(XMLHttpRequest){
		    //XMLHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8'");
		    // $("#div-loading").show();
			if(fucnBefore != undefined){
				fucnBefore();
			}
		},
		success: function(data, textStatus){
			if(data.result=='success'&&textStatus){
		        callbackfunc1(data);
		    }else{
		        if(callbackfunc2 != undefined){
   					callbackfunc2(data);
   				}else{
   					
   				}
		    }
		},
		complete: function(XMLHttpRequest, textStatus){
		    // $("#div-loading").hide();
			if(funAfter != undefined){
				funAfter();
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			// 请求出错处理
			var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			if(sessionstatus=="timeout"){   
            	// 如果超时就处理 ，指定要跳转的页面
				alert('登陆超时，请重新登陆');
				//top.location.repalce("");
				//if(top != self){
					//if(top.location != self.location){
			        	//top.location=self.location; 
					//}
			    //}
                //window.parent.location.href("/");//因为只有在登陆之后才会出现timeout，所以直接parent返回首页
                window.parent.location.href=basePath;
            }else if(sessionstatus=="nologgettree"){
            	alert('登陆超时，请重新登陆');
            	window.location.href=basePath;
            }else{
            	if(callbackfunc3 != undefined){
    				callbackfunc3();
    			}else{
       				// Ext.Msg.alert('系统提示','通讯超时!');return;
       			}
            }
		}
	});
}

function geneSubmitDataFromForm(element,submitData){
	 var x = $(element).serializeArray();
	 if(submitData==undefined){
		 submitData = {};
	 }
	 $.each(x, function(i, field){
       submitData[field.name] = field.value;
	});
	 return submitData;
}

function clearForm(element){//element为form表单  
	//var x = $(element).serializeArray();
	//$.each(x, function(i, field){
	//	$("[name='"+field.name+"']").val('');
	//});
	
	$(element)[0].reset();
}  

function loadDataForForm(element,data){
	var x = $(element).serializeArray();
	$.each(x, function(i, field){
		$("[name='"+field.name+"']").val(data[field.name]);
		//$(field.name).val(data[field.name]);
	});
}

function comboboxRequest(url,cb,successLoadValue){
	$(cb).combobox({
		url:  url,
		onLoadSuccess: function(param){
			if(successLoadValue != undefined){
				$(cb).combobox('setValue', successLoadValue);
			}
		}
	});
}

function combogridRequest(url,cb){
	$(cb).combogrid({
		url:  url
	});
}

function initPriviligeBtns(parentControl){
    //构造按钮
    var submitData = {};
    submitData['control'] = parentControl;//父节点
    $.messager.progress({title:'提示',msg:'正在初始化权限按钮，请稍等...',text:''});
    jQueryAjaxForJSON(basePath + '/cchs/initPriviligeBtns.do', submitData, function(
    			sRet) {
    		$.messager.progress('close');
    		var datastr = sRet.field1;
    		//COMMONPARAM_LOGONUSER = sRet.field2;//把登陆的用户加载起来
    		str=datastr.split(",");      
		    for (var i=0;i<str.length ;i++ )   
		    {           
		        $('#'+str[i]+'').show();
		    }   
		    $('#'+str[0]+'').show();
    	}, function(sRet) {
    		alert(2);
    	}, function() {
    		alert(3);
    	});
}

