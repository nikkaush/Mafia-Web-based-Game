<!DOCTYPE html>    
<html>    
<head>    
    <title>Mafia night</title>    
    <link rel="stylesheet" type="text/css" href="css/style.css">    
</head>    
<body onload="setInterval(function() {window.location.reload();}, 5000);">    
    <h2>Game on!</h2><br>    
    <div class="login">    
    <form id="login" method="get" action="login.php">    
         
           
        <input type="button" name="guest" id="guest" onclick="location.href='host_or_enter_game.html';" value="Host a game" >       
        <br><br>    
        <input type="button" name="signup" id="signup" value="Enter an existing game">       
        <br><br>  
        <label><b>I'm mafia
        </b>    
        </label>    
        <input type="text" name="" id="" placeholder="Game id">    
        <br><br> 
         
    </form>     
</div>    
</body>    
</html> 