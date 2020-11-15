function leaderboards_search() {
    console.log("here")
    window.location.href = location.href.split("?")[0] + "?search=" + document.getElementById("leaderboard_text").value;
    return false
  }