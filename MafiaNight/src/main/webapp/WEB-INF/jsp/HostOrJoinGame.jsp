<!DOCTYPE html>    
<html>    
<head>    
    <title>Login Form</title>    
    <link rel="stylesheet" type="text/css" href="css/style.css">    
</head>    
<body>    
    <h2>Host a game or join a game!</h2><br>    
    <div class="login">    
    <form id="hostGame" method="get" >    
    	 
        <label><b>Player name: 
        </b>    
        </label>    
        <input type="text" name="pname" id="Pass" placeholder="Player name">    
        <br><br>
          
        <input type="hidden" name="pid" value="123" id="Pass" placeholder="Player id">    
        <br><br>
        
        <input type="submit" id="log" value="Host a game" formaction="/hostNewGame" >       
        <br><br>    
       
        <br><br>  
        <label><b>Enter Game id: 
        </b>    
        </label>    
        <input type="text" id="Pass" name="gameId" placeholder="Game id" >    
        <br><br> 
        
        <input type="submit" id="log" value="Enter existing game" formaction="/joinGame"> 
         
    </form>     
</div>    
</body>    
</html> 