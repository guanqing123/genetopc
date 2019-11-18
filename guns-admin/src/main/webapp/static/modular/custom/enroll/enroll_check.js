/**
 * 初始化报名列表详情对话框
 */
var EnrollCheckDlg = {
    enrollCheckData : {}
};

/**
 * 清除数据
 */
EnrollCheckDlg.clearData = function() {
    this.enrollCheckData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EnrollCheckDlg.set = function(key, val) {
    this.enrollCheckData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EnrollCheckDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EnrollCheckDlg.close = function() {
    parent.layer.close(window.parent.Enroll.layerIndex);
}

/**
 * 收集数据
 */
EnrollCheckDlg.collectData = function() {
    this
    .set('enrollid')
    .set('checkState', $("input[type='radio'][name='check_state']:checked").val())
    .set('checkComment', $("#check_comment").val());
}

/**
 * 提交修改
 */
EnrollCheckDlg.checkSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/enroll/check", function(data){
        Feng.success("修改成功!");
        window.parent.Enroll.table.refresh();
        EnrollCheckDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.enrollCheckData);
    ajax.start();
}

$(function() {

});
