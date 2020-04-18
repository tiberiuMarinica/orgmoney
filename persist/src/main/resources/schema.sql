CREATE TABLE users(
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name CHARACTER VARYING(255) NOT NULL,
	cnp CHARACTER VARYING(13) NOT NULL,
	iban CHARACTER VARYING(255) NOT NULL,
	
	CONSTRAINT users_cnp_uk UNIQUE (cnp)
);

CREATE TABLE transactions (
	id character varying(36) NOT NULL PRIMARY KEY,
	payer_id bigint NOT NULL,
	payee_id bigint NOT NULL,
	`type` smallint NOT NULL,
	sum double precision NOT NULL,
	description character varying(255) NOT NULL,
	
	CONSTRAINT transactions_users_payer_id_fkey FOREIGN KEY (payer_id) REFERENCES users (id),
	CONSTRAINT transactions_users_payee_id_fkey FOREIGN KEY (payee_id) REFERENCES users (id)
);

