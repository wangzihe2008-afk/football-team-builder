import java.util.Random;

public class Match {

    private Team homeTeam;
    private Team awayTeam;

    private int homeScore;
    private int awayScore;
    private int currentMinute;

    private boolean halfTime;
    private boolean fullTime;
    private boolean resultRecorded;

    private MatchEvent[] events;
    private int eventCount;

    private Random random;

    private int homeShots;
    private int awayShots;
    private int homeShotsOnTarget;
    private int awayShotsOnTarget;

    private int homePossessionPoints;
    private int awayPossessionPoints;

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        homeScore = 0;
        awayScore = 0;
        currentMinute = 0;

        halfTime = false;
        fullTime = false;
        resultRecorded = false;

        events = new MatchEvent[50];
        eventCount = 0;

        random = new Random();

        homeShots = 0;
        awayShots = 0;
        homeShotsOnTarget = 0;
        awayShotsOnTarget = 0;

        homePossessionPoints = 5;
        awayPossessionPoints = 5;
    }

    public Team getHomeTeam() { return homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public int getHomeScore() { return homeScore; }
    public int getAwayScore() { return awayScore; }
    public int getCurrentMinute() { return currentMinute; }
    public boolean isHalfTime() { return halfTime; }
    public boolean isFullTime() { return fullTime; }
    public int getEventCount() { return eventCount; }

    public MatchEvent getEvent(int index) {
        if (index < 0 || index >= eventCount) return null;
        return events[index];
    }

    private void addEvent(MatchEvent event) {
        if (event == null) return;

        if (eventCount < events.length) {
            events[eventCount++] = event;
        }
    }

    private boolean teamHasCustomPlayer(Team team) {
        Player[] lineup = team.getLineup();
        for (int i = 0; i < lineup.length; i++) {
            if (lineup[i].isCustomPlayer()) return true;
        }
        return false;
    }

    private void advanceMinute() {
        currentMinute += 4 + random.nextInt(7);

        if (!halfTime && currentMinute >= 45) {
            currentMinute = 45;
        } else if (halfTime && currentMinute >= 90) {
            currentMinute = 90;
        }
    }

    public MatchEvent nextEvent() {
        if (fullTime || halfTime) {
            return null;
        }

        advanceMinute();

        if (currentMinute == 45) {
            halfTime = true;
            MatchEvent event = new MatchEvent(45, MatchEvent.HALF_TIME, null, null, null);
            addEvent(event);
            return event;
        }

        if (currentMinute == 90) {
            fullTime = true;
            finishMatch();
            MatchEvent event = new MatchEvent(90, MatchEvent.FULL_TIME, null, null, null);
            addEvent(event);
            return event;
        }

        double homePower = homeTeam.getTeamPower();
        double awayPower = awayTeam.getTeamPower();

        if (teamHasCustomPlayer(homeTeam)) homePower *= 1.20;
        if (teamHasCustomPlayer(awayTeam)) awayPower *= 1.20;

        double totalPower = homePower + awayPower;
        Team attackingTeam = random.nextDouble() * totalPower < homePower ? homeTeam : awayTeam;
        Team defendingTeam = attackingTeam == homeTeam ? awayTeam : homeTeam;

        if (attackingTeam == homeTeam) homePossessionPoints++;
        else awayPossessionPoints++;

        Player shooter = chooseShooter(attackingTeam);
        if (shooter == null) return null;

        if (attackingTeam == homeTeam) homeShots++;
        else awayShots++;

        double avgDefense = averageDefense(defendingTeam);
        Player goalkeeper = defendingTeam.getPlayer(0);
        int keeperDefense = goalkeeper == null ? 0 : goalkeeper.getDefense();

        double goalChance = 0.05
                + shooter.getShooting() * 0.05
                - avgDefense * 0.015
                - keeperDefense * 0.015;

        if (shooter.isCustomPlayer()) {
            goalChance += 0.08;
        }

        if (goalChance < 0.05) goalChance = 0.05;
        if (goalChance > 0.60) goalChance = 0.60;

        if (random.nextDouble() < goalChance) {
            if (attackingTeam == homeTeam) {
                homeScore++;
                homeShotsOnTarget++;
            } else {
                awayScore++;
                awayShotsOnTarget++;
            }

            shooter.addGoal();
            Player assist = chooseAssist(attackingTeam, shooter);
            if (assist != null) assist.addAssist();

            MatchEvent event = new MatchEvent(currentMinute, MatchEvent.GOAL,
                    attackingTeam, shooter, assist);
            addEvent(event);
            return event;
        }

        double onTargetChance = 0.30 + shooter.getShooting() * 0.05;
        if (onTargetChance > 0.90) onTargetChance = 0.90;

        if (random.nextDouble() < onTargetChance) {
            if (attackingTeam == homeTeam) homeShotsOnTarget++;
            else awayShotsOnTarget++;

            MatchEvent event = new MatchEvent(currentMinute, MatchEvent.SAVE,
                    defendingTeam, goalkeeper, null);
            addEvent(event);
            return event;
        }

        double missType = random.nextDouble();
        String type;

        if (missType < 0.10) {
            type = MatchEvent.HIT_POST;
        } else if (missType < 0.40) {
            type = MatchEvent.SHOT_BLOCKED;
        } else if (missType < 0.65) {
            type = MatchEvent.SHOT_OVER;
        } else {
            type = MatchEvent.SHOT_WIDE;
        }

        MatchEvent event = new MatchEvent(currentMinute, type, attackingTeam, shooter, null);
        addEvent(event);
        return event;
    }

    public boolean startSecondHalf() {
        if (!halfTime || fullTime) return false;

        halfTime = false;
        currentMinute = 45;
        return true;
    }

    private Player chooseShooter(Team team) {
        Player[] lineup = team.getLineup();
        int[] weights = new int[lineup.length];
        int total = 0;

        for (int i = 1; i < lineup.length; i++) {
            int weight;

            if (i >= 8) weight = 5;
            else if (i >= 5) weight = 3;
            else weight = 1;

            if (lineup[i].isCustomPlayer()) weight *= 2;

            weights[i] = weight;
            total += weight;
        }

        if (total <= 0) return null;

        int pick = random.nextInt(total);
        int running = 0;

        for (int i = 1; i < lineup.length; i++) {
            running += weights[i];
            if (pick < running) return lineup[i];
        }

        return lineup[9];
    }

    private Player chooseAssist(Team team, Player scorer) {
        if (random.nextDouble() < 0.20) return null;

        Player[] lineup = team.getLineup();
        int[] weights = new int[lineup.length];
        int total = 0;

        for (int i = 1; i < lineup.length; i++) {
            if (lineup[i] == scorer) continue;

            int positionWeight;
            if (i >= 8) positionWeight = 2;
            else if (i >= 5) positionWeight = 3;
            else positionWeight = 1;

            int weight = Math.max(1, lineup[i].getPassing() * positionWeight);
            if (lineup[i].isCustomPlayer()) weight *= 2;

            weights[i] = weight;
            total += weight;
        }

        if (total <= 0) return null;

        int pick = random.nextInt(total);
        int running = 0;

        for (int i = 1; i < lineup.length; i++) {
            running += weights[i];
            if (pick < running) return lineup[i];
        }

        return null;
    }

    private double averageDefense(Team team) {
        Player[] lineup = team.getLineup();
        double total = 0;

        for (int i = 1; i <= 4; i++) {
            total += lineup[i].getDefense();
        }

        return total / 4.0;
    }

    private void finishMatch() {
        if (resultRecorded) return;

        resultRecorded = true;

        homeTeam.recordMatch(homeScore, awayScore);
        awayTeam.recordMatch(awayScore, homeScore);

        Player[] homePlayers = homeTeam.getLineup();
        Player[] awayPlayers = awayTeam.getLineup();

        for (int i = 0; i < homePlayers.length; i++) {
            homePlayers[i].addMatchPlayed();
        }

        for (int i = 0; i < awayPlayers.length; i++) {
            awayPlayers[i].addMatchPlayed();
        }
    }

    public String getScoreText() {
        return homeTeam.getName() + " " + homeScore + " - " + awayScore + " " + awayTeam.getName();
    }

    public String getMatchStatus() {
        if (fullTime) return "FULL TIME";
        if (halfTime) return "HALF TIME";
        if (currentMinute == 0) return "READY";
        return currentMinute + "'";
    }

    public String getMatchStatsText() {
        int possessionTotal = homePossessionPoints + awayPossessionPoints;
        int homePossession = possessionTotal == 0 ? 50 : (int) Math.round(homePossessionPoints * 100.0 / possessionTotal);
        int awayPossession = 100 - homePossession;

        return String.format(
                "%-20s %8s %8s%n"
                        + "%-20s %8d %8d%n"
                        + "%-20s %8d %8d%n"
                        + "%-20s %7d%% %7d%%",
                "", homeTeam.getName(), awayTeam.getName(),
                "Shots", homeShots, awayShots,
                "Shots on Target", homeShotsOnTarget, awayShotsOnTarget,
                "Possession", homePossession, awayPossession
        );
    }
}
