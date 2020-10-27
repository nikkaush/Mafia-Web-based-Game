 package com.mafia.application.data;

public class Player {
	
	String playerId;
	String playerName;
	String role;
	String gameRoomId;
	boolean isAlive;
	boolean isHost;
	
	public Player(String playerId, String playerName, String role, String gameRoomId, boolean isHost) {
		this.playerId = playerId;
		this.playerName = playerName;
		this.role = role;
		this.gameRoomId = gameRoomId;
		this.isAlive = true;
		this.isHost = isHost;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getRole() {
		return role;
	}

//	public void setRole(String role) {
//		this.role = role;
//	}

	public String getGameRoomId() {
		return gameRoomId;
	}

//	public void setGameRoomId(String gameRoomId) {
//		this.gameRoomId = gameRoomId;
//	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
		
//	public String toString() {
//		return this.playerName + " " + this.role + " " + this.gameRoomId + " " + this.isAlive;
//	}

}
