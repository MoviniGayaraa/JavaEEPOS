//load all existing customers
getAllCustomers();

//add customer event
$("#btnCustomer").click(function () {

    if (checkAll()){
        saveCustomer();
    }else{
        alert("Error");
    }

});

//get all customer event

$("#btnGetAll").click(function () {
    getAllCustomers();
});


//bind tr events for getting back data of the rows to text fields
function bindTrEventsCustomer() {
    $('#tblCustomer>tr').click(function () {
        //get the selected rows data
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let address = $(this).children().eq(2).text();
        let salary = $(this).children().eq(3).text();

        //set the selected rows data to the input fields
        $("#txtCustomerID").val(id);
        $("#txtCustomerName").val(name);
        $("#txtCustomerAddress").val(address);

        $("#btnCusDelete").prop("disabled", false);

    })
}

//delete btn event
$("#btnCusDelete").click(function () {

        let id = $("#txtCustomerID").val();

        validCustomer(id).then(function (isValid) {
            if (isValid == false) {
                alert("No such Customer..please check the ID");
                clearCustomerInputFields();
            } else {
                let consent = confirm("Do you want to delete.?");
                if (consent) {
                    $.ajax({
                        url: "http://localhost:8080/backend/customer?cusId="+id,
                        method: "DELETE",
                        success:function (res) {
                            console.log(res);
                            alert("Customer Delete Successfully");
                            clearCustomerInputFields();
                            //getAllCustomers();
                        },
                        error:function (ob, textStatus, error) {
                            alert(textStatus+" : Error Customer Not Delete")
                        }
                    });
                }
            }
        });
});

//update  btn event

$("#btnUpdate").click(function () {
        let id = $("#txtCustomerID").val();
        let customerName = $("#txtCustomerName").val();
        let customerAddress = $("#txtCustomerAddress").val();
        validCustomer(id).then(function (isValid){
            if (isValid) {
                let consent = confirm("Do you really want to update this customer.?");
                if (consent) {
                    var data = {
                        cusID : id,
                        cusName: customerName,
                        cusAddress: customerAddress
                    };
                    console.log(data)
                    $.ajax({
                        url: "http://localhost:8080/backend/customer",
                        method: "PUT",
                        data:JSON.stringify(data),
                        contentType:"application/json",
                        success:function (res) {
                            console.log(res);
                            alert("Customer Update Successfully")
                            //getAllCustomers();
                        },
                        error:function (ob, textStatus, error) {
                            alert(textStatus+" : Error Customer Not Update");
                        }
                    });
                    clearCustomerInputFields();
                }
            } else {
                alert("No such Customer..please check the ID");
            }
        });
});


//clear btn event
$("#btn-clear1").click(function () {
    clearCustomerInputFields();
});

$("#cusSearch").click(function () {
    let id = $("#txtCustomerID").val();
    searchCustomer(id);
});

// CRUD operation Functions
function saveCustomer() {
    alert("save")
        let customerName = $("#txtCustomerName").val();
        let customerAddress = $("#txtCustomerAddress").val();

    let id = $("#txtCustomerID").val();
    validCustomer(id).then(function (isValid) {
        console.log(isValid)
        if (!isValid) {
            console.log(isValid)
            var data = {
                cusID : id,
                cusName: customerName,
                cusAddress: customerAddress
            };
            $.ajax({
                url:"http://localhost:8080/backend/customer",
                method: "POST",
                data:JSON.stringify(data),
                contentType:"application/json",
                success:function (res,textStatus,jsXH) {
                    console.log(res);
                    alert("Customer Added Successfully");
                    clearCustomerInputFields();
                    getAllCustomers();
                },
                error:function (ob, textStatus, error) {
                    alert(textStatus+" : Error Customer Not Added")
                }
            });
        }else {
            alert("Customer already exits.!");
            clearCustomerInputFields();
        }
    });
}
function validCustomer(id) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: "http://localhost:8080/backend/customer?cusId=" + id + "&info=search",
            method: "GET",
            dataType: "json",
            success: function (res, textStatus, xhr) {
                console.log(res);
                if(xhr.status===200){
                    resolve(true);
                }else {
                    resolve(false);
                }
            },
            error: function (ob, textStatus, error) {
                resolve(false);
            }
        });
    });
}

function getAllCustomers() {

        $("#tblCustomer").empty();
      //  $("#modalTable").empty();
        $.ajax({
            url:"http://localhost:8080/backend/customer?info=getall",
            method: "GET",
            success:function (res) {
                console.log(res);
                for (var r of res) {
                    let row = `<tr>
                     <td>${r.cusID}</td>
                     <td>${r.cusName}</td>
                     <td>${r.cusAddress}</td>
                    </tr>`;
                    $("#tblCustomer").append(row);
                    //$("#modalTable").append(row);
                    bindTrEventsCustomer();
                }
            }
    });

}
function searchCustomer(id) {
    console.log(id);
    return new Promise(function (resolve, reject) {
        $.ajax({
            url:"http://localhost:8080/backend/customer?cusId="+id+"&info=search",
            method: "GET",
            dataType:"json",
            success:function (res) {
                console.log(res);
                resolve(res);
                $("#txtCustomerName").val(res.cusName);
                $("#txtCustomerAddress").val(res.cusAddress);
            },
            error:function (ob, textStatus, error) {
                resolve(error);
            }
        });
    });
}
function loadCusAr(){
    return new Promise(function (resolve, reject) {
        var ar;
        $.ajax({
            url: "http://localhost:8080/backend/customer?info=getall",
            method: "GET",
            success: function (res) {
                console.log(res);
                ar = res;
                resolve(ar);
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}




