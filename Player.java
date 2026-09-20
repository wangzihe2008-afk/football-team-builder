public class Player {

    private String name;
    private int shooting;
    private int passing;
    private int defense;

    private int seasonGoals;
    private int seasonAssists;
    private int seasonMatches;

    private int careerGoals;
    private int careerAssists;
    private int careerMatches;

    private boolean customPlayer;

    public Player(String name, int shooting, int passing, int defense, boolean customPlayer) {
        this.name = name;
        this.shooting = shooting;
        this.passing = passing;
        this.defense = defense;
        this.customPlayer = customPlayer;

        seasonGoals = 0;
        seasonAssists = 0;
        seasonMatches = 0;

        careerGoals = 0;
        careerAssists = 0;
        careerMatches = 0;
    }

    public String getName() {
        return name;
    }

    public int getShooting() {
        return shooting;
    }

    public int getPassing() {
        return passing;
    }

    public int getDefense() {
        return defense;
    }

    public int getSeasonGoals() {
        return seasonGoals;
    }

    public int getSeasonAssists() {
        return seasonAssists;
    }

    public int getSeasonMatches() {
        return seasonMatches;
    }

    public int getCareerGoals() {
        return careerGoals;
    }

    public int getCareerAssists() {
        return careerAssists;
    }

    public int getCareerMatches() {
        return careerMatches;
    }

    public boolean isCustomPlayer() {
        return customPlayer;
    }

    public double getContribution(String position) {
        double contribution;

        if (position.equals("LW")
                || position.equals("ST")
                || position.equals("RW")) {

            contribution =
                    shooting * 2.00
                            + passing * 1.00
                            + defense * 0.50;

        } else if (position.equals("CM-L")
                || position.equals("CAM")
                || position.equals("CM-R")) {

            contribution =
                    shooting * 0.75
                            + passing * 2.00
                            + defense * 0.75;

        } else if (position.equals("LB")
                || position.equals("CB-L")
                || position.equals("CB-R")
                || position.equals("RB")) {

            contribution =
                    shooting * 0.50
                            + passing * 1.00
                            + defense * 2.00;

        } else {
            contribution =
                    shooting * 0.25
                            + passing * 0.75
                            + defense * 2.25;
        }

        if (customPlayer) {
            contribution *= 1.75;
        }

        return contribution;
    }

    public boolean addSkillPoint(String skill) {
        if (skill == null) {
            return false;
        }

        if (skill.equalsIgnoreCase("Shooting")
                && shooting < 15) {

            shooting++;
            return true;
        }

        if (skill.equalsIgnoreCase("Passing")
                && passing < 15) {

            passing++;
            return true;
        }

        if (skill.equalsIgnoreCase("Defense")
                && defense < 15) {

            defense++;
            return true;
        }

        return false;
    }

    public void addGoal() {
        seasonGoals++;
        careerGoals++;
    }

    public void addAssist() {
        seasonAssists++;
        careerAssists++;
    }

    public void addMatchPlayed() {
        seasonMatches++;
        careerMatches++;
    }

    public void resetSeasonStats() {
        seasonGoals = 0;
        seasonAssists = 0;
        seasonMatches = 0;
    }

    public String getSkillText() {
        return "Shooting: "
                + shooting
                + " | Passing: "
                + passing
                + " | Defense: "
                + defense;
    }

    public String getSeasonStatsText() {
        return "Matches: "
                + seasonMatches
                + " | Goals: "
                + seasonGoals
                + " | Assists: "
                + seasonAssists;
    }

    public String getCareerStatsText() {
        return "Matches: "
                + careerMatches
                + " | Goals: "
                + careerGoals
                + " | Assists: "
                + careerAssists;
    }
}