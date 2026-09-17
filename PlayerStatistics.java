public class PlayerStatistics {

    private Team[] teams;

    public PlayerStatistics(Team[] teams) {
        this.teams = teams;
    }

    public Player[] getAllPlayers() {
        Player[] players = new Player[teams.length * 11];
        int index = 0;

        for (int i = 0; i < teams.length; i++) {
            Player[] lineup = teams[i].getLineup();

            for (int j = 0; j < lineup.length; j++) {
                players[index++] = lineup[j];
            }
        }

        return players;
    }

    public Player[] getPlayersByGoals() {
        Player[] players = getAllPlayers();

        for (int i = 0; i < players.length - 1; i++) {
            for (int j = 0; j < players.length - 1 - i; j++) {
                if (shouldSwapGoals(players[j], players[j + 1])) {
                    Player temp = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = temp;
                }
            }
        }

        return players;
    }

    private boolean shouldSwapGoals(Player first, Player second) {
        if (first.getSeasonGoals() != second.getSeasonGoals()) {
            return first.getSeasonGoals() < second.getSeasonGoals();
        }
        return first.getSeasonAssists() < second.getSeasonAssists();
    }

    public Player[] getPlayersByAssists() {
        Player[] players = getAllPlayers();

        for (int i = 0; i < players.length - 1; i++) {
            for (int j = 0; j < players.length - 1 - i; j++) {
                if (shouldSwapAssists(players[j], players[j + 1])) {
                    Player temp = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = temp;
                }
            }
        }

        return players;
    }

    private boolean shouldSwapAssists(Player first, Player second) {
        if (first.getSeasonAssists() != second.getSeasonAssists()) {
            return first.getSeasonAssists() < second.getSeasonAssists();
        }
        return first.getSeasonGoals() < second.getSeasonGoals();
    }

    public Team getPlayerTeam(Player player) {
        if (player == null) {
            return null;
        }

        for (int i = 0; i < teams.length; i++) {
            Player[] lineup = teams[i].getLineup();

            for (int j = 0; j < lineup.length; j++) {
                if (lineup[j] == player) {
                    return teams[i];
                }
            }
        }

        return null;
    }

    private String displayName(Player player) {
        return player.isCustomPlayer() ? "⭐ " + player.getName() : player.getName();
    }

    public String getTopScorersText(int amount) {
        Player[] players = getPlayersByGoals();
        int limit = Math.min(amount, players.length);

        String text = String.format("%-4s %-24s %-22s %6s %7s%n",
                "#", "PLAYER", "TEAM", "GOALS", "ASSISTS");
        text += "---------------------------------------------------------------------\n";

        for (int i = 0; i < limit; i++) {
            Team team = getPlayerTeam(players[i]);
            text += String.format("%-4d %-24s %-22s %6d %7d%n",
                    i + 1,
                    displayName(players[i]),
                    team == null ? "Unknown" : team.getName(),
                    players[i].getSeasonGoals(),
                    players[i].getSeasonAssists());
        }

        return text;
    }

    public String getTopAssistsText(int amount) {
        Player[] players = getPlayersByAssists();
        int limit = Math.min(amount, players.length);

        String text = String.format("%-4s %-24s %-22s %7s %6s%n",
                "#", "PLAYER", "TEAM", "ASSISTS", "GOALS");
        text += "---------------------------------------------------------------------\n";

        for (int i = 0; i < limit; i++) {
            Team team = getPlayerTeam(players[i]);
            text += String.format("%-4d %-24s %-22s %7d %6d%n",
                    i + 1,
                    displayName(players[i]),
                    team == null ? "Unknown" : team.getName(),
                    players[i].getSeasonAssists(),
                    players[i].getSeasonGoals());
        }

        return text;
    }
}
