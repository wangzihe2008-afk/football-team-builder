public class Fixture {

    private Team homeTeam;
    private Team awayTeam;

    public Fixture(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public boolean containsTeam(Team team) {
        return team == homeTeam || team == awayTeam;
    }

    public Team getOpponent(Team team) {
        if (team == homeTeam) {
            return awayTeam;
        }

        if (team == awayTeam) {
            return homeTeam;
        }

        return null;
    }

    public String getFixtureText() {
        return homeTeam.getName()
                + " vs "
                + awayTeam.getName();
    }
}