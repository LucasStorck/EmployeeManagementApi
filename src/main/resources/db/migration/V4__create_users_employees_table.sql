CREATE TABLE user_employee (
    user_id UUID NOT NULL,
    employee_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, employee_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees (employee_id) ON DELETE CASCADE
);