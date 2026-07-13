# AquaReminder 💧

AquaReminder adalah aplikasi Android berbasis Kotlin yang membantu pengguna menjaga kebutuhan hidrasi harian dengan memberikan pengingat minum air secara berkala serta mencatat konsumsi air setiap hari.

## Fitur

- 💧 Mencatat konsumsi air harian.
- 🔔 Pengingat minum air menggunakan WorkManager.
- ⚡ Quick Log langsung dari notifikasi tanpa membuka aplikasi.
- 📊 Dashboard yang menampilkan progres konsumsi air harian.
- 📜 Riwayat konsumsi air.
- ⚙️ Pengaturan target harian, ukuran gelas, interval pengingat, dan jam aktif notifikasi.
- 💾 Penyimpanan data menggunakan Room Database.
- 🎨 Antarmuka modern menggunakan Jetpack Compose.

## Teknologi

- Kotlin
- Jetpack Compose
- Room Database
- WorkManager
- Material Design 3
- MVVM Architecture

## Struktur Project

```
app/
├── data/            # Entity, DAO, Database, Repository
├── notification/    # WorkManager, Notification Helper, Scheduler
├── ui/              # Screen, ViewModel, Navigation
└── MainActivity.kt
```

## Cara Menjalankan

### Persyaratan
- Android Studio Hedgehog atau versi yang lebih baru.
- Android SDK minimum API 24 (Android 7.0).
- Gradle sesuai konfigurasi project.

### Langkah

1. Clone repository.

```bash
git clone https://github.com/Stryver-dtm/AquaReminder.git
```

2. Buka project menggunakan Android Studio.

3. Tunggu proses **Gradle Sync** selesai.

4. Jalankan aplikasi pada emulator atau perangkat Android.

## Cara Kerja Aplikasi

1. Pengguna menentukan target konsumsi air harian.
2. Aplikasi menjadwalkan pengingat menggunakan WorkManager.
3. Notifikasi akan muncul sesuai interval yang telah ditentukan.
4. Pengguna dapat langsung mencatat konsumsi air melalui tombol pada notifikasi ataupun melalui dashboard aplikasi.
5. Data konsumsi disimpan menggunakan Room Database dan ditampilkan secara otomatis pada dashboard serta halaman riwayat.

## Arsitektur

Aplikasi menggunakan pola **MVVM (Model-View-ViewModel)**.

- **Model** : Room Database (Entity, DAO, Repository)
- **View** : Jetpack Compose
- **ViewModel** : Mengelola state dan logika bisnis
- **Background Task** : WorkManager
- **Notification** : Notification Helper & Broadcast Receiver

## Repository

https://github.com/Stryver-dtm/AquaReminder

## Tim Pengembang

- Rara
- Desta Mufti Nusangga
- Anggota Kelompok

---

**AquaReminder** dikembangkan sebagai proyek pembelajaran Android menggunakan Kotlin dan Jetpack Compose untuk membantu pengguna membangun kebiasaan minum air secara teratur.
