UPDATE [hr].[dbo].[VN_LEAVECARD]
      SET [SINGROLEL1] = '<SINGROLEL1/>'
      ,[SINGROLEL2] = '<SINGROLEL2/>'
      ,[SINGROLEL3] = '<SINGROLEL3/>'
      ,[SINGROLEL4] = '<SINGROLEL4/>'
       ,[SINGROLEL0] = '<SINGROLEL0/>'
       ,[SINGROLEL0EP] = '<SINGROLEL0EP/>'
      ,[SINGROLEL1EP] = '<SINGROLEL1EP/>'
      ,[SINGROLEL2EP] = '<SINGROLEL2EP/>'
      ,[SINGROLEL3EP] = '<SINGROLEL3EP/>'
      ,[SINGROLEL4EP] = '<SINGROLEL4EP/>'
 WHERE P_DEPT='<P_DEPT/>'
 AND P_UNIT='<P_UNIT/>'
 AND PROCESS='<PROCESS/>'
  AND P_ROLE='<P_ROLE/>'
  AND [GROUP]='<GROUP/>'
 AND LEAVEAPPLY<>'1'