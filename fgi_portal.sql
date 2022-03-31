-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2022 at 01:08 PM
-- Server version: 5.5.39
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fgi_portal`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_attendance`
--

CREATE TABLE `data_attendance` (
  `id` int(4) NOT NULL,
  `username` varchar(75) NOT NULL,
  `class_registered` varchar(75) NOT NULL,
  `status` varchar(75) NOT NULL,
  `signature` varchar(75) NOT NULL DEFAULT 'not available',
  `date_created` datetime NOT NULL,
  `date_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_attendance`
--

INSERT INTO `data_attendance` (`id`, `username`, `class_registered`, `status`, `signature`, `date_created`, `date_modified`) VALUES
(5, 'udin', 'javascript web', 'hadir', 'not available', '2022-03-18 00:00:00', '2022-03-27 08:20:14'),
(6, 'udin', 'javascript web', 'hadir', '1648369214_absensi_20220327152009.jpg', '2022-03-27 15:20:14', '2022-03-27 08:20:14'),
(7, 'udin', 'javascript web', 'hadir', 'not available', '2022-03-18 00:00:00', '2022-03-28 04:08:50'),
(8, 'udin', 'javascript web', 'hadir', '1648440530_absensi_20220328110844.jpg', '2022-03-28 11:08:50', '2022-03-28 04:08:50'),
(9, 'udin', 'javascript web', 'hadir', 'not available', '2022-03-31 00:00:00', '2022-03-28 04:17:00'),
(10, 'udin', 'javascript web', 'hadir', '1648441020_absensi_20220328111654.jpg', '2022-03-28 11:17:00', '2022-03-28 04:17:00'),
(11, 'udin', 'javascript web', 'hadir', 'not available', '2022-03-25 00:00:00', '2022-03-28 04:19:24'),
(12, 'udin', 'javascript web', 'hadir', '1648441164_absensi_20220328111920.jpg', '2022-03-28 11:19:24', '2022-03-28 04:19:24'),
(13, 'udin', 'javascript web', 'hadir', 'not available', '2022-03-31 00:00:00', '2022-03-28 04:21:42'),
(14, 'udin', 'javascript web', 'hadir', '1648441302_absensi_20220328112138.jpg', '2022-03-28 11:21:42', '2022-03-28 04:21:42'),
(15, 'udin', 'javascript web', 'hadir', 'not available', '2022-03-25 00:00:00', '2022-03-28 04:26:35'),
(16, 'udin', 'javascript web', 'hadir', '1648441595_absensi_20220328112631.jpg', '2022-03-28 11:26:35', '2022-03-28 04:26:35');

-- --------------------------------------------------------

--
-- Table structure for table `data_bill`
--

CREATE TABLE `data_bill` (
  `id` int(4) NOT NULL,
  `amount` double NOT NULL,
  `description` varchar(100) NOT NULL,
  `username` varchar(75) NOT NULL,
  `status` varchar(75) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_bill`
--

INSERT INTO `data_bill` (`id`, `amount`, `description`, `username`, `status`, `date_created`) VALUES
(2, 750000, 'bayaran nov 2020', 'udin', 'paid', '2022-03-12 06:36:20'),
(3, 500000, 'bayaran kelas java', 'udin', 'pending', '2022-03-12 06:36:23');

-- --------------------------------------------------------

--
-- Table structure for table `data_certificate_student`
--

CREATE TABLE `data_certificate_student` (
  `id` int(4) NOT NULL,
  `student_username` varchar(75) NOT NULL,
  `exam_category_id` int(4) NOT NULL,
  `exam_category_title` varchar(75) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `filename` varchar(200) NOT NULL,
  `url` varchar(200) NOT NULL,
  `exam_date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_certificate_student`
--

INSERT INTO `data_certificate_student` (`id`, `student_username`, `exam_category_id`, `exam_category_title`, `status`, `filename`, `url`, `exam_date_created`) VALUES
(21, 'udin', 10, 'sampe category', 1, '1647485505_fotoAepReady.pdf', 'https://tinyurl.com/y6wukvup', '2022-03-11');

-- --------------------------------------------------------

--
-- Table structure for table `data_class_room`
--

CREATE TABLE `data_class_room` (
  `id` int(4) NOT NULL,
  `instructor_id` int(4) NOT NULL,
  `name` varchar(75) NOT NULL,
  `for_exam` tinyint(1) NOT NULL,
  `description` varchar(75) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_class_room`
--

INSERT INTO `data_class_room` (`id`, `instructor_id`, `name`, `for_exam`, `description`, `date_created`) VALUES
(4, 37, 'javascript web', 0, 'just a simple desc', '2022-03-12 06:23:25'),
(7, 37, 'coba class udin', 0, 'kelas udin baru neeee.... luar biasaa!', '2021-09-23 10:03:13'),
(8, 37, 'sss', 0, 'bbbb desssc', '2022-03-12 06:23:28');

-- --------------------------------------------------------

--
-- Table structure for table `data_document`
--

CREATE TABLE `data_document` (
  `id` int(4) NOT NULL,
  `title` varchar(75) NOT NULL,
  `description` varchar(200) NOT NULL,
  `filename` varchar(75) DEFAULT NULL,
  `username` varchar(75) NOT NULL,
  `url` varchar(200) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_document`
--

INSERT INTO `data_document` (`id`, `title`, `description`, `filename`, `username`, `url`, `date_created`) VALUES
(9, 'Sample 1', 'contoh awal 1 word', '1647484283_ringkasan_latihan_ms_word_01.pdf', 'udin', 'https://tinyurl.com/ycm4fphf', '2022-03-17 02:31:24'),
(10, 'contoh lagi beda 2', 'contoh beda ke 2 untuk ppoint', '1647484314_materi_power_point_sesi01.pdf', 'udin', 'https://tinyurl.com/yd4dfavo', '2022-03-17 02:31:55');

-- --------------------------------------------------------

--
-- Table structure for table `data_exam_category`
--

CREATE TABLE `data_exam_category` (
  `id` int(4) NOT NULL,
  `title` varchar(75) NOT NULL,
  `code` varchar(25) NOT NULL,
  `score_base` int(4) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_exam_category`
--

INSERT INTO `data_exam_category` (`id`, `title`, `code`, `score_base`, `date_created`) VALUES
(10, 'sampe category', 'JV11', 100, '2021-08-28 07:59:53'),
(11, 'category lain', 'CLain', 100, '2021-08-28 09:06:17');

-- --------------------------------------------------------

--
-- Table structure for table `data_exam_category_schedule`
--

CREATE TABLE `data_exam_category_schedule` (
  `id` int(4) NOT NULL,
  `schedule_id` int(4) NOT NULL,
  `exam_category_id` int(4) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_exam_category_schedule`
--

INSERT INTO `data_exam_category_schedule` (`id`, `schedule_id`, `exam_category_id`, `date_created`) VALUES
(1, 10, 10, '2021-09-28 14:42:00');

-- --------------------------------------------------------

--
-- Table structure for table `data_exam_qa`
--

CREATE TABLE `data_exam_qa` (
  `id` int(4) NOT NULL,
  `question` varchar(200) NOT NULL,
  `jenis` tinyint(1) NOT NULL,
  `option_a` varchar(125) DEFAULT NULL,
  `option_b` varchar(125) DEFAULT NULL,
  `option_c` varchar(125) DEFAULT NULL,
  `option_d` varchar(125) DEFAULT NULL,
  `preview` varchar(75) DEFAULT 'exam-prev-default.png',
  `answer` varchar(200) DEFAULT NULL,
  `score_point` int(4) NOT NULL,
  `exam_category_id` int(4) NOT NULL,
  `exam_sub_category_id` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_exam_qa`
--

INSERT INTO `data_exam_qa` (`id`, `question`, `jenis`, `option_a`, `option_b`, `option_c`, `option_d`, `preview`, `answer`, `score_point`, `exam_category_id`, `exam_sub_category_id`) VALUES
(7, 'nah ini soalnya', 2, NULL, NULL, NULL, NULL, 'exam-prev-default.png', 'coba lihaaaattttt', 25, 10, 6),
(8, 'essay lagi dengan image', 2, NULL, NULL, NULL, NULL, '1630382172_silverwood.jpg', 'tessttt essa', 30, 10, 6),
(10, 'pg dengan jadi tanpa image', 3, 'sekali', 'sesekali lagi', NULL, NULL, 'exam-prev-default.png', 'A', 35, 10, 6),
(11, 'ini ialah soalan praktek Typing, silahkan klik menu Typing disamping', 4, NULL, NULL, NULL, NULL, 'exam-prev-default.png', NULL, 50, 10, 1),
(12, 'praktek word', 5, NULL, NULL, NULL, NULL, 'soal-word.pdf', NULL, 75, 10, 1),
(13, 'praktek ms excel', 6, NULL, NULL, NULL, NULL, 'soal-excel.pdf', NULL, 75, 10, 1),
(14, 'soal ppoint', 7, NULL, NULL, NULL, NULL, 'soal-ppoint.pdf', NULL, 75, 10, 2),
(15, 'soal pdf-pdfan', 8, NULL, NULL, NULL, NULL, 'soal-pdf.pdf', NULL, 75, 10, 2),
(16, 'soal lainlain praktek juga', 9, NULL, NULL, NULL, NULL, 'soal-lain.pdf', NULL, 75, 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `data_exam_student`
--

CREATE TABLE `data_exam_student` (
  `id` int(4) NOT NULL,
  `student_username` varchar(75) NOT NULL,
  `exam_qa_id` int(4) NOT NULL,
  `answer` varchar(200) DEFAULT NULL,
  `score_earned` int(4) NOT NULL,
  `status` varchar(25) DEFAULT NULL,
  `fileupload` varchar(200) DEFAULT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `data_exam_student_scoring`
--

CREATE TABLE `data_exam_student_scoring` (
  `id` int(4) NOT NULL,
  `title` varchar(75) NOT NULL,
  `exam_sub_category_id` int(4) NOT NULL,
  `exam_category_id` int(4) NOT NULL,
  `scores` int(4) NOT NULL,
  `student_username` varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `data_exam_sub_category`
--

CREATE TABLE `data_exam_sub_category` (
  `id` int(4) NOT NULL,
  `title` varchar(75) NOT NULL,
  `exam_category_id` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_exam_sub_category`
--

INSERT INTO `data_exam_sub_category` (`id`, `title`, `exam_category_id`) VALUES
(6, 'sub 1', 10),
(7, 'sub2', 10);

-- --------------------------------------------------------

--
-- Table structure for table `data_history`
--

CREATE TABLE `data_history` (
  `id` int(4) NOT NULL,
  `username` varchar(75) NOT NULL,
  `description` varchar(200) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_history`
--

INSERT INTO `data_history` (`id`, `username`, `description`, `date_created`) VALUES
(1, 'asd', 'deleting product', '2020-10-21 14:13:53'),
(2, 'asd', 'paying 500 to atm bank', '2020-10-21 14:13:53'),
(3, 'asd', 'deleting charis', '2020-10-21 14:14:21'),
(4, 'asd', 'moving up freespace', '2020-10-21 14:14:21'),
(5, 'asd', 'copying document', '2020-10-21 14:14:35'),
(6, 'asd', 'deleting document locally', '2020-10-21 14:14:35'),
(7, 'ccc', 'waiting', '2020-10-21 14:14:47'),
(12, 'dede', 'add new report bugs [dsdsd]', '2020-12-09 07:35:20'),
(13, 'dede', 'add new payment proof from [Cash]', '2020-12-09 07:35:58'),
(14, 'dede', 'updating user profile', '2020-12-09 07:36:21'),
(15, 'udin', 'reporting bugs [coba caob]', '2020-12-09 07:58:07'),
(16, 'udin', 'uploading payment proof [Transfer Bank]', '2020-12-09 07:59:40'),
(17, 'udin', 'changing schedule [ monday 12:14] to [Wednesday]', '2020-12-09 09:34:59'),
(18, 'udin', 'updating client settings', '2020-12-09 09:58:09'),
(19, 'udin', 'changing schedule [ monday 12:14] to [selasa]', '2020-12-09 10:01:14'),
(20, 'udin', 'reporting bugs [asd]', '2020-12-09 17:54:09'),
(21, 'udin', 'reporting bugs [cccc]', '2020-12-09 17:54:22'),
(22, 'udin', 'reporting bugs [asdasd]', '2020-12-09 17:54:42'),
(23, 'udin', 'logging in successfuly', '2020-12-10 09:00:44'),
(24, 'udin', 'logging in successfuly', '2020-12-10 09:02:11'),
(25, 'udin', 'logging in successfuly', '2020-12-10 09:03:40'),
(26, 'udin', 'logging in successfuly', '2020-12-10 09:04:29'),
(27, 'udin', 'opening youtube [https://www.youtube.com/watch?v=_B5QM1ZkPZk]', '2020-12-10 09:04:33'),
(28, 'udin', 'opening youtube [https://www.youtube.com/watch?v=_B5QM1ZkPZk]', '2020-12-10 09:04:46'),
(29, 'udin', 'opening document [bitplay.mp4]', '2020-12-10 09:05:47'),
(30, 'udin', 'logging in successfuly', '2020-12-10 09:10:17'),
(31, 'udin', 'opening youtube [https://www.youtube.com/watch?v=_B5QM1ZkPZk]', '2020-12-10 09:11:04'),
(32, 'udin', 'logging in successfuly', '2020-12-10 09:15:53'),
(33, 'udin', 'opening youtube [https://www.youtube.com/watch?v=_B5QM1ZkPZk]', '2020-12-10 09:16:28'),
(34, 'udin', 'opening youtube [https://www.youtube.com/watch?v=_B5QM1ZkPZk]', '2020-12-10 09:16:52'),
(35, 'udin', 'logging in successfuly', '2020-12-10 09:22:34'),
(36, 'udin', 'logging in successfuly', '2020-12-10 09:23:29'),
(37, 'udin', 'logging in successfuly', '2020-12-10 09:24:39'),
(38, 'udin', 'logging in successfuly', '2020-12-10 09:43:59'),
(39, 'udin', 'opening youtube [https://www.youtube.com/watch?v=_B5QM1ZkPZk]', '2020-12-10 09:44:39'),
(40, 'udin', 'opening document [pencil.png]', '2020-12-10 09:45:44'),
(41, 'udin', 'opening teamviewer', '2020-12-10 09:46:47'),
(42, 'udin', 'logging in successfuly', '2020-12-10 10:22:46'),
(43, 'udin', 'logging in successfuly', '2020-12-10 10:26:07'),
(44, 'udin', 'logging in successfuly', '2020-12-21 04:48:00'),
(45, 'udin', 'logging in successfuly', '2020-12-22 03:40:22'),
(46, 'udin', 'logging in successfuly', '2020-12-22 03:43:33'),
(47, 'udin', 'logging in successfuly', '2020-12-22 03:44:19'),
(48, 'udin', 'logging in successfuly', '2020-12-22 04:35:14'),
(49, 'udin', 'logging in successfuly', '2020-12-22 04:35:56'),
(50, 'udin', 'logging in successfuly', '2020-12-22 04:38:15'),
(51, 'udin', 'logging in successfuly', '2020-12-22 07:18:14'),
(52, 'admin', 'logging in successfully.', '2020-12-25 02:40:47'),
(53, 'admin', 'logging in successfully.', '2020-12-25 02:43:27'),
(54, 'admin', 'logging in successfully.', '2020-12-25 02:44:01'),
(55, 'admin', 'logging in successfully.', '2020-12-25 07:57:08'),
(56, 'admin', 'logging in successfully.', '2021-01-04 02:13:01'),
(57, 'udin', 'logging in successfully.', '2021-01-04 04:14:37'),
(58, 'udin', 'logging in successfully.', '2021-01-04 18:09:23'),
(59, 'udin', 'logging in successfully.', '2021-01-05 01:25:24'),
(60, 'udin', 'logging in successfully.', '2021-01-05 01:26:44'),
(61, 'udin', 'logging in successfully.', '2021-01-06 04:14:18'),
(62, 'udin', 'logging in successfully.', '2021-01-06 05:30:36'),
(63, 'udin', 'logging in successfully.', '2021-01-06 08:19:25'),
(64, 'udin', 'logging in successfully.', '2021-01-06 16:31:49'),
(65, 'udin', 'logging in successfully.', '2021-01-07 07:51:37'),
(66, 'udin', 'logging in successfully.', '2021-01-07 07:52:16'),
(67, 'udin', 'logging in successfully.', '2021-01-07 07:54:13'),
(68, 'udin', 'logging in successfully.', '2021-01-07 07:55:53'),
(69, 'udin', 'logging in successfully.', '2021-01-08 15:10:15'),
(70, 'udin', 'logging in successfully.', '2021-01-08 15:15:34'),
(71, 'udin', 'logging in successfully.', '2021-01-08 15:22:45'),
(72, 'udin', 'logging in successfully.', '2021-01-08 15:23:28'),
(73, 'udin', 'logging in successfully.', '2021-01-08 15:25:06'),
(74, 'udin', 'logging in successfully.', '2021-01-08 16:36:42'),
(75, 'udin', 'logging in successfully.', '2021-01-08 16:38:39'),
(76, 'udin', 'logging in successfully.', '2021-01-08 16:52:47'),
(77, 'udin', 'logging in successfully.', '2021-01-09 00:18:06'),
(78, 'udin', 'logging in successfully.', '2021-01-11 17:17:13'),
(79, 'udin', 'logging in successfully.', '2021-01-11 17:20:16'),
(80, 'udin', 'logging in successfully.', '2021-01-11 17:23:00'),
(81, 'udin', 'logging in successfully.', '2021-01-11 17:24:02'),
(82, 'udin', 'logging in successfully.', '2021-01-11 17:24:16'),
(83, 'udin', 'logging in successfully.', '2021-01-11 17:32:23'),
(84, 'udin', 'logging in successfully.', '2021-01-11 18:05:55'),
(85, 'udin', 'logging in successfully.', '2021-01-11 18:06:21'),
(86, 'udin', 'logging in successfully.', '2021-01-11 18:40:59'),
(87, 'udin', 'logging in successfully.', '2021-01-11 18:52:19'),
(88, 'udin', 'logging in successfully.', '2021-01-11 19:10:55'),
(89, 'udin', 'logging in successfully.', '2021-01-11 19:18:34'),
(90, 'udin', 'logging in successfully.', '2021-01-13 07:11:49'),
(91, 'udin', 'logging in successfully.', '2021-01-14 13:41:38'),
(92, 'udin', 'logging in successfully.', '2021-01-14 13:43:53'),
(93, 'udin', 'logging in successfully.', '2021-01-14 13:44:53'),
(94, 'udin', 'logging in successfully.', '2021-01-14 13:45:29'),
(95, 'udin', 'logging in successfully.', '2021-01-14 13:45:50'),
(96, 'udin', 'logging in successfully.', '2021-01-14 13:56:58'),
(97, 'udin', 'logging in successfully.', '2021-01-14 13:57:18'),
(98, 'udin', 'logging in successfully.', '2021-01-14 13:57:40'),
(99, 'udin', 'logging in successfully.', '2021-01-14 14:36:56'),
(100, 'udin', 'logging in successfully.', '2021-01-14 14:48:05'),
(101, 'udin', 'logging in successfully.', '2021-01-14 14:53:06'),
(102, 'udin', 'logging in successfully.', '2021-01-14 14:56:49'),
(103, 'udin', 'logging in successfully.', '2021-01-14 14:57:18'),
(104, 'udin', 'logging in successfully.', '2021-01-14 15:09:09'),
(105, 'udin', 'logging in successfully.', '2021-01-14 22:25:20'),
(106, 'udin', 'logging in successfully.', '2021-01-14 22:29:50'),
(107, 'udin', 'logging in successfully.', '2021-01-14 22:34:15'),
(108, 'udin', 'logging in successfully.', '2021-01-14 23:16:14'),
(109, 'udin', 'logging in successfully.', '2021-01-15 00:59:07'),
(110, 'udin', 'logging in successfully.', '2021-01-15 01:00:08'),
(111, 'udin', 'logging in successfully.', '2021-01-15 01:26:02'),
(112, 'udin', 'logging in successfully.', '2021-01-15 04:18:30'),
(113, 'udin', 'logging in successfully.', '2021-01-15 04:21:33'),
(114, 'udin', 'logging in successfully.', '2021-01-15 04:28:58'),
(115, 'udin', 'logging in successfully.', '2021-01-15 07:05:55'),
(116, 'udin', 'logging in successfully.', '2021-01-15 07:06:57'),
(117, 'udin', 'logging in successfully.', '2021-01-15 07:13:35'),
(118, 'udin', 'logging in successfully.', '2021-01-15 07:43:10'),
(119, 'udin', 'logging in successfully.', '2021-01-15 07:49:31'),
(120, 'udin', 'logging in successfully.', '2021-01-15 07:51:25'),
(121, 'udin', 'logging in successfully.', '2021-01-15 09:12:35'),
(122, 'udin', 'logging in successfully.', '2021-01-15 09:20:25'),
(123, 'udin', 'logging in successfully.', '2021-01-15 09:42:31'),
(124, 'udin', 'logging in successfully.', '2021-01-15 10:58:11'),
(125, 'udin', 'logging in successfully.', '2021-01-15 11:01:13'),
(126, 'udin', 'logging in successfully.', '2021-01-16 04:55:39'),
(127, 'udin', 'logging in successfully.', '2021-01-16 12:33:48'),
(128, 'udin', 'logging in successfully.', '2021-01-16 14:23:30'),
(129, 'udin', 'logging in successfully.', '2021-01-16 14:24:58'),
(130, 'udin', 'logging in successfully.', '2021-01-16 14:49:47'),
(131, 'udin', 'logging in successfully.', '2021-01-16 14:50:41'),
(132, 'udin', 'logging in successfully.', '2021-01-16 14:57:33'),
(133, 'udin', 'logging in successfully.', '2021-01-16 20:05:17'),
(134, 'udin', 'logging in successfully.', '2021-01-17 07:55:29'),
(135, 'udin', 'logging in successfully.', '2021-01-24 03:38:10'),
(136, 'udin', 'logging in successfully.', '2021-01-24 04:33:26'),
(137, 'udin', 'logging in successfully.', '2021-01-24 06:15:30'),
(138, 'udin', 'logging in successfully.', '2021-01-24 06:27:32'),
(139, 'udin', 'logging in successfully.', '2021-01-24 06:35:30'),
(140, 'udin', 'logging in successfully.', '2021-01-24 07:02:00'),
(141, 'udin', 'logging in successfully.', '2021-01-24 07:03:27'),
(142, 'udin', 'logging in successfully.', '2021-01-24 07:11:29'),
(143, 'udin', 'logging in successfully.', '2021-01-27 04:49:59'),
(144, 'udin', 'logging in successfully.', '2021-01-27 09:37:11'),
(145, 'udin', 'logging in successfully.', '2021-01-27 15:09:08'),
(146, 'udin', 'logging in successfully.', '2021-01-27 16:38:02'),
(147, 'udin', 'logging in successfully.', '2021-01-31 07:30:43'),
(148, 'udin', 'logging in successfully.', '2021-01-31 08:15:03'),
(149, 'udin', 'logging in successfully.', '2021-01-31 08:16:37'),
(150, 'udin', 'logging in successfully.', '2021-01-31 17:13:03'),
(151, 'udin', 'logging in successfully.', '2021-01-31 17:23:30'),
(152, 'udin', 'verifying client successfully.', '2021-01-31 20:56:48'),
(153, 'udin', 'logging in successfuly', '2021-01-31 20:56:51'),
(154, 'udin', 'verifying client successfully.', '2021-01-31 21:02:03'),
(155, 'udin', 'logging in successfuly', '2021-01-31 21:02:15'),
(156, 'udin', 'logging in successfully.', '2021-02-07 16:04:59'),
(157, 'udin', 'logging in successfully.', '2021-02-10 20:21:06'),
(158, 'udin', 'logging in successfully.', '2021-02-10 20:32:10'),
(159, 'udin', 'logging in successfully.', '2021-02-10 20:42:49'),
(160, 'udin', 'logging in successfully.', '2021-02-10 20:58:54'),
(161, 'udin', 'logging in successfully.', '2021-02-10 21:05:52'),
(162, 'udin', 'logging in successfully.', '2021-02-10 21:06:19'),
(163, 'udin', 'logging in successfully.', '2021-02-10 21:09:22'),
(164, 'dede', 'logging in successfuly', '2021-09-04 06:31:23'),
(165, 'dede', 'opening embedded whatsapp', '2021-09-04 06:31:49'),
(166, 'dede', 'logging in successfuly', '2021-09-04 06:34:35'),
(167, 'dede', 'logging in successfuly', '2021-09-04 06:47:16'),
(168, 'dede', 'opening embedded google meet', '2021-09-04 06:47:42'),
(169, 'dede', 'opening embedded google meet', '2021-09-04 06:52:06'),
(170, 'udin', 'logging in successfuly', '2021-09-09 07:21:07'),
(171, 'udin', 'logging in successfuly', '2021-09-09 07:21:56'),
(172, 'udin', 'logging in successfuly', '2021-09-28 04:43:44'),
(173, 'udin', 'logging in successfuly', '2021-09-28 04:46:06'),
(174, 'udin', 'logging in successfuly', '2021-09-28 04:47:12'),
(175, 'udin', 'logging in successfuly', '2021-09-28 04:49:37'),
(176, 'udin', 'logging in successfuly', '2021-09-28 05:36:51'),
(177, 'udin', 'logging in successfuly', '2021-09-28 05:38:42'),
(178, 'udin', 'logging in successfuly', '2021-09-28 06:37:32'),
(179, 'udin', 'logging in successfuly', '2021-09-28 13:50:08'),
(180, 'udin', 'logging in successfuly', '2021-09-28 14:28:50'),
(181, 'udin', 'logging in successfuly', '2021-09-28 14:29:53'),
(182, 'udin', 'logging in successfuly', '2021-09-28 14:31:30'),
(183, 'udin', 'logging in successfuly', '2021-09-28 14:32:42'),
(184, 'udin', 'logging in successfuly', '2021-09-28 14:33:42'),
(185, 'udin', 'logging in successfuly', '2021-09-28 14:36:27'),
(186, 'udin', 'logging in successfuly', '2021-09-28 14:38:33'),
(187, 'udin', 'logging in successfuly', '2021-09-28 14:42:09'),
(188, 'udin', 'logging in successfuly', '2021-09-28 14:44:40'),
(189, 'udin', 'logging in successfuly', '2021-09-28 14:47:02'),
(190, 'udin', 'logging in successfuly', '2021-09-28 14:47:03'),
(191, 'udin', 'logging in successfuly', '2021-09-28 14:47:03'),
(192, 'udin', 'logging in successfuly', '2021-09-28 14:47:28'),
(193, 'udin', 'logging in successfuly', '2021-09-28 14:51:53'),
(194, 'udin', 'logging in successfuly', '2021-09-28 14:56:49'),
(195, 'udin', 'logging in successfuly', '2021-09-28 15:17:37'),
(196, 'udin', 'logging in successfuly', '2021-09-28 15:20:27'),
(197, 'udin', 'logging in successfuly', '2021-09-28 15:23:10'),
(198, 'udin', 'logging in successfuly', '2021-09-28 15:23:10'),
(199, 'udin', 'logging in successfuly', '2021-09-28 15:26:37'),
(200, 'udin', 'logging in successfuly', '2021-09-28 15:27:42'),
(201, 'udin', 'logging in successfuly', '2021-09-28 15:33:36'),
(202, 'udin', 'logging in successfuly', '2021-09-28 15:38:15'),
(203, 'udin', 'checking attendance statistics', '2021-09-28 15:47:44'),
(204, 'udin', 'logging in successfuly', '2021-09-28 16:07:22'),
(205, 'udin', 'logging in successfuly', '2021-09-28 16:08:47'),
(206, 'udin', 'logging in successfuly', '2021-09-28 16:11:00'),
(207, 'udin', 'logging in successfuly', '2021-09-28 16:12:43'),
(208, 'udin', 'logging in successfuly', '2021-09-28 16:46:10'),
(209, 'udin', 'logging in successfuly', '2021-09-28 16:47:58'),
(210, 'udin', 'logging in successfuly', '2021-09-28 16:52:38'),
(211, 'udin', 'logging in successfuly', '2021-09-28 16:53:12'),
(212, 'udin', 'logging in successfuly', '2021-09-28 16:53:35'),
(213, 'udin', 'logging in successfuly', '2021-09-28 16:54:01'),
(214, 'udin', 'logging in successfuly', '2021-09-28 16:55:03'),
(215, 'udin', 'logging in successfuly', '2021-09-28 16:58:52'),
(216, 'udin', 'logging in successfuly', '2021-09-28 17:00:26'),
(217, 'udin', 'logging in successfuly', '2021-09-28 17:01:55'),
(218, 'udin', 'logging in successfuly', '2021-09-28 17:02:28'),
(219, 'udin', 'logging in successfuly', '2021-09-28 17:35:59'),
(220, 'udin', 'opening embedded browser', '2021-09-28 17:36:16'),
(221, 'udin', 'opening embedded browser', '2021-09-28 17:38:10'),
(222, 'udin', 'opening embedded browser', '2021-09-28 17:38:39'),
(223, 'udin', 'opening embedded browser', '2021-09-28 17:39:39'),
(224, 'udin', 'opening embedded browser', '2021-09-28 17:41:39'),
(225, 'udin', 'logging in successfuly', '2021-09-28 18:10:59'),
(226, 'udin', 'opening embedded browser', '2021-09-28 18:11:10'),
(227, 'udin', 'logging in successfuly', '2021-09-28 18:16:02'),
(228, 'udin', 'logging in successfuly', '2021-09-28 18:16:46'),
(229, 'udin', 'opening embedded browser', '2021-09-28 18:16:51'),
(230, 'udin', 'logging in successfuly', '2021-09-28 18:22:55'),
(231, 'udin', 'opening embedded browser', '2021-09-28 18:23:04'),
(232, 'udin', 'logging in successfuly', '2021-09-28 18:25:01'),
(233, 'udin', 'logging in successfuly', '2021-09-28 18:26:02'),
(234, 'udin', 'logging in successfuly', '2021-09-28 18:32:10'),
(235, 'udin', 'logging in successfuly', '2021-09-28 18:35:23'),
(236, 'udin', 'opening embedded typing', '2021-09-28 18:35:55'),
(237, 'udin', 'logging in successfuly', '2021-09-28 18:36:40'),
(238, 'udin', 'opening embedded typing', '2021-09-28 18:37:23'),
(239, 'udin', 'logging in successfuly', '2021-09-28 18:40:56'),
(240, 'udin', 'opening embedded typing', '2021-09-28 18:41:45'),
(241, 'udin', 'logging in successfuly', '2021-09-28 18:49:36'),
(242, 'udin', 'logging in successfuly', '2021-09-29 03:07:42'),
(243, 'udin', 'logging in successfuly', '2021-09-29 03:17:09'),
(244, 'udin', 'logging in successfuly', '2021-09-29 03:19:12'),
(245, 'udin', 'logging in successfuly', '2021-09-29 03:24:01'),
(246, 'udin', 'opening embedded typing', '2021-09-29 03:24:36'),
(247, 'udin', 'logging in successfuly', '2021-09-29 03:27:39'),
(248, 'udin', 'logging in successfuly', '2021-09-29 03:29:49'),
(249, 'udin', 'logging in successfuly', '2021-09-29 03:34:56'),
(250, 'udin', 'logging in successfuly', '2021-09-29 03:37:27'),
(251, 'udin', 'opening embedded typing', '2021-09-29 03:37:45'),
(252, 'udin', 'logging in successfuly', '2021-09-29 03:44:13'),
(253, 'udin', 'logging in successfuly', '2021-09-29 03:45:35'),
(254, 'udin', 'logging in successfuly', '2021-09-29 03:49:08'),
(255, 'udin', 'logging in successfuly', '2021-09-29 03:51:08'),
(256, 'udin', 'opening embedded typing', '2021-09-29 03:51:40'),
(257, 'udin', 'logging in successfuly', '2021-09-29 03:58:31'),
(258, 'udin', 'logging in successfuly', '2021-09-29 04:00:57'),
(259, 'udin', 'logging in successfuly', '2021-09-29 04:02:50'),
(260, 'udin', 'logging in successfuly', '2021-09-29 04:09:31'),
(261, 'udin', 'logging in successfuly', '2021-09-29 04:20:00'),
(262, 'udin', 'logging in successfuly', '2021-09-29 04:22:23'),
(263, 'udin', 'logging in successfuly', '2021-09-29 04:23:38'),
(264, 'udin', 'logging in successfuly', '2021-09-29 04:25:37'),
(265, 'udin', 'logging in successfuly', '2021-09-29 04:27:45'),
(266, 'udin', 'opening embedded typing', '2021-09-29 04:27:53'),
(267, 'udin', 'logging in successfuly', '2021-09-29 04:32:52'),
(268, 'udin', 'opening embedded typing', '2021-09-29 04:33:32'),
(269, 'udin', 'logging in successfuly', '2021-09-29 04:36:50'),
(270, 'udin', 'opening embedded typing', '2021-09-29 04:37:04'),
(271, 'udin', 'logging in successfuly', '2021-09-29 07:14:46'),
(272, 'udin', 'opening embedded typing', '2021-09-29 07:15:40'),
(273, 'udin', 'logging in successfuly', '2021-09-29 07:19:29'),
(274, 'udin', 'logging in successfuly', '2021-09-29 07:22:33'),
(275, 'udin', 'logging in successfuly', '2021-09-29 08:46:25'),
(276, 'udin', 'opening embedded typing', '2021-09-29 08:46:38'),
(277, 'udin', 'logging in successfuly', '2021-09-29 08:50:29'),
(278, 'udin', 'opening embedded typing', '2021-09-29 08:50:56'),
(279, 'udin', 'logging in successfuly', '2021-09-29 08:57:06'),
(280, 'udin', 'opening embedded typing', '2021-09-29 08:57:39'),
(281, 'udin', 'logging in successfuly', '2021-09-29 09:00:55'),
(282, 'udin', 'opening embedded typing', '2021-09-29 09:01:07'),
(283, 'udin', 'opening embedded whatsapp', '2021-09-29 09:24:58'),
(284, 'udin', 'logging in successfuly', '2021-09-29 15:27:36'),
(285, 'udin', 'logging in successfuly', '2021-10-01 13:43:21'),
(286, 'udin', 'logging in successfuly', '2021-10-01 13:46:10'),
(287, 'udin', 'logging in successfuly', '2021-10-01 13:50:06'),
(288, 'udin', 'logging in successfuly', '2021-10-01 13:53:18'),
(289, 'udin', 'logging in successfuly', '2021-10-01 14:00:36'),
(290, 'udin', 'logging in successfuly', '2021-10-01 14:14:45'),
(291, 'udin', 'logging in successfuly', '2021-10-01 14:17:02'),
(292, 'udin', 'opening embedded typing', '2021-10-01 14:17:11'),
(293, 'udin', 'logging in successfuly', '2021-10-02 09:32:10'),
(294, 'udin', 'logging in successfuly', '2021-10-02 09:36:49'),
(295, 'udin', 'opening embedded typing', '2021-10-02 09:37:11'),
(296, 'udin', 'logging in successfuly', '2021-10-02 09:39:05'),
(297, 'udin', 'logging in successfuly', '2021-10-02 09:40:54'),
(298, 'udin', 'opening embedded typing', '2021-10-02 09:41:28'),
(299, 'udin', 'logging in successfuly', '2021-10-02 09:45:00'),
(300, 'udin', 'opening embedded typing', '2021-10-02 09:45:17'),
(301, 'udin', 'logging in successfuly', '2021-10-02 09:47:19'),
(302, 'udin', 'opening embedded typing', '2021-10-02 09:47:48'),
(303, 'udin', 'logging in successfuly', '2021-10-02 09:54:30'),
(304, 'udin', 'opening embedded typing', '2021-10-02 09:54:53'),
(305, 'udin', 'logging in successfuly', '2021-10-02 10:08:59'),
(306, 'udin', 'opening embedded typing', '2021-10-02 10:09:36'),
(307, 'udin', 'logging in successfuly', '2021-10-02 10:11:24'),
(308, 'udin', 'opening embedded typing', '2021-10-02 10:11:52'),
(309, 'udin', 'logging in successfuly', '2021-10-02 10:13:43'),
(310, 'udin', 'opening embedded typing', '2021-10-02 10:13:50'),
(311, 'udin', 'logging in successfuly', '2021-10-03 10:21:13'),
(312, 'udin', 'opening embedded typing', '2021-10-03 10:21:35'),
(313, 'udin', 'logging in successfuly', '2021-10-03 10:30:26'),
(314, 'udin', 'opening embedded typing', '2021-10-03 10:30:47'),
(315, 'udin', 'logging in successfuly', '2021-10-03 10:40:44'),
(316, 'udin', 'opening embedded typing', '2021-10-03 10:41:19'),
(317, 'udin', 'logging in successfuly', '2021-10-03 10:48:34'),
(318, 'udin', 'opening embedded typing', '2021-10-03 10:49:13'),
(319, 'udin', 'logging in successfuly', '2021-10-03 13:19:45'),
(320, 'udin', 'logging in successfuly', '2021-10-03 13:20:29'),
(321, 'udin', 'opening embedded typing', '2021-10-03 13:21:02'),
(322, 'udin', 'logging in successfuly', '2021-10-03 13:47:02'),
(323, 'udin', 'opening embedded typing', '2021-10-03 13:47:09'),
(324, 'udin', 'logging in successfuly', '2021-10-03 15:10:29'),
(325, 'udin', 'opening embedded typing', '2021-10-03 15:11:17'),
(326, 'udin', 'logging in successfuly', '2021-10-03 15:37:24'),
(327, 'udin', 'opening embedded typing', '2021-10-03 15:37:43'),
(328, 'udin', 'logging in successfuly', '2021-10-03 15:41:14'),
(329, 'udin', 'opening embedded typing', '2021-10-03 15:41:21'),
(330, 'udin', 'logging in successfuly', '2021-10-03 15:51:51'),
(331, 'udin', 'opening embedded typing', '2021-10-03 15:52:09'),
(332, 'udin', 'logging in successfuly', '2021-10-03 16:00:41'),
(333, 'udin', 'opening embedded typing', '2021-10-03 16:01:01'),
(334, 'udin', 'logging in successfuly', '2021-10-03 16:04:34'),
(335, 'udin', 'opening embedded typing', '2021-10-03 16:04:52'),
(336, 'udin', 'logging in successfuly', '2021-10-03 16:08:30'),
(337, 'udin', 'opening embedded typing', '2021-10-03 16:08:48'),
(338, 'udin', 'logging in successfuly', '2021-10-03 16:28:17'),
(339, 'udin', 'opening embedded typing', '2021-10-03 16:28:57'),
(340, 'udin', 'logging in successfuly', '2021-10-03 17:22:22'),
(341, 'udin', 'opening embedded typing', '2021-10-03 17:22:32'),
(342, 'udin', 'logging in successfuly', '2021-10-03 17:25:14'),
(343, 'udin', 'opening embedded typing', '2021-10-03 17:25:33'),
(344, 'udin', 'logging in successfuly', '2021-10-03 17:35:58'),
(345, 'udin', 'opening embedded typing', '2021-10-03 17:36:12'),
(346, 'udin', 'logging in successfuly', '2021-10-03 17:44:37'),
(347, 'udin', 'opening embedded typing', '2021-10-03 17:45:13'),
(348, 'udin', 'logging in successfuly', '2021-10-03 17:46:37'),
(349, 'udin', 'opening embedded typing', '2021-10-03 17:46:45'),
(350, 'udin', 'logging in successfuly', '2021-10-03 18:02:30'),
(351, 'udin', 'opening embedded typing', '2021-10-03 18:03:28'),
(352, 'udin', 'logging in successfuly', '2021-10-03 18:13:46'),
(353, 'udin', 'opening embedded typing', '2021-10-03 18:14:11'),
(354, 'udin', 'logging in successfuly', '2021-10-04 09:45:11'),
(355, 'udin', 'opening embedded typing', '2021-10-04 09:46:01'),
(356, 'udin', 'logging in successfuly', '2021-10-04 10:06:02'),
(357, 'udin', 'opening embedded typing', '2021-10-04 10:06:29'),
(358, 'udin', 'logging in successfuly', '2021-10-04 10:08:00'),
(359, 'udin', 'logging in successfuly', '2021-10-04 10:08:41'),
(360, 'udin', 'opening embedded typing', '2021-10-04 10:09:08'),
(361, 'udin', 'logging in successfuly', '2021-10-04 10:33:46'),
(362, 'udin', 'opening embedded typing', '2021-10-04 10:35:24'),
(363, 'udin', 'logging in successfuly', '2021-10-04 15:44:52'),
(364, 'udin', 'logging in successfuly', '2021-10-04 15:45:33'),
(365, 'udin', 'opening embedded typing', '2021-10-04 15:46:05'),
(366, 'udin', 'logging in successfuly', '2021-10-04 15:48:12'),
(367, 'udin', 'opening embedded typing', '2021-10-04 15:48:25'),
(368, 'udin', 'logging in successfuly', '2021-10-04 15:51:57'),
(369, 'udin', 'opening embedded typing', '2021-10-04 15:52:31'),
(370, 'udin', 'logging in successfuly', '2021-10-04 15:58:29'),
(371, 'udin', 'opening embedded typing', '2021-10-04 15:58:38'),
(372, 'udin', 'logging in successfuly', '2021-10-04 16:00:14'),
(373, 'udin', 'opening embedded typing', '2021-10-04 16:00:43'),
(374, 'udin', 'logging in successfuly', '2021-10-04 16:02:46'),
(375, 'udin', 'opening embedded typing', '2021-10-04 16:03:10'),
(376, 'udin', 'logging in successfuly', '2021-10-04 16:04:53'),
(377, 'udin', 'opening embedded typing', '2021-10-04 16:05:14'),
(378, 'udin', 'logging in successfuly', '2021-10-04 16:14:29'),
(379, 'udin', 'opening embedded typing', '2021-10-04 16:14:37'),
(380, 'dede', 'logging in successfuly', '2021-10-19 09:36:24'),
(381, 'dede', 'opening embedded whatsapp', '2021-10-19 09:37:10'),
(382, 'udin', 'logging in successfuly', '2021-10-19 09:51:45'),
(383, 'udin', 'logging in successfuly', '2021-10-19 09:53:15'),
(384, 'udin', 'logging in successfuly', '2021-10-19 09:53:16'),
(385, 'udin', 'logging in successfuly', '2021-10-19 10:03:38'),
(386, 'udin', 'logging in successfuly', '2021-10-19 10:03:39'),
(387, 'udin', 'logging in successfuly', '2021-10-19 10:11:18'),
(388, 'udin', 'logging in successfuly', '2021-10-19 10:14:50'),
(389, 'udin', 'logging in successfuly', '2021-10-19 10:16:27'),
(390, 'udin', 'logging in successfuly', '2021-10-19 10:47:19'),
(391, 'udin', 'opening embedded whatsapp', '2021-10-19 10:47:25'),
(392, 'udin', 'logging in successfuly', '2021-10-19 10:51:55'),
(393, 'udin', 'logging in successfuly', '2021-10-19 10:56:39'),
(394, 'udin', 'logging in successfuly', '2021-10-19 10:58:21'),
(395, 'udin', 'logging in successfully', '2022-03-09 07:41:26'),
(396, 'udin', 'logging in successfully', '2022-03-09 07:44:01'),
(397, 'udin', 'logging in successfully', '2022-03-09 07:52:01'),
(398, 'udin', 'logging in successfully', '2022-03-09 07:53:52'),
(399, 'udin', 'logging in successfully', '2022-03-09 13:58:55'),
(400, 'udin', 'logging in successfully', '2022-03-09 14:04:58'),
(401, 'udin', 'logging in successfully', '2022-03-09 14:05:21'),
(402, 'udin', 'logging in successfully', '2022-03-09 14:10:55'),
(403, 'udin', 'logging in successfully', '2022-03-09 14:11:27'),
(404, 'udin', 'logging in successfully', '2022-03-09 14:11:54'),
(405, 'udin', 'logging in successfully', '2022-03-09 14:15:12'),
(406, 'udin', 'logging in successfully', '2022-03-09 14:15:21'),
(407, 'udin', 'logging in successfully', '2022-03-09 14:23:29'),
(408, 'udin', 'logging in successfully', '2022-03-09 14:38:12'),
(409, 'udin', 'activating notification in mobile update settings!', '2022-03-09 14:38:19'),
(410, 'udin', 'deactivating autologout in settings!', '2022-03-09 14:38:32'),
(411, 'udin', 'logging in successfully', '2022-03-09 23:56:02'),
(412, 'udin', 'logging in successfully', '2022-03-10 00:06:34'),
(413, 'udin', 'logging in successfully', '2022-03-10 00:20:37'),
(414, 'udin', 'logging in successfully', '2022-03-10 00:39:00'),
(415, 'udin', 'logging in successfully', '2022-03-10 01:04:32'),
(416, 'udin', 'logging in successfully', '2022-03-10 01:14:54'),
(417, 'udin', 'logging in successfully', '2022-03-10 01:39:21'),
(418, 'udin', 'logging in successfully', '2022-03-10 02:07:25'),
(419, 'udin', 'logging in successfully', '2022-03-10 02:24:55'),
(420, 'udin', 'logging in successfully', '2022-03-10 02:32:43'),
(421, 'udin', 'logging in successfully', '2022-03-10 08:35:30'),
(422, 'udin', 'logging in successfully', '2022-03-10 10:02:53'),
(423, 'udin', 'logging out from mobile phone successfully', '2022-03-10 10:02:57'),
(424, 'udin', 'logging in successfully', '2022-03-10 10:03:28'),
(425, 'udin', 'logging out from mobile phone successfully', '2022-03-10 10:36:57'),
(426, 'udin', 'logging in to mobile phone successfully', '2022-03-10 10:38:54'),
(427, 'udin', 'logging out from mobile phone successfully', '2022-03-10 10:41:11'),
(428, 'udin', 'logging in to mobile phone successfully', '2022-03-10 10:42:30'),
(429, 'udin', 'attendance updated with idzin status!', '2022-03-10 10:42:57'),
(430, 'udin', 'logging out from mobile phone successfully', '2022-03-10 10:46:27'),
(431, 'udin', 'logging in to mobile phone successfully', '2022-03-10 10:47:21'),
(432, 'udin', 'attendance updated with idzin status!', '2022-03-10 10:47:39'),
(433, 'udin', 'attendance updated with sakit status!', '2022-03-10 10:48:15'),
(434, 'udin', 'logging in to mobile phone successfully', '2022-03-10 10:51:38'),
(435, 'udin', 'logging out from mobile phone successfully', '2022-03-10 10:53:48'),
(436, 'udin', 'logging in to mobile phone successfully', '2022-03-10 10:56:04'),
(437, 'udin', 'logging out from mobile phone successfully', '2022-03-10 11:10:36'),
(438, 'udin', 'logging in to mobile phone successfully', '2022-03-10 12:01:03'),
(439, 'udin', 'logging out from mobile phone successfully', '2022-03-10 12:01:48'),
(440, 'udin', 'logging in to mobile phone successfully', '2022-03-10 12:01:50'),
(441, 'udin', 'logging out from mobile phone successfully', '2022-03-10 12:01:57'),
(442, 'udin', 'logging in to mobile phone successfully', '2022-03-10 12:02:03'),
(443, 'udin', 'logging in to mobile phone successfully', '2022-03-10 12:06:57'),
(444, 'udin', 'logging in to mobile phone successfully', '2022-03-10 13:19:48'),
(445, 'udin', 'logging in to mobile phone successfully', '2022-03-10 13:52:54'),
(446, 'udin', 'logging out from mobile phone successfully', '2022-03-10 13:53:21'),
(447, 'udin', 'logging in to mobile phone successfully', '2022-03-11 08:55:36'),
(448, 'udin', 'logging in to mobile phone successfully', '2022-03-11 10:57:50'),
(449, 'udin', 'logging in to mobile phone successfully', '2022-03-11 12:51:04'),
(450, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-11 12:51:08'),
(451, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-11 12:57:59'),
(452, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-11 12:59:22'),
(453, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-11 13:04:38'),
(454, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-11 13:06:12'),
(455, 'udin', 'logging in successfuly', '2022-03-12 03:48:25'),
(456, 'udin', 'logging in successfuly', '2022-03-12 03:54:25'),
(457, 'udin', 'logging in successfuly', '2022-03-12 04:37:31'),
(458, 'udin', 'logging in successfuly', '2022-03-12 04:51:14'),
(459, 'udin', 'logging in successfuly', '2022-03-12 05:42:22'),
(460, 'udin', 'logging in successfuly', '2022-03-12 05:50:54'),
(461, 'udin', 'logging in to mobile phone successfully', '2022-03-12 05:54:13'),
(462, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-12 05:54:26'),
(463, 'udin', 'logging in successfuly', '2022-03-12 05:54:33'),
(464, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-12 06:00:07'),
(465, 'udin', 'logging in successfuly', '2022-03-12 06:00:20'),
(466, 'udin', 'logging in successfuly', '2022-03-12 06:07:56'),
(467, 'udin', 'logging in successfuly', '2022-03-12 06:09:48'),
(468, 'udin', 'logging in successfuly', '2022-03-12 06:13:47'),
(469, 'udin', 'logging in successfuly', '2022-03-12 06:16:26'),
(470, 'udin', 'logging in successfuly', '2022-03-12 06:19:38'),
(471, 'udin', 'logging in successfuly', '2022-03-12 06:23:36'),
(472, 'udin', 'logging in successfuly', '2022-03-12 06:23:54'),
(473, 'udin', 'logging in successfuly', '2022-03-12 06:34:24'),
(474, 'udin', 'logging in successfuly', '2022-03-12 06:34:55'),
(475, 'udin', 'logging in successfuly', '2022-03-12 06:35:48'),
(476, 'udin', 'logging in successfuly', '2022-03-12 06:39:37'),
(477, 'udin', 'logging in successfuly', '2022-03-12 06:43:19'),
(478, 'udin', 'logging in successfuly', '2022-03-12 06:46:35'),
(479, 'udin', 'logging in successfuly', '2022-03-12 06:57:31'),
(480, 'udin', 'logging in successfuly', '2022-03-12 06:58:22'),
(481, 'udin', 'logging in successfuly', '2022-03-12 07:01:55'),
(482, 'udin', 'logging in successfuly', '2022-03-12 07:06:38'),
(483, 'udin', 'updating client settings', '2022-03-12 07:07:17'),
(484, 'udin', 'logging in successfuly', '2022-03-12 07:07:32'),
(485, 'udin', 'updating client settings', '2022-03-12 07:08:16'),
(486, 'udin', 'logging in successfuly', '2022-03-12 07:12:55'),
(487, 'udin', 'updating client settings', '2022-03-12 07:13:05'),
(488, 'udin', 'logging in successfuly', '2022-03-12 07:14:21'),
(489, 'udin', 'updating client settings', '2022-03-12 07:15:10'),
(490, 'udin', 'logging in successfuly', '2022-03-12 07:15:38'),
(491, 'udin', 'updating client settings', '2022-03-12 07:30:10'),
(492, 'udin', 'logging in successfuly', '2022-03-12 07:40:37'),
(493, 'udin', 'updating client settings', '2022-03-12 07:40:52'),
(494, 'udin', 'logging in successfuly', '2022-03-12 08:57:45'),
(495, 'udin', 'updating client settings', '2022-03-12 08:58:11'),
(496, 'udin', 'logging in successfuly', '2022-03-12 10:16:24'),
(497, 'udin', 'logging in successfuly', '2022-03-12 10:26:09'),
(498, 'udin', 'logging in successfuly', '2022-03-12 10:27:51'),
(499, 'udin', 'logging in successfuly', '2022-03-12 10:40:19'),
(500, 'udin', 'updating client settings', '2022-03-12 10:40:28'),
(501, 'udin', 'logging in to mobile phone successfully', '2022-03-12 10:41:26'),
(502, 'udin', 'logging in to mobile phone successfully', '2022-03-13 04:16:49'),
(503, 'udin', 'verifying remote client from mobile phone successfully', '2022-03-13 04:17:23'),
(504, 'udin', 'logging in successfuly', '2022-03-13 04:17:36'),
(505, 'udin', 'logging in to mobile phone successfully', '2022-03-13 05:52:38'),
(506, 'udin', 'logging in to mobile phone successfully', '2022-03-13 06:11:50'),
(507, 'udin', 'logging in to mobile phone successfully', '2022-03-13 06:41:53'),
(508, 'udin', 'logging in to mobile phone successfully', '2022-03-13 06:46:09'),
(509, 'udin', 'logging in to mobile phone successfully', '2022-03-13 06:55:47'),
(510, 'udin', 'logging in to mobile phone successfully', '2022-03-13 07:13:11'),
(511, 'udin', 'logging in to mobile phone successfully', '2022-03-13 07:21:55'),
(512, 'udin', 'logging in to mobile phone successfully', '2022-03-13 07:30:47'),
(513, 'udin', 'logging out from mobile phone successfully', '2022-03-13 07:34:18'),
(514, 'udin', 'logging in to mobile phone successfully', '2022-03-13 07:35:14'),
(515, 'udin', 'logging in successfuly', '2022-03-13 07:35:43'),
(516, 'udin', 'logging in to mobile phone successfully', '2022-03-13 17:14:35'),
(517, 'udin', 'logging in to mobile phone successfully', '2022-03-13 17:32:29'),
(518, 'udin', 'logging in to mobile phone successfully', '2022-03-13 17:50:31'),
(519, 'udin', 'logging in to mobile phone successfully', '2022-03-13 18:08:52'),
(520, 'udin', 'logging in to mobile phone successfully', '2022-03-13 18:32:56'),
(521, 'udin', 'logging in to mobile phone successfully', '2022-03-13 18:35:59'),
(522, 'udin', 'logging in to mobile phone successfully', '2022-03-13 18:46:55'),
(523, 'udin', 'logging in to mobile phone successfully', '2022-03-13 18:56:26'),
(524, 'udin', 'logging in to mobile phone successfully', '2022-03-13 19:02:26'),
(525, 'udin', 'logging in to mobile phone successfully', '2022-03-13 19:04:24'),
(526, 'udin', 'logging in to mobile phone successfully', '2022-03-13 19:17:44'),
(527, 'udin', 'logging in to mobile phone successfully', '2022-03-13 19:18:47'),
(528, 'udin', 'logging in to mobile phone successfully', '2022-03-13 19:37:13'),
(529, 'udin', 'logging in to mobile phone successfully', '2022-03-13 19:47:29'),
(530, 'udin', 'logging in to mobile phone successfully', '2022-03-13 19:58:36'),
(531, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:05:30'),
(532, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:07:14'),
(533, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:22:20'),
(534, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:23:44'),
(535, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:31:16'),
(536, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:33:21'),
(537, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:34:36'),
(538, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:36:33'),
(539, 'udin', 'logging in to mobile phone successfully', '2022-03-13 20:51:54'),
(540, 'udin', 'logging in to mobile phone successfully', '2022-03-13 21:02:33'),
(541, 'udin', 'logging in to mobile phone successfully', '2022-03-13 21:07:57'),
(542, 'udin', 'logging in to mobile phone successfully', '2022-03-13 21:18:16'),
(543, 'udin', 'logging in to mobile phone successfully', '2022-03-13 21:19:51'),
(544, 'udin', 'logging in to mobile phone successfully', '2022-03-13 21:21:20'),
(545, 'udin', 'logging in to mobile phone successfully', '2022-03-14 04:31:56'),
(546, 'udin', 'logging in to mobile phone successfully', '2022-03-15 06:52:48'),
(547, 'udin', 'logging in to mobile phone successfully', '2022-03-15 07:00:21'),
(548, 'udin', 'logging in to mobile phone successfully', '2022-03-15 07:07:49'),
(549, 'udin', 'logging in to mobile phone successfully', '2022-03-15 07:03:08'),
(550, 'udin', 'logging in to mobile phone successfully', '2022-03-15 07:11:08'),
(551, 'udin', 'logging in to mobile phone successfully', '2022-03-15 07:51:45'),
(552, 'udin', 'logging in to mobile phone successfully', '2022-03-15 08:01:04'),
(553, 'udin', 'logging in to mobile phone successfully', '2022-03-15 08:02:10'),
(554, 'udin', 'logging in to mobile phone successfully', '2022-03-15 08:07:31'),
(555, 'udin', 'logging in to mobile phone successfully', '2022-03-15 08:36:44'),
(556, 'udin', 'logging in to mobile phone successfully', '2022-03-15 08:41:46'),
(557, 'udin', 'logging in to mobile phone successfully', '2022-03-15 08:45:45'),
(558, 'udin', 'logging in to mobile phone successfully', '2022-03-15 08:52:47'),
(559, 'udin', 'logging in to mobile phone successfully', '2022-03-15 09:45:26'),
(560, 'udin', 'logging in to mobile phone successfully', '2022-03-15 09:52:54'),
(561, 'udin', 'logging in to mobile phone successfully', '2022-03-15 23:09:17'),
(562, 'udin', 'logging in to mobile phone successfully', '2022-03-16 00:19:38'),
(563, 'udin', 'logging in to mobile phone successfully', '2022-03-16 00:20:18'),
(564, 'udin', 'logging in to mobile phone successfully', '2022-03-16 00:23:11'),
(565, 'udin', 'logging in to mobile phone successfully', '2022-03-16 00:25:55'),
(566, 'udin', 'logging in to mobile phone successfully', '2022-03-16 00:27:07'),
(567, 'udin', 'logging in to mobile phone successfully', '2022-03-16 00:52:49'),
(568, 'udin', 'logging in to mobile phone successfully', '2022-03-16 02:31:43'),
(569, 'udin', 'logging in to mobile phone successfully', '2022-03-16 02:47:09'),
(570, 'udin', 'logging in to mobile phone successfully', '2022-03-16 03:04:44'),
(571, 'udin', 'activating click sounds in settings!', '2022-03-16 03:10:07'),
(572, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:20:46'),
(573, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:24:10'),
(574, 'udin', 'logging out from mobile phone successfully', '2022-03-16 06:24:17'),
(575, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:25:43'),
(576, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:26:48'),
(577, 'udin', 'logging out from mobile phone successfully', '2022-03-16 06:26:55'),
(578, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:29:51'),
(579, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:41:26'),
(580, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:46:02'),
(581, 'udin', 'logging in to mobile phone successfully', '2022-03-16 06:50:53'),
(582, 'udin', 'logging in to mobile phone successfully', '2022-03-16 08:52:55'),
(583, 'udin', 'logging in to mobile phone successfully', '2022-03-16 10:04:39'),
(584, 'udin', 'logging in to mobile phone successfully', '2022-03-16 10:05:15'),
(585, 'udin', 'logging in to mobile phone successfully', '2022-03-16 10:42:14'),
(586, 'udin', 'logging in to mobile phone successfully', '2022-03-16 10:51:16'),
(587, 'udin', 'logging in to mobile phone successfully', '2022-03-16 10:53:08'),
(588, 'udin', 'logging in to mobile phone successfully', '2022-03-16 10:57:51'),
(589, 'udin', 'logging in to mobile phone successfully', '2022-03-16 10:59:41'),
(590, 'udin', 'logging in to mobile phone successfully', '2022-03-16 11:01:38'),
(591, 'udin', 'logging in to mobile phone successfully', '2022-03-16 11:03:58'),
(592, 'udin', 'logging in to mobile phone successfully', '2022-03-16 12:44:04'),
(593, 'udin', 'logging in to mobile phone successfully', '2022-03-16 12:46:15'),
(594, 'udin', 'logging in to mobile phone successfully', '2022-03-16 12:47:02'),
(595, 'udin', 'logging in to mobile phone successfully', '2022-03-16 12:48:23'),
(596, 'udin', 'logging in to mobile phone successfully', '2022-03-16 12:50:35'),
(597, 'udin', 'logging in to mobile phone successfully', '2022-03-16 12:54:54'),
(598, 'udin', 'logging in to mobile phone successfully', '2022-03-16 12:55:25'),
(599, 'udin', 'logging in to mobile phone successfully', '2022-03-16 13:00:30'),
(600, 'udin', 'logging in to mobile phone successfully', '2022-03-16 13:10:40'),
(601, 'udin', 'logging out from mobile phone successfully', '2022-03-16 13:11:03'),
(602, 'udin', 'logging in to mobile phone successfully', '2022-03-16 13:11:09'),
(603, 'udin', 'logging in to mobile phone successfully', '2022-03-16 13:17:04'),
(604, 'udin', 'logging in to mobile phone successfully', '2022-03-16 13:18:18'),
(605, 'udin', 'logging in to mobile phone successfully', '2022-03-16 13:22:42'),
(606, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:07:48'),
(607, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:10:10'),
(608, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:13:09'),
(609, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:15:52'),
(610, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:19:57'),
(611, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:21:42'),
(612, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:22:52'),
(613, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:35:13'),
(614, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:37:27'),
(615, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:39:30'),
(616, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:41:58'),
(617, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:45:00'),
(618, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:46:26'),
(619, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:48:00'),
(620, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:50:03'),
(621, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:51:00'),
(622, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:54:29'),
(623, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:56:26'),
(624, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:57:32'),
(625, 'udin', 'logging in to mobile phone successfully', '2022-03-16 14:59:55'),
(626, 'udin', 'logging in to mobile phone successfully', '2022-03-16 15:10:04'),
(627, 'udin', 'logging in to mobile phone successfully', '2022-03-17 02:52:02'),
(628, 'udin', 'logging in to mobile phone successfully', '2022-03-17 02:59:41'),
(629, 'udin', 'logging in to mobile phone successfully', '2022-03-17 03:02:55'),
(630, 'udin', 'logging in to mobile phone successfully', '2022-03-17 03:23:22'),
(631, 'udin', 'logging in to mobile phone successfully', '2022-03-17 03:25:47'),
(632, 'udin', 'logging in to mobile phone successfully', '2022-03-17 03:45:24'),
(633, 'udin', 'logging in to mobile phone successfully', '2022-03-17 03:46:57'),
(634, 'udin', 'logging in to mobile phone successfully', '2022-03-17 03:49:49'),
(635, 'udin', 'logging in to mobile phone successfully', '2022-03-17 03:51:14'),
(636, 'udin', 'logging in to mobile phone successfully', '2022-03-17 04:00:06'),
(637, 'udin', 'logging in to mobile phone successfully', '2022-03-17 04:01:37'),
(638, 'udin', 'logging in to mobile phone successfully', '2022-03-17 04:03:10'),
(639, 'udin', 'logging in to mobile phone successfully', '2022-03-17 04:06:21'),
(640, 'udin', 'logging in to mobile phone successfully', '2022-03-17 04:10:13'),
(641, 'udin', 'logging in to mobile phone successfully', '2022-03-17 04:55:33'),
(642, 'udin', 'logging in to mobile phone successfully', '2022-03-17 04:58:35'),
(643, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:28:13'),
(644, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:46:42'),
(645, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:47:44'),
(646, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:49:51'),
(647, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:51:23'),
(648, 'udin', 'logging out from mobile phone successfully', '2022-03-17 06:52:57'),
(649, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:53:01'),
(650, 'udin', 'logging out from mobile phone successfully', '2022-03-17 06:54:44'),
(651, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:55:07'),
(652, 'udin', 'logging in to mobile phone successfully', '2022-03-17 06:57:34'),
(653, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:00:11'),
(654, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:01:33'),
(655, 'udin', 'logging out from mobile phone successfully', '2022-03-17 07:01:36'),
(656, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:01:44'),
(657, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:12:34'),
(658, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:17:20'),
(659, 'udin', 'activating click sounds in settings!', '2022-03-17 07:17:42'),
(660, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:20:22'),
(661, 'udin', 'deactivating click sounds in settings!', '2022-03-17 07:20:26'),
(662, 'udin', 'activating click sounds in settings!', '2022-03-17 07:20:28'),
(663, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:23:52'),
(664, 'udin', 'logging in to mobile phone successfully', '2022-03-17 07:33:41'),
(665, 'udin', 'logging in to mobile phone successfully', '2022-03-19 06:46:54'),
(666, 'udin', 'logging in to mobile phone successfully', '2022-03-19 07:03:53'),
(667, 'udin', 'logging in to mobile phone successfully', '2022-03-19 07:06:51'),
(668, 'udin', 'logging in to mobile phone successfully', '2022-03-27 03:56:28'),
(669, 'udin', 'logging in to mobile phone successfully', '2022-03-27 03:59:20'),
(670, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:04:29'),
(671, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:05:18'),
(672, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:08:00'),
(673, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:08:50'),
(674, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:13:17'),
(675, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:36:40'),
(676, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:39:46'),
(677, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:46:39'),
(678, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:48:55'),
(679, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:49:46'),
(680, 'udin', 'logging in to mobile phone successfully', '2022-03-27 04:52:58'),
(681, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:02:54'),
(682, 'udin', 'logging out from mobile phone successfully', '2022-03-27 05:03:18'),
(683, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:03:23'),
(684, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:05:53'),
(685, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:40:07'),
(686, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:43:18'),
(687, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:47:12'),
(688, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:48:27'),
(689, 'udin', 'logging in to mobile phone successfully', '2022-03-27 05:50:53'),
(690, 'udin', 'logging in to mobile phone successfully', '2022-03-27 06:17:40'),
(691, 'udin', 'logging in to mobile phone successfully', '2022-03-27 06:22:16'),
(692, 'udin', 'logging in to mobile phone successfully', '2022-03-27 06:52:17'),
(693, 'udin', 'logging in to mobile phone successfully', '2022-03-27 06:56:21'),
(694, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:01:45'),
(695, 'udin', 'logging out from mobile phone successfully', '2022-03-27 07:02:11'),
(696, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:02:15'),
(697, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:05:41'),
(698, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:09:48'),
(699, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:15:37'),
(700, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:17:20'),
(701, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:26:28'),
(702, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:33:27'),
(703, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:41:18'),
(704, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:50:07'),
(705, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:52:41'),
(706, 'udin', 'logging in to mobile phone successfully', '2022-03-27 07:55:00'),
(707, 'udin', 'logging in to mobile phone successfully', '2022-03-27 08:04:48'),
(708, 'udin', 'attendance updated with hadir status!', '2022-03-27 08:05:06'),
(709, 'udin', 'logging in to mobile phone successfully', '2022-03-27 08:10:33'),
(710, 'udin', 'attendance updated with hadir status!', '2022-03-27 08:10:58'),
(711, 'udin', 'attendance updated with hadir status!', '2022-03-27 08:10:58'),
(712, 'udin', 'logging in to mobile phone successfully', '2022-03-27 08:15:03'),
(713, 'udin', 'logging in to mobile phone successfully', '2022-03-27 08:19:52'),
(714, 'udin', 'attendance updated with hadir status!', '2022-03-27 08:20:14'),
(715, 'udin', 'attendance updated with hadir status!', '2022-03-27 08:20:14'),
(716, 'udin', 'logging in to mobile phone successfully', '2022-03-27 08:21:50'),
(717, 'udin', 'logging in to mobile phone successfully', '2022-03-28 03:05:31'),
(718, 'udin', 'logging out from mobile phone successfully', '2022-03-28 03:05:57'),
(719, 'udin', 'logging in to mobile phone successfully', '2022-03-28 03:06:01'),
(720, 'udin', 'logging in to mobile phone successfully', '2022-03-28 03:18:33'),
(721, 'udin', 'logging in to mobile phone successfully', '2022-03-28 03:43:43'),
(722, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:08:36'),
(723, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:08:50'),
(724, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:08:50'),
(725, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:16:46'),
(726, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:17:00');
INSERT INTO `data_history` (`id`, `username`, `description`, `date_created`) VALUES
(727, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:17:00'),
(728, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:18:52'),
(729, 'udin', 'logging out from mobile phone successfully', '2022-03-28 04:19:08'),
(730, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:19:13'),
(731, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:19:24'),
(732, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:19:25'),
(733, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:21:22'),
(734, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:21:43'),
(735, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:21:43'),
(736, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:26:25'),
(737, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:26:35'),
(738, 'udin', 'attendance updated with hadir status!', '2022-03-28 04:26:35'),
(739, 'udin', 'logging out from mobile phone successfully', '2022-03-28 04:28:21'),
(740, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:42:48'),
(741, 'udin', 'logging out from mobile phone successfully', '2022-03-28 04:43:11'),
(742, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:46:35'),
(743, 'udin', 'starting class at 11:48', '2022-03-28 04:48:28'),
(744, 'udin', 'starting class at 11:48', '2022-03-28 04:48:38'),
(745, 'udin', 'starting class at 11:48', '2022-03-28 04:48:49'),
(746, 'udin', 'logging out from mobile phone successfully', '2022-03-28 04:56:35'),
(747, 'udin', 'logging in to mobile phone successfully', '2022-03-28 04:56:55'),
(748, 'udin', 'starting class at 11:57', '2022-03-28 04:57:20'),
(749, 'udin', 'logging out from mobile phone successfully', '2022-03-28 04:57:35'),
(750, 'udin', 'logging in to mobile phone successfully', '2022-03-31 07:28:38'),
(751, 'udin', 'logging out from mobile phone successfully', '2022-03-31 07:28:44'),
(752, 'udin', 'logging in to mobile phone successfully', '2022-03-31 07:29:28'),
(753, 'udin', 'logging out from mobile phone successfully', '2022-03-31 07:29:34'),
(754, 'udin', 'logging in to mobile phone successfully', '2022-03-31 07:29:38'),
(755, 'udin', 'logging out from mobile phone successfully', '2022-03-31 07:51:58'),
(756, 'udin', 'logging in to mobile phone successfully', '2022-03-31 07:52:15'),
(757, 'udin', 'logging in to mobile phone successfully', '2022-03-31 07:54:15'),
(758, 'udin', 'starting class at 15:53', '2022-03-31 08:53:28'),
(759, 'udin', 'starting class at 15:53', '2022-03-31 08:53:46'),
(760, 'udin', 'starting class at 15:55', '2022-03-31 08:55:45'),
(761, 'udin', 'starting class at 15:57', '2022-03-31 08:57:18'),
(762, 'udin', 'starting class at 16:00', '2022-03-31 09:00:28'),
(763, 'udin', 'starting class at 16:16', '2022-03-31 09:16:12'),
(764, 'udin', 'starting class at 16:18', '2022-03-31 09:18:16'),
(765, 'udin', 'starting class at 16:18', '2022-03-31 09:18:17'),
(766, 'udin', 'starting class at 16:19', '2022-03-31 09:19:47'),
(767, 'udin', 'starting class at 17:09', '2022-03-31 10:09:53'),
(768, 'udin', 'starting class at 17:10', '2022-03-31 10:10:45'),
(769, 'udin', 'starting class at 17:14', '2022-03-31 10:14:15'),
(770, 'udin', 'starting class at 17:15', '2022-03-31 10:15:06'),
(771, 'udin', 'starting class at 17:22', '2022-03-31 10:22:22'),
(772, 'udin', 'starting class at 17:23', '2022-03-31 10:23:54'),
(773, 'udin', 'starting class at 17:24', '2022-03-31 10:24:09'),
(774, 'udin', 'starting class at 17:24', '2022-03-31 10:24:42'),
(775, 'udin', 'logging out from mobile phone successfully', '2022-03-31 11:06:05');

-- --------------------------------------------------------

--
-- Table structure for table `data_payment`
--

CREATE TABLE `data_payment` (
  `id` int(4) NOT NULL,
  `username` varchar(75) NOT NULL,
  `amount` double NOT NULL,
  `method` varchar(45) NOT NULL,
  `screenshot` varchar(75) NOT NULL DEFAULT 'not available',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_payment`
--

INSERT INTO `data_payment` (`id`, `username`, `amount`, `method`, `screenshot`, `date_created`) VALUES
(3, 'dddd', 500000, 'Cash', 'not available', '2020-11-30 03:42:05'),
(13, 'dede', 900000, 'Cash', 'not available', '2020-12-09 07:35:58'),
(27, 'xxx', 500000, 'Transfer Bank', '1646804159_payment_1646804155753.jpeg', '2022-03-12 06:39:29');

-- --------------------------------------------------------

--
-- Table structure for table `data_remote_login`
--

CREATE TABLE `data_remote_login` (
  `id` int(4) NOT NULL,
  `username` varchar(75) DEFAULT NULL,
  `machine_unique` varchar(100) NOT NULL,
  `country` varchar(75) NOT NULL,
  `region` varchar(75) NOT NULL,
  `city` varchar(75) NOT NULL,
  `isp` varchar(75) NOT NULL,
  `isp_as` varchar(75) NOT NULL,
  `ip_address` varchar(25) NOT NULL,
  `status` varchar(25) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_remote_login`
--

INSERT INTO `data_remote_login` (`id`, `username`, `machine_unique`, `country`, `region`, `city`, `isp`, `isp_as`, `ip_address`, `status`, `date_created`) VALUES
(1, 'udin', '4B435451-394A-3043-4631-14DAE9AD8243', 'Indonesia', 'West Java', 'Bandung', 'Indonesia Broadband Communications', 'AS55699 PT. Cemerlang Multimedia', '103.247.197.1', 'out', '2022-03-13 04:17:43');

-- --------------------------------------------------------

--
-- Table structure for table `data_report_bugs`
--

CREATE TABLE `data_report_bugs` (
  `id` int(4) NOT NULL,
  `app_name` varchar(75) NOT NULL,
  `username` varchar(75) NOT NULL,
  `ip_address` varchar(20) NOT NULL,
  `title` varchar(75) NOT NULL,
  `description` varchar(200) NOT NULL,
  `screenshot` varchar(75) NOT NULL DEFAULT 'not available',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_report_bugs`
--

INSERT INTO `data_report_bugs` (`id`, `app_name`, `username`, `ip_address`, `title`, `description`, `screenshot`, `date_created`) VALUES
(14, 'portal access', 'udin', '127.0.0.1', 'asd', 'asd', 'not available', '2020-12-09 17:54:09'),
(15, 'portal access', 'udin', '127.0.0.1', 'cccc', 'cccc cccc', 'not available', '2020-12-09 17:54:22'),
(16, 'portal access', 'udin', '127.0.0.1', 'asdasd', 'asdasd asd', '1607536482_drawing.png', '2020-12-09 17:54:42');

-- --------------------------------------------------------

--
-- Table structure for table `data_schedule`
--

CREATE TABLE `data_schedule` (
  `id` int(4) NOT NULL,
  `username` varchar(75) NOT NULL,
  `day_schedule` varchar(12) NOT NULL,
  `time_schedule` varchar(12) NOT NULL,
  `class_registered` varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_schedule`
--

INSERT INTO `data_schedule` (`id`, `username`, `day_schedule`, `time_schedule`, `class_registered`) VALUES
(1, 'dddd', 'friday', '10:00', 'java web'),
(10, 'udin', 'thursday', '17:00', 'javascript web'),
(11, 'dede', 'sunday', '13:00', 'java web'),
(12, 'admin', 'wednesday', '10:00', 'java web'),
(13, 'udin', 'wednesday', '09:00', 'javascript web'),
(14, 'dede', 'monday', '00:00', 'javascript web');

-- --------------------------------------------------------

--
-- Table structure for table `data_token`
--

CREATE TABLE `data_token` (
  `id` int(4) NOT NULL,
  `username` varchar(75) NOT NULL,
  `token` varchar(75) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expired_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_token`
--

INSERT INTO `data_token` (`id`, `username`, `token`, `created_date`, `expired_date`) VALUES
(110, 'udin', '087667b2532fb78ab9cd5ac75918cf807a3fa1c9', '2022-03-20 13:34:34', '2022-03-20 21:03:34'),
(111, 'dede', 'a08decd1ac560fd88e853b9de60c72e71892f20d', '2022-03-20 13:34:35', '2022-03-20 21:03:35'),
(112, 'udin', '45c5f7a716d544863c8ffa44de509af98df47399', '2022-03-20 13:39:22', '2022-03-20 21:03:22'),
(113, 'dede', '175ba32680a7ff63c3f12f916ad6bcf64d999037', '2022-03-20 13:39:23', '2022-03-20 21:03:23'),
(114, 'udin', '9a3e7674ec178c7e52d56ac2459b71f6ab8c8762', '2022-03-20 13:47:42', '2022-03-19 21:03:03'),
(115, 'dede', 'eaad6283c0f9bc5a0927c6fe96c95353f26489b0', '2022-03-20 13:45:04', '2022-03-20 21:03:04'),
(116, 'udin', '8d81f85866bdf99080c7174956cd9b5ac24d0922', '2022-03-27 03:56:28', '2022-03-28 10:03:28'),
(117, 'udin', '070ea76b7e123551dd9892d57a6a0fc622ecf7a3', '2022-03-28 03:05:31', '2022-03-29 10:03:31'),
(118, 'udin', '79b1240590e459c415e6dd4a049e56fca8b67fff', '2022-03-31 07:53:29', '2022-03-31 14:04:38'),
(119, 'udin', 'a2893e841dadbf2af233e1bca56543efc2ee23e1', '2022-03-31 07:54:15', '2022-04-01 14:04:15');

-- --------------------------------------------------------

--
-- Table structure for table `data_tools`
--

CREATE TABLE `data_tools` (
  `id` int(4) NOT NULL,
  `app_name` varchar(75) NOT NULL,
  `app_ver` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_tools`
--

INSERT INTO `data_tools` (`id`, `app_name`, `app_ver`) VALUES
(1, 'teamviewer', '15.12.4.0');

-- --------------------------------------------------------

--
-- Table structure for table `data_user`
--

CREATE TABLE `data_user` (
  `id` int(4) NOT NULL,
  `username` varchar(75) NOT NULL,
  `pass` varchar(75) NOT NULL,
  `email` varchar(75) NOT NULL,
  `access_level` tinyint(1) NOT NULL DEFAULT '1',
  `address` varchar(200) NOT NULL,
  `propic` varchar(75) NOT NULL DEFAULT 'default.png',
  `mobile` varchar(75) NOT NULL DEFAULT 'not available',
  `tmv_id` varchar(25) NOT NULL DEFAULT 'not available',
  `tmv_pass` varchar(25) NOT NULL DEFAULT 'not available',
  `warning_status` tinyint(1) NOT NULL DEFAULT '0',
  `expired_date` date DEFAULT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_user`
--

INSERT INTO `data_user` (`id`, `username`, `pass`, `email`, `access_level`, `address`, `propic`, `mobile`, `tmv_id`, `tmv_pass`, `warning_status`, `expired_date`, `date_created`) VALUES
(1, 'admin', 'admin', 'admin@gmail.com', 1, 'bdg jakartaaa\nluar syaaaa', '1608179927_images.jpeg', 'not available', 'not available', 'not available\r\n\r\n\r\n\r\n', 0, NULL, '2022-03-03 09:55:06'),
(37, 'dede', 'dede', 'mentari.56@gmail.com', 3, 'hehehe', 'default.png', '123-123', '432996191', 'sosoais', 0, NULL, '2022-03-20 11:10:37'),
(38, 'udin', '123', 'udin@gmail.com', 1, 'bandung kota lautan apheee', '1647399767_propic_1647400305019.jpeg', '0812578', '098654', 'test\r\nx\r\n', 0, '2022-10-03', '2022-03-31 11:05:06');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_attendance`
--
ALTER TABLE `data_attendance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_bill`
--
ALTER TABLE `data_bill`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_certificate_student`
--
ALTER TABLE `data_certificate_student`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_class_room`
--
ALTER TABLE `data_class_room`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_document`
--
ALTER TABLE `data_document`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_exam_category`
--
ALTER TABLE `data_exam_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_exam_category_schedule`
--
ALTER TABLE `data_exam_category_schedule`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_exam_qa`
--
ALTER TABLE `data_exam_qa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_exam_student`
--
ALTER TABLE `data_exam_student`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_exam_student_scoring`
--
ALTER TABLE `data_exam_student_scoring`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_exam_sub_category`
--
ALTER TABLE `data_exam_sub_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_history`
--
ALTER TABLE `data_history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_payment`
--
ALTER TABLE `data_payment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_remote_login`
--
ALTER TABLE `data_remote_login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_report_bugs`
--
ALTER TABLE `data_report_bugs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_schedule`
--
ALTER TABLE `data_schedule`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_token`
--
ALTER TABLE `data_token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_tools`
--
ALTER TABLE `data_tools`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_user`
--
ALTER TABLE `data_user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_attendance`
--
ALTER TABLE `data_attendance`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `data_bill`
--
ALTER TABLE `data_bill`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `data_certificate_student`
--
ALTER TABLE `data_certificate_student`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `data_class_room`
--
ALTER TABLE `data_class_room`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `data_document`
--
ALTER TABLE `data_document`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `data_exam_category`
--
ALTER TABLE `data_exam_category`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `data_exam_category_schedule`
--
ALTER TABLE `data_exam_category_schedule`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `data_exam_qa`
--
ALTER TABLE `data_exam_qa`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `data_exam_student`
--
ALTER TABLE `data_exam_student`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `data_exam_sub_category`
--
ALTER TABLE `data_exam_sub_category`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `data_history`
--
ALTER TABLE `data_history`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=776;

--
-- AUTO_INCREMENT for table `data_payment`
--
ALTER TABLE `data_payment`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `data_remote_login`
--
ALTER TABLE `data_remote_login`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `data_report_bugs`
--
ALTER TABLE `data_report_bugs`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `data_schedule`
--
ALTER TABLE `data_schedule`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `data_token`
--
ALTER TABLE `data_token`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=120;

--
-- AUTO_INCREMENT for table `data_tools`
--
ALTER TABLE `data_tools`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `data_user`
--
ALTER TABLE `data_user`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
