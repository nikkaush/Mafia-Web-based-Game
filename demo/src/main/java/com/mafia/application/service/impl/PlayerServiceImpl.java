package com.mafia.application.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mafia.application.data.GamePlay;
import com.mafia.application.data.GameRoom;
import com.mafia.application.data.Player;
import com.mafia.application.data.PlayerProfile;
import com.mafia.application.data.Worker;
import com.mafia.application.service.PlayerService;


@Service
public class PlayerServiceImpl implements PlayerService {
	
	Map<String, GameRoom> gameMap = new HashMap<>();
	Map<String, GamePlay> playMap = new HashMap<>();
	

	@Override
	public boolean createPlayer(Player p) {
		
		p.setAlive(true);
		
//		p.setRole("");
//		p.setGameRoomId(0);
		
		System.out.println(p.getPlayerName());
		
		return true;
	}


	@Override
	public GameRoom hostGame(PlayerProfile pf) {
		String gameId = UUID.randomUUID().toString();
		GameRoom gameObj = new GameRoom(gameId, pf);
		gameMap.put(gameId, gameObj);
		return gameObj;
	}


	@Override
	public GameRoom joinGame(String gameId, PlayerProfile pf) {
		if(!gameMap.containsKey(gameId) || gameMap.get(gameId).isFull())
			return null;
		
		GameRoom gameObj = gameMap.get(gameId);
		gameObj.addPlayer(pf);
		
		return gameObj;
	}


	@Override
	public GameRoom getGame(String gameId) {
		if(!gameMap.containsKey(gameId))
			return null;
		
		return gameMap.get(gameId);
	}

//gameId:GameRoomObj
//game will be played in GameRoomObj
	
	
	@Override
	public GamePlay startGame(String gameId) {
		GameRoom gm = gameMap.get(gameId);
		playMap.put(gameId, new GamePlay(gm.getPlayerList()));
		gameMap.remove(gameId);
		
		new Worker(playMap.get(gameId)).start();
		
		System.out.println("Main control");
		return playMap.get(gameId);
	}
	
	@Override
	public Map<String, List<String>> currentGame(String gameId) {
		Map<String, List<String>> pp = playMap.get(gameId).getDisplayMap();
		return pp;
//		return playMap.get(gameId).getDisplayMap();
	}
	

	@Override
	public void updateSelection(String gameId, String playerId, String selectionId) {
		playMap.get(gameId).updateSelection(playerId, selectionId);
	}


	


	

}
