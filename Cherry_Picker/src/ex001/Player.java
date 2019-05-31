package ex001;

/**
 *
 * @author franz
 */
public class Player {
    private int id;
    private String name;
    private int highscore;

    public Player(int id, String name, int highscore) {
        this.id = id;
        this.name = name;
        this.highscore = highscore;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHighscore() {
        return highscore;
    }

    @Override
    public String toString() {
        return String.format("%-3d %-10s %-5d", id, name, highscore);
    }  
}
