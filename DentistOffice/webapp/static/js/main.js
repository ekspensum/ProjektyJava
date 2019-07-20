/**
 * 
 */

function sentEmailConfirm() {
    alert("Mail has been sent!");
    document.getElementById("subject").value = "";
    document.getElementById("message").value = "";
    document.getElementById("replyMail").value = "";
}

function dateTime() {
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth() + 1;
    if (month < 10)
        month = "0" + month;
    var year = today.getFullYear();
    var hours = today.getHours();
    if (hours < 10)
        hours = "0" + hours;
    var minute = today.getMinutes();
    if (minute < 10)
        minute = "0" + minute;
    var seconde = today.getSeconds();
    if (seconde < 10)
        seconde = "0" + seconde;
    document.getElementById("clock").innerHTML = "Aktualna data i czas: " + day +
        "/" + month + "/" + year + " " + " " + hours + ":" + minute + ":" +
        seconde;
    setTimeout("dateTime()", 1000);
}

function goBack() {
    window.history.back();
}

function validateLoginForm() {
    if (document.loginForm.username.value == "" && document.loginForm.password.value == "") {
        alert("Login i hasło są wymagane!");
        document.loginForm.username.focus();
        return false;
    }
    if (document.loginForm.username.value == "") {
        alert("Proszę podać login!");
        document.loginForm.username.focus();
        return false;
    }
    if (document.loginForm.password.value == "") {
        alert("Proszę podać hasło");
        document.loginForm.password.focus();
        return false;
    }
    return true;
}

function validateSelectChbxPatient() {
    let idChbx = document.forms.selectPatientForm.elements['patientId'];
    if (idChbx.length > 1) {
    	let j = 0;
    	for (i = 0; i < idChbx.length; i++) {
    		if (idChbx[i].checked) {
    			j++;
    		}
    	}
    	if (j != 1) {
    		alert("Proszę wybrać jeden rekord!");
    		return false;
    	} else {
    		return true;
    	}
    } else {
        if (document.selectPatientForm.patientId.checked) {
            return true;
        } else {
            alert("Proszę zaznaczyć wybór pacjenta!");
            return false;
        }
    }
}

function validateInputFieldPatientData() {
    if (document.searchDataPatientForm.patientData.value == "") {
        alert("Proszę wprowadzić dane do wyszukania!");
        document.searchDataPatientForm.patientData.focus();
        return false;
    } else {
        return true;
    }
}

function setValueOnInputFromChbx(chbx_id) {
    if (document.getElementById(chbx_id).checked == true) {
        document.getElementById("in" + chbx_id).value = true;
    } else {
        document.getElementById("in" + chbx_id).value = false;
    }
}

// function setEnableInputFromChbx(chbx_id) {
//     if (document.getElementById(chbx_id).checked == true) {
//         for (let i = 0; i < document.getElementsByName(chbx_id).length; i++)
//             document.getElementsByName(chbx_id)[i].disabled = false;
//         for (let i = 0; i < document.getElementsByName("chbx_" + chbx_id).length; i++)
//             document.getElementsByName("chbx_" + chbx_id)[i].disabled = false;
//     } else {
//         for (let i = 0; i < document.getElementsByName(chbx_id).length; i++)
//             document.getElementsByName(chbx_id)[i].disabled = true;
//         for (let i = 0; i < document.getElementsByName("chbx_" + chbx_id).length; i++)
//             document.getElementsByName("chbx_" + chbx_id)[i].disabled = true;
//     }
// }