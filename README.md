# AquaReminder — Android Kotlin

Skeleton project untuk aplikasi pengingat & pelacak minum air putih.

## Cara pakai
1. Buka Android Studio → **Open** → pilih folder `AquaReminder` ini.
2. Biarkan Gradle sync (Android Studio akan melengkapi file `settings.gradle`,
   `build.gradle` (project-level), dan `gradle.properties` secara otomatis
   lewat wizard "New Project" jika folder ini dijadikan project baru —
   atau salin file-file itu dari template proyek Compose kosong).
3. Jalankan di emulator/device dengan minSdk 24 (Android 7.0) ke atas.

## Struktur kode

```
data/
  WaterEntry.kt          -> Entity WaterEntry & UserSettings (Room)
  WaterDao.kt            -> DAO query database
  AppDatabase.kt         -> Setup Room database (singleton)
  WaterRepository.kt     -> Jembatan data antara DAO <-> ViewModel

ui/
  DashboardViewModel.kt  -> State & logika bisnis dashboard
  DashboardScreen.kt     -> UI Compose (progress ring, tombol log cepat)

notification/
  WaterReminderWorker.kt -> WorkManager job, jalan tiap interval, cek jam aktif
  NotificationHelper.kt  -> Membuat & menampilkan notifikasi + action button
  QuickLogReceiver.kt    -> Menangani tap "Sudah minum" tanpa buka app
  ReminderScheduler.kt   -> Mendaftarkan/membatalkan periodic work

MainActivity.kt          -> Entry point, request izin notifikasi, setContent Compose
```

## Alur reminder (inti fitur)
1. `MainActivity` memanggil `ReminderScheduler.scheduleReminder()` saat app dibuka.
2. WorkManager menjalankan `WaterReminderWorker` tiap N menit (sesuai `UserSettings`).
3. Worker cek apakah sekarang dalam jam aktif → jika ya, tampilkan notifikasi lewat `NotificationHelper`.
4. User tap tombol "Sudah minum (250ml)" di notifikasi → `QuickLogReceiver` menerima broadcast → langsung `INSERT` ke Room, tanpa membuka aplikasi.
5. `DashboardViewModel` yang meng-observe Room lewat `Flow` otomatis ter-update, progress ring di dashboard langsung berubah saat app dibuka.

