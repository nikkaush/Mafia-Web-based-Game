<!DOCTYPE html>    
<html>    
<head>    
    <title>Welcome to mafia</title>    
    <link rel="stylesheet" type="text/css" href="css/style.css">    
</head>    
<body>    
    <h2>Welcome to the game night!</h2><br>    
    <div class="login">    
    <form id="guestLogin" method="get" action="/hostOrJoinGame">    
         
        <input type="submit" id="log" value="Play as a guest" >       
        <br><br>    
        
    </form>
    
    <form id="login" method="get" action="/login">  
        <input type="button" id="log" value="Sign Up">       
        <br><br>  
        <input type="button" id="log" onclick="location.href='login.html';" value="Log In ">       
        <br><br>  
         
    </form>     
</div>    
</body>    
</html> 