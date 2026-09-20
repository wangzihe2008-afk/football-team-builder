public class SeasonSchedule {

    public static final int TOTAL_ROUNDS = 15;
    public static final int MATCHES_PER_ROUND = 6;

    private Team[] teams;
    private Fixture[][] rounds;

    public SeasonSchedule(Team[] teams) {
        this.teams = teams;
        rounds = new Fixture[TOTAL_ROUNDS][MATCHES_PER_ROUND];
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
                Team second = rotation[rotation.length - 1 - match];

                if ((round + match) % 2 == 0) {
                    rounds[round][match] = new Fixture(
                            first,
                            second
                    );
                } else {
                    rounds[round][match] = new Fixture(
                            second,
                            first
                    );
                }
            }

            rotateTeams(rotation);
        }

        for (int round = 11; round < TOTAL_ROUNDS; round++) {
            int originalRound = round - 11;

            for (int match = 0; match < MATCHES_PER_ROUND; match++) {
                Fixture originalFixture =
                        rounds[originalRound][match];

                rounds[round][match] = new Fixture(
                        originalFixture.getAwayTeam(),
                        originalFixture.getHomeTeam()
                );
            }
        }
    }

    private void rotateTeams(Team[] rotation) {
        Team last = rotation[rotation.length - 1];

        for (int i = rotation.length - 1; i > 1; i--) {
            rotation[i] = rotation[i - 1];
        }

        rotation[1] = last;
    }

    public Fixture getFixture(
            int roundIndex,
            int matchIndex) {

        if (roundIndex < 0
                || roundIndex >= TOTAL_ROUNDS) {

            return null;
        }

        if (matchIndex < 0
                || matchIndex >= MATCHES_PER_ROUND) {

            return null;
        }

        return rounds[roundIndex][matchIndex];
    }

    public Fixture[] getRound(int roundIndex) {
        if (roundIndex < 0
                || roundIndex >= TOTAL_ROUNDS) {

            return null;
        }

        Fixture[] result =
                new Fixture[MATCHES_PER_ROUND];

        for (int i = 0;
             i < MATCHES_PER_ROUND;
             i++) {

            result[i] =
                    rounds[roundIndex][i];
        }

        return result;
    }

    public Fixture getTeamFixture(
            int roundIndex,
            Team team) {

        if (roundIndex < 0
                || roundIndex >= TOTAL_ROUNDS
                || team == null) {

            return null;
        }

        for (int i = 0;
             i < MATCHES_PER_ROUND;
             i++) {

            if (rounds[roundIndex][i]
                    .containsTeam(team)) {

                return rounds[roundIndex][i];
            }
        }

        return null;
    }

    public String getRoundText(int roundIndex) {
        if (roundIndex < 0
                || roundIndex >= TOTAL_ROUNDS) {

            return "Invalid round.";
        }

        String text =
                "ROUND "
                        + (roundIndex + 1)
                        + "\n";

        for (int i = 0;
             i < MATCHES_PER_ROUND;
             i++) {

            text += rounds[roundIndex][i]
                    .getFixtureText()
                    + "\n";
        }

        return text;
    }

    public String getFullScheduleText() {
        String text = "";

        for (int round = 0;
             round < TOTAL_ROUNDS;
             round++) {

            text += getRoundText(round)
                    + "\n";
        }

        return text;
    }
}