package is.hi.hbv601g.podypus.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        User.class,
        Episode.class,
        Channel.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract EpisodeDao episodeDao();
    public abstract ChannelDao channelDao();
}
