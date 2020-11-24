function update_stats(wonGame, isMafia) {
    if (document.getElementById("login-info").classList.contains("logged-in")) {
        const options = {
            headers: {'x-api-key': env.x_api_key}
        };
        var formUrl = env.x_api_url + 'user-statistics';
        axios.put(formUrl, {
            "pname": document.getElementById("pname").value,
            "won": wonGame,
            "mafia": isMafia,
            "correctVotes": 0
        }, options)
        .then((response) => {
            console.log("stats updated");
        }, (error) => {
          console.log(error);
        });
    }
}