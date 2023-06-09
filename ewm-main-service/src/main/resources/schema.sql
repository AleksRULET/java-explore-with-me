CREATE TABLE IF NOT EXISTS USERS
(
    ID    BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT USERS_PK PRIMARY KEY,
    EMAIL VARCHAR(55) NOT NULL UNIQUE,
    NAME  VARCHAR(55) NOT NULL
);

CREATE TABLE IF NOT EXISTS CATEGORIES
(
    ID   BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT CATEGORY_PK PRIMARY KEY,
    NAME VARCHAR(55) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS EVENTS
(
    ID                 BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT EVENTS_PK PRIMARY KEY,
    ANNOTATION         VARCHAR(2000)  NOT NULL,
    CATEGORY_ID        BIGINT         NOT NULL
        CONSTRAINT EVENTS_EVENTS_FK REFERENCES CATEGORIES,
    CREATED_ON         TIMESTAMP      NOT NULL,
    DESCRIPTION        VARCHAR(10000) NOT NULL,
    EVENT_DATE         TIMESTAMP      NOT NULL,
    LAT                REAL           NOT NULL,
    LON                REAL           NOT NULL,
    INITIATOR_ID       BIGINT         NOT NULL
        CONSTRAINT EVENTS_USERS_FK REFERENCES USERS,
    PAID               BOOLEAN        NOT NULL,
    PARTICIPANT_LIMIT  INTEGER,
    PUBLISHED_ON       TIMESTAMP,
    REQUEST_MODERATION BOOLEAN        NOT NULL,
    STATE              VARCHAR(20)    NOT NULL,
    TITLE              VARCHAR(200)   NOT NULL
);

CREATE TABLE IF NOT EXISTS REQUESTS
(
    ID       BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT REQUESTS_PM PRIMARY KEY,
    CREATED  TIMESTAMP   NOT NULL,
    EVENT_ID BIGINT      NOT NULL
        CONSTRAINT REQUESTS_EVENTS_FK REFERENCES EVENTS,
    USER_ID  BIGINT      NOT NULL
        CONSTRAINT REQUESTS_USERS_FK REFERENCES USERS,
    STATUS   VARCHAR(10) NOT NULL
);


CREATE TABLE IF NOT EXISTS COMMENTS
(
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY CONSTRAINT COMMENTS_PM PRIMARY KEY,
    MESSAGE VARCHAR(1000) NOT NULL,
    CREATED TIMESTAMP NOT NULL,
    EVENT_ID BIGINT NOT NULL CONSTRAINT REQUESTS_EVENTS_FK REFERENCES EVENTS,
    USER_ID BIGINT NOT NULL CONSTRAINT REQUESTS_USERS_FK REFERENCES USERS
);

CREATE TABLE IF NOT EXISTS COMPILATIONS
(
    ID     BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT COMPILATIONS_PK PRIMARY KEY,
    PINNED BOOLEAN       NOT NULL,
    TITLE  VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS EVENTS_COMPILATIONS
(
    EVENT_ID       BIGINT NOT NULL REFERENCES EVENTS (ID),
    COMPILATION_ID BIGINT NOT NULL REFERENCES COMPILATIONS (ID),
    CONSTRAINT EVENTS_COMPILATIONS_PK PRIMARY KEY (EVENT_ID, COMPILATION_ID)
);

