/**
 * 项目城市
 */
var ProjectCityDlg = {
	projectCityData : {},
	validateFields : {
		cityName : {
    		validators: {
                notEmpty: {
                    message: '城市不能为空'
                }
            }
    	},
    	person : {
    		validators: {
                notEmpty: {
                    message: '联系人不能为空'
                }
            }
    	},
    	phone : {
    		validators: {
                notEmpty: {
                    message: '联系电话不能为空'
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
ProjectCityDlg.set = function(key, val) {
    this.projectCityData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 清除数据
 */
ProjectCityDlg.clearData = function() {
    this.projectCityData = {};
}

/**
 * 收集数据
 */
ProjectCityDlg.collectData = function() {
	this
	.set('projectid')
	.set('cityName')
	.set('person')
	.set('phone');
}

/**
 * 关闭此对话框
 */
ProjectCityDlg.close = function() {
	parent.layer.close(window.parent.ProjectCityDlg.layerIndex)
}

/**
 * 提交
 */
ProjectCityDlg.citySubmit = function() {
	// 验证数据
	if (!this.validate()) {
		return;
	}
	
	this.clearData();
	this.collectData();
	
	// 提交信息
	var ajax = new $ax(Feng.ctxPath + "/project/cityAdd", function (data) {
		Feng.success("创建成功!");
		window.parent.ProjectCityDlg.table.refresh();
		ProjectCityDlg.close();
	}, function (data) {
		Feng.error("修改失败!", + data.responseJSON.message + "!");
	});
	ajax.set(this.projectCityData);
	ajax.start();
}

/**
 * 验证数据是否合法
 */
ProjectCityDlg.validate = function() {
	$("#projectCityForm").data("bootstrapValidator").resetForm();
	$('#projectCityForm').bootstrapValidator('validate');
	return $("#projectCityForm").data('bootstrapValidator').isValid();
}

$(function() {
	Feng.initValidator("projectCityForm", ProjectCityDlg.validateFields);
})