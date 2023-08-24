CREATE TABLE `tasks` (
  `id` int(5) NOT NULL,
  `task` varchar(50) NOT NULL,
  `type` enum('FIXED_IN_TIME') NOT NULL,
  `last_activation` timestamp NOT NULL DEFAULT '2011-12-18 10:00:00',
  `startTime` varchar(8) NOT NULL,
  `delay` int(10) NOT NULL,
  `param` text NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Records of tasks (Restart-Time 3.00AM and 3.00PM)

INSERT INTO `tasks` (`id`, `task`, `type`, `last_activation`, `startTime`, `delay`, `param`) VALUES
(0, 'restart', 'FIXED_IN_TIME', '2011-12-17 03:00:00', '03:10:00', 0, '60 5 320');