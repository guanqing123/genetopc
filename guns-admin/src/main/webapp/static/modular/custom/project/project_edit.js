/**
 * 初始化编辑项目对话框
 */
var ProjectInfoDlg = {
    projectInfoData : {},
    validateFields : {
    	xmmc : {
    		validators: {
                notEmpty: {
                    message: '项目名称不能为空'
                }
            }
    	},
    	syz : {
    		validators: {
                notEmpty: {
                    message: '适应症不能为空'
                }
            }
    	},
    	xmyy : {
    		validators: {
                notEmpty: {
                    message: '项目用药不能为空'
                }
            }
    	},
    	jzsj : {
    		validators: {
                notEmpty: {
                    message: '截止时间不能为空'
                }
            }
    	}
    }
};

/**
 * 初始化switchery
 */
ProjectInfoDlg.setSwitchery = function (switchElement, checkedBool) {
    if ((checkedBool && !switchElement.isChecked()) || (!checkedBool && switchElement.isChecked())) {
        switchElement.setPosition(true);
        switchElement.handleOnchange(true);
    }
}

/**
 * 清除数据
 */
ProjectInfoDlg.clearData = function() {
    this.projectInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectInfoDlg.set = function(key, val) {
    this.projectInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectInfoDlg.close = function() {
    parent.layer.close(window.parent.Project.layerIndex);
}

/**
 * 收集数据
 */
ProjectInfoDlg.collectData = function(base64Data, state) {
    this
    .set('projectid')
    .set('xmmc')
    .set('syz')
    .set('xmyy')
    .set('jzsj')
    .set('xmjs', $("#xmjs").summernote('code'))
    .set('cjbz', $("#cjbz").summernote('code'))
    .set('base64Data', base64Data)
    .set('state', state?1:0);
}

/**
 * 验证数据是否为空
 */
ProjectInfoDlg.validate = function () {
	$('#projectInfoForm').data("bootstrapValidator").resetForm();
    $('#projectInfoForm').bootstrapValidator('validate');
    return $("#projectInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交修改
 */
ProjectInfoDlg.editSubmit = function() {

	if (!this.validate()){
		return;
	}
	
	var base64Data = $("#imgview").attr("src");
	if (base64Data.length < 1) {
		Feng.error('缩略图必须上传');
		return;
	}
	
	this.clearData();
	this.collectData(base64Data, document.querySelector('.js-switch').checked);
	
	$.ajax({
		url : Feng.ctxPath + "/project/update",
		type : 'post',
		data : this.projectInfoData,
		success: function (data) {
			Feng.success("修改成功!");
			window.parent.Project.table.refresh();
			ProjectInfoDlg.close();
		},
		error: function (r, m, e) {
			Feng.error(r.responseJSON.message)
		}
	});
}

/**
 * 初始化Web Uploader
 */
var uploader = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto: false,
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#imagePicker',
    // 只允许选择图片文件。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    }
});
// 当有文件添加进来的时候
uploader.on('fileQueued', function (file) {
    // 如果为非图片文件，可以不用调用此方法。
    // thumbnailWidth x thumbnailHeight 为 100 x 100
    uploader.makeThumb(file, function (error, src) {
        $("#imgview").attr("src", src);
    }, 80, 80);
});

$(function() {
	Feng.initValidator("projectInfoForm", ProjectInfoDlg.validateFields);
	
	// switchery初始化
	var elem = document.querySelector('.js-switch');
	var init = new Switchery(elem);
	ProjectInfoDlg.setSwitchery(init, $('#state').val()==="1"?true:false);
	
	// 截止日期
	laydate({
        elem: '#jzsj', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        choose: function(dates){
        	$('#projectInfoForm').data("bootstrapValidator").resetForm();
            $('#projectInfoForm').bootstrapValidator('validate');
        }
    });
	
    // summernote初始化
    $(".summernote").summernote({
    	toolbar: [
		    // [groupName, [list of button]]
		    ['style', ['bold', 'italic', 'underline', 'clear']],
		    ['fontsize', ['fontsize']],
		    ['color', ['color']],
		    ['para', ['ul', 'ol', 'paragraph']],
		    ['height', ['height']]
		  ],
        height:"100px",
        tabsize: 2,
        lang: 'zh-CN',
        codemirror: {
            mode: 'text/html',
            htmlMode: true,
            lineNumbers: true,
            theme: 'default'
        }
    });
});


