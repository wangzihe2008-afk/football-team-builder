public class SeasonAwards {

    private Team[] teams;
    private LeagueTable leagueTable;
    private PlayerStatistics playerStatistics;

    private Team champion;
    private Player[] goldenBootWinners;
    private Player[] playmakerWinners;
    private Player goldenGloveWinner;

    public SeasonAwards(Team[] teams, LeagueTable leagueTable, PlayerStatistics playerStatistics) {
        this.teams = teams;
        this.leagueTable = leagueTable;
        this.playerStatistics = playerStatistics;

        goldenBootWinners = new Player[0];
        playmakerWinners = new Player[0];
    }

    public void calculateAwards() {
        champion = leagueTable.getLeader();
        goldenBootWinners = findGoldenBootWinners();
        playmakerWinners = findPlaymakerWinners();
        goldenGloveWinner = findGoldenGloveWinner();
    }

    private Player[] findGoldenBootWinners() {
        Player[] players = playerStatistics.getPlayersByGoals();
        if (players.length == 0 || players[0].getSeasonGoals() == 0) {
            return new Player[0];
        }

        int topGoals = players[0].getSeasonGoals();
        int count = 0;

        for (int i = 0; i < players.length; i++) {
            if (players[i].getSeasonGoals() == topGoals) {
                count++;
            } else {
                break;
            }
        }

        Player[] winners = new Player[count];
        for (int i = 0; i < count; i++) {
            winners[i] = players[i];
        }
        return winners;
    }

    private Player[] findPlaymakerWinners() {
        Player[] players = playerStatistics.getPlayersByAssists();
        if (players.length == 0 || players[0].getSeasonAssists() == 0) {
            return new Player[0];
        }

        int topAssists = players[0].getSeasonAssists();
        int count = 0;

        for (int i = 0; i < players.length; i++) {
            if (players[i].getSeasonAssists() == topAssists) {
                count++;
            } else {
                break;
            }
        }

        Player[] winners = new Player[count];
        for (int i = 0; i < count; i++) {
            winners[i] = players[i];
        }
        return winners;
    }

    private Player findGoldenGloveWinner() {
        if (teams.length == 0) return null;

        Team best = teams[0];

        for (int i = 1; i < teams.length; i++) {
            Team current = teams[i];

            if (current.getGoalsAgainst() < best.getGoalsAgainst()) {
                best = current;
            } else if (current.getGoalsAgainst() == best.getGoalsAgainst()) {
                if (current.getWins() > best.getWins()) {
                    best = current;
                } else if (current.getWins() == best.getWins()
                        && leagueTable.getTeamPosition(current) < leagueTable.getTeamPosition(best)) {
                    best = current;
                }
            }
        }

        return best.getPlayer(0);
    }

    private String displayName(Player player) {
        if (player == null) return "None";
        return player.isCustomPlayer() ? "⭐ " + player.getName() : player.getName();
    }

    private String playerList(Player[] players, boolean goals) {
        if (players.length == 0) return "No winner";

        String text = "";
        for (int i = 0; i < players.length; i++) {
            if (i > 0) text += ", ";
            text += displayName(players[i]);
        }

        int number = goals ? players[0].getSeasonGoals() : players[0].getSeasonAssists();
        return text + " (" + number + ")";
    }

    public Team getChampion() { return champion; }
    public Player[] getGoldenBootWinners() { return goldenBootWinners; }
    public Player[] getPlaymakerWinners() { return playmakerWinners; }
    public Player getGoldenGloveWinner() { return goldenGloveWinner; }

    public String getAwardsText() {
        String championText = champion == null ? "No champion" : champion.getName();
        String goldenGloveText = goldenGloveWinner == null ? "No winner" : displayName(goldenGloveWinner);

        Team keeperTeam = goldenGloveWinner == null ? null : playerStatistics.getPlayerTeam(goldenGloveWinner);
        if (keeperTeam != null) {
            goldenGloveText += " (" + keeperTeam.getGoalsAgainst() + " goals conceded)";
        }

        return "🏆 LEAGUE CHAMPION\n"
                + championText + "\n\n"
                + "⚽ GOLDEN BOOT\n"
                + playerList(goldenBootWinners, true) + "\n\n"
                + "👟 PLAYMAKER\n"
                + playerList(playmakerWinners, false) + "\n\n"
                + "🧤 GOLDEN GLOVE\n"
                + goldenGloveText + "\n";
    }
}
