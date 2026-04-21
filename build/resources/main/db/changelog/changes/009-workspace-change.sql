-- liquibase formatted sql

-- changeset mightyloot:009-workspace-change
CREATE TABLE user_workspaces (
    user_id BIGINT NOT NULL,
    workspace_id BIGINT NOT NULL,
    workspace_role VARCHAR(200),
    joined_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    -- Створюємо композитний первинний ключ (один юзер не може бути доданий в один воркспейс двічі)
    PRIMARY KEY (user_id, workspace_id),

    -- Налаштовуємо зовнішні ключі з каскадним видаленням
    CONSTRAINT fk_user_workspace_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_workspace_workspace FOREIGN KEY (workspace_id) REFERENCES workspace (id) ON DELETE CASCADE
);

-- rollback DROP TABLE user_workspaces;