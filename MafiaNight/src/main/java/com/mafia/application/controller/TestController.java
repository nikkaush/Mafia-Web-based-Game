package com.mafia.application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mafia.application.data.GamePlay;
import com.mafia.application.data.GameRoom;
import com.mafia.application.data.Player;
import com.mafia.application.data.PlayerProfile;
import com.mafia.application.data.Roles;
import com.mafia.application.service.PlayerService;


/**
 * @author JavaSolutionsGuide
 *
 */

@Controller
@Scope("session")
@RequestMapping(value = "/")
public class TestController {
	@Autowired
	private PlayerService playerService;

	@GetMapping(value = "/v1/signup")
	public boolean playerSignup(@RequestBody Player p) {
		return playerService.createPlayer(p);
	}
	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String welcome(){
        return "welcome";
    }
	@RequestMapping(value="/hostOrJoinGame", method = RequestMethod.GET)
    public String hostOrJoinGame(){
        return "HostOrJoinGame";
    }
	
	   
	    @GetMapping("/home")
	    public String home(Model model, HttpSession session) {
	        List<String> notes = (List<String>) session.getAttribute("GAME_SESSION");
	        model.addAttribute("notesSession", notes!=null? notes:new ArrayList<>());
	        return "home";
	    }
	
	
	@GetMapping(value = "/hostNewGame")
	public String hostGame(@RequestParam String pid, @RequestParam String pname, HttpServletRequest request) {
		PlayerProfile pf = new PlayerProfile();
		pf.setPid(pid);
		pf.setPname(pname);
		GameRoom gameRoom = playerService.hostGame(pf); 

        //set gameRoom object in the request session
        request.getSession().setAttribute("GAME_SESSION", gameRoom);
        request.getSession().setAttribute("USER_SESSION", pname);
        request.getSession().setAttribute("isHost", true);
		return "redirect:/updateGame?gameId="+gameRoom.getGameId(); 
	}
	
	@PostMapping(value = "/v1/test")
	public List<Player> testGame(@RequestBody PlayerProfile pf) {
		List<Player> l1 = new ArrayList<>();
		Player p = new Player(pf.getPid(), pf.getPname(), Roles.getRole(4), "w3r3wedsa", true);
		l1.add(p);
		return l1;
	}
	
	@GetMapping(value = "/joinGame")
	public String joinGame(@RequestParam String gameId, @RequestParam String pid, 
			@RequestParam String pname, HttpServletRequest request) {
		
		PlayerProfile pf = new PlayerProfile();
		pf.setPid(pid);
		pf.setPname(pname);
		request.getSession().setAttribute("GAME_SESSION", playerService.joinGame(gameId, pf));
		request.getSession().setAttribute("USER_SESSION", pname);
		request.getSession().setAttribute("isHost", false);
		return "redirect:/updateGame?gameId="+gameId; 
	}
	
	@GetMapping(value = "/updateGame")
	public String getGame(@RequestParam String gameId) {
		return "GameRoom";
	}
	
	@GetMapping(value = "/startGame")
	public String startGame(@RequestParam String gameId, HttpServletRequest request) {
		GamePlay game = playerService.startGame(gameId); 
		request.getSession().setAttribute("GAME_SESSION", game);
		return "redirect:/gamePlay?gameId="+gameId;
	}
	
//	@GetMapping(value = "/gamePlay/refreshSelection/{gameId}")
//	public Map<String, List<String>> refreshGame(@PathVariable String gameId) {
//		return playerService.currentGame(gameId);
//	}
	
	@GetMapping(value = "/gamePlay")
	public String refreshGame1(@RequestParam String gameId, HttpServletRequest request) {
		request.getSession().setAttribute("GAME_SESSION", playerService.currentGameObj(gameId));
			
		return "PlayerScreen";
	}
	
	@PutMapping(value = "/gamePlay/updateSelection/{gameId}/{pid}/{sid}")
	public void gameSelection(@PathVariable String gameId, @PathVariable String pid, @PathVariable String sid) {
		playerService.updateSelection(gameId, pid, sid);
	}
	
}
