SELECT  
	[EMPLOYEENO] as '工号'
,[EMPLOYEE] as '姓名'
,DR.DEPARTMENT  as '部门'
,DR.UNIT  as '单位'
,[ATTENDANCE] as '正班出勤' 
,[OVERTIME]  as '加班'
,[HOLIDAYH]  as '年假'
,[HOLIDAYE]  as '产假'
,[HOLIDAYD]  as '婚假'
,[HOLIDAYF]  as '丧假'
,[HOLIDAYB]  as '病假'
,[HOLIDAYA] as '事假'
,[HOLIDAYI]  as '工傷'
,[HOLIDAYG]  as '調休'
,[HOLIDAYO]  as '公假'
,[NOTWORK] as '曠工'
,[BELATE] as '遲到'
,[EARLY] as '早退'
,[STOPWORK]  as '早退'
,[MEALS] as '簽名'
,[NOTE]  as '備註'
  FROM [hr].[dbo].[VN_DAY_REPORT] DR
JOIN  VN_UNIT VU ON DR.UNIT=VU.UNIT
JOIN VN_DEPARTMENT VD ON VD.DEPARTMENT=DR.DEPARTMENT
WHERE DAY='<DAY/>'
AND <DEPT/>
AND <UNIT/>
order by DR.DEPARTMENT DESC, DR.UNIT DESC