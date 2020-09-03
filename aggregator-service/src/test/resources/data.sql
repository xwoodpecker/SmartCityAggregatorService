
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (1, NULL, 'airQuality_sensor1', 'AIR_QUALITY', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (2, NULL, 'airQuality_sensor2', 'AIR_QUALITY', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (3, NULL, 'parking_sensor1', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (4, NULL, 'parking_sensor2', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (5, NULL, 'parking_sensor3', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (6, NULL, 'parking_sensor4', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (7, NULL, 'parking_sensor5', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (8, NULL, 'temperature_sensor1', 'TEMPERATURE', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (9, NULL, 'temperature_sensor2', 'TEMPERATURE', NULL, NULL);

INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:31:46.000000', 105, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:32:25.000000', 110, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:36:12.000000', 80, 2);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:39:16.000000', 92, 2);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:41:19.000000', 94, 2);

INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 15:31:46.000000', false, 3);
INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 16:42:26.000000', true, 3);
INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 7:13:16.000000', true, 4);
INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 10:22:59.000000', false, 4);
INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 14:14:13.000000', true, 4);
INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 15:36:12.000000', false, 4);
INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 16:39:16.000000', true, 4);
INSERT INTO parking_data (time, value, sensor_id) VALUES ('2020-07-23 19:41:19.000000', false, 4);

INSERT INTO parking_groups (id, information, name) VALUES (1, NULL, 'parking_group1');
INSERT INTO parking_groups (id, information, name) VALUES (2, NULL, 'parking_group2');

INSERT INTO parking_group_sensors(parking_group_id, sensors_id) VALUES (1, 3);
INSERT INTO parking_group_sensors(parking_group_id, sensors_id) VALUES (1, 4);
INSERT INTO parking_group_sensors(parking_group_id, sensors_id) VALUES (1, 5);
INSERT INTO parking_group_sensors(parking_group_id, sensors_id) VALUES (1, 6);
INSERT INTO parking_group_sensors(parking_group_id, sensors_id) VALUES (2, 7);

INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (4, '2020-07-23 15:31:46.000000', 0, 1);
INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (3, '2020-07-23 16:42:26.000000', 1, 1);
INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (2, '2020-07-23 7:13:16.000000', 2, 1);
INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (3, '2020-07-23 10:22:59.000000', 1, 1);
INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (2, '2020-07-23 14:14:13.000000', 2, 1);
INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (3, '2020-07-23 15:36:12.000000', 1, 1);
INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (2, '2020-07-23 16:39:16.000000', 2, 1);
INSERT INTO parking_group_counters(free, time, used, parking_group_id) VALUES (3, '2020-07-23 19:41:19.000000', 1, 1);

INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:31:46.000000', 37.2, 1);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:32:25.000000', 37.5, 1);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:36:12.000000', 23.2, 2);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:39:16.000000', 23.45, 2);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:41:19.000000', 23.7, 2);