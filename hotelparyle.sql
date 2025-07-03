-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 01 Jul 2025 pada 17.43
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotelparyle`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `tipe_kamar` varchar(50) NOT NULL,
  `id_kamar_booked` varchar(20) DEFAULT NULL,
  `lama_menginap` int(11) NOT NULL,
  `total_bayar` double NOT NULL,
  `tanggal_booking` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `customers`
--

INSERT INTO `customers` (`id`, `nama`, `nik`, `tipe_kamar`, `id_kamar_booked`, `lama_menginap`, `total_bayar`, `tanggal_booking`) VALUES
(11, 'adiada', '123123', 'Suite Room', 'STE001', 1, 300000, '2025-06-28 20:18:31'),
(12, 'Adi', '11298981', 'Single Room', 'SGL001', 1, 100000, '2025-06-28 20:30:36'),
(13, 'Yusrizal', '1239081', 'Double Room', 'DBL001', 2, 300000, '2025-06-28 20:31:29'),
(14, 'Lebu', '12120981', 'Suite Room', NULL, 1, 300000, '2025-06-28 20:32:19');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kamar`
--

CREATE TABLE `kamar` (
  `tipe_kamar` varchar(50) NOT NULL,
  `id_kamar` varchar(20) NOT NULL,
  `harga` double NOT NULL,
  `ketersediaan` varchar(20) NOT NULL DEFAULT 'Tersedia'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `kamar`
--

INSERT INTO `kamar` (`tipe_kamar`, `id_kamar`, `harga`, `ketersediaan`) VALUES
('Double Room', 'DBL001', 150000, 'Tidak Tersedia'),
('Double Room', 'DBL002', 150000, 'Tersedia'),
('Deluxe Room', 'DLX001', 250000, 'Tersedia'),
('Single Room', 'SGL001', 100000, 'Tidak Tersedia'),
('Single Room', 'SGL002', 100000, 'Tersedia'),
('Suite Room', 'STE001', 300000, 'Tidak Tersedia');

-- --------------------------------------------------------

--
-- Struktur dari tabel `rooms`
--

CREATE TABLE `rooms` (
  `id` int(11) NOT NULL,
  `tipe_kamar` varchar(50) NOT NULL,
  `id_kamar` varchar(20) NOT NULL,
  `harga` double NOT NULL,
  `ketersediaan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `rooms`
--

INSERT INTO `rooms` (`id`, `tipe_kamar`, `id_kamar`, `harga`, `ketersediaan`) VALUES
(1, 'Single Room', 'SR001', 100000, 'Available'),
(2, 'Double Room', 'DR001', 150000, 'Available'),
(3, 'Suite Room', 'SU001', 300000, 'Available'),
(4, 'Deluxe Room', 'DL001', 250000, 'Available');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`username`, `password`, `role`) VALUES
('customer', '123456', 'customer'),
('pegawai', '123456', 'pegawai');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nik` (`nik`);

--
-- Indeks untuk tabel `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`);

--
-- Indeks untuk tabel `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tipe_kamar` (`tipe_kamar`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
