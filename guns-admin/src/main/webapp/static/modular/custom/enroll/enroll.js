/**
 * 报名列表管理初始化
 */
var Enroll = {
    id: "EnrollTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Enroll.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'enrollid', visible: false, align: 'center', valign: 'middle'},
            {title: '项目id', field: 'projectid', visible: false, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'xmmc', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'telephone', visible: false, align: 'center', valign: 'middle'},
            {title: '性别', field: 'sex', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
            {title: '所患疾病', field: 'disease', visible: true, align: 'center', valign: 'middle'},
            {
            	title: '所在地区', field: 'address', viaible: true, align: 'center', valign: 'middle',
            	formatter: function(index, row) {
            		return row['province'] +' '+ row['city'] +' '+ row['district'];
            	}
            },
            {
            	title: '状态', field: 'state', visible: true, align: 'center', valign: 'middle',
            	formatter: function(index, row) {
					var state;
					switch (row['state']) {
					case 0:
						state = '等待审核';
						break;
					case 1:
						state = '已通过';
						break;
					case 2:
						state = '已被拒';
						break;
					default:
						break;
					}
					return state;
				},
				cellStyle: function(index, row) {
					var style;
					switch (row['state']) {
					case 0:
						style = 'bg-yellow';
						break;
					case 1:
						style = 'bg-green';
						break;
					case 2:
						style = 'bg-red';
						break;
					default:
						break;
					}
					return {
				        classes: style
				    };
				}
            },
            {title: '创建日期', field: 'createDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Enroll.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Enroll.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加报名列表
 */
Enroll.openAddEnroll = function () {
    var index = layer.open({
        type: 2,
        title: '添加报名列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/enroll/enroll_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看报名列表详情
 */
Enroll.openEnrollDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '报名列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/enroll/enroll_update/' + Enroll.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除报名列表
 */
Enroll.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/enroll/delete", function (data) {
            Feng.success("删除成功!");
            Enroll.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("enrollId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询报名列表列表
 */
Enroll.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Enroll.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Enroll.initColumn();
    var table = new BSTable(Enroll.id, "/enroll/list", defaultColunms);
    table.setPaginationType("server");
    Enroll.table = table.init();
});
