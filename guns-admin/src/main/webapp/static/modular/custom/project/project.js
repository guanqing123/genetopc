/**
 * 项目列表管理初始化
 */
var Project = {
    id: "ProjectTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Project.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'projectid', visible: true, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'xmmc', visible: true, align: 'center', valign: 'middle'},
            {title: '适应症', field: 'syz', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'xmyy', visible: true, align: 'center', valign: 'middle'},
            {title: '项目状态', field: 'state', visible: true, align: 'center', valign: 'middle'},
            {title: '截止时间', field: 'jzsj', visible: true, align: 'center', valign: 'middle'},
            {title: '焦点', field: 'jd', visible: true, align: 'center', valign: 'middle'},
            {title: '缩略图key', field: 'sltKey', visible: true, align: 'center', valign: 'middle'},
            {title: '缩略图路径', field: 'sltPath', visible: true, align: 'center', valign: 'middle'},
            {title: '焦点图key', field: 'jdtKey', visible: true, align: 'center', valign: 'middle'},
            {title: '焦点图路径', field: 'jdtPath', visible: true, align: 'center', valign: 'middle'},
            {title: '项目介绍', field: 'xmjs', visible: true, align: 'center', valign: 'middle'},
            {title: '参加标准', field: 'cjbz', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Project.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Project.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加项目列表
 */
Project.openAddProject = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/project/project_add'
    });
    this.layerIndex = index;
    layer.full(index)
};

/**
 * 打开查看项目列表详情
 */
Project.openProjectDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/project/project_update/' + Project.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除项目列表
 */
Project.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/project/delete", function (data) {
            Feng.success("删除成功!");
            Project.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("projectId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询项目列表列表
 */
Project.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Project.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Project.initColumn();
    var table = new BSTable(Project.id, "/project/list", defaultColunms);
    table.setPaginationType("client");
    Project.table = table.init();
});
