/**
 * 医院管理初始化
 */
var BaseCityHospital = {
    id: "BaseCityHospitalTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BaseCityHospital.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'hospitalid', visible: false, align: 'center', valign: 'middle'},
            {title: '城市id', field: 'cityid', visible: false, align: 'center', valign: 'middle'},
            {title: '医院名称', field: 'hospitalName', visible: true, align: 'center', valign: 'middle'},
            {title: '科室', field: 'departmentName', visible: true, align: 'center', valign: 'middle'},
            {title: '创建日期', field: 'createDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BaseCityHospital.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	BaseCityHospital.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加医院
 */
BaseCityHospital.openAddBaseCityHospital = function () {
    var index = layer.open({
        type: 2,
        title: '添加医院',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/baseCity/baseCity_hospitalAdd/' + $("#baseCityId").val()
    });
    this.layerIndex = index;
};

/**
 * 打开查看医院详情
 */
BaseCityHospital.openBaseCityHospitalDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '医院详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/baseCity/baseCity_hospitalUpdate/' + BaseCityHospital.seItem.hospitalid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除医院
 */
BaseCityHospital.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/baseCity/hospital/delete", function (data) {
            Feng.success("删除成功!");
            BaseCityHospital.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("hospitalId",this.seItem.hospitalid);
        ajax.start();
    }
};

/**
 * 查询医院列表
 */
BaseCityHospital.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['baseCityId'] = $("#baseCityId").val();
    BaseCityHospital.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BaseCityHospital.initColumn();
    var table = new BSTable(BaseCityHospital.id, "/baseCity/hospital/list", defaultColunms);
    table.setQueryParams({baseCityId : $("#baseCityId").val()});
    table.setPaginationType("server");
    BaseCityHospital.table = table.init();
});
