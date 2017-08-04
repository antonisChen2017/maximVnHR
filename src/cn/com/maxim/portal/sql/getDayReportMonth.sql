SELECT
DAY,
 R.[EMPLOYEENO] 
,R.[EMPLOYEE] 
,D.ID AS DEPARTMENT
,U.ID AS UNIT
 ,STATUS = 
    CASE 
       when CONVERT(float,[HOLIDAYI])>0 then '伤'
        when CONVERT(float,[HOLIDAYG])>0 then '调'
       when CONVERT(float,[HOLIDAYB])>0 then '病'
       when CONVERT(float,[HOLIDAYA])>0 then '事'
       when CONVERT(float,[HOLIDAYD])>0 then '婚'
       when  CONVERT(float,[HOLIDAYF])>0 then '丧'
       when  CONVERT(float,[HOLIDAYE])>0 then '产'
       when  CONVERT(float,[HOLIDAYH])>0 then '年'
       when  CONVERT(float,[STOPWORK])>0 then '待'
       when  CONVERT(float,[ATTENDANCE])>0 then '上'
        when CONVERT(float,[HOLIDAYO])>0 then '公'
       when  CONVERT(float,[NOTWORK])>0 then '旷'
    END 
  ,[ATTENDANCE] 
,[OVERTIME] ,
   [HOLIDAYH] ,
	[HOLIDAYI] ,
	[HOLIDAYE] ,
	[HOLIDAYD] ,	
	[HOLIDAYF] ,
	[HOLIDAYB] ,
	[HOLIDAYA] ,	
	[HOLIDAYG] ,
	[HOLIDAYO] ,
	[HOLIDAYS] 
,[NOTWORK] 
,[BELATE] 
,[EARLY] 
,[STOPWORK] 
,[MEALS] 
,[NOTE] 
  FROM 
  VN_DAY_REPORT AS R
   JOIN VN_UNIT AS U On R.UNIT=U.UNIT
   JOIN VN_DEPARTMENT AS D On  R.DEPARTMENT=D.DEPARTMENT
   JOIN HR_EMPLOYEE AS H On  R.EMPLOYEENO=H.EMPLOYEENO
WHERE DAY  LIKE '<DAY/>%'
and DATEPART(weekday,DAY)!=1
order by DAY 