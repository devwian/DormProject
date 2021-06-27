$('#table').bootstrapTable({
    method: "get",
    url: "/getSD",
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
            field: 'dormId',
            title: '宿舍号'
        }, {
            field: 'studentId',
            title: '学号'
        }, {
            field: 'operate',
            title: '操作',
            width: 200,
            events: {
                'click #edit': function (e,value,row){
                    $('#dormId').val(row.dormId);
                    $('#studentId').val(row.studentId);
                },
                'click #delete':function (e,value,row){
                    delInfo(row.studentId)
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

function addInfo(){
    $.ajax(
        {
            type: 'post',
            url: '/admin/addSD',
            dataType: 'json',
            data: {
                studentId: $('#student').val(),
                dormId: $('#dorm').val(),
            },
            success:function (data){
                if (data==='YES'){
                    alert("添加成功");
                    window.location.reload();
                }else if (data==="FULL") {
                    alert("添加失败，房间人数已满");
                    window.location.reload();
                }else if (data==="NULL") {
                    alert("添加失败，房间不存在");
                    window.location.reload();
                }else {
                    alert("添加失败");
                    window.location.reload();
                }
            }
        }
    )
}
function editInfo() {
    $.ajax(
        {
            type: 'post',
            url: '/editSD',
            dataType: 'json',
            data: {
                studentId: $('#studentId').val(),
                dormId: $('#dormId').val(),
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

function delInfo(studentId){
    <!-- 运行之前需要解绑-->
    $('#warningModal').modal({backdrop:'static',keyboard:false}).off("click",'#delButton').one('click','#delButton',function () {
        $.ajax(
            {
                type: 'post',
                url: "/admin/delSD",
                dataType: 'json',
                data: {
                    studentId: studentId
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