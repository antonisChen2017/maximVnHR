SELECT [ID]
      ,[DEPT]
      ,[UNIT]
      ,[GROUP]
      ,[ROLE]
      ,[STATUS]
      ,[SINGROLEL0]
      ,[SINGROLEL1]
      ,[SINGROLEL2]
      ,[SINGROLEL3]
      ,[SINGROLEL4]
      ,[SINGROLEL0EP]
      ,[SINGROLEL1EP]
      ,[SINGROLEL2EP]
      ,[SINGROLEL3EP]
      ,[SINGROLEL4EP]
      ,[oneTitle]
      ,[twoTitle]
  FROM [hr].[dbo].[VN_DEPT_OVER_ROLE]
  where [DEPT]='<DEPT/>'
  AND [UNIT]='<UNIT/>'
  AND [STATUS]='<STATUS/>'
  AND [ROLE]='<ROLE/>'
    AND <GROUP/>