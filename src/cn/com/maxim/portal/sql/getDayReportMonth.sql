SELECT
DAY,
 R.[EMPLOYEENO] 
,R.[EMPLOYEE]+' '+H.VIETNAMESE as EMPLOYEE
,D.ID AS DEPARTMENT
,U.ID AS UNIT
,[ATTENDANCE] 
 ,STATUS = 
    CASE 
       when CONVERT(float,[HOLIDAYI])>0 then '事'
       when CONVERT(float,[HOLIDAYB])>0 then '病'
       when CONVERT(float,[HOLIDAYA])>0 then '事'
       when CONVERT(float,[HOLIDAYC])>0 then '公'
       when CONVERT(float,[HOLIDAYD])>0 then '婚'
       when  CONVERT(float,[HOLIDAYF])>0 then '丧'
       when  CONVERT(float,[HOLIDAYE])>0 then '产'
       when  CONVERT(float,[HOLIDAYH])>0 then '特'
       when  CONVERT(float,[STOPWORK])>0 then '待'
       when  CONVERT(float,[ATTENDANCE])>0 then '上'
       when  CONVERT(float,[NOTWORK])>0 then '旷'
    END 
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
  FROM 
  VN_DAY_REPORT AS R
   JOIN VN_UNIT AS U On R.UNIT=U.UNIT
   JOIN VN_DEPARTMENT AS D On  R.DEPARTMENT=D.DEPARTMENT
   JOIN HR_EMPLOYEE AS H On  R.EMPLOYEENO=H.EMPLOYEENO
WHERE DAY  LIKE '<DAY/>%'
and DATEPART(weekday,DAY)!=1
order by DAY 