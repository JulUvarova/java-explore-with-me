create TABLE IF NOT EXISTS stats (
  id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  app   VARCHAR(256) NOT NULL, --Идентификатор сервиса для которого записывается информация
  uri   VARCHAR(256) NOT NULL, -- URI для которого был осуществлен запрос
  ip    VARCHAR(256) NOT NULL, --IP-адрес пользователя, осуществившего запрос
  times TIMESTAMP WITHOUT TIME ZONE NOT NULL -- Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
);