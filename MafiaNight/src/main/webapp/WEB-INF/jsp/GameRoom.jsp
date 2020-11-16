<!DOCTYPE html>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>    
<head>    
    <title>Login Form</title>    
    <link rel="stylesheet" type="text/css" href="css/style.css">  
</head>    

<body onload="setInterval(function() {window.location.reload();}, 5000);">    
    <h2>Game Room</h2>   
    <div class="login">  
    <h2><c:out value="${sessionScope.GAME_SESSION.gameId}"></c:out></h2>
    
    <form id="startGame" method="get" action="/startGame">    
    
    	<input type="hidden" id="gameId" name="gameId" value="${sessionScope.GAME_SESSION.gameId}"/>
    	
    	<c:forEach items="${sessionScope.GAME_SESSION.playerList}" var="player">
		    <label><b>
		       <input type="button" id="log" name="<c:out value="${player.playerName}"/>" value="<c:out value="${player.playerName}"/>" ></b>
		    </label><br><br> 
		</c:forEach>
        <c:if test="${sessionScope.isHost == true}">
       		<input type="submit" id="log" value="Start Game" placeholder="Start Game">    
        	<br><br> 
        </c:if>
         
    </form>     
</div>    
</body>    
</html> 