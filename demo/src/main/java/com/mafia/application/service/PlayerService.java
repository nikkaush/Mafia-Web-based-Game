package com.mafia.application.service;


import com.mafia.application.data.GameRoom;
import com.mafia.application.data.Player;
import com.mafia.application.data.PlayerProfile;


public interface PlayerService {
	
	boolean createPlayer(Player p);
	
	GameRoom hostGame(PlayerProfile pf);
	
	GameRoom joinGame(String gameId, PlayerProfile pf);
	
	GameRoom getGame(String gameId);

}
