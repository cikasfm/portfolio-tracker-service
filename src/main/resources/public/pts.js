$(document).ready(() => {

    const auth = {
        authenticated: false,
        provider: "github"
    };

    $.ajaxSetup({
        beforeSend: function (xhr, settings) {
            if (settings.type == 'POST' || settings.type == 'PUT'
                || settings.type == 'DELETE') {
                if (!(/^http:.*/.test(settings.url) || /^https:.*/
                    .test(settings.url))) {
                    // Only send the token to relative URLs i.e. locally.
                    xhr.setRequestHeader("X-XSRF-TOKEN",
                        Cookies.get('XSRF-TOKEN'));
                }
            }
        }
    });

    const tplHoldings = Handlebars.compile(document.getElementById('tpl-holdings').innerHTML);
    const tplTrades = Handlebars.compile(document.getElementById('tpl-trades').innerHTML);

    let firstTime = true;

    let interval;

    const updateUI = () => fetch('/api/portfolio')
        .then(response => {
            if (response.status == 200) {
                return response.json();
            }
            throw new Error(response.text());
        })
        .then(data => {
            console.log(data);
            $('#portfolio').html(tplHoldings(data));
            if (firstTime) {
                $('#purchases').html(tplTrades({trades: data.purchases}));
                $('#liquidations').html(tplTrades({trades: data.liquidations}));
                firstTime = false;
            }
        })
        .catch(error => {
            console.error("Fetch error:", JSON.stringify(error));
            auth.authenticated = false;
            clearInterval(interval);
        });

    $.get("/api/user", function (data) {
        $("#user").html(data.name);
        $(".unauthenticated").hide();
        $(".authenticated").show();
        auth.authenticated = true;
        interval = setInterval(updateUI, 500);
    });

    const postLogout = () => {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
        clearInterval(interval);
    };

    $('#logout').click(() => $.post("/logout")
        .then(postLogout)
        .catch(postLogout));

});