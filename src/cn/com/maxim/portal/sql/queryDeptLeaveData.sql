SELECT [ID]
      ,[DEPT]
      ,[UNIT]
      ,[ROLE]
      ,[STATUS]
      ,[SINGROLEL1]
      ,[SINGROLEL2]
      ,[SINGROLEL3]
      ,[SINGROLEL4]
      ,[SINGROLEL1EP]
      ,[SINGROLEL2EP]
      ,[SINGROLEL3EP]
      ,[SINGROLEL4EP]
      ,[oneTitle]
      ,[twoTitle]
  FROM [hr].[dbo].[VN_DEPT_LEAVE_ROLE]
  where [DEPT]='<DEPT/>'
  AND [UNIT]='<UNIT/>'
  AND [STATUS]='<STATUS/>'
  AND [ROLE]='<ROLE/>'