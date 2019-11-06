/**
 * 初始化设置焦点图对话框
 */
var ProjectFocusDlg = {
	projectFocusData : {}
};

/**
 * 清除数据
 */
ProjectFocusDlg.clearData = function() {
    this.projectInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectFocusDlg.set = function(key, val) {
    this.projectFocusData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 收集数据
 */
ProjectFocusDlg.collectData = function(base64Data, jd) {
    this
    .set('projectid')
    .set('base64Data', base64Data)
    .set('jd', jd?1:0)
    .set('jdOrder');
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
    }, 320, 128);
});

/**
 * 初始化switchery
 */
ProjectFocusDlg.setSwitchery = function (switchElement, checkedBool) {
    if ((checkedBool && !switchElement.isChecked()) || (!checkedBool && switchElement.isChecked())) {
        switchElement.setPosition(true);
        switchElement.handleOnchange(true);
    }
}

/**
 * 提交焦点图设置
 */
ProjectFocusDlg.focusSubmit = function() {
	// 焦点
	var jd = document.querySelector('.js-switch').checked;
	var base64Data = $("#imgview").attr("src");
	if (jd & base64Data.length < 1) {
		Feng.error('打开焦点,必须上传焦点图');
		return;
	}
	
	this.clearData();
	this.collectData(base64Data, jd);
	
	$.ajax({
		url : Feng.ctxPath + "/project/focus",
		type : 'post',
		data : this.projectFocusData,
		success: function (data) {
			Feng.success("设置成功!");
			window.parent.Project.table.refresh();
			ProjectFocusDlg.close();
		},
		error: function (r, m, e) {
			Feng.error(r.responseJSON.message)
		}
	});
}

/**
 * 关闭此对话框
 */
ProjectFocusDlg.close = function() {
	parent.layer.close(window.parent.Project.layerIndex);
}

$(function() {
	// switchery初始化
	var elem = document.querySelector('.js-switch');
	var init = new Switchery(elem);
	ProjectFocusDlg.setSwitchery(init, $('#jd').val()==="1"?true:false);
})