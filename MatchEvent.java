public class MatchEvent {

    public static final String GOAL = "GOAL";
    public static final String SHOT = "SHOT";
    public static final String SHOT_ON_TARGET = "SHOT_ON_TARGET";
    public static final String SHOT_WIDE = "SHOT_WIDE";
    public static final String SHOT_BLOCKED = "SHOT_BLOCKED";
    public static final String SHOT_OVER = "SHOT_OVER";
    public static final String HIT_POST = "HIT_POST";
    public static final String SAVE = "SAVE";
    public static final String BIG_CHANCE = "BIG_CHANCE";
    public static final String HALF_TIME = "HALF_TIME";
    public static final String FULL_TIME = "FULL_TIME";

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

    public int getMinute() { return minute; }
    public String getType() { return type; }
    public Team getTeam() { return team; }
    public Player getPlayer() { return player; }
    public Player getAssistPlayer() { return assistPlayer; }

    private String playerName(Player p) {
        if (p == null) return "";
        return p.isCustomPlayer() ? "⭐ " + p.getName() : p.getName();
    }

    public String getDisplayText() {
        if (type.equals(HALF_TIME)) {
            return "45'  HALF TIME";
        }

        if (type.equals(FULL_TIME)) {
            return "90'  FULL TIME";
        }

        String prefix = minute + "'  ";

        if (type.equals(GOAL)) {
            String text = prefix + "GOAL ⚽  " + playerName(player)
                    + " | " + (team == null ? "" : team.getName());

            if (assistPlayer != null) {
                text += "\n      Assist 👟  " + playerName(assistPlayer);
            }

            return text;
        }

        if (type.equals(SAVE)) {
            return prefix + "SAVE 🧤  " + playerName(player);
        }

        if (type.equals(HIT_POST)) {
            return prefix + "HIT THE POST!  " + playerName(player);
        }

        if (type.equals(SHOT_BLOCKED)) {
            return prefix + "SHOT BLOCKED  " + playerName(player);
        }

        if (type.equals(SHOT_OVER)) {
            return prefix + "SHOT OVER  " + playerName(player);
        }

        if (type.equals(SHOT_WIDE)) {
            return prefix + "SHOT WIDE  " + playerName(player);
        }

        if (type.equals(SHOT_ON_TARGET)) {
            return prefix + "SHOT ON TARGET  " + playerName(player);
        }

        if (type.equals(BIG_CHANCE)) {
            return prefix + "BIG CHANCE  " + playerName(player);
        }

        return prefix + type + "  " + playerName(player);
    }
}
