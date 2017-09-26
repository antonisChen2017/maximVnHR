SELECT [ID]
      ,[DEPT]
      ,[UNIT]
      ,[GROUP]
      ,[ROLE]
      ,[STATUS]
      ,(case when[SINGROLEL0] IS NULL then '0' else [SINGROLEL0] end ) as SINGROLEL0
      ,(case when[SINGROLEL1] IS NULL then '0' else [SINGROLEL1] end ) as SINGROLEL1
      ,(case when[SINGROLEL2] IS NULL then '0' else [SINGROLEL2] end ) as SINGROLEL2
      ,(case when[SINGROLEL3] IS NULL then '0' else [SINGROLEL3] end ) as SINGROLEL3
      ,(case when[SINGROLEL4] IS NULL then '0' else [SINGROLEL4] end ) as SINGROLEL4
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