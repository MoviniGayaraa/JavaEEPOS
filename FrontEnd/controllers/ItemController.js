//Write down all the item form controllers here

//load all existing items
getAllItems();

//add customer event
$("#btnItem").click(function () {
    if (checkAllItem()){
        saveItem();
    }else{
        alert("Error");
    }

});


//get all customer event
$("#btnGetAllItem").click(function () {
    getAllItems();
});


//bind tr events for getting back data of the rows to text fields

function bindTrEventsItem() {
    $('#tblItem>tr').click(function () {
        //get the selected rows data
        let code = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let price = $(this).children().eq(2).text();
        let qty = $(this).children().eq(3).text();

        //set the selected rows data to the input fields
        $("#txtItemCode").val(code);
        $("#txtItemName").val(name);
        $("#txtItemPrice").val(price);
        $("#txtItemQty").val(qty);

        $("#btnItemDelete").prop("disabled", false);
    })
}

//delete btn event
$("#btnItemDelete").click(function () {
        let id = $("#txtItemCode").val();

        validItem(id).then(function (isValid) {
            if (isValid == false) {
                alert("No such item..please check the ID");
                clearCustomerInputFields();
            } else {
                let consent = confirm("Do you want to delete.?");
                if (consent) {
                    $.ajax({
                        url: "http://localhost:8080/backend/item?itmCode="+id,
                        method: "DELETE",
                        success:function (res) {
                            console.log(res);
                            alert("Item Delete Successfully");
                            clearItemInputFields();
                            getAllItems();
                        },
                        error:function (ob, textStatus, error) {
                            alert(textStatus+" : Error Item Not Delete")
                        }
                    });
                }
            }
        });

});

//update  btn event
$("#btnItemUpdate").click(function () {
    let id = $("#txtItemCode").val();
    let itemName = $("#txtItemName").val();
    let itemPrice = $("#txtItemPrice").val();
    let itemQty = $("#txtItemQty").val();
        validItem(id).then(function (isValid){
            if (isValid) {
                let consent = confirm("Do you really want to update this Item.?");
                if (consent) {
                    var data = {
                        itmCode : id,
                        itmName: itemName,
                        itmPrice: itemPrice,
                        itmQTY:itemQty
                    };
                    console.log(data)
                    $.ajax({
                        url: "http://localhost:8080/backend/item",
                        method: "PUT",
                        data:JSON.stringify(data),
                        contentType:"application/json",
                        success:function (res) {
                            console.log(res);
                            alert("Customer Update Successfully")
                            getAllItems();
                        },
                        error:function (ob, textStatus, error) {
                            alert(textStatus+" : Error Customer Not Update");
                        }
                    });
                    clearItemInputFields();
                }
            } else {
                alert("No such item..please check the ID");
            }
        });
});

//clear btn event
$("#btnItemCancel").click(function () {
    clearItemInputFields();
});


// CRUD operation Functions
function saveItem() {
    alert("save")
    let itemName = $("#txtItemName").val();
    let itemPrice = $("#txtItemPrice").val();
    let itemQty = $("#txtItemQty").val();
    let id = $("#txtItemCode").val();
    validItem(id).then(function (isValid) {
        console.log(isValid)
        if (!isValid) {
            console.log(isValid)
            var data = {
                itmCode : id,
                itmName: itemName,
                itmPrice: itemPrice,
                itmQTY:itemQty
            };
            $.ajax({
                url:"http://localhost:8080/backend/item",
                method: "POST",
                data:JSON.stringify(data),
                contentType:"application/json",
                success:function (res,textStatus,jsXH) {
                    console.log(res);
                    alert("Item Added Successfully");
                    clearItemInputFields();
                    getAllItems();
                },
                error:function (ob, textStatus, error) {
                    alert(textStatus+" : Error Item Not Added")
                }
            });
        }else {
            alert("Item already exits.!");
            clearItemInputFields();
        }
    });
}

function getAllItems() {

    $("#tblItem").empty();
   // $("#modalItemTable").empty();
        $.ajax({
            url:"http://localhost:8080/backend/item?info=getall",
            method: "GET",
            success:function (res) {
                console.log(res);
                for (var r of res) {
                    let row = `<tr>
                     <td>${r.itmCode}</td>
                     <td>${r.itmName}</td>
                     <td>${r.itmPrice}</td>
                     <td>${r.itmQTY}</td>
                    </tr>`;
                    $("#tblItem").append(row);
                    //$("#modalItemTable").append(row);
                    bindTrEventsItem();
                }
            }
    });

}

$("#itmSearch").click(function () {
    let id = $("#txtItemCode").val();
    searchItem(id);
});
function searchItem(id) {

    console.log(id);
    return new Promise(function (resolve, reject) {
        $.ajax({
            url:"http://localhost:8080/backend/item?itmCode="+id+"&info=search",
            method: "GET",
            dataType:"json",
            success:function (res) {
                console.log(res);
                resolve(res);
                $("#txtItemName").val(res.itmName);
                $("#txtItemPrice").val(res.itmPrice);
                $("#txtItemQty").val(res.itmQTY);
            },
            error:function (ob, textStatus, error) {
                resolve(error);
            }
        });
});
}

function validItem(id) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: "http://localhost:8080/backend/item?itmCode=" + id + "&info=search",
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
function loadItemAr(){
    return new Promise(function (resolve, reject) {
        var ar;
        $.ajax({
            url: "http://localhost:8080/backend/item?info=getall",
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




