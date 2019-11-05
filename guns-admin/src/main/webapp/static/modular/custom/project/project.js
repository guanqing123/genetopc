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
 * 初始化switchery
 */
Project.setSwitchery = function (switchElement, checkedBool) {
    if ((checkedBool && !switchElement.isChecked()) || (!checkedBool && switchElement.isChecked())) {
        switchElement.setPosition(true);
        switchElement.handleOnchange(true);
    }
}

/**
 * 初始化表格的列
 */
Project.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        	{
        		title: '缩略图', field: 'sltPath', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row, index) {
                    var h;
                    if (row.sltPath != null) {
                        var url = row.sltPath;
                        h = '<div style="height: 30px;overflow: hidden"><img style="width:30px" src="' + url + '" /></a></div>'
                    }
                    return h;
                }
        	},
            {title: '项目编号', field: 'projectid', visible: true, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'xmmc', visible: true, align: 'center', valign: 'middle'},
            {title: '适应症', field: 'syz', visible: true, align: 'center', valign: 'middle'},
            {title: '项目用药', field: 'xmyy', visible: true, align: 'center', valign: 'middle'},
            {
            	title: '项目状态', field: 'state', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
            		return '<div class="active"><input type="checkbox" id="act_' + row.projectid + '" class="js-switch" /></div>'
            	},
            	events: 'operatActive'
            },
            {title: '截止时间', field: 'jzsj', visible: true, align: 'center', valign: 'middle'},
            {
            	title: '焦点', field: 'jd', visible: true, align: 'center', valign: 'middle',
            	formatter: function (value, row, index) {
            		if (row.jd === 1) {
            			return '是'
            		} else {
            			return '否'
            		}
            	}
            }
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
    var table = new BSTable(Project.id, "/project/list", defaultColunms, function(data) {
    	$.each(data.rows, function (i, v) {
            var acSwitch = new Switchery(document.getElementById('act_' + v.projectid), {color: '#1AB394', size: 'small' });
            if (v.state === 1) {
            	Project.setSwitchery(acSwitch, true);
            }
        });
	});
    table.setPaginationType("server");
    Project.table = table.init();
});
