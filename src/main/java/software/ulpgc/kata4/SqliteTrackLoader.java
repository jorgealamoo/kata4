package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqliteTrackLoader implements TrackLoader{

    private final Connection connection;
    private final static String URL = "select tracks.name as track, composer, Milliseconds, title, artists.Name as artist from tracks, albums, artists, genres where tracks.AlbumId = albums.AlbumId and albums.ArtistId = artists.ArtistId and tracks.GenreId = genres.GenreId";

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Track> loadAll() {
        try {
            return load(queryAll());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(trackOf(resultSet));
        }
        return result;
    }

    private Track trackOf(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getString("track"),
                resultSet.getString("composer"),
                resultSet.getString("title"),
                resultSet.getString("artist"),
                resultSet.getInt("milliseconds") / 1000
        );
    }

    private ResultSet queryAll() throws SQLException {
        return connection
                .createStatement()
                .executeQuery(URL);
    }
}
