searchString = window.location.search.split("=")[1];
console.log(searchString);

var leaderboard_ol_item = '<li class="leaderboard_list_item">';
var leaderboard_ol_item_end = '</li>';
var leaderboard_ol_end = '</ol></div>';
if (searchString != null) {
    var params = {
        "Stat_Type" : "Wins"     
    }
    console.log(JSON.stringify(params));
    var reqURL = "https://zpikv0u2x1.execute-api.us-east-1.amazonaws.com/test/user-statistics?";
    if ((searchString == "Wins") || (searchString == "Mafia_Wins") || (searchString == "Nonmafia_Wins")) {
        reqURL += "stat_type=" + searchString;
    } else {
        reqURL += "display_name=" + searchString;
    }
    
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", reqURL, false);
    xhttp.setRequestHeader('x-api-key', '7stiazlX4l4A9xKTohMgX2A7CKUFxn5U3gSVZNe6');
    xhttp.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    // xhttp.setRequestHeader('Stat_Type', 'Wins');
    var sendJson = JSON.stringify(params);
    // var needToEnd = false;
    // try {
        xhttp.send(sendJson);
    // } finally {
    //     window.location.href = location.href.split("?")[0];
    //     needToEnd = true;
    // }

    // if (needToEnd == true) {
    //     console.log('Bad Search');
    //     throw new Error("Bad Search");
    // }

    // console.log(xhttp.responseText);
    var resText = xhttp.responseText.replaceAll("\'", "\"");
    var resText = resText.replaceAll("\n", "");
    resText = resText.trim();
    var myJson = JSON.parse(resText);

    var stats = myJson.User_Statistics;
    console.log(stats);


    if (searchString == "Wins") {
        var leaderboard_ol = '<h2>Leaderboards - Top Players by Wins</h2><br><div class="login"><ol class="leaderboard_list">';
        
        var new_body_content = leaderboard_ol;

        for (i = 0; i < stats.length; i++) {
            new_body_content += leaderboard_ol_item + stats[i].Display_Name + " | " + stats[i].Wins + leaderboard_ol_item_end;
        }
        new_body_content += leaderboard_ol_end;

        document.getElementsByTagName('body')[0].innerHTML = new_body_content; 
        console.log("here")
    } else if (searchString == "Mafia_Wins") {
        var leaderboard_ol = '<h2>Leaderboards - Top Players by Mafia Wins</h2><br><div class="login"><ol class="leaderboard_list">';
        
        var new_body_content = leaderboard_ol;
        for (i = 0; i < stats.length; i++) {
            new_body_content += leaderboard_ol_item + stats[i].Display_Name + " | " + stats[i].Mafia_Wins + leaderboard_ol_item_end;
        }
        new_body_content += leaderboard_ol_end;

        document.getElementsByTagName('body')[0].innerHTML = new_body_content; 
        console.log("here")
    } else if (searchString == "Nonmafia_Wins") {
        var leaderboard_ol = '<h2>Leaderboards - Top Players by Nonmafia Wins</h2><br><div class="login"><ol class="leaderboard_list">';
        
        var new_body_content = leaderboard_ol;
        for (i = 0; i < stats.length; i++) {
            new_body_content += leaderboard_ol_item + stats[i].Display_Name + " | " + stats[i].Nonmafia_Wins + leaderboard_ol_item_end;
        }
        new_body_content += leaderboard_ol_end;

        document.getElementsByTagName('body')[0].innerHTML = new_body_content; 
    } else {
        console.log(myJson);
        var leaderboard_ol = '<h2>Statistic for ' + searchString + '</h2><br><div class="login"><ol class="leaderboard_list">';
        
        var new_body_content = leaderboard_ol;
        
        new_body_content += '<h3 class="leaderboard_list_item">Wins</h3>';
        new_body_content += '<p class="leaderboard_list_item">' + myJson.Wins + '</p>';
        new_body_content += '<h3 class="leaderboard_list_item">Losses</h3>';
        new_body_content += '<p class="leaderboard_list_item">' + myJson.Losses + '</p>';
        new_body_content += '<h3 class="leaderboard_list_item">Mafia Wins</h3>';
        new_body_content += '<p class="leaderboard_list_item">' + myJson.Mafia_Wins + '</p>';
        new_body_content += '<h3 class="leaderboard_list_item">Mafia Losses</h3>';
        new_body_content += '<p class="leaderboard_list_item">' + myJson.Mafia_Losses + '</p>';
        new_body_content += '<h3 class="leaderboard_list_item">Nonmafia Wins</h3>';
        new_body_content += '<p class="leaderboard_list_item">' + myJson.Nonmafia_Wins + '</p>';
        new_body_content += '<h3 class="leaderboard_list_item">Nonmafia Losses</h3>';
        new_body_content += '<p class="leaderboard_list_item">' + myJson.Nonmafia_Losses + '</p>';
        new_body_content += '<h3 class="leaderboard_list_item">Games Played</h3>';
        new_body_content += '<p class="leaderboard_list_item">' + myJson.Games_Played + '</p>';
        
        
        

        new_body_content += leaderboard_ol_end;

        document.getElementsByTagName('body')[0].innerHTML = new_body_content; 

    }
}