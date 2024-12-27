# Pustaka Digital

## Deskripsi
Pustaka Digital adalah aplikasi manajemen perpustakaan yang memungkinkan Admin untuk mencari, menambah, dan mengelola buku menggunakan ISBN atau secara manual. Aplikasi ini juga menyediakan fitur manajemen pengguna dan autentikasi.

## Fitur Utama
- **Pencarian Buku**: Cari buku berdasarkan judul, genre, atau ISBN.
- **Tambah Buku**: Tambah buku baru menggunakan ISBN atau secara manual.
- **Manajemen Pengguna**: Admin dapat mengelola pengguna, termasuk menambah dan menghapus pengguna.
- **Autentikasi**: Sistem login dan registrasi untuk pengguna dan admin.
- **Antarmuka Pengguna**: Antarmuka berbasis Swing yang responsif dan mudah digunakan.

## Alur Kerja Program
1. **Login**: Pengguna masuk ke sistem menggunakan kredensial mereka.
2. **Dashboard**: Setelah login, pengguna diarahkan ke dashboard yang sesuai dengan peran mereka (admin atau pengguna biasa).
3. **Manajemen Buku**: Admin dapat mencari buku, menambah buku baru, atau menghapus buku yang ada.
4. **Manajemen Pengguna**: Admin dapat mengelola pengguna melalui antarmuka yang disediakan.
5. **Logout**: Pengguna dapat keluar dari sistem kapan saja.

## Cara Instalasi
1. Clone repositori ini: 
   ```bash
   git clone https://github.com/username/pustaka-digital.git
   ```
2. Masuk ke direktori proyek:
   ```bash
   cd pustaka-digital
   ```
3. Instal dependensi yang diperlukan:
   - Untuk Java, pastikan Anda memiliki JDK dan Maven terinstal.
   - Jalankan `mvn clean install` untuk membangun proyek.
4. Jalankan aplikasi:
   - Gunakan IDE seperti IntelliJ atau Eclipse untuk menjalankan kelas `Main` di `src/main/java/com/pustakadigital/controller/Main.java`.

## Contoh Penggunaan
Setelah menjalankan aplikasi, Anda akan melihat layar login. Masukkan kredensial Anda untuk masuk. Sebagai admin, Anda dapat mengakses fitur manajemen pengguna dan buku. Sebagai pengguna biasa, Anda dapat mencari dan melihat detail buku.

## Kontribusi
Kami menyambut kontribusi dari siapa pun. Silakan buat pull request atau buka issue untuk diskusi lebih lanjut.
