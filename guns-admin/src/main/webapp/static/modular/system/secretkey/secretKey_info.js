/**
 * 初始化密钥管理详情对话框
 */
var SecretKeyInfoDlg = {
    secretKeyInfoData : {},
    validateFields: {
    	userSel: {
            validators: {
                notEmpty: {
                    message: '云端用户不能为空'
                }
            }
        },
        appName: {
            validators: {
                notEmpty: {
                    message: 'AppName不能为空'
                }
            }
        },
    }
};
/**
 * 点击用户input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
SecretKeyInfoDlg.onClickUser = function (e, treeId, treeNode) {
    $("#userSel").attr("value", instance.getSelectedVal());
    $("#userId").attr("value", treeNode.id);
};
/**
 * 显示用户选择的树
 *
 * @returns
 */
SecretKeyInfoDlg.showCloudUserTree = function () {
    var cityObj = $("#userSel");
    var cityOffset = $("#userSel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
};
/**
 * 隐藏用户选择的树
 */
SecretKeyInfoDlg.hideUserSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};
/**
 * 清除数据
 */
SecretKeyInfoDlg.clearData = function() {
    this.secretKeyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SecretKeyInfoDlg.set = function(key, val) {
    this.secretKeyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SecretKeyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SecretKeyInfoDlg.close = function() {
    parent.layer.close(window.parent.SecretKey.layerIndex);
}

/**
 * 收集数据
 */
SecretKeyInfoDlg.collectData = function() {
    this
    .set('userId')
    .set('appKey')
    .set('appSecret')
    .set('status')
    .set('appName');
}

/**
 * 提交添加
 */
SecretKeyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/secretKey/add", function(data){
        Feng.success("添加成功!");
        window.parent.SecretKey.table.refresh();
        SecretKeyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.secretKeyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SecretKeyInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/secretKey/update", function(data){
        Feng.success("修改成功!");
        window.parent.SecretKey.table.refresh();
        SecretKeyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.secretKeyInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
    	SecretKeyInfoDlg.hideUserSelectTree();
    }
}

$(function() {
	Feng.initValidator("secretKeyForm", SecretKeyInfoDlg.validateFields);
	var ztree = new $ZTree("treeDemo", "/mgr/cloudUserTree");
    ztree.bindOnClick(SecretKeyInfoDlg.onClickUser);
    ztree.init();
    instance = ztree;
});
