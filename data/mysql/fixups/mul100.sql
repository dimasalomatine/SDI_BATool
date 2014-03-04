/*
select * from menaya_base 
where name like '%BK%'
*/
declare @menaya_id varchar(10)
declare @sql varchar(8000)

declare cur_all cursor FOR
SELECT cast(menaya_id as varchar(10))--,*
 FROM MENAYA_BASE 
WHERE [update]=1
and market_id=1
AND FLOOR(CAST(since AS FLOAT))>=FLOOR(CAST(CAST('2012-09-03 00:00:00' AS DATETIME) AS FLOAT))
and menaya_id not in(90 ,92 ,91 ,467,993,994,1067,1397)

open cur_all
fetch next from cur_all into @menaya_id
while @@fetch_status = 0
begin 
  set @sql='SELECT * 
--UPDATE A SET [OPEN]=[OPEN]*100,[CLOSE]=[CLOSE]*100,[HI]=[HI]*100,[LO]=[LO]*100
 FROM ['+@menaya_id+'] A
WHERE FLOOR(CAST(DAY AS FLOAT))>=FLOOR(CAST(CAST(''2012-09-03 00:00:00'' AS DATETIME) AS FLOAT))
      AND FLOOR(CAST(DAY AS FLOAT))<=FLOOR(CAST(CAST(''2012-09-04 00:00:00'' AS DATETIME) AS FLOAT))'
      
      print @sql
			exec (@sql)

fetch next from cur_all into @menaya_id
end
close cur_all
deallocate cur_all

