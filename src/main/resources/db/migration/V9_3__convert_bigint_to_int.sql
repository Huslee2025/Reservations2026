
-- V9_3 : Conversion des identifiants BIGINT en INT
-- Conservation des donnees et des relations existantes.

-- 1. Retirer temporairement les cles etrangeres

ALTER TABLE `locations`
    DROP FOREIGN KEY `locations_locality_id_22dd0b44_fk_localities_id`;

ALTER TABLE `representations`
    DROP FOREIGN KEY `representations_show_id_a6832141_fk_shows_id`,
    DROP FOREIGN KEY `representations_location_id_a6832141_fk_locations_id`;

ALTER TABLE `shows`
    DROP FOREIGN KEY `shows_location_id_a6832141_fk_locations_id`;


-- 2. Convertir les colonnes BIGINT en INT

ALTER TABLE `localities`
    MODIFY COLUMN `id` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `locations`
    MODIFY COLUMN `id` INT NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN `locality_id` INT NOT NULL;

ALTER TABLE `representations`
    MODIFY COLUMN `id` INT NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN `show_id` INT NOT NULL,
    MODIFY COLUMN `location_id` INT NULL;

ALTER TABLE `roles`
    MODIFY COLUMN `id` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `shows`
    MODIFY COLUMN `id` INT NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN `location_id` INT NULL;

ALTER TABLE `types`
    MODIFY COLUMN `id` INT NOT NULL AUTO_INCREMENT;


-- 3. Recreer les cles etrangeres avec leurs regles d'origine

ALTER TABLE `locations`
    ADD CONSTRAINT `locations_locality_id_22dd0b44_fk_localities_id`
    FOREIGN KEY (`locality_id`)
    REFERENCES `localities` (`id`)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE `representations`
    ADD CONSTRAINT `representations_show_id_a6832141_fk_shows_id`
    FOREIGN KEY (`show_id`)
    REFERENCES `shows` (`id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;

ALTER TABLE `representations`
    ADD CONSTRAINT `representations_location_id_a6832141_fk_locations_id`
    FOREIGN KEY (`location_id`)
    REFERENCES `locations` (`id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;

ALTER TABLE `shows`
    ADD CONSTRAINT `shows_location_id_a6832141_fk_locations_id`
    FOREIGN KEY (`location_id`)
    REFERENCES `locations` (`id`)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

