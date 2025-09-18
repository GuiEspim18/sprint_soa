CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255)
);

CREATE TABLE portfolios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT UNIQUE,
  CONSTRAINT fk_portfolio_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE experiences (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  period VARCHAR(255),
  reaction VARCHAR(255),
  value DOUBLE,
  revenue DOUBLE,
  CONSTRAINT fk_experience_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE goals (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  experience_id BIGINT,
  goal VARCHAR(1000),
  CONSTRAINT fk_goal_experience FOREIGN KEY (experience_id) REFERENCES experiences(id) ON DELETE CASCADE
);

CREATE TABLE investments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  portfolio_id BIGINT,
  type VARCHAR(255),
  name VARCHAR(255),
  code VARCHAR(100),
  description TEXT,
  status VARCHAR(100),
  CONSTRAINT fk_investment_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolios(id) ON DELETE CASCADE
);

CREATE TABLE investment_attributes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  investment_id BIGINT,
  type VARCHAR(255),
  value VARCHAR(2000),
  CONSTRAINT fk_attr_investment FOREIGN KEY (investment_id) REFERENCES investments(id) ON DELETE CASCADE
);