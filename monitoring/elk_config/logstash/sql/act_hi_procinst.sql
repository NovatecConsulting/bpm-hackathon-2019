SELECT *, 'act_hi_procinst' as source from act_hi_procinst
where end_time_ is not null and start_time_ > :sql_last_value