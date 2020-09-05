
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (1, NULL, 'airQuality_sensor1', 'AIR_QUALITY', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (2, NULL, 'airQuality_sensor2', 'AIR_QUALITY', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (3, NULL, 'parking_sensor1', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (4, NULL, 'parking_sensor2', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (5, NULL, 'parking_sensor3', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (6, NULL, 'parking_sensor4', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (7, NULL, 'parking_sensor5', 'PARKING', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (8, NULL, 'temperature_sensor1', 'TEMPERATURE', NULL, NULL);
INSERT INTO sensors (id, information, name, sensor_type, x, y) VALUES (9, NULL, 'temperature_sensor2', 'TEMPERATURE', NULL, NULL);

INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:31:46.000000', 37, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:32:25.000000', 37, 2);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:36:12.000000', 23, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:39:16.000000', 23, 2);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-07-23 15:41:19.000000', 23, 1);

INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-01 15:31:46.000000', 13, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-01 15:32:25.000000', 15, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-01 15:36:12.000000', 18, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-01 15:39:16.000000', 19, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-03 15:41:19.000000', 22, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-04 15:31:46.000000', 21, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-04 15:32:25.000000', 19, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-04 15:36:12.000000', 19, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-04 15:39:16.000000', 20, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-04 15:41:19.000000', 20, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-05 15:31:46.000000', 20, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-05 15:32:25.000000', 20, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-05 15:36:12.000000', 21, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-05 15:39:16.000000', 22, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-05 15:41:19.000000', 23, 1);

INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-07 15:36:12.000000', 42, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-07 15:39:16.000000', 43, 1);
INSERT INTO air_quality_data (time, value, sensor_id) VALUES ('2020-09-07 15:41:19.000000', 45, 1);

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

INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:31:46.000000', 37.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:32:25.000000', 37.5, 9);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:36:12.000000', 23.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:39:16.000000', 23.45, 9);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-07-23 15:41:19.000000', 23.7, 8);

INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-01 15:31:46.000000', 13.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-01 15:32:25.000000', 15.5, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-01 15:36:12.000000', 18.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-01 15:39:16.000000', 19.45, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-03 15:41:19.000000', 22.7, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-04 15:31:46.000000', 21.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-04 15:32:25.000000', 19.5, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-04 15:36:12.000000', 19.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-04 15:39:16.000000', 20.45, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-04 15:41:19.000000', 20.7, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-05 15:31:46.000000', 20.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-05 15:32:25.000000', 20.5, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-05 15:36:12.000000', 21.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-05 15:39:16.000000', 22.45, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-05 15:41:19.000000', 23.7, 8);

INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-07 15:36:12.000000', 42.2, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-07 15:39:16.000000', 43.45, 8);
INSERT INTO temperature_data (time, value, sensor_id) VALUES ('2020-09-07 15:41:19.000000', 45.7, 8);


-- Temperature Aggregate
INSERT INTO temperature_average_daily (value, time, sensor_id) VALUES (20.25, '2020-08-20 01:00:00.000000', 8);
INSERT INTO temperature_average_daily (value, time, sensor_id) VALUES (32.01, '2020-08-20 01:00:00.000000', 9);
INSERT INTO temperature_average_daily (value, time, sensor_id) VALUES (5.0, '2020-08-21 01:00:00.000000', 8);
INSERT INTO temperature_average_daily (value, time, sensor_id) VALUES (10.11, '2020-08-21 01:00:00.000000', 9);

INSERT INTO temperature_average_weekly (value, begin_date, end_date, sensor_id) VALUES (18.35, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 8);
INSERT INTO temperature_average_weekly (value, begin_date, end_date, sensor_id) VALUES (45.02, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 9);
INSERT INTO temperature_average_weekly (value, begin_date, end_date, sensor_id) VALUES (-6.1, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 8);
INSERT INTO temperature_average_weekly (value, begin_date, end_date, sensor_id) VALUES (8.04, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 9);

INSERT INTO temperature_average_monthly (value, begin_date, end_date, sensor_id) VALUES (65.35, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 8);
INSERT INTO temperature_average_monthly (value, begin_date, end_date, sensor_id) VALUES (12.02, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 9);
INSERT INTO temperature_average_monthly (value, begin_date, end_date, sensor_id) VALUES (-3.1, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 8);
INSERT INTO temperature_average_monthly (value, begin_date, end_date, sensor_id) VALUES (5.04, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 9);

INSERT INTO temperature_maximum_daily (value, time, sensor_id) VALUES (20.25, '2020-08-20 01:00:00.000000', 8);
INSERT INTO temperature_maximum_daily (value, time, sensor_id) VALUES (32.01, '2020-08-20 01:00:00.000000', 9);
INSERT INTO temperature_maximum_daily (value, time, sensor_id) VALUES (5.0, '2020-08-21 01:00:00.000000', 8);
INSERT INTO temperature_maximum_daily (value, time, sensor_id) VALUES (10.11, '2020-08-21 01:00:00.000000', 9);

INSERT INTO temperature_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (18.35, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 8);
INSERT INTO temperature_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (45.02, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 9);
INSERT INTO temperature_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (-6.1, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 8);
INSERT INTO temperature_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (8.04, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 9);

INSERT INTO temperature_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (65.35, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 8);
INSERT INTO temperature_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (12.02, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 9);
INSERT INTO temperature_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (-3.1, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 8);
INSERT INTO temperature_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (5.04, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 9);

INSERT INTO temperature_minimum_daily (value, time, sensor_id) VALUES (20.25, '2020-08-20 01:00:00.000000', 8);
INSERT INTO temperature_minimum_daily (value, time, sensor_id) VALUES (32.01, '2020-08-20 01:00:00.000000', 9);
INSERT INTO temperature_minimum_daily (value, time, sensor_id) VALUES (5.0, '2020-08-21 01:00:00.000000', 8);
INSERT INTO temperature_minimum_daily (value, time, sensor_id) VALUES (10.11, '2020-08-21 01:00:00.000000', 9);

INSERT INTO temperature_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (18.35, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 8);
INSERT INTO temperature_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (45.02, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 9);
INSERT INTO temperature_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (-6.1, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 8);
INSERT INTO temperature_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (8.04, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 9);

INSERT INTO temperature_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (65.35, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 8);
INSERT INTO temperature_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (12.02, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 9);
INSERT INTO temperature_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (-3.1, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 8);
INSERT INTO temperature_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (5.04, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 9);

-- Air Quality Aggregate
INSERT INTO air_quality_average_daily (value, time, sensor_id) VALUES (20.01, '2020-08-20 01:00:00.000000', 1);
INSERT INTO air_quality_average_daily (value, time, sensor_id) VALUES (32, '2020-08-20 01:00:00.000000', 2);
INSERT INTO air_quality_average_daily (value, time, sensor_id) VALUES (5, '2020-08-21 01:00:00.000000', 1);
INSERT INTO air_quality_average_daily (value, time, sensor_id) VALUES (10.57, '2020-08-21 01:00:00.000000', 2);

INSERT INTO air_quality_average_weekly (value, begin_date, end_date, sensor_id) VALUES (18, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 1);
INSERT INTO air_quality_average_weekly (value, begin_date, end_date, sensor_id) VALUES (45.87, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 2);
INSERT INTO air_quality_average_weekly (value, begin_date, end_date, sensor_id) VALUES (-6.71, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 1);
INSERT INTO air_quality_average_weekly (value, begin_date, end_date, sensor_id) VALUES (8, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 2);

INSERT INTO air_quality_average_monthly (value, begin_date, end_date, sensor_id) VALUES (65.43, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 1);
INSERT INTO air_quality_average_monthly (value, begin_date, end_date, sensor_id) VALUES (12, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 2);
INSERT INTO air_quality_average_monthly (value, begin_date, end_date, sensor_id) VALUES (-3, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 1);
INSERT INTO air_quality_average_monthly (value, begin_date, end_date, sensor_id) VALUES (5.66, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 2);

INSERT INTO air_quality_maximum_daily (value, time, sensor_id) VALUES (20, '2020-08-20 01:00:00.000000', 1);
INSERT INTO air_quality_maximum_daily (value, time, sensor_id) VALUES (32, '2020-08-20 01:00:00.000000', 2);
INSERT INTO air_quality_maximum_daily (value, time, sensor_id) VALUES (5, '2020-08-21 01:00:00.000000', 1);
INSERT INTO air_quality_maximum_daily (value, time, sensor_id) VALUES (10, '2020-08-21 01:00:00.000000', 2);

INSERT INTO air_quality_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (18, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 1);
INSERT INTO air_quality_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (45, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 2);
INSERT INTO air_quality_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (-6, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 1);
INSERT INTO air_quality_maximum_weekly (value, begin_date, end_date, sensor_id) VALUES (8, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 2);

INSERT INTO air_quality_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (65, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 1);
INSERT INTO air_quality_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (12, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 2);
INSERT INTO air_quality_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (-3, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 1);
INSERT INTO air_quality_maximum_monthly (value, begin_date, end_date, sensor_id) VALUES (5, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 2);

INSERT INTO air_quality_minimum_daily (value, time, sensor_id) VALUES (20, '2020-08-20 01:00:00.000000', 1);
INSERT INTO air_quality_minimum_daily (value, time, sensor_id) VALUES (32, '2020-08-20 01:00:00.000000', 2);
INSERT INTO air_quality_minimum_daily (value, time, sensor_id) VALUES (5, '2020-08-21 01:00:00.000000', 1);
INSERT INTO air_quality_minimum_daily (value, time, sensor_id) VALUES (10, '2020-08-21 01:00:00.000000', 2);

INSERT INTO air_quality_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (18, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 1);
INSERT INTO air_quality_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (45, '2020-08-17 01:00:00.000000', '2020-08-24 01:00:00.000000', 2);
INSERT INTO air_quality_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (-6, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 1);
INSERT INTO air_quality_minimum_weekly (value, begin_date, end_date, sensor_id) VALUES (8, '2020-08-24 01:00:00.000000', '2020-08-31 01:00:00.000000', 2);

INSERT INTO air_quality_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (65, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 1);
INSERT INTO air_quality_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (12, '2020-07-01 01:00:00.000000', '2020-08-01 01:00:00.000000', 2);
INSERT INTO air_quality_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (-3, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 1);
INSERT INTO air_quality_minimum_monthly (value, begin_date, end_date, sensor_id) VALUES (5, '2020-08-01 01:00:00.000000', '2020-09-01 01:00:00.000000', 2);