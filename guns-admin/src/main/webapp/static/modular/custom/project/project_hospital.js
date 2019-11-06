/**
 * city table
 */
var ProjectCityDlg = {
	id : "ProjectCityTable", // 表格id
	seItem: null,			// 选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectCityDlg.initColumn = function() {
	return [
		{field: 'selectItem', radio: true},
        {title: '主键', field: 'cityid', visible: false, align: 'center', valign: 'middle'},
        {title: '城市', field: 'cityName', visible: true, align: 'center', valign: 'middle'},
        {title: '联系人', field: 'person', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'}
	];
}

/**
 * 打开添加城市页面
 */
ProjectCityDlg.openAddCity = function(projectid) {
    var index = layer.open({
        type: 2,
        title: '添加城市',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/project/project_cityadd/' + projectid
    });
    this.layerIndex = index;
}

/**
 * hospital table
 */
var ProjectCityHospitalDlg = {
	id : "ProjectCityHospitalTable", // 表格id
	seItem: null,					// 选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectCityHospitalDlg.initColumn = function() {
	return [
		{field: 'selectItem', radio: true},
        {title: '主键', field: 'hospitalid', visible: false, align: 'center', valign: 'middle'},
        {title: '医院名称', field: 'hospitalName', visible: true, align: 'center', valign: 'middle'},
        {title: '科室', field: 'departmentName', visible: true, align: 'center', valign: 'middle'}
	];
}

/**
 * 初始化表格的列
 */

$(function () {
    var defaultColunms = ProjectCityDlg.initColumn();
    var table = new BSTable(ProjectCityDlg.id, "/project/cityList", defaultColunms);
    table.setPagination(false);
    table.set('projectid');
    table.setPaginationType("client");
    ProjectCityDlg.table = table.init();
});
