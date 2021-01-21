delete from mmocore_playerdata;
delete from stats_playtime;
delete from stats_players;
delete from luckperms_players;
delete from images;
delete from comments;
delete from news;
delete from guide;
delete from authme;

INSERT INTO `authme` (`id`, `username`, `realname`, `password`, `ip`, `lastlogin`, `x`, `y`, `z`, `world`, `regdate`, `regip`, `yaw`, `pitch`, `email`, `isLogged`, `hasSession`, `totp`, `profileicon`) VALUES
(2, 'dante', 'Dante', '$2y$12$qUGoYRnZHHi8WCO/fk2/rO2YquztoWp/h7vY859lljTX5eaizxsMC', '178.71.195.39', 1607122620039, 0, 0, 0, 'world', 1607019054810, NULL, NULL, NULL, NULL, 0, 1, NULL, 0);

INSERT INTO `luckperms_players` (`uuid`, `username`, `primary_group`) VALUES
('56071dbf-3584-3531-8f51-d06000e6fc0d', 'dante', 'owner');

INSERT INTO `luckperms_players` (`uuid`, `username`, `primary_group`) VALUES
('dante-1611222303669', 'dante', 'admin');

INSERT INTO `stats_players` (`id`, `uuid`, `username`) VALUES
(11, 0x9aebd52f4b3f3fbfaf21aacc85542371, 'Dante');

INSERT INTO `mmocore_playerdata` (`uuid`, `class_points`, `skill_points`, `attribute_points`, `attribute_realloc_points`, `level`, `experience`, `class`, `guild`, `last_login`, `attributes`, `professions`, `quests`, `waypoints`, `friends`, `skills`, `bound_skills`, `class_info`) VALUES
('56071dbf-3584-3531-8f51-d06000e6fc0d', 0, 0, 0, 0, 1, 0, 'HUMAN', 'null', '1609707827269', '{}', '{\"smithing\":{\"exp\":0,\"level\":1},\"enchanting\":{\"exp\":0,\"level\":1},\"woodcutting\":{\"exp\":32,\"level\":1},\"fishing\":{\"exp\":0,\"level\":1},\"farming\":{\"exp\":0,\"level\":1},\"alchemy\":{\"exp\":0,\"level\":1},\"mining\":{\"exp\":0,\"level\":1},\"smelting\":{\"exp\":0,\"level\":1}}', '{}', '[]', '[]', '{}', '[]', '{}');

INSERT INTO `stats_playtime` (`player`, `world`, `amount`, `last_updated`) VALUES
(0x9aebd52f4b3f3fbfaf21aacc85542371, 0x5ba134a1827c4c06b6862703d01922dd, 8223, '2021-01-16 21:30:44');