package is.hi.hbv601g.podypus.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public  interface UserDao {
    // There will only ever be one user
    @Query("SELECT * FROM user LIMIT 1")
    public abstract User get();

    @Insert
    public abstract void insertAll(User... users);

    @Delete
    public abstract void delete(User user);
}

