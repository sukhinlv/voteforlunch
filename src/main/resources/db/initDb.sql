DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO "user";

CREATE TABLE "users"
(
    "id"         INTEGER                        NULL,
    "name"       VARCHAR(255)                   NOT NULL,
    "email"      VARCHAR(255)                   NOT NULL,
    "password"   VARCHAR(255)                   NOT NULL,
    "registered" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL DEFAULT 'now()',
    "enabled"    BOOLEAN                        NOT NULL DEFAULT '1'
);
ALTER TABLE
    "users"
    ADD PRIMARY KEY ("id");
ALTER TABLE
    "users"
    ADD CONSTRAINT "users_email_unique" UNIQUE ("email");
CREATE TABLE "user_roles"
(
    "user_id" INTEGER      NOT NULL,
    "role"    VARCHAR(255) NOT NULL
);
ALTER TABLE
    "user_roles"
    ADD CONSTRAINT "user_roles_user_id_role_unique" UNIQUE ("user_id", "role");
CREATE TABLE "meal"
(
    "id"   INTEGER      NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "meal"
    ADD CONSTRAINT "meal_name_unique" UNIQUE ("name");
ALTER TABLE
    "meal"
    ADD PRIMARY KEY ("id");
CREATE TABLE "restaurant"
(
    "id"   INTEGER      NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "restaurant"
    ADD PRIMARY KEY ("id");
ALTER TABLE
    "restaurant"
    ADD CONSTRAINT "restaurant_name_unique" UNIQUE ("name");
CREATE TABLE "menu"
(
    "id"            INTEGER NOT NULL,
    "restaurant_id" INTEGER NOT NULL,
    "date_of_menu"  DATE    NOT NULL
);
ALTER TABLE
    "menu"
    ADD CONSTRAINT "menu_date_of_menu_restaurant_id_unique" UNIQUE ("date_of_menu", "restaurant_id");
ALTER TABLE
    "menu"
    ADD PRIMARY KEY ("id");
CREATE TABLE "menu_set"
(
    "id"      INTEGER          NOT NULL,
    "menu_id" INTEGER          NOT NULL,
    "meal_id" INTEGER          NOT NULL,
    "price"   DOUBLE PRECISION NOT NULL
);
ALTER TABLE
    "menu_set"
    ADD CONSTRAINT "menu_set_menu_id_meal_id_unique" UNIQUE ("menu_id", "meal_id");
ALTER TABLE
    "menu_set"
    ADD PRIMARY KEY ("id");
CREATE TABLE "vote"
(
    "id"             INTEGER                        NOT NULL,
    "user_id"        INTEGER                        NOT NULL,
    "menu_id"        INTEGER                        NOT NULL,
    "vote_date_time" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);
ALTER TABLE
    "vote"
    ADD CONSTRAINT "vote_user_id_vote_date_time_unique" UNIQUE ("user_id", "vote_date_time");
CREATE INDEX "vote_vote_date_time_menu_id_index" ON
    "vote" ("vote_date_time", "menu_id");
ALTER TABLE
    "vote"
    ADD PRIMARY KEY ("id");
ALTER TABLE
    "user_roles"
    ADD CONSTRAINT "user_roles_user_id_foreign" FOREIGN KEY ("user_id") REFERENCES "users" ("id");
ALTER TABLE
    "menu"
    ADD CONSTRAINT "menu_restaurant_id_foreign" FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id");
ALTER TABLE
    "menu_set"
    ADD CONSTRAINT "menu_set_menu_id_foreign" FOREIGN KEY ("menu_id") REFERENCES "menu" ("id");
ALTER TABLE
    "menu_set"
    ADD CONSTRAINT "menu_set_meal_id_foreign" FOREIGN KEY ("meal_id") REFERENCES "meal" ("id");
ALTER TABLE
    "vote"
    ADD CONSTRAINT "vote_menu_id_foreign" FOREIGN KEY ("menu_id") REFERENCES "menu" ("id");
