package com.aquareminder.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserSettingsDao_Impl implements UserSettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserSettings> __insertionAdapterOfUserSettings;

  public UserSettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserSettings = new EntityInsertionAdapter<UserSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_settings` (`id`,`dailyTargetMl`,`glassSizeMl`,`reminderIntervalMinutes`,`activeStartHour`,`activeEndHour`,`isReminderEnabled`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettings entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDailyTargetMl());
        statement.bindLong(3, entity.getGlassSizeMl());
        statement.bindLong(4, entity.getReminderIntervalMinutes());
        statement.bindLong(5, entity.getActiveStartHour());
        statement.bindLong(6, entity.getActiveEndHour());
        final int _tmp = entity.isReminderEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
  }

  @Override
  public Object saveSettings(final UserSettings settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserSettings.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserSettings> getSettings() {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_settings"}, new Callable<UserSettings>() {
      @Override
      @Nullable
      public UserSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDailyTargetMl = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyTargetMl");
          final int _cursorIndexOfGlassSizeMl = CursorUtil.getColumnIndexOrThrow(_cursor, "glassSizeMl");
          final int _cursorIndexOfReminderIntervalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderIntervalMinutes");
          final int _cursorIndexOfActiveStartHour = CursorUtil.getColumnIndexOrThrow(_cursor, "activeStartHour");
          final int _cursorIndexOfActiveEndHour = CursorUtil.getColumnIndexOrThrow(_cursor, "activeEndHour");
          final int _cursorIndexOfIsReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReminderEnabled");
          final UserSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpDailyTargetMl;
            _tmpDailyTargetMl = _cursor.getInt(_cursorIndexOfDailyTargetMl);
            final int _tmpGlassSizeMl;
            _tmpGlassSizeMl = _cursor.getInt(_cursorIndexOfGlassSizeMl);
            final int _tmpReminderIntervalMinutes;
            _tmpReminderIntervalMinutes = _cursor.getInt(_cursorIndexOfReminderIntervalMinutes);
            final int _tmpActiveStartHour;
            _tmpActiveStartHour = _cursor.getInt(_cursorIndexOfActiveStartHour);
            final int _tmpActiveEndHour;
            _tmpActiveEndHour = _cursor.getInt(_cursorIndexOfActiveEndHour);
            final boolean _tmpIsReminderEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsReminderEnabled);
            _tmpIsReminderEnabled = _tmp != 0;
            _result = new UserSettings(_tmpId,_tmpDailyTargetMl,_tmpGlassSizeMl,_tmpReminderIntervalMinutes,_tmpActiveStartHour,_tmpActiveEndHour,_tmpIsReminderEnabled);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSettingsSync(final Continuation<? super UserSettings> $completion) {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserSettings>() {
      @Override
      @Nullable
      public UserSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDailyTargetMl = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyTargetMl");
          final int _cursorIndexOfGlassSizeMl = CursorUtil.getColumnIndexOrThrow(_cursor, "glassSizeMl");
          final int _cursorIndexOfReminderIntervalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderIntervalMinutes");
          final int _cursorIndexOfActiveStartHour = CursorUtil.getColumnIndexOrThrow(_cursor, "activeStartHour");
          final int _cursorIndexOfActiveEndHour = CursorUtil.getColumnIndexOrThrow(_cursor, "activeEndHour");
          final int _cursorIndexOfIsReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReminderEnabled");
          final UserSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpDailyTargetMl;
            _tmpDailyTargetMl = _cursor.getInt(_cursorIndexOfDailyTargetMl);
            final int _tmpGlassSizeMl;
            _tmpGlassSizeMl = _cursor.getInt(_cursorIndexOfGlassSizeMl);
            final int _tmpReminderIntervalMinutes;
            _tmpReminderIntervalMinutes = _cursor.getInt(_cursorIndexOfReminderIntervalMinutes);
            final int _tmpActiveStartHour;
            _tmpActiveStartHour = _cursor.getInt(_cursorIndexOfActiveStartHour);
            final int _tmpActiveEndHour;
            _tmpActiveEndHour = _cursor.getInt(_cursorIndexOfActiveEndHour);
            final boolean _tmpIsReminderEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsReminderEnabled);
            _tmpIsReminderEnabled = _tmp != 0;
            _result = new UserSettings(_tmpId,_tmpDailyTargetMl,_tmpGlassSizeMl,_tmpReminderIntervalMinutes,_tmpActiveStartHour,_tmpActiveEndHour,_tmpIsReminderEnabled);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
