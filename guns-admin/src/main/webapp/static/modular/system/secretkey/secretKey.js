/**
 * 密钥管理管理初始化
 */
var SecretKey = {
    id: "SecretKeyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SecretKey.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '用户', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: 'AppName', field: 'appName', visible: true, align: 'center', valign: 'middle'},
            {title: 'AppKey', field: 'appKey', visible: true, align: 'center', valign: 'middle'},
            {title: 'AppSecret', field: 'appSecret', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SecretKey.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SecretKey.seItem = selected[0];
        return true;
    }
};
/**
 * 冻结密钥
 */
SecretKey.freezeAccount = function () {
	if (this.check()) {
        var appKey = this.seItem.appKey;
        var ajax = new $ax(Feng.ctxPath + "/secretKey/freeze", function (data) {
            Feng.success("冻结成功!");
            SecretKey.table.refresh();
        }, function (data) {
            Feng.error("冻结失败!" + data.responseJSON.message + "!");
        });
        ajax.set("appKey", appKey);
        ajax.start();
    }
};
/**
 * 解除冻结
 */
SecretKey.unfreeze = function () {
	if (this.check()) {
        var appKey = this.seItem.appKey;
        var ajax = new $ax(Feng.ctxPath + "/secretKey/unfreeze", function (data) {
            Feng.success("解除冻结成功!");
            SecretKey.table.refresh();
        }, function (data) {
            Feng.error("解除冻结失败!");
        });
        ajax.set("appKey", appKey);
        ajax.start();
    }
};

/**
 * 点击添加密钥管理
 */
SecretKey.openAddSecretKey = function () {
    var index = layer.open({
        type: 2,
        title: '添加密钥管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/secretKey/secretKey_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看密钥管理详情
 */
SecretKey.openSecretKeyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '密钥管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/secretKey/secretKey_update/' + SecretKey.seItem.appKey
        });
        this.layerIndex = index;
    }
};

/**
 * 删除密钥管理
 */
SecretKey.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/secretKey/delete", function (data) {
            Feng.success("删除成功!");
            SecretKey.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("appKey",this.seItem.appKey);
        ajax.start();
    }
};

/**
 * 查询密钥管理列表
 */
SecretKey.search = function () {
    var queryData = {};
    queryData['appName'] = $("#appName").val();
    SecretKey.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SecretKey.initColumn();
    var table = new BSTable(SecretKey.id, "/secretKey/list", defaultColunms);
    table.setPaginationType("client");
    SecretKey.table = table.init();
});
