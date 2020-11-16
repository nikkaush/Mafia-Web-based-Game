package com.mafia.application.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Scope("session")
public class GameRoom {
	@JsonIgnore
	private String gameId;
	
	private List<Player> playerList;
	
	public String getGameId() {
		return gameId;
	}
//
//	public List<Integer> getRoles() {
//		return roles;
//	}
//
//	public int getPlayerNum() {
//		return playerNum;
//	}

	@JsonIgnore
	private List<Integer> roles;
	@JsonIgnore
	private int playerNum = 0;
	
	
	public List<Player> getPlayerList() {
		return playerList;
	}

	
	
	// debug const
	public GameRoom() {
		playerList = new ArrayList<>();
	}

	public GameRoom(String id, PlayerProfile pf) {
		playerList = new ArrayList<>();
		gameId = "100";
		roles = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
		Collections.shuffle(roles);
		this.gameId = id;
		playerList.add(new Player(pf.getPid(), pf.getPname(), Roles.getRole(roles.get(playerNum++)), this.gameId, true));
		
	}
	
	public boolean isFull() {
		return playerNum >= 10;
	}
	
	public void addPlayer(PlayerProfile pf) {
		playerList.add(new Player(pf.getPid(), pf.getPname(), Roles.getRole(playerNum++), this.gameId, false));
	}
	
	

}
