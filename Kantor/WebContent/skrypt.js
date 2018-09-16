/**
 * 
 */
var i = 10;
function odliczKF() {
    i--;
    document.getElementById("zegarKF").innerHTML = i;
    if (i == 0) {
        window.location.href = "http://localhost:8080/Kantor/panelKlientaFirmowego";
        return;
    }
    setTimeout("odliczKF()", 1000);
}

var j = 10;
function odliczKP() {
    j--;
    document.getElementById("zegarKP").innerHTML = j;
    if (j == 0) {
        window.location.href = "http://localhost:8080/Kantor/panelKlientaPrywatnego";
        return;
    }
    setTimeout("odliczKP()", 1000);
}

