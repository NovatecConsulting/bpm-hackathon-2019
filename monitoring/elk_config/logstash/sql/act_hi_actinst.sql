SELECT * from act_hi_actinst
where start_time_ > :sql_last_value or end_time_ > :sql_last_value