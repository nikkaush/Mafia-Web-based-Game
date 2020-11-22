function signup_user() {
    console.log("here");
    const display_name = document.getElementById("displayname-field").value;
    const password = document.getElementById("password-field").value;
    console.log(display_name);
    console.log(password);

    const bad_return = document.getElementById("signup-return-bad");
    const good_return = document.getElementById("signup-return-good");
    const form_div = document.getElementById("signup-form");

    const options = {
        // headers: {'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Headers': '*'}
        headers: {'x-api-key': env.x_api_key}
      };
    
      var formUrl = env.x_api_url + 'test/users';
    
      var gameId ;
    
      axios.post(formUrl, {
        "Display_Name": display_name,
        "Password": password
      }, options)
      .then((response) => {
        console.log(response);
        if (response.data.statusCode == 400) {
            bad_return.style.display = "block";
        } else {
            good_return.style.display = "block";
            bad_return.style.display = "none";
            form_div.style.display = "none";
        }
        // gameId = response.data.playerList[0].gameRoomId;
        // // console.log("here",response.data.playerList[0].gameRoomId);
        // toggleRoomDiv();
      
        // document.getElementById("gameRoomId").innerHTML = gameId;
        // document.getElementById("gameId").value = gameId;
    
        // document.getElementById("isHost").value = "true";
        // // setInterval( function() { refreshGame(gameId); }, 800 );
        // // refreshGame(gameId);
    
    }, (error) => {
      console.log(error);
    });

    return false;
}