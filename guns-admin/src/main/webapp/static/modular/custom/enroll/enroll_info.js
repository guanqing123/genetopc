/**
 * 初始化报名列表详情对话框
 */
var EnrollInfoDlg = {
    enrollInfoData : {}
};

/**
 * 清除数据
 */
EnrollInfoDlg.clearData = function() {
    this.enrollInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EnrollInfoDlg.set = function(key, val) {
    this.enrollInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EnrollInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EnrollInfoDlg.close = function() {
    parent.layer.close(window.parent.Enroll.layerIndex);
}

/**
 * 收集数据
 */
EnrollInfoDlg.collectData = function() {
    this
    .set('enrollid')
    .set('projectid')
    .set('name')
    .set('telephone')
    .set('sex')
    .set('age')
    .set('disease')
    .set('province')
    .set('city')
    .set('district')
    .set('detailAddress')
    .set('comment')
    .set('state')
    .set('createDate');
}

/**
 * 提交添加
 */
EnrollInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/enroll/add", function(data){
        Feng.success("添加成功!");
        window.parent.Enroll.table.refresh();
        EnrollInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.enrollInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EnrollInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/enroll/update", function(data){
        Feng.success("修改成功!");
        window.parent.Enroll.table.refresh();
        EnrollInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.enrollInfoData);
    ajax.start();
}

$(function() {

});
