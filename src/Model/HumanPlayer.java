package Model;

public class HumanPlayer extends Player {


    public HumanPlayer(GameModel model, int team) {
        super(model, team);
    }

    @Override
    public boolean isLocalHuman() {
        return true;
    }

    @Override
    public void action(GameModel model) {

    }

    @Override
    public void move(GameModel model) {

    }

    @Override
    public void prepare(GameModel model) {

    }

}
