create database websocket;
use websocket;

CREATE TABLE USERS(
                      USER_ID BIGINT AUTO_INCREMENT,
                      USER_NAME VARCHAR(20) UNIQUE NOT NULL,
                      USER_EMAIL VARCHAR(20),
                      USER_PASSWORD VARCHAR(16) NOT NULL,
                      CONSTRAINT PK_USER PRIMARY KEY (USER_ID)
);

CREATE TABLE STUDY(
                      STUDY_ID BIGINT AUTO_INCREMENT,
                      STUDY_TITLE VARCHAR(20) NOT NULL,
                      STUDY_LEADER_ID BIGINT NOT NULL,
                      STUDY_DATE DATE NOT NULL,
                      CONSTRAINT PK_STUDY_GROUP PRIMARY KEY(STUDY_ID),
                      CONSTRAINT FK_STUDY_LEADER FOREIGN KEY (STUDY_LEADER_ID) REFERENCES USERS(USER_ID)
);

CREATE TABLE STUDY_MEMBER(
                             STUDY_MEMBER_ID BIGINT AUTO_INCREMENT,
                             STUDY_ID BIGINT NOT NULL,
                             USER_ID BIGINT NOT NULL,
                             USER_NICKNAME VARCHAR(20) NOT NULL,
                             AUTH_ROLE VARCHAR(10) NULL,
                             MESSAGE_COUNT BIGINT DEFAULT 0,
                             CONSTRAINT PK_STUDY_MEMBER PRIMARY KEY(STUDY_MEMBER_ID),
                             CONSTRAINT FK_STUDY FOREIGN KEY (STUDY_ID) REFERENCES STUDY(STUDY_ID),
                             CONSTRAINT FK_USERS FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)
);

CREATE TABLE CHAT_MESSAGE(
                             CHAT_MESSAGE_ID BIGINT AUTO_INCREMENT,
                             STUDY_ID BIGINT NOT NULL,
                             USER_ID BIGINT NOT NULL,
                             MESSAGE_CONTENT VARCHAR(100),
                             MESSAGE_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
                             UNREAD_COUNT BIGINT,
                             CONSTRAINT PK_CHAT_MESSAGE PRIMARY KEY (CHAT_MESSAGE_ID),
                             CONSTRAINT FK_STUDY_MESSAGE FOREIGN KEY (STUDY_ID) REFERENCES STUDY(STUDY_ID),
                             CONSTRAINT FK_USERS_MESSAGE FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)
);

CREATE TABLE ALARM(
                      ALARM_ID BIGINT AUTO_INCREMENT,
                      STUDY_ID BIGINT NOT NULL,
                      USER_ID BIGINT NOT NULL,
                      ALARM_TYPE VARCHAR(1) NOT NULL,
                      ALARM_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
                      ALARM_TITLE VARCHAR(100) NOT NULL,
                      CONSTRAINT PK_ALARM PRIMARY KEY (ALARM_ID),
                      CONSTRAINT FK_STUDY_ALARM FOREIGN KEY (STUDY_ID) REFERENCES STUDY(STUDY_ID),
                      CONSTRAINT FK_USERS_ALARM FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)
);

CREATE TABLE UNREAD_MESSAGE(
                               UNREAD_MESSAGE_ID BIGINT AUTO_INCREMENT,
                               USER_ID BIGINT NOT NULL,
                               STUDY_ID BIGINT NOT NULL,
                               CONSTRAINT PK_UNREAD_MESSAGE PRIMARY KEY (UNREAD_MESSAGE_ID),
                               CONSTRAINT FK_STUDY_UNREAD_MESSAGE FOREIGN KEY (STUDY_ID) REFERENCES STUDY(STUDY_ID),
                               CONSTRAINT FK_USERS_UNREAD_MESSAGE FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)
);