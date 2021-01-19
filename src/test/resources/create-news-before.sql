delete from comments;
delete from news;

INSERT INTO `news` (`id`, `header`, `description`, `message`, `date`, `img_name`, `views`) VALUES
(3, 'Приветствие', 'Приятной игры на нашем сервере!', 'Message 1',  '2021-01-14 21:16:14', null, 22) ,
(5, 'Обновление РПГ системы', 'Началась разработка РПГ системы', 'Message 2', '2021-01-15 22:16:14', null, 39);