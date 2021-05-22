$(document).ready(() => {

    const tplHoldings = Handlebars.compile(document.getElementById('tpl-holdings').innerHTML);
    const tplTrades = Handlebars.compile(document.getElementById('tpl-trades').innerHTML);

    fetch('/api/portfolio')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            $('#portfolio').html(tplHoldings(data));
            $('#purchases').html(tplTrades({trades: data.purchases}));
            $('#liquidations').html(tplTrades({trades: data.liquidations}));
        });

});