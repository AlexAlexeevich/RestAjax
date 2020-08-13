$(document).ready(function () {
    allUsers();
    $('#deleteUserButton').click(function () {
        deleteUser();
    });
    $('#editUserButton').click(function () {
        editUser();
    });
});

function allUsers() {
    $('#tableData').empty();
    $.ajax({
        url: '/api/users',
        type: "GET",
        dataType: "json",
        success: function (json) {
            $.each(json, function (key, value) {
                let role = value.roles;
                let rolesUser = "";
                $.each(role, function (k, v) {
                    rolesUser += v.roleToString + " ";
                });
                let tbl_body = "<tr id='tr"+ value.id + "'>" + "<td>" + value.id + "</td>" +
                    "<td>" + value.firstName + "</td>" +
                    "<td>" + value.lastName + "</td>" +
                    "<td>" + value.age + "</td>" +
                    "<td>" + value.email + "</td>" +
                    "<td>" + rolesUser + "</td>" +
                    "<td>" + '<button type="button" class="btn btn-info btn-sm" data-toggle="modal" onclick = "fillUpdateUser(' + value.id + ')">Edit</button>' + "</td>" +
                    "<td>" + '<button type="button" class="delete btn btn-danger btn-sm" data-toggle="modal"  onclick = "fillDeleteUser(' + value.id + ')" id=' + value.id + '>Delete</button>' + "</td>" + "</tr>";
                $("#tableData").append(tbl_body);
            });
        }
    });
}

function fillDeleteUser(id) {
    $.ajax({
        url: '/api/users/' + id,
        type: "GET",
        dataType: "json",
        success: function (json) {
            $('#modalDelete #deleteUserId').val(json.id);
            $('#modalDelete #deleteFirstName').val(json.firstName);
            $('#modalDelete #deleteLastName').val(json.lastName);
            $('#modalDelete #deleteAge').val(json.age);
            $('#modalDelete #deleteEmail').val(json.email);
            $('#deleteRole option').remove(); //очистка ролей в модальном окне
            $.each(json.roles, function (k, v) {
                $("#deleteRole").append($("<option>" + v.roleToString + "</option>"));
            });
            $("#deleteRole").attr("size", $('#deleteRole option').length);
            $("#modalDelete").modal('show');
        }
    });
}
function fillUpdateUser(id) {
    $.ajax({
        url: '/api/users/' + id,
        type: "GET",
        dataType: "json",
        success: function (json) {
            $('#modalEdit #editUserId').val(json.id);
            $('#modalEdit #editFirstName').val(json.firstName);
            $('#modalEdit #editLastName').val(json.lastName);
            $('#modalEdit #editAge').val(json.age);
            $('#modalEdit #editEmail').val(json.email);
            $('#updateRole option').remove(); //очистка ролей в модальном окне
            $("#modalEdit").modal('show');
        }
    });
}

function editUser() {
    let id = $('#modalEdit #editUserId').val();
    let firstName = $('#modalEdit #editFirstName').val();
    let lastName = $('#modalEdit #editLastName').val();
    let age = $('#modalEdit #editAge').val();
    let email = $('#modalEdit #editEmail').val();
    let password = $('#modalEdit #editPassword').val();
    let roles = [];
    let rolesUser = "";
    let selectRoles = document.getElementById("editRole").options;
    for (let i = 0; i < selectRoles.length; i++) {
        if (selectRoles[i].selected) {
            if (selectRoles[i].value === "ROLE_USER") {
                roles.push(JSON.parse('{"id": 2, "role": "ROLE_USER"}'));
                rolesUser+= "USER" + " ";
            } else if (selectRoles[i].value === "ROLE_ADMIN") {
                roles.push(JSON.parse('{"id": 1, "role": "ROLE_ADMIN"}'));
                rolesUser+= "ADMIN" + " ";
            }
        }
    }
    let userEdit = JSON.stringify({
        id: id,
        firstName: firstName,
        lastName: lastName,
        age: age,
        email: email,
        password: password,
        roles: roles
    });
    let user_id = $('#editUserId').val();
    $.ajax({
        url: '/api/users/' + user_id,
        type: "PUT",
        dataType: 'json',
        data: userEdit,
        contentType: "application/json; charset=utf-8",
        complete: function () {
            let myRow = $("#tr" + user_id).index() - 1;
            $("#tr" + user_id).remove();
            let tr = "<tr id='tr"+ user_id + "'>" + "<td>" + user_id + "</td>" +
                "<td>" + firstName + "</td>" +
                "<td>" + lastName + "</td>" +
                "<td>" + age + "</td>" +
                "<td>" + email + "</td>" +
                "<td>" + rolesUser + "</td>" +
                "<td>" + '<button type="button" class="btn btn-info btn-sm" data-toggle="modal" onclick = "fillUpdateUser(' + id + ')">Edit</button>' + "</td>" +
                "<td>" + '<button type="button" class="delete btn btn-danger btn-sm" data-toggle="modal"  onclick = "fillDeleteUser(' + id + ')" id=' + id + '>Delete</button>' + "</td>" + "</tr>";
            $(tr).insertAfter('#tableData tr:eq(' + myRow +')');
        },
    });
    $("#modalEdit").modal('hide');
}

function deleteUser() {
    let user_id = $('#deleteUserId').val();
    $.ajax({
        url: '/api/users/' + user_id,
        type: "DELETE",
        cache: false,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        complete: function () {
            $("#tr" + user_id).remove();
        }
    });
    $("#modalDelete").modal('hide');
}

function addUser() {
    let firstName = $('#inputFirstName').val();
    let lastName = $('#inputLastName').val();
    let age = $('#inputAge').val();
    //let age = document.getElementById("inputAge").value;
    let email = $('#inputEmail').val();
    let password = $('#inputPassword').val();
    let roles = [];
    let selectRoles = document.getElementById("inputRole").options;
    for (let i = 0; i < selectRoles.length; i++) {
        if (selectRoles[i].selected) {
            roles.push(JSON.parse('{"id":"' + selectRoles[i].id + '", "role":"' + selectRoles[i].value + '"}'));
        }
    }
    let user = JSON.stringify({
        firstName: firstName,
        lastName: lastName,
        age: age,
        email: email,
        password: password,
        roles: roles
        //roles: [{ "id": 2, "role": "ROLE_USER" }]
    });
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: '/api/users/',
        type: 'POST',
        data: user,
        dataType: "json",
        success: function (json) {
            let role = json.roles;
            let rolesUser = "";
            $.each(role, function (k, v) {
                rolesUser += v.roleToString + " ";
            });
            let tbl_body = "<tr id='tr" + json.id + "'>" + "<td>" + json.id + "</td>" +
                "<td>" + json.firstName + "</td>" +
                "<td>" + json.lastName + "</td>" +
                "<td>" + json.age + "</td>" +
                "<td>" + json.email + "</td>" +
                "<td>" + rolesUser + "</td>" +
                "<td>" + '<button type="button" class="btn btn-info btn-sm" data-toggle="modal" onclick = "fillUpdateUser(' + json.id + ')">Edit</button>' + "</td>" +
                "<td>" + '<button type="button" class="delete btn btn-danger btn-sm" data-toggle="modal"  onclick = "fillDeleteUser(' + json.id + ')" id=' + json.id + '>Delete</button>' + "</td>" + "</tr>";
            $("#tableData").append(tbl_body);

            $(document).ready(function () {
                $("#addNewUser").find('input:text, input:password, input:file, select, textarea').val('');
            });

        },
    });
}



