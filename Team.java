public class Team {

    public static final String[] POSITIONS = {
            "GK", "LB", "CB-L", "CB-R", "RB",
            "CM-L", "CAM", "CM-R", "LW", "ST", "RW"
    };

    private String name;
    private Player[] lineup;

    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    private int trophies;

    public Team(String name, Player[] lineup) {
        this.name = name;
        this.lineup = lineup;

        wins = 0;
        draws = 0;
        losses = 0;
        goalsFor = 0;
        goalsAgainst = 0;
        trophies = 0;
    }

    public String getName() {
        return name;
    }

    public Player[] getLineup() {
        return lineup;
    }

    public Player getPlayer(int index) {
        if (index < 0 || index >= lineup.length) {
            return null;
        }

        return lineup[index];
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public int getPoints() {
        return wins * 3 + draws;
    }

    public int getTrophies() {
        return trophies;
    }

    public void recordMatch(int scored, int conceded) {
        goalsFor += scored;
        goalsAgainst += conceded;

        if (scored > conceded) {
            wins++;
        } else if (scored < conceded) {
            losses++;
        } else {
            draws++;
        }
    }

    public void resetSeasonStats() {
        wins = 0;
        draws = 0;
        losses = 0;
        goalsFor = 0;
        goalsAgainst = 0;
    }

    public void addTrophy() {
        trophies++;
    }

    public double getTeamPower() {
        double total = 0;

        for (int i = 0; i < lineup.length; i++) {
            total += lineup[i].getContribution(POSITIONS[i]);
        }

        return total;
    }

    public boolean replacePlayer(int slot, Player player) {
        if (slot <= 0 || slot >= lineup.length || player == null) {
            return false;
        }

        lineup[slot] = player;
        return true;
    }

    public boolean swapPlayers(int firstSlot, int secondSlot) {
        if (firstSlot <= 0 || secondSlot <= 0) {
            return false;
        }

        if (firstSlot >= lineup.length || secondSlot >= lineup.length) {
            return false;
        }

        if (firstSlot == secondSlot) {
            return false;
        }

        Player temp = lineup[firstSlot];
        lineup[firstSlot] = lineup[secondSlot];
        lineup[secondSlot] = temp;

        return true;
    }

    public String getLineupText() {
        String text = name + " LINEUP\n";

        for (int i = 0; i < lineup.length; i++) {
            text += POSITIONS[i]
                    + " - "
                    + lineup[i].getName();

            if (lineup[i].isCustomPlayer()) {
                text += " ⭐";
            }

            text += "\n";
        }

        return text;
    }
}