/**
 * 城市医院
 */
var ProjectCityHospitalDlg = {
	projectCityHospitalData : {},
	validateFields : {
		hospitalName : {
    		validators: {
                notEmpty: {
                    message: '医院不能为空'
                }
            }
    	},
    	departmentName : {
    		validators: {
                notEmpty: {
                    message: '科室不能为空'
                }
            }
    	}
	}
};

/**
 * 设置对话框中的数据
 * 
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectCityHospitalDlg.set = function(key, val) {
    this.projectCityHospitalData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 清除数据
 */
ProjectCityHospitalDlg.clearData = function() {
    this.projectCityHospitalData = {};
}

/**
 * 收集数据
 */
ProjectCityHospitalDlg.collectData = function() {
	this
	.set('projectid')
	.set('cityid')
	.set('hospitalName')
	.set('departmentName');
}

/**
 * 关闭此对话框
 */
ProjectCityHospitalDlg.close = function() {
	parent.layer.close(window.parent.ProjectCityHospitalDlg.layerIndex)
}

/**
 * 提交
 */
ProjectCityHospitalDlg.hospitalSubmit = function() {
	// 验证数据
	if (!this.validate()) {
		return;
	}
	
	this.clearData();
	this.collectData();
	
	// 提交信息
	var ajax = new $ax(Feng.ctxPath + "/project/hospitalAdd", function (data) {
		Feng.success("创建成功!");
		window.parent.ProjectCityHospitalDlg.table.refresh();
		ProjectCityHospitalDlg.close();
	}, function (data) {
		Feng.error("创建失败!", + data.responseJSON.message + "!");
	});
	ajax.set(this.projectCityHospitalData);
	ajax.start();
}

/**
 * 验证数据是否合法
 */
ProjectCityHospitalDlg.validate = function() {
	$("#projectCityHospitalForm").data("bootstrapValidator").resetForm();
	$('#projectCityHospitalForm').bootstrapValidator('validate');
	return $("#projectCityHospitalForm").data('bootstrapValidator').isValid();
}

$(function() {
	Feng.initValidator("projectCityHospitalForm", ProjectCityHospitalDlg.validateFields);
})