SELECT 
		[DEPARTMENT_ID] as DEPARTMENT,
		[UNIT_ID] as UNIT
      , ROLE
      ,' ' as STATUS
      ,(case when [GROUP] IS null then '0' else [GROUP] end )[GROUP]
  FROM [hr].[dbo].[HR_EMPLOYEE] E
    FULL  JOIN VN_GROUP_EMP G
   ON E.EMPLOYEENO=G.EMPLOYEENO
where E.[ID]='<EMPLOYEENO/>'