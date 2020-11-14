package com.mafia.application.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePlay {
	
	int gameTime = 0;
	public int getGameTime() {
		return gameTime;
	}


	int mafiaTime = 30;
	int doctorTime = 30;
	int inspectorTime = 30;
	int voteTime = 60;
	
	List<String> selectionList = new ArrayList<>();
	Map<String, String> currSelectionMap;
	
	Map<String, List<String>> displayMap;
	public Map<String, List<String>> getDisplayMap() {
		return this.displayMap;
	}
	
	String saved = "";
	String killed = "";
	
	String returnMessage = "";
	public String getReturnMessage() {
		return returnMessage;
	}


	List<Player> playerList;
	public List<Player> getPlayerList() {
		return playerList;
	}


	int numMafiaAlive = 0;
	int numCitizenAlive = 0;

	int[] playTimes = new int[] { mafiaTime, doctorTime, inspectorTime, voteTime };

	boolean mafiaPlay = false;
	boolean doctorPlay = false;
	boolean inspectorPlay = false;
	boolean votePlay = false;

	boolean[] playingStatus = new boolean[] { mafiaPlay, doctorPlay, inspectorPlay, votePlay };
	
	String[] roles = new String[] {"Mafia", "Doctor", "Inspector", "Vote"};

	public GamePlay(List<Player> playerList) {
		this.playerList = playerList;
		
		this.currSelectionMap = new HashMap<>();
		this.displayMap = new HashMap<>();
		
		for (Player p : playerList) {
			if (p.getRole().equals("Mafia"))
				numMafiaAlive += 1;
			else
				numCitizenAlive += 1;
		}

	}
	
	public void updateSelection(String playerId, String selectionId) {
		if(currSelectionMap.containsKey(playerId)) {
			String currSelectionId = currSelectionMap.get(playerId);
			
			if(!currSelectionId.equals(selectionId)) {
				currSelectionMap.put(playerId, selectionId);
				
				displayMap.putIfAbsent(selectionId, new ArrayList<>());
				displayMap.get(selectionId).add(playerId);
				
				
				
				displayMap.get(currSelectionId).remove(playerId);
				
				if(displayMap.get(currSelectionId).size() == 0)
					displayMap.remove(currSelectionId);
					
				
			} 
		} else {
			currSelectionMap.put(playerId, selectionId);
			displayMap.putIfAbsent(selectionId, new ArrayList<>());
			displayMap.get(selectionId).add(playerId);
		}
	}

	public void startGame() {

		while (!isGame()) {
			for (int i = 0; i < playTimes.length; i++) {
				
				
				if(i == 3) {
					updateReturnMafia();
				}
				
				setTurn(roles[i]);
				
				this.gameTime = playTimes[i];
				System.out.println(this.gameTime + "\n" + roles[i]);
				
				try {
					Thread.sleep(playTimes[i]*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				actionHelper(i);
			}
			
		}
	}
	
	public void setTurnHelper(int t) {
		if(t == 0) {
			setTurn("Mafia");
			returnMessage = "Select unanimously to kill someone.";
		} else if(t == 1) {
			setTurn("Doctor");
			returnMessage = "Select someone to save. \n You can also save yourself";
		} else if(t == 2) {
			setTurn("Inspector");
			returnMessage = "Select someone to see if they are Mafia.";
		} else {
			setTurn("Vote");
		}
	}
	
	public void setTurn(String role) {
		for(Player p : playerList) {
			if(role.equals("vote")) {
				p.turn = true;
			} else {
				if(p.role.equals(role))
					p.turn = true;
				else
					p.turn = false;
			}
			
		}
	}
	
	public void actionHelper(int i) {
		if(i == 0)
			mafiaAction();
		else if(i == 1)
			doctorAction();
		else if(i == 2)
			inspectorAction();
		else
			voteAction();
	}
	
	public void mafiaAction() {
		for(Map.Entry<String, List<String>> es : displayMap.entrySet()) {
			if(es.getValue().size() == 2) {
				killed = es.getKey();
			}
		}
		
		clearSelection();
	}
	
	public void doctorAction() {
		for(String k : displayMap.keySet()) {
			saved = k;
			break;
		}
		
		clearSelection();
	}
	
	public void inspectorAction() {
		String inspectId = "";
		for(String k : displayMap.keySet()) {
			inspectId = k;
			break;
		}
		
		clearSelection();
		
		if(inspectId.length() == 0) {
			returnMessage = "You didn't caught the Mafia";
			return;
		}
		
		for(Player p : playerList) {
			if(p.playerId.equals(inspectId)) {
				if(p.role.equals("Mafia"))
					returnMessage = "You caught the Mafia";
				else
					returnMessage = "You didn't caught the Mafia";
				break;
			}
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void voteAction() {
		if(displayMap.size() == 0) {
			returnMessage = "No one was kicked out!";
			return;
		}
		
		String msg1 = "";
		String msg2 = "";
		String msg3 = "";
		
		
		int votes = 0;
		Map<String, Integer> voteMap = new HashMap<>();
		
		for(Map.Entry<String, List<String>> es : displayMap.entrySet()) {
			int currVote = es.getValue().size();
			votes = Math.max(votes, currVote);
			voteMap.put(es.getKey(), votes); 
		}
		
		
		List<String> picked = new ArrayList<>();
		for(Map.Entry<String, Integer> es : voteMap.entrySet()) {
			if(es.getValue() == votes)
				picked.add(es.getKey());
		}
		
		clearSelection();
		
		if(picked.size() > 1) {
			returnMessage = "No one was kicked out!";
			return;
		} else {
			msg1 = picked.get(0) + " was kicked out!";
			for(Player p : playerList) {
				if(p.playerId.equals(picked.get(0))) {
					p.isAlive = false;
					if(p.role.equals("Mafia")) {
						numMafiaAlive -= 1;
						msg2 = picked.get(0) + " was the mafia!"; 
						
					} else {
						numCitizenAlive -= 1;
						msg2 = picked.get(0) + " was not the mafia!";
					}
						
				}
			}
			msg3 = numMafiaAlive + " Mafia remaining.";
			returnMessage = msg1 + "\n" + msg2 + "\n" + msg3;
		}
		
		
	}
	
	public void clearSelection() {
		displayMap.clear();
		currSelectionMap.clear();
	}
	
	public void updateReturnMafia() {
		String msg1 = "";
		String msg2 = "";
		
		if(killed.length() > 0) {
			msg1 = killed + " was killed by Mafia's";
			if(!killed.equals(saved)) {
				for(Player p : playerList) {
					if(p.playerId.equals(killed)) {
						p.isAlive = false;
						numCitizenAlive -= 1;
						break;
					}
				}
			}
			
		}
			
		else
			msg1 = "No one was killed by Mafia's";
		
		msg2 = saved + " was saved by the doctor";
		
		String msg3 = "Time to vote out the Mafia";
		
		killed = "";
		saved = "";
		
		returnMessage = msg1 + "\n" + msg2 + "\n" + msg3;
	}

	
	public boolean isGame() {
		boolean gameCont = (numMafiaAlive == 0) || (numCitizenAlive == numMafiaAlive);
		if (numMafiaAlive == 0) {
			for (Player player : this.playerList) {
				if ((!player.role.equals("Mafia")) && (player.isAlive)) {
					player.wonGame();
				}
			}
		} else if (numCitizenAlive == numMafiaAlive) {
			for (Player player : this.playerList) {
				if (player.role.equals("Mafia")) {
					player.wonGame();
				}
			}
		}
		
		return gameCont;
		
	}

}
