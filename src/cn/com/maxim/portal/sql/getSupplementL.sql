SELECT
		D.DEPARTMENT AS '部门'
		,U.UNIT AS '单位'
      ,E.EMPLOYEE AS '人员'
      ,V.STARTDAY as '出差开始日'
      ,V.ENDDAY  as '出差结束日'
      ,V.NOTE as '备注'
      , V.ID as 'action'
       , V.ID 
  FROM [hr].[dbo].[VN_EMPLOYEE_SUPPLEMENT] V
  JOIN HR_EMPLOYEE  E
  ON V.EMPLOYEENO=E.ID
  JOIN  VN_UNIT U
   ON E.UNIT_ID=U.ID
  JOIN  VN_DEPARTMENT D
  ON D.ID=E.DEPARTMENT_ID
where V.STATUS='L'