$('#table').bootstrapTable({
    method: "get",
    url: "/getProperty",
    dataType: "json",
    pageSize: "5",
    pageList: "[5,10,15,30]",
    showRefresh: false,
    showFullscreen: false,
    // 是否显示详细视图和列表视图的切换按钮
    showToggle : false,
    // 是否显示列操作按钮
    showColumns : false,
    // 是否启用点击选中行
    clickToSelect : false,
    //单行选择
    singleSelect: true,
    // 是否分页
    pagination: true,
    // 是否显示搜索框
    search: true,
    sortable: true,
    columns: [
        {
            checkbox: true
        }, {
            field: 'propertyId',
            title: '财产号'
        },{
            field: 'dormId',
            title: '宿舍号'
        }, {
            field: 'propertyName',
            title: '财产名'
        }, {
            field: 'operate',
            title: '操作',
            width: 200,
            events: {
                'click #edit': function (e,value,row){
                    $('#propertyId').val(row.propertyId);
                    $('#dormId').val(row.dormId);
                    $('#propertyName').val(row.propertyName);
                },
                'click #delete':function (e,value,row){
                    delInfo(row.propertyId)
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

function editProperty() {
    $.ajax(
        {
            type: 'post',
            url: '/editProperty',
            dataType: 'json',
            data: {
                propertyId: $('#propertyId').val(),
                dormId: $('#dormId').val(),
                propertyName: $('#propertyName').val(),
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
            url: '/addProperty',
            dataType: 'json',
            data: {
                propertyId: $('#Id').val(),
                dormId: $('#Dorm').val(),
                propertyName: $('#Name').val(),
            },
            success:function (data){
                if (data==='YES'){
                    alert("添加成功");
                    window.location.reload();
                }else if(data==="EXIST"){
                    alert("财产号已存在")
                    window.location.reload();
                }
                else {
                    alert("添加失败");
                    window.location.reload();
                }
            }
        }
    )
}


function delInfo(propertyId){
    <!-- 运行之前需要解绑-->
    $('#warningModal').modal({backdrop:'static',keyboard:false}).off("click",'#delButton').one('click','#delButton',function () {
        $.ajax(
            {
                type: 'post',
                url: "/delProperty",
                dataType: 'json',
                data: {
                    propertyId: propertyId
                },
                success: function (data) {
                    if (data === "YES") {
                        alert("删除成功");
                        window.location.reload();
                    } else {
                        alert('删除失败');
                        window.location.reload();
                    }
                }
            }
        )
    })
}