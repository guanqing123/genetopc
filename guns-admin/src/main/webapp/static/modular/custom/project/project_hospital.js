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
        {
        	title: '城市', field: 'cityName', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                //return '<a href="#" class="cityName" data-name="cityName" data-type="text" data-pk="'+row.cityid+'" data-url="/project/modifyCity" data-title="输入城市">'+value+'</a>';
            	return '<a href="#" id="city_name_'+row.cityid+'">'+ value +'</a>';
            }
        },
        {
        	title: '联系人', field: 'person', visible: true, align: 'center', valign: 'middle',
        	formatter: function (value, row, index) {
				return '<a href="#" id="person_'+row.cityid+'">'+ value +'</a>';
			}
        },
        {
        	title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle',
        	formatter: function(value, row, index) {
				return '<a href="#" id="phone_'+row.cityid+'">'+ value +'</a>'
			}
        },
        {
        	title: '操作', field: 'operate', visible: true, align: 'center', valign: 'middle',
        	formatter: function(value, row, index) {
				return [
					'<button type="button" class="btn btn-xs btn-primary addhospital" style="margin-bottom: 0px"><i class="fa fa-arrow-right"></i>&nbsp;已添加的医院</button>'
				].join('');
			},
			events: 'operateCity'
        }
	];
}

/**
 * 操作
 */
window.operateCity = {
	'click .addhospital': function(e, value, row, index) {
		e.stopPropagation();
		$('#cityid').val(row.cityid);
		$('#cityName').val(row.cityName + '的医院');
		ProjectCityHospitalDlg.table.refresh({query: {'projectid' : $('#projectid').val(), 'cityid' : $('#cityid').val()}});
	}
}

/**
 * 修改城市
 */
ProjectCityDlg.modifyCity = function(column, pk, tip) {
	var id = '#' + column + '_' + pk;
	$(id).editable({
		type: 'text',
		pk : pk,
		mode : "inline",
		name : column,
		url : Feng.ctxPath + '/project/cityModify',
		validate : function(value) {
			if ($.trim(value) == '') {
				return tip;
			}
		},
		success : function() {
			ProjectCityDlg.table.refresh();
		}
	});
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
 * 检查是否选中
 */
ProjectCityDlg.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	ProjectCityDlg.seItem = selected[0];
        return true;
    }
};

/**
 * 删除城市
 */
ProjectCityDlg.deleteCity = function() {
	if(this.check()){
		layer.confirm('删除城市,将清空城市下的所有医院!', {icon: 3, title:'警告'}, function(index){
				var ajax = new $ax(Feng.ctxPath + '/project/cityDelete',function (data) {
					Feng.success("删除成功!");
					$('#cityid').val("");
					$('#cityName').val("");
					layer.close(index);
					ProjectCityDlg.table.refresh({query: {'projectid' : $('#projectid').val()} });
					ProjectCityHospitalDlg.table.refresh({query: {'projectid' : $('#projectid').val(), 'cityid' : null } });
				}, function (data) {
					Feng.error("删除失败!" + data.responseJSON.message + "!");
					layer.close(index);
				});
				ajax.set("cityid", ProjectCityDlg.seItem.cityid);
				ajax.start();
			}
		);
	}
}

/**====================================================================================**/

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
        {title: '主键', field: 'hospitalid', visible: false, align: 'center', valign: 'middle'},
        {
        	title: '医院名称', field: 'hospitalName', visible: true, align: 'center', valign: 'middle',
        	formatter: function(value, row, index) {
				return '<a href="#" id="hospital_name_'+row.hospitalid+'">'+ value +'</a>'
			}
        },
        {
        	title: '科室', field: 'departmentName', visible: true, align: 'center', valign: 'middle',
        	formatter: function(value, row, index) {
        		return '<a href="#" id="department_name_'+row.hospitalid+'">'+ value +'</a>'
			}
        },
        {
        	title: '操作', field: 'operate', visible: true, align: 'center', valign: 'middle',
        	formatter: function(value, row, index) {
				return [
					'<button type="button" class="btn btn-xs btn-danger deletehospital" style="margin-bottom: 0px"><i class="fa fa-trash"></i>&nbsp;删除</button>'
				].join('');
			},
			events: 'operateHospital'
        }
	];
}

/**
 * 操作
 */
window.operateHospital = {
	'click .deletehospital': function(e, value, row, index) {
		var ajax = new $ax(Feng.ctxPath + '/project/hospitalDelete',function (data) {
            Feng.success("删除成功!");
            ProjectCityHospitalDlg.table.refresh({query: {'projectid' : $('#projectid').val(), 'cityid' : $('#cityid').val()}});
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("hospitalid", row.hospitalid);
        ajax.start();
	}
}

/**
 * 打开添加医院页面
 */
ProjectCityHospitalDlg.openAddHospital = function() {
	var cityid = $('#cityid').val();
	if ( cityid.length < 1) {
		Feng.error('请选择左侧城市');
		return;
	}
    var index = layer.open({
        type: 2,
        title: '添加医院',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/project/project_hospitaladd/'+ cityid
    });
    this.layerIndex = index;
}

/**
 * 修改医院
 */
ProjectCityHospitalDlg.modifyHospital = function(column, pk, tip) {
	var id = '#' + column + '_' + pk;
	$(id).editable({
		type: 'text',
		pk : pk,
		mode : "inline",
		name : column,
		url : Feng.ctxPath + '/project/hospitalModify',
		validate : function(value) {
			if ($.trim(value) == '') {
				return tip;
			}
		},
		success : function() {
			ProjectCityHospitalDlg.table.refresh();
		}
	});
}

/**
 * 初始化表格的列
 */
$(function () {
    // 初始化城市列表
	var defaultColunms = ProjectCityDlg.initColumn();
    var table = new BSTable(ProjectCityDlg.id, "/project/cityList", defaultColunms, function(data) {
    	$.each(data, function(i, v){
    		ProjectCityDlg.modifyCity('city_name', v.cityid, '城市必填');
    		ProjectCityDlg.modifyCity('person', v.cityid, '联系人必填');
    		ProjectCityDlg.modifyCity('phone', v.cityid, '联系电话必填');
    	});
	});
    table.onCheck(function(row) {
    	$('#cityid').val(row.cityid);
		$('#cityName').val(row.cityName + '的医院');
		ProjectCityHospitalDlg.table.refresh({query: {'projectid' : $('#projectid').val(), 'cityid' : $('#cityid').val()}});
	});
    table.setQueryParams({projectid: $("#projectid").val()});
    table.setPaginationType("client");
    ProjectCityDlg.table = table.init();
    
    // 初始化医院列表
    var hdefaultColumns = ProjectCityHospitalDlg.initColumn();
    var htable = new BSTable(ProjectCityHospitalDlg.id, "/project/hospitalList", hdefaultColumns, function(data) {
		$.each(data, function(i, v) {
			ProjectCityHospitalDlg.modifyHospital('hospital_name', v.hospitalid, '医院必填');
			ProjectCityHospitalDlg.modifyHospital('department_name', v.hospitalid, '科室必填');
		});
	});
    htable.setQueryParams({projectid : $('#projectid').val(), cityid: $("#cityid").val()});
    htable.setPaginationType("client");
    ProjectCityHospitalDlg.table = htable.init();
});
