# transactionRepo

Project TransactionRepo mempresentasikan transaksi bank sederhana input akun, deposit uang, dan transfer antar nomor rekening. Project ini dibuat agar bisa berjalan di docker container sehingga memudahkan dalam proses CI/CD dan sangat penting dalam arsitektur microservice. Dengan menggunakan git memudahkan Developer untuk melacak perubahan yang dilakukan bahkan mengembalikan ke kondisi project yang diinginkan. Penggunaan Logging untuk cek proses yang sudah terjadi. Untuk menambah kualitas dari code, penambahan Unit Test menjaga jika perubahan code yang dilakukan, tidak mempengaruhi code yang sudah berjalan.

**Langkah langkah running transaction repo dengan docker container**
1. Clone project transactionRepo.
2. Buka project ini di IDE favorit.
3. Arahkan terminal ke folder project.
4. Buat file jar Spring Boot Transaction Repo dengan menjalankan command : mvn package -DskipTests (-DskipTests digunakan untuk skip unit testing yang ada di project. Biasa dilakukan jika unit test memperlambat proses pacakaging). Untuk melihat file .jar springboot sudah terbentuk bisa dicek di dalam target folder.
5. Jalankan docker container menggunakan command : docker compose -f .\compose.yaml up (setting container postgres dan spring boot ada didalam file compose.yaml).
6. Project dijalankan dengan port : 8081 (configuration didalam file compose.yaml).
7. Untuk test API dapat mengunakan Swagger v3 dengan mengakses halaman ini : http://localhost:8081/swagger-ui/index.html. Atau check postman json collection yang dikirimkan bersama dengan tugas ini.

**Keterangan dalam project ini**
1. Project ini menggunakan REST API untuk membedakan jenis transaksi yang masuk ke API
2. Menggunakan Logging Slf4j. Log file dapat ditemukan di folder project dengan nama transaction.log.
3. Menggunakan Swagger UI untuk akses testing yang lebih mudah
4. Memakai dependency Spring Boot 3 yang sudah menggunakan java 17.
5. Terdapat validasi nomor akun yang mengharuskan 6 angka saja, validasi email, dan validasi jika nomor akun tidak ditemukan.
6. Menggunakan database relation untuk table Account dan table Transaction_History. Setiap transaksi oleh akun tertentu akan di record didalam Transaction_History yang dihubungkan dengan nomor akun.
7. Menggunakan Jupiter Unit Tests.
8. Memiliki template request response yang memudahkan tim front end/ tim mobile untuk cek status response API, beserta alasan kenapa terjadi error, sehingga memudahkan untuk debug error.
9. Berikut folder Structur yang ada di project transactionRepo :

![image](https://github.com/Yosu4/transactionRepo/assets/38896649/75018b05-96ea-4da3-b37b-b87cbe86a7ce)


**Yang bisa dikembangkan dalam project ini**
1. Menambah field status pada Account table untuk soft delete. Sehingga tidak menghapus data namun menonaktifkan akun.
2. Menggunakan sistem asynchronous agar transfer lebih efisien.
3. Menambah set time out.
4. Login akun, menambah cache, dan session agar transaksi lebih secure.
5. Menambah Logging dengan track account number.
6. Menambah fitur mengirim dana lebih dari 1 akun.
7. Menambah fitur withdrawal.
8. Menggunakan message broker untuk transaksi lebih interaktif.
9. Menambah Unit test
