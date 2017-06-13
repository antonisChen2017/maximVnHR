SELECT
DAY,
[EMPLOYEENO] 
,[EMPLOYEE] 
,[DEPARTMENT] 
,[UNIT] 
 ,STATUS = 
    CASE 
       when CONVERT(float,[HOLIDAYI])>0 then 'L'
       when CONVERT(float,[HOLIDAYB])>0 then 'B'
       when CONVERT(float,[HOLIDAYA])>0 then 'R'
       when CONVERT(float,[HOLIDAYC])>0 then 'N'
       when CONVERT(float,[HOLIDAYD])>0 then 'H'
       when  CONVERT(float,[HOLIDAYF])>0 then 'T'
       when  CONVERT(float,[HOLIDAYE])>0 then 'TS'
       when  CONVERT(float,[HOLIDAYH])>0 then 'P'
       when  CONVERT(float,[STOPWORK])>0 then 'W'
       when  CONVERT(float,[ATTENDANCE])>0 then 'X'
       when  CONVERT(float,[NOTWORK])>0 then 'O'
    END 
,[ATTENDANCE] 
,[OVERTIME] 
,[HOLIDAYH] 
,[HOLIDAYC] 
,[HOLIDAYE] 
,[HOLIDAYD] 
,[HOLIDAYF] 
,[HOLIDAYB] 
,[HOLIDAYA] 
,[HOLIDAYI] 
,[NOTWORK] 
,[BELATE] 
,[STOPWORK] 
,[MEALS] 
,[NOTE] 
  FROM [hr].[dbo].[VN_DAY_REPORT]
WHERE DAY LIKE '<DAY/>%'
and DATEPART(weekday,DAY)!=1
order by [EMPLOYEENO] 