$('#table').bootstrapTable({
    method: "get",
    url: "/admin/getDorm",
    dataType: "json",
    pageSize: "5",
    pageList: "[5,10,15,30]",
    showRefresh: false,
    showFullscreen: false,
    showToggle : false, // 是否显示详细视图和列表视图的切换按钮
    showColumns : false, // 是否显示列操作按钮
    clickToSelect : false, // 是否启用点击选中行
    singleSelect: true, //单行选择
    pagination: true, // 是否分页
    search: true, // 是否显示搜索框
    columns: [
        {
            checkbox: true
        },{
            field: 'dormId',
            title: '宿舍号'
        }, {
            field: 'dormNum',
            title: '宿舍人数上限'
        }, {
            field: 'dormTel',
            title: '宿舍电话'
        },{
            field: 'className',
            title: '宿舍班级',
        },{
            field: 'operate',
            title: '操作',
            width: 200,
            events: {
                'click #edit': function (e,value,row){
                    $('#dormId').val(row.dormId);
                    $('#dormNum').val(row.dormNum);
                    $('#dormTel').val(row.dormTel);
                    $('#date').val(row.className);
                },
                'click #delete':function (e,value,row){
                    delInfo(row.dormId)
                }
            },
            formatter:function (){
                let result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">编辑</button>';
                result += '<button id="delete" class="btn btn-danger" data-toggle="modal" style="margin-left:10px;">删除</button>';
                return result;
            },
        }]
})

function delInfo(dormId){
    $('#warningModal').modal({backdrop:'static',keyboard:false}).off("click",'#delButton').one('click','#delButton',function (){
        $.ajax(
            {
                type: 'post',
                url: '/admin/delDorm',
                dataType: 'json',
                data:{
                    dormId: dormId
                },
                success:function (data){
                    if (data==="YES"){
                        alert('删除成功');
                        window.location.reload()
                    }else{
                        alert('删除失败');
                        window.location.reload();
                    }
                }
            }
        )
    })
}

function editInfo() {
    $.ajax(
        {
            type: 'post',
            url: '/admin/editDorm',
            dataType: 'json',
            data: {
                dormId: $('#dormId').val(),
                dormNum: $('#dormNum').val(),
                dormTel: $('#dormTel').val(),
                className: $('#className').val()
            },
            success:function (data){
                if (data==='YES'){
                    alert("更改成功");
                    window.location.reload();
                }else {
                    alert("更改失败");
                    window.location.reload();
                }
            }
        }
    )
}

function addInfo(){
    $.ajax(
        {
            type: 'post',
            url: '/admin/addDorm',
            dataType: 'json',
            data: {
                dormId: $('#id').val(),
                dormNum: $('#num').val(),
                dormTel: $('#tel').val(),
                className: $('#class').val()
            },
            success:function (data){
                if (data==='YES'){
                    alert("添加成功");
                    window.location.reload();
                }else {
                    alert("添加失败");
                    window.location.reload();
                }
            }
        }
    )
}
