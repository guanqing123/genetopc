/**
 * 城市管理初始化
 */
var BaseCity = {
    id: "BaseCityTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BaseCity.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'cityid', visible: false, align: 'center', valign: 'middle'},
            {title: '省', field: 'provinceName', visible: true, align: 'center', valign: 'middle'},
            {title: '城市', field: 'cityName', visible: true, align: 'center', valign: 'middle'},
            {title: '联系人', field: 'person', visible: true, align: 'center', valign: 'middle'},
            {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '创建日期', field: 'createDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BaseCity.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BaseCity.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加城市
 */
BaseCity.openAddBaseCity = function () {
    var index = layer.open({
        type: 2,
        title: '添加城市',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/baseCity/baseCity_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看城市详情
 */
BaseCity.openBaseCityDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '城市详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/baseCity/baseCity_update/' + BaseCity.seItem.cityid
        });
        this.layerIndex = index;
    }
};

/**
 * 打开医院列表
 */
BaseCity.openBaseCityHospital = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: BaseCity.seItem.cityName + ' > 医院列表',
			area: ['800px', '420px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/baseCity/baseCity_hospital/' + BaseCity.seItem.cityid
		});
		this.layerIndex = index;
		layer.full(index)
	}
}

/**
 * 删除城市
 */
BaseCity.delete = function () {
    if (this.check()) {
		layer.confirm('删除城市,将同步删除城市下的医院!', {icon: 3, title:'提示'}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/baseCity/delete", function (data) {
	            Feng.success("删除成功!");
	            BaseCity.table.refresh();
	            layer.close(index);
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("baseCityId",BaseCity.seItem.cityid);
	        ajax.start();
		});
    }
};

/**
 * 查询城市列表
 */
BaseCity.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BaseCity.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BaseCity.initColumn();
    var table = new BSTable(BaseCity.id, "/baseCity/list", defaultColunms);
    table.setPaginationType("server");
    BaseCity.table = table.init();
});
