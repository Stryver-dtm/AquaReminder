package com.aquareminder.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile WaterDao _waterDao;

  private volatile UserSettingsDao _userSettingsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `water_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `amountMl` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_settings` (`id` INTEGER NOT NULL, `dailyTargetMl` INTEGER NOT NULL, `glassSizeMl` INTEGER NOT NULL, `reminderIntervalMinutes` INTEGER NOT NULL, `activeStartHour` INTEGER NOT NULL, `activeEndHour` INTEGER NOT NULL, `isReminderEnabled` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0a1716733e56237418274934f72c011b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `water_entries`");
        db.execSQL("DROP TABLE IF EXISTS `user_settings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWaterEntries = new HashMap<String, TableInfo.Column>(3);
        _columnsWaterEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWaterEntries.put("amountMl", new TableInfo.Column("amountMl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWaterEntries.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWaterEntries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWaterEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWaterEntries = new TableInfo("water_entries", _columnsWaterEntries, _foreignKeysWaterEntries, _indicesWaterEntries);
        final TableInfo _existingWaterEntries = TableInfo.read(db, "water_entries");
        if (!_infoWaterEntries.equals(_existingWaterEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "water_entries(com.aquareminder.data.WaterEntry).\n"
                  + " Expected:\n" + _infoWaterEntries + "\n"
                  + " Found:\n" + _existingWaterEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsUserSettings = new HashMap<String, TableInfo.Column>(7);
        _columnsUserSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("dailyTargetMl", new TableInfo.Column("dailyTargetMl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("glassSizeMl", new TableInfo.Column("glassSizeMl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("reminderIntervalMinutes", new TableInfo.Column("reminderIntervalMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("activeStartHour", new TableInfo.Column("activeStartHour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("activeEndHour", new TableInfo.Column("activeEndHour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("isReminderEnabled", new TableInfo.Column("isReminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserSettings = new TableInfo("user_settings", _columnsUserSettings, _foreignKeysUserSettings, _indicesUserSettings);
        final TableInfo _existingUserSettings = TableInfo.read(db, "user_settings");
        if (!_infoUserSettings.equals(_existingUserSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "user_settings(com.aquareminder.data.UserSettings).\n"
                  + " Expected:\n" + _infoUserSettings + "\n"
                  + " Found:\n" + _existingUserSettings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "0a1716733e56237418274934f72c011b", "8ee36f6d0644fee009a24ef604b67516");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "water_entries","user_settings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `water_entries`");
      _db.execSQL("DELETE FROM `user_settings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WaterDao.class, WaterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserSettingsDao.class, UserSettingsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WaterDao waterDao() {
    if (_waterDao != null) {
      return _waterDao;
    } else {
      synchronized(this) {
        if(_waterDao == null) {
          _waterDao = new WaterDao_Impl(this);
        }
        return _waterDao;
      }
    }
  }

  @Override
  public UserSettingsDao userSettingsDao() {
    if (_userSettingsDao != null) {
      return _userSettingsDao;
    } else {
      synchronized(this) {
        if(_userSettingsDao == null) {
          _userSettingsDao = new UserSettingsDao_Impl(this);
        }
        return _userSettingsDao;
      }
    }
  }
}
