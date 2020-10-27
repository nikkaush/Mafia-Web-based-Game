import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Player> players;
    Random rng = new Random();
    private int Round;
    private int[] murderVictims;
    private int investigated;
    private int protectedPerson;
    private int tiebreaker;
    private int mafia1;
    private int mafia2;
    private int detective;
    private int doctor;
    private int banished;

    private int[][] mostVotes;
    public Game(ArrayList<String> playerNames) {
        for (String name :
                playerNames) {
            players.add(new Player(PlayerStatus.Civilian, name));
        }

        int playerNum;
        while (true) {
            playerNum = rng.nextInt(playerNames.size());
            if (players.get(playerNum).getStatus().equals(PlayerStatus.Civilian)) {
                players.get(playerNum).setStatus(PlayerStatus.Mafia);
                mafia1 = playerNum;
                break;
            }
        }

        while (true) {
            playerNum = rng.nextInt(playerNames.size());
            if (players.get(playerNum).getStatus().equals(PlayerStatus.Civilian)) {
                players.get(playerNum).setStatus(PlayerStatus.Mafia);
                mafia2 = playerNum;
                break;
            }
        }

        ReturnMessage(PlayerStatus.Mafia, "You are a member of the mafia");

        while (true) {
            playerNum = rng.nextInt(playerNames.size());
            if (players.get(playerNum).getStatus().equals(PlayerStatus.Civilian)) {
                players.get(playerNum).setStatus(PlayerStatus.Doctor);
                doctor = playerNum;
                break;
            }
        }

        ReturnMessage(PlayerStatus.Doctor, "You are the doctor");

        while (true) {
            playerNum = rng.nextInt(playerNames.size());
            if (players.get(playerNum).getStatus().equals(PlayerStatus.Civilian)) {
                players.get(playerNum).setStatus(PlayerStatus.Detective);
                detective = playerNum;
                break;
            }
        }

        ReturnMessage(PlayerStatus.Detective, "You are the detective");

        while (!isOver()) {
            ReturnMessage(PlayerStatus.Civilian, "It is night, everyone is definitely asleep");
            ReturnMessage(PlayerStatus.Doctor, "It is night, choose someone to protect");
            ReturnMessage(PlayerStatus.Detective, "It is night, choose someone to investigate");
            ReturnMessage(PlayerStatus.Mafia, "It is night, choose someone to kill");

            civsDontDoAnyThing();
            while (waitingForVoting()) ;

            murderVictims = new int[2];
            murderVictims[0] = -1;
            murderVictims[1] = -1;
            protectedPerson = -1;
            investigated = -1;


            if (players.get(mafia1).getStatus() != PlayerStatus.Banished) {
                murderVictims[0] = players.get(mafia1).getTarget();
            }
            if (players.get(mafia2).getStatus() != PlayerStatus.Banished) {
                murderVictims[1] = players.get(mafia2).getTarget();
            }
            if (players.get(doctor).getStatus() != PlayerStatus.Banished && players.get(doctor).getStatus() != PlayerStatus.Corpse) {
                protectedPerson = players.get(doctor).getTarget();
            }
            if (players.get(detective).getStatus() != PlayerStatus.Banished && players.get(doctor).getStatus() != PlayerStatus.Corpse) {
                investigated = players.get(detective).getTarget();
            }

            tiebreaker = -1;

            while (tiebreaker == -1) {
                tiebreaker = rng.nextInt(2);
                tiebreaker = murderVictims[tiebreaker];
            }

            if (tiebreaker == protectedPerson) {
                ReturnMessage(PlayerStatus.Civilian, players.get(tiebreaker).getName() + " was targeted by the mafia but saved by the doctor");
            } else {
                ReturnMessage(PlayerStatus.Civilian, players.get(tiebreaker).getName() + " was killed by the mafia");
                players.get(tiebreaker).setStatus(PlayerStatus.Corpse);
            }
            if (investigated != -1){
                ReturnMessage(PlayerStatus.Detective,players.get(investigated).getName() + " is a "+players.get(investigated).getStatus());
            }

            if (isOver()){
                break;
            }

            resetActivePlayerVotes();

            ReturnMessage(PlayerStatus.Civilian,"It's day now, vote who to kick out");
            while (waitingForVoting());

            for (Player player:
                 players) {
                players.get(player.getTarget()).addVote();
            }

            mostVotes = new int[2][2];
            int[] votes = {0,0,0,0,0,0,0,0,0,0};
            mostVotes[0][0] = -1;
            mostVotes[0][1] = -1;
            mostVotes[1][0] = -1;
            mostVotes[1][1] = -1;
            for (Player player:
                    players) {
                votes[player.getTarget()]+=1;
            }

            for (int i = 0; i < 10; i++){
                if (votes[i] > mostVotes[0][0]){
                    mostVotes[0][0] = votes[i];
                    mostVotes[0][1] = i;
                }
                if (votes[i] == mostVotes[0][0]){
                    mostVotes[1][0] = votes[i];
                    mostVotes[1][1] = i;
                }
            }

            banished = mostVotes[rng.nextInt(2)][1];

            ReturnMessage(PlayerStatus.Civilian,players.get(banished).getName()+" was voted out. They were a "+players.get(banished).getStatus());
            players.get(banished).setStatus(PlayerStatus.Banished);
        }

        ReturnMessage(PlayerStatus.Civilian,"The game is over");
        if (getNumStatus(PlayerStatus.Mafia)==0){
            ReturnMessage(PlayerStatus.Civilian,"The townsfolk successfully voted out all the mafia");
        }
        else {
            for (Player player:
                 players) {
                if (player.getStatus().equals(PlayerStatus.Mafia)){
                    ReturnMessage(PlayerStatus.Civilian,player.getName()+" managed to evade detection and now runs the town");
                }
            }
        }
    }




    public void ReturnMessage(PlayerStatus target, String message){
        //send the message to every player with the associated status, or everyone if sent to civilians.
    }

    public void PlayerVote(int voter, int choice){
        players.get(voter).setTarget(choice);
    }

    public int getNumStatus(PlayerStatus status){
        int total = 0;
        for (Player player:
             players) {
            if (player.getStatus().equals(status)){
                total+=1;
            }
        }
        return total;
    }

    public boolean isOver(){
        if (getNumStatus(PlayerStatus.Mafia) == 0){
            ReturnMessage(PlayerStatus.Civilian,"All the mafia are gone, game over");
            return true;
        }

        if (getNumStatus(PlayerStatus.Mafia) > (getNumStatus(PlayerStatus.Civilian)+getNumStatus(PlayerStatus.Doctor)+getNumStatus(PlayerStatus.Detective))){
            ReturnMessage(PlayerStatus.Civilian,"The mafia outnumber the rest of you, you all gonna die");
            return true;
        }

        return false;
    }

    public boolean waitingForVoting(){
        for (Player player:
             players) {
            if (player.getTarget() == -1){
                return true;
            }
        }
        return false;
    }

    public void civsDontDoAnyThing(){
        for (Player player:
                players) {
            if (player.getStatus().equals(PlayerStatus.Civilian)){
                player.setTarget(1);
            }
        }
    }

    public void resetActivePlayerVotes(){
        for (Player player:
                players) {
            if (!player.getStatus().equals(PlayerStatus.Corpse) && !player.getStatus().equals(PlayerStatus.Banished)){
                player.setTarget(-1);
                player.resetVotes();
            }
        }
    }

}
