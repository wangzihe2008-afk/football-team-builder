public class PlayerStatistics {

    private Team[] teams;

    public PlayerStatistics(Team[] teams) {
        this.teams = teams;
    }

    public Player[] getAllPlayers() {
        Player[] players = new Player[teams.length * Team.POSITIONS.length];
        int index = 0;

        for (int i = 0; i < teams.length; i++) {
            Player[] lineup = teams[i].getLineup();

            for (int j = 0; j < lineup.length; j++) {
                players[index] = lineup[j];
                index++;
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

    private String getDisplayName(Player player) {
        if (player.isCustomPlayer()) {
            return "⭐ " + player.getName();
        }

        return player.getName();
    }

    public String getTopScorersText(int limit) {
        Player[] players = getPlayersByGoals();

        String text = String.format(
                "%-4s %-26s %-22s %5s %7s%n",
                "#", "PLAYER", "TEAM", "GOALS", "ASSISTS"
        );

        int amount = Math.min(limit, players.length);

        for (int i = 0; i < amount; i++) {
            Player player = players[i];
            Team team = getPlayerTeam(player);

            String teamName = team == null
                    ? "Unknown"
                    : team.getName();

            text += String.format(
                    "%-4d %-26s %-22s %5d %7d%n",
                    i + 1,
                    getDisplayName(player),
                    teamName,
                    player.getSeasonGoals(),
                    player.getSeasonAssists()
            );
        }

        return text;
    }

    public String getTopAssistsText(int limit) {
        Player[] players = getPlayersByAssists();

        String text = String.format(
                "%-4s %-26s %-22s %7s %5s%n",
                "#", "PLAYER", "TEAM", "ASSISTS", "GOALS"
        );

        int amount = Math.min(limit, players.length);

        for (int i = 0; i < amount; i++) {
            Player player = players[i];
            Team team = getPlayerTeam(player);

            String teamName = team == null
                    ? "Unknown"
                    : team.getName();

            text += String.format(
                    "%-4d %-26s %-22s %7d %5d%n",
                    i + 1,
                    getDisplayName(player),
                    teamName,
                    player.getSeasonAssists(),
                    player.getSeasonGoals()
            );
        }return text;
    }
}