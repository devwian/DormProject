$('#table').bootstrapTable({
    method: "get",
    url: "/admin/getStudent",
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
        field: 'studentId',
        title: '学号'
    }, {
        field: 'studentName',
        title: '姓名'
    }, {
        field: 'studentTel',
        title: '电话'
    },{
      field: 'date',
      title: '注册时间',
    },{
        field: 'operate',
        title: '操作',
        width: 200,
            events: {
                'click #edit': function (e,value,row){
                    $('#studentId').val(row.studentId);
                    $('#studentName').val(row.studentName);
                    $('#studentTel').val(row.studentTel);
                    $('#date').val(row.date);
                },
                'click #delete':function (e,value,row){
                    delInfo(row.studentId)
                }
            },
            formatter:function (){
                let result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">编辑</button>';
                result += '<button id="delete" class="btn btn-danger" data-toggle="modal" data-target="#" style="margin-left:10px;">删除</button>';
                return result;
            },
        }]
})

function editInfo() {
    $.ajax(
        {
            type: 'post',
            url: '/admin/editStudent',
            dataType: 'json',
            data: {
                studentId: $('#studentId').val(),
                studentName: $('#studentName').val(),
                studentTel: $('#studentTel').val()
            },
            success:function (data){
                if (data==='YES'){
                    alert("更改成功");
                    window.location.reload();
                }else {
                    alert("更改失败");
                }
            }
        }
    )
}


function delInfo(studentId) {
    $('#warningModal').modal({backdrop:'static',keyboard:false}).off("click",'#delButton').one('click','#delButton',function (){

        $.ajax(
        {
            type: 'post',
            url: '/admin/delStudent',
            dataType: 'json',
            data:{
                studentId: studentId
            },
            success:function (data){
                    if (data==="YES"){
                        alert("删除成功");
                        window.location.reload();
                    }else{
                        alert('删除失败');
                        window.location.reload();
                    }
                }
            }
        )
    })

}

function addInfo(){
    $.ajax(
        {
            type: 'post',
            url: '/admin/addStudent',
            dataType: 'json',
            data: {
                studentId: $('#id').val(),
                studentName: $('#name').val(),
                studentTel: $('#tel').val()
            },
            success:function (data){
                if (data==='YES'){
                    alert("添加成功，默认密码为123456");
                    window.location.reload();
                }else {
                    alert("添加失败");
                    window.location.reload();
                }
            }
        }
    )
}
