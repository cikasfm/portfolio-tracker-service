$(document).ready(() => {

    const tplHoldings = Handlebars.compile(document.getElementById('tpl-holdings').innerHTML);
    const tplTrades = Handlebars.compile(document.getElementById('tpl-trades').innerHTML);

    let firstTime = true;

    const updateUI = () => fetch('/api/portfolio')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            $('#portfolio').html(tplHoldings(data));
            if ( firstTime ) {
                $('#purchases').html(tplTrades({trades: data.purchases}));
                $('#liquidations').html(tplTrades({trades: data.liquidations}));
                firstTime = false;
            }
        });

    setInterval(updateUI,500);

});