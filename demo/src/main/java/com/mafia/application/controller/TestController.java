package com.mafia.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mafia.application.data.GameRoom;
import com.mafia.application.data.Player;
import com.mafia.application.data.PlayerProfile;
import com.mafia.application.data.Roles;
import com.mafia.application.service.PlayerService;


/**
 * @author JavaSolutionsGuide
 *
 */

@RestController
@RequestMapping(value = "/mafia")
public class TestController {
	@Autowired
	private PlayerService playerService;

	@GetMapping(value = "/v1/signup")
	public boolean playerSignup(@RequestBody Player p) {
		return playerService.createPlayer(p);
	}
	
	@PostMapping(value = "/v1/host")
	public GameRoom hostGame(@RequestBody PlayerProfile pf) {
		GameRoom obj = playerService.hostGame(pf); 
		return obj;
	}
	
	@PostMapping(value = "/v1/test")
	public List<Player> testGame(@RequestBody PlayerProfile pf) {
		List<Player> l1 = new ArrayList<>();
		Player p = new Player(pf.getPid(), pf.getPname(), Roles.getRole(4), "w3r3wedsa", true);
		l1.add(p);
		return l1;
	}
	
	@PostMapping(value = "/v1/join/{gameId}")
	public GameRoom joinGame(@PathVariable String gameId, @RequestBody PlayerProfile pf) {
		return playerService.joinGame(gameId, pf);
	}
	
	@GetMapping(value = "/v1/updateGame/{gameId}")
	public GameRoom getGame(@PathVariable String gameId) {
		return playerService.getGame(gameId);
	}
	
}
