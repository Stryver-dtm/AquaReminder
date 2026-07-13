package com.aquareminder.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WaterDao_Impl implements WaterDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WaterEntry> __insertionAdapterOfWaterEntry;

  private final EntityDeletionOrUpdateAdapter<WaterEntry> __deletionAdapterOfWaterEntry;

  public WaterDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWaterEntry = new EntityInsertionAdapter<WaterEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `water_entries` (`id`,`amountMl`,`timestamp`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WaterEntry entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAmountMl());
        statement.bindLong(3, entity.getTimestamp());
      }
    };
    this.__deletionAdapterOfWaterEntry = new EntityDeletionOrUpdateAdapter<WaterEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `water_entries` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WaterEntry entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public Object insertEntry(final WaterEntry entry, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWaterEntry.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEntry(final WaterEntry entry, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWaterEntry.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WaterEntry>> getEntriesBetween(final long startMillis, final long endMillis) {
    final String _sql = "SELECT * FROM water_entries WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startMillis);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endMillis);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"water_entries"}, new Callable<List<WaterEntry>>() {
      @Override
      @NonNull
      public List<WaterEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmountMl = CursorUtil.getColumnIndexOrThrow(_cursor, "amountMl");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<WaterEntry> _result = new ArrayList<WaterEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WaterEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAmountMl;
            _tmpAmountMl = _cursor.getInt(_cursorIndexOfAmountMl);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new WaterEntry(_tmpId,_tmpAmountMl,_tmpTimestamp);
            _result.add(_item);
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
  public Flow<Integer> getTotalBetween(final long startMillis, final long endMillis) {
    final String _sql = "SELECT COALESCE(SUM(amountMl), 0) FROM water_entries WHERE timestamp BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startMillis);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endMillis);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"water_entries"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public Flow<List<WaterEntry>> getRecentEntries() {
    final String _sql = "SELECT * FROM water_entries ORDER BY timestamp DESC LIMIT 500";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"water_entries"}, new Callable<List<WaterEntry>>() {
      @Override
      @NonNull
      public List<WaterEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmountMl = CursorUtil.getColumnIndexOrThrow(_cursor, "amountMl");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<WaterEntry> _result = new ArrayList<WaterEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WaterEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAmountMl;
            _tmpAmountMl = _cursor.getInt(_cursorIndexOfAmountMl);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new WaterEntry(_tmpId,_tmpAmountMl,_tmpTimestamp);
            _result.add(_item);
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
  public Object getTotalBetweenSync(final long startMillis, final long endMillis,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(SUM(amountMl), 0) FROM water_entries WHERE timestamp BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startMillis);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endMillis);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
