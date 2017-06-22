SELECT 
		[DEPARTMENT_ID] as DEPARTMENT,
		[UNIT_ID] as UNIT
      , ROLE
      ,' ' as STATUS
  FROM [hr].[dbo].[HR_EMPLOYEE]
where [ID]='<EMPLOYEENO/>'