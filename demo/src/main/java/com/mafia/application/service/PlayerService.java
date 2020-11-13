package com.mafia.application.service;


import java.util.List;
import java.util.Map;

import com.mafia.application.data.GamePlay;
import com.mafia.application.data.GameRoom;
import com.mafia.application.data.Player;
import com.mafia.application.data.PlayerProfile;


public interface PlayerService {
	
	boolean createPlayer(Player p);
	
	GameRoom hostGame(PlayerProfile pf);
	
	GameRoom joinGame(String gameId, PlayerProfile pf);
	
	GameRoom getGame(String gameId);
	
	GamePlay startGame(String gameId);
	
	Map<String, List<String>> currentGame(String gameId);
	
	void updateSelection(String gameId, String playerId, String selectionId);
	
	

}
