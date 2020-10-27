public class Player {
    private PlayerStatus status;
    private String name;
    private int target;
    private int votes;

    public Player(PlayerStatus status, String name){
        this.status = status;
        this.name = name;
        this.target = -1;
        this.votes = 0;
    }

    public void setStatus(PlayerStatus status){
        this.status = status;
    }

    public PlayerStatus getStatus(){
        return status;
    }

    public String getName(){
        return name;
    }

    public void setTarget(int target){
        this.target = target;
    }

    public boolean hasChosen(){
        return (target == -1);
    }

    public void resetTarget(){
        target = -1;
    }

    public int getTarget(){
        return target;
    }

    public void addVote(){
        votes++;
    }

    public void resetVotes(){
        votes = 0;
    }

    public int getVotes(){
        return votes;
    }
}
