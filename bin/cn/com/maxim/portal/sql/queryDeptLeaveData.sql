SELECT [ID]
      ,[DEPT]
      ,[UNIT]
      ,(case when [GROUP] IS null then '0' else [GROUP] end ) as [GROUP]
      ,[ROLE]
      ,[STATUS]
      ,(case when [SINGROLEL0] IS null then '0' else [SINGROLEL0] end ) as [SINGROLEL0]
      ,[SINGROLEL1]
      ,[SINGROLEL2]
      ,[SINGROLEL3]
      ,[SINGROLEL4]
      ,(case when [SINGROLEL0EP] IS null then '0' else [SINGROLEL0EP] end ) as  [SINGROLEL0EP]
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
  AND <GROUP/>