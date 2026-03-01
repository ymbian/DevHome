-- Tech improvement task tables (one per type). Run against MySQL when using tech list from MySQL.
-- Each table: application name, issue description, deadline.

CREATE TABLE IF NOT EXISTS tech_task_vulnerability_fix (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  application_name VARCHAR(255) NOT NULL DEFAULT '',
  issue_description TEXT,
  deadline DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tech_task_component_upgrade (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  application_name VARCHAR(255) NOT NULL DEFAULT '',
  issue_description TEXT,
  deadline DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tech_task_database_compliance (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  application_name VARCHAR(255) NOT NULL DEFAULT '',
  issue_description TEXT,
  deadline DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tech_task_database_performance (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  application_name VARCHAR(255) NOT NULL DEFAULT '',
  issue_description TEXT,
  deadline DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tech_task_other (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  application_name VARCHAR(255) NOT NULL DEFAULT '',
  issue_description TEXT,
  deadline DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
