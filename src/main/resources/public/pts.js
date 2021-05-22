$(document).ready(() => {

    const tplHoldings = Handlebars.compile(document.getElementById('tpl-holdings').innerHTML);

    /**
     *
     const renderProducts = ( products = [] ) => {
    const $products = $( '#results-products' );
    products.forEach( item => item.product.imgsvg = imgSvgSrc( { height: 400, width: 300, text: 'placeholder' } ) );
    products.map( item => $products.append( productCardTpl( item.product ) ) );
}
     */

    fetch('/api/portfolio')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            $('#portfolio').html(tplHoldings(data));
        });

});