/**
 * 初始化城市列表详情对话框
 */
var BaseCityHospitalInfoDlg = {
    baseCityInfoData : {},
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
 * 清除数据
 */
BaseCityHospitalInfoDlg.clearData = function() {
    this.baseCityInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BaseCityHospitalInfoDlg.set = function(key, val) {
    this.baseCityInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BaseCityHospitalInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BaseCityHospitalInfoDlg.close = function() {
    parent.layer.close(window.parent.BaseCityHospital.layerIndex);
}

/**
 * 收集添加数据
 */
BaseCityHospitalInfoDlg.collectAddData = function() {
    this
    .set('cityid', $("#baseCityId").val())
    .set('hospitalName')
    .set('departmentName');
}

/**
 * 收集编辑数据
 */
BaseCityHospitalInfoDlg.collectEditData = function() {
    this
    .set('hospitalid', $("#hospitalid").val())
    .set('hospitalName')
    .set('departmentName');
}

/**
 * 提交添加
 */
BaseCityHospitalInfoDlg.addSubmit = function() {
	// 验证数据
	if (!this.validate()){
		return;
	}
	
    this.clearData();
    this.collectAddData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/baseCity/hospital/add", function(data){
        Feng.success("添加成功!");
        window.parent.BaseCityHospital.table.refresh();
        BaseCityHospitalInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.baseCityInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BaseCityHospitalInfoDlg.editSubmit = function() {
	// 验证数据
	if (!this.validate()){
		return;
	}
	
    this.clearData();
    this.collectEditData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/baseCity/hospital/update", function(data){
        Feng.success("修改成功!");
        window.parent.BaseCityHospital.table.refresh();
        BaseCityHospitalInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.baseCityInfoData);
    ajax.start();
}

/**
 * 验证数据是否合法
 */
BaseCityHospitalInfoDlg.validate = function() {
	$("#baseCityHospitalForm").data("bootstrapValidator").resetForm();
	$('#baseCityHospitalForm').bootstrapValidator('validate');
	return $("#baseCityHospitalForm").data('bootstrapValidator').isValid();
}

$(function() {
	Feng.initValidator("baseCityHospitalForm", BaseCityHospitalInfoDlg.validateFields);
});
