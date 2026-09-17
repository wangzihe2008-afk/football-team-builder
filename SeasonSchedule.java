public class SeasonSchedule {

    public static final int TOTAL_ROUNDS = 15;
    public static final int MATCHES_PER_ROUND = 6;

    private Team[] teams;
    private Fixture[][] schedule;

    public SeasonSchedule(Team[] teams) {
        this.teams = teams;
        schedule = new Fixture[TOTAL_ROUNDS][MATCHES_PER_ROUND];
        createSchedule();
    }

    private void createSchedule() {
        Team[] rotation = new Team[teams.length];

        for (int i = 0; i < teams.length; i++) {
            rotation[i] = teams[i];
        }

        for (int round = 0; round < 11; round++) {
            for (int match = 0; match < MATCHES_PER_ROUND; match++) {
                Team first = rotation[match];
                Team second = rotation[teams.length - 1 - match];

                if ((round + match) % 2 == 0) {
                    schedule[round][match] = new Fixture(first, second);
                } else {
                    schedule[round][match] = new Fixture(second, first);
                }
            }

            Team last = rotation[rotation.length - 1];
            for (int i = rotation.length - 1; i >= 2; i--) {
                rotation[i] = rotation[i - 1];
            }
            rotation[1] = last;
        }

        for (int round = 11; round < TOTAL_ROUNDS; round++) {
            int sourceRound = round - 11;

            for (int match = 0; match < MATCHES_PER_ROUND; match++) {
                Fixture source = schedule[sourceRound][match];
                schedule[round][match] = new Fixture(source.getAwayTeam(), source.getHomeTeam());
            }
        }
    }

    public Fixture getFixture(int round, int match) {
        if (round < 0 || round >= TOTAL_ROUNDS || match < 0 || match >= MATCHES_PER_ROUND) {
            return null;
        }
        return schedule[round][match];
    }

    public Fixture[] getRound(int round) {
        if (round < 0 || round >= TOTAL_ROUNDS) {
            return null;
        }

        Fixture[] copy = new Fixture[MATCHES_PER_ROUND];
        for (int i = 0; i < MATCHES_PER_ROUND; i++) {
            copy[i] = schedule[round][i];
        }
        return copy;
    }

    public Fixture getTeamFixture(int round, Team team) {
        if (round < 0 || round >= TOTAL_ROUNDS || team == null) {
            return null;
        }

        for (int i = 0; i < MATCHES_PER_ROUND; i++) {
            if (schedule[round][i].containsTeam(team)) {
                return schedule[round][i];
            }
        }

        return null;
    }

    public String getRoundText(int round) {
        if (round < 0 || round >= TOTAL_ROUNDS) {
            return "Invalid round";
        }

        String text = "ROUND " + (round + 1) + "\n";

        for (int i = 0; i < MATCHES_PER_ROUND; i++) {
            text += schedule[round][i].getFixtureText() + "\n";
        }

        return text;
    }
}
