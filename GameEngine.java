public class GameEngine {

    private Team[] teams;

    private Player[] customPlayers;
    private int customPlayerCount;

    private Player controlledPlayer;
    private Team userTeam;

    private boolean setupCompleted;

    private LeagueTable leagueTable;
    private SeasonSchedule seasonSchedule;
    private PlayerStatistics playerStatistics;
    private SeasonAwards seasonAwards;

    private int currentRound;

    private Match currentUserMatch;
    private Match[] currentRoundMatches;

    private boolean roundStarted;
    private boolean roundCompleted;

    private boolean seasonAwardsCalculated;
    private boolean championTrophyAwarded;

    private int availableSkillPoints;
    private int nextUpgradeMatchTarget;

    public GameEngine() {

        teams =
                PresetTeam.createTeams();

        customPlayers =
                new Player[120];

        customPlayerCount = 0;

        controlledPlayer = null;
        userTeam = null;

        setupCompleted = false;

        leagueTable =
                new LeagueTable(
                        teams
                );

        seasonSchedule =
                new SeasonSchedule(
                        teams
                );

        playerStatistics =
                new PlayerStatistics(
                        teams
                );

        seasonAwards =
                new SeasonAwards(
                        teams,
                        leagueTable,
                        playerStatistics
                );

        currentRound = 0;

        currentUserMatch = null;
        currentRoundMatches = null;

        roundStarted = false;
        roundCompleted = false;

        seasonAwardsCalculated = false;
        championTrophyAwarded = false;

        availableSkillPoints = 0;
        nextUpgradeMatchTarget = 3;
    }

    public Team[] getTeams() {

        return teams;
    }

    public Player getCustomPlayer(
            int index) {

        if (index < 0
                || index >= customPlayerCount) {

            return null;
        }

        return customPlayers[index];
    }

    public int getCustomPlayerCount() {

        return customPlayerCount;
    }

    public Player getControlledPlayer() {

        return controlledPlayer;
    }

    public Team getUserTeam() {

        return userTeam;
    }

    public LeagueTable getLeagueTable() {

        return leagueTable;
    }

    public SeasonSchedule getSeasonSchedule() {

        return seasonSchedule;
    }

    public PlayerStatistics getPlayerStatistics() {

        return playerStatistics;
    }

    public SeasonAwards getSeasonAwards() {

        return seasonAwards;
    }

    public boolean isSetupCompleted() {

        return setupCompleted;
    }

    public boolean hasControlledPlayer() {

        return controlledPlayer != null;
    }

    public boolean hasCustomPlayer() {

        return customPlayerCount > 0;
    }

    public boolean hasCustomPlayer(
            Team team) {

        if (team == null) {

            return false;
        }

        Player[] lineup =
                team.getLineup();

        for (int i = 0;
             i < lineup.length;
             i++) {

            if (lineup[i] != null
                    && lineup[i]
                    .isCustomPlayer()) {

                return true;
            }
        }

        return false;
    }

    public Team findPlayerTeam(
            Player player) {

        if (player == null) {

            return null;
        }

        for (int i = 0;
             i < teams.length;
             i++) {

            Player[] lineup =
                    teams[i]
                            .getLineup();

            for (int j = 0;
                 j < lineup.length;
                 j++) {

                if (lineup[j]
                        == player) {

                    return teams[i];
                }
            }
        }

        return null;
    }

    public Team getCustomPlayerTeam(
            int index) {

        Player player =
                getCustomPlayer(
                        index
                );

        return findPlayerTeam(
                player
        );
    }

    public String createCustomPlayer(
            String name,
            int shooting,
            int passing,
            int defense,
            int teamIndex,
            int replaceSlot) {

        if (setupCompleted) {

            return "Player setup has already been completed.";
        }

        if (name == null
                || name.trim().isEmpty()) {

            return "Enter a player name.";
        }

        if (shooting < 0
                || shooting > 15
                || passing < 0
                || passing > 15
                || defense < 0
                || defense > 15) {

            return "Each skill must be between 0 and 15.";
        }

        if (shooting
                + passing
                + defense
                != 20) {

            return "You must use exactly 20 skill points.";
        }

        if (teamIndex < 0
                || teamIndex >= teams.length) {

            return "Choose a valid club.";
        }

        if (replaceSlot <= 0
                || replaceSlot >= Team.POSITIONS.length) {

            return "Choose a valid outfield player to replace.";
        }

        if (customPlayerCount
                >= customPlayers.length) {

            return "Maximum number of custom players reached.";
        }

        Team team =
                teams[teamIndex];

        Player oldPlayer =
                team.getPlayer(
                        replaceSlot
                );

        if (oldPlayer == null) {

            return "The selected player could not be found.";
        }

        if (oldPlayer.isCustomPlayer()) {

            return "You cannot replace a custom player.";
        }

        Player customPlayer =
                new Player(
                        name.trim(),
                        shooting,
                        passing,
                        defense,
                        true
                );

        boolean replaced =
                team.replacePlayer(
                        replaceSlot,
                        customPlayer
                );

        if (!replaced) {

            return "Could not create the player.";
        }

        customPlayers[
                customPlayerCount
                ] =
                customPlayer;

        customPlayerCount++;

        return customPlayer.getName()
                + " created for "
                + team.getName()
                + ".";
    }

    public String completeSetup(
            int controlledPlayerIndex) {

        if (setupCompleted) {

            return "Setup has already been completed.";
        }

        if (customPlayerCount == 0) {

            return "Create at least one player first.";
        }

        if (controlledPlayerIndex < 0
                || controlledPlayerIndex
                >= customPlayerCount) {

            return "Choose a valid controlled player.";
        }

        controlledPlayer =
                customPlayers[
                        controlledPlayerIndex
                        ];

        userTeam =
                findPlayerTeam(
                        controlledPlayer
                );

        if (userTeam == null) {

            controlledPlayer = null;

            return "Could not find the controlled player's club.";
        }

        setupCompleted = true;

        availableSkillPoints = 0;
        nextUpgradeMatchTarget = 3;

        currentRound = 0;

        currentUserMatch = null;
        currentRoundMatches = null;

        roundStarted = false;
        roundCompleted = false;

        seasonAwardsCalculated = false;
        championTrophyAwarded = false;

        return "Setup complete. You control "
                + controlledPlayer.getName()
                + " at "
                + userTeam.getName()
                + ".";
    }

    public boolean swapUserTeamPlayers(
            int firstSlot,
            int secondSlot) {

        if (!setupCompleted
                || userTeam == null) {

            return false;
        }

        if (isSeasonFinished()) {

            return false;
        }

        if (roundStarted
                && !roundCompleted) {

            return false;
        }

        return userTeam.swapPlayers(
                firstSlot,
                secondSlot
        );
    }

    public String getCustomPlayerPosition() {

        if (!setupCompleted
                || controlledPlayer == null
                || userTeam == null) {

            return "Unknown";
        }

        Player[] lineup =
                userTeam.getLineup();

        for (int i = 0;
             i < lineup.length;
             i++) {

            if (lineup[i]
                    == controlledPlayer) {

                return Team.POSITIONS[i];
            }
        }

        return "Unknown";
    }

    public int getAvailableSkillPoints() {

        return availableSkillPoints;
    }

    public boolean hasAvailableSkillPoint() {

        return availableSkillPoints > 0;
    }

    public int getNextUpgradeMatchTarget() {

        return nextUpgradeMatchTarget;
    }

    private void checkSkillPointReward() {

        if (controlledPlayer == null) {

            return;
        }

        while (nextUpgradeMatchTarget <= 15
                && controlledPlayer
                .getCareerMatches()
                >= nextUpgradeMatchTarget) {

            availableSkillPoints++;

            nextUpgradeMatchTarget += 3;
        }
    }

    public String upgradeCustomPlayer(
            String skill) {

        if (!setupCompleted
                || controlledPlayer == null) {

            return "Complete player setup first.";
        }

        if (availableSkillPoints <= 0) {

            return "No skill points available.";
        }

        boolean upgraded =
                controlledPlayer
                        .addSkillPoint(
                                skill
                        );

        if (!upgraded) {

            if (skill != null) {

                if (skill.equalsIgnoreCase(
                        "Shooting"
                )
                        && controlledPlayer
                        .getShooting()
                        >= 15) {

                    return "Shooting is already at the maximum.";
                }

                if (skill.equalsIgnoreCase(
                        "Passing"
                )
                        && controlledPlayer
                        .getPassing()
                        >= 15) {

                    return "Passing is already at the maximum.";
                }

                if (skill.equalsIgnoreCase(
                        "Defense"
                )
                        && controlledPlayer
                        .getDefense()
                        >= 15) {

                    return "Defense is already at the maximum.";
                }
            }

            return "Could not upgrade that skill.";
        }

        availableSkillPoints--;

        return skill
                + " increased to "
                + getControlledSkillValue(
                skill
        )
                + ".";
    }

    private int getControlledSkillValue(
            String skill) {

        if (controlledPlayer == null
                || skill == null) {

            return 0;
        }

        if (skill.equalsIgnoreCase(
                "Shooting"
        )) {

            return controlledPlayer
                    .getShooting();
        }

        if (skill.equalsIgnoreCase(
                "Passing"
        )) {

            return controlledPlayer
                    .getPassing();
        }

        if (skill.equalsIgnoreCase(
                "Defense"
        )) {

            return controlledPlayer
                    .getDefense();
        }

        return 0;
    }

    public int getCurrentRoundIndex() {

        return currentRound;
    }

    public int getCurrentRoundNumber() {

        return currentRound + 1;
    }

    public boolean isRoundStarted() {

        return roundStarted;
    }

    public boolean isRoundCompleted() {

        return roundCompleted;
    }

    public Match getCurrentUserMatch() {

        return currentUserMatch;
    }

    public Match[] getCurrentRoundMatches() {

        return currentRoundMatches;
    }

    public Fixture getUserFixture(
            int round) {

        if (!setupCompleted
                || userTeam == null) {

            return null;
        }

        return seasonSchedule
                .getTeamFixture(
                        round,
                        userTeam
                );
    }

    public boolean startCurrentRound() {

        if (!setupCompleted) {

            return false;
        }

        if (isSeasonFinished()) {

            return false;
        }

        if (roundStarted) {

            return false;
        }

        if (currentRound < 0
                || currentRound
                >= SeasonSchedule.TOTAL_ROUNDS) {

            return false;
        }

        Fixture[] fixtures =
                seasonSchedule
                        .getRound(
                                currentRound
                        );

        if (fixtures == null) {

            return false;
        }

        currentRoundMatches =
                new Match[
                        fixtures.length
                        ];

        currentUserMatch = null;

        for (int i = 0;
             i < fixtures.length;
             i++) {

            Fixture fixture =
                    fixtures[i];

            Match match =
                    new Match(
                            fixture.getHomeTeam(),
                            fixture.getAwayTeam()
                    );

            currentRoundMatches[i] =
                    match;

            if (fixture.containsTeam(
                    userTeam
            )) {

                currentUserMatch =
                        match;
            }
        }

        if (currentUserMatch == null) {

            currentRoundMatches = null;

            return false;
        }

        roundStarted = true;
        roundCompleted = false;

        return true;
    }

    public MatchEvent nextUserMatchEvent() {

        if (!roundStarted
                || currentUserMatch == null) {

            return null;
        }

        if (currentUserMatch.isFullTime()) {

            return null;
        }

        MatchEvent event =
                currentUserMatch
                        .nextEvent();

        if (currentUserMatch
                .isFullTime()) {

            checkSkillPointReward();
        }

        return event;
    }

    public boolean startUserSecondHalf() {

        if (!roundStarted
                || currentUserMatch == null) {

            return false;
        }

        return currentUserMatch
                .startSecondHalf();
    }

    public boolean skipCurrentUserMatch() {

        if (!roundStarted
                || currentUserMatch == null) {

            return false;
        }

        if (currentUserMatch.isFullTime()) {

            return false;
        }

        int safety = 0;

        while (!currentUserMatch.isFullTime()
                && safety < 100) {

            if (currentUserMatch.isHalfTime()) {

                startUserSecondHalf();

            } else {

                nextUserMatchEvent();
            }

            safety++;
        }

        if (currentUserMatch.isFullTime()) {

            checkSkillPointReward();

            return true;
        }

        return false;
    }

    private void simulateMatch(
            Match match) {

        if (match == null
                || match.isFullTime()) {

            return;
        }

        int safety = 0;

        while (!match.isFullTime()
                && safety < 100) {

            if (match.isHalfTime()) {

                match.startSecondHalf();

            } else {

                match.nextEvent();
            }

            safety++;
        }
    }

    private void simulateOtherMatches() {

        if (currentRoundMatches == null) {

            return;
        }

        for (int i = 0;
             i < currentRoundMatches.length;
             i++) {

            Match match =
                    currentRoundMatches[i];

            if (match == null) {

                continue;
            }

            if (match == currentUserMatch) {

                continue;
            }

            simulateMatch(
                    match
            );
        }
    }

    public boolean completeCurrentRound() {

        if (!roundStarted
                || roundCompleted) {

            return false;
        }

        if (currentUserMatch == null
                || !currentUserMatch
                .isFullTime()) {

            return false;
        }

        checkSkillPointReward();

        simulateOtherMatches();

        roundCompleted = true;

        if (currentRound
                == SeasonSchedule.TOTAL_ROUNDS - 1) {

            calculateSeasonAwards();
        }

        return true;
    }

    public String getCurrentRoundResultsText() {

        if (currentRoundMatches == null) {

            return "No round results available.";
        }

        String text =
                "ROUND "
                        + (currentRound + 1)
                        + " RESULTS\n\n";

        for (int i = 0;
             i < currentRoundMatches.length;
             i++) {

            Match match =
                    currentRoundMatches[i];

            if (match == null) {

                continue;
            }

            text +=
                    match.getScoreText()
                            + "\n";
        }

        return text;
    }

    public boolean advanceToNextRound() {

        if (!roundCompleted) {

            return false;
        }

        if (isSeasonFinished()) {

            return false;
        }

        currentRound++;

        currentUserMatch = null;
        currentRoundMatches = null;

        roundStarted = false;
        roundCompleted = false;

        return true;
    }

    public int getUserTeamPosition() {

        if (!setupCompleted
                || userTeam == null) {

            return -1;
        }

        return leagueTable
                .getTeamPosition(
                        userTeam
                );
    }

    public boolean isSeasonFinished() {

        return setupCompleted
                && currentRound
                == SeasonSchedule.TOTAL_ROUNDS - 1
                && roundCompleted;
    }

    private void calculateSeasonAwards() {

        if (seasonAwardsCalculated) {

            return;
        }

        seasonAwards.calculateAwards();

        seasonAwardsCalculated = true;

        if (!championTrophyAwarded) {

            Team champion =
                    seasonAwards
                            .getChampion();

            if (champion != null) {

                champion.addTrophy();
            }

            championTrophyAwarded = true;
        }
    }

    public boolean isSeasonAwardsCalculated() {

        return seasonAwardsCalculated;
    }
}