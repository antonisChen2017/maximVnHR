SELECT
  DAY,
  R.[EMPLOYEENO] 
,(case when charindex('<DAY/>',DAY)>0 
 then 
  CASE 
       when CONVERT(float,[HOLIDAYI])>0 then '傷'
       when CONVERT(float,[HOLIDAYB])>0 then '病'
       when CONVERT(float,[HOLIDAYA])>0 then '事'
       when CONVERT(float,[HOLIDAYD])>0 then '婚'
       when  CONVERT(float,[HOLIDAYF])>0 then '丧'
       when  CONVERT(float,[HOLIDAYE])>0 then '产'
       when  CONVERT(float,[HOLIDAYH])>0 then '年'
       when  CONVERT(float,[STOPWORK])>0 then '待'
       when  CONVERT(float,[ATTENDANCE])>0 then '上'
       when  CONVERT(float,[HOLIDAYG] )>0  then '调'
       when  CONVERT(float,[NOTWORK])>0 then '旷'
    END 
  else '' end ) 
  AS outDAY
 FROM VN_DAY_REPORT AS R
   JOIN VN_UNIT AS U On R.UNIT=U.UNIT
   JOIN VN_DEPARTMENT AS D On  R.DEPARTMENT=D.DEPARTMENT
WHERE DAY LIKE '<DAY/>'
--AND D.ID='FI'
--AND U.ID='11'
AND <DEPARTMENT/>
AND <UNIT/>
and DATEPART(weekday,DAY)!=1
GROUP BY  R.[EMPLOYEENO],DAY,[HOLIDAYI],[HOLIDAYB],[HOLIDAYA]
,[HOLIDAYC],[HOLIDAYD],[HOLIDAYF]
,[HOLIDAYE],[HOLIDAYH],[STOPWORK]
,[ATTENDANCE],[NOTWORI],[NOTWORK]
order by DAY 