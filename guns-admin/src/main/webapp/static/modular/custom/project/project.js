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
                        h = '<div style="height: 30px;overflow: hidden"><img onmouseover = "Project.showLargeImg(event, this)" onmouseout = "Project.hideLargeImg()" style="width:30px" src="' + url + '" /></a></div>'
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
            		return '<div class="state"><input type="checkbox" id="sta_' + row.projectid + '" class="js-switch" /></div>'
            	},
            	events: 'operatState'
            },
            {title: '截止时间', field: 'jzsj', visible: true, align: 'center', valign: 'middle'},
            {
            	title: '焦点', field: 'jd', visible: true, align: 'center', valign: 'middle',
            	formatter: function (value, row, index) {
            		if (row.jd === 1) {
            			return '是  [ order : '+row.jdOrder+' ] '
            		} else {
            			return '否'
            		}
            	}
            }
    ];
};

// 获取鼠标坐标
Project.mousePosition = function(ev) {
    ev = ev || window.event;
    if(ev.pageX || ev.pageY){ 
        return {x:ev.pageX, y:ev.pageY}; 
    } 
    return { 
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, 
        y:ev.clientY + document.body.scrollTop - document.body.clientTop
    }; 
}

// 显示大图
Project.showLargeImg = function(e, t) {
	var mousePos = this.mousePosition(e);
    var  xOffset = 30;
    var  yOffset = 30;
    $("#tooltip").css("display","block")
    	.css("position","absolute").css("top",(mousePos.y - 90) + "px")
    		.css("left",(mousePos.x) + "px").css("z-index","100")
    		.css("width", "160px").css("height", "160px")
    		.attr("src", $(t).attr("src"));
}

// 隐藏
Project.hideLargeImg = function () {
	$("#tooltip").attr("src","");
	$("#tooltip").css("display","none");
}

/**
 * events操作
 * 是否启用
 */
window.operatState = {
	'click .state': function (e, value, row, index) {
		staRes = {};
		
        function set(key, val) {
        	staRes[key] = val;
        }
        
        set('state', Project.ick(row.projectid));
        set('projectid', row.projectid);
        
        var ajax = new $ax(Feng.ctxPath + "/project/modifyState", function (data) {
			Feng.success("修改成功!");
		}, function (data) {
			Feng.error("修改失败!" + data.responseJSON.message + "!");
			Project.table.refresh();
		});
        ajax.set(staRes);
        ajax.start();
	}
}

/**
 * switchery状态
 */
Project.ick = function (projectid) {
    var isChecked = $("#sta_" + projectid + "").get(0).checked;
    var state;
    if (isChecked) {
        state = 1;
    }
    else state = 0;
    return state;
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
        title: '添加项目',
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
            title: '项目详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/project/project_update/' + Project.seItem.projectid
        });
        this.layerIndex = index;
        layer.full(index)
    }
};

/**
 * 打开项目医院
 */
Project.openProjectHospital = function() {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '开展医院',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Feng.ctxPath + '/project/project_hospital/' + Project.seItem.projectid
        });
        this.layerIndex = index;
        layer.full(index)
	}
}

/**
 * 打开焦点图设置页面
 */
Project.openProjectFocus = function() {
	if (this.check()){
		var index = layer.open({
			type: 2,
			title: '焦点图设置',
			area: ['800px', '420px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/project/project_focus/' + Project.seItem.projectid
		});
		this.layerIndex = index;
		layer.full(index)
	}
}

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
    queryData['jd']=$("input[type='radio'][name='jd']:checked").val();
    Project.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Project.initColumn();
    var table = new BSTable(Project.id, "/project/list", defaultColunms, function(data) {
    	$.each(data.rows, function (i, v) {
            var acSwitch = new Switchery(document.getElementById('sta_' + v.projectid), {color: '#1AB394', size: 'small' });
            if (v.state === 1) {
            	Project.setSwitchery(acSwitch, true);
            }
        });
	});
    table.setPaginationType("server");
    Project.table = table.init();
    
    window.onresize = function() {
    	Project.search();
    }
});
