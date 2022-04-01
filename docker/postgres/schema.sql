DROP TABLE IF EXISTS "rules";
DROP SEQUENCE IF EXISTS rule_id_seq;
CREATE SEQUENCE rule_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE rules
(
    rule_id        bigint    DEFAULT nextval('rule_id_seq') NOT NULL,
    rule_namespace varchar(256)                                    not null,
    condition      varchar(2000),
    action         varchar(2000),
    priority       integer,
    description    varchar(1000),
    status         smallint,
    "create_by"    bigint                                          NOT NULL,
    "create_time"  timestamp DEFAULT current_timestamp             NOT NULL,
    "update_by"    bigint,
    "update_time"  timestamp,
    CONSTRAINT "rules_pkey" PRIMARY KEY (rule_id)
);
