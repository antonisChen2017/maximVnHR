SELECT
[EMPLOYEENO] AS '工号'
,[EMPLOYEE] AS '姓名'
,[DEPARTMENT] AS '部门'
,[UNIT] AS '单位'
,[ATTENDANCE] AS '正班出勤'
,[OVERTIME] AS '加班'
,[HOLIDAYH] AS '年假'
,[HOLIDAYC] AS '公假'
,[HOLIDAYE] AS '产假'
,[HOLIDAYD] AS '婚假'
,[HOLIDAYF] AS '丧假'
,[HOLIDAYB] AS '病假'
,[HOLIDAYA] AS '事假'
,[NOTWORK] AS '旷工'
,[BELATE] AS '迟到'
,[STOPWORK] AS '待工'
,[MEALS] AS '补贴餐费'
,[NOTE] AS '备注'
  FROM [hr].[dbo].[VN_DAY_REPORT]
WHERE DAY='<DAY/>'
order by [EMPLOYEENO] 