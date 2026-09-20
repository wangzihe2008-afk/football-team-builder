public class LeagueTable {

    private Team[] teams;

    public LeagueTable(Team[] teams) {
        this.teams = teams;
    }

    public Team[] getStandings() {
        Team[] standings = new Team[teams.length];

        for (int i = 0; i < teams.length; i++) {
            standings[i] = teams[i];
        }

        for (int i = 0; i < standings.length - 1; i++) {
            for (int j = 0; j < standings.length - 1 - i; j++) {
                if (shouldSwap(standings[j], standings[j + 1])) {
                    Team temp = standings[j];
                    standings[j] = standings[j + 1];
                    standings[j + 1] = temp;
                }
            }
        }

        return standings;
    }

    private boolean shouldSwap(Team first, Team second) {
        if (first.getPoints() != second.getPoints()) {
            return first.getPoints() < second.getPoints();
        }

        if (first.getGoalDifference() != second.getGoalDifference()) {
            return first.getGoalDifference() < second.getGoalDifference();
        }

        if (first.getGoalsFor() != second.getGoalsFor()) {
            return first.getGoalsFor() < second.getGoalsFor();
        }

        return first.getWins() < second.getWins();
    }

    public int getTeamPosition(Team team) {
        if (team == null) {
            return -1;
        }

        Team[] standings = getStandings();

        for (int i = 0; i < standings.length; i++) {
            if (standings[i] == team) {
                return i + 1;
            }
        }

        return -1;
    }

    public Team getLeader() {
        Team[] standings = getStandings();

        if (standings.length == 0) {
            return null;
        }

        return standings[0];
    }

    public String getTableText() {
        Team[] standings = getStandings();

        String text = String.format(
                "%-4s %-24s %3s %3s %3s %4s %4s %4s %4s%n",
                "POS", "TEAM", "W", "D", "L", "GF", "GA", "GD", "PTS"
        );

        for (int i = 0; i < standings.length; i++) {
            Team team = standings[i];

            text += String.format(
                    "%-4d %-24s %3d %3d %3d %4d %4d %4d %4d%n",
                    i + 1,
                    team.getName(),
                    team.getWins(),
                    team.getDraws(),
                    team.getLosses(),
                    team.getGoalsFor(),
                    team.getGoalsAgainst(),
                    team.getGoalDifference(),
                    team.getPoints()
            );
        }

        return text;
    }
}