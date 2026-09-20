public class PresetTeam {

    private static Player p(String name, int shooting, int passing, int defense) {
        return new Player(name, shooting, passing, defense, false);
    }

    public static Team[] createTeams() {
        Team[] teams = new Team[12];

        teams[0] = new Team("Barcelona", new Player[]{
                p("Victor Valdes", 1, 8, 9),
                p("Jordi Alba", 6, 8, 8),
                p("Carles Puyol", 4, 7, 10),
                p("Ronald Koeman", 8, 9, 9),
                p("Dani Alves", 6, 9, 8),
                p("Xavi", 6, 10, 7),
                p("Johan Cruyff", 9, 10, 6),
                p("Andres Iniesta", 7, 10, 6),
                p("Ronaldinho", 9, 10, 3),
                p("Luis Suarez", 10, 8, 4),
                p("Lionel Messi", 10, 10, 4)
        });

        teams[1] = new Team("Real Madrid", new Player[]{
                p("Iker Casillas", 1, 6, 10),
                p("Roberto Carlos", 8, 8, 9),
                p("Fernando Hierro", 6, 8, 10),
                p("Sergio Ramos", 7, 8, 10),
                p("Dani Carvajal", 5, 8, 9),
                p("Luka Modric", 7, 10, 7),
                p("Zinedine Zidane", 9, 10, 5),
                p("Toni Kroos", 7, 10, 7),
                p("Cristiano Ronaldo", 10, 8, 5),
                p("Alfredo Di Stefano", 10, 9, 6),
                p("Raul Gonzalez", 9, 8, 4)
        });

        teams[2] = new Team("Manchester City", new Player[]{
                p("Ederson", 1, 10, 9),
                p("Aleksandar Kolarov", 7, 8, 8),
                p("Vincent Kompany", 5, 7, 10),
                p("Ruben Dias", 4, 8, 10),
                p("Pablo Zabaleta", 4, 7, 9),
                p("Yaya Toure", 8, 9, 8),
                p("Kevin De Bruyne", 9, 10, 5),
                p("David Silva", 7, 10, 5),
                p("Raheem Sterling", 8, 7, 3),
                p("Sergio Aguero", 10, 8, 3),
                p("Riyad Mahrez", 9, 9, 3)
        });

        teams[3] = new Team("Liverpool", new Player[]{
                p("Ray Clemence", 1, 6, 10),
                p("Alan Kennedy", 5, 7, 9),
                p("Alan Hansen", 3, 9, 10),
                p("Virgil van Dijk", 6, 8, 10),
                p("Phil Neal", 5, 7, 9),
                p("Graeme Souness", 7, 9, 9),
                p("Kenny Dalglish", 9, 9, 5),
                p("Steven Gerrard", 9, 9, 8),
                p("John Barnes", 9, 8, 4),
                p("Ian Rush", 10, 7, 5),
                p("Mohamed Salah", 10, 8, 4)
        });

        teams[4] = new Team("Manchester United", new Player[]{
                p("Peter Schmeichel", 1, 6, 10),
                p("Denis Irwin", 6, 8, 9),
                p("Rio Ferdinand", 3, 9, 10),
                p("Nemanja Vidic", 3, 6, 10),
                p("Gary Neville", 3, 7, 9),
                p("Roy Keane", 6, 8, 10),
                p("Bobby Charlton", 9, 9, 6),
                p("Paul Scholes", 8, 10, 7),
                p("Ryan Giggs", 8, 9, 4),
                p("Wayne Rooney", 10, 9, 7),
                p("George Best", 10, 9, 3)
        });

        teams[5] = new Team("Chelsea", new Player[]{
                p("Petr Cech", 1, 6, 10),
                p("Ashley Cole", 5, 8, 10),
                p("John Terry", 5, 7, 10),
                p("Ricardo Carvalho", 3, 7, 10),
                p("Branislav Ivanovic", 6, 7, 9),
                p("Claude Makelele", 4, 8, 10),
                p("Frank Lampard", 9, 9, 7),
                p("N'Golo Kante", 5, 8, 10),
                p("Eden Hazard", 9, 9, 3),
                p("Didier Drogba", 10, 7, 6),
                p("Gianfranco Zola", 9, 9, 3)
        });

        teams[6] = new Team("Paris Saint-Germain", new Player[]{
                p("Bernard Lama", 1, 6, 9),
                p("Maxwell", 5, 8, 8),
                p("Thiago Silva", 4, 9, 10),
                p("Marquinhos", 4, 8, 9),
                p("Achraf Hakimi", 8, 8, 7),
                p("Marco Verratti", 5, 10, 7),
                p("Rai", 9, 9, 5),
                p("Luis Fernandez", 6, 8, 9),
                p("Kylian Mbappe", 10, 8, 3),
                p("Zlatan Ibrahimovic", 10, 9, 4),
                p("Angel Di Maria", 8, 10, 4)
        });

        teams[7] = new Team("Benfica", new Player[]{
                p("Manuel Bento", 1, 6, 9),
                p("Alvaro Magalhaes", 4, 7, 9),
                p("Humberto Coelho", 4, 7, 10),
                p("Luisao", 5, 6, 10),
                p("Antonio Veloso", 4, 7, 9),
                p("Mario Coluna", 8, 9, 8),
                p("Rui Costa", 8, 10, 5),
                p("Sheu", 6, 8, 8),
                p("Antonio Simoes", 8, 8, 4),
                p("Eusebio", 10, 8, 4),
                p("Jose Augusto", 9, 8, 3)
        });

        teams[8] = new Team("AC Milan", new Player[]{
                p("Dida", 1, 6, 10),
                p("Paolo Maldini", 4, 8, 10),
                p("Franco Baresi", 3, 9, 10),
                p("Alessandro Nesta", 3, 8, 10),
                p("Mauro Tassotti", 4, 7, 9),
                p("Andrea Pirlo", 6, 10, 7),
                p("Kaka", 9, 9, 5),
                p("Frank Rijkaard", 7, 9, 10),
                p("Ruud Gullit", 9, 9, 7),
                p("Marco van Basten", 10, 8, 4),
                p("Roberto Donadoni", 8, 9, 4)
        });

        teams[9] = new Team("Inter Milan", new Player[]{
                p("Walter Zenga", 1, 6, 10),
                p("Giacinto Facchetti", 7, 8, 9),
                p("Giuseppe Bergomi", 4, 7, 10),
                p("Walter Samuel", 3, 6, 10),
                p("Javier Zanetti", 5, 9, 10),
                p("Lothar Matthaus", 9, 9, 10),
                p("Wesley Sneijder", 9, 10, 5),
                p("Esteban Cambiasso", 6, 9, 9),
                p("Sandro Mazzola", 9, 9, 5),
                p("Ronaldo Nazario", 10, 8, 3),
                p("Jair da Costa", 9, 8, 3)
        });

        teams[10] = new Team("Tottenham Hotspur", new Player[]{
                p("Pat Jennings", 1, 6, 10),
                p("Cyril Knowles", 5, 7, 9),
                p("Ledley King", 4, 8, 10),
                p("Mike England", 4, 7, 9),
                p("Steve Perryman", 5, 8, 9),
                p("Dave Mackay", 7, 8, 10),
                p("Glenn Hoddle", 9, 10, 5),
                p("Ossie Ardiles", 7, 9, 7),
                p("Gareth Bale", 10, 8, 5),
                p("Harry Kane", 10, 9, 5),
                p("Heung-Min Son", 10, 8, 4)
        });

        teams[11] = new Team("Bayern Munich", new Player[]{
                p("Manuel Neuer", 1, 10, 10),
                p("Paul Breitner", 8, 9, 9),
                p("Franz Beckenbauer", 7, 10, 10),
                p("Hans-Georg Schwarzenbeck", 3, 7, 10),
                p("Philipp Lahm", 4, 9, 10),
                p("Bastian Schweinsteiger", 7, 9, 9),
                p("Thomas Muller", 9, 9, 6),
                p("Stefan Effenberg", 8, 9, 8),
                p("Franck Ribery", 9, 9, 4),
                p("Gerd Muller", 10, 7, 4),
                p("Arjen Robben", 10, 9, 3)
        });

        return teams;
    }
}