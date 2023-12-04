# Query for mySQL

## Create Database
CREATE DATABASE simplebank;

## Create Table Users
CREATE TABLE users
(
    username			VARCHAR(100) NOT NULL,
    passwords			VARCHAR(100) NOT NULL,
    name             	VARCHAR(100) NOT NULL,
    token            	VARCHAR(100),
    token_expired_at 	BIGINT,
    PRIMARY KEY (username),
    UNIQUE (token)
) ENGINE InnoDB;

## Create Table Accounts
CREATE TABLE accounts
(
    id					VARCHAR(100) NOT NULL,
    username			VARCHAR(100) NOT NULL,
    account_number		VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_users_accounts (username) REFERENCES users (username)
) ENGINE InnoDB;

## Create Table Transactions
CREATE TABLE transactions
(
    id					VARCHAR(100) NOT NULL,
    account_id			VARCHAR(100) NOT NULL,
    to_account			VARCHAR(100) NOT NULL,
    amount				VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_accounts_transactions (account_id) REFERENCES accounts (id)
) ENGINE InnoDB;
