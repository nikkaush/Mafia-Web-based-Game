function login_user() {
    console.log("login");
    const display_name = document.getElementById("displayname-field").value;
    const password = document.getElementById("password-field").value;
    console.log(display_name);
    console.log(password);

    const bad_return = document.getElementById("login-return-bad");
    
    const options = {
        // headers: {'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Headers': '*'}
        headers: {'x-api-key': env.x_api_key}
      };
    
      var formUrl = env.x_api_url + 'users';
    
      axios.put(formUrl, {
        "Display_Name": display_name,
        "Password": password
      }, options)
      .then((response) => {
        if (response.data.statusCode == 403) {
            bad_return.style.display = "block";
        } else if (response.data.statusCode == 200) {
            document.getElementById("pname").readOnly = true;
            document.getElementById("pname").value = display_name;
            document.getElementById("gameLogin").style.display = "none";
            document.getElementById("joinhost").style.display = "block";
            document.getElementById("login-info").classList.add("logged-in");
            
        }
    
    }, (error) => {
      console.log(error);
    });

    return false;;
}

function go_to_login() {
    console.log("login page");
    document.getElementById("welcome").style.display = "none";
    document.getElementById("gameLogin").style.display = "block";
    return false;
}