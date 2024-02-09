$(document).ready(function () {
    $("#order-add-item").prop("disabled", true);
    $("#btnSubmitOrder").prop("disabled", true);
    $("#order-clear").prop("disabled", true);
    generateOrderId();
    setCusIds();
    setItemIds();
    $('#order-thead').css({
        'width': '100%',
        'display': 'flex'
    });
    $('#order-thead>th').css({
        'flex': '1',
        'max-width': 'calc(100%/5*1)'
    })
});
$(".order-nav").click(function () {
    setCusIds();
    setItemIds();
});
$("#order-clear,.order-nav").click(function () {
    clearAll();
});

function generateOrderId() {
    loadOrderAr().then(function (orderDB) {
        if (orderDB.length === 0) {
            $("#orderID").val("OID-1");
        } else {
            console.log(orderDB[orderDB.length - 1].oid);
            var id = orderDB[orderDB.length - 1].oid.split("-")[1];
            var tempId = parseInt(id, 10);
            if (!isNaN(tempId)) {
                tempId = tempId + 1;
                $("#orderID").val("OID-" + tempId);
            } else {
                console.error("Error converting order ID to a number");
            }
        }
    }).catch(function (error) {
        console.error("Error loading order data:", error);
    });
}

function loadOrderAr() {
    return new Promise(function (resolve, reject) {
        var ar;
        $.ajax({
            url: "http://localhost:8080/backend/order?info=getall",
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

function searchOrder(id) {
    console.log(id);
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: "http://localhost:8080/backend/order?oid=" + id + "&info=search",
            method: "GET",
            dataType: "json",
            success: function (res) {
                console.log(res);
                resolve(res);
            },
            error: function (ob, textStatus, error) {
                resolve(error);
            }
        });
    });
}

function setCusIds() {
    $("#cId").empty();
    loadCusAr().then(function (cusDB) {
        cusDB.forEach(function (e) {
            let id = e.cusID;
            let select = `<option selected>${id}</option>`;
            $("#cId").append(select);
        });
    });

}

$("#cId").change(function () {
    $(this).val($(this).val());
    searchCustomer($(this).val()).then(function (customer) {
        $("#cName").val(customer.cusName);
        $("#cAddress").val(customer.cusAddress);

        setAndTriggerValue($("#cName"), customer.cusName);
        setAndTriggerValue($("#cAddress"), customer.cusAddress);
        dateCheck();
    });
});

function setItemIds() {
    $("#icode").empty();
    loadItemAr().then(function (cusDB) {
        cusDB.forEach(function (e) {
            let id = e.itmCode;
            let select = `<option selected>${id}</option>`;
            $("#icode").append(select);
        });
    });
}

$("#icode").change(function () {
    $(this).val($(this).val());
    searchItem($(this).val()).then(function (item) {
        console.log(item)
        $("#itemName").val(item.itmName);
        $("#price").val(item.itmPrice);
        $("#qtyOnHand").val(item.itmQTY);

        setAndTriggerValue($("#itemName"), item.itmName);
        setAndTriggerValue($("#price"), item.itmPrice);
        setAndTriggerValue($("#qtyOnHand"), item.itmQTY);
        dateCheck();
    });

});

function placeOrder() {
    let order = {
        oid: "",
        date: "",
        cusID: "",
        orderDetails: []
    };

    let cusId = $("#cId").val();
    let date = $("#orderDate").val();
    let OId = $("#orderID").val();

    $('#order-table>tr').each(function () {
        let code = $(this).children().eq(0).text();
        let qty = $(this).children().eq(3).text();
        let price = $(this).children().eq(2).text();
        let orderDetails = {
            oid: OId,
            itmCode: code,
            itmQTY: parseInt(qty),
            itmPrice: parseFloat(price)
        };

        order.orderDetails.push(orderDetails);
    });

    order.oid = OId;
    order.date = date;
    order.cusID = cusId;

    console.log(order)
    $.ajax({
        url: "http://localhost:8080/backend/order",
        method: "POST",
        data: JSON.stringify(order),
        contentType: "application/json",
        success: function (res, textStatus, jsXH) {
            console.log(res);
            alert("Order Added Successfully");
            generateOrderId();
        },
        error: function (ob, textStatus, error) {
            alert(textStatus + " : Error Order Not Added")
        }
    });
}

$("#order-add-item").click(function () {
    let id = $("#icode").val();
    let name = $("#itemName").val();
    let price = $("#price").val();
    let qty = $("#orderQty").val();
    let total = parseFloat(price) * parseFloat(qty);
    let allTotal = 0;
    let itemExists = false;

    $('#order-table>tr').each(function (e) {
        let check = $(this).children().eq(0).text();
        if (id === check) {
            let liQty = $(this).children().eq(3).text();
            let upQty = parseInt(liQty) + parseInt(qty);
            $(this).children().eq(1).text(name);
            $(this).children().eq(2).text(price);
            $(this).children().eq(3).text(upQty);
            $(this).children().eq(4).text(upQty * parseFloat(price));
            itemExists = true;
            return false;
        }
    });

    if (!itemExists) {
        let row = `<tr>
                     <td>${id}</td>
                     <td>${name}</td>
                     <td>${price}</td>
                     <td>${qty}</td>
                     <td>${total}</td>
                    </tr>`;

        $("#order-table").append(row);
        $('#order-table').css({
            'width ': '101.8%',
            'max-height': '80px',
            'overflow-y': 'auto',
            'display': 'table-caption'
        });
        $('#order-table>tr>td').css({
            'flex': '1',
            'max-width': 'calc(100%/5*1)'
        });
        if ($("#order-table>tr").length > 1) {
            $('#order-table>tr').css({
                'width': '100%',
                'display': 'flex'
            });
        } else {
            $('#order-table>tr').css({
                'width': '925px',
                'display': 'flex'
            });
        }

    }
    $('#order-table>tr').each(function (e) {
        let full = $(this).children().eq(4).text();
        allTotal += parseFloat(full);
    });
    $("#total").text(allTotal);
    $("#subtotal").text(allTotal);
});
$("#txtDiscount").on("keydown keyup input", function (e) {
    let total = parseFloat($("#total").text());
    if (total > 0) {
        let discount = $(this).val();
        let fullAm = (total / 100 * discount);
        total -= fullAm;
        $("#subtotal").text(total);
        setAndTriggerValue($("#subtotal"), total);
    }

});
$("#txtCash").on("keydown keyup input", function () {
    cashValidate();
    setBalance();
});
$("#subtotal").on("input", function () {
    cashValidate();
});

function setBalance() {
    let subtotalText = $("#subtotal").text();
    let cashText = $("#txtCash").val();
    let subtotal = parseFloat(subtotalText);
    let cash = parseFloat(cashText);
    if (!isNaN(subtotal) && !isNaN(cash)) {
        let balance = cash - subtotal;
        $("#txtBalance").val(balance.toFixed(2));
    } else {
        $("#txtBalance").val("0");
    }
}

$("#orderDate").on("input", function () {
    dateCheck();
});

function dateCheck() {
    let val = $("#orderDate").val();
    if (val == "") {
        $("#orderDate").css("border", "2px solid red");
        return false
    } else {
        $("#orderDate").css("border", "2px solid green");
        return true;
    }
}

$("#btnSubmitOrder").click(function () {
    let oId = $("#orderID").val();

    if (itemValidate()) {
        searchOrder(oId).then(function (order) {
            if (Object.keys(order).length === 0) {
                if (cashValidate()) {
                    if (dateCheck()) {
                        placeOrder();
                        clearAll();
                        generateOrderId();
                    } else {
                        alert("Insert Date!");
                    }
                } else {
                    alert("Insuficent Credit : Check Cash!");
                }
            } else {
                alert("Order Already Registered");
            }
        });
    } else {
        alert("Please Add Items to Place Order");
    }
});

function loadOrderDetailAr() {
    return new Promise(function (resolve, reject) {
        var ar;
        $.ajax({
            url: "http://localhost:8080/backend/orderDetails",
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

$("#orderID").on("keydown", async function (e) {
    $("#order-table").empty();
    if (e.keyCode === 13) {
        let id = $("#orderID").val();
        let order = await searchOrder(id);
        if (Object.keys(order).length !== 0) {
            $("#order-table").empty();
            let date = order.date;
            let cusId = order.cusID;

            let customer = await searchCustomer(cusId);

            if (Object.keys(customer).length !== 0) {
                let cusName = customer.cusName;
                let address = customer.cusAddress;

                $("#cId").val(cusId);
                $("#cName").val(cusName);
                $("#cAddress").val(address);
                $("#orderDate").val(date);
            }

            let code;
            let qty;
            let unitPrice;
            let itemName;

            loadOrderDetailAr().then(async function (detail) {
                if (detail.length !== 0) {
                    for (var info of detail) {
                        if (info.oid == id) {
                            console.log(info.oid, info.itmCode, info.itmQTY, info.itmPrice);
                            code = info.itmCode;
                            qty = info.itmQTY;
                            unitPrice = info.itmPrice;
                            let res = await searchItem(code);

                            if (Object.keys(res).length !== 0) {
                                itemName = res.itmName;
                                console.log(itemName);
                            }

                            let total = parseFloat(unitPrice) * parseFloat(qty);
                            let row = `<tr>
                     <td>${code}</td>
                     <td>${itemName}</td>
                     <td>${unitPrice}</td>
                     <td>${qty}</td>
                     <td>${total}</td>
                    </tr>`;
                            $("#order-table").append(row);
                            $('#order-table').css({
                                'width ': '101.8%',
                                'max-height': '80px',
                                'overflow-y': 'auto',
                                'display': 'table-caption'
                            });
                            $('#order-table>tr>td').css({
                                'flex': '1',
                                'max-width': 'calc(100%/5*1)'
                            });
                            if ($("#order-table>tr").length > 1) {
                                $('#order-table>tr').css({
                                    'width': '100%',
                                    'display': 'flex'
                                });
                            } else {
                                $('#order-table>tr').css({
                                    'width': '925px',
                                    'display': 'flex'
                                });
                            }
                        }
                    }
                }
            });
        }

    }
});
