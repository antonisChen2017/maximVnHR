SELECT  
	ROW_NUMBER() over(order by [EMPLOYEENO]) as ID,
	[EMPLOYEENO] 
,DR.EMPLOYEE
,DR.DEPARTMENT
,DR.UNIT
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
,[HOLIDAYG] 
,[HOLIDAYO] 
,[NOTWORK] 
,[BELATE] 
,[EARLY] 
,[STOPWORK] 
,[MEALS] 
,[NOTE] 
  FROM [hr].[dbo].[VN_DAY_REPORT] DR
  JOIN  VN_UNIT VU ON DR.UNIT=VU.UNIT
JOIN VN_DEPARTMENT VD ON VD.DEPARTMENT=DR.DEPARTMENT
WHERE DAY='<DAY/>'
AND <DEPT/>
AND <UNIT/>
order by DR.DEPARTMENT DESC, DR.UNIT DESC