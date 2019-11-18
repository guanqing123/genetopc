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
            {
            	title: '性别', field: 'sex', visible: true, align: 'center', valign: 'middle',
            	formatter: function(index, row) {
            		var sex;
					switch (row['sex']) {
					case '男':
						sex = '<i class="fa fa-male"></i>';
						break;
					case '女':
						sex = '<i class="fa fa-female"></i>';
						break;
					default:
						break;
					}
					return sex;
				}
            },
            {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
            {title: '所患疾病', field: 'disease', visible: true, align: 'center', valign: 'middle'},
            {
            	title: '所在地区', field: 'address', visible: false, align: 'center', valign: 'middle',
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
						state = '<span class="wait">等待审核</span>';
						break;
					case 1:
						state = '<span class="pass">已通过</span>';
						break;
					case 2:
						state = '<span class="refuse">已被拒</span>';
						break;
					default:
						break;
					}
					return state;
				}
            },
            {
            	title: '创建日期', field: 'createDate', visible: true, align: 'center', valign: 'middle',
            	formatter: function(index, row) {
					return moment(row['createDate']).fromNow();
				}
            }
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
 * 打开报名审核页面
 */
Enroll.openEnrollCheck = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '报名审核',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/enroll/enroll_check/' + Enroll.seItem.enrollid
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 查询报名列表列表
 */
Enroll.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['state']=$("input[type='radio'][name='state']:checked").val();
    Enroll.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Enroll.initColumn();
    var table = new BSTable(Enroll.id, "/enroll/list", defaultColunms);
    table.setPaginationType("server");
    Enroll.table = table.init();
});
