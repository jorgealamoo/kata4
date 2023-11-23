package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(urlOf("chinook.db"))) {
            SqliteTrackLoader trackLoader = new SqliteTrackLoader(connection);
            List<Track> tracks = trackLoader.loadAll();
            for (Track track : tracks) {
                System.out.println(track);
            }
        }
    }

    private static String urlOf(String filename) {
        return "jdbc:sqlite:" + filename;
    }
}
