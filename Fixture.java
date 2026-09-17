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
        return homeTeam == team || awayTeam == team;
    }

    public Team getOpponent(Team team) {
        if (homeTeam == team) return awayTeam;
        if (awayTeam == team) return homeTeam;
        return null;
    }

    public String getFixtureText() {
        return homeTeam.getName() + " vs " + awayTeam.getName();
    }
}
