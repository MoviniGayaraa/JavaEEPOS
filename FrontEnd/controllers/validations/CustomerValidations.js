const CUS_ID_REGEX = /^C00-(0*[1-9]\d{0,2})$/;
const CUS_NAME_REGEX = /^[A-Za-z ]{5,}$/;
const CUS_ADDRESS_REGEX = /^[A-Za-z0-9 ]{5,}$/;

let c_vArray = new Array();
c_vArray.push({field: $("#txtCustomerID"), regEx: CUS_ID_REGEX});
c_vArray.push({field: $("#txtCustomerName"), regEx: CUS_NAME_REGEX});
c_vArray.push({field: $("#txtCustomerAddress"), regEx: CUS_ADDRESS_REGEX});

function clearCustomerInputFields() {
    $("#txtCustomerID,#txtCustomerName,#txtCustomerAddress").val("");
    $("#txtCustomerID,#txtCustomerName,#txtCustomerAddress").css("border", "1px solid #ced4da");
    $("#txtCustomerID").focus();
    setBtn();
}

setBtn();

$("#txtCustomerID,#txtCustomerName,#txtCustomerAddress").on("keydown keyup", function (e) {

    let indexNo = c_vArray.indexOf(c_vArray.find((c) => c.field.attr("id") == e.target.id));

    if (e.key == "Tab") {
        e.preventDefault();
    }

    checkValidations(c_vArray[indexNo]);

    setBtn();

    if (e.key == "Enter") {

        if (e.target.id != c_vArray[c_vArray.length - 1].field.attr("id")) {

            if (checkValidations(c_vArray[indexNo])) {
                c_vArray[indexNo + 1].field.focus();
            }
        } else {
            if (checkValidations(c_vArray[indexNo])) {
                saveCustomer();
            }
        }
    }
});


function checkValidations(object) {
    if (object.regEx.test(object.field.val())) {
        setBorder(true, object)
        return true;
    }
    setBorder(false, object)
    return false;
}

function setBorder(bol, ob) {
    if (!bol) {
        if (ob.field.val().length >= 1) {
            ob.field.css("border", "2px solid red");
        } else {
            ob.field.css("border", "1px solid #ced4da");
        }
    } else {
        if (ob.field.val().length >= 1) {
            ob.field.css("border", "2px solid green");
        } else {
            ob.field.css("border", "1px solid #ced4da");
        }
    }

}

function checkAll() {
    for (let i = 0; i < c_vArray.length; i++) {
        if (!checkValidations(c_vArray[i])) return false;
    }
    return true;
}

function setBtn() {
    //setClBtn();
    $("#btnCustomer").prop("disabled", true);
    $("#btnCusDelete").prop("disabled", true);
    $("#btnUpdate").prop("disabled", true);
    $("#cusSearch").prop("disabled", true);
    let id = $("#txtCustomerID").val();
    if ($("#txtCustomerID").val() != "" && CUS_ID_REGEX.test($("#txtCustomerID").val())){
        $("#cusSearch").prop("disabled", false);
    }else {
        $("#cusSearch").prop("disabled", true);
    }
    validCustomer(id)
        .then(function (isValid) {
            if (isValid) {
                $("#btnCusDelete").prop("disabled", false);
                if (checkAll()) {
                    $("#btnUpdate").prop("disabled", false);
                    $("#btnCusDelete").prop("disabled", false);
                } else {
                    $("#btnUpdate").prop("disabled", true);
                }
            }else {
                $("#btnCusDelete").prop("disabled", true);
                $("#btnUpdate").prop("disabled", true);
                if (checkAll()) {
                    $("#btnCustomer").prop("disabled", false);
                } else {
                    $("#btnCustomer").prop("disabled", true);
                }
            }
        })
        .catch(function () {
            $("#btnCusDelete").prop("disabled", true);
            $("#btnUpdate").prop("disabled", true);
            if (checkAll()) {
                $("#btnCustomer").prop("disabled", false);
            } else {
                $("#btnCustomer").prop("disabled", true);
            }
        });
}

