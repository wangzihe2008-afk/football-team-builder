public class MatchEvent {

    public static final String GOAL = "GOAL";
    public static final String SHOT = "SHOT";
    public static final String SHOT_ON_TARGET = "SHOT ON TARGET";
    public static final String SHOT_WIDE = "SHOT WIDE";
    public static final String SHOT_BLOCKED = "SHOT BLOCKED";
    public static final String SHOT_OVER = "SHOT OVER THE BAR";
    public static final String HIT_POST = "HIT THE POST";
    public static final String SAVE = "SAVE";
    public static final String BIG_CHANCE = "BIG CHANCE";
    public static final String HALF_TIME = "HALF TIME";
    public static final String FULL_TIME = "FULL TIME";

    private int minute;
    private String type;
    private Team team;
    private Player player;
    private Player assistPlayer;

    public MatchEvent(int minute, String type, Team team, Player player, Player assistPlayer) {
        this.minute = minute;
        this.type = type;
        this.team = team;
        this.player = player;
        this.assistPlayer = assistPlayer;
    }

    public int getMinute() {
        return minute;
    }

    public String getType() {
        return type;
    }

    public Team getTeam() {
        return team;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getAssistPlayer() {
        return assistPlayer;
    }

    private String playerName(Player player) {
        if (player == null) {
            return "";
        }

        if (player.isCustomPlayer()) {
            return "⭐ " + player.getName();
        }

        return player.getName();
    }

    public String getDisplayText() {
        if (type.equals(HALF_TIME)) {
            return "45'  HALF TIME";
        }

        if (type.equals(FULL_TIME)) {
            return "90'  FULL TIME";
        }

        if (type.equals(GOAL)) {
            String text =
                    minute
                            + "'  GOAL ⚽  "
                            + playerName(player);

            if (team != null) {
                text +=
                        "  |  "
                                + team.getName();
            }

            if (assistPlayer != null) {
                text +=
                        "\n      Assist 👟  "
                                + playerName(assistPlayer);
            }

            return text;
        }

        if (type.equals(SAVE)) {
            return minute
                    + "'  SAVE 🧤  "
                    + playerName(player);
        }

        if (type.equals(SHOT_WIDE)) {
            return minute
                    + "'  SHOT WIDE  "
                    + playerName(player);
        }

        if (type.equals(SHOT_BLOCKED)) {
            return minute
                    + "'  SHOT BLOCKED  "
                    + playerName(player);
        }

        if (type.equals(SHOT_OVER)) {
            return minute
                    + "'  OVER THE BAR  "
                    + playerName(player);
        }

        if (type.equals(HIT_POST)) {
            return minute
                    + "'  HIT THE POST!  "
                    + playerName(player);
        }

        if (type.equals(SHOT_ON_TARGET)) {
            return minute
                    + "'  SHOT ON TARGET  "
                    + playerName(player);
        }

        if (type.equals(BIG_CHANCE)) {
            return minute
                    + "'  BIG CHANCE  "
                    + playerName(player);
        }

        return minute
                + "'  SHOT  "
                + playerName(player);
    }
}