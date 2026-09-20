import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;

public class FootballGUI extends JFrame
        implements ActionListener, ChangeListener {

    private static final Color NAVY = new Color(15, 23, 42);
    private static final Color DEEP_NAVY = new Color(7, 18, 32);
    private static final Color MATCH_NAVY = new Color(9, 25, 40);
    private static final Color DARK_GREEN = new Color(20, 83, 45);
    private static final Color LIGHT_GREEN = new Color(220, 252, 231);
    private static final Color CARD = new Color(250, 252, 251);
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Color GOLD = new Color(250, 204, 21);
    private static final Color LIGHT_GOLD = new Color(254, 249, 195);
    private static final Color SILVER = new Color(203, 213, 225);
    private static final Color BRONZE = new Color(217, 119, 6);
    private static final Color LIVE_RED = new Color(239, 68, 68);
    private static final Color SKY = new Color(56, 189, 248);

    private static final String CREATE_MUSIC =
            "audio/create.wav";

    private static final String[] MAIN_PLAYLIST = {
            "audio/main1.wav",
            "audio/main2.wav",
            "audio/main3.wav",
            "audio/main4.wav"
    };

    private GameEngine game;
    private AudioManager audioManager;

    private JTabbedPane tabs;
    private JButton exitButton;

    private JLabel homeReminderLabel;
    private JLabel homeRoundLabel;
    private JLabel homeClubLabel;
    private JLabel homePositionLabel;
    private JLabel pointsLabel;
    private JLabel homePlayerLabel;
    private JLabel homeSkillsLabel;
    private JLabel homeCareerLabel;
    private JLabel homeNextMatchLabel;
    private JLabel nextPointLabel;
    private JLabel upgradeMessageLabel;

    private JButton upgradeShootingButton;
    private JButton upgradePassingButton;
    private JButton upgradeDefenseButton;

    private JTextField nameField;
    private JSpinner shootingSpinner;
    private JSpinner passingSpinner;
    private JSpinner defenseSpinner;
    private JLabel skillTotalLabel;
    private JComboBox<String> teamComboBox;
    private JComboBox<String> replaceComboBox;
    private JButton createButton;
    private JLabel createMessageLabel;
    private JTextArea createdPlayersArea;
    private JComboBox<String> controlledPlayerComboBox;
    private JButton completeSetupButton;
    private JLabel setupMessageLabel;

    private JButton[] squadButtons;
    private JLabel squadTeamLabel;
    private JLabel squadPowerLabel;
    private JLabel selectedLabel;
    private JLabel squadMessageLabel;
    private int selectedSlot = -1;

    private JLabel matchRoundLabel;
    private JLabel fixtureLabel;
    private JLabel scoreLabel;
    private JLabel matchStatusLabel;
    private JLabel matchMessageLabel;
    private JLabel matchFeedStateLabel;
    private JLabel matchFeedMinuteLabel;

    private JTextPane eventPane;
    private MatchStatsPanel matchStatsPanel;

    private JButton startMatchButton;
    private JButton nextEventButton;
    private JButton secondHalfButton;
    private JButton skipMatchButton;
    private JButton completeRoundButton;
    private JButton nextRoundButton;

    private JPanel leagueContentPanel;

    private JPanel statsContentPanel;
    private JButton goalsButton;
    private JButton assistsButton;
    private boolean showingGoals = true;

    private JPanel awardsContentPanel;

    private int lastResultEffectRound = -1;
    private Timer resultEffectTimer;

    public FootballGUI() {

        game = new GameEngine();
        audioManager = new AudioManager();

        setTitle("Football Team Builder");

        setSize(
                1200,
                800
        );

        setMinimumSize(
                new Dimension(
                        1050,
                        700
                )
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        tabs = new JTabbedPane();

        tabs.setFont(
                font(
                        15,
                        true
                )
        );

        tabs.setBackground(
                Color.WHITE
        );

        tabs.setForeground(
                TEXT
        );

        tabs.addTab(
                "Home",
                createHomePage()
        );

        tabs.addTab(
                "Create Player",
                createPlayerPage()
        );

        tabs.addTab(
                "Squad",
                createSquadPage()
        );

        tabs.addTab(
                "Match",
                createMatchPage()
        );

        tabs.addTab(
                "League",
                createLeaguePage()
        );

        tabs.addTab(
                "Player Stats",
                createStatsPage()
        );

        tabs.addTab(
                "Awards",
                createAwardsPage()
        );

        tabs.addChangeListener(this);

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.add(
                tabs,
                BorderLayout.CENTER
        );

        JPanel exitPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                8
                        )
                );

        exitPanel.setBackground(
                NAVY
        );

        exitButton =
                new JButton(
                        "Exit Game"
                );

        exitButton.setFont(
                font(
                        13,
                        true
                )
        );

        exitButton.setForeground(
                Color.WHITE
        );

        exitButton.setBackground(
                new Color(
                        185,
                        28,
                        28
                )
        );

        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(true);
        exitButton.setFocusPainted(false);

        exitButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        exitButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        127,
                                        29,
                                        29
                                ),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                20,
                                8,
                                20
                        )
                )
        );

        exitButton.addActionListener(this);

        exitPanel.add(
                exitButton
        );

        mainPanel.add(
                exitPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        addWindowListener(
                new WindowAdapter() {

                    public void windowClosing(
                            WindowEvent e) {

                        audioManager.stop();
                    }
                }
        );

        refreshAll();

        audioManager.playLoop(
                CREATE_MUSIC
        );

        setVisible(true);
    }

    private JPanel createHomePage() {

        PatternPanel page =
                new PatternPanel();

        page.setLayout(
                new BorderLayout()
        );

        page.add(
                createHero(
                        "FOOTBALL TEAM BUILDER",
                        "CREATE YOUR PLAYERS  •  BUILD YOUR SQUAD  •  WIN THE LEAGUE"
                ),
                BorderLayout.NORTH
        );

        RoundedPanel mainCard =
                new RoundedPanel(
                        CARD
                );

        mainCard.setLayout(
                new BorderLayout(
                        20,
                        16
                )
        );

        mainCard.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        30,
                        22,
                        30
                )
        );

        mainCard.setPreferredSize(
                new Dimension(
                        900,
                        520
                )
        );

        homeReminderLabel =
                label(
                        "",
                        14,
                        true,
                        new Color(
                                133,
                                77,
                                14
                        ),
                        SwingConstants.CENTER
                );

        homeReminderLabel.setOpaque(true);

        homeReminderLabel.setBackground(
                LIGHT_GOLD
        );

        homeReminderLabel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        234,
                                        179,
                                        8
                                ),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                9,
                                10,
                                9,
                                10
                        )
                )
        );

        JPanel statusRow =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                12,
                                0
                        )
                );

        statusRow.setOpaque(false);

        homeRoundLabel =
                createInfoCard(
                        "ROUND",
                        "--"
                );

        homeClubLabel =
                createInfoCard(
                        "CLUB",
                        "--"
                );

        homePositionLabel =
                createInfoCard(
                        "LEAGUE POSITION",
                        "--"
                );

        pointsLabel =
                createInfoCard(
                        "SKILL POINTS",
                        "0"
                );

        statusRow.add(
                homeRoundLabel.getParent()
        );

        statusRow.add(
                homeClubLabel.getParent()
        );

        statusRow.add(
                homePositionLabel.getParent()
        );

        statusRow.add(
                pointsLabel.getParent()
        );

        JPanel top =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        top.setOpaque(false);

        top.add(
                homeReminderLabel,
                BorderLayout.NORTH
        );

        top.add(
                statusRow,
                BorderLayout.CENTER
        );

        JPanel playerPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                5,
                                5
                        )
                );

        playerPanel.setOpaque(false);

        homePlayerLabel =
                label(
                        "",
                        27,
                        true,
                        TEXT,
                        SwingConstants.CENTER
                );

        homeSkillsLabel =
                label(
                        "",
                        18,
                        false,
                        TEXT,
                        SwingConstants.CENTER
                );

        homeCareerLabel =
                label(
                        "",
                        16,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        homeNextMatchLabel =
                label(
                        "",
                        16,
                        true,
                        DARK_GREEN,
                        SwingConstants.CENTER
                );

        nextPointLabel =
                label(
                        "",
                        15,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        playerPanel.add(
                homePlayerLabel
        );

        playerPanel.add(
                homeSkillsLabel
        );

        playerPanel.add(
                homeCareerLabel
        );

        playerPanel.add(
                homeNextMatchLabel
        );

        playerPanel.add(
                nextPointLabel
        );

        JPanel upgradePanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                12,
                                0
                        )
                );

        upgradePanel.setOpaque(false);

        upgradeShootingButton =
                createButton(
                        "+ Shooting"
                );

        upgradePassingButton =
                createButton(
                        "+ Passing"
                );

        upgradeDefenseButton =
                createButton(
                        "+ Defense"
                );

        upgradeShootingButton
                .addActionListener(this);

        upgradePassingButton
                .addActionListener(this);

        upgradeDefenseButton
                .addActionListener(this);

        upgradePanel.add(
                upgradeShootingButton
        );

        upgradePanel.add(
                upgradePassingButton
        );

        upgradePanel.add(
                upgradeDefenseButton
        );

        upgradeMessageLabel =
                label(
                        "",
                        14,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        JPanel bottom =
                new JPanel(
                        new BorderLayout(
                                0,
                                8
                        )
                );

        bottom.setOpaque(false);

        bottom.add(
                upgradePanel,
                BorderLayout.CENTER
        );

        bottom.add(
                upgradeMessageLabel,
                BorderLayout.SOUTH
        );

        mainCard.add(
                top,
                BorderLayout.NORTH
        );

        mainCard.add(
                playerPanel,
                BorderLayout.CENTER
        );

        mainCard.add(
                bottom,
                BorderLayout.SOUTH
        );

        page.add(
                center(mainCard),
                BorderLayout.CENTER
        );

        return page;
    }

    private JPanel createPlayerPage() {

        PatternPanel page =
                new PatternPanel();

        page.setLayout(
                new BorderLayout()
        );

        page.add(
                createHero(
                        "PLAYER SETUP",
                        "CREATE PLAYERS  •  CHOOSE ONE TO CONTROL  •  COMPLETE SETUP"
                ),
                BorderLayout.NORTH
        );

        JPanel content =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );

        content.setOpaque(false);

        content.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        25,
                        30
                )
        );

        RoundedPanel form =
                new RoundedPanel(
                        CARD
                );

        form.setLayout(
                new GridBagLayout()
        );

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        30,
                        22,
                        30
                )
        );

        GridBagConstraints c =
                new GridBagConstraints();

        c.insets =
                new Insets(
                        7,
                        10,
                        7,
                        10
                );

        c.fill =
                GridBagConstraints.HORIZONTAL;

        nameField =
                new JTextField();

        styleInput(
                nameField
        );

        shootingSpinner =
                createSpinner(7);

        passingSpinner =
                createSpinner(7);

        defenseSpinner =
                createSpinner(6);

        shootingSpinner
                .addChangeListener(this);

        passingSpinner
                .addChangeListener(this);

        defenseSpinner
                .addChangeListener(this);

        skillTotalLabel =
                label(
                        "20 / 20",
                        17,
                        true,
                        DARK_GREEN,
                        SwingConstants.LEFT
                );

        Team[] teams =
                game.getTeams();

        String[] names =
                new String[
                        teams.length
                        ];

        for (int i = 0;
             i < teams.length;
             i++) {

            names[i] =
                    teams[i]
                            .getName();
        }

        teamComboBox =
                new JComboBox<String>(
                        names
                );

        replaceComboBox =
                new JComboBox<String>();

        styleInput(
                teamComboBox
        );

        styleInput(
                replaceComboBox
        );

        teamComboBox
                .addActionListener(this);

        createButton =
                createButton(
                        "CREATE PLAYER"
                );

        createButton
                .addActionListener(this);

        createMessageLabel =
                label(
                        "Use exactly 20 skill points.",
                        13,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        int row = 0;

        addFormRow(
                form,
                c,
                row++,
                "PLAYER NAME",
                nameField
        );

        addFormRow(
                form,
                c,
                row++,
                "SHOOTING",
                shootingSpinner
        );

        addFormRow(
                form,
                c,
                row++,
                "PASSING",
                passingSpinner
        );

        addFormRow(
                form,
                c,
                row++,
                "DEFENSE",
                defenseSpinner
        );

        addFormRow(
                form,
                c,
                row++,
                "POINTS USED",
                skillTotalLabel
        );

        addFormRow(
                form,
                c,
                row++,
                "CLUB",
                teamComboBox
        );

        addFormRow(
                form,
                c,
                row++,
                "REPLACE",
                replaceComboBox
        );

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.weightx = 1;

        form.add(
                createButton,
                c
        );

        c.gridy =
                row + 1;

        form.add(
                createMessageLabel,
                c
        );

        RoundedPanel setupCard =
                new RoundedPanel(
                        CARD
                );

        setupCard.setLayout(
                new BorderLayout(
                        0,
                        14
                )
        );

        setupCard.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        25,
                        22,
                        25
                )
        );

        JLabel createdTitle =
                label(
                        "CREATED PLAYERS",
                        18,
                        true,
                        DARK_GREEN,
                        SwingConstants.LEFT
                );

        createdPlayersArea =
                textArea();

        createdPlayersArea.setFont(
                font(
                        14,
                        false
                )
        );

        JScrollPane createdScroll =
                new JScrollPane(
                        createdPlayersArea
                );

        JPanel controlPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                0,
                                7
                        )
                );

        controlPanel.setOpaque(false);

        JLabel controlTitle =
                label(
                        "CONTROLLED PLAYER",
                        13,
                        true,
                        MUTED,
                        SwingConstants.LEFT
                );

        controlledPlayerComboBox =
                new JComboBox<String>();

        styleInput(
                controlledPlayerComboBox
        );

        completeSetupButton =
                createGoldButton(
                        "COMPLETE SETUP"
                );

        completeSetupButton
                .addActionListener(this);

        setupMessageLabel =
                label(
                        "Create at least one player first.",
                        13,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        JLabel warningLabel =
                label(
                        "After setup is completed, players cannot be changed.",
                        12,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        controlPanel.add(
                controlTitle
        );

        controlPanel.add(
                controlledPlayerComboBox
        );

        controlPanel.add(
                completeSetupButton
        );

        controlPanel.add(
                setupMessageLabel
        );

        controlPanel.add(
                warningLabel
        );

        setupCard.add(
                createdTitle,
                BorderLayout.NORTH
        );

        setupCard.add(
                createdScroll,
                BorderLayout.CENTER
        );

        setupCard.add(
                controlPanel,
                BorderLayout.SOUTH
        );

        content.add(
                form
        );

        content.add(
                setupCard
        );

        page.add(
                content,
                BorderLayout.CENTER
        );

        updateReplaceOptions();
        updateControlledPlayerOptions();

        return page;
    }

    private JPanel createSquadPage() {

        JPanel page =
                new JPanel(
                        new BorderLayout()
                );

        page.setBackground(
                NAVY
        );

        JPanel top =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                0,
                                2
                        )
                );

        top.setBackground(
                NAVY
        );

        top.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        20,
                        12,
                        20
                )
        );

        JLabel title =
                label(
                        "STARTING XI",
                        29,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        squadTeamLabel =
                label(
                        "",
                        20,
                        true,
                        new Color(
                                134,
                                239,
                                172
                        ),
                        SwingConstants.CENTER
                );

        squadPowerLabel =
                label(
                        "",
                        15,
                        false,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        selectedLabel =
                label(
                        "",
                        14,
                        false,
                        new Color(
                                203,
                                213,
                                225
                        ),
                        SwingConstants.CENTER
                );

        top.add(
                title
        );

        top.add(
                squadTeamLabel
        );

        top.add(
                squadPowerLabel
        );

        top.add(
                selectedLabel
        );

        PitchPanel pitch =
                new PitchPanel();

        pitch.setLayout(
                new GridBagLayout()
        );

        squadButtons =
                new JButton[
                        Team.POSITIONS.length
                        ];

        for (int i = 0;
             i < squadButtons.length;
             i++) {

            squadButtons[i] =
                    new JButton();

            squadButtons[i]
                    .setPreferredSize(
                            new Dimension(
                                    150,
                                    82
                            )
                    );

            squadButtons[i]
                    .setFont(
                            font(
                                    12,
                                    true
                            )
                    );

            squadButtons[i]
                    .setFocusPainted(false);

            squadButtons[i]
                    .setOpaque(true);

            squadButtons[i]
                    .setContentAreaFilled(true);

            squadButtons[i]
                    .addActionListener(this);
        }

        addSquadButton(
                pitch,
                8,
                0,
                0
        );

        addSquadButton(
                pitch,
                9,
                2,
                0
        );

        addSquadButton(
                pitch,
                10,
                4,
                0
        );

        addSquadButton(
                pitch,
                5,
                1,
                1
        );

        addSquadButton(
                pitch,
                6,
                2,
                1
        );

        addSquadButton(
                pitch,
                7,
                3,
                1
        );

        addSquadButton(
                pitch,
                1,
                0,
                2
        );

        addSquadButton(
                pitch,
                2,
                1,
                2
        );

        addSquadButton(
                pitch,
                3,
                3,
                2
        );

        addSquadButton(
                pitch,
                4,
                4,
                2
        );

        addSquadButton(
                pitch,
                0,
                2,
                3
        );

        squadMessageLabel =
                label(
                        "",
                        15,
                        false,
                        TEXT,
                        SwingConstants.CENTER
                );

        squadMessageLabel.setOpaque(true);

        squadMessageLabel.setBackground(
                Color.WHITE
        );

        squadMessageLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        5,
                        10,
                        5
                )
        );

        page.add(
                top,
                BorderLayout.NORTH
        );

        page.add(
                pitch,
                BorderLayout.CENTER
        );

        page.add(
                squadMessageLabel,
                BorderLayout.SOUTH
        );

        return page;
    }

    private JPanel createMatchPage() {

        PatternPanel page =
                new PatternPanel();

        page.setLayout(
                new BorderLayout()
        );

        JPanel scoreboard =
                new ScoreboardPanel();

        scoreboard.setLayout(
                new GridLayout(
                        4,
                        1,
                        0,
                        2
                )
        );

        scoreboard.setBorder(
                BorderFactory.createEmptyBorder(
                        13,
                        20,
                        15,
                        20
                )
        );

        matchRoundLabel =
                label(
                        "",
                        15,
                        true,
                        new Color(
                                134,
                                239,
                                172
                        ),
                        SwingConstants.CENTER
                );

        fixtureLabel =
                label(
                        "",
                        23,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        scoreLabel =
                label(
                        "",
                        48,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        matchStatusLabel =
                label(
                        "",
                        14,
                        true,
                        GOLD,
                        SwingConstants.CENTER
                );

        scoreboard.add(
                matchRoundLabel
        );

        scoreboard.add(
                fixtureLabel
        );

        scoreboard.add(
                scoreLabel
        );

        scoreboard.add(
                matchStatusLabel
        );

        JPanel centerPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                0
                        )
                );

        centerPanel.setOpaque(false);

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        eventPane =
                new JTextPane();

        eventPane.setEditable(false);

        eventPane.setBackground(
                MATCH_NAVY
        );

        eventPane.setForeground(
                new Color(
                        226,
                        232,
                        240
                )
        );

        eventPane.setCaretColor(
                Color.WHITE
        );

        eventPane.setFont(
                new Font(
                        "Dialog",
                        Font.PLAIN,
                        15
                )
        );

        eventPane.setMargin(
                new Insets(
                        18,
                        18,
                        18,
                        18
                )
        );

        matchStatsPanel =
                new MatchStatsPanel();

        matchStatsPanel.setPreferredSize(
                new Dimension(
                        420,
                        350
                )
        );

        centerPanel.add(
                createMatchFeedCard()
        );

        centerPanel.add(
                createMatchStatsCard()
        );

        RoundedPanel bottom =
                new RoundedPanel(
                        CARD
                );

        bottom.setLayout(
                new BorderLayout(
                        0,
                        10
                )
        );

        bottom.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        18,
                        14,
                        18
                )
        );

        JPanel buttons =
                new JPanel(
                        new GridLayout(
                                1,
                                6,
                                8,
                                0
                        )
                );

        buttons.setOpaque(false);

        startMatchButton =
                createButton(
                        "Start Match"
                );

        nextEventButton =
                createButton(
                        "Next Event"
                );

        secondHalfButton =
                createButton(
                        "Second Half"
                );

        skipMatchButton =
                createButton(
                        "Skip Match"
                );

        completeRoundButton =
                createButton(
                        "Complete Round"
                );

        nextRoundButton =
                createButton(
                        "Next Round"
                );

        startMatchButton
                .addActionListener(this);

        nextEventButton
                .addActionListener(this);

        secondHalfButton
                .addActionListener(this);

        skipMatchButton
                .addActionListener(this);

        completeRoundButton
                .addActionListener(this);

        nextRoundButton
                .addActionListener(this);

        buttons.add(
                startMatchButton
        );

        buttons.add(
                nextEventButton
        );

        buttons.add(
                secondHalfButton
        );

        buttons.add(
                skipMatchButton
        );

        buttons.add(
                completeRoundButton
        );

        buttons.add(
                nextRoundButton
        );

        matchMessageLabel =
                label(
                        "",
                        14,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        bottom.add(
                buttons,
                BorderLayout.CENTER
        );

        bottom.add(
                matchMessageLabel,
                BorderLayout.SOUTH
        );

        JPanel bottomHolder =
                new JPanel(
                        new BorderLayout()
                );

        bottomHolder.setOpaque(false);

        bottomHolder.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        25,
                        20,
                        25
                )
        );

        bottomHolder.add(
                bottom
        );

        page.add(
                scoreboard,
                BorderLayout.NORTH
        );

        page.add(
                centerPanel,
                BorderLayout.CENTER
        );

        page.add(
                bottomHolder,
                BorderLayout.SOUTH
        );

        return page;
    }

    private JPanel createMatchFeedCard() {

        RoundedPanel card =
                new RoundedPanel(
                        DEEP_NAVY
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        30,
                                        64,
                                        80
                                ),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                0,
                                0,
                                0
                        )
                )
        );

        JPanel header =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        header.setBackground(
                new Color(
                        11,
                        35,
                        55
                )
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        JPanel left =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        left.setOpaque(false);

        JLabel dot =
                label(
                        "●",
                        15,
                        true,
                        LIVE_RED,
                        SwingConstants.CENTER
                );

        JLabel title =
                label(
                        "MATCH CENTRE",
                        14,
                        true,
                        Color.WHITE,
                        SwingConstants.LEFT
                );

        matchFeedStateLabel =
                label(
                        "PRE-MATCH",
                        11,
                        true,
                        SILVER,
                        SwingConstants.LEFT
                );

        left.add(
                dot
        );

        left.add(
                title
        );

        left.add(
                matchFeedStateLabel
        );

        matchFeedMinuteLabel =
                label(
                        "--",
                        15,
                        true,
                        GOLD,
                        SwingConstants.RIGHT
                );

        header.add(
                left,
                BorderLayout.CENTER
        );

        header.add(
                matchFeedMinuteLabel,
                BorderLayout.EAST
        );

        JScrollPane scroll =
                new JScrollPane(
                        eventPane
                );

        scroll.setBorder(null);

        scroll.getViewport()
                .setBackground(
                        MATCH_NAVY
                );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(18);

        card.add(
                header,
                BorderLayout.NORTH
        );

        card.add(
                scroll,
                BorderLayout.CENTER
        );

        return card;
    }

    private JPanel createMatchStatsCard() {

        RoundedPanel card =
                new RoundedPanel(
                        CARD
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        203,
                                        213,
                                        225
                                ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                0,
                                0,
                                0
                        )
                )
        );

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                NAVY
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        JLabel title =
                label(
                        "MATCH STATS",
                        14,
                        true,
                        Color.WHITE,
                        SwingConstants.LEFT
                );

        JLabel subtitle =
                label(
                        "LIVE DATA",
                        11,
                        true,
                        new Color(
                                134,
                                239,
                                172
                        ),
                        SwingConstants.RIGHT
                );

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                subtitle,
                BorderLayout.EAST
        );

        card.add(
                header,
                BorderLayout.NORTH
        );

        card.add(
                matchStatsPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    private JPanel createLeaguePage() {

        PatternPanel page =
                new PatternPanel();

        page.setLayout(
                new BorderLayout()
        );

        page.add(
                createHero(
                        "LEAGUE TABLE",
                        "THE RACE FOR THE CHAMPIONSHIP"
                ),
                BorderLayout.NORTH
        );

        leagueContentPanel =
                new JPanel();

        leagueContentPanel.setOpaque(false);

        leagueContentPanel.setLayout(
                new BoxLayout(
                        leagueContentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        leagueContentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        45,
                        35,
                        45
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        leagueContentPanel
                );

        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(18);

        page.add(
                scroll,
                BorderLayout.CENTER
        );

        return page;
    }

    private JPanel createLeaguePodium(
            Team[] standings) {

        JPanel podium =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                16,
                                0
                        )
                );

        podium.setOpaque(false);

        if (standings.length > 0) {

            podium.add(
                    createLeaguePodiumCard(
                            1,
                            standings[0]
                    )
            );
        }

        if (standings.length > 1) {

            podium.add(
                    createLeaguePodiumCard(
                            2,
                            standings[1]
                    )
            );
        }

        if (standings.length > 2) {

            podium.add(
                    createLeaguePodiumCard(
                            3,
                            standings[2]
                    )
            );
        }

        return podium;
    }

    private JPanel createLeaguePodiumCard(
            int position,
            Team team) {

        Color accent =
                getRankColor(
                        position
                );

        RoundedPanel card =
                new RoundedPanel(
                        Color.WHITE
                );

        card.setLayout(
                new BorderLayout(
                        5,
                        5
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                accent,
                                position == 1
                                        ? 3
                                        : 2
                        ),
                        BorderFactory.createEmptyBorder(
                                16,
                                15,
                                16,
                                15
                        )
                )
        );

        JLabel rank =
                label(
                        ordinal(position),
                        28,
                        true,
                        accent,
                        SwingConstants.CENTER
                );

        JLabel teamName =
                label(
                        team.getName(),
                        18,
                        true,
                        TEXT,
                        SwingConstants.CENTER
                );

        JLabel points =
                label(
                        team.getPoints()
                                + " PTS",
                        22,
                        true,
                        DARK_GREEN,
                        SwingConstants.CENTER
                );

        JLabel record =
                label(
                        team.getWins()
                                + "W   "
                                + team.getDraws()
                                + "D   "
                                + team.getLosses()
                                + "L",
                        13,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        JPanel center =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                0,
                                4
                        )
                );

        center.setOpaque(false);

        center.add(
                teamName
        );

        center.add(
                points
        );

        center.add(
                record
        );

        card.add(
                rank,
                BorderLayout.NORTH
        );

        card.add(
                center,
                BorderLayout.CENTER
        );

        return card;
    }

    private JPanel createLeagueTableCard(
            Team[] standings) {

        RoundedPanel card =
                new RoundedPanel(
                        CARD
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );

        JLabel title =
                label(
                        "FULL STANDINGS",
                        17,
                        true,
                        DARK_GREEN,
                        SwingConstants.LEFT
                );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                title
        );

        card.add(
                Box.createVerticalStrut(
                        12
                )
        );

        JPanel header =
                createLeagueHeaderRow();

        header.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                header
        );

        card.add(
                Box.createVerticalStrut(
                        5
                )
        );

        for (int i = 0;
             i < standings.length;
             i++) {

            JPanel row =
                    createLeagueRow(
                            i + 1,
                            standings[i]
                    );

            row.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            card.add(
                    row
            );

            if (i
                    < standings.length - 1) {

                card.add(
                        Box.createVerticalStrut(
                                4
                        )
                );
            }
        }

        return card;
    }

    private JPanel createLeagueHeaderRow() {

        JPanel row =
                new JPanel(
                        new BorderLayout()
                );

        row.setOpaque(true);

        row.setBackground(
                NAVY
        );

        row.setBorder(
                BorderFactory.createEmptyBorder(
                        9,
                        12,
                        9,
                        12
                )
        );

        JLabel pos =
                label(
                        "POS",
                        11,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        pos.setPreferredSize(
                new Dimension(
                        45,
                        25
                )
        );

        JLabel club =
                label(
                        "CLUB",
                        11,
                        true,
                        Color.WHITE,
                        SwingConstants.LEFT
                );

        club.setPreferredSize(
                new Dimension(
                        230,
                        25
                )
        );

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                8,
                                4,
                                0
                        )
                );

        stats.setOpaque(false);

        String[] headings = {
                "P",
                "W",
                "D",
                "L",
                "GF",
                "GA",
                "GD",
                "PTS"
        };

        for (int i = 0;
             i < headings.length;
             i++) {

            stats.add(
                    label(
                            headings[i],
                            11,
                            true,
                            Color.WHITE,
                            SwingConstants.CENTER
                    )
            );
        }

        stats.setPreferredSize(
                new Dimension(
                        470,
                        25
                )
        );

        row.add(
                pos,
                BorderLayout.WEST
        );

        row.add(
                club,
                BorderLayout.CENTER
        );

        row.add(
                stats,
                BorderLayout.EAST
        );

        return row;
    }

    private JPanel createLeagueRow(
            int position,
            Team team) {

        JPanel row =
                new JPanel(
                        new BorderLayout()
                );

        Team userTeam =
                game.isSetupCompleted()
                        ? game.getUserTeam()
                        : null;

        boolean user =
                team == userTeam;

        row.setOpaque(true);

        if (user) {

            row.setBackground(
                    new Color(
                            220,
                            252,
                            231
                    )
            );

        } else if (position <= 3) {

            row.setBackground(
                    new Color(
                            255,
                            253,
                            235
                    )
            );

        } else {

            row.setBackground(
                    Color.WHITE
            );
        }

        Color borderColor =
                user
                        ? new Color(
                        34,
                        197,
                        94
                )
                        : new Color(
                        226,
                        232,
                        240
                );

        row.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                borderColor,
                                user
                                        ? 2
                                        : 1
                        ),
                        BorderFactory.createEmptyBorder(
                                9,
                                12,
                                9,
                                12
                        )
                )
        );

        JLabel pos =
                label(
                        ""
                                + position,
                        15,
                        true,
                        position <= 3
                                ? getRankColor(
                                position
                        )
                                : TEXT,
                        SwingConstants.CENTER
                );

        pos.setPreferredSize(
                new Dimension(
                        45,
                        30
                )
        );

        String teamText =
                team.getName();

        if (user) {

            teamText +=
                    "   ★ YOUR CLUB";
        }

        JLabel club =
                label(
                        teamText,
                        14,
                        true,
                        user
                                ? DARK_GREEN
                                : TEXT,
                        SwingConstants.LEFT
                );

        club.setPreferredSize(
                new Dimension(
                        230,
                        30
                )
        );

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                8,
                                4,
                                0
                        )
                );

        stats.setOpaque(false);

        int played =
                team.getWins()
                        + team.getDraws()
                        + team.getLosses();

        int[] values = {
                played,
                team.getWins(),
                team.getDraws(),
                team.getLosses(),
                team.getGoalsFor(),
                team.getGoalsAgainst(),
                team.getGoalDifference(),
                team.getPoints()
        };

        for (int i = 0;
             i < values.length;
             i++) {

            stats.add(
                    label(
                            ""
                                    + values[i],
                            13,
                            i == 7,
                            i == 7
                                    ? DARK_GREEN
                                    : TEXT,
                            SwingConstants.CENTER
                    )
            );
        }

        stats.setPreferredSize(
                new Dimension(
                        470,
                        30
                )
        );

        row.add(
                pos,
                BorderLayout.WEST
        );

        row.add(
                club,
                BorderLayout.CENTER
        );

        row.add(
                stats,
                BorderLayout.EAST
        );

        row.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        58
                )
        );

        return row;
    }

    private JPanel createStatsPage() {

        PatternPanel page =
                new PatternPanel();

        page.setLayout(
                new BorderLayout()
        );

        page.add(
                createHero(
                        "PLAYER STATS",
                        "THE STARS OF THE SEASON"
                ),
                BorderLayout.NORTH
        );

        JPanel topButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                12
                        )
                );

        topButtons.setOpaque(false);

        goalsButton =
                createButton(
                        "⚽ Top Scorers"
                );

        assistsButton =
                createButton(
                        "👟 Top Assists"
                );

        goalsButton
                .addActionListener(this);

        assistsButton
                .addActionListener(this);

        topButtons.add(
                goalsButton
        );

        topButtons.add(
                assistsButton
        );

        statsContentPanel =
                new JPanel();

        statsContentPanel.setOpaque(false);

        statsContentPanel.setLayout(
                new BoxLayout(
                        statsContentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        statsContentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        45,
                        35,
                        45
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        statsContentPanel
                );

        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(18);

        JPanel middle =
                new JPanel(
                        new BorderLayout()
                );

        middle.setOpaque(false);

        middle.add(
                topButtons,
                BorderLayout.NORTH
        );

        middle.add(
                scroll,
                BorderLayout.CENTER
        );

        page.add(
                middle,
                BorderLayout.CENTER
        );

        return page;
    }

    private JPanel createStatsPodium(
            Player[] players,
            PlayerStatistics stats) {

        JPanel podium =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                16,
                                0
                        )
                );

        podium.setOpaque(false);

        int limit =
                Math.min(
                        3,
                        players.length
                );

        for (int i = 0;
             i < limit;
             i++) {

            podium.add(
                    createStatPodiumCard(
                            i + 1,
                            players[i],
                            stats
                    )
            );
        }

        return podium;
    }

    private JPanel createStatPodiumCard(
            int rank,
            Player player,
            PlayerStatistics stats) {

        Color accent =
                getRankColor(
                        rank
                );

        boolean controlled =
                game.isSetupCompleted()
                        && player
                        == game.getControlledPlayer();

        RoundedPanel card =
                new RoundedPanel(
                        controlled
                                ? LIGHT_GOLD
                                : Color.WHITE
                );

        card.setLayout(
                new BorderLayout(
                        5,
                        5
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                controlled
                                        ? GOLD
                                        : accent,
                                controlled
                                        ? 4
                                        : 2
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel rankLabel =
                label(
                        ordinal(
                                rank
                        ),
                        27,
                        true,
                        accent,
                        SwingConstants.CENTER
                );

        Team team =
                stats.getPlayerTeam(
                        player
                );

        JLabel name =
                label(
                        displayPlayerName(
                                player
                        ),
                        18,
                        true,
                        controlled
                                ? new Color(
                                161,
                                98,
                                7
                        )
                                : TEXT,
                        SwingConstants.CENTER
                );

        JLabel teamLabel =
                label(
                        team == null
                                ? "Unknown"
                                : team.getName(),
                        13,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        String mainStat;
        String secondStat;

        if (showingGoals) {

            mainStat =
                    player.getSeasonGoals()
                            + " GOALS";

            secondStat =
                    player.getSeasonAssists()
                            + " Assists";

        } else {

            mainStat =
                    player.getSeasonAssists()
                            + " ASSISTS";

            secondStat =
                    player.getSeasonGoals()
                            + " Goals";
        }

        JLabel main =
                label(
                        mainStat,
                        22,
                        true,
                        DARK_GREEN,
                        SwingConstants.CENTER
                );

        JLabel secondary =
                label(
                        secondStat,
                        13,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                );

        JPanel center =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                0,
                                3
                        )
                );

        center.setOpaque(false);

        center.add(
                name
        );

        center.add(
                teamLabel
        );

        center.add(
                main
        );

        center.add(
                secondary
        );

        card.add(
                rankLabel,
                BorderLayout.NORTH
        );

        card.add(
                center,
                BorderLayout.CENTER
        );

        return card;
    }

    private JPanel createStatsListCard(
            Player[] players,
            PlayerStatistics stats) {

        RoundedPanel card =
                new RoundedPanel(
                        CARD
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );

        JLabel title =
                label(
                        showingGoals
                                ? "TOP SCORERS"
                                : "TOP ASSISTS",
                        17,
                        true,
                        DARK_GREEN,
                        SwingConstants.LEFT
                );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                title
        );

        card.add(
                Box.createVerticalStrut(
                        12
                )
        );

        JPanel header =
                createStatsHeader();

        header.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                header
        );

        card.add(
                Box.createVerticalStrut(
                        5
                )
        );

        int limit =
                Math.min(
                        20,
                        players.length
                );

        for (int i = 3;
             i < limit;
             i++) {

            JPanel row =
                    createStatsRow(
                            i + 1,
                            players[i],
                            stats
                    );

            row.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            card.add(
                    row
            );

            card.add(
                    Box.createVerticalStrut(
                            4
                    )
            );
        }

        return card;
    }

    private JPanel createStatsHeader() {

        JPanel row =
                new JPanel(
                        new BorderLayout()
                );

        row.setOpaque(true);

        row.setBackground(
                NAVY
        );

        row.setBorder(
                BorderFactory.createEmptyBorder(
                        9,
                        12,
                        9,
                        12
                )
        );

        JLabel rank =
                label(
                        "#",
                        11,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        rank.setPreferredSize(
                new Dimension(
                        45,
                        25
                )
        );

        JLabel player =
                label(
                        "PLAYER",
                        11,
                        true,
                        Color.WHITE,
                        SwingConstants.LEFT
                );

        player.setPreferredSize(
                new Dimension(
                        230,
                        25
                )
        );

        JLabel team =
                label(
                        "TEAM",
                        11,
                        true,
                        Color.WHITE,
                        SwingConstants.LEFT
                );

        team.setPreferredSize(
                new Dimension(
                        220,
                        25
                )
        );

        JPanel values =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                5,
                                0
                        )
                );

        values.setOpaque(false);

        values.add(
                label(
                        showingGoals
                                ? "GOALS"
                                : "ASSISTS",
                        11,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                )
        );

        values.add(
                label(
                        showingGoals
                                ? "ASSISTS"
                                : "GOALS",
                        11,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                )
        );

        values.setPreferredSize(
                new Dimension(
                        200,
                        25
                )
        );

        JPanel center =
                new JPanel(
                        new BorderLayout()
                );

        center.setOpaque(false);

        center.add(
                player,
                BorderLayout.WEST
        );

        center.add(
                team,
                BorderLayout.CENTER
        );

        row.add(
                rank,
                BorderLayout.WEST
        );

        row.add(
                center,
                BorderLayout.CENTER
        );

        row.add(
                values,
                BorderLayout.EAST
        );

        return row;
    }

    private JPanel createStatsRow(
            int rank,
            Player player,
            PlayerStatistics stats) {

        JPanel row =
                new JPanel(
                        new BorderLayout()
                );

        boolean controlled =
                game.isSetupCompleted()
                        && player
                        == game.getControlledPlayer();

        row.setOpaque(true);

        row.setBackground(
                controlled
                        ? LIGHT_GOLD
                        : Color.WHITE
        );

        row.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                controlled
                                        ? GOLD
                                        : new Color(
                                        226,
                                        232,
                                        240
                                ),
                                controlled
                                        ? 2
                                        : 1
                        ),
                        BorderFactory.createEmptyBorder(
                                9,
                                12,
                                9,
                                12
                        )
                )
        );

        JLabel rankLabel =
                label(
                        ""
                                + rank,
                        14,
                        true,
                        TEXT,
                        SwingConstants.CENTER
                );

        rankLabel.setPreferredSize(
                new Dimension(
                        45,
                        28
                )
        );

        String name =
                displayPlayerName(
                        player
                );

        if (controlled) {

            name +=
                    "   ★ CONTROLLED";
        }

        JLabel playerLabel =
                label(
                        name,
                        14,
                        true,
                        controlled
                                ? new Color(
                                161,
                                98,
                                7
                        )
                                : TEXT,
                        SwingConstants.LEFT
                );

        playerLabel.setPreferredSize(
                new Dimension(
                        230,
                        28
                )
        );

        Team team =
                stats.getPlayerTeam(
                        player
                );

        JLabel teamLabel =
                label(
                        team == null
                                ? "Unknown"
                                : team.getName(),
                        13,
                        false,
                        MUTED,
                        SwingConstants.LEFT
                );

        teamLabel.setPreferredSize(
                new Dimension(
                        220,
                        28
                )
        );

        JPanel middle =
                new JPanel(
                        new BorderLayout()
                );

        middle.setOpaque(false);

        middle.add(
                playerLabel,
                BorderLayout.WEST
        );

        middle.add(
                teamLabel,
                BorderLayout.CENTER
        );

        JPanel values =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                5,
                                0
                        )
                );

        values.setOpaque(false);

        int first =
                showingGoals
                        ? player.getSeasonGoals()
                        : player.getSeasonAssists();

        int second =
                showingGoals
                        ? player.getSeasonAssists()
                        : player.getSeasonGoals();

        values.add(
                label(
                        ""
                                + first,
                        15,
                        true,
                        DARK_GREEN,
                        SwingConstants.CENTER
                )
        );

        values.add(
                label(
                        ""
                                + second,
                        14,
                        false,
                        TEXT,
                        SwingConstants.CENTER
                )
        );

        values.setPreferredSize(
                new Dimension(
                        200,
                        28
                )
        );

        row.add(
                rankLabel,
                BorderLayout.WEST
        );

        row.add(
                middle,
                BorderLayout.CENTER
        );

        row.add(
                values,
                BorderLayout.EAST
        );

        row.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        58
                )
        );

        return row;
    }

    private JPanel createAwardsPage() {

        PatternPanel page =
                new PatternPanel();

        page.setLayout(
                new BorderLayout()
        );

        page.add(
                createHero(
                        "SEASON AWARDS",
                        "HONOURING THE BEST OF THE SEASON"
                ),
                BorderLayout.NORTH
        );

        awardsContentPanel =
                new JPanel();

        awardsContentPanel.setOpaque(false);

        awardsContentPanel.setLayout(
                new BoxLayout(
                        awardsContentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        awardsContentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        55,
                        40,
                        55
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        awardsContentPanel
                );

        scroll.setOpaque(false);

        scroll.getViewport()
                .setOpaque(false);

        scroll.setBorder(null);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(18);

        page.add(
                scroll,
                BorderLayout.CENTER
        );

        return page;
    }

    private JPanel createSeasonCompleteHeader() {

        RoundedPanel card =
                new RoundedPanel(
                        NAVY
                );

        card.setLayout(
                new GridLayout(
                        3,
                        1,
                        0,
                        4
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                3
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        JLabel title =
                label(
                        "★ SEASON COMPLETE ★",
                        28,
                        true,
                        GOLD,
                        SwingConstants.CENTER
                );

        JLabel team =
                label(
                        game.getUserTeam()
                                .getName(),
                        19,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        JLabel position =
                label(
                        "Final League Position: "
                                + ordinal(
                                game.getUserTeamPosition()
                        )
                                + " of 12",
                        15,
                        false,
                        SILVER,
                        SwingConstants.CENTER
                );

        card.add(
                title
        );

        card.add(
                team
        );

        card.add(
                position
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        145
                )
        );

        return card;
    }

    private JPanel createChampionCard(
            Team champion) {

        RoundedPanel card =
                new RoundedPanel(
                        new Color(
                                255,
                                253,
                                235
                        )
                );

        card.setLayout(
                new GridLayout(
                        4,
                        1,
                        0,
                        3
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                GOLD,
                                4
                        ),
                        BorderFactory.createEmptyBorder(
                                22,
                                20,
                                22,
                                20
                        )
                )
        );

        card.add(
                label(
                        "🏆",
                        40,
                        true,
                        GOLD,
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        "LEAGUE CHAMPION",
                        16,
                        true,
                        new Color(
                                161,
                                98,
                                7
                        ),
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        champion == null
                                ? "No Champion"
                                : champion.getName(),
                        28,
                        true,
                        TEXT,
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        champion == null
                                ? ""
                                : champion.getPoints()
                                + " points   •   "
                                + champion.getWins()
                                + " wins",
                        14,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        210
                )
        );

        return card;
    }

    private JPanel createAwardGrid() {

        SeasonAwards awards =
                game.getSeasonAwards();

        PlayerStatistics stats =
                game.getPlayerStatistics();

        JPanel grid =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                16,
                                16
                        )
                );

        grid.setOpaque(false);

        Player[] boot =
                awards.getGoldenBootWinners();

        String bootName =
                playerArrayText(
                        boot
                );

        String bootStat =
                boot.length == 0
                        ? "No winner"
                        : boot[0]
                        .getSeasonGoals()
                        + " Goals";

        grid.add(
                createAwardCard(
                        "⚽",
                        "GOLDEN BOOT",
                        bootName,
                        bootStat,
                        new Color(
                                22,
                                163,
                                74
                        )
                )
        );

        Player[] playmakers =
                awards.getPlaymakerWinners();

        String assistName =
                playerArrayText(
                        playmakers
                );

        String assistStat =
                playmakers.length == 0
                        ? "No winner"
                        : playmakers[0]
                        .getSeasonAssists()
                        + " Assists";

        grid.add(
                createAwardCard(
                        "👟",
                        "PLAYMAKER",
                        assistName,
                        assistStat,
                        new Color(
                                37,
                                99,
                                235
                        )
                )
        );

        Player keeper =
                awards.getGoldenGloveWinner();

        Team keeperTeam =
                keeper == null
                        ? null
                        : stats.getPlayerTeam(
                        keeper
                );

        String keeperName =
                keeper == null
                        ? "No winner"
                        : displayPlayerName(
                        keeper
                );

        String keeperStat =
                keeperTeam == null
                        ? ""
                        : keeperTeam.getName()
                        + "   •   "
                        + keeperTeam
                        .getGoalsAgainst()
                        + " conceded";

        grid.add(
                createAwardCard(
                        "🧤",
                        "GOLDEN GLOVE",
                        keeperName,
                        keeperStat,
                        new Color(
                                124,
                                58,
                                237
                        )
                )
        );

        int position =
                game.getUserTeamPosition();

        grid.add(
                createAwardCard(
                        "🏅",
                        "YOUR FINAL POSITION",
                        ordinal(position),
                        game.getUserTeam()
                                .getName()
                                + "   •   "
                                + game.getUserTeam()
                                .getPoints()
                                + " Points",
                        GOLD
                )
        );

        return grid;
    }

    private JPanel createAwardCard(
            String icon,
            String title,
            String winner,
            String detail,
            Color accent) {

        RoundedPanel card =
                new RoundedPanel(
                        Color.WHITE
                );

        card.setLayout(
                new GridLayout(
                        4,
                        1,
                        0,
                        4
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                accent,
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        card.add(
                label(
                        icon,
                        31,
                        true,
                        accent,
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        title,
                        14,
                        true,
                        accent,
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        winner,
                        20,
                        true,
                        TEXT,
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        detail,
                        13,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                )
        );

        return card;
    }

    private JPanel createAwardsLockedCard() {

        RoundedPanel card =
                new RoundedPanel(
                        CARD
                );

        card.setLayout(
                new GridLayout(
                        4,
                        1,
                        0,
                        8
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                SILVER,
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                35,
                                30,
                                35,
                                30
                        )
                )
        );

        card.add(
                label(
                        "🏆",
                        48,
                        true,
                        GOLD,
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        game.isSetupCompleted()
                                ? "SEASON IN PROGRESS"
                                : "SEASON NOT STARTED",
                        25,
                        true,
                        NAVY,
                        SwingConstants.CENTER
                )
        );

        card.add(
                label(
                        game.isSetupCompleted()
                                ? "Complete all 15 rounds to unlock the awards."
                                : "Complete player setup to begin your season.",
                        15,
                        false,
                        MUTED,
                        SwingConstants.CENTER
                )
        );

        String progress;

        if (!game.isSetupCompleted()) {

            progress =
                    "Awards Locked";

        } else {

            progress =
                    "Round "
                            + game.getCurrentRoundNumber()
                            + " / "
                            + SeasonSchedule.TOTAL_ROUNDS;
        }

        card.add(
                label(
                        progress,
                        16,
                        true,
                        DARK_GREEN,
                        SwingConstants.CENTER
                )
        );

        card.setMaximumSize(
                new Dimension(
                        760,
                        300
                )
        );

        return card;
    }

    private void refreshHome() {

        boolean skillReminder =
                game.isSetupCompleted()
                        && game.hasAvailableSkillPoint()
                        && !game.isSeasonFinished();

        tabs.setTitleAt(
                0,
                skillReminder
                        ? "Home ★"
                        : "Home"
        );

        if (!game.isSetupCompleted()) {

            homeReminderLabel.setVisible(
                    true
            );

            homeReminderLabel.setText(
                    "<html><center>"
                            + "SETUP INCOMPLETE<br>"
                            + "Create your players, choose one controlled player, "
                            + "then press Complete Setup."
                            + "</center></html>"
            );

            homeRoundLabel.setText(
                    "SETUP"
            );

            homeClubLabel.setText(
                    "NOT SELECTED"
            );

            homePositionLabel.setText(
                    "-- / 12"
            );

            pointsLabel.setText(
                    "0"
            );

            homePlayerLabel.setText(
                    game.getCustomPlayerCount()
                            + " CUSTOM PLAYER(S) CREATED"
            );

            homeSkillsLabel.setText(
                    "Complete setup to begin the season."
            );

            homeCareerLabel.setText("");

            homeNextMatchLabel.setText(
                    "Go to Create Player to finish setup."
            );

            nextPointLabel.setText(
                    "Skill upgrades begin after the season starts."
            );

            upgradeShootingButton.setEnabled(false);
            upgradePassingButton.setEnabled(false);
            upgradeDefenseButton.setEnabled(false);

            return;
        }

        Player player =
                game.getControlledPlayer();

        if (game.isSeasonFinished()) {

            homeReminderLabel.setVisible(
                    true
            );

            homeReminderLabel.setText(
                    "<html><center>"
                            + "★ SEASON COMPLETE ★<br>"
                            + "Check the Awards tab for the final results."
                            + "</center></html>"
            );

        } else if (game.hasAvailableSkillPoint()) {

            int points =
                    game.getAvailableSkillPoints();

            homeReminderLabel.setVisible(
                    true
            );

            homeReminderLabel.setText(
                    "<html><center>"
                            + "★ "
                            + points
                            + (points == 1
                            ? " SKILL POINT AVAILABLE!"
                            : " SKILL POINTS AVAILABLE!")
                            + " ★<br>"
                            + "Upgrade Shooting, Passing, or Defense below."
                            + "</center></html>"
            );

        } else {

            homeReminderLabel.setVisible(
                    false
            );
        }

        homeRoundLabel.setText(
                game.isSeasonFinished()
                        ? "COMPLETE"
                        : game.getCurrentRoundNumber()
                        + " / "
                        + SeasonSchedule.TOTAL_ROUNDS
        );

        homeClubLabel.setText(
                game.getUserTeam()
                        .getName()
        );

        homePositionLabel.setText(
                game.getUserTeamPosition()
                        + " / 12"
        );

        pointsLabel.setText(
                ""
                        + game.getAvailableSkillPoints()
        );

        homePlayerLabel.setText(
                player.getName()
                        + "   •   "
                        + game.getCustomPlayerPosition()
        );

        homeSkillsLabel.setText(
                "SHOOTING "
                        + player.getShooting()
                        + "     •     PASSING "
                        + player.getPassing()
                        + "     •     DEFENSE "
                        + player.getDefense()
        );

        homeCareerLabel.setText(
                "MATCHES "
                        + player.getCareerMatches()
                        + "     •     GOALS "
                        + player.getCareerGoals()
                        + "     •     ASSISTS "
                        + player.getCareerAssists()
        );

        homeNextMatchLabel.setText(
                getHomeMatchText()
        );

        nextPointLabel.setText(
                game.isSeasonFinished()
                        ? "Season Complete"
                        : "Next Skill Point: Match "
                        + game.getNextUpgradeMatchTarget()
        );

        boolean available =
                game.hasAvailableSkillPoint()
                        && !game.isSeasonFinished();

        upgradeShootingButton.setEnabled(
                available
                        && player.getShooting()
                        < 15
        );

        upgradePassingButton.setEnabled(
                available
                        && player.getPassing()
                        < 15
        );

        upgradeDefenseButton.setEnabled(
                available
                        && player.getDefense()
                        < 15
        );
    }

    private String getHomeMatchText() {

        if (!game.isSetupCompleted()) {

            return "NEXT MATCH: --";
        }

        if (game.isSeasonFinished()) {

            return "SEASON COMPLETE";
        }

        int round =
                game.getCurrentRoundIndex();

        if (game.isRoundCompleted()) {

            round++;
        }

        if (round
                >= SeasonSchedule.TOTAL_ROUNDS) {

            return "SEASON COMPLETE";
        }

        Fixture fixture =
                game.getUserFixture(
                        round
                );

        if (fixture == null) {

            return "NEXT MATCH: --";
        }

        if (game.isRoundStarted()
                && !game.isRoundCompleted()) {

            return "CURRENT MATCH: "
                    + fixture.getHomeTeam()
                    .getName()
                    + " VS "
                    + fixture.getAwayTeam()
                    .getName();
        }

        return "NEXT MATCH: "
                + fixture.getHomeTeam()
                .getName()
                + " VS "
                + fixture.getAwayTeam()
                .getName();
    }

    private void refreshCreatePage() {

        if (createdPlayersArea == null) {

            return;
        }

        String text =
                "Created Players: "
                        + game.getCustomPlayerCount()
                        + "\n\n";

        if (game.getCustomPlayerCount()
                == 0) {

            text +=
                    "No players created yet.";

        } else {

            for (int i = 0;
                 i < game.getCustomPlayerCount();
                 i++) {

                Player player =
                        game.getCustomPlayer(i);

                Team team =
                        game.getCustomPlayerTeam(i);

                text +=
                        (i + 1)
                                + ". "
                                + player.getName()
                                + "\n";

                text +=
                        "   Club: "
                                + (team == null
                                ? "Unknown"
                                : team.getName())
                                + "\n";

                text +=
                        "   Shooting "
                                + player.getShooting()
                                + "   Passing "
                                + player.getPassing()
                                + "   Defense "
                                + player.getDefense()
                                + "\n\n";
            }
        }

        createdPlayersArea.setText(
                text
        );

        createdPlayersArea.setCaretPosition(
                0
        );

        boolean setupComplete =
                game.isSetupCompleted();

        tabs.setTitleAt(
                1,
                setupComplete
                        ? "Create Player ✓"
                        : "Create Player"
        );

        nameField.setEnabled(
                !setupComplete
        );

        shootingSpinner.setEnabled(
                !setupComplete
        );

        passingSpinner.setEnabled(
                !setupComplete
        );

        defenseSpinner.setEnabled(
                !setupComplete
        );

        teamComboBox.setEnabled(
                !setupComplete
        );

        replaceComboBox.setEnabled(
                !setupComplete
                        && replaceComboBox
                        .getItemCount()
                        > 0
        );

        createButton.setEnabled(
                !setupComplete
                        && replaceComboBox
                        .getItemCount()
                        > 0
        );

        controlledPlayerComboBox.setEnabled(
                !setupComplete
                        && game.getCustomPlayerCount()
                        > 0
        );

        completeSetupButton.setEnabled(
                !setupComplete
                        && game.getCustomPlayerCount()
                        > 0
        );

        if (setupComplete) {

            setupMessageLabel.setForeground(
                    DARK_GREEN
            );

            setupMessageLabel.setText(
                    "Setup complete. Controlled Player: "
                            + game.getControlledPlayer()
                            .getName()
            );

        } else {

            setupMessageLabel.setForeground(
                    MUTED
            );

            setupMessageLabel.setText(
                    game.getCustomPlayerCount()
                            == 0
                            ? "Create at least one player first."
                            : "Choose one player, then complete setup."
            );
        }
    }

    private void updateControlledPlayerOptions() {

        if (controlledPlayerComboBox
                == null) {

            return;
        }

        int oldSelection =
                controlledPlayerComboBox
                        .getSelectedIndex();

        controlledPlayerComboBox
                .removeAllItems();

        for (int i = 0;
             i < game.getCustomPlayerCount();
             i++) {

            Player player =
                    game.getCustomPlayer(i);

            Team team =
                    game.getCustomPlayerTeam(i);

            String teamName =
                    team == null
                            ? "Unknown"
                            : team.getName();

            controlledPlayerComboBox.addItem(
                    player.getName()
                            + " - "
                            + teamName
            );
        }

        if (game.isSetupCompleted()) {

            Player controlled =
                    game.getControlledPlayer();

            for (int i = 0;
                 i < game.getCustomPlayerCount();
                 i++) {

                if (game.getCustomPlayer(i)
                        == controlled) {

                    controlledPlayerComboBox
                            .setSelectedIndex(i);

                    break;
                }
            }

        } else if (oldSelection >= 0
                && oldSelection
                < controlledPlayerComboBox
                .getItemCount()) {

            controlledPlayerComboBox
                    .setSelectedIndex(
                            oldSelection
                    );

        } else if (controlledPlayerComboBox
                .getItemCount()
                > 0) {

            controlledPlayerComboBox
                    .setSelectedIndex(0);
        }
    }

    private void updateSkillTotal() {

        if (shootingSpinner == null) {

            return;
        }

        int shooting =
                (Integer)
                        shootingSpinner
                                .getValue();

        int passing =
                (Integer)
                        passingSpinner
                                .getValue();

        int defense =
                (Integer)
                        defenseSpinner
                                .getValue();

        int total =
                shooting
                        + passing
                        + defense;

        skillTotalLabel.setText(
                total
                        + " / 20"
        );

        skillTotalLabel.setForeground(
                total == 20
                        ? DARK_GREEN
                        : new Color(
                        220,
                        38,
                        38
                )
        );
    }

    private void updateReplaceOptions() {

        if (teamComboBox == null
                || replaceComboBox == null) {

            return;
        }

        replaceComboBox
                .removeAllItems();

        int teamIndex =
                teamComboBox
                        .getSelectedIndex();

        if (teamIndex < 0) {

            return;
        }

        Player[] lineup =
                game.getTeams()[
                        teamIndex
                        ].getLineup();

        for (int i = 1;
             i < lineup.length;
             i++) {

            if (!lineup[i]
                    .isCustomPlayer()) {

                replaceComboBox.addItem(
                        Team.POSITIONS[i]
                                + "   •   "
                                + lineup[i]
                                .getName()
                );
            }
        }

        if (createButton != null) {

            createButton.setEnabled(
                    !game.isSetupCompleted()
                            && replaceComboBox
                            .getItemCount()
                            > 0
            );
        }
    }

    private int getActualReplaceSlot() {

        int selected =
                replaceComboBox
                        .getSelectedIndex();

        if (selected < 0) {

            return -1;
        }

        int teamIndex =
                teamComboBox
                        .getSelectedIndex();

        if (teamIndex < 0) {

            return -1;
        }

        Player[] lineup =
                game.getTeams()[
                        teamIndex
                        ].getLineup();

        int visibleIndex = 0;

        for (int i = 1;
             i < lineup.length;
             i++) {

            if (!lineup[i]
                    .isCustomPlayer()) {

                if (visibleIndex
                        == selected) {

                    return i;
                }

                visibleIndex++;
            }
        }

        return -1;
    }

    private void createPlayer() {

        int replaceSlot =
                getActualReplaceSlot();

        int oldCount =
                game.getCustomPlayerCount();

        String result =
                game.createCustomPlayer(
                        nameField.getText(),
                        (Integer)
                                shootingSpinner
                                        .getValue(),
                        (Integer)
                                passingSpinner
                                        .getValue(),
                        (Integer)
                                defenseSpinner
                                        .getValue(),
                        teamComboBox
                                .getSelectedIndex(),
                        replaceSlot
                );

        createMessageLabel.setText(
                result
        );

        if (game.getCustomPlayerCount()
                > oldCount) {

            createMessageLabel.setForeground(
                    DARK_GREEN
            );

            nameField.setText("");

            shootingSpinner.setValue(7);
            passingSpinner.setValue(7);
            defenseSpinner.setValue(6);

            updateReplaceOptions();
            updateControlledPlayerOptions();
            refreshCreatePage();
            refreshLeague();
            refreshStats();

        } else {

            createMessageLabel.setForeground(
                    new Color(
                            185,
                            28,
                            28
                    )
            );
        }
    }

    private void completeSetup() {

        int selected =
                controlledPlayerComboBox
                        .getSelectedIndex();

        if (selected < 0) {

            setupMessageLabel.setText(
                    "Choose a controlled player."
            );

            return;
        }

        String selectedPlayer =
                (String)
                        controlledPlayerComboBox
                                .getSelectedItem();

        String[] options = {
                "Yes",
                "No"
        };

        int choice =
                JOptionPane.showOptionDialog(
                        this,
                        "Complete player setup?\n\n"
                                + "Controlled Player: "
                                + selectedPlayer
                                + "\n\n"
                                + "You will not be able to create more players\n"
                                + "or change your controlled player.",
                        "Complete Setup",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]
                );

        if (choice != 0) {

            return;
        }

        String result =
                game.completeSetup(
                        selected
                );

        setupMessageLabel.setText(
                result
        );

        if (game.isSetupCompleted()) {

            setupMessageLabel.setForeground(
                    DARK_GREEN
            );

            audioManager.stopMusic();

            audioManager.startRandomPlaylist(
                    MAIN_PLAYLIST
            );

            updateControlledPlayerOptions();

            refreshAll();

            tabs.setSelectedIndex(
                    0
            );
        }
    }

    private void updateMusicForCurrentTab() {

        if (!game.isSetupCompleted()) {

            if (!CREATE_MUSIC.equals(
                    audioManager.getCurrentPath()
            )
                    || !audioManager.isPlaying()) {

                audioManager.playLoop(
                        CREATE_MUSIC
                );
            }

            return;
        }

        if (!audioManager.isPlaylistMode()) {

            audioManager.startRandomPlaylist(
                    MAIN_PLAYLIST
            );
        }
    }

    private void upgradePlayer(
            String skill) {

        upgradeMessageLabel.setText(
                game.upgradeCustomPlayer(
                        skill
                )
        );

        refreshHome();
        refreshSquad();
    }

    private void addSquadButton(
            JPanel pitch,
            int slot,
            int x,
            int y) {

        GridBagConstraints c =
                new GridBagConstraints();

        c.gridx = x;
        c.gridy = y;

        c.insets =
                new Insets(
                        10,
                        12,
                        10,
                        12
                );

        pitch.add(
                squadButtons[slot],
                c
        );
    }

    private void refreshSquad() {

        selectedSlot = -1;

        selectedLabel.setText(
                "SELECTED: NONE"
        );

        if (!game.isSetupCompleted()) {

            squadTeamLabel.setText(
                    "SETUP INCOMPLETE"
            );

            squadPowerLabel.setText(
                    "TEAM POWER  --"
            );

            squadMessageLabel.setText(
                    "Complete player setup before managing your squad."
            );

            for (int i = 0;
                 i < squadButtons.length;
                 i++) {

                squadButtons[i].setText(
                        Team.POSITIONS[i]
                );

                squadButtons[i].setEnabled(
                        false
                );
            }

            return;
        }

        Team team =
                game.getUserTeam();

        Player[] lineup =
                team.getLineup();

        Player controlled =
                game.getControlledPlayer();

        squadTeamLabel.setText(
                team.getName()
                        .toUpperCase()
        );

        squadPowerLabel.setText(
                String.format(
                        "TEAM POWER   %.2f",
                        team.getTeamPower()
                )
        );

        boolean locked =
                (game.isRoundStarted()
                        && !game.isRoundCompleted())
                        || game.isSeasonFinished();

        squadMessageLabel.setText(
                locked
                        ? "Squad changes are currently locked."
                        : "Select two outfield players to swap positions."
        );

        for (int i = 0;
             i < lineup.length;
             i++) {

            Player player =
                    lineup[i];

            String playerType = "";

            if (player == controlled) {

                playerType =
                        "<br><font size='2'><b>CONTROLLED PLAYER</b></font>";

            } else if (player.isCustomPlayer()) {

                playerType =
                        "<br><font size='2'><b>CUSTOM AI</b></font>";
            }

            squadButtons[i].setText(
                    "<html><center>"
                            + "<font size='4'><b>"
                            + Team.POSITIONS[i]
                            + "</b></font>"
                            + "<br>"
                            + player.getName()
                            + "<br><font size='2'>S "
                            + player.getShooting()
                            + "   P "
                            + player.getPassing()
                            + "   D "
                            + player.getDefense()
                            + "</font>"
                            + playerType
                            + "</center></html>"
            );

            squadButtons[i].setForeground(
                    TEXT
            );

            if (player == controlled) {

                squadButtons[i].setBackground(
                        GOLD
                );

                squadButtons[i].setBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        161,
                                        98,
                                        7
                                ),
                                4
                        )
                );

            } else if (player.isCustomPlayer()) {

                squadButtons[i].setBackground(
                        LIGHT_GOLD
                );

                squadButtons[i].setBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        202,
                                        138,
                                        4
                                ),
                                2
                        )
                );

            } else if (i == 0) {

                squadButtons[i].setBackground(
                        new Color(
                                219,
                                234,
                                254
                        )
                );

                squadButtons[i].setBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        37,
                                        99,
                                        235
                                ),
                                2
                        )
                );

            } else {

                squadButtons[i].setBackground(
                        Color.WHITE
                );

                squadButtons[i].setBorder(
                        BorderFactory.createLineBorder(
                                SILVER,
                                2
                        )
                );
            }

            squadButtons[i].setEnabled(
                    i != 0
                            && !locked
            );
        }
    }

    private void selectSquadPlayer(
            int slot) {

        if (!game.isSetupCompleted()
                || slot == 0) {

            return;
        }

        Player[] lineup =
                game.getUserTeam()
                        .getLineup();

        if (selectedSlot == -1) {

            selectedSlot = slot;

            selectedLabel.setText(
                    "SELECTED: "
                            + lineup[slot]
                            .getName()
                            .toUpperCase()
                            + "   •   "
                            + Team.POSITIONS[slot]
            );

            squadMessageLabel.setText(
                    "Now select another player."
            );

            squadButtons[slot].setBorder(
                    BorderFactory.createLineBorder(
                            new Color(
                                    34,
                                    197,
                                    94
                            ),
                            4
                    )
            );

            return;
        }

        if (selectedSlot == slot) {

            refreshSquad();

            return;
        }

        String first =
                lineup[selectedSlot]
                        .getName();

        String second =
                lineup[slot]
                        .getName();

        boolean swapped =
                game.swapUserTeamPlayers(
                        selectedSlot,
                        slot
                );

        refreshSquad();

        if (swapped) {

            squadMessageLabel.setText(
                    first
                            + " and "
                            + second
                            + " swapped positions."
            );
        }
    }

    private int getSquadSlot(
            Object source) {

        for (int i = 0;
             i < squadButtons.length;
             i++) {

            if (source
                    == squadButtons[i]) {

                return i;
            }
        }

        return -1;
    }

    private void refreshMatch() {

        matchRoundLabel.setText(
                game.isSetupCompleted()
                        ? "ROUND "
                        + game.getCurrentRoundNumber()
                        + " / "
                        + SeasonSchedule.TOTAL_ROUNDS
                        : "PLAYER SETUP"
        );

        if (!game.isSetupCompleted()) {

            fixtureLabel.setText(
                    "COMPLETE SETUP FIRST"
            );

            scoreLabel.setText(
                    "—"
            );

            matchStatusLabel.setText(
                    "SEASON NOT STARTED"
            );

            matchMessageLabel.setText(
                    "Create your players and press Complete Setup."
            );

            matchFeedStateLabel.setText(
                    "LOCKED"
            );

            matchFeedStateLabel.setForeground(
                    SILVER
            );

            matchFeedMinuteLabel.setText(
                    "--"
            );

            clearEventPane();

            appendStyledText(
                    "SEASON NOT STARTED\n",
                    SILVER,
                    true,
                    19
            );

            appendStyledText(
                    "\nComplete player setup to unlock Match Centre.\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    false,
                    14
            );

            matchStatsPanel.setMatch(
                    null
            );

            setMatchButtons(
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            );

            return;
        }

        if (game.isSeasonFinished()) {

            Match match =
                    game.getCurrentUserMatch();

            fixtureLabel.setText(
                    "SEASON COMPLETED"
            );

            if (match == null) {

                scoreLabel.setText(
                        "VIEW AWARDS"
                );

                matchStatsPanel.setMatch(
                        null
                );

            } else {

                scoreLabel.setText(
                        match.getHomeScore()
                                + "   -   "
                                + match.getAwayScore()
                );

                matchStatsPanel.setMatch(
                        match
                );
            }

            matchStatusLabel.setText(
                    "FULL SEASON COMPLETE"
            );

            matchMessageLabel.setText(
                    "Check the Awards tab for the final results."
            );

            matchFeedStateLabel.setText(
                    "SEASON COMPLETE"
            );

            matchFeedStateLabel.setForeground(
                    GOLD
            );

            matchFeedMinuteLabel.setText(
                    "FT"
            );

            setMatchButtons(
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            );

            return;
        }

        if (!game.isRoundStarted()) {

            Fixture fixture =
                    game.getUserFixture(
                            game.getCurrentRoundIndex()
                    );

            if (fixture != null) {

                fixtureLabel.setText(
                        fixture.getHomeTeam()
                                .getName()
                                .toUpperCase()
                                + "     VS     "
                                + fixture.getAwayTeam()
                                .getName()
                                .toUpperCase()
                );
            }

            scoreLabel.setText(
                    "0   -   0"
            );

            matchStatusLabel.setText(
                    "READY FOR KICK OFF"
            );

            matchFeedStateLabel.setText(
                    "PRE-MATCH"
            );

            matchFeedStateLabel.setForeground(
                    SKY
            );

            matchFeedMinuteLabel.setText(
                    "0'"
            );

            clearEventPane();

            appendStyledText(
                    "MATCH DAY\n",
                    GOLD,
                    true,
                    21
            );

            appendStyledText(
                    "\nYour match is ready.\n\n",
                    Color.WHITE,
                    true,
                    15
            );

            appendStyledText(
                    "Start Match to play event by event.\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    false,
                    14
            );

            appendStyledText(
                    "Skip Match will simulate the full match instantly.\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    false,
                    14
            );

            matchStatsPanel.setMatch(
                    null
            );

            matchMessageLabel.setText(
                    "Start the match or skip directly to Full Time."
            );

            setMatchButtons(
                    true,
                    false,
                    false,
                    true,
                    false,
                    false
            );

            return;
        }

        Match match =
                game.getCurrentUserMatch();

        fixtureLabel.setText(
                match.getHomeTeam()
                        .getName()
                        .toUpperCase()
                        + "     VS     "
                        + match.getAwayTeam()
                        .getName()
                        .toUpperCase()
        );

        scoreLabel.setText(
                match.getHomeScore()
                        + "   -   "
                        + match.getAwayScore()
        );

        matchStatusLabel.setText(
                match.getMatchStatus()
        );

        matchStatsPanel.setMatch(
                match
        );

        if (match.isFullTime()) {

            matchFeedStateLabel.setText(
                    "FULL TIME"
            );

            matchFeedStateLabel.setForeground(
                    GOLD
            );

            matchFeedMinuteLabel.setText(
                    "90'"
            );

        } else if (match.isHalfTime()) {

            matchFeedStateLabel.setText(
                    "HALF TIME"
            );

            matchFeedStateLabel.setForeground(
                    GOLD
            );

            matchFeedMinuteLabel.setText(
                    "45'"
            );

        } else {

            matchFeedStateLabel.setText(
                    "● LIVE"
            );

            matchFeedStateLabel.setForeground(
                    LIVE_RED
            );

            matchFeedMinuteLabel.setText(
                    match.getCurrentMinute()
                            + "'"
            );
        }

        if (game.isRoundCompleted()) {

            matchMessageLabel.setText(
                    "Round completed."
            );

            setMatchButtons(
                    false,
                    false,
                    false,
                    false,
                    false,
                    !game.isSeasonFinished()
            );

            return;
        }

        if (match.isFullTime()) {

            matchMessageLabel.setText(
                    "Full Time. Complete the round."
            );

            setMatchButtons(
                    false,
                    false,
                    false,
                    false,
                    true,
                    false
            );

            return;
        }

        if (match.isHalfTime()) {

            matchMessageLabel.setText(
                    "Half Time."
            );

            setMatchButtons(
                    false,
                    false,
                    true,
                    true,
                    false,
                    false
            );

            return;
        }

        matchMessageLabel.setText(
                "Press Next Event or Skip Match."
        );

        setMatchButtons(
                false,
                true,
                false,
                true,
                false,
                false
        );
    }

    private void setMatchButtons(
            boolean start,
            boolean next,
            boolean secondHalf,
            boolean skip,
            boolean complete,
            boolean nextRound) {

        startMatchButton.setEnabled(
                start
        );

        nextEventButton.setEnabled(
                next
        );

        secondHalfButton.setEnabled(
                secondHalf
        );

        skipMatchButton.setEnabled(
                skip
        );

        completeRoundButton.setEnabled(
                complete
        );

        nextRoundButton.setEnabled(
                nextRound
        );
    }

    private void startMatch() {

        if (game.startCurrentRound()) {

            clearEventPane();

            appendStyledText(
                    "● LIVE\n",
                    LIVE_RED,
                    true,
                    13
            );

            appendStyledText(
                    "\n⚽ KICK OFF\n",
                    GOLD,
                    true,
                    23
            );

            appendStyledText(
                    "\nThe match is underway.\n\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    false,
                    14
            );

            appendMatchDivider();
        }

        refreshAll();
    }

    private void nextEvent() {

        MatchEvent event =
                game.nextUserMatchEvent();

        if (event != null) {

            appendMatchEvent(
                    event
            );

            if (event.getType()
                    .equals(
                            MatchEvent.GOAL
                    )) {

                appendStyledText(
                        "\nCURRENT SCORE   "
                                + game.getCurrentUserMatch()
                                .getScoreText()
                                + "\n",
                        GOLD,
                        true,
                        15
                );
            }

            appendStyledText(
                    "\n",
                    Color.WHITE,
                    false,
                    12
            );

            appendMatchDivider();
        }

        refreshHome();
        refreshMatch();
        refreshStats();

        Match match =
                game.getCurrentUserMatch();

        if (match != null
                && match.isFullTime()) {

            showMatchResultEffect();
        }
    }

    private void appendMatchEvent(
            MatchEvent event) {

        String type =
                event.getType();

        if (type.equals(
                MatchEvent.GOAL
        )) {

            appendStyledText(
                    "⚽  GOAL\n",
                    GOLD,
                    true,
                    12
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    new Color(
                            134,
                            239,
                            172
                    ),
                    true,
                    20
            );

            return;
        }

        if (type.equals(
                MatchEvent.SAVE
        )) {

            appendStyledText(
                    "🧤  SAVE\n",
                    SKY,
                    true,
                    12
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    new Color(
                            186,
                            230,
                            253
                    ),
                    true,
                    16
            );

            return;
        }

        if (type.equals(
                MatchEvent.HIT_POST
        )) {

            appendStyledText(
                    "◉  WOODWORK\n",
                    new Color(
                            251,
                            146,
                            60
                    ),
                    true,
                    12
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    new Color(
                            253,
                            186,
                            116
                    ),
                    true,
                    16
            );

            return;
        }

        if (type.equals(
                MatchEvent.SHOT_BLOCKED
        )) {

            appendStyledText(
                    "■  BLOCKED\n",
                    SILVER,
                    true,
                    12
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    SILVER,
                    false,
                    15
            );

            return;
        }

        if (type.equals(
                MatchEvent.SHOT_WIDE
        )) {

            appendStyledText(
                    "→  WIDE\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    true,
                    12
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    SILVER,
                    false,
                    15
            );

            return;
        }

        if (type.equals(
                MatchEvent.SHOT_OVER
        )) {

            appendStyledText(
                    "↑  OVER\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    true,
                    12
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    SILVER,
                    false,
                    15
            );

            return;
        }

        if (type.equals(
                MatchEvent.HALF_TIME
        )) {

            appendStyledText(
                    "HALF TIME\n",
                    GOLD,
                    true,
                    22
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    Color.WHITE,
                    true,
                    15
            );

            return;
        }

        if (type.equals(
                MatchEvent.FULL_TIME
        )) {

            appendStyledText(
                    "FULL TIME\n",
                    GOLD,
                    true,
                    23
            );

            appendStyledText(
                    event.getDisplayText()
                            + "\n",
                    Color.WHITE,
                    true,
                    16
            );

            return;
        }

        appendStyledText(
                event.getDisplayText()
                        + "\n",
                Color.WHITE,
                false,
                15
        );
    }

    private void appendMatchDivider() {

        appendStyledText(
                "────────────────────────────────────\n\n",
                new Color(
                        51,
                        65,
                        85
                ),
                false,
                11
        );
    }

    private void startSecondHalf() {

        if (game.startUserSecondHalf()) {

            appendStyledText(
                    "● LIVE\n",
                    LIVE_RED,
                    true,
                    13
            );

            appendStyledText(
                    "\n⚽ SECOND HALF\n",
                    GOLD,
                    true,
                    21
            );

            appendStyledText(
                    "\nThe second half is underway.\n\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    false,
                    14
            );

            appendMatchDivider();
        }

        refreshMatch();
    }

    private void skipMatch() {

        if (!game.isSetupCompleted()) {

            return;
        }

        boolean justStarted = false;

        if (!game.isRoundStarted()) {

            if (!game.startCurrentRound()) {

                return;
            }

            justStarted = true;

            clearEventPane();

            appendStyledText(
                    "⏩ MATCH SIMULATION\n",
                    GOLD,
                    true,
                    21
            );

            appendStyledText(
                    "\nSimulating match events...\n\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    false,
                    14
            );

            appendMatchDivider();
        }

        Match match =
                game.getCurrentUserMatch();

        if (match == null
                || match.isFullTime()) {

            return;
        }

        boolean wasHalfTime =
                match.isHalfTime();

        int oldEventCount =
                match.getEventCount();

        if (game.skipCurrentUserMatch()) {

            if (!justStarted) {

                appendStyledText(
                        "⏩ SIMULATING REMAINING MATCH\n\n",
                        GOLD,
                        true,
                        16
                );
            }

            if (wasHalfTime) {

                appendStyledText(
                        "⚽ SECOND HALF\n\n",
                        GOLD,
                        true,
                        19
                );

                appendMatchDivider();
            }

            for (int i = oldEventCount;
                 i < match.getEventCount();
                 i++) {

                MatchEvent event =
                        match.getEvent(i);

                if (event == null) {

                    continue;
                }

                appendMatchEvent(
                        event
                );

                appendStyledText(
                        "\n",
                        Color.WHITE,
                        false,
                        11
                );

                if (event.getType()
                        .equals(
                                MatchEvent.HALF_TIME
                        )) {

                    appendMatchDivider();

                    appendStyledText(
                            "⚽ SECOND HALF\n\n",
                            GOLD,
                            true,
                            19
                    );
                }

                appendMatchDivider();
            }

            appendStyledText(
                    "FINAL SCORE\n",
                    GOLD,
                    true,
                    18
            );

            appendStyledText(
                    "\n"
                            + match.getScoreText()
                            + "\n\n",
                    Color.WHITE,
                    true,
                    21
            );

            appendStyledText(
                    "Press Complete Round to continue.\n",
                    new Color(
                            148,
                            163,
                            184
                    ),
                    false,
                    13
            );
        }

        refreshHome();
        refreshMatch();
        refreshStats();

        showMatchResultEffect();
    }

    private void completeRound() {

        if (game.completeCurrentRound()) {

            appendStyledText(
                    "\nROUND COMPLETE\n",
                    GOLD,
                    true,
                    19
            );

            appendMatchDivider();

            appendStyledText(
                    game.getCurrentRoundResultsText()
                            + "\n",
                    Color.WHITE,
                    false,
                    14
            );

            appendStyledText(
                    "\nLeague Position   "
                            + game.getUserTeamPosition()
                            + " / 12\n",
                    new Color(
                            134,
                            239,
                            172
                    ),
                    true,
                    15
            );
        }

        refreshAll();

        if (game.isSeasonFinished()) {

            tabs.setSelectedIndex(
                    6
            );
        }
    }

    private void nextRound() {

        if (game.advanceToNextRound()) {

            clearEventPane();

            matchStatsPanel.setMatch(
                    null
            );
        }

        refreshAll();
    }

    private void showMatchResultEffect() {

        if (!game.isSetupCompleted()) {

            return;
        }

        Match match =
                game.getCurrentUserMatch();

        if (match == null
                || !match.isFullTime()) {

            return;
        }

        int round =
                game.getCurrentRoundIndex();

        if (lastResultEffectRound
                == round) {

            return;
        }

        lastResultEffectRound =
                round;

        boolean userHome =
                match.getHomeTeam()
                        == game.getUserTeam();

        int userGoals =
                userHome
                        ? match.getHomeScore()
                        : match.getAwayScore();

        int opponentGoals =
                userHome
                        ? match.getAwayScore()
                        : match.getHomeScore();

        Team opponent =
                userHome
                        ? match.getAwayTeam()
                        : match.getHomeTeam();

        String result;

        if (userGoals
                > opponentGoals) {

            result =
                    "VICTORY!";

        } else if (userGoals
                < opponentGoals) {

            result =
                    "DEFEAT";

        } else {

            result =
                    "DRAW";
        }

        String score =
                game.getUserTeam()
                        .getName()
                        + "  "
                        + userGoals
                        + " - "
                        + opponentGoals
                        + "  "
                        + opponent.getName();

        ResultEffectPanel effect =
                new ResultEffectPanel(
                        result,
                        score
                );

        setGlassPane(
                effect
        );

        effect.setVisible(
                true
        );

        if (resultEffectTimer != null
                && resultEffectTimer
                .isRunning()) {

            resultEffectTimer.stop();
        }

        resultEffectTimer =
                new Timer(
                        2000,
                        new ActionListener() {

                            public void actionPerformed(
                                    ActionEvent e) {

                                resultEffectTimer.stop();

                                effect.setVisible(
                                        false
                                );
                            }
                        }
                );

        resultEffectTimer.setRepeats(
                false
        );

        resultEffectTimer.start();
    }

    private void refreshLeague() {

        if (leagueContentPanel == null) {

            return;
        }

        leagueContentPanel.removeAll();

        Team[] standings =
                game.getLeagueTable()
                        .getStandings();

        JLabel heading =
                label(
                        game.isSetupCompleted()
                                ? game.isSeasonFinished()
                                ? "FINAL STANDINGS"
                                : "ROUND "
                                + game.getCurrentRoundNumber()
                                + " STANDINGS"
                                : "PRE-SEASON STANDINGS",
                        15,
                        true,
                        MUTED,
                        SwingConstants.LEFT
                );

        heading.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leagueContentPanel.add(
                heading
        );

        leagueContentPanel.add(
                Box.createVerticalStrut(
                        12
                )
        );

        JPanel podium =
                createLeaguePodium(
                        standings
                );

        podium.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        podium.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        160
                )
        );

        leagueContentPanel.add(
                podium
        );

        leagueContentPanel.add(
                Box.createVerticalStrut(
                        20
                )
        );

        JPanel table =
                createLeagueTableCard(
                        standings
                );

        table.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leagueContentPanel.add(
                table
        );

        leagueContentPanel.revalidate();
        leagueContentPanel.repaint();
    }

    private void refreshStats() {

        if (statsContentPanel == null) {

            return;
        }

        statsContentPanel.removeAll();

        PlayerStatistics stats =
                game.getPlayerStatistics();

        Player[] players =
                showingGoals
                        ? stats.getPlayersByGoals()
                        : stats.getPlayersByAssists();

        if (goalsButton != null) {

            goalsButton.setBackground(
                    showingGoals
                            ? new Color(
                            187,
                            247,
                            208
                    )
                            : LIGHT_GREEN
            );
        }

        if (assistsButton != null) {

            assistsButton.setBackground(
                    !showingGoals
                            ? new Color(
                            187,
                            247,
                            208
                    )
                            : LIGHT_GREEN
            );
        }

        JLabel title =
                label(
                        showingGoals
                                ? "⚽ GOAL LEADERS"
                                : "👟 ASSIST LEADERS",
                        17,
                        true,
                        DARK_GREEN,
                        SwingConstants.LEFT
                );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        statsContentPanel.add(
                title
        );

        statsContentPanel.add(
                Box.createVerticalStrut(
                        12
                )
        );

        JPanel podium =
                createStatsPodium(
                        players,
                        stats
                );

        podium.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        podium.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        170
                )
        );

        statsContentPanel.add(
                podium
        );

        statsContentPanel.add(
                Box.createVerticalStrut(
                        20
                )
        );

        JPanel list =
                createStatsListCard(
                        players,
                        stats
                );

        list.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        statsContentPanel.add(
                list
        );

        statsContentPanel.revalidate();
        statsContentPanel.repaint();
    }

    private void refreshAwards() {

        if (awardsContentPanel == null) {

            return;
        }

        awardsContentPanel.removeAll();

        if (game.isSeasonAwardsCalculated()) {

            tabs.setTitleAt(
                    6,
                    "Awards ★"
            );

            JPanel seasonHeader =
                    createSeasonCompleteHeader();

            seasonHeader.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            awardsContentPanel.add(
                    seasonHeader
            );

            awardsContentPanel.add(
                    Box.createVerticalStrut(
                            18
                    )
            );

            JPanel champion =
                    createChampionCard(
                            game.getSeasonAwards()
                                    .getChampion()
                    );

            champion.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            awardsContentPanel.add(
                    champion
            );

            awardsContentPanel.add(
                    Box.createVerticalStrut(
                            18
                    )
            );

            JPanel grid =
                    createAwardGrid();

            grid.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            grid.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            370
                    )
            );

            awardsContentPanel.add(
                    grid
            );

        } else {

            tabs.setTitleAt(
                    6,
                    "Awards"
            );

            awardsContentPanel.add(
                    Box.createVerticalGlue()
            );

            JPanel locked =
                    createAwardsLockedCard();

            locked.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            awardsContentPanel.add(
                    locked
            );

            awardsContentPanel.add(
                    Box.createVerticalGlue()
            );
        }

        awardsContentPanel.revalidate();
        awardsContentPanel.repaint();
    }

    private String displayPlayerName(
            Player player) {

        if (player == null) {

            return "None";
        }

        if (player.isCustomPlayer()) {

            return "⭐ "
                    + player.getName();
        }

        return player.getName();
    }

    private String playerArrayText(
            Player[] players) {

        if (players == null
                || players.length == 0) {

            return "No winner";
        }

        String text = "";

        for (int i = 0;
             i < players.length;
             i++) {

            if (i > 0) {

                text +=
                        ", ";
            }

            text +=
                    displayPlayerName(
                            players[i]
                    );
        }

        return text;
    }

    private Color getRankColor(
            int rank) {

        if (rank == 1) {

            return new Color(
                    202,
                    138,
                    4
            );
        }

        if (rank == 2) {

            return new Color(
                    100,
                    116,
                    139
            );
        }

        if (rank == 3) {

            return BRONZE;
        }

        return TEXT;
    }

    private String ordinal(
            int number) {

        if (number <= 0) {

            return "--";
        }

        int lastTwo =
                number % 100;

        if (lastTwo >= 11
                && lastTwo <= 13) {

            return number
                    + "th";
        }

        int last =
                number % 10;

        if (last == 1) {

            return number
                    + "st";
        }

        if (last == 2) {

            return number
                    + "nd";
        }

        if (last == 3) {

            return number
                    + "rd";
        }

        return number
                + "th";
    }

    private void clearEventPane() {

        if (eventPane != null) {

            eventPane.setText("");
        }
    }

    private void appendStyledText(
            String text,
            Color color,
            boolean bold,
            int size) {

        StyledDocument document =
                eventPane
                        .getStyledDocument();

        SimpleAttributeSet attributes =
                new SimpleAttributeSet();

        StyleConstants.setForeground(
                attributes,
                color
        );

        StyleConstants.setBold(
                attributes,
                bold
        );

        StyleConstants.setFontFamily(
                attributes,
                "Dialog"
        );

        StyleConstants.setFontSize(
                attributes,
                size
        );

        try {

            document.insertString(
                    document.getLength(),
                    text,
                    attributes
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not update match feed."
            );
        }

        eventPane.setCaretPosition(
                document.getLength()
        );
    }

    private JPanel createHero(
            String titleText,
            String subtitleText) {

        HeroPanel panel =
                new HeroPanel();

        panel.setLayout(
                new GridLayout(
                        2,
                        1,
                        0,
                        5
                )
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel title =
                label(
                        titleText,
                        30,
                        true,
                        Color.WHITE,
                        SwingConstants.CENTER
                );

        JLabel subtitle =
                label(
                        subtitleText,
                        13,
                        true,
                        new Color(
                                187,
                                247,
                                208
                        ),
                        SwingConstants.CENTER
                );

        panel.add(
                title
        );

        panel.add(
                subtitle
        );

        return panel;
    }

    private JLabel createInfoCard(
            String name,
            String value) {

        RoundedPanel panel =
                new RoundedPanel(
                        new Color(
                                240,
                                253,
                                244
                        )
                );

        panel.setLayout(
                new GridLayout(
                        2,
                        1,
                        0,
                        2
                )
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        187,
                                        247,
                                        208
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                12,
                                8,
                                12,
                                8
                        )
                )
        );

        JLabel nameLabel =
                label(
                        name,
                        11,
                        true,
                        MUTED,
                        SwingConstants.CENTER
                );

        JLabel valueLabel =
                label(
                        value,
                        18,
                        true,
                        DARK_GREEN,
                        SwingConstants.CENTER
                );

        panel.add(
                nameLabel
        );

        panel.add(
                valueLabel
        );

        return valueLabel;
    }

    private JPanel center(
            JPanel card) {

        JPanel holder =
                new JPanel(
                        new GridBagLayout()
                );

        holder.setOpaque(false);

        holder.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        holder.add(
                card
        );

        return holder;
    }

    private JLabel label(
            String text,
            int size,
            boolean bold,
            Color color,
            int alignment) {

        JLabel label =
                new JLabel(
                        text,
                        alignment
                );

        label.setFont(
                font(
                        size,
                        bold
                )
        );

        label.setForeground(
                color
        );

        return label;
    }

    private Font font(
            int size,
            boolean bold) {

        return new Font(
                "SansSerif",
                bold
                        ? Font.BOLD
                        : Font.PLAIN,
                size
        );
    }

    private JButton createButton(
            String text) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                font(
                        14,
                        true
                )
        );

        button.setForeground(
                DARK_GREEN
        );

        button.setBackground(
                LIGHT_GREEN
        );

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        74,
                                        222,
                                        128
                                ),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                9,
                                14,
                                9,
                                14
                        )
                )
        );

        return button;
    }

    private JButton createGoldButton(
            String text) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                font(
                        14,
                        true
                )
        );

        button.setForeground(
                new Color(
                        66,
                        32,
                        6
                )
        );

        button.setBackground(
                GOLD
        );

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        202,
                                        138,
                                        4
                                ),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                9,
                                14,
                                9,
                                14
                        )
                )
        );

        return button;
    }

    private void styleInput(
            JComponent component) {

        component.setFont(
                font(
                        15,
                        false
                )
        );

        component.setBackground(
                Color.WHITE
        );

        component.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                SILVER
                        ),
                        BorderFactory.createEmptyBorder(
                                6,
                                8,
                                6,
                                8
                        )
                )
        );
    }

    private JSpinner createSpinner(
            int value) {

        JSpinner spinner =
                new JSpinner(
                        new SpinnerNumberModel(
                                value,
                                0,
                                15,
                                1
                        )
                );

        spinner.setFont(
                font(
                        15,
                        false
                )
        );

        return spinner;
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints c,
            int row,
            String text,
            JComponent component) {

        JLabel label =
                label(
                        text,
                        13,
                        true,
                        MUTED,
                        SwingConstants.LEFT
                );

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        c.weightx = 0.3;

        panel.add(
                label,
                c
        );

        c.gridx = 1;
        c.weightx = 0.7;

        panel.add(
                component,
                c
        );
    }

    private JTextArea textArea() {

        JTextArea area =
                new JTextArea();

        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        area.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        area.setBackground(
                Color.WHITE
        );

        area.setForeground(
                TEXT
        );

        area.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        14,
                        14,
                        14
                )
        );

        return area;
    }

    private void refreshAll() {

        refreshCreatePage();
        refreshHome();
        refreshSquad();
        refreshMatch();
        refreshLeague();
        refreshStats();
        refreshAwards();
    }

    public void stateChanged(
            ChangeEvent event) {

        if (event.getSource()
                == tabs) {

            refreshAll();

            updateMusicForCurrentTab();

        } else {

            updateSkillTotal();
        }
    }

    public void actionPerformed(
            ActionEvent event) {

        Object source =
                event.getSource();

        if (source == exitButton) {

            String[] options = {
                    "Yes",
                    "No"
            };

            int choice =
                    JOptionPane.showOptionDialog(
                            this,
                            "Are you sure you want to exit the game?",
                            "Exit Game",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]
                    );

            if (choice == 0) {

                audioManager.stop();

                System.exit(0);
            }

            return;
        }

        if (source == teamComboBox) {

            updateReplaceOptions();
            refreshCreatePage();

            return;
        }

        if (source == createButton) {

            createPlayer();

            return;
        }

        if (source == completeSetupButton) {

            completeSetup();

            return;
        }

        if (source == upgradeShootingButton) {

            upgradePlayer(
                    "Shooting"
            );

            return;
        }

        if (source == upgradePassingButton) {

            upgradePlayer(
                    "Passing"
            );

            return;
        }

        if (source == upgradeDefenseButton) {

            upgradePlayer(
                    "Defense"
            );

            return;
        }

        int slot =
                getSquadSlot(
                        source
                );

        if (slot != -1) {

            selectSquadPlayer(
                    slot
            );

            return;
        }

        if (source == startMatchButton) {

            startMatch();

            return;
        }

        if (source == nextEventButton) {

            nextEvent();

            return;
        }

        if (source == secondHalfButton) {

            startSecondHalf();

            return;
        }

        if (source == skipMatchButton) {

            skipMatch();

            return;
        }

        if (source == completeRoundButton) {

            completeRound();

            return;
        }

        if (source == nextRoundButton) {

            nextRound();

            return;
        }

        if (source == goalsButton) {

            showingGoals = true;

            refreshStats();

            return;
        }

        if (source == assistsButton) {

            showingGoals = false;

            refreshStats();
        }
    }

    private class PatternPanel
            extends JPanel {

        public PatternPanel() {

            setOpaque(false);
        }

        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w =
                    getWidth();

            int h =
                    getHeight();

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    236,
                                    253,
                                    245
                            ),
                            w,
                            h,
                            new Color(
                                    226,
                                    232,
                                    240
                            )
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRect(
                    0,
                    0,
                    w,
                    h
            );

            g2.setColor(
                    new Color(
                            22,
                            163,
                            74,
                            18
                    )
            );

            g2.setStroke(
                    new BasicStroke(2)
            );

            for (int x = -h;
                 x < w;
                 x += 90) {

                g2.drawLine(
                        x,
                        0,
                        x + h,
                        h
                );
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class HeroPanel
            extends JPanel {

        public HeroPanel() {

            setOpaque(false);
        }

        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            int w =
                    getWidth();

            int h =
                    getHeight();

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            NAVY,
                            w,
                            h,
                            DARK_GREEN
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRect(
                    0,
                    0,
                    w,
                    h
            );

            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            20
                    )
            );

            for (int x = 0;
                 x < w;
                 x += 70) {

                g2.drawLine(
                        x,
                        0,
                        x + 100,
                        h
                );
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class ScoreboardPanel
            extends JPanel {

        public ScoreboardPanel() {

            setOpaque(false);
        }

        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            int w =
                    getWidth();

            int h =
                    getHeight();

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    5,
                                    15,
                                    35
                            ),
                            w,
                            h,
                            new Color(
                                    15,
                                    100,
                                    60
                            )
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRect(
                    0,
                    0,
                    w,
                    h
            );

            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            25
                    )
            );

            for (int x = 0;
                 x < w;
                 x += 80) {

                g2.drawLine(
                        x,
                        0,
                        x + 60,
                        h
                );
            }

            g2.setColor(
                    new Color(
                            250,
                            204,
                            21,
                            45
                    )
            );

            g2.fillOval(
                    w / 2 - 130,
                    -80,
                    260,
                    260
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class RoundedPanel
            extends JPanel {

        private Color backgroundColor;

        public RoundedPanel(
                Color backgroundColor) {

            this.backgroundColor =
                    backgroundColor;

            setOpaque(false);
        }

        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    new Color(
                            15,
                            23,
                            42,
                            20
                    )
            );

            g2.fillRoundRect(
                    4,
                    5,
                    getWidth() - 8,
                    getHeight() - 8,
                    24,
                    24
            );

            g2.setColor(
                    backgroundColor
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 8,
                    getHeight() - 8,
                    24,
                    24
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class PitchPanel
            extends JPanel {

        public PitchPanel() {

            setOpaque(false);
        }

        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w =
                    getWidth();

            int h =
                    getHeight();

            GradientPaint pitchGradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    22,
                                    120,
                                    62
                            ),
                            w,
                            h,
                            new Color(
                                    31,
                                    150,
                                    74
                            )
                    );

            g2.setPaint(
                    pitchGradient
            );

            g2.fillRect(
                    0,
                    0,
                    w,
                    h
            );

            int stripeWidth =
                    Math.max(
                            1,
                            w / 10
                    );

            for (int i = 0;
                 i < 10;
                 i++) {

                if (i % 2 == 0) {

                    g2.setColor(
                            new Color(
                                    255,
                                    255,
                                    255,
                                    13
                            )
                    );

                    g2.fillRect(
                            i * stripeWidth,
                            0,
                            stripeWidth,
                            h
                    );
                }
            }

            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            195
                    )
            );

            g2.setStroke(
                    new BasicStroke(2)
            );

            int margin = 22;

            g2.drawRoundRect(
                    margin,
                    margin,
                    w - margin * 2,
                    h - margin * 2,
                    10,
                    10
            );

            int middleY =
                    h / 2;

            g2.drawLine(
                    margin,
                    middleY,
                    w - margin,
                    middleY
            );

            g2.drawOval(
                    w / 2 - 55,
                    middleY - 55,
                    110,
                    110
            );

            g2.fillOval(
                    w / 2 - 3,
                    middleY - 3,
                    6,
                    6
            );

            int boxWidth =
                    Math.min(
                            300,
                            w / 3
                    );

            int boxHeight =
                    80;

            g2.drawRect(
                    w / 2
                            - boxWidth / 2,
                    margin,
                    boxWidth,
                    boxHeight
            );

            g2.drawRect(
                    w / 2
                            - boxWidth / 2,
                    h
                            - margin
                            - boxHeight,
                    boxWidth,
                    boxHeight
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class MatchStatsPanel
            extends JPanel {

        private Match match;

        public MatchStatsPanel() {

            match = null;

            setOpaque(false);
        }

        public void setMatch(
                Match match) {

            this.match =
                    match;

            repaint();
        }

        protected void paintComponent(
                Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w =
                    getWidth();

            int h =
                    getHeight();

            g2.setColor(
                    new Color(
                            248,
                            250,
                            252
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    w,
                    h
            );

            if (match == null) {

                g2.setColor(
                        MUTED
                );

                g2.setFont(
                        new Font(
                                "Dialog",
                                Font.BOLD,
                                17
                        )
                );

                String text =
                        "MATCH NOT STARTED";

                FontMetrics fm =
                        g2.getFontMetrics();

                g2.drawString(
                        text,
                        w / 2
                                - fm.stringWidth(
                                text
                        )
                                / 2,
                        h / 2
                                - 5
                );

                g2.setFont(
                        new Font(
                                "Dialog",
                                Font.PLAIN,
                                13
                        )
                );

                g2.setColor(
                        new Color(
                                148,
                                163,
                                184
                        )
                );

                String second =
                        "Live statistics will appear here.";

                FontMetrics secondFm =
                        g2.getFontMetrics();

                g2.drawString(
                        second,
                        w / 2
                                - secondFm
                                .stringWidth(
                                        second
                                )
                                / 2,
                        h / 2
                                + 25
                );

                g2.dispose();

                return;
            }

            String homeName =
                    match.getHomeTeam()
                            .getName();

            String awayName =
                    match.getAwayTeam()
                            .getName();

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.BOLD,
                            15
                    )
            );

            g2.setColor(
                    NAVY
            );

            FontMetrics teamFm =
                    g2.getFontMetrics();

            String leftName =
                    shortenTeamName(
                            homeName,
                            16
                    );

            String rightName =
                    shortenTeamName(
                            awayName,
                            16
                    );

            g2.drawString(
                    leftName,
                    24,
                    37
            );

            g2.drawString(
                    rightName,
                    w
                            - 24
                            - teamFm
                            .stringWidth(
                                    rightName
                            ),
                    37
            );

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.BOLD,
                            30
                    )
            );

            String scoreText =
                    match.getHomeScore()
                            + "   -   "
                            + match.getAwayScore();

            FontMetrics scoreFm =
                    g2.getFontMetrics();

            g2.drawString(
                    scoreText,
                    w / 2
                            - scoreFm
                            .stringWidth(
                                    scoreText
                            )
                            / 2,
                    43
            );

            g2.setColor(
                    new Color(
                            226,
                            232,
                            240
                    )
            );

            g2.fillRoundRect(
                    20,
                    58,
                    w - 40,
                    2,
                    2,
                    2
            );

            int available =
                    Math.max(
                            280,
                            h - 70
                    );

            int gap =
                    available / 4;

            int first =
                    88;

            drawStatRow(
                    g2,
                    first,
                    "SHOTS",
                    match.getHomeShots(),
                    match.getAwayShots(),
                    false
            );

            drawStatRow(
                    g2,
                    first + gap,
                    "SHOTS ON TARGET",
                    match.getHomeShotsOnTarget(),
                    match.getAwayShotsOnTarget(),
                    false
            );

            drawStatRow(
                    g2,
                    first + gap * 2,
                    "POSSESSION",
                    match.getHomePossession(),
                    match.getAwayPossession(),
                    true
            );

            drawStatRow(
                    g2,
                    first + gap * 3,
                    "GOALS",
                    match.getHomeScore(),
                    match.getAwayScore(),
                    false
            );

            g2.dispose();
        }

        private void drawStatRow(
                Graphics2D g2,
                int y,
                String title,
                int homeValue,
                int awayValue,
                boolean percentage) {

            int w =
                    getWidth();

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.BOLD,
                            11
                    )
            );

            g2.setColor(
                    MUTED
            );

            FontMetrics titleFm =
                    g2.getFontMetrics();

            g2.drawString(
                    title,
                    w / 2
                            - titleFm
                            .stringWidth(
                                    title
                            )
                            / 2,
                    y
            );

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.BOLD,
                            17
                    )
            );

            g2.setColor(
                    NAVY
            );

            String homeText =
                    percentage
                            ? homeValue
                            + "%"
                            : ""
                            + homeValue;

            String awayText =
                    percentage
                            ? awayValue
                            + "%"
                            : ""
                            + awayValue;

            g2.drawString(
                    homeText,
                    27,
                    y + 28
            );

            FontMetrics valueFm =
                    g2.getFontMetrics();

            g2.drawString(
                    awayText,
                    w
                            - 27
                            - valueFm
                            .stringWidth(
                                    awayText
                            ),
                    y + 28
            );

            int barX =
                    72;

            int barWidth =
                    Math.max(
                            80,
                            w - 144
                    );

            int barY =
                    y + 15;

            int barHeight =
                    12;

            g2.setColor(
                    new Color(
                            226,
                            232,
                            240
                    )
            );

            g2.fillRoundRect(
                    barX,
                    barY,
                    barWidth,
                    barHeight,
                    12,
                    12
            );

            int total =
                    homeValue
                            + awayValue;

            double homeRatio;

            if (total == 0) {

                homeRatio =
                        0.5;

            } else {

                homeRatio =
                        homeValue
                                / (double)
                                total;
            }

            int homeWidth =
                    (int)
                            Math.round(
                                    barWidth
                                            * homeRatio
                            );

            int awayWidth =
                    barWidth
                            - homeWidth;

            if (homeWidth > 0) {

                g2.setColor(
                        new Color(
                                34,
                                197,
                                94
                        )
                );

                g2.fillRoundRect(
                        barX,
                        barY,
                        homeWidth,
                        barHeight,
                        12,
                        12
                );
            }

            if (awayWidth > 0) {

                g2.setColor(
                        new Color(
                                59,
                                130,
                                246
                        )
                );

                g2.fillRoundRect(
                        barX
                                + homeWidth,
                        barY,
                        awayWidth,
                        barHeight,
                        12,
                        12
                );
            }

            g2.setColor(
                    Color.WHITE
            );

            g2.fillRect(
                    w / 2
                            - 2,
                    barY
                            - 2,
                    4,
                    barHeight
                            + 4
            );
        }

        private String shortenTeamName(
                String name,
                int limit) {

            if (name == null) {

                return "";
            }

            if (name.length()
                    <= limit) {

                return name;
            }

            return name.substring(
                    0,
                    limit - 2
            )
                    + "..";
        }
    }

    private class ResultEffectPanel
            extends JPanel {

        private String result;
        private String score;

        public ResultEffectPanel(
                String result,
                String score) {

            this.result =
                    result;

            this.score =
                    score;

            setOpaque(false);

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            addMouseListener(
                    new MouseAdapter() {

                        public void mouseClicked(
                                MouseEvent e) {

                            if (!SwingUtilities
                                    .isLeftMouseButton(
                                            e
                                    )) {

                                return;
                            }

                            if (resultEffectTimer
                                    != null
                                    && resultEffectTimer
                                    .isRunning()) {

                                resultEffectTimer.stop();
                            }

                            setVisible(
                                    false
                            );
                        }
                    }
            );
        }

        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w =
                    getWidth();

            int h =
                    getHeight();

            g2.setColor(
                    new Color(
                            0,
                            0,
                            0,
                            160
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    w,
                    h
            );

            Color mainColor;

            if (result.equals(
                    "VICTORY!"
            )) {

                mainColor =
                        GOLD;

            } else if (result.equals(
                    "DEFEAT"
            )) {

                mainColor =
                        new Color(
                                239,
                                68,
                                68
                        );

            } else {

                mainColor =
                        SILVER;
            }

            int panelWidth =
                    600;

            int panelHeight =
                    270;

            int x =
                    w / 2
                            - panelWidth / 2;

            int y =
                    h / 2
                            - panelHeight / 2;

            g2.setColor(
                    new Color(
                            15,
                            23,
                            42,
                            245
                    )
            );

            g2.fillRoundRect(
                    x,
                    y,
                    panelWidth,
                    panelHeight,
                    35,
                    35
            );

            g2.setStroke(
                    new BasicStroke(
                            5
                    )
            );

            g2.setColor(
                    mainColor
            );

            g2.drawRoundRect(
                    x,
                    y,
                    panelWidth,
                    panelHeight,
                    35,
                    35
            );

            g2.fillRoundRect(
                    x + 150,
                    y + 18,
                    300,
                    5,
                    5,
                    5
            );

            int titleSize =
                    result.equals(
                            "VICTORY!"
                    )
                            ? 64
                            : 58;

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.BOLD,
                            titleSize
                    )
            );

            g2.setColor(
                    mainColor
            );

            FontMetrics titleMetrics =
                    g2.getFontMetrics();

            int titleX =
                    w / 2
                            - titleMetrics
                            .stringWidth(
                                    result
                            )
                            / 2;

            g2.drawString(
                    result,
                    titleX,
                    h / 2
                            - 40
            );

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.BOLD,
                            21
                    )
            );

            g2.setColor(
                    Color.WHITE
            );

            FontMetrics scoreMetrics =
                    g2.getFontMetrics();

            int scoreX =
                    w / 2
                            - scoreMetrics
                            .stringWidth(
                                    score
                            )
                            / 2;

            g2.drawString(
                    score,
                    scoreX,
                    h / 2
                            + 20
            );

            String message;

            if (result.equals(
                    "VICTORY!"
            )) {

                message =
                        "WHAT A WIN!";

            } else if (result.equals(
                    "DEFEAT"
            )) {

                message =
                        "COME BACK STRONGER.";

            } else {

                message =
                        "POINTS SHARED.";
            }

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.ITALIC,
                            16
                    )
            );

            g2.setColor(
                    new Color(
                            226,
                            232,
                            240
                    )
            );

            FontMetrics messageMetrics =
                    g2.getFontMetrics();

            int messageX =
                    w / 2
                            - messageMetrics
                            .stringWidth(
                                    message
                            )
                            / 2;

            g2.drawString(
                    message,
                    messageX,
                    h / 2
                            + 65
            );

            String clickText =
                    "LEFT-CLICK TO CONTINUE";

            g2.setFont(
                    new Font(
                            "Dialog",
                            Font.PLAIN,
                            12
                    )
            );

            g2.setColor(
                    new Color(
                            148,
                            163,
                            184
                    )
            );

            FontMetrics clickMetrics =
                    g2.getFontMetrics();

            int clickX =
                    w / 2
                            - clickMetrics
                            .stringWidth(
                                    clickText
                            )
                            / 2;

            g2.drawString(
                    clickText,
                    clickX,
                    h / 2
                            + 105
            );

            g2.dispose();
        }
    }

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () ->
                        new FootballGUI()
        );
    }
}