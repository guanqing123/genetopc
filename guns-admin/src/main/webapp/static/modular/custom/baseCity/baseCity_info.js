/**
 * 初始化城市列表详情对话框
 */
var BaseCityInfoDlg = {
    baseCityInfoData : {},
	validateFields : {
		provinceName : {
			validators: {
				notEmpty: {
					message: '省不能为空'
				}
			}
		},
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
 * 清除数据
 */
BaseCityInfoDlg.clearData = function() {
    this.baseCityInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BaseCityInfoDlg.set = function(key, val) {
    this.baseCityInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BaseCityInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BaseCityInfoDlg.close = function() {
    parent.layer.close(window.parent.BaseCity.layerIndex);
}

/**
 * 收集数据
 */
BaseCityInfoDlg.collectData = function() {
    this
    .set('cityid')
    .set('cityName')
    .set('provinceName')
    .set('person')
    .set('phone');
}

/**
 * 提交添加
 */
BaseCityInfoDlg.addSubmit = function() {
	// 验证数据
	if (!this.validate()){
		return;
	}
	
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/baseCity/add", function(data){
        Feng.success("添加成功!");
        window.parent.BaseCity.table.refresh();
        BaseCityInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.baseCityInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BaseCityInfoDlg.editSubmit = function() {
	// 验证数据
	if (!this.validate()){
		return;
	}
	
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/baseCity/update", function(data){
        Feng.success("修改成功!");
        window.parent.BaseCity.table.refresh();
        BaseCityInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.baseCityInfoData);
    ajax.start();
}

/**
 * 验证数据是否合法
 */
BaseCityInfoDlg.validate = function() {
	$("#baseCityForm").data("bootstrapValidator").resetForm();
	$('#baseCityForm').bootstrapValidator('validate');
	return $("#baseCityForm").data('bootstrapValidator').isValid();
}

$(function() {
	Feng.initValidator("baseCityForm", BaseCityInfoDlg.validateFields);
});
