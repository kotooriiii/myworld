CREATE TABLE authors
(
    id            UUID NOT NULL PRIMARY KEY,

    name          TEXT NOT NULL,
    email         TEXT NOT NULL UNIQUE,
    password      TEXT NOT NULL,
    gender        TEXT NOT NULL,
    birth_date    DATE NOT NULL,

    image_icon_id VARCHAR(36),

    is_deleted    BOOLEAN DEFAULT FALSE
);

CREATE TABLE projects
(
    id            UUID NOT NULL PRIMARY KEY,

    title         TEXT NOT NULL,
    description   TEXT NOT NULL,
    image_icon_id VARCHAR(36),

    created_at TIMESTAMP NOT NULL,
    last_updated_at TIMESTAMP NOT NULL,
    created_by UUID NOT NULL,
    last_updated_by UUID NOT NULL,

    is_deleted    BOOLEAN DEFAULT FALSE
);

CREATE TABLE project_collaborators
(
    project_id   UUID NOT NULL,
    author_id    UUID NOT NULL,
    access_level TEXT NOT NULL,
    PRIMARY KEY (project_id, author_id),
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);

CREATE TABLE audit_logs
(
    id           UUID      NOT NULL PRIMARY KEY,

    timestamp    TIMESTAMP NOT NULL,

    event_type   TEXT      NOT NULL,

    source_class TEXT      NOT NULL,
    source_id    UUID      NOT NULL,
    target_class TEXT      NOT NULL,
    target_id    UUID      NOT NULL
);

CREATE TABLE change_logs
(
    id           UUID      NOT NULL PRIMARY KEY,

    timestamp    TIMESTAMP NOT NULL,

    event_type   TEXT      NOT NULL,

    old_data     JSONB     NOT NULL,
    new_data     JSONB     NOT NULL,

    audit_log_id UUID NOT NULL,

    FOREIGN KEY (audit_log_id) REFERENCES audit_logs(id) ON DELETE CASCADE
);