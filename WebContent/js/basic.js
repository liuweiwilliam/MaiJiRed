var IS_LOCAL_TEST = true;

//ajax请求
function xtlAjax(type,url,para,func){
	$.ajax({
		type:type,
		url:url,
		data: "sub=" + encodeURIComponent(JSON.stringify(para)),
		dataType:"json",
		success:function(data){
			if(data.error.isError){
				alert(data.error.content);
			}else{
  				func(data.result);
			}
		},
		error:function(XHR){
			//alert("请求失败：" + XHR.status);
			console.log(url + "请求失败：" + XHR.status);
		}
	});	
}

//默认ajax GET请求
function xtlGet(url, para, func){
	xtlAjax("GET", url, para, func);
}

//默认无参的ajax GET请求
function xtlRawGet(url, func){
	var para = {};
	xtlAjax("GET", url, para, func);
}

//默认ajax POST请求
function xtlPost(url,para,func){
	xtlAjax("POST", url, para, func);
}

//获得url参数时
function getUrlParameter(para) {
    var reg = new RegExp('(^|&)' + para + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

//显示toast提示
function showToast(msg, timeout){
	if($("#toastModal").length==0){
		var html = 
			  '<div class="modal fade" id="toastModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
				+ '<div class="modal-dialog" style="width:30%;margin-left:auto;margin-right:auto;">'
				+ 	'<div class="modal-content">'
				+ 		'<div class="modal-header">'
				+ 		'    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
				+ 		'    <h4 class="modal-title" id="myModalLabel">提示</h4>'
				+	 	'</div>'
				+	 	'<div class="modal-body" id="toastMsg"></div>'
				+	 	'<div class="modal-footer">'
				+ 		'</div>'
				+ 	'</div>'
				+ '</div>'
			+ '</div>';
		
		$('body').append(html);
	}
	
	$("#toastModal").find("#toastMsg").html(msg);
	$("#toastModal").modal("show");
	var t=setTimeout('$("#toastModal").modal("hide")', timeout);
}

//显示警告类型的toast提示
function showWarn(msg, timeout){
	if($("#toastModal").length==0){
		var html = 
			  '<div class="modal fade" id="toastModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
				+ '<div class="modal-dialog" style="width:30%;margin-left:auto;margin-right:auto;">'
				+ 	'<div class="modal-content">'
				+ 		'<div class="modal-header">'
				+ 		'    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
				+ 		'    <h4 class="modal-title" id="myModalLabel">提示</h4>'
				+	 	'</div>'
				+	 	'<div class="modal-body" id="toastMsg"></div>'
				+	 	'<div class="modal-footer">'
				+ 		'</div>'
				+ 	'</div>'
				+ '</div>'
			+ '</div>';
		
		$('body').append(html);
	}
	
	$("#toastModal").find("#toastMsg").html(msg);
	$("#toastModal").modal("show");
	var t=setTimeout('$("#toastModal").modal("hide")', timeout);
}

var mask_id = 0;
//显示遮罩层
//参数：
//	sel : 要遮盖的控件的选择器
//  msg ： 遮罩层上显示的信息
//
function xtlShowMask(sel, msg){
	var par = $(sel).parent();
	
	var pos_flag = $(par).css("position")!="static";
	if(pos_flag){
		$(par).css("position", "relative");
	}
	
	var id = "lzzmask-" + mask_id;
	mask_id ++;
	
	if($(sel)[0]==$("body")[0]){
		$(sel).append('<div id="' + id + '" class="lzzmask" style="display:none;text-align:center;"><div class="maskMsg" style="color:white;"></div></div>');
	}else{
		$(sel).after('<div id="' + id + '" class="lzzmask" style="display:none;text-align:center;"><div class="maskMsg" style="color:white;"></div></div>');
	}
	
	var offsettop=$(sel).position().top;
    var offsetleft=$(sel).position().left;
    var height = $(sel).height();
    var width = $(sel).width();
    
    var mask_div;
    if($(sel)[0]==$("body")[0]){
    	mask_div = $(sel).children(".lzzmask");
    }else{
    	mask_div = $(sel).nextAll(".lzzmask").eq(0);
    }
    
    $(mask_div).css({"position":"absolute","top":offsettop+"px", "left":offsetleft+"px", "width":width, "height":height});
    var msg_div = $(mask_div).find(".maskMsg");
    $(msg_div).css({
    	"display": "flex",
    	"justify-content": "center",
    	"align-items": "center",
    	"width":"100%",
    	"height":"100%"
    });
    $(msg_div).text(msg);
    $(mask_div).show();
}

//隐藏遮罩层
function xtlHideMask(sel){
    if($(sel)[0]==$("body")[0]){
    	$(sel).children(".lzzmask").remove();
    }else{
    	$(sel).nextAll(".lzzmask").eq(0).remove();
    }
}

//zTree 获得勾选节点的数组
function getCheckedTreeId(sel){
	var treeObj = $.fn.zTree.getZTreeObj(sel);
	var nodes = treeObj.getCheckedNodes(true);
	var ids = [];
	for(var i=0; i<nodes.length; i++){
		ids.push(nodes[i].id);
	}
	return ids;
}

//modal窗口内容的验证
function modalValidation(btn,c){
	//console.log(c);
	var errorMsg = new Array();
	for(var i = 0; i < c.length; i++){
		switch (c[i].type) {
			case "number":
				if(isNaN(c[i].content)){
					errorMsg.push(c[i].tip);
				}
				break;
			case "email":
				if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(c[i].content)){
					errorMsg.push(c[i].tip);
				}
				break;
				
			case "notEmpty":
				if(c[i].content=="" || c[i].content==undefined || c[i].content==[]){
					errorMsg.push(c[i].tip);
				}
				break;
				
			case "custom":
				if(!c[i].rule.test(c[i].content)){
					errorMsg.push(c[i].tip);
				}
				break;
				
			case "error":
				errorMsg.push(c[i].tip);
				break;
	
			default:
				console.log(c[i].type + "undefine type");
				break;
			}
	}
	//验证结果
	//console.log(errorMsg);
	var modalBody = $(btn).parents(".modal-body");
	$(".alert",modalBody).remove();
	if(errorMsg.length == 0){
		return true;
	}else{
		//显示提示信息
		var alertText = "";
		alertText +=	'<div class="alert alert-danger alert-dismissable" style="margin:10px 0; display:block; position:absolute; top:0; left:2%;;width:96%;">';
		alertText +=		'<button type="button" class="close" data-dismiss="alert">';
		alertText +=			'&times;';
		alertText +=		'</button>';
		alertText +=		'<p>填写项出现以下错误</p>';
		alertText +=		'<ul>';
		for(var i = 0; i < errorMsg.length; i++){
			alertText +=		'<li>' + errorMsg[i] + '</li>';
		}
		alertText +=		'</ul>';
		alertText +=	'</div>';
		
		modalBody.append(alertText);
		modalBody[0].scrollTop = 0;
		
		return false;
	}
}

//提交数据验证
function modalValidation_new(c){
	var errorMsg = new Array();
	for(var i = 0; i < c.length; i++){
		switch (c[i].type) {
			case "number":
				if(isNaN(c[i].content)){
					errorMsg.push(c[i].tip);
				}
				break;
			case "email":
				if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(c[i].content)){
					errorMsg.push(c[i].tip);
				}
				break;
				
			case "notEmpty":
				if(c[i].content=="" || c[i].content==undefined || c[i].content==[]){
					errorMsg.push(c[i].tip);
				}
				break;
				
			case "custom":
				if(!c[i].rule.test(c[i].content)){
					errorMsg.push(c[i].tip);
				}
				break;
				
			case "error":
				errorMsg.push(c[i].tip);
				break;
	
			default:
				console.log(c[i].type + "undefine type");
				break;
			}
	}
	//验证结果
	if(errorMsg.length == 0){
		return true;
	}else{
		//显示提示信息
		var alertText = "提交信息存在以下错误，请修正后提交：\n";
		for(var i = 0; i < errorMsg.length; i++){
			alertText += (i+1) + "." +  errorMsg[i] + "\n";
		}
		alert(alertText);
		return false;
	}
}

$("document").ready(function(){
	
	//为modal添加打开时动画
	$(".modal").each(function(index) {
	  $(this).on('show.bs.modal', function(e) {
	    var open = $(this).attr('data-easein');
	    if (open == 'shake') {
	      $('.modal-dialog').velocity('callout.' + open);
	    } else if (open == 'pulse') {
	      $('.modal-dialog').velocity('callout.' + open);
	    } else if (open == 'tada') {
	      $('.modal-dialog').velocity('callout.' + open);
	    } else if (open == 'flash') {
	      $('.modal-dialog').velocity('callout.' + open);
	    } else if (open == 'bounce') {
	      $('.modal-dialog').velocity('callout.' + open);
	    } else if (open == 'swing') {
	      $('.modal-dialog').velocity('callout.' + open);
	    }/* else {
	      $('.modal-dialog').velocity('transition.' + open);
	    }*/
	  });
	});
	
});