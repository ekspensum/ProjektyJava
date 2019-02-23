function callServlet() {

    var xmlHttp = new XMLHttpRequest();
    var currency;
    var usd, eur, chf;

    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            currency = xmlHttp.responseXML.getElementsByTagName("currency")[0];
            usd = currency.getElementsByTagName("USD")[0];
            eur = currency.getElementsByTagName("EUR")[0];
            chf = currency.getElementsByTagName("CHF")[0];
            document.getElementById("usdBid").innerHTML = usd.getElementsByTagName("bid")[0].childNodes[0].nodeValue;
            document.getElementById("usdAsk").innerHTML = usd.getElementsByTagName("ask")[0].childNodes[0].nodeValue;
            document.getElementById("eurBid").innerHTML = eur.getElementsByTagName("bid")[0].childNodes[0].nodeValue;
            document.getElementById("eurAsk").innerHTML = eur.getElementsByTagName("ask")[0].childNodes[0].nodeValue;
            document.getElementById("chfBid").innerHTML = chf.getElementsByTagName("bid")[0].childNodes[0].nodeValue;
            document.getElementById("chfAsk").innerHTML = chf.getElementsByTagName("ask")[0].childNodes[0].nodeValue;
        }
    };

    xmlHttp.open("POST", "/Kantor/GetRateServlet", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.responseType = "document";
    xmlHttp.send();

    setTimeout("callServlet()", 30000);
}