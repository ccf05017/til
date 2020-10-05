
-- chapter04.02

CREATE TABLE groups (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0) PRIMARY KEY,
    group_name VARCHAR(256) NOT NULL
);


CREATE TABLE group_authorities (
    group_id BIGINT NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_group_authorities_group FOREIGN KEY(group_id) REFERENCES groups(id)
);


CREATE TABLE group_members (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    group_id BIGINT NOT NULL,
    CONSTRAINT fk_group_members_group FOREIGN KEY(group_id) REFERENCES groups(id)
);

-- The End...
